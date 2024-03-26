package dao;

import entity.LoaiVe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoaiVe_Dao {

    private static final String XML_FILE_PATH = "loaiVeData.xml";

    public List<LoaiVe> getLoaiVeList() {
        List<LoaiVe> loaiVeList = new ArrayList<>();

        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("LoaiVe");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String maLV = element.getElementsByTagName("MaVe").item(0).getTextContent();
                    String tenLV = element.getElementsByTagName("TenVe").item(0).getTextContent();
                    LoaiVe loaiVe = new LoaiVe(maLV, tenLV);
                    loaiVeList.add(loaiVe);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loaiVeList;
    }

    public LoaiVe findLoaiVeByMa(String ma) {
        LoaiVe loaiVe = null;

        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("LoaiVe");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String maLV = element.getElementsByTagName("MaVe").item(0).getTextContent();
                    if (maLV.equals(ma)) {
                        String tenLV = element.getElementsByTagName("TenVe").item(0).getTextContent();
                        loaiVe = new LoaiVe(maLV, tenLV);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loaiVe;
    }
}
