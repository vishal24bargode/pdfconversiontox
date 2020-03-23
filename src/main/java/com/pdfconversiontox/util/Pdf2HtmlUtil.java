package com.pdfconversiontox.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.pdfconversiontox.dto.HtmlFile;

@Repository
public class Pdf2HtmlUtil {
	
	@Autowired
	private Environment env;

	public Boolean generateHTMLFromPDF(String pdfPath) throws ParserConfigurationException, IOException {
		String pdfPathSplit[] = pdfPath.split("/");
		String htmlFileName = pdfPathSplit[pdfPathSplit.length-1];
		
		if(htmlFileName.endsWith(".pdf") || htmlFileName.endsWith(".PDF")) {
			htmlFileName = htmlFileName.replace(".pdf", ".html");
			htmlFileName = htmlFileName.replace(".PDF", ".html");
		}
		
		
		String htmlOutputDir = "src/main/resources/static/";
		
		if(!env.getProperty("app.pdfconversiontox.html.output.dir").isEmpty())
		{
			htmlOutputDir = this.getApplicationPath() + env.getProperty("app.pdfconversiontox.html.output.dir");
		}
		
		htmlFileName = htmlOutputDir+htmlFileName;
				
		PDDocument pdf = PDDocument.load(new File(pdfPath));
		//PDFDomTree parser = new PDFDomTree();
		CustomPDFDomTree customParser = new CustomPDFDomTree();
		try {
			File htmlFile = new File(htmlFileName);
			Path htmlPath = Paths.get(htmlFile.toURI());
			Writer output = new PrintWriter(new File(htmlPath.toFile().toURI()), "utf-8");
			customParser.writeText(pdf, output);

			output.close();
			pdf.close();
		}catch(Exception e){
			System.out.println("Error:\n"+e.getMessage());
			e.printStackTrace();
		}
		
		this.modifyGeneratedHTML(htmlFileName);
		
		return true;		
	}
	
	public void modifyGeneratedHTML(String htmlPath) {
		
		org.jsoup.nodes.Document doc;
        try {
        	doc = Jsoup.parse(new File(htmlPath),"utf-8");           
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
            
            
            final File f = new File(htmlPath);
            FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	public void generatePDFFromHTML(String filename) throws ParserConfigurationException, IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/output/html.pdf"));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
		document.close();
	}
	
	public Set<HtmlFile> listFilesUsingDirectoryStream(Path htmlPath) throws IOException {
		String htmlOutputDir = "src/main/resources/static/";
		Set<HtmlFile> fileList = new HashSet<HtmlFile>();
		if (!env.getProperty("app.pdfconversiontox.html.output.dir").isEmpty()) {
			htmlOutputDir = this.getApplicationPath() + env.getProperty("app.pdfconversiontox.html.output.dir");		
		}
		

		//Path filePath = Paths.get(new File(htmlPath).toURI()).toAbsolutePath().normalize();
        Resource resource = new UrlResource(htmlPath.toUri());
		
		for (File file : resource.getFile().listFiles()) {
			if (!file.isDirectory()) {
				HtmlFile htmlFile = new HtmlFile();
				htmlFile.setFileName(file.getName());
				htmlFile.setFileHref(env.getProperty("app.pdfconversiontox.html.output.dir")+file.getName());
				fileList.add(htmlFile);
			}
		}
		return fileList;
	}
	
	public String getApplicationPath() throws java.io.UnsupportedEncodingException {

		String path = this.getClass().getClassLoader().getResource("").getPath();

		String fullPath = URLDecoder.decode(path, "UTF-8");

		String pathArr[] = fullPath.split("/WEB-INF/classes/");

		fullPath = pathArr[0];

		return fullPath;

	}
}

