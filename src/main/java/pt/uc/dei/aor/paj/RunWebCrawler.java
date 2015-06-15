package pt.uc.dei.aor.paj;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;

import pt.uc.dei.aor.paj.crawler.*;
import pt.uc.dei.aor.paj.xml.*;
import pt.uc.dei.aor.paj.data.*;

public class RunWebCrawler {

	public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Boolean stop = false;
//		for (int i = 0; i < 10; i++) {
		while (!stop) {
			//chamar o parser -> dá origem ao XML
			Publisher p = new Publisher();
			NewsParser c = new NewsParser();
			NoticiasType noticias = c.doCrawler();

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
			System.out.println("XML transformado em String.");
			
//			System.out.println(stringXML);

			//passar xml ou a string como argumento
			p.publish(stringXML);
			System.out.println("Mensagem Enviada.");
			
			System.out.print("1- continua; 2 - termina? ");
			String answer = reader.readLine();
			
			if (answer.equals("2")) {
				// terminar subscribers
				p.publish("stop");
			
				// terminar publisher				
				System.out.println("Exiting...");
				reader.close();
				System.out.println("Goodbye!");
				stop = true;
			}
		}

		// para stats é igual
	}
	
	public static String outputNameFile() {

		Calendar now = new GregorianCalendar();
		String filename = "..\\src\\main\\resources\\output";
		filename += "_"+now.get(Calendar.YEAR);
		int mes = now.get(Calendar.MONTH)+1;
		filename += "_"+mes+"";
		filename += "_"+now.get(Calendar.DAY_OF_MONTH);
		filename += "_"+now.get(Calendar.HOUR_OF_DAY);
		filename += "_"+now.get(Calendar.MINUTE);
		filename += "_"+now.get(Calendar.SECOND);
		filename += ".xml";
		System.out.println("NOME FICHEIRO: "+filename);
		return filename;
		
	}
}