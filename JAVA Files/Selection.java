package image;

import java.awt.Rectangle;
import java.util.LinkedList;

public class Selection {
	private String name;
	LinkedList<Rectangle> rectangles=new LinkedList();
	private boolean activeSelection=false;
	
	public Selection(String name) {
		this.name=name;
		
	}
	public Selection(String name, LinkedList<Rectangle> rectangle, boolean activeSelection) {
		super();
		this.name = name;
		this.rectangles = rectangle;
		this.activeSelection = activeSelection;
	}

	public void addRect(Rectangle rect) {
		rectangles.add(rect);
		System.out.println("Uca");
	}
	
	public String getName() {return name;}
	public boolean isActive() {return activeSelection;}
	public LinkedList<Rectangle> getSeleciton(){return rectangles; }
	
}
