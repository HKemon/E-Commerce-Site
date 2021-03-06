package excel;

import classes.IterateLists;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static util.ProjectUtils.excelFolder;
import static util.ProjectUtils.tempIpAgent;

public class ReadDataFromExcel {
    private IterateLists iterateLists = new IterateLists();

    // Used to Read URL from excel and pass it for further operation
    public void readDataForDatabase() {
        try (InputStream inputStream = new FileInputStream(excelFolder + "Aliexpress.xlsx")) {
            Workbook workbook = WorkbookFactory.create(inputStream);

//            long a = System.currentTimeMillis();
            int x = workbook.getNumberOfSheets();
            for (int in = 0; in < x; in++) {
                Sheet sheet = workbook.getSheetAt(in);
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int r = firstRow; r <= lastRow; r++) {
                    Row row = sheet.getRow(r);
                    int firstCellNumber = row.getFirstCellNum();
                    int lastCellNumber = row.getLastCellNum();
                    for (int c = firstCellNumber; c < lastCellNumber; c++) {
                        Cell cell = row.getCell(c);
                        if (r != 0 && c == 0) {
                            System.out.println(cell.toString());
                            // Find the all possible category from the specific url
                            iterateLists.rawHtml(cell.toString());
                        }
                    }
                }
            }
//            long b = System.currentTimeMillis();
//            System.out.println(b - a);
        } catch (IOException ex) {
            System.out.println("The file could not be read : " + ex.getMessage());
        } catch (EncryptedDocumentException ex) {
            System.out.println("The excel file is encrypted : " + ex.getMessage());
        }
    }

    // Used to populate the Ip Agent from Excel file to 2D Array
    public void readDataForIpAgent() {
        try (InputStream inputStream = new FileInputStream(excelFolder + "IpPort.xlsx")) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            int x = workbook.getNumberOfSheets();
            for (int in = 0; in < x; in++) {
                Sheet sheet = workbook.getSheetAt(in);
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int r = firstRow; r <= lastRow; r++) {
                    Row row = sheet.getRow(r);
                    int firstCellNumber = row.getFirstCellNum();
                    int lastCellNumber = row.getLastCellNum();

                    for (int c = firstCellNumber; c < lastCellNumber; c++) {
                        Cell cell = row.getCell(c);
                        cell.setCellType(CellType.STRING);
                        tempIpAgent[r][c] = cell.toString();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("The file could not be read : " + ex.getMessage());
        } catch (EncryptedDocumentException ex) {
            System.out.println("The excel file is encrypted : " + ex.getMessage());
        }
    }
}