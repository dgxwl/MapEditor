package com.dgx;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadConfig { 
	public static void main(String[] args) {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read("map.xml");
			Element root = doc.getRootElement();
			int width = Integer.parseInt(root.attributeValue("width"));
			int height = Integer.parseInt(root.attributeValue("height"));
			System.out.println("fragment's width: "+width);
			System.out.println("fragment's height: "+height);
			List<Element> items = root.elements("item");
			for (Element e : items) {
				int x = Integer.parseInt(e.elementText("x"));
				int y = Integer.parseInt(e.elementText("y"));
				String src = e.elementText("src");
				System.out.println("x: "+x+", y: "+y+", src:"+src);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
