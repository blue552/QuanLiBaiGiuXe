package dao;

import entity.KhuVuc;
import entity.ViTri;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViTri_Dao {
    private static final String XML_FILE_PATH = "viTriData.xml";
    private KhuVuc_Dao khuVuc_dao;

    public ViTri_Dao() {
        khuVuc_dao = new KhuVuc_Dao();
    }

    public List<ViTri> getLS() {
        List<ViTri> ds = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ViTri");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String maViTri = element.getElementsByTagName("MaViTri").item(0).getTextContent();
                    String tenViTri = element.getElementsByTagName("TenViTri").item(0).getTextContent();

                    ViTri viTri = new ViTri(maViTri, tenViTri);
                    viTri.setKhuVuc(khuVuc_dao.TimKiemMa(element.getElementsByTagName("MaKhuVuc").item(0).getTextContent()));

                    ds.add(viTri);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return ds;
    }

    public List<ViTri> TimKiemMaKV(String ma) {
        List<ViTri> list = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ViTri");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    if (element.getElementsByTagName("MaKhuVuc").item(0).getTextContent().equals(ma)) {
                        String maViTri = element.getElementsByTagName("MaViTri").item(0).getTextContent();
                        String tenViTri = element.getElementsByTagName("TenViTri").item(0).getTextContent();

                        ViTri viTri = new ViTri(maViTri, tenViTri);
                        viTri.setKhuVuc(khuVuc_dao.TimKiemMa(element.getElementsByTagName("MaKhuVuc").item(0).getTextContent()));

                        list.add(viTri);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ViTri TimKiemViTriByTen(String ma) {
        ViTri viTri = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ViTri");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    if (element.getElementsByTagName("TenViTri").item(0).getTextContent().equals(ma)) {
                        String maViTri = element.getElementsByTagName("MaViTri").item(0).getTextContent();
                        String tenViTri = element.getElementsByTagName("TenViTri").item(0).getTextContent();

                        viTri = new ViTri(maViTri, tenViTri);
                        viTri.setKhuVuc(khuVuc_dao.TimKiemMa(element.getElementsByTagName("MaKhuVuc").item(0).getTextContent()));

                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return viTri;
    }

    public ViTri TimKiemViTriByMa(String ma) {
        ViTri viTri = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ViTri");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    if (element.getElementsByTagName("MaViTri").item(0).getTextContent().equals(ma)) {
                        String maViTri = element.getElementsByTagName("MaViTri").item(0).getTextContent();
                        String tenViTri = element.getElementsByTagName("TenViTri").item(0).getTextContent();

                        viTri = new ViTri(maViTri, tenViTri);
                        viTri.setKhuVuc(khuVuc_dao.TimKiemMa(element.getElementsByTagName("MaKhuVuc").item(0).getTextContent()));

                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return viTri;
    }

    public void ThemViTri(ViTri viTri) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();

            Element viTriElement = doc.createElement("ViTri");
            rootElement.appendChild(viTriElement);

            Element maViTriElement = doc.createElement("MaViTri");
            maViTriElement.appendChild(doc.createTextNode(viTri.getMaViTri()));
            viTriElement.appendChild(maViTriElement);

            Element tenViTriElement = doc.createElement("TenViTri");
            tenViTriElement.appendChild(doc.createTextNode(viTri.getTenViTri()));
            viTriElement.appendChild(tenViTriElement);

            Element maKhuVucElement = doc.createElement("MaKhuVuc");
            maKhuVucElement.appendChild(doc.createTextNode(viTri.getKhuVuc().getMaKhuVuc()));
            viTriElement.appendChild(maKhuVucElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XML_FILE_PATH));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
