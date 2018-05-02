package com.dgx;

public class LocAndItem {

	private Point point;
	private Ele item;
	
	public LocAndItem() {
		
	}

	public LocAndItem(Point point, Ele item) {
		super();
		this.point = point;
		this.item = item;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Ele getItem() {
		return item;
	}

	public void setItem(Ele item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "LocAndItem [point=" + point + ", item=" + item + "]";
	}
	
	
}
