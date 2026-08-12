package helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    // use the cell reference coordinate directly
    public static void writeResult(String pathTestResult, String pathIssueLog, String sheetName, String cellResultRef, int testResult, String causeOfFailure) {
        System.out.println("Written value : " + testResult + " at Sheet=" + sheetName + " Col : " + cellResultRef);

        try {
            File file = new File(pathTestResult);
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            CellReference refResult = new CellReference(cellResultRef);
            int rowIndex = refResult.getRow();
            int colIndex = refResult.getCol();

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

            // If test result = 0, write cause of failure on the right cell of test result
            if(testResult == 0) {
                // init cell reference
                char nextColumn = (char) (cellResultRef.charAt(0) + 1);
                String cellCauseFailureRef = nextColumn + cellResultRef.substring(1);
                CellReference refCauseFailure = new CellReference(cellCauseFailureRef);
                int rowCauseFailureIndex = refCauseFailure.getRow();
                int colCauseFailureIndex = refCauseFailure.getCol();

                // write cause of failure
                Row row2 = sheet.getRow(rowCauseFailureIndex);
                if (row2 == null) {
                    row2 = sheet.createRow(rowCauseFailureIndex);
                }

                Cell cell2 = row2.getCell(colCauseFailureIndex);
                if (cell2 == null) {
                    cell2 = row2.createCell(colCauseFailureIndex);
                }

                cell2.setCellValue(causeOfFailure);


                // TODO: write to issue log
                File file2 = new File(pathIssueLog);
                FileInputStream fis2 = new FileInputStream(file2);
                Workbook workbook2 = new XSSFWorkbook(fis2);
                Sheet issueLogSheet = workbook2.getSheet("Issue LOG");

                if (issueLogSheet == null) {
                    System.out.println("Sheet Issue LOG not found!");
                } else {
                    int issueRowIndex = 1; // Skip header row

                    // Find the first row with an empty Issue Description column (B)
                    while (issueRowIndex <= issueLogSheet.getLastRowNum()) {
                        Row availableRow = issueLogSheet.getRow(issueRowIndex);
                        Cell issueDescriptionCell =
                                availableRow == null ? null : availableRow.getCell(1);

                        if (issueDescriptionCell == null || issueDescriptionCell.getCellType() == CellType.BLANK || issueDescriptionCell.toString().trim().isEmpty()) {
                            break;
                        }

                        issueRowIndex++;
                    }

                    Row issueRow = issueLogSheet.getRow(issueRowIndex);

                    if (issueRow == null) {
                        issueRow = issueLogSheet.createRow(issueRowIndex);
                    }

                    // B - Issue Description
                    issueRow.getCell(
                            1,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
                    ).setCellValue(causeOfFailure);

                    // G - Status
                    issueRow.getCell(
                            6,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
                    ).setCellValue("Open");

                    // H - Repetition
                    issueRow.getCell(
                            7,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
                    ).setCellValue(1);

                    // I - Criteria
                    issueRow.getCell(
                            8,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
                    ).setCellValue("Bug");

                    // M - Raised By
                    issueRow.getCell(
                            12,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
                    ).setCellValue("Ikky");

                    // N - Date Raised
                    issueRow.getCell(
                            13,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
                    ).setCellValue(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }

                fis2.close();
                FileOutputStream fos2 = new FileOutputStream(file2);
                workbook2.write(fos2);
                fos2.close();
                workbook2.close();

            }

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
}
