package classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import scheduler.WebScraperScheduler;
import util.ProjectUtils;

import java.io.IOException;
import java.util.LinkedList;

public class IterateLists {
    private LinkedList<String> queue = new LinkedList<>();
//    WebScraperScheduler webScraperScheduler = new WebScraperScheduler();

    public void iterateCategory(String url) throws IOException {
//        int randValueForIp = (int) (Math.random() * 1000 - 1);
//        int randValueForAgent = (int) (Math.random() * 1000 - 1);
//
//        System.setProperty("http.proxyHost", ProjectUtils.tempIpAgent[randValueForIp][0]);
//        System.setProperty("http.proxyPort", String.valueOf((int) (Math.random() * 1000 + 1024)));
//        String urlTemp = url;
        String urls = ProjectUtils.splitURLForPage(url) + ".html";
//        String urlEx = ProjectUtils.getUrlEx();
//
//        Document document = Jsoup.connect(urlFirst)
//                .userAgent(ProjectUtils.tempIpAgent[randValueForAgent][1])
//                .get();

        categoryList(ProjectUtils.callJsoup(urls).body());
    }

    private void categoryList(Element document) throws IOException {
        Outer:
        for (Element element1 : document.getElementsByClass("category-list  ")) {
            for (Element element2 : element1.getElementsByClass("son-category")) {
                if (!element2.select("ul").text().equals("")) {
                    for (Element element3 : element2.select("ul")) {
                        for (Element e : element3.select("li")) {
                            String link = e.select("li").select("a").attr("href");
                            queue.add("https://" + ProjectUtils.splitURLForPage(link.substring(2)) + ProjectUtils.getUrlEx());
                        }
                    }
                    break Outer;
                } else {
//                    System.out.println("Nothing");
                }
            }
        }
        if (!queue.isEmpty())
            call();
        else System.out.println("Done");
    }

    private void call() throws IOException {
        String s = queue.poll();
//        webScraperScheduler.webScraperAliexpress(s);
        System.out.println(s);
        Document document1 = Jsoup.connect(s)
                .get();
        categoryList(document1);
    }
}