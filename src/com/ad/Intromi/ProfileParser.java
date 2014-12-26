package com.ad.Intromi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProfileParser {

	Profile objItem;
	List<Profile> listArray;

	public List<Profile> getData(String url) {
//	public Profile getData(String url) {
		try {

			listArray = new ArrayList<Profile>();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());
			

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					objItem = new Profile();
/*        
					objItem.setMacHw(getTagValue("id", eElement));
					objItem.setName(getTagValue("name", eElement));
					objItem.setEmail(getTagValue("email", eElement));
					objItem.setMobilePhoneNum(getTagValue("mobilePhone", eElement));
					objItem.setSite(getTagValue("site", eElement));
					objItem.setPhotoLink(getTagValue("link", eElement));

*/					
					listArray.add(objItem);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listArray;
	
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();

	}
}
