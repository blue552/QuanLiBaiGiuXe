package dao;

import entity.VeNgay;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Time;
import java.text.ParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class VeXe_Dao {

    private List<VeNgay> veNgayList;
    private final String XML_FILE_PATH = "veNgayData.xml";

    public VeXe_Dao() {
        veNgayList = readXml();
    }

    public List<VeNgay> getVeNgayList() {
        return veNgayList;
    }

    public List<VeNgay> TimKiemNgay(Date ngay) {
        List<VeNgay> list = new ArrayList<>();
        for (VeNgay veNgay : veNgayList) {
            if (veNgay.getNgayNhan().equals(ngay)) {
                list.add(veNgay);
            }
        }
        return list;
    }

    public boolean addVeNgay(VeNgay veNgay) {
        veNgayList.add(veNgay);
        writeXml(veNgayList);
        return true;
    }

    public boolean deleteVeNgay(String maVe) {
        VeNgay veNgayToDelete = findVeNgayByMaVe(maVe);
        if (veNgayToDelete != null) {
            veNgayList.remove(veNgayToDelete);
            writeXml(veNgayList);
            return true;
        }
        return false;
    }

    public VeNgay TimKiemBienSo(String bienSo) {
        for (VeNgay veNgay : veNgayList) {
            if (veNgay.getBienSo().contains(bienSo)) {
                return veNgay;
            }
        }
        return null;
    }

    public VeNgay TimKiemMa(String ma) {
        for (VeNgay veNgay : veNgayList) {
            if (veNgay.getMaVe().equals(ma)) {
                return veNgay;
            }
        }
        return null;
    }

    public List<VeNgay> TimKiemLoaiXe(String loaiXe) {
        List<VeNgay> list = new ArrayList<>();
        for (VeNgay veNgay : veNgayList) {
            if (veNgay.getLoaiXe().equalsIgnoreCase(loaiXe)) {
                list.add(veNgay);
            }
        }
        return list;
    }

    public List<VeNgay> TimKiemThang(int thang) {
        List<VeNgay> list = new ArrayList<>();
        for (VeNgay veNgay : veNgayList) {
            if (veNgay.getNgayNhan().getMonth() + 1 == thang) {
                list.add(veNgay);
            }
        }
        return list;
    }

    public boolean updateVeNgay(VeNgay veNgay) {
        VeNgay existingVeNgay = findVeNgayByMaVe(veNgay.getMaVe());
        if (existingVeNgay != null) {
            veNgayList.remove(existingVeNgay);
            veNgayList.add(veNgay);
            writeXml(veNgayList);
            return true;
        }
        return false;
    }

    public VeNgay findVeNgayByMaVe(String maVe) {
        for (VeNgay veNgay : veNgayList) {
            if (veNgay.getMaVe().equals(maVe)) {
                return veNgay;
            }
        }
        return null;
    }

    private List<VeNgay> readXml() {
        List<VeNgay> result = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("VeNgay");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String maVe = element.getElementsByTagName("MaVe").item(0).getTextContent();
                    String loaiXe = element.getElementsByTagName("LoaiXe").item(0).getTextContent();
                    String bienSo = element.getElementsByTagName("BienSo").item(0).getTextContent();
                    String mauXe = element.getElementsByTagName("MauXe").item(0).getTextContent();
                    String khuVuc = element.getElementsByTagName("KhuVuc").item(0).getTextContent();

                    String ViTri = element.getElementsByTagName("ViTri").item(0).getTextContent();
                    String GioNhan = element.getElementsByTagName("GioNhan").item(0).getTextContent();
                    String NgayNhan = element.getElementsByTagName("NgayNhan").item(0).getTextContent();
                    String GioTra = element.getElementsByTagName("GioTra").item(0).getTextContent();
                    String NgayTra = element.getElementsByTagName("NgayTra").item(0).getTextContent();

                    VeNgay veNgay = new VeNgay(maVe, loaiXe, bienSo, mauXe);
                    
                    Date parsedDate2 = dateFormat2.parse(NgayNhan);

//                    Date parsedDate = dateFormat.parse(GioNhan);
//                    Time gioNhan = new Time(parsedDate.getTime());
                    try {
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                        // Parse the GioNhan string to a Date object
                        Date parsedTime = timeFormat.parse(GioNhan);

                        // Create a Time object using the parsed time
                        Time gioNhan2 = new Time(parsedTime.getTime());

                        // Print the Time object
                        System.out.println("GioNhan formatted as Time: " + gioNhan2);

                        veNgay.setGioNhan(gioNhan2);
                    } catch (ParseException e) {
                        // Handle the parse exception
                        e.printStackTrace();
                    }

                    veNgay.setNgayNhan(parsedDate2);
                    ViTri_Dao daovt = new ViTri_Dao();
                    KhuVuc_Dao daokv = new KhuVuc_Dao();
                     SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                        // Parse the GioNhan string to a Date object
                        Date parsedTime = timeFormat.parse(GioNhan);
                    if (!GioTra.equals("null")) {
                        Date parsedDate3 = dateFormat2.parse(NgayTra);
                        Date parsedDate4 = timeFormat.parse(GioTra);
                        Time gioTra = new Time(parsedDate4.getTime());
                        veNgay.setGioTra(gioTra);
                        veNgay.setNgayTra(parsedDate3);
                    }
                    veNgay.setViTri(daovt.TimKiemViTriByMa(ViTri));
                    veNgay.setKhuVuc(daokv.TimKiemMa(khuVuc));

                    result.add(veNgay);
                }
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void writeXml(List<VeNgay> veNgayList) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("VeNgayList");
            doc.appendChild(rootElement);

            for (VeNgay veNgay : veNgayList) {
                Element veNgayElement = doc.createElement("VeNgay");
                rootElement.appendChild(veNgayElement);

                veNgayElement.appendChild(createXmlElement(doc, "MaVe", veNgay.getMaVe()));
                veNgayElement.appendChild(createXmlElement(doc, "LoaiXe", veNgay.getLoaiXe()));
                veNgayElement.appendChild(createXmlElement(doc, "BienSo", veNgay.getBienSo()));
                veNgayElement.appendChild(createXmlElement(doc, "MauXe", veNgay.getMauXe()));
                veNgayElement.appendChild(createXmlElement(doc, "KhuVuc", veNgay.getKhuVuc().getMaKhuVuc()));
                veNgayElement.appendChild(createXmlElement(doc, "ViTri", veNgay.getViTri().getMaViTri()));
                veNgayElement.appendChild(createXmlElement(doc, "GioNhan", veNgay.getGioNhan().toString()));
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                if (veNgay.getGioTra() != null) {
                    veNgayElement.appendChild(createXmlElement(doc, "GioTra", veNgay.getGioTra().toString()));
                    veNgayElement.appendChild(createXmlElement(doc, "NgayTra", dateFormat2.format(veNgay.getNgayTra())));
                    veNgayElement.appendChild(createXmlElement(doc, "NgayNhan", dateFormat2.format(veNgay.getNgayNhan())));
                } else {
                    veNgayElement.appendChild(createXmlElement(doc, "NgayNhan",dateFormat2.format(veNgay.getNgayNhan())));
                    veNgayElement.appendChild(createXmlElement(doc, "NgayTra", "null"));
                    veNgayElement.appendChild(createXmlElement(doc, "GioTra", "null"));
                }

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XML_FILE_PATH));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element createXmlElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }
}
