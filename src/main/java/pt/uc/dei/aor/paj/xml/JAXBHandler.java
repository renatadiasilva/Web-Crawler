package pt.uc.dei.aor.paj.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import pt.uc.dei.aor.paj.data.NoticiaType;
import pt.uc.dei.aor.paj.data.NoticiasType;


public class JAXBHandler {
	
	// Export
	public static void marshal(Noticias news, File selectedFile)
			throws IOException, JAXBException {
        JAXBContext context;
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(selectedFile));
        context = JAXBContext.newInstance(Noticias.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, "US-ASCII");
        
        try{
            m.setProperty("com.sun.xml.internal.bind.xmlHeaders",
            		"<?xml-stylesheet type=\"text/xsl\" href=\"text.xsl\"?>\n");
            
            System.out.println("PONTO 1! JAXBHandler");
        }
        catch(PropertyException pex)
        {
            m.setProperty("com.sun.xml.bind.xmlHeaders",
            		"<?xml-stylesheet type=\"text/xsl\" href=\"text.xsl\"?>\n");
            System.out.println("PONTO 2! JAXBHandler");
        }
        for(NoticiaType n: news.getNoticia()){
        	System.out.println(n);
        }
        System.out.println("JAXBHandler");
        
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        System.out.println("JAXBHandler  teste marshal 1");
        
        m.marshal(news, writer);
        System.out.println("JAXBHandler  teste marshal 2");
        writer.close();
        
        
    }
	
	// Import
    public static Noticias unmarshal(File importFile) throws JAXBException {
        Noticias news = new Noticias();
        JAXBContext context = JAXBContext.newInstance(Noticias.class);
        Unmarshaller um = context.createUnmarshaller();
        news = (Noticias) um.unmarshal(importFile);
        return news;
    }

}
