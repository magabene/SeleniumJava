package genericMethods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {
	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell xCell;

	private static XSSFRow xRow;

	// This method is to set the File path and to open the Excel file, Pass Excel
	// Path and Sheetname as Arguments to this method

	public void setExcelFile(String Path, String SheetName) throws Exception {

		try {

			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e) {

			throw (e);

		}

	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num

	public String getCellData(int RowNum, int ColNum) throws Exception {

		try {

			xCell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			String CellData = xCell.getStringCellValue();

			return CellData;

		} catch (Exception e) {

			try {

				int CellNumData = (int)xCell.getNumericCellValue();

				return Integer.toString(CellNumData);

			} catch (Exception ex) {
				try {

					boolean CellBoolData = xCell.getBooleanCellValue();

					return Boolean.toString(CellBoolData);

				} catch (Exception exe) {
					try {
                        if(xCell.getCellType() == CellType.BLANK) {
                            return "";
                        }else {
                            throw new Exception("Unable to get cell value");
                        }
                    }
                    catch (NullPointerException exce){
                        return "";
                    }catch (Exception exce){
                        throw new Exception("Unable to get cell value");
                    }
					
				}

			}

		}

	}

	// This method is to write in the Excel cell, Row num and Col num are the
	// parameters

	public void setCellData(String Path, String Result, int RowNum, int ColNum) throws Exception {

		try {

			xRow = ExcelWSheet.getRow(RowNum);

			xCell = xRow.getCell(ColNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

			if (xCell == null) {

				xCell = xRow.createCell(ColNum);

				xCell.setCellValue(Result);

			} else {

				xCell.setCellValue(Result);

			}

			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(Path);

			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception e) {

			throw (e);

		}

	}

	public int getRowNumbers() {
		return ExcelWSheet.getPhysicalNumberOfRows();
	}

	public int getColumnNumbers() {
		return ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
	}

	public String internallyGetValue(String value, int index) throws Exception {
		xRow = ExcelWSheet.getRow(0);

		int valueColumn = -1;
		String cellValue = "cell Not Found";
		for (int cn = 0; cn < xRow.getLastCellNum(); cn++) {
			xCell = xRow.getCell(cn);
			if (xCell == null) {
				// Can't be this cell - it's empty
				continue;
			}
			if (xCell.getCellType() == CellType.STRING) {
				String text = xCell.getStringCellValue();
				if (value.equals(text)) {
					valueColumn = cn;
					cellValue = getCellData(index, cn);
					break;
				}
			}
		}
		if (valueColumn == -1 || cellValue.equals("cell Not Found")) {
			throw new Exception("Unable to find column of value" + value);
		}
		return cellValue;
	}

	public Object[][] getTableArray(String FilePath, String SheetName) throws Exception {

		String[][] tabArray = null;

		try {

			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startRow = 1;

			int startCol = 0;

			int ci, cj;

			int totalRows = ExcelWSheet.getLastRowNum();

			int totalCols = getColumnNumbers();

			tabArray = new String[totalRows][totalCols];

			ci = 0;

			for (int i = startRow; i <= totalRows; i++, ci++) {

				cj = 0;

				for (int j = startCol; j <= totalCols - 1; j++, cj++) {

					tabArray[ci][cj] = getCellData(i, j);

					System.out.println(tabArray[ci][cj]);

				}

			}

		}

		catch (FileNotFoundException e) {

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e) {

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return (tabArray);

	}

	/**
	 * Returns the URL for the actual Excel File in src/test/resources
	 * @param Excel file or data file location
	 * @author Justice Mohuba
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("rawtypes")
	public String getDataFileUrl(String location){

		URL url = null;
		try
		{
			Class cls = Class.forName("genericMethods.ExcelUtilities");

			// returns the ClassLoader object associated with this Class
			ClassLoader cLoader = cls.getClassLoader();

			System.out.println(cLoader.getClass());

			// finds resource with the given name
			url = cLoader.getResource(location);
			System.out.println("URL Value = " + url);

		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return url.toString();
	}


}
