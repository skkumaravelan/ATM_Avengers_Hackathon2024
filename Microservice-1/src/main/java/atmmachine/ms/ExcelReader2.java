package atmmachine.ms;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class ExcelReader2 {
	public String readExcelAndConvertToJson2(String excelFilePath) {
	    List<ObjectNode> jsonDataList = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        // Iterate through each sheet in the workbook
	        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	            Sheet sheet = workbook.getSheetAt(i);

	            // Iterate through each row in the sheet
	            Iterator<Row> rowIterator = sheet.iterator();
	            while (rowIterator.hasNext()) {
	                Row row = rowIterator.next();

	                // Skip header row
	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                // Read LU Name (assuming it's in the first column)
	                Cell luNameCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	                String luName = getStringValue(luNameCell);

	                // Create JSON object for all columns including LU Name
	                ObjectNode jsonNode = new ObjectMapper().createObjectNode();
	                jsonNode.put("LU Name", luName); // Add LU Name field

	                // Iterate through selected cells in the row (adjust as per your needs)
	                int columnCount = Math.min(row.getLastCellNum(), 6); // Limit to 7 columns or row's last cell, whichever is less
	                for (int j = 1; j <= columnCount; j++) {
	                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	                    String columnName = sheet.getRow(0).getCell(j).getStringCellValue().trim();
	                    String cellValue = getStringValue(cell);
	                    jsonNode.put(columnName, cellValue);
	                }

	                // Add JSON object to the list
	                jsonDataList.add(jsonNode);
	            }
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Convert List<ObjectNode> to JSON array string
	    ObjectMapper mapper = new ObjectMapper();
	    try {
	        return mapper.writeValueAsString(jsonDataList);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	// Helper method to convert Cell value to String
	private static String getStringValue(Cell cell) {
	    if (cell == null) {
	        return "";
	    }

	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue().trim();
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                return cell.getDateCellValue().toString();
	            } else {
	                return Double.toString(cell.getNumericCellValue());
	            }
	        case BOOLEAN:
	            return Boolean.toString(cell.getBooleanCellValue());
	        case BLANK:
	            return "";
	        default:
	            return cell.toString().trim();
	    }
	}

}
