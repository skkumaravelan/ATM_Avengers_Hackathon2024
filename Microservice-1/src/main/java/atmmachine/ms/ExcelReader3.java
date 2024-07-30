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
public class ExcelReader3 {
    public String readExcelAndConvertToJson3(String excelFilePath) {
        List<ObjectNode> jsonDataList = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Iterate through each sheet in the workbook
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

                // Ensure there are at least two rows: headers and data
                if (sheet.getPhysicalNumberOfRows() < 2) {
                    continue; // Skip if no data
                }

                // Read header row
                Row headerRow = sheet.getRow(0);
                Map<Integer, String> headerMap = new HashMap<>();
                for (Cell cell : headerRow) {
                    headerMap.put(cell.getColumnIndex(), getStringValue(cell));
                }

                // Iterate through each row in the sheet
                Iterator<Row> rowIterator = sheet.iterator();
                rowIterator.next(); // Skip header row

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    // Create JSON object for the row
                    ObjectNode jsonNode = new ObjectMapper().createObjectNode();

                    for (Map.Entry<Integer, String> entry : headerMap.entrySet()) {
                        Integer columnIndex = entry.getKey();
                        String columnName = entry.getValue();
                        Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
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
