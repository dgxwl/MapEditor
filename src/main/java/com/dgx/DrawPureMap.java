package com.dgx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPureMap extends JPanel {
	static BufferedImage sky;
	static BufferedImage ground;
	public static void main(String[] args) throws IOException {
		sky = ImageIO.read(new File("0.png"));
		ground = ImageIO.read(new File("1.png"));
		
		JFrame frame = new JFrame();
		frame.setSize(768, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		DrawPureMap jp = new DrawPureMap();
		frame.add(jp);
		frame.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < 768; i+=48) {
			for (int j = 0; j < 624; j+=48) {
				g.drawImage(sky, i, j, null);
			}
		}
		for (int i = 0; i < 768; i+=48) {
			for (int j = 624; j < 720; j+=48) {
				g.drawImage(ground, i, j, null);
			}
		}
	}
}
