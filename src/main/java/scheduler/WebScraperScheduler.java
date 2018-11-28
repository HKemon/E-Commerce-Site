package scheduler;

import classes.ArrayToEntityObjects;
import excel.ReadDataFromExcel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.ProjectUtils;

import java.io.IOException;

import static util.ProjectUtils.print;
import static util.ProjectUtils.data;

public class WebScraperScheduler implements Job {
    private ReadDataFromExcel readDataFromExcel = new ReadDataFromExcel();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // Populate Ip Address and Agent 2D array from excel file
        readDataFromExcel.readDataForIpAgent();
        // Read url from excel file and check it that page is eligible to
        // execute on certain conditions
        readDataFromExcel.readDataForDatabase();
    }

    public void webScraperAliexpress(String url) {


        Document document = ProjectUtils.callJsoup(url);
        String tagId = ProjectUtils.setTagId(document);
        int pageNum = ProjectUtils.splitURLForPageNumber(url);

        Elements picture = document.getElementsByClass("picRind ");
        Elements productId = document.getElementsByClass("atc-product-id");

//        Elements info = document.getElementsByClass("info");
//        Elements h = info.select("h3");
//        Elements productName = h.select("a");

        Elements productName = document.getElementsByClass("icon-hotproduct");
        Elements orderNumbers = document.getElementsByClass("order-num-a ");

        int pageNumber = ProjectUtils.splitURLForPageNumber(url);

        data = arraySize(orderNumbers);
        populateDataArray(picture, productId, productName, orderNumbers, pageNumber);
//        print(data);
        new ArrayToEntityObjects().iterateArray(tagId, url, pageNum);
    }

    private void populateDataArray(Elements picture,
                                   Elements productId, Elements productName,
                                   Elements orderNumbers, int pageNumber) {

        int i = 0;
        for (Element e : orderNumbers) {
            int x = Integer.parseInt(e.text().substring(e.text().indexOf("(") + 1, e.text().lastIndexOf(")")));
            if (x < ProjectUtils.minimumOrder) {
                break;
            }
            System.out.println(e.text());
            i++;
        }
        System.out.println(i);
        data = new Object[i][6];

        int rank = (pageNumber - 1) * data.length + 1;
        int index = 0;

        for (Element e : picture) {
            if (index < i) {
                data[index][0] = rank;
                data[index][1] = ProjectUtils.splitURLForProduct(e.attr("href"));
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : productId) {
            if (index < i) {
                data[index][2] = Long.parseLong(e.attr("value"));
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : productName) {
            if (index < i && !e.getElementsByClass("product ").isEmpty()) {
                data[index][3] = e.text();
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : picture.select("img")) {
            if (index < i) {
                if (!e.attr("src").equals("")) {
                    data[index][4] = e.attr("src");
                } else {
                    data[index][4] = e.attr("image-src");
                }
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : orderNumbers) {
            if (index < i) {
                String value = e.text();
                data[index][5] = Integer.parseInt(value.substring(value.indexOf("(") + 1, value.lastIndexOf(")")));
                index++;
                rank++;
            } else break;
        }
    }

    private Object[][] arraySize(Elements orderNumbers) {
        int i = 0;
        for (Element e : orderNumbers) {
            int x = Integer.parseInt(e.text().substring(e.text().indexOf("(") + 1, e.text().lastIndexOf(")")));
            if (x < ProjectUtils.minimumOrder) {
                break;
            }
            i++;
        }
        return new Object[i][6];
    }

    private void webScraperAmazon() throws IOException {
        Document document = Jsoup.connect("https://www.amazon.com/Best-Sellers-Electronics/zgbs/electronics/").get();

        Element el = document.body();

        Elements element1 = el.getElementsByClass("a-section a-spacing-small");
        Elements element2 = el.getElementsByClass("aok-inline-block zg-item");

        Object[][] data = new Object[50][3];

        int index = 0;

        for (Element e : element1.select("img")) {
            data[index][0] = e.attr("alt");
            data[index][1] = e.attr("src");
            index++;
        }

        index = 0;

        for (Element e : element2.select("a")) {
            if (!e.attr("href").substring(1, 15).equals("product-review")) {
                if (index % 2 == 0) {
                    String id = "";
                    String[] arr = e.attr("href").split("/");
                    id = arr[1] + "/" + arr[2] + "/" + arr[3];
                    data[index / 2][2] = "https://www.amazon.com/" + id;
                }
                index++;
            }
        }

        print(data);
    }
}