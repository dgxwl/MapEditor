package com.dgx;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class AddItem {
	public static void main(String[] args) {
		XMLWriter writer = null;
		try (
				Scanner scanner = new Scanner(System.in);
		){
			SAXReader reader = new SAXReader();
			Document doc = reader.read("map.xml");
			Element root = doc.getRootElement();
			Element newItem = root.addElement("item");
			System.out.print("Input x:");
			int x = scanner.nextInt();
			System.out.print("Input y:");
			int y = scanner.nextInt();
			System.out.print("Input src:");
			String src = scanner.next();
			newItem.addElement("x").setText(""+x);
			newItem.addElement("y").setText(""+y);
			newItem.addElement("src").setText(src);
			writer = new XMLWriter(new FileOutputStream("map.xml"), OutputFormat.createPrettyPrint());
			writer.write(doc);
			System.out.println("done.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
