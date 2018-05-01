package com.dgx;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public class JPLeftBar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JPLeftBar() {
		this.setSize(48, this.getHeight());
	}
	
	

	public JPLeftBar(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}



	public JPLeftBar(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}



	public JPLeftBar(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}



//	@Override
//	public void paint(Graphics g) {
//		this.setSize(48, this.getHeight());
//		g.setColor(new Color(233, 165, 212));
//		g.fillRect(0, 0, this.getWidth(), this.getHeight());
//	}
}
