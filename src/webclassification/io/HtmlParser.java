/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.io;

import java.io.IOException;
import java.nio.charset.Charset;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Duc Phan
 */
public class HtmlParser {

    public String getHtml(String url) {
        @SuppressWarnings("UnusedAssignment")
        String html = "";
        HttpGet request = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity, Charset.forName("utf-8"));
            if (html.contains("dantri.com.vn")) {
                html = parseHtml_Dantri(html);
            } else if (html.contains("news.zing.vn")) {
                html = parseHtml_ZingNews(html);
            } else if (html.contains("24h.com.vn")) {
                html = parseHtml_24h(html);
            } else if (html.contains("vnexpress.net")) {
                html = parseHtml_VnExpress(html);
            } else if (html.contains("vietnamnet.vn")) {
                html = parseHtml_Vietnamnet(html);
            } else if (html.contains("baomoi.com")) {
                html = parseHtml_BaoMoi(html);
            } else {
                html = "";
            }
        } catch (IOException ex) {
//            Logger.getLogger(HtmlParser.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Url sai!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return  null;
        }
        return html;
    }

    public String parseHtml_Dantri(String html) {
        String content = "";
        Document doc = Jsoup.parse(html);
        Element newsdiv = doc.select("#ctl00_IDContent_ctl00_divContent").first();
        Elements newstags = newsdiv.select("p:not([style])");
        for (Element e : newstags) {
            if (!e.hasClass("detail_subtitle")) {
                content += e.text();
                content += '\n';
            }
        }
        //System.out.println(newstag.text());
        return content;
    }

    public String parseHtml_ZingNews(String html) {
        String content = "";
        Document doc = Jsoup.parse(html);
        Elements newsdiv = doc.select(".the-article-body > p");
        if (newsdiv == null) //check if <p> tag not found or ".the-article-body" not found
        {
            return null;
        }
        for (Element e : newsdiv) {
            content += e.text();
            content += '\n';
        }
        return content;
    }

    public String parseHtml_24h(String html) {
        String content = "";
        Document doc = Jsoup.parse(html);
        Elements newsdiv = doc.select(".text-conent > p");
        if (newsdiv == null) {
            return null; //if <p> tags not found or "text-conent" class not found
        }
        for (Element e : newsdiv) {
            content += e.text();
            content += '\n';
        }
        return content;
    }

    public String parseHtml_VnExpress(String html) {
        String content = "";
        Document doc = Jsoup.parse(html);
        Elements newsdiv = doc.select("#left_calculator");
        if (newsdiv == null) //#left_calculator not found
        {
            return null;
        }
        Element div = newsdiv.first();
        if (div == null) {
            return null;
        }
        Elements newstags = div.select("p:not([style])");
        for (Element e : newstags) {
            if (e.hasClass("Image")) {
                continue;
            }
            content += e.text();
            System.out.println(e.text());
            content += '\n';
        }
        return content;
    }

    public String parseHtml_Vietnamnet(String html) {
        String result = "";
        Document doc = Jsoup.parse(html);
        Element newsdiv = doc.select("#ArticleContent").first();
        Elements newstags = newsdiv.select("p");
        for (Element e : newstags) {
            if (e.html().contains("<strong>")) {
                continue;
            }
            String temp = e.text();
            if (temp.length() == 0) {
                continue;
            }
            result += temp;
            result += '\n';
        }
        return result;
    }

    public String parseHtml_BaoMoi(String html) {
        String result = "";
        Document doc = Jsoup.parse(html);
        Elements temp = doc.select(".body");
        if (temp == null) {
            return null;
        }
        Element newsdiv = temp.first();
        Elements newstags = newsdiv.select("p");
        int size = newstags.size();
        int count = 0;
        for (Element e : newstags) {
            if (count == size - 1) {
                break;
            }
            String tempString = e.text();
            result += tempString;
            result += '\n';
            count++;
        }
        return result;
    }
}
