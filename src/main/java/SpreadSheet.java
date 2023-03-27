import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadSheet {
    private Workbook workbook;

    SpreadSheet(){
        String fileName = null;
        fileName = URLDecoder.decode(Registration.class.getClassLoader().getResource("InputSheet.xlsx").getPath(), StandardCharsets.UTF_8);
        System.out.println(fileName);
        try (FileInputStream file = new FileInputStream(new File(fileName));
             Workbook workbook = new XSSFWorkbook(file)) {
            this.workbook = workbook;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> getIds(){
        String sheetName = "Sheet1";
        Sheet sheet = workbook.getSheet(sheetName);

        Row row = sheet.getRow(0);
        List<String> headers = IntStream.range(0, row.getLastCellNum())
                .mapToObj(i -> row.getCell(i).getStringCellValue())
                .collect(Collectors.toList());
        return headers;
    }

    public List<List<String>> getRows(){
        String sheetName = "Sheet1";
        List<List<String>> rows = StreamSupport.stream(workbook.getSheet(sheetName).spliterator(), false)
                .skip(1) // skip the first row
                .map(row -> StreamSupport.stream(row.spliterator(), false)
//                        .peek(cell -> System.out.println(cell.toString()))
                        .map(Cell::getStringCellValue)
                        .map(String::trim)
                        .collect(Collectors.toList()))
                .filter(row -> !row.stream().allMatch(String::isBlank))
                .collect(Collectors.toList());
        return rows;
    }
}
