package Ispirer.Test.PhonebookSearch;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PhonebookSearchApp {
    private static final String XML_FILE_PATH = "phonebook.xml";
    private static Document document;
    private static Map<String, String> phonebookMap = new HashMap<>();

    public static void main(String[] args) {
        loadXmlData();

        JFrame frame = new JFrame("Phonebook Search");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JLabel resultLabel = new JLabel("Result: ");

        searchButton.addActionListener(e -> {
            String searchSurname = searchField.getText().toLowerCase();
            String phoneNumber = searchPhoneBySurname(searchSurname);
            resultLabel.setText("Result: " + phoneNumber);
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(searchField);
        frame.add(searchButton);
        frame.add(resultLabel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void loadXmlData() {
        try {
            File xmlFile = new File(XML_FILE_PATH);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList entries = document.getElementsByTagName("entry");
            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);
                String surname = entry.getElementsByTagName("surname").item(0).getTextContent().toLowerCase();
                String phoneNumber = entry.getElementsByTagName("number").item(0).getTextContent();
                phonebookMap.put(surname, phoneNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String searchPhoneBySurname(String surname) {
        String phoneNumber = phonebookMap.get(surname);
        return phoneNumber != null ? phoneNumber : "Phone number not found";
    }
}