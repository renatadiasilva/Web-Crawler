package pt.uc.dei.aor.paj.crawler;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pt.uc.dei.aor.paj.data.*;

public class NewsParser {

	// Variaveis JSOUP
	private static Document document;
	//	private static String maindiv = "div.column";
	//	private static String artigo = "article";
	//	private static String headlineNoticia = "h3.cd__headline";

	// variaveis criacao noticia Jsoup
	private static String seccaoString = "body-text";
	private static String paragrafosString = "p.zn-body__paragraph";
	private static String tituloString = "h1.pg-headline";
	private static String highlightsString = "ul.el__storyhighlights__list";
	private static String dataString = "p.update-time";
	private static String autorString = "span.metadata__byline__author";
	private static String imgString = "img.media__image";

	// variaveis JAXB
	private static JAXBContext jaxbcontext;
	//	private JAXBElement<Object> objectWrapper;
	private static String generatedXml;
	private static NoticiasType noticiasType;
	private static ArrayList<String> listaLinks = new ArrayList<String>();

	public NoticiasType doCrawler() {
		noticiasType = new NoticiasType();
		getLinks();
//		for (String s : listaLinks)
//			System.out.println(s);

		try {
			for (String s : listaLinks) {
				document = Jsoup.connect(s).get();
				NoticiaType n = constroiNoticia(s);
				noticiasType.getNoticia().add(n);
			}
			// escrever XML
			System.out.println(noticiasType);
			return noticiasType;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(generatedXml);
		return null;
	}

	private static void getLinks() {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeXml(NoticiasType noticiasType) {
		StringWriter sw = new StringWriter();
		try {
			jaxbcontext = JAXBContext.newInstance(ObjectFactory.class);
			// fazer marshall das noticias
			Marshaller criaNoticias = jaxbcontext.createMarshaller();
			// propriedades do marshaller
			criaNoticias.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			criaNoticias.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			JAXBElement<NoticiasType> elemento = new ObjectFactory()
			.createNoticias(noticiasType);
			criaNoticias.marshal(elemento, sw);
			// colocar xml em string na variavel
			generatedXml = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	private static NoticiaType constroiNoticia(String link) {
		// cria noticias
		NoticiaType n = new NoticiaType();
		// parse do link para obter informacao da noticia
		try {
			Document doc = Jsoup.connect(link).get();

			// ir buscar atributos da noticia
			Element seccao = doc.getElementById(seccaoString);
			Elements textStrings = seccao.select(paragrafosString);
			// titulo
			String titulo = doc.select(tituloString).first().text();
			n.setTitulo(titulo);
			// constroi texto da noticia
			String textoNoticia = "";
			for (Element e1 : textStrings) {
				if (!e1.ownText().isEmpty())
					textoNoticia += e1.text();
			}
			n.setTexto(textoNoticia);
			// data e autor
			Element metadata = doc.select("div.metadata").first();
			String data = metadata.select(dataString).text();
			
			XMLGregorianCalendar datafinal = stringToXMLGregorianCalendar(stringDateConvert(data));
			
			String autor = metadata.select(autorString).text();
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
				n.setLocal("middle-east");
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
				if (imgUrlElement.hasAttr("data-src-large")) {
					imgUrl = imgUrlElement.attr("data-src-large");
				} else {
					imgUrl = imgUrlElement.attr("src");
				}
				n.setMediaurl(imgUrl);
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
			e2.printStackTrace();
		}
		return n;
	}
	
	/////////////////////////////////////////////////////////
	
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
	
	private static String stringDateConvert(String date){
		
//		Updated 1657 GMT (2357 HKT) June 12, 2015
		String [] datas = date.split("\\s+");    //divide a string pelos espa�os em branco
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
		
		String datafinal = datas[7]+"-"+month+"-"+datas[6].substring(0,datas[6].length()-1)+"T"+datas[1].substring(0, 2)+":"+datas[1].substring(2)+":00";
		
		return datafinal;
	}
}