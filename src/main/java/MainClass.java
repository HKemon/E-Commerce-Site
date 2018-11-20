import excel.ReadDataFromExcel;
import scheduler.WebScraper;

public class MainClass {
    private static ReadDataFromExcel readDataFromExcel = new ReadDataFromExcel();

    public static void main(String[] args) {
//        readDataFromExcel.readDataForDatabase();
        new WebScraper().WebScraperRun();
    }
}