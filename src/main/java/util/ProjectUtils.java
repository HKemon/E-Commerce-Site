package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ProjectUtils {
    public static String[][] tempIpAgent = new String[1000][2];

    public static Document callJsoup(String url) {
        int randValueForIp = (int) (Math.random() * 1000 - 1);
        int randValueForAgent = (int) (Math.random() * 1000 - 1);
        System.out.println(ProjectUtils.tempIpAgent[randValueForIp][0] + " " + ProjectUtils.tempIpAgent[randValueForAgent][1]);

        System.setProperty("http.proxyHost", ProjectUtils.tempIpAgent[randValueForIp][0]);
        System.setProperty("http.proxyPort", String.valueOf((int) (Math.random() * 1000 + 1024)));
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent(ProjectUtils.tempIpAgent[randValueForAgent][1])
                    .header("Content-Language", "en-US")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static String splitURLForPage(String URL) {
        return URL.split(".html")[0];
    }


    public static Integer splitURLForPageNumber(String urls) {
        String[] url = urls.split(".html");
        return Character.getNumericValue(url[0].charAt(url[0].length() - 1));
    }

    public static String getUrlEx() {
        return "/" + 1 + ".html?isFavorite=y&SortType=total_tranpro_desc";
    }

    public static Object splitURLForProduct(String urls) {
        String[] url = urls.split(".html");
        return "https:" + url[0] + ".html";
    }

    public static void print(Object[][] data) {
        for (Object[] aData : data) {
            for (Object anAData : aData) {
                System.out.print(anAData + " | ");
            }
            System.out.println();
        }
    }
}