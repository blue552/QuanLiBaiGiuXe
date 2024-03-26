package dao;

import entity.KhuVuc;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KhuVuc_Dao {
    private static final String XML_FILE_PATH = "khuVucData.xml";

    public List<KhuVuc> getLS() {
        List<KhuVuc> ds = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("KhuVuc");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String ma = element.getElementsByTagName("MaKhuVuc").item(0).getTextContent();
                    String ten = element.getElementsByTagName("TenKhuVuc").item(0).getTextContent();

                    KhuVuc khuVuc = new KhuVuc(ma, ten);
                    ds.add(khuVuc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public KhuVuc TimKiemMa(String ma) {
        KhuVuc khuVuc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("KhuVuc");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String currentMa = element.getElementsByTagName("MaKhuVuc").item(0).getTextContent();

                    if (currentMa.equals(ma)) {
                        String ten = element.getElementsByTagName("TenKhuVuc").item(0).getTextContent();
                        khuVuc = new KhuVuc(ma, ten);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return khuVuc;
    }

    public String TimKiemTen(String ten) {
        String maKhuVuc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("KhuVuc");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String currentTen = element.getElementsByTagName("TenKhuVuc").item(0).getTextContent();

                    if (currentTen.equals(ten)) {
                        maKhuVuc = element.getElementsByTagName("MaKhuVuc").item(0).getTextContent();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maKhuVuc;
    }

    // Add a method to save data to the XML file
    public void saveToXml(List<KhuVuc> khuVucList) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("KhuVucList");
            doc.appendChild(rootElement);

            for (KhuVuc khuVuc : khuVucList) {
                Element khuVucElement = doc.createElement("KhuVuc");

                Element maElement = doc.createElement("MaKhuVuc");
                maElement.appendChild(doc.createTextNode(khuVuc.getMaKhuVuc()));
                khuVucElement.appendChild(maElement);

                Element tenElement = doc.createElement("TenKhuVuc");
                tenElement.appendChild(doc.createTextNode(khuVuc.getTenKhuVuc()));
                khuVucElement.appendChild(tenElement);

                rootElement.appendChild(khuVucElement);
            }

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XML_FILE_PATH));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
