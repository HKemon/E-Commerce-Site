package classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import scheduler.WebScraperScheduler;
import util.GenderConstantEnum;
import util.ProjectUtils;
import util.SeasonConstantEnum;

import java.io.IOException;
import java.util.ArrayList;

import static util.ProjectUtils.*;

public class IterateLists {
    // Used to hold html raw file
    private Document document = null;

    // Extract Raw Html file by calling Jsoup function
    public void rawHtml(String url) throws IOException {
        document = ProjectUtils.callJsoup(url);
        categoryList(document.body());
    }

    // Find All Category Subcategory of that url and populate the list
    private void categoryList(Element element) throws IOException {
        Outer:
        for (Element element1 : element.getElementsByClass("category-list  ")) {
            for (Element element2 : element1.getElementsByClass("son-category")) {
                if (!element2.select("ul").text().equals("")) {
                    // Add all categories in the list
                    for (Element element3 : element2.select("ul")) {
                        for (Element e : element3.select("li")) {
                            String link = e.select("li").select("a").attr("href");
                            String url = "https://" + ProjectUtils.splitURLForPage(link.substring(2)) + ProjectUtils.getUrlEx(1);

                            // Add to the list if that page is new and unvisited
                            // Add page number with it also
                            // For fist page pass 1 to getUrlEx method

                            if (!visited.contains(url))                        // -----------------> Have to work on it
                                unVisited.add("https://" + ProjectUtils.splitURLForPage(link.substring(2)) + ProjectUtils.getUrlEx(1));
                            else System.out.println("This page is already visited");
                        }
                    }
                    break Outer;
                }
            }
        }
        // Wait for while so that website does not detect the unusual traffic
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if the list is empty
        // empty means visiting all urls is completed
        if (!unVisited.isEmpty())
            callURL();
        else System.out.println("Done");
    }

