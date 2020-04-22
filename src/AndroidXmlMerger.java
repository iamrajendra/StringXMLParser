import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Objects;

public class AndroidXmlMerger {
    public static void main(String argv[]) {
        try {
//creating a constructor of file class and parsing an XML file
            File file = new File("C:\\Users\\91700\\IdeaProjects\\untitled1\\src\\file\\strings.xml");
//an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("string");
// nodeList is not iterable, so we are using for loop
            ArrayList<KeyValues> tagList = new ArrayList<>();
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                Element eElement = (Element) node;
                String key = node.getAttributes().getNamedItem("name").getNodeValue();


                if (eElement.getChildNodes().getLength()!=0) {
                    key = node.getAttributes().getNamedItem("name").getNodeValue();
                    String v = eElement.getChildNodes().item(0).getNodeValue();

                    KeyValues keyValues = new KeyValues(node.getAttributes().getNamedItem("name").getNodeValue(), v);

                    tagList.add(keyValues);
                }else {
                    System.out.println("key = " + key);
                }
            }


            System.out.println("With Duplicates" + tagList.size());

            LinkedHashSet<KeyValues> hashSet = new LinkedHashSet<>(tagList);

            ArrayList<KeyValues> listWithoutDuplicates = new ArrayList<>(hashSet);

            System.out.println("Removed Duplicates" + (tagList.size()-listWithoutDuplicates.size()));
            System.out.println("<resources>");

            for (int i = 0; i < listWithoutDuplicates.size(); i++) {
                KeyValues f = listWithoutDuplicates.get(i);
                System.out.println("<string name=\""+f.name+"\">"+f.value+"</string>");
            }
            System.out.println("</resources>");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static class KeyValues {
        public KeyValues(String name, String value) {
            this.name = name;
            this.value = value;
        }

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public boolean equals(Object o) {
            KeyValues kk = (KeyValues) o;
            return name.equals(kk.name);
        }


    }
}
