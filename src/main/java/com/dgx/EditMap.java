package com.dgx;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EditMap extends JPanel {
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
	
	Map<Point, BufferedImage> optionsMap = new HashMap<>();
	BufferedImage selected;
	
	public void doClick() {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX() / 48 * 48;
				int y = e.getY() / 48 * 48;
				if (selected == null) {
					selected = optionsMap.get(new Point(x, y));
					System.out.println(selected);
				} else {
					Graphics g = getGraphics();
					g.drawImage(selected, x, y, null);
				}
			}
		};
		this.addMouseListener(l);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(img.get(0), 0, 0, null);
		optionsMap.put(new Point(0, 0), img.get(0));
		g.drawImage(img.get(1), 48, 0, null);
		optionsMap.put(new Point(48, 0), img.get(1));
		System.out.println("ok");
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(768, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		EditMap em = new EditMap();
		frame.add(em);
		frame.setVisible(true);
		
		em.doClick();
	}
}
