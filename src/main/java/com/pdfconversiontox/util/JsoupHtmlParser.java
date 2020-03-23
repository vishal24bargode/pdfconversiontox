package com.pdfconversiontox.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupHtmlParser {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc;
        try {

            // need http protocol
            //doc = Jsoup.connect("http://google.com").get();
        	doc = Jsoup.parse(new File("D:/vishal/WORKSPACE/AEMDEMO/pdfconversiontox/src/main/resources/static/PR2066C3E416D4252746E59EC9588CD281CCE7.html"),"utf-8");

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);
           
            // get all div with class=p
            Elements divElements = doc.select("div[class=p]");
            int pcount = 0;
            int breakingCount = 60;
            for (Element dElement : divElements) {
            	if(pcount < breakingCount) {
            		if(dElement.attr("id").equalsIgnoreCase("p"+pcount)) {
            			dElement.remove();
            		}
            		pcount++;
            	}else {
            		break;
            	}
            }
            
            // remove first 5 div with class=r
            Elements divElementsWithR =  doc.select("div[class=r]");
            pcount = 0;
            breakingCount = 5;
            for (Element dElement : divElementsWithR) {
            	if(pcount < breakingCount) {
            		dElement.remove();
            		pcount++;
            	}else {
            			break;
            	}
            }
         
            // remove first img element
            doc.select("img").first().attr("src", "rbi-header.jpg").attr("style", "width:595.44pt;overflow:hidden;");
            
            
            final File f = new File("D:/vishal/WORKSPACE/AEMDEMO/pdfconversiontox/src/main/resources/static/PR2066C3E416D4252746E59EC9588CD281CCE7.html");
            FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
            System.out.println("File : " + f.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
*/
}
