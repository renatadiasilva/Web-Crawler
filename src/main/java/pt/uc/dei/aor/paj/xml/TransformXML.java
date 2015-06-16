package pt.uc.dei.aor.paj.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformXML {
	
	private static final Logger log = LoggerFactory.getLogger(TransformXML.class);
	
	//XML to String
	public static String convertXMLFileToString(String fileName) 
	{ 
		try{ 
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
			InputStream inputStream = new FileInputStream(new File(fileName)); 
			org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
			StringWriter stw = new StringWriter(); 
			Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
			serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
			return stw.toString(); 
		} 
		catch (Exception e) { 
			log.error("Error: "+e.getMessage());
		} 
		return null; 
	}

}
