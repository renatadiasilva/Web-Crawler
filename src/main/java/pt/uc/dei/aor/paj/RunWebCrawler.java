package pt.uc.dei.aor.paj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import pt.uc.dei.aor.paj.crawler.*;
import pt.uc.dei.aor.paj.xml.*;
import pt.uc.dei.aor.paj.data.*;

public class RunWebCrawler {

	public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Boolean stop = false;

		Publisher p = new Publisher();

		while (!stop) {
			
			//verifica se há ficheiros a enviar na diretoria log-files
			List<File> filesInFolder = Files.walk(Paths.get("../src/main/resources/log-files"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
			
			for (File f : filesInFolder) {
				String filePathDir = "..\\src\\main\\resources\\log-files\\"+f.getName();
				String stringXML = TransformXML.convertXMLFileToString(filePathDir);
				System.out.println("XML transformado em String.");
				p.publish(stringXML);
				if (!p.isError()) {
					System.out.println("XML enviado.");
					if (f.delete()) System.out.println("Ficheiro log apagado: "+f.getName());
					else System.out.println("Erro ao apagar log.");
				} else {
					System.out.println("Ficheiro log não enviado.");
					System.out.println(f.getName()+" mantém-se na diretoria log-files.");
				}
			}

	
			//apaga ficheiros finalmente enviados
			
			//chamar o parser -> dá origem ao XML
//			NewsParser c = new NewsParser();
//			System.out.println("Começando o parser.");
//			NoticiasType noticias = c.doCrawler();
//			System.out.println("Parser feito.");
//
//			String filename = "output.xml";
//			//passa para XML
//			try {
//				filename = outputNameFile("sent-files", "output", "xml");
//				JAXBHandler.marshal(noticias, new File (filename));
//				System.out.println("Marshall feito.");
//			} catch (IOException | JAXBException e) {
//				e.printStackTrace();
//				System.exit(1);
//			}

			//passar XML para String
//			String stringXML = TransformXML.convertXMLFileToString(filename);
//			System.out.println("XML transformado em String.");
			
			String stringXML = TransformXML.convertXMLFileToString("..\\src\\main\\resources\\sent-files\\output.xml");
			//passar string para o topic
			p = new Publisher();
			p.publish(stringXML);
			
			//repete durante 10 segundos tentativa de publicar no topic se falhar
			int times = 0;
			while ((p.isError()) && (times < 9)) {
				System.out.println("XML Não Enviado.");
				Thread.sleep(1000);
				p.setError(false);
				System.out.println("Tentando enviar de novo o XML (Tentativa "+(times+2)+").");
				p.publish(stringXML);
				times++;
			}
			
			//guardar info não enviada num ficheiro
			if (p.isError()) {
				System.out.println("XML Não Enviado (10 Tentativas esgotadas).");
				String filePathLog = outputNameFile("log-files", "log", "xml");
				TransformXML.convertStringToXMLFile(stringXML, filePathLog);
				System.out.println("Info não enviada guardada no ficheiro "+filePathLog);
				
			} else System.out.println("XML Enviado.");

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

	}
	
	public static String outputNameFile(String dir, String name, String ext) {

		Calendar now = new GregorianCalendar();
		String filename = "..\\src\\main\\resources\\"+dir+"\\"+name;
		filename += "_"+now.get(Calendar.YEAR);
		int mes = now.get(Calendar.MONTH)+1;
		filename += "_"+mes+"";
		filename += "_"+now.get(Calendar.DAY_OF_MONTH);
		filename += "_"+now.get(Calendar.HOUR_OF_DAY);
		filename += "_"+now.get(Calendar.MINUTE);
		filename += "_"+now.get(Calendar.SECOND);
		filename += "."+ext;
		return filename;
		
	}
}