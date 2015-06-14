package pt.uc.dei.aor.paj;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;

import pt.uc.dei.aor.paj.crawler.*;
import pt.uc.dei.aor.paj.xml.*;
import pt.uc.dei.aor.paj.data.*;

public class RunWebCrawler {

	public static void main(String[] args) throws Exception {

		Publisher p = new Publisher();
//		for (int i = 0; i < 10; i++) {
			//chamar o parser -> dá origem ao XML
			Crawler c = new Crawler();
			Noticias noticias = c.doCrawler();

			String filename = "output.xml";
			//passa para XML
			try {
				filename = outputNameFile();
				JAXBHandler.marshal(noticias, new File (filename));
				System.out.println("Marshall ok");//RETIRAR
			} catch (IOException | JAXBException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}

			//passar XML para String
			String stringXML = TransformXML.convertXMLFileToString(filename);

			System.out.println(stringXML);

			//passar xml ou a string como argumento
			p.publish(stringXML);
//		}

		// para stats é igual
	}
	
	public static String outputNameFile() {

		Calendar now = new GregorianCalendar();
		String filename = "output";
		filename += now.get(Calendar.YEAR);
		filename += now.get(Calendar.MONTH);
		filename += now.get(Calendar.DAY_OF_MONTH);
		filename += now.get(Calendar.HOUR_OF_DAY);
		filename += now.get(Calendar.MINUTE);
		filename += now.get(Calendar.SECOND);
		filename += ".xml";
		return filename;
		
	}
}