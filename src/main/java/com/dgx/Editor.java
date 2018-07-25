package com.dgx;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.thoughtworks.xstream.XStream;

public class Editor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static List<BufferedImage> img = JPEditArea.getAllImg();
	/** 根据预览图key获取大图value */
	private static Map<BufferedImage, BufferedImage> imgMapping = JPEditArea.getAllImgMapping();
	private BufferedImage selected = img.get(0);
	private Set<GameItem> mapSet = new HashSet<>();
	private Map<BufferedImage, Ele> imgToItem = new HashMap<>();
	
	/* 预设物体image */
	private void initImgToItem() {
		imgToItem.put(img.get(0), Ele.SKY);
		imgToItem.put(img.get(1), Ele.GROUND);
		imgToItem.put(img.get(2), Ele.QUESTION);
		imgToItem.put(img.get(3), Ele.BRICK);
		imgToItem.put(img.get(4), Ele.HARDBRICK);
		imgToItem.put(img.get(5), Ele.PIT);
		imgToItem.put(img.get(6), Ele.PIPE);
	}
	
	private void initMenuBar() {
		// 菜单栏
		JMenuBar menuBar = new JMenuBar();
		// 菜单
		JMenu menu = new JMenu("操作");
		// 菜单项
		JMenuItem menuItem1 = new JMenuItem("新建关卡");
		doClickNewMap(menuItem1);

		menu.add(menuItem1);
		menuBar.add(menu);
		this.add(menuBar, BorderLayout.NORTH);
	}
	
	private void initRightBar() {
		//右侧物体栏
		JPanel rightBar = new JPanel();
		//盒式布局
		Box box = Box.createVerticalBox();
		//存入48*48的物体
		for (int i = 0; i < 6; i++) {
			JLabel label = new JLabel(new ImageIcon(img.get(i)));
			doClickLabel(label);
			box.add(label);
		}
		//大件物体根据mapping将预览图存入
		imgMapping.forEach((k, v)->{
			JLabel label = new JLabel(new ImageIcon(k));
			doClickThumbLabel(label);
			box.add(label);
		});
		
		rightBar.add(box);
		this.add(rightBar, BorderLayout.EAST);
	}
	
	private JPEditArea editArea;  //编辑区
	private void initEditArea() {
		editArea = new JPEditArea();
		doClickEditArea(editArea);
		
		this.add(editArea, BorderLayout.CENTER);
	}
	
	private int currentPage = 1;
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
		initRightBar();
		initOffsetBar();
		initEditArea();
	}
	
	/* 新建一个地图文件.mapNum:地图文件编号0,1,2.. */
	private int mapNum = 0;
	private void doClickNewMap(JMenuItem menuItem) {
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mapSet.clear();
				mapNum++;
				Graphics g = editArea.getGraphics();
				editArea.paint(g);
			}
		});
	}
	
	/* 根据当前编辑内容创建地图文件 */
	private void doClickCreate(JButton button) {
		MouseAdapter l = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//把maplist的数据写出到XML文件
				XStream xStream = new XStream();
				xStream.processAnnotations(GameItem.class);
				String xmlStr = xStream.toXML(mapSet);
				
				try (
						FileOutputStream fos = new FileOutputStream("./map"+ mapNum +".xml");
						OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
				){
					osw.write(xmlStr);
					//弹出小窗口提示
					JOptionPane.showMessageDialog(frame, "写出完毕!", "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		};
		button.addMouseListener(l);
	}
	
	/* 清空编辑区当前页面内容 */
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
				editArea.repaint();
			}
		};
		button.addMouseListener(l);
	}
	
	/* 点击物体栏,选中被点击的图 */
	private void doClickLabel(JLabel label) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selected = (BufferedImage) ((ImageIcon) label.getIcon()).getImage();
			}
		};
		label.addMouseListener(l);
	}
	
	/* 点击物体栏,选中被点击的预览图所对应的大件物体 */
	private void doClickThumbLabel(JLabel label) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selected = imgMapping.get((BufferedImage) ((ImageIcon) label.getIcon()).getImage());
			}
		};
		label.addMouseListener(l);
	}
	
	/* 切换页面 */
	private void doClickOffset(int i, JButton button) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (i) {
				case 0:
					if (currentPage > 1) {
						currentPage--;
						jlPage.setText("page: " + currentPage);
						//切换页码重画编辑区
						repaintEditArea();
					}
					break;
				case 1:
					currentPage++;
					jlPage.setText("page: " + currentPage);
					//切换页码重画编辑区
					repaintEditArea();
					break;
				}
			}
		};
		button.addMouseListener(l);
	}
	/* 切换页码重画编辑区 */
	private void repaintEditArea() {
		Graphics g = editArea.getGraphics();
		editArea.paint(g);
		mapSet.forEach((gi) -> {
			if (gi.getWhichPage() == currentPage) {
				//怎么根据item(value)逆向拿到img(key)？
				BufferedImage key = null;
				for (Entry<BufferedImage, Ele> en : imgToItem.entrySet()) {
					if (gi.getElement().equals(en.getValue())) {
						key = en.getKey();
						break;
					}
				}
				g.drawImage(key, gi.getX(), gi.getY(), null);
			}
		});
	}
	
	private void doClickEditArea(JPEditArea editArea) {
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX() / 48 * 48;
				int y = e.getY() / 48 * 48 + 23;
				
				Graphics g = getGraphics();
				g.drawImage(selected, x, y, null);
				GameItem gameItem = new GameItem(currentPage, new Point(x, y-23), imgToItem.get(selected));
				if (mapSet.contains(gameItem)) {
					mapSet.remove(gameItem);
					mapSet.add(gameItem);
				} else {
					mapSet.add(gameItem);
				}
			}
		};
		editArea.addMouseListener(l);
	}
	
	private static JFrame frame;
	public static void main(String[] args) {
		frame = new JFrame("地图编辑");
		frame.setSize(768+64, 23+720+57);  //右侧物体栏64,上方菜单栏23,下方操作栏57
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		Editor editor = new Editor();

		frame.add(editor);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
