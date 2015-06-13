package pt.uc.dei.aor.paj;

import java.io.File;
import java.util.Date;

import pt.uc.dei.aor.paj.crawler.Crawler;
import pt.uc.dei.aor.paj.xml.JAXBHandler;
//import pt.uc.dei.aor.paj.data.Noticias;

public class RunWebCrawler {
	
	public static void main(String[] args) throws Exception {
		
		//chamar o parser -> dá origem ao XML
//		Crawler c = new Crawler();
//		Noticias noticias = c.doCrawler();

		//passa para XML
//		try {
//			JAXBHandler.marshal(noticias, new File ("output.xml"));
//			System.out.println("Marshall ok");//RETIRAR
//		} catch (IOException | JAXBException e) {
//			System.out.println(e.getMessage());
//		}	

		//enviar XML (como String) transformações?
		// cena quirino
		//passar xml ou a string como argumento
		Publisher p = new Publisher();
		p.publish();  // p.publish(stringXML);

		// para stats é igual
	}
}