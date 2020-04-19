import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

public class dataAggregator {

	final static String FILE_NAME = "/Users/Shared/testData/PROD_data_check_16022018_2.xlsx";
	final static String buyOnlineUrl = "http://pre-vtp.audi.com/restapi/v1/adegwb/search/car;t_online=1/results";
	final String carDealerUrl = "http://pre-vtp.audi.com/restapi/v1/adegwb/datastore/cars/";
	final String carDealerByIdUrl = "http://www.audi-boerse.de/gebrauchtwagen/url-2_1-search.htm?act=offer&carid=";
	final static String xPageItems = "100";
	static int numberOfReturnedCars = 1;

	public static void main(String[] args) {

		dataAggregator converter = new dataAggregator();

		try {
			int xPageCounter = 1;
			ArrayList<CarDealerSelector> listOfCarDealerComplete = new ArrayList<CarDealerSelector>();
			
			while (numberOfReturnedCars != 0) {
				HttpURLConnection conn = getHttpConnection(buyOnlineUrl, Integer.toString(xPageCounter++));
				String jsonOutput = readInputStream(conn);
				ArrayList<CarDealerSelector> listOfCarDealers = converter.getValuesFromJson(jsonOutput);
				
				for (CarDealerSelector elementOfCarDealers : listOfCarDealers) {
					listOfCarDealerComplete.add(elementOfCarDealers);
				}

				IOOperations.writeArrayListToExcel(FILE_NAME, listOfCarDealers, "fullListCarDealer");
				
//				Checks if more iterations are neccessary. If not (no full result [max pageItems] set delivered) numberOfReturnedCars is set to 0
				if (numberOfReturnedCars != Integer.parseInt(xPageItems)) {
					numberOfReturnedCars = 0;
				}
				conn.disconnect();
			}
			IOOperations.writeArrayListToExcel(FILE_NAME, Helpers.compareCheckedVehicles(listOfCarDealerComplete),
					"reducedListCarDealer");

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private static String readInputStream(HttpURLConnection conn) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		String saveOutput = "";
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			saveOutput = output;
		}
		return saveOutput;
	}

	private static HttpURLConnection getHttpConnection(final String targetUrl, String xPage)
			throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL(targetUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("X-Pattern", "Audi_EComGW_ae8bd28b");
		conn.setRequestProperty("X-Page-Items", xPageItems);
		conn.setRequestProperty("X-Page", xPage);

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
		return conn;
	}

	public ArrayList<CarDealerSelector> getValuesFromJson(String json)
			throws JsonParseException, JsonMappingException, IOException {

		ArrayList<CarDealerSelector> listOfCarsWithDealers = new ArrayList<CarDealerSelector>();

		JsonNode rootNode = new ObjectMapper().readTree(json);
		ArrayNode carsNode = (ArrayNode) rootNode.get("cars");
		Iterator<JsonNode> carsNodeIterator = carsNode.getElements();

		while (carsNodeIterator.hasNext()) {

			JsonNode carNode = carsNodeIterator.next();

			CarDealerSelector car = new CarDealerSelector();
			if (carNode.path("car").path("carid").getTextValue().equals("DEU28100A1 WQS 04")) {
				System.out.println("Das ist die ID: DEU28100A1 WQS 04");
			}
			car.setCarId(carNode.path("car").path("carid").getTextValue());
			car.setDealerKey(carNode.path("car").path("hypermediadealer").path("key").getTextValue());
			car.setDealerHref(carNode.path("car").path("hypermediadealer").path("href").getTextValue());
			if (car.getCarId().contains(" ")) {
				String escapedCarId = car.getCarId().replaceAll(" ", "%20");
				car.setCarDealerUrl(carDealerUrl + escapedCarId);
			} else {
				car.setCarDealerUrl(carDealerUrl + car.getCarId());
			}
			car.setLinkToCarById(carDealerByIdUrl + car.getCarId());
			
			ArrayNode itemsNode = (ArrayNode) carNode.path("car").path("items");
			Iterator<JsonNode> itemsNodeIterator = itemsNode.getElements();
			while (itemsNodeIterator.hasNext()) {
				JsonNode itemNode = itemsNodeIterator.next();
				if (itemNode.path("key").getTextValue().equals("buy_online")) {
					car.setBuyOnline(itemNode.path("value").getTextValue());
					break;
				}
			}

			ArrayNode dealerItemsNode = (ArrayNode) carNode.path("car").path("hypermediadealer").path("dealer")
					.path("items");
			Iterator<JsonNode> dealerItemsIterator = dealerItemsNode.getElements();
			while (dealerItemsIterator.hasNext()) {
				JsonNode dealerItemNode = dealerItemsIterator.next();
				if (dealerItemNode.path("key").getTextValue().equals("name")) {
					car.setDealerName(dealerItemNode.path("value").getTextValue());
				}
				if (dealerItemNode.path("key").getTextValue().equals("zip")) {
					car.setDealerZip(dealerItemNode.path("value").getTextValue());
				}
				if (dealerItemNode.path("key").getTextValue().equals("city")) {
					car.setDealerCity(dealerItemNode.path("value").getTextValue());
				}
			}

			HttpURLConnection conn = getHttpConnection(car.getCarDealerUrl(), "1");
			String jsonOutputCarDetail = readInputStream(conn);

			Helpers.getCarsDetails(car, jsonOutputCarDetail);

			listOfCarsWithDealers.add(car);
		}
		numberOfReturnedCars = rootNode.path("cars").size();
		System.out.println("Size of cars-array: " + rootNode.path("cars").size());

		return listOfCarsWithDealers;
	}



}
