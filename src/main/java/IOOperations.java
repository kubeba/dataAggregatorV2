import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.awt.Color;

public class IOOperations {

	private static final String FILE_NAME = "/Users/Shared/testData/checkedVehicles.xlsx";

	static void writeArrayListToExcel(final String file_name, ArrayList<CarDealerSelector> finalJavaObject,
			String nameSheet) throws EncryptedDocumentException, IOException {
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		int lastRow = 0;
		File excelFile = new File(file_name);
		if (excelFile.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(new File(file_name));
				workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);
				if (workbook.getSheet(nameSheet) == null) {
					sheet = workbook.createSheet(nameSheet);
				} else {
					sheet = workbook.getSheet(nameSheet);
					lastRow = sheet.getPhysicalNumberOfRows();
				}
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
		} else {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(nameSheet);
		}

		int rowNum = lastRow;

		CellStyle style = workbook.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		style.setFillPattern(FillPatternType.BIG_SPOTS);

		XSSFColor colorGreen = new XSSFColor(Color.GREEN);

		for (CarDealerSelector objectFromList : finalJavaObject) {

			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			Cell cell;
			cell = row.createCell(colNum++);
			if (objectFromList.getDealerName() instanceof String) {
				cell.setCellValue(objectFromList.getDealerName());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getDealerZip() instanceof String) {
				cell.setCellValue(objectFromList.getDealerZip());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getDealerCity() instanceof String) {
				cell.setCellValue(objectFromList.getDealerCity());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getDealerKey() instanceof String) {
				cell.setCellValue(objectFromList.getDealerKey());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getDealerHref() instanceof String) {
				cell.setCellValue(objectFromList.getDealerHref());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getCarId() instanceof String) {
				cell.setCellValue(objectFromList.getCarId());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getCommissionId() instanceof String) {
				cell.setCellValue(objectFromList.getCommissionId());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getVin() instanceof String) {
				cell.setCellValue(objectFromList.getVin());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getBuyOnline() instanceof String) {
				cell.setCellValue(objectFromList.getBuyOnline());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getCarDealerUrl() instanceof String) {
				cell.setCellValue(objectFromList.getCarDealerUrl());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getLinkToCarById() instanceof String) {
				cell.setCellValue(objectFromList.getLinkToCarById());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getFirstUsage() instanceof String) {
				cell.setCellValue(objectFromList.getFirstUsage());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getReduction() instanceof String) {
				cell.setCellValue(objectFromList.getReduction());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getFuel() instanceof String) {
				cell.setCellValue(objectFromList.getFuel());
				setCellColor(cell, workbook, objectFromList.getFuel());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getPowerKw() instanceof String) {
				cell.setCellValue(objectFromList.getPowerKw());
				setCellColor(cell, workbook, objectFromList.getPowerKw());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getPowerPs() instanceof String) {
				cell.setCellValue(objectFromList.getPowerPs());
				setCellColor(cell, workbook, objectFromList.getPowerPs());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getGear() instanceof String) {
				cell.setCellValue(objectFromList.getGear());
				setCellColor(cell, workbook, objectFromList.getGear());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getDrive() instanceof String) {
				cell.setCellValue(objectFromList.getDrive());
				setCellColor(cell, workbook, objectFromList.getDrive());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getConsumptionComb() instanceof String) {
				cell.setCellValue(objectFromList.getConsumptionComb());
				setCellColor(cell, workbook, objectFromList.getConsumptionComb());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getColorOut() instanceof String) {
				cell.setCellValue(objectFromList.getColorOut());
				setCellColor(cell, workbook, objectFromList.getColorOut());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getColorSeat() instanceof String) {
				cell.setCellValue(objectFromList.getColorSeat());
				setCellColor(cell, workbook, objectFromList.getColorSeat());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getPadtype() instanceof String) {
				cell.setCellValue(objectFromList.getPadtype());
				setCellColor(cell, workbook, objectFromList.getPadtype());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getInitialreg() instanceof String) {
				cell.setCellValue(objectFromList.getInitialreg());
				setCellColor(cell, workbook, objectFromList.getInitialreg());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getMileage() instanceof String) {
				cell.setCellValue(objectFromList.getMileage());
				setCellColor(cell, workbook, objectFromList.getMileage());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getGeneralInspection() instanceof String) {
				cell.setCellValue(objectFromList.getGeneralInspection());
				setCellColor(cell, workbook, objectFromList.getGeneralInspection());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getAvailableFrom() instanceof String) {
				cell.setCellValue(objectFromList.getAvailableFrom());
				setCellColor(cell, workbook, objectFromList.getAvailableFrom());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getPreuse() instanceof String) {
				cell.setCellValue(objectFromList.getPreuse());
				setCellColor(cell, workbook, objectFromList.getPreuse());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getPown() instanceof String) {
				cell.setCellValue(objectFromList.getPown());
				setCellColor(cell, workbook, objectFromList.getPown());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getAsg() instanceof String) {
				cell.setCellValue(objectFromList.getAsg());
				setCellColor(cell, workbook, objectFromList.getAsg());
			}
			cell = row.createCell(colNum++);
			if (objectFromList.getGwplus() instanceof String) {
				cell.setCellValue(objectFromList.getGwplus());
				cell.setCellStyle(style);
			} else {
				cell.setCellValue("Non-string input");
				System.out.println("somehow some non-string value appeared.");
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file_name);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	protected static Cell setCellColor(Cell cell, XSSFWorkbook wb, String value) {
		CellStyle style = wb.createCellStyle();
		
		if (value.isEmpty()) {
			style.setFillBackgroundColor(HSSFColor.RED.index);	
		} else {
			style.setFillBackgroundColor(HSSFColor.WHITE.index);
		}
		style.setFillPattern(FillPatternType.THIN_BACKWARD_DIAG);
		cell.setCellStyle(style);

		return cell;
	}

	public static List<String> readFromExcel() {

		List<String> carsChecked = new ArrayList<String>();
		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					if (currentCell.getStringCellValue() instanceof String) {
						carsChecked.add(currentCell.getStringCellValue());
					} else {
						carsChecked.add("Non-String value");
					}

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return carsChecked;
	}
}