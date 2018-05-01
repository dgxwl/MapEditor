package com.dgx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPEditArea extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static BufferedImage skyblue = null;
	private static BufferedImage ground = null;
	private static BufferedImage question = null;
	private static BufferedImage brick = null;
	
	static List<BufferedImage> img = new ArrayList<>();
	static {
		try {
			skyblue = ImageIO.read(new File("skyblue.png"));
			ground = ImageIO.read(new File("ground.png"));
			question = ImageIO.read(new File("question.png"));
			brick = ImageIO.read(new File("brick.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		img.add(skyblue);
		img.add(ground);
		img.add(question);
		img.add(brick);
	}
	
	public static BufferedImage getImg(int index) {
		return img.get(index);
	}

	public static List<BufferedImage> getAllImg() {
		return img;
	}

	public JPEditArea() {
		this.setSize(768, 720);
	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < 768; i+=48) {
			for (int j = 0; j < 624; j+=48) {
				g.drawImage(img.get(0), i, j, null);
			}
		}

		for (int i = 0; i < 768; i+=48) {
			for (int j = 624; j < 720; j+=48) {
				g.drawImage(img.get(1), i, j, null);
			}
		}
	}
}
