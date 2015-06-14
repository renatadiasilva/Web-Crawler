package pt.uc.dei.aor.paj;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import pt.uc.dei.aor.paj.crawler.*;
import pt.uc.dei.aor.paj.xml.*;
import pt.uc.dei.aor.paj.data.*;

public class RunWebCrawler {
	
	public static void main(String[] args) throws Exception {
		
		//chamar o parser -> dá origem ao XML
		Crawler c = new Crawler();
		Noticias noticias = c.doCrawler();

		//passa para XML
		try {
			JAXBHandler.marshal(noticias, new File ("output.xml"));
			System.out.println("Marshall ok");//RETIRAR
		} catch (IOException | JAXBException e) {
			System.out.println(e.getMessage());
		}
		
		//passar XML para String
		String xmlText = TransformXML.convertXMLFileToString("output.xml");
		
		System.out.println(xmlText);
		
		//passar xml ou a string como argumento
//		Publisher p = new Publisher();
//		p.publish();  // p.publish(stringXML);

		// para stats é igual
	}
}