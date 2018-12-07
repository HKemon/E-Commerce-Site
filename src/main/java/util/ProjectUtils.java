package util;

import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProjectUtils {
    // Used to hold the ip and user agent
    public static String[][] tempIpAgent = new String[1000][2];
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String yearMonthDate = dateFormat.format(new Date());
    public static String year = dateFormat.format(new Date()).split("-")[0];
    public static JSONParser parser = new JSONParser();
    // Set the minimum order to iterate that product
    public static int minimumOrder = 300;
    public static String excelFolder = "C:\\Users\\emon\\Desktop\\";
    // Check if the next page is needed to iterate;
    public static boolean accessNextPage = true;
    public static Object[][] data;

    // Used to hold all unvisited urls
    public static LinkedList<String> unVisited = new LinkedList<>();
    // Used to hold all visited urls
    public static LinkedList<String> visited = new LinkedList<>();

    public static StringBuilder tagId = new StringBuilder();

    // Return RawHtmlFile
    public static Document callJsoup(String url) {
        int randomNumber = setProxyIpAgent();  // Set Proxy
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent(ProjectUtils.tempIpAgent[randomNumber][1])
                    .header("Content-Language", "en-US")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    // Used to set Proxy for every request
    private static int setProxyIpAgent(){
        int randValueForIp = (int) (Math.random() * 1000 - 1);
        int randValueForAgent = (int) (Math.random() * 1000 - 1);
        System.out.println(ProjectUtils.tempIpAgent[randValueForIp][0] + " " + ProjectUtils.tempIpAgent[randValueForAgent][1]);

        System.setProperty("http.proxyHost", ProjectUtils.tempIpAgent[randValueForIp][0]);
        System.setProperty("http.proxyPort", String.valueOf((int) (Math.random() * 1000 + 1024)));
        return randValueForAgent;
    }

    // Split url for getting the page number of that url
    public static String splitURLForPage(String URL) {
        return URL.split(".html")[0];
    }

    public static Integer splitURLForPageNumber(String urls) {
        String[] url = urls.split(".html");
        return Character.getNumericValue(url[0].charAt(url[0].length() - 1));
    }

    public static String setTagId(Document document) {
        Elements elements = document.getElementsByClass("sn-parent-title");
        for (Element element1 : elements) {
            String s;
            if (element1.text().contains("<")) {
                s = element1.text().replace("<", "") + " > ";
            } else {
                s = element1.text() + " > ";
            }
            tagId.append(s);
        }
        String s = tagId.toString().trim();
        tagId = new StringBuilder();
        return s.substring(0, s.length() - 2);
    }

    // Add page number and extension to the url
    public static String getUrlEx(int i) {
        return "/" + i + ".html?isFavorite=y&SortType=total_tranpro_desc";
    }

    public static Object splitURLForProduct(String urls) {
        String[] url = urls.split(".html");
        return "https:" + url[0] + ".html";
    }

    // Print the 2d Array
    public static void print(Object[][] data) {
        for (Object[] aData : data) {
            for (Object anAData : aData) {
                System.out.print(anAData + " | ");
            }
            System.out.println();
        }
    }

    // Used to identify for filters and insert to arrayList
    public static ArrayList<ArrayList<String>> seasonGender(Document document) {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        for (Element ele : document.getElementsByClass("cate-title attr-less")) {
            if (ele.text().equals("Season")) {
                ArrayList seasonArrayList = new ArrayList<String>();
                Element parent = ele.parent();
                for (Element e : parent.getElementsByClass("common-select clearfix one-row")) {
                    for (Element e1: e.select("li")) {
                        System.out.println(e1.text());
                        seasonArrayList.add(e1.text());
                    }
                    break;
                }
                arrayList.add(seasonArrayList);
            } else if (ele.text().equals("Gender")) {
                ArrayList genderArrayList = new ArrayList<String>();
                Element parent = ele.parent();
                for (Element e : parent.getElementsByClass("common-select clearfix one-row")) {
                    for (Element e1: e.select("li")) {
                        System.out.println(e1.text());
                        genderArrayList.add(e1.text());
                    }
                    break;
                }
                arrayList.add(genderArrayList);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }
}