package pt.uc.dei.aor.paj;

import java.io.BufferedReader;
import java.io.File;
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

	// MAIN
	public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Boolean quit = false;

		Publisher p = new Publisher();

		while (!quit) {

			//verifica se há ficheiros a enviar na diretoria log-files
			List<File> filesInFolder = Files.walk(Paths.get("../src/main/resources/log-files"))
					.filter(Files::isRegularFile)
					.map(Path::toFile)
					.collect(Collectors.toList());

			for (File f : filesInFolder) {
				String filePathDir = "..\\src\\main\\resources\\log-files\\"+f.getName();
				//envia info para o topic
				tryToSend(1, filePathDir);
			}

			//chamar o parser
			NewsParser c = new NewsParser();
			System.out.println("Starting News Crawling.");
			NoticiasType noticias = c.doCrawler();

			if (c.isErro()) {
				System.out.println("Failure in Crawling (possible bad network connection).");
				System.out.println("No message will be sent.");
			}
			else {
				System.out.println("News Crawling done.");

				String filename = "output.xml";
				//passa para XML
				try {
					filename = outputNameFile("sent-files", "output");
					JAXBHandler.marshal(noticias, new File (filename));
					System.out.println("Marshall done.");
				} catch (IOException | JAXBException e) {
					System.out.println("Marshall failed.");
					System.out.println("RunWebCrawler Error: "+e.getMessage());
				}

				//envia info para o topic
				tryToSend(2, filename);

			}

			//termina ou continua
			System.out.print("1- continue; 2- quit? ");
			String answer = reader.readLine();

			if (answer.equals("2")) {
				// termina subscribers
				p.publish("stop");

				// termina publisher				
				System.out.println("Exiting...");
				reader.close();
				System.out.println("Goodbye!");
				quit = true;
			}
		}

	}

	// cria nome de ficheiro
	public static String outputNameFile(String dir, String name) {

		Calendar now = new GregorianCalendar();
		String filename = "..\\src\\main\\resources\\"+dir+"\\"+name;
		filename += "_"+now.get(Calendar.YEAR);
		int mes = now.get(Calendar.MONTH)+1;
		filename += "_"+mes+"";
		filename += "_"+now.get(Calendar.DAY_OF_MONTH);
		filename += "_"+now.get(Calendar.HOUR_OF_DAY);
		filename += "_"+now.get(Calendar.MINUTE);
		filename += "_"+now.get(Calendar.SECOND);
		filename += "_"+now.get(Calendar.MILLISECOND);
		filename += ".xml";
		return filename;

	}

	// envia para o JMS, tentando durante algum tempo
	public static void tryToSend(int tag, String filePath) {

		try {

			//passar XML para String
			String stringXML = TransformXML.convertXMLFileToString(filePath);
			System.out.println("XML converted into String.");

			//passar String para o topic
			Publisher p = new Publisher();
			p.publish(stringXML);

			//repete durante 10 segundos tentativa de publicar no topic se falhar
			int times = 0;
			while ((p.isError()) && (times < 9)) {
				System.out.println("XML not published.");
				Thread.sleep(1000);
				p.setError(false);
				System.out.println("Trying to publish XML ("+(times+2)+").");
				p.publish(stringXML);
				times++;
			}

			if (p.isError()) {
				System.out.println("XML not published (Timeout).");

				//guardar info não enviada (se é publish normal)
				if (tag == 2) {
					//move ficheiro para log-files (muda nome para log)
					if (moveFile(filePath,"log","log-files"))
						System.out.println("XML not sent saved in folder LOG-FILES.");
					else System.out.println("Error moving XML file.");
				} else System.out.println("XML file kept in folder LOG-FILES.");
			} else {
				System.out.println("XML sent.");

				//guardar info enviada (se é re-publish)
				if (tag == 1) {
					//move ficheiro para sent-files (muda nome para output)
					if (moveFile(filePath, "output", "sent-files"))
						System.out.println("XML sent saved in folder SENT-FILES.");
					else System.out.println("Error moving XML file.");
				} else System.out.println("XML file kept in folder SENT-FILES.");
			}

		} catch (InterruptedException ie) {
			System.out.println("RunWebCrawler.tryToSend Error: "+ie.getMessage());
		} catch (Exception e) {
			System.out.println("RunWebCrawler.tryToSend Error: "+e.getMessage());
		}

	}

	//muda ficheiro de diretoria
	private static boolean moveFile(String filePath, String newName, String newDir) {

		try{

			File file = new File(filePath);

			if(file.renameTo(new File(outputNameFile(newDir, newName))))
				return true;

		} catch(Exception e) {
			System.out.println("RunWebCrawler.modeFile Error: "+e.getMessage());
		}

		return false;
	}

}