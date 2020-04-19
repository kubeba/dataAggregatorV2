import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

public class Helpers {
	
	public static ArrayList<CarDealerSelector> compareCheckedVehicles(ArrayList<CarDealerSelector> onlineCarList) {

		ArrayList<CarDealerSelector> reducedListOfCarsWithDealers = new ArrayList<CarDealerSelector>(onlineCarList);
		List<String> checkedCars = IOOperations.readFromExcel();
		
		for (CarDealerSelector onlineCar : onlineCarList) {
			for (String checkedCar : checkedCars) {
				if (onlineCar.getCarId().equals(checkedCar)) {
					reducedListOfCarsWithDealers.remove(onlineCar);	
					break;
				}
			}
		}
		return reducedListOfCarsWithDealers;
	}
	
	public static CarDealerSelector getCarsDetails(CarDealerSelector car, String jsonOutputCarDetail) throws JsonProcessingException, IOException {
		
		JsonNode carDetailNode = new ObjectMapper().readTree(jsonOutputCarDetail);
		ArrayNode carDetailsItemsNode = (ArrayNode) carDetailNode.get("items");
		Iterator<JsonNode> carsDetailItemsNodeIterator = carDetailsItemsNode.getElements();
		
		while (carsDetailItemsNodeIterator.hasNext()) {
			JsonNode itemNode = carsDetailItemsNodeIterator.next();
			if (itemNode.path("key").getTextValue().equals("knr")) {
				car.setCommissionId(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("vin")) {
				car.setVin(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("residual_value_reduction")) {
				car.setReduction(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("fsFinance")) {
				JsonNode fsFinanceNode = itemNode.get("values");
				Iterator<JsonNode> fsFinanceNodeIterator = fsFinanceNode.getElements();
				while (fsFinanceNodeIterator.hasNext()) {
					JsonNode fsNode = fsFinanceNodeIterator.next();
					if (fsNode.path("key").getTextValue().equals("firstUsage")) {
						car.setFirstUsage(fsNode.path("value").getTextValue());
						break;
					}
				}
			}
			if (itemNode.path("key").getTextValue().equals("gear")) {
				car.setGear(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("drive")) {
				car.setDrive(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("padtype")) {
				car.setPadtype(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("initialreg")) {
				car.setInitialreg(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("mileage")) {
				car.setMileage(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("generalInspection")) {
				car.setGeneralInspection(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("available_from")) {
				car.setAvailableFrom(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("preuse")) {
				car.setPreuse(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("pown")) {
				car.setPown(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("asg")) {
				car.setAsg(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("gwplus")) {
				car.setGwplus(itemNode.path("value").getTextValue());
			}
			if (itemNode.path("key").getTextValue().equals("motor")) {
				JsonNode motorsNode = itemNode.get("values");
				Iterator<JsonNode> motorNodeIterator = motorsNode.getElements();
				while (motorNodeIterator.hasNext()) {
					JsonNode motorNode = motorNodeIterator.next();
					if (motorNode.path("key").getTextValue().equals("fuel")) {
						car.setFuel(motorNode.path("value").getTextValue());
					}
					if (motorNode.path("key").getTextValue().equals("power.kw")) {
						car.setPowerKw(motorNode.path("value").getTextValue());
					}
					if (motorNode.path("key").getTextValue().equals("power.ps")) {
						car.setPowerPs(motorNode.path("value").getTextValue());
					}
				}
			}
			if (itemNode.path("key").getTextValue().equals("consumption")) {
				JsonNode consumptionNode = itemNode.get("values");
				Iterator<JsonNode> consumptionNodeIterator = consumptionNode.getElements();
				while (consumptionNodeIterator.hasNext()) {
					JsonNode consumValueNode = consumptionNodeIterator.next();
					if (consumValueNode.path("key").getTextValue().equals("comb")) {
						car.setConsumptionComb(consumValueNode.path("value").getTextValue());
						break;
					}
				}
			}
			if (itemNode.path("key").getTextValue().equals("color")) {
				JsonNode colorNode = itemNode.get("values");
				Iterator<JsonNode> colorNodeIterator = colorNode.getElements();
				while (colorNodeIterator.hasNext()) {
					JsonNode colorValueNode = colorNodeIterator.next();
					if (colorValueNode.path("key").getTextValue().equals("out")) {
						car.setColorOut(colorValueNode.path("value").getTextValue());
					}
					if (colorValueNode.path("key").getTextValue().equals("in")) {
						JsonNode colorInNode = colorValueNode.get("values");
						Iterator<JsonNode> colorInNodeIterator = colorInNode.getElements();
						while (colorInNodeIterator.hasNext()) {
							JsonNode colorInValueNode = colorInNodeIterator.next();
							if (colorInValueNode.path("key").getTextValue().equals("seat")){
								car.setColorSeat(colorInValueNode.path("value").getTextValue());
								break;
							}
						}
					}
				}
			}
		} 
		return car;
	}
}
