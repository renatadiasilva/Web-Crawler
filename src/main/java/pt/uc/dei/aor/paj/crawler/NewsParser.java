package pt.uc.dei.aor.paj.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.paj.data.*;
import pt.uc.dei.aor.paj.xml.TransformXML;

public class NewsParser {

	private static final Logger log = LoggerFactory.getLogger(TransformXML.class);

	private boolean error;

	// Variaveis JSOUP
	private static Document document;

	// variaveis criacao noticia Jsoup
	private static String seccaoString = "body-text";
	private static String paragrafosString = "p.zn-body__paragraph";
	private static String tituloString = "h1.pg-headline";
	private static String highlightsString = "ul.el__storyhighlights__list";
	private static String dataString = "p.update-time";
	private static String autorString = "span.metadata__byline__author";
	private static String imgString = "img.media__image";

	// variaveis JAXB
	private static NoticiasType noticiasType;
	private static ArrayList<String> listaLinks = new ArrayList<String>();

	// faz o crawling
	public NoticiasType doCrawler() {
		error = false;
		noticiasType = new NoticiasType();
		error = getLinks();

		if (!error) {
			try {
				for (String s : listaLinks) {
					document = Jsoup.connect(s).get();
					NoticiaType n = constroiNoticia(s);
					noticiasType.getNoticia().add(n);
				}

				return noticiasType;
			} catch (Exception e) {
				error = true;
				log.error("Error: "+e.getMessage());
			}
		}
		return null;
	}

	// links
	private static boolean getLinks() {
		try {
			// obter documento html
			document = Jsoup.connect("http://edition.cnn.com/").get();
			Elements links = document.select("a");
			for (Element e : links) {
				if (!listaLinks.contains(e.attr("abs:href"))
						&& e.attr("abs:href").contains("/index.html")
						&& (e.attr("abs:href").contains("/us/")
								|| 
								e.attr("abs:href").contains("/americas/")
								|| e.attr("abs:href").contains("/china/")
								|| e.attr("abs:href").contains("/asia/")
								|| e.attr("abs:href").contains("/europe/")
								|| 
								e.attr("abs:href").contains("/middleeast/") || e
								.attr("abs:href").contains("/africa/"))) {
					listaLinks.add(e.attr("abs:href"));

				}
			}
			return true;
		} catch (Exception e) {
			log.error("Error: "+e.getMessage());
			return false;
		}
	}

	// constroi objeto Noticias
	private static NoticiaType constroiNoticia(String link) {
		// cria noticias
		NoticiaType n = new NoticiaType();
		// parse do link para obter informacao da noticia
		try {
			Document doc = Jsoup.connect(link).get();

			// ir buscar atributos da noticia
			Element seccao = doc.getElementById(seccaoString);
			Elements textStrings = seccao.select(paragrafosString);
			//url
			if (link.isEmpty()) link = "No link.";
			n.setUrl(link);
			// titulo
			String titulo = doc.select(tituloString).first().text();
			if (titulo.isEmpty()) titulo = "No title.";
			n.setTitulo(titulo);
			// constroi texto da noticia
			String textoNoticia = "";
			for (Element e1 : textStrings) {
				if (!e1.ownText().isEmpty())
					textoNoticia += e1.text();
			}
			if (textoNoticia.isEmpty()) textoNoticia = "No text.";
			n.setTexto(textoNoticia);
			// data e autor
			Element metadata = doc.select("div.metadata").first();
			String data = metadata.select(dataString).text();

			XMLGregorianCalendar datafinal = stringToXMLGregorianCalendar(stringDateConvert(data));

			String autor = metadata.select(autorString).text();
			if (autor.isEmpty()) autor = "No author.";
			n.setAutor(autor);
			n.setData(datafinal);
			// set local
			if (link.contains("us")) {
				n.setLocal("us");
			} else if (link.contains("/europe/")) {
				n.setLocal("europe");
			} else if (link.contains("/asia/")) {
				n.setLocal("asia");
			} else if (link.contains("/china/")) {
				n.setLocal("china");
			} else if (link.contains("/middleeast/")) {
				n.setLocal("middleeast");
			} else if (link.contains("/africa/")) {
				n.setLocal("africa");
			} else if (link.contains("/americas/")) {
				n.setLocal("americas");
			}
			// url photo ou video
			Element media = doc.getElementById("large-media");
			if (media != null) {
				Element imgUrlElement = media.select(imgString).first();
				// ver se tem imagem grande
				String imgUrl;
				if (imgUrlElement != null) {
					if (imgUrlElement.hasAttr("data-src-large")) {
						imgUrl = imgUrlElement.attr("data-src-large");
					} else {
						imgUrl = imgUrlElement.attr("src");
					}
					n.setMediaurl(imgUrl);
				}
			}

			// highlights
			Element highlights = doc.select(highlightsString).first();
			if (highlights != null) {
				Elements highlightList = highlights.select("li");
				ArrayList<String> high = new ArrayList<String>();
				for (Element e2 : highlightList) {
					high.add(e2.text());
				}
				n.setHighlights(high);
			}

		} catch (Exception e2) {
			log.error("Error: "+e2.getMessage());
		}
		return n;
	}

	//set date
	private static XMLGregorianCalendar stringToXMLGregorianCalendar(String s) throws ParseException, DatatypeConfigurationException{
		XMLGregorianCalendar result = null;
		Date date;
		SimpleDateFormat simpleDateFormat;
		GregorianCalendar gregorianCalendar;

		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		date = simpleDateFormat.parse(s);        
		gregorianCalendar = 
				(GregorianCalendar)GregorianCalendar.getInstance();
		gregorianCalendar.setTime(date);
		result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		return result;
	}


	// date format CNN: Updated 1657 GMT (2357 HKT) June 12, 2015
	private static String stringDateConvert(String date) {

		//divide a string pelos espacos em branco
		String [] datas = date.split("\\s+");
		String month = "";
		if(datas[5].equals("January"))month="01";
		if(datas[5].equals("February"))month="02";
		if(datas[5].equals("March"))month="03";
		if(datas[5].equals("April"))month="04";
		if(datas[5].equals("May"))month="05";
		if(datas[5].equals("June"))month="06";
		if(datas[5].equals("July"))month="07";
		if(datas[5].equals("August"))month="08";
		if(datas[5].equals("September"))month="09";
		if(datas[5].equals("October"))month="10";
		if(datas[5].equals("November"))month="11";
		if(datas[5].equals("December"))month="12";

		String datafinal = datas[7]+"-"+month+"-"+datas[6].substring(0,datas[6].length()-1)+"T"+
				datas[1].substring(0, 2)+":"+datas[1].substring(2)+":00";

		return datafinal;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}