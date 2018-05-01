package com.dgx;

import java.awt.BorderLayout;
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
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Editor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	static List<BufferedImage> img = JPEditArea.getAllImg();
	
	Map<Point, BufferedImage> optionsMap = new HashMap<>();
	BufferedImage selected = img.get(1);
	
	private void initMenuBar() {
		// 菜单栏
		JMenuBar menuBar = new JMenuBar();
		// 菜单
		JMenu menu = new JMenu("操作");
		// 菜单项
		JMenuItem menuItem1 = new JMenuItem("新建");

		menu.add(menuItem1);
		menuBar.add(menu);
		this.add(menuBar, BorderLayout.NORTH);
	}
	
	private void initLeftBar() {
		JPLeftBar leftBar = new JPLeftBar();
		Box box = Box.createVerticalBox();
		
		for (int i = 0; i < img.size(); i++) {
			JLabel label = new JLabel(new ImageIcon(img.get(i)));
			doClickLabel(label);
			box.add(label);
		}
		
		leftBar.add(box);
		this.add(leftBar, BorderLayout.EAST);
	}
	
	private void initEditArea() {
		JPEditArea editArea = new JPEditArea();
		doClickEditArea(editArea);
		
		this.add(editArea, BorderLayout.CENTER);
	}
	
	private int currentPage = 0;
	private JLabel jlPage;
	private void initOffsetBar() {
		JPanel offsetBar = new JPanel();
		JButton jbLeft = new JButton("pre");
		jlPage = new JLabel("page");
		JButton jbRight = new JButton("next");
		JButton jbClear = new JButton("clear");
		offsetBar.add(jbLeft);
		jlPage.setText("page: " + currentPage);
		offsetBar.add(jlPage);
		offsetBar.add(jbRight);
		offsetBar.add(jbClear);
		
		doClickOffset(0, jbLeft);
		doClickOffset(1, jbRight);
		
		this.add(offsetBar, BorderLayout.SOUTH);
	}
	
	public Editor() {
//		this.setSize(768, 720);
		this.setLayout(new BorderLayout());
		
		initMenuBar();
		initLeftBar();
		initEditArea();
		initOffsetBar();
	}
	
	private void doClickLabel(JLabel label) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selected = (BufferedImage) ((ImageIcon) label.getIcon()).getImage();
			}
		};
		label.addMouseListener(l);
	}
	
	private void doClickOffset(int i, JButton button) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (i) {
				case 0:
					if (currentPage > 0) {
						currentPage--;
						jlPage.setText("page: " + currentPage);
					}
					break;

				case 1:
					currentPage++;
					jlPage.setText("page: " + currentPage);
					break;
				}
			}
		};
		button.addMouseListener(l);
	}
	
	private void doClickEditArea(JPEditArea editArea) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX() / 48 * 48;
				int y = e.getY() / 48 * 48 + 23;
				if (selected == null) {
					selected = optionsMap.get(new Point(x, y));
					System.out.println(selected);
				} else {
					Graphics g = getGraphics();
					g.drawImage(selected, x, y, null);
				}
			}
		};
		editArea.addMouseListener(l);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("地图编辑");
		frame.setSize(832, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		Editor editor = new Editor();

		frame.add(editor);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
