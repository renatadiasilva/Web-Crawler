//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.12 at 06:30:46 PM BST 
//


package pt.uc.dei.aor.paj.data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the packageCrawler package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Noticias_QNAME = new QName("", "noticias");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: packageCrawler
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NoticiasType }
     * 
     */
    public NoticiasType createNoticiasType() {
        return new NoticiasType();
    }

    /**
     * Create an instance of {@link NoticiaType }
     * 
     */
    public NoticiaType createNoticiaType() {
        return new NoticiaType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoticiasType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "noticias")
    public JAXBElement<NoticiasType> createNoticias(NoticiasType value) {
        return new JAXBElement<NoticiasType>(_Noticias_QNAME, NoticiasType.class, null, value);
    }

}
