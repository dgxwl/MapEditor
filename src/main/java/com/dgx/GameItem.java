package com.dgx;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 描述一个游戏物体在第几页/坐标/是什么物体
 * @author Administrator
 *
 */
@XStreamAlias("item")
public class GameItem {

	private int whichPage;
	private Point point;
	private Ele element;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		result = prime * result + whichPage;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameItem other = (GameItem) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		if (whichPage != other.whichPage)
			return false;
		return true;
	}

	public GameItem() {
		
	}

	public GameItem(int whichPage, Point point, Ele element) {
		this.whichPage = whichPage;
		this.point = point;
		this.element = element;
	}

	public int getWhichPage() {
		return whichPage;
	}

	public void setWhichPage(int whichPage) {
		this.whichPage = whichPage;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public int getX() {
		return point.getX();
	}
	
	public void setX(int x) {
		this.point.setX(x);;
	}
	
	public int getY() {
		return point.getY();
	}

	public void setY(int y) {
		this.point.setY(y);;
	}

	public Ele getElement() {
		return element;
	}

	public void setElement(Ele element) {
		this.element = element;
	}

	@Override
	public String toString() {
		return "page:" + whichPage + ", point:" + point + ", element:" + element;
	}
	
	
}
