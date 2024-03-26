package dao;

import entity.VeNgay;
import entity.VeThang;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VeThang_Dao {
    private static final String XML_FILE_PATH = "veThangData.xml";
    
    
    

    public List<VeThang> getLS() {
        List<VeThang> veThangList = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("VeThang");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String maVe = element.getElementsByTagName("MaVe").item(0).getTextContent();
                    String loaiXe = element.getElementsByTagName("LoaiXe").item(0).getTextContent();
                    String bienSo = element.getElementsByTagName("BienSo").item(0).getTextContent();
                    String mauXe = element.getElementsByTagName("MauXe").item(0).getTextContent();
                    String tenKH = element.getElementsByTagName("TenKH").item(0).getTextContent();
                    String soDienThoai = element.getElementsByTagName("SoDienThoai").item(0).getTextContent();
                    Date ngayDangKy = new SimpleDateFormat("yyyy-MM-dd")
                            .parse(element.getElementsByTagName("NgayDangKy").item(0).getTextContent());

                    VeThang veThang = new VeThang(maVe, loaiXe, bienSo, mauXe, tenKH, soDienThoai,ngayDangKy);
                    String NgayNhan = element.getElementsByTagName("NgayNhan").item(0).getTextContent();

                    if(!NgayNhan.equals("null")){
                          String khuVuc = element.getElementsByTagName("KhuVuc").item(0).getTextContent();
                        String ViTri = element.getElementsByTagName("ViTri").item(0).getTextContent();
                        String GioNhan = element.getElementsByTagName("GioNhan").item(0).getTextContent();
//                    String NgayNhan = element.getElementsByTagName("NgayNhan").item(0).getTextContent();
                        String GioTra = element.getElementsByTagName("GioTra").item(0).getTextContent();
                        String NgayTra = element.getElementsByTagName("NgayTra").item(0).getTextContent();
                    
                    Date parsedDate = dateFormat.parse(GioNhan);
                    Time gioNhan = new Time(parsedDate.getTime());

                    Date parsedDate2 = dateFormat2.parse(NgayNhan);
                    
                    veThang.setGioNhan(gioNhan);
                    veThang.setNgayNhan(parsedDate2);
                    ViTri_Dao daovt = new ViTri_Dao();
                                        KhuVuc_Dao daokv = new KhuVuc_Dao();
                    if(!GioTra.equals("null")){
                                            Date parsedDate3 = dateFormat2.parse(NgayTra);
                    Date parsedDate4 = dateFormat.parse(GioTra);
                    Time gioTra = new Time(parsedDate4.getTime());
                         veThang.setGioTra(gioTra);
                         veThang.setNgayTra(parsedDate3);
                    }
                    veThang.setViTri(daovt.TimKiemViTriByMa(ViTri));
                    veThang.setKhuVuc(daokv.TimKiemMa(khuVuc));
                    }
                   
              
                    veThangList.add(veThang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return veThangList;
    }

    public VeThang TimKiemMa(String ma) {
        List<VeThang> veThangList = getLS();
        for (VeThang veThang : veThangList) {
            if (veThang.getMaVe().equals(ma)) {
                return veThang;
            }
        }
        return null;
    }

    public boolean addVeThang(VeThang veThang) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));

            Element newVeThang = doc.createElement("VeThang");

            // Add child elements
            newVeThang.appendChild(createXmlElement(doc, "MaVe", veThang.getMaVe()));
            newVeThang.appendChild(createXmlElement(doc, "LoaiXe", veThang.getLoaiXe()));
            newVeThang.appendChild(createXmlElement(doc, "BienSo", veThang.getBienSo()));
            newVeThang.appendChild(createXmlElement(doc, "MauXe", veThang.getMauXe()));
            newVeThang.appendChild(createXmlElement(doc, "TenKH", veThang.getTenKH()));
            newVeThang.appendChild(createXmlElement(doc, "SoDienThoai", veThang.getSoDienThoai()));
            newVeThang.appendChild(createXmlElement(doc, "NgayDangKy",
                    new SimpleDateFormat("yyyy-MM-dd").format(veThang.getNgayDangKy())));
            
                              newVeThang.appendChild(createXmlElement(doc, "GioTra", "null"));
                             newVeThang.appendChild(createXmlElement(doc, "NgayTra", "null"));
                             newVeThang.appendChild(createXmlElement(doc, "NgayNhan", "null"));
                                                   newVeThang.appendChild(createXmlElement(doc, "GioNhan", "null"));
                                                   newVeThang.appendChild(createXmlElement(doc, "ViTri", "null"));
                                                   newVeThang.appendChild(createXmlElement(doc, "KhuVuc", "null"));

            
         
            // Append to root element
            doc.getDocumentElement().appendChild(newVeThang);

            // Write the updated XML back to the file
            writeXmlToFile(doc);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVeThang(String maVe) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(XML_FILE_PATH));

            NodeList veThangList = doc.getElementsByTagName("VeThang");

            for (int i = 0; i < veThangList.getLength(); i++) {
                Node node = veThangList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    if (element.getElementsByTagName("MaVe").item(0).getTextContent().equals(maVe)) {
                        // Remove the element
                        element.getParentNode().removeChild(element);

                        // Write the updated XML back to the file
                        writeXmlToFile(doc);

                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
     

    public boolean updateVeThang(VeThang veThang) {
        VeThang existingVeNgay = TimKiemMa(veThang.getMaVe());
        List<VeThang> veNgayList = getLS();
        if (existingVeNgay != null) {
            
            veNgayList.remove(existingVeNgay);
            veNgayList.add(veThang);
            
            return true;
        }
        return false;
    }

    public VeThang TimKiemBienSo(String bienSo) {
        List<VeThang> veThangList = getLS();
        for (VeThang veThang : veThangList) {
            if (veThang.getBienSo().contains(bienSo)) {
                return veThang;
            }
        }
        return null;
    }

    public List<VeThang> TimKiemLoaiXe(String loaiXe) {
        List<VeThang> result = new ArrayList<>();
        List<VeThang> veThangList = getLS();

        for (VeThang veThang : veThangList) {
            if (veThang.getLoaiXe().equals(loaiXe)) {
                result.add(veThang);
            }
        }
        return result;
    }

    public List<VeThang> TimKiemThangDK(int thang) {
        List<VeThang> result = new ArrayList<>();
        List<VeThang> veThangList = getLS();

        for (VeThang veThang : veThangList) {
            if (veThang.getNgayDangKy().getMonth() == thang - 1) {
                result.add(veThang);
            }
        }
        return result;
    }

    public List<VeThang> TimKiemNgayDK(Date ngay) {
        List<VeThang> result = new ArrayList<>();
        List<VeThang> veThangList = getLS();

        for (VeThang veThang : veThangList) {
            if (veThang.getNgayDangKy().equals(ngay)) {
                result.add(veThang);
            }
        }
        return result;
    }

    public List<VeThang> TimKiemThangGui(int thang) {
        List<VeThang> result = new ArrayList<>();
        List<VeThang> veThangList = getLS();

        for (VeThang veThang : veThangList) {
            if (veThang.getNgayNhan().getMonth() == thang - 1) {
                result.add(veThang);
            }
        }
        return result;
    }
    
    public boolean updateTTVeThang2(VeThang veThang) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(XML_FILE_PATH);

            NodeList nodeList = doc.getElementsByTagName("VeThang");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String maVe = element.getElementsByTagName("MaVe").item(0).getTextContent();
                    if (maVe.equals(veThang.getMaVe())) {
                        element.getElementsByTagName("LoaiXe").item(0).setTextContent(veThang.getLoaiXe());
                        element.getElementsByTagName("TenKH").item(0).setTextContent(veThang.getTenKH());
                        element.getElementsByTagName("SoDienThoai").item(0).setTextContent(veThang.getSoDienThoai());
                        if (veThang.getLoaiXe().equals("Xe Đạp")) {
                            element.getElementsByTagName("BienSo").item(0).setTextContent("Không");
                        } else {
                            element.getElementsByTagName("BienSo").item(0).setTextContent(veThang.getBienSo());
                        }
                     
                        // Save the updated XML file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(XML_FILE_PATH));
                        transformer.transform(source, result);

                        return true;
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateTTVeThang(VeThang veThang) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(XML_FILE_PATH);

            NodeList nodeList = doc.getElementsByTagName("VeThang");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String maVe = element.getElementsByTagName("MaVe").item(0).getTextContent();
                    if (maVe.equals(veThang.getMaVe())) {
                        element.getElementsByTagName("LoaiXe").item(0).setTextContent(veThang.getLoaiXe());
                        element.getElementsByTagName("TenKH").item(0).setTextContent(veThang.getTenKH());
                        element.getElementsByTagName("SoDienThoai").item(0).setTextContent(veThang.getSoDienThoai());
                        if (veThang.getLoaiXe().equals("Xe Đạp")) {
                            element.getElementsByTagName("BienSo").item(0).setTextContent("Không");
                        } else {
                            element.getElementsByTagName("BienSo").item(0).setTextContent(veThang.getBienSo());
                        }
                        element.getElementsByTagName("MauXe").item(0).setTextContent(veThang.getMauXe());
                                                    element.getElementsByTagName("GioNhan").item(0).setTextContent( veThang.getGioNhan().toString());
                                                    element.getElementsByTagName("KhuVuc").item(0).setTextContent( veThang.getKhuVuc().getMaKhuVuc());
                                                    element.getElementsByTagName("ViTri").item(0).setTextContent( veThang.getViTri().getMaViTri());

                        if(veThang.getGioTra() != null){
                                                                               SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

                            element.getElementsByTagName("GioTra").item(0).setTextContent( veThang.getGioTra().toString());
                                                        element.getElementsByTagName("NgayTra").item(0).setTextContent(  dateFormat2.format(veThang.getNgayTra()));
                                                        element.getElementsByTagName("NgayNhan").item(0).setTextContent(  dateFormat2.format(veThang.getNgayNhan()));

                                              
                                              }else{
                            element.getElementsByTagName("NgayNhan").item(0).setTextContent( veThang.getNgayNhan().toString());
                                                   element.getElementsByTagName("NgayTra").item(0).setTextContent( "null");
                                                        element.getElementsByTagName("GioTra").item(0).setTextContent( "null");
                                              }
                        // Save the updated XML file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(XML_FILE_PATH));
                        transformer.transform(source, result);

                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<VeThang> TimKiemNgayGui(Date ngay) {
        List<VeThang> result = new ArrayList<>();
        List<VeThang> veThangList = getLS();

        for (VeThang veThang : veThangList) {
            if (veThang.getNgayNhan().equals(ngay)) {
                result.add(veThang);
            }
        }
        return result;
    }

    private Element createXmlElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }

    private void writeXmlToFile(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XML_FILE_PATH));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
