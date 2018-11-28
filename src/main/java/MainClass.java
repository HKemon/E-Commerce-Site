import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scheduler.WebScraper;
import util.ProjectUtils;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) {
        new WebScraper().WebScraperRun();
//        try {
//            Document document = Jsoup.connect("https://www.aliexpress.com/category/100001606/women-shoes.html?minPrice=&maxPrice=&isBigSale=n&isShoppingCard=n&isFreeShip=n&isNew=n&isFavorite=y&shipFromCountry=&shipCompanies=&SearchText=&CatId=100001606&g=y&SortType=total_tranpro_desc&needQuery=n")
//                    .get();
//
//            Elements info = document.getElementsByClass("info");
//            Elements h = info.select("h3");
//            Elements a = h.select("a");
//            System.out.println(a.size());
//
//            Elements orderNumbers = document.getElementsByClass("order-num-a ");
//
//            int i = 0 ;
//            for (Element e : orderNumbers) {
//                int x =  Integer.parseInt(e.text().substring(e.text().indexOf("(") + 1, e.text().lastIndexOf(")")));
//                if (x < ProjectUtils.minimumOrder) {
//                    break;
//                }
//                System.out.println(e.text());
//                i++;
//            }
//            System.out.println(i);
////            System.out.println(info);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}