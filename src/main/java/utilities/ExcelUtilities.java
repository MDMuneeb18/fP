package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {
	public static FileInputStream fis;
	public static FileOutputStream fos;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static CellStyle style;
	
	public static String getCellData(String xlFile, String xlSheet, int rowNo, int cellNo) throws IOException {
		fis = new FileInputStream(xlFile);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(xlSheet);
		row = sheet.getRow(rowNo);
		cell = row.getCell(cellNo);
		String cellData;
		//here we try catch block bcoz some times data present in cell or not present to overcome that we use try catch block
		try {
			cellData = cell.toString();
			//can use above one also to retrieve data but below one is also a method
//			DataFormatter formatter = new DataFormatter();
//			cellData = formatter.formatCellValue(cell);
			//returns formatted value of a cell as a string regardless of the cell type
		}
		catch(Exception e) {
			cellData="";
		}
		workbook.close();
		fis.close();
		return cellData;
	}
	public static void setCellData(String xlFile, String xlSheet, int rowNo, int cellNo, String data) throws IOException {
		fis = new FileInputStream(xlFile);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(xlSheet);
		row = sheet.getRow(rowNo);
		if (row == null) {
            row = sheet.createRow(rowNo); // If null, create the row
        }
		cell = row.getCell(cellNo);
		if (cell == null) {
            cell = row.createCell(cellNo); // If null, create the cell
        }
		cell.setCellValue(data);
		fos = new FileOutputStream(xlFile);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();
	}
	public static void deleteSheetAndCreateNewSheet(String xlFile, String xlSheet) throws IOException {
		fis = new FileInputStream(xlFile);
        workbook = new XSSFWorkbook(fis); // Load existing workbook
        fis.close(); // Close the stream immediately after loading

        // clear existing sheets
        int numberOfSheets = workbook.getNumberOfSheets();
        while(workbook.getNumberOfSheets() > 0) {
        	workbook.removeSheetAt(0);
        }

        // Create the new sheet
        sheet = workbook.createSheet(xlSheet);
        fos = new FileOutputStream(xlFile);
		workbook.write(fos);
		workbook.close();
		fos.close();
        System.out.println("******************* CREATED NEW SHEET *******************");
	}
	public static void setStyledCell(String xlFile, String xlSheet, int rowNo, int cellNo, String data) throws IOException {
	    fis = new FileInputStream(xlFile);
	    workbook = new XSSFWorkbook(fis);

	    sheet = workbook.getSheet(xlSheet);
	    if (sheet == null) {
	        sheet = workbook.createSheet(xlSheet);
	    }

	    row = sheet.getRow(rowNo);
	    if (row == null) {
	        row = sheet.createRow(rowNo);
	    }

	    cell = row.getCell(cellNo);
	    if (cell == null) {
	        cell = row.createCell(cellNo);
	    }

	    // Set cell value
	    cell.setCellValue(data);

	    // Create style
	    style = workbook.createCellStyle();
	    style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    // Set font color to white
	    org.apache.poi.ss.usermodel.Font font = workbook.createFont();
	    font.setColor(IndexedColors.WHITE.getIndex());
	    style.setFont(font);

	    cell.setCellStyle(style);

	    fos = new FileOutputStream(xlFile);
	    workbook.write(fos);
	    workbook.close();
	    fis.close();
	    fos.close();
	}
	public static String[][] returnData(String xlFile, String xlSheet) throws IOException{
		fis = new FileInputStream(xlFile);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(xlSheet);
		int noOfRows = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int noOfCells = row.getLastCellNum();
		DataFormatter formatter = new DataFormatter();
		String[][] data = new String[noOfRows][noOfCells];
		int currRow  = 0;
		for(int i=1;i<=noOfRows; i++) {
			row = sheet.getRow(i);
			int currCell=0;
			for(Cell cell:row) {
				data[currRow][currCell++] = formatter.formatCellValue(cell);
			}
			currRow++;
		}
		workbook.close();
		fis.close();
		return data;
	}
}
