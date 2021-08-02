package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {
	@SuppressWarnings("deprecation")
	public ArrayList<String> getData(String testCaseName) throws FileNotFoundException, IOException {
		ArrayList<String> a = new ArrayList<String>();
		XSSFWorkbook workbook = new XSSFWorkbook(
				new FileInputStream("/Users/Mrinmoy/Documents/Study_Materials/RestAssuredProject/Test.xlsx"));
		// System.out.println(workbook.getSheetAt(0).getRow(1).getCell(2).getStringCellValue());
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("testData")) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator(); // Sheet is collection of rows
				Row firstRow = rows.next();
				Iterator<Cell> ce = firstRow.cellIterator(); // Row is collection of cells
				int k = 0;
				int column = 0;
				while (ce.hasNext()) {
					Cell value = ce.next();
					if (value.getStringCellValue().equalsIgnoreCase("Testcases")) {
						column = k;
					}
					k++;
				}
				System.out.println(column);
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							Cell c = cv.next();
							if(c.getCellTypeEnum()==CellType.STRING)
							{
								a.add(c.getStringCellValue());
							}
							else
							{
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
					}
				}
			}
		}
		return a;
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {

	}
}
