package helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "testcase" + File.separator + "generated" + File.separator + "sample.xlsx";

    // zero based index, A1 would be row 0 col 0
    public static void writeResult(String sheetName, int rowIndex, int colIndex, int testResult) {
        try {
            File file = new File(FILE_PATH);
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("Sheet " + sheetName + " not found!");
                fis.close();
                return;
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }

            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                cell = row.createCell(colIndex);
            }

            cell.setCellValue(testResult);

            fis.close();
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Written value " + testResult + " at Sheet=" + sheetName +
                    " Row=" + rowIndex + " Col=" + colIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // use the cell reference coordinate directly
    public static void writeResult(String sheetName, String cellRef, int testResult) {
        System.out.println("Written value : " + testResult + " at Sheet=" + sheetName + " Col : " + cellRef);
//        try {
//            File file = new File(FILE_PATH);
//            FileInputStream fis = new FileInputStream(file);
//            Workbook workbook = new XSSFWorkbook(fis);
//            CellReference ref = new CellReference(cellRef);
//            int rowIndex = ref.getRow();
//            int colIndex = ref.getCol();
//
//            Sheet sheet = workbook.getSheet(sheetName);
//            if (sheet == null) {
//                System.out.println("Sheet " + sheetName + " not found!");
//                fis.close();
//                return;
//            }
//
//            Row row = sheet.getRow(rowIndex);
//            if (row == null) {
//                row = sheet.createRow(rowIndex);
//            }
//
//            Cell cell = row.getCell(colIndex);
//            if (cell == null) {
//                cell = row.createCell(colIndex);
//            }
//
//            cell.setCellValue(testResult);
//
//            fis.close();
//            FileOutputStream fos = new FileOutputStream(file);
//            workbook.write(fos);
//            fos.close();
//            workbook.close();
//
//            System.out.println("Written value " + testResult + " at Sheet=" + sheetName +
//                    " Row=" + rowIndex + " Col=" + colIndex);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
