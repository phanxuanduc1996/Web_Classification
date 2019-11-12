/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclassification.io;

import webclassification.alogrithm.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
import org.xml.sax.SAXException;

/**
 *
 * @author THANH
 */
public class XmlHandler {

    public XmlHandler(String sFilePath) {
        xmlFile = new File(sFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            db = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            
            Logger.getLogger(XmlHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(ArrayList<Category> listCategory) {
        dom = db.newDocument();
        Element rootElement = dom.createElement("info");
        for (Category cat : listCategory) {
            Element topicElement = createElementFromCategory(cat);
            rootElement.appendChild(topicElement);
        }
        dom.appendChild(rootElement);

        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(dom);
            StreamResult result = new StreamResult(xmlFile);
            tr.transform(source, result);
        } catch (IllegalArgumentException | TransformerException ex) {
            Algorithm.checkFile2 = false;
            JOptionPane.showMessageDialog(null, "File xml không hợp lệ!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        } 
        Algorithm.checkFile2 = true;
        dom = null; // huỷ đối tượng
    }

    public ArrayList<Category> getData() {
        ArrayList<Category> listCategories = new ArrayList<>();
        
        try {
            dom = db.parse(xmlFile);
            Element root = (Element) dom.getElementsByTagName("info").item(0);
            NodeList nodeList = root.getElementsByTagName("topic");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element topicElement = (Element) nodeList.item(i);
                Category cat = createCategoryFromTopicElement(topicElement);
                listCategories.add(cat);
            }
        } catch (SAXException | IOException ex) {
            Algorithm.checkFile1 = false;
            JOptionPane.showMessageDialog(null, "File xml không hợp lệ!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        Algorithm.checkFile1 = true;
        return listCategories;
    }

    private Element createElementFromCategory(Category cat) {
        Element topicElement = dom.createElement("topic");
        topicElement.setAttribute("name", cat.getName());
        topicElement.setAttribute("sumdocs", String.valueOf(cat.getSumDocs()));

        for (Map.Entry<String, Integer> entry : cat.getHashMapWords().entrySet()) {
            String sWord = entry.getKey();
            String sFreq = String.valueOf(entry.getValue());

            Element wordElement = dom.createElement("word");
            Element valElement = dom.createElement("value");
            Element freqElement = dom.createElement("freq");

            valElement.appendChild(dom.createTextNode(sWord));
            freqElement.appendChild(dom.createTextNode(sFreq));

            wordElement.appendChild(valElement);
            wordElement.appendChild(freqElement);
            topicElement.appendChild(wordElement);
        }
        return topicElement;
    }
    
    private Category createCategoryFromTopicElement(Element topicElement) {
        String sCatName = topicElement.getAttribute("name");
        Category cat = new Category(sCatName);
        
        HashMap<String, Integer> mapWords = new HashMap<>();
        NodeList nodeList = topicElement.getElementsByTagName("word");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element wordElement = (Element) nodeList.item(i);
            Node valNode = wordElement.getElementsByTagName("value").item(0);
            Node freqNode = wordElement.getElementsByTagName("freq").item(0);
            String sWord = valNode.getTextContent();
            int freq = Integer.parseInt(freqNode.getTextContent());
            mapWords.put(sWord, freq);
        }
        cat.addHashMapAsDocument(mapWords);
        
        int sumDocs = Integer.parseInt(topicElement.getAttribute("sumdocs"));
        cat.resetSumDocs(sumDocs);
        
        return cat;
    }

    private DocumentBuilder db;
    private Document dom;
    private File xmlFile;
}
