package com.dgx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPSelection extends JPanel {
	private static final long serialVersionUID = 1L;
	
	static List<BufferedImage> img = new ArrayList<>();
	static {
		for (int i = 0; i < 3; i++) {
			BufferedImage b = null;
			try {
				b = ImageIO.read(new File(i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			img.add(b);
		}
	}
	
	int width = 48;
	int height = 48;
	
	public JPSelection() {
		setSize(width, height);
	}
	
	public void drawSelection() {
		Graphics g = getGraphics();
		g.drawImage(img.get(0), 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		System.out.println("OK");
		this.setSize(width, height);
		g.drawImage(img.get(0), width, height, null);
	}
}