    // Access all data for specific URL
    private void callURL() throws IOException {
        String s = unVisited.poll();  // Take the first url and start operation
        document = Jsoup.connect(s).get();
        System.out.println(s);

        Outer:
        for (Element element1 : document.getElementsByClass("category-list  ")) {
            for (Element element2 : element1.getElementsByClass("son-category")) {  // Need To Change it DRY Principle
                if (element2.select("ul").text().equals("")) {
                    accessNextPage = true;
                    int i = 1;
                    String extension = "";
                    // Access Next page if the order of last product of that page is greater that ProjectUtils.minimumOrder
                    while (accessNextPage) {
                        if (s == null) {
                            System.out.println("Provided URL is null");
                            break Outer;
                        } else {
                            String url = splitURLForPage(s);
                            ArrayList<ArrayList<String>> seasonGender = seasonGender(document);
                            // Check if the filter option is null or not???
                            if (seasonGender != null) {
                                // if there is two filter
                                if (seasonGender.size() == 2) {
                                    for (int j = 0; j < seasonGender.get(0).size(); j++) {
                                        executeForFilterTwo(i, url, seasonGender.get(0).get(j), extension);
                                    }
                                } else {
                                    // if there is one filter
                                    for (int k = 0; k < seasonGender.get(0).size(); k++) {
                                        executeForFilterOne(i, url, seasonGender.get(0).get(k), extension);
                                    }
                                }
                            } else {
                                // if there is no filter
                                int x = orderCountForFirstProductOfPage(callJsoup(url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i)));
                                executeWebScraperAliexpressWithExtension(x, i, url, "");
                            }
                            // increment the page number to execute next page
                            i++;
                        }
                    }
                }
                // Find all subcategory of that page
                categoryList(document);
                break Outer;
            }
        }
    }

    // Return the order of first product of that page
    private int orderCountForFirstProductOfPage(Document document) {
        int x = 0;
        for (Element element1 : document.getElementsByClass("order-num-a ")) {
            String value = element1.text();
            x = Integer.parseInt(value.substring(value.indexOf("(") + 1, value.lastIndexOf(")")));
            break;
        }
        return x;
    }

    // Execute that page if the order of the first product is grater than minimumOrder
    // Otherwise do not need to execute that and next page
    private void executeWebScraperAliexpressWithExtension(int x, int i, String url, String extension) {
        if (x > minimumOrder) {
            System.out.println("ENTERED " + url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i) + extension);
            new WebScraperScheduler()
                    .webScraperAliexpress(url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i) + extension);
        } else {
            System.out.println("NOT ENTERED " + url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i) + extension);
            accessNextPage = false; // To stop executing the next page
        }
    }

    // Used to execute for one filter combination
    private void executeForFilterOne(int i, String url, String val, String extension) {
        System.out.println("test 2 " + val);
        switch (val) {
            case "Winter":
                extension = "&pvId=" + SeasonConstantEnum.Winter.getSeasonConstantCode();
                callEnum(i, url, "", extension, SeasonConstantEnum.Winter);
                break;
            case "Spring/Autumn":
                extension = "&pvId=" + SeasonConstantEnum.SpringAutumn.getSeasonConstantCode();
                callEnum(i, url, "", extension, SeasonConstantEnum.SpringAutumn);
                break;
            case "Summer":
                extension = "&pvId=" + SeasonConstantEnum.Summer.getSeasonConstantCode();
                callEnum(i, url, "", extension, SeasonConstantEnum.Summer);
                break;
            case "Men":
                extension = "&pvId=" + GenderConstantEnum.MenUnisex.getGenderConstantCode();
                callEnum(i, url, "", extension, GenderConstantEnum.MenUnisex);
                break;
            case "Women":
                extension = "&pvId=" + GenderConstantEnum.WomenUnisex.getGenderConstantCode();
                callEnum(i, url, "", extension, GenderConstantEnum.WomenUnisex);
                break;
            case "lovers'":
                extension = "&pvId=" + GenderConstantEnum.Lovers.getGenderConstantCode();
                callEnum(i, url, "", extension, GenderConstantEnum.Lovers);
                break;
            case "Girls":
                extension = "&pvId=" + GenderConstantEnum.Girls.getGenderConstantCode();
                callEnum(i, url, "", extension, GenderConstantEnum.Girls);
                break;
            case "Boys":
                extension = "&pvId=" + GenderConstantEnum.Boys.getGenderConstantCode();
                callEnum(i, url, "", extension, GenderConstantEnum.Boys);
                break;
            default:
                accessNextPage = false;  // Check this line
                break;
        }
    }

    // Used to execute for two filter combination
    private void executeForFilterTwo(int i, String url, String val, String extension) {
        switch (val) {
            case "Spring/Autumn":
                for (GenderConstantEnum genderConstantEnum : GenderConstantEnum.values()) {
                    extension = "&pvId=" + genderConstantEnum.getGenderConstantCode() + ";" + SeasonConstantEnum.SpringAutumn.getSeasonConstantCode();
                    callEnum(i, url, genderConstantEnum.toString(), extension, SeasonConstantEnum.SpringAutumn);
                }
                break;
            case "Summer":
                for (GenderConstantEnum genderConstantEnum : GenderConstantEnum.values()) {
                    extension = "&pvId=" + genderConstantEnum.getGenderConstantCode() + ";" + SeasonConstantEnum.Summer.getSeasonConstantCode();
                    callEnum(i, url, genderConstantEnum.toString(), extension, SeasonConstantEnum.Summer);
                }
                break;
            case "Winter":
                for (GenderConstantEnum genderConstantEnum : GenderConstantEnum.values()) {
                    extension = "&pvId=" + genderConstantEnum.getGenderConstantCode() + ";" + SeasonConstantEnum.Winter.getSeasonConstantCode();
                    callEnum(i, url, genderConstantEnum.toString(), extension, SeasonConstantEnum.Winter);
                }
                break;
        }
    }

    // Used to simplify and execute next page
    private void callEnum(int i, String url, String genderConstantEnum, String extension, Enum e) {
        System.out.println("extension " + ProjectUtils.getUrlEx(i) + extension);
        genderConstantEnum += " ";
        ProjectUtils.tagId = new StringBuilder("[" + genderConstantEnum + e.name() + "] ");
        System.out.println(url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i) + extension);
        int x = orderCountForFirstProductOfPage(callJsoup(url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i) + extension));
        executeWebScraperAliexpressWithExtension(x, i, url, extension);
    }
}