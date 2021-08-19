import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Lesson4XML {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		 if (args.length < 1) {
			 System.out.println("File name required");
			 return;
	        }
		 String filename = args[0];
		 //String filename = "C:\\Git\\ucsd-java4\\Lesson4XML\\bin\\JobResult_UCSDExt.xml";
		 domParser(filename);
		 saxParser(filename);
		 xpathParser(filename);
		 System.out.println("All Done!");
	}

	private static void domParser(String filename) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("Results of XML Parsing using DOM Parser:");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File(filename));
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		
		String serial = root.getElementsByTagName("serial").item(0).getTextContent();
		System.out.println("serial: " + serial);
		
		NodeList nodeList = document.getElementsByTagName("data");
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if (node instanceof Element)
			{
				Element element = (Element)node;
				if (element.getElementsByTagName("visible-string").getLength() > 0) {
					String visibleString = element.getElementsByTagName("visible-string").item(0).getTextContent();
					System.out.println("visible-string: " + visibleString);
				}
				if (element.getElementsByTagName("unsigned").getLength() > 0) {
					String unsigned = element.getElementsByTagName("unsigned").item(0).getTextContent();
					System.out.println("unsigned: " + unsigned);
				}
			}
		}
	}	
	
	private static void saxParser(String filename) throws ParserConfigurationException, SAXException, IOException {
		DefaultHandler handler = new DefaultHandler()
		{
		   boolean isSerial = false;
		   boolean isVisibleString = false;
		   boolean isUnsigned = false;
			   
			@Override
			public void startElement(String namespaceURI, String lname, String qname,
			Attributes attrs)
			{
				 if (qname.equalsIgnoreCase("serial")) {
					 isSerial = true;
			      } else if (qname.equalsIgnoreCase("visible-string")) {
			    	  isVisibleString = true;
			      } else if (qname.equalsIgnoreCase("unsigned")) {
			    	  isUnsigned = true;
			      }
			}
			
			@Override
			public void characters(char ch[], int start, int length) throws SAXException {
				if (isSerial) {
			         System.out.println("serial: " + new String(ch, start, length));
			         isSerial = false;
			    } else if (isVisibleString) {
			         System.out.println("visible-string: " + new String(ch, start, length));
			         isVisibleString = false;
			    } else if (isUnsigned) {
			         System.out.println("unsigned: " + new String(ch, start, length));
			         isUnsigned = false;
			    } 
		   }
		};
		System.out.println("Results of XML Parsing using SAX Parser:");
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		saxParserFactory.setNamespaceAware(true);
		SAXParser saxParser = saxParserFactory.newSAXParser();

		File file = new File(filename);
		saxParser.parse(file, handler);
	}
	
	private static void xpathParser(String filename) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		System.out.println("Results of XML Parsing using XPath:");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true); 
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File(filename));

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		
        XPathExpression xPathExpression = xPath.compile("//serial/text()");
        Object result = xPathExpression.evaluate(document, XPathConstants.NODESET);
        NodeList nodeList = (NodeList) result;
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println("serial: " + nodeList.item(i).getNodeValue());
        }
        
        xPathExpression = xPath.compile("//data/visible-string/text()");
        result = xPathExpression.evaluate(document, XPathConstants.NODESET);
        nodeList = (NodeList) result;
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println("visible-string: " + nodeList.item(i).getNodeValue());
        }
        
        xPathExpression = xPath.compile("//data/structure/unsigned/text()");
        result = xPathExpression.evaluate(document, XPathConstants.NODESET);
        nodeList = (NodeList) result;
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println("unsigned: " + nodeList.item(i).getNodeValue());
        }
	}
}
