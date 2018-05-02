package com.dgx;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.thoughtworks.xstream.XStream;

public class Editor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static List<BufferedImage> img = JPEditArea.getAllImg();
	private BufferedImage selected = img.get(0);
	private Set<GameItem> mapSet = new HashSet<>();
	private Map<BufferedImage, Ele> imgToItem = new HashMap<>();
	
	private void initImgToItem() {
		imgToItem.put(img.get(0), Ele.SKY);
		imgToItem.put(img.get(1), Ele.GROUND);
		imgToItem.put(img.get(2), Ele.QUESTION);
		imgToItem.put(img.get(3), Ele.BRICK);
	}
	
	private void initMenuBar() {
		// 菜单栏
		JMenuBar menuBar = new JMenuBar();
		// 菜单
		JMenu menu = new JMenu("操作");
		// 菜单项
		JMenuItem menuItem1 = new JMenuItem("新建关卡");

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
		JButton jbCreate = new JButton("create!");
		offsetBar.add(jbCreate);
		
		doClickOffset(0, jbLeft);
		doClickOffset(1, jbRight);
		doClickClear(jbClear);
		doClickCreate(jbCreate);
		
		this.add(offsetBar, BorderLayout.SOUTH);
	}
	
	public Editor() {
		initImgToItem();
		this.setLayout(new BorderLayout());
		
		initMenuBar();
		initLeftBar();
		initEditArea();
		initOffsetBar();
	}
	
	private void doClickCreate(JButton button) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//先不管切换page时改变显示内容的问题，
				//把maplist的数据写出到XML文件
				XStream xStream = new XStream();
				xStream.processAnnotations(GameItem.class);
				String xmlStr = xStream.toXML(mapSet);
				System.out.println(xmlStr);
				try (
						FileOutputStream fos = new FileOutputStream("./map.xml");
						OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
				){
					osw.write(xmlStr);
					System.out.println("写出完毕！");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		};
		button.addMouseListener(l);
	}
	
	private void doClickClear(JButton button) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Iterator<GameItem> it = mapSet.iterator();
				while (it.hasNext()) {
					GameItem g = it.next();
					if (g.getWhichPage() == currentPage) {
						it.remove();
					}
				}
//				System.out.println(mapList);
			}
		};
		button.addMouseListener(l);
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
				
				Graphics g = getGraphics();
				g.drawImage(selected, x, y, null);
				mapSet.add(new GameItem(currentPage, new Point(x, y-23), imgToItem.get(selected)));
//				System.out.println(mapList);
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
