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

public class WebScraperScheduler implements Job {

    private ReadDataFromExcel readDataFromExcel = new ReadDataFromExcel();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        readDataFromExcel.readDataForIpAgent();
        readDataFromExcel.readDataForDatabase();
    }

    public void webScraperAliexpress(String tagId, int pageNum, String url) {

        Element e1 = ProjectUtils.callJsoup(url).body();
        Elements element1 = e1.getElementsByClass("picRind ");
        Elements element2 = e1.getElementsByClass("atc-product-id");
        Elements productName = e1.getElementsByClass("icon-hotproduct");
        Elements orderNumbers = e1.getElementsByClass("order-num-a ");

        Object[][] data = new Object[48][6];
        int pageNumber = ProjectUtils.splitURLForPageNumber(url);

        int rank = (pageNumber - 1) * data.length + 1;
        int index = 0;

        for (Element e : element1) {
            data[index][0] = rank;
            data[index][1] = ProjectUtils.splitURLForProduct(e.attr("href"));
            index++;
            rank++;
        }

        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : element2) {
            data[index][2] = Long.parseLong(e.attr("value"));
            index++;
            rank++;
        }

        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : productName.select("a")) {
            data[index][3] = e.text();
            index++;
            rank++;
        }

        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : element1.select("img")) {
            if (!e.attr("src").equals("")) {
                data[index][4] = e.attr("src");
            } else {
                data[index][4] = e.attr("image-src");
            }
            index++;
            rank++;
        }

        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : orderNumbers) {
            String value = e.text();
            data[index][5] = Integer.parseInt(value.substring(value.indexOf("(") + 1, value.lastIndexOf(")")));
            index++;
            rank++;
        }

        new ArrayToEntityObjects().iterateArray(data, tagId, pageNum);
    }



//    public Document callJsoup(String url) {
//        int randValueForIp = (int) (Math.random() * 1000 - 1);
//        int randValueForAgent = (int) (Math.random() * 1000 - 1);
//        System.out.println(ProjectUtils.tempIpAgent[randValueForIp][0] + " " + ProjectUtils.tempIpAgent[randValueForAgent][1]);
//
//        System.setProperty("http.proxyHost", ProjectUtils.tempIpAgent[randValueForIp][0]);
//        System.setProperty("http.proxyPort", String.valueOf((int) (Math.random() * 1000 + 1024)));
//        Document document = null;
//        try {
//            document = Jsoup.connect(ProjectUtils.URLS)
//                    .userAgent(ProjectUtils.tempIpAgent[randValueForAgent][1])
//                    .header("Content-Language", "en-US")
//                    .get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return document;
//    }


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

        ProjectUtils.print(data);
    }
}