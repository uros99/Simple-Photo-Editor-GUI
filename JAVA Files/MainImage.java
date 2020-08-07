package image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JLabel;

public class MainImage extends Panel {
	LinkedList<Layer> layers=new LinkedList();
	Layer operatedImage=null;
	Selection selection=null;
	private int widthMax=0;
	private int heightMax=0;
	private int x;
	private int y;
	private int width;
	private int height;
	public MainImage() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				x=e.getX();
				y=e.getY();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				width=Math.abs(e.getX()-x);
				height=Math.abs(e.getY()-y);
				if(e.getX()-x<0)x=e.getX();
				if(e.getY()-y<0)y=e.getY();
				Rectangle r=new Rectangle(x,y,width,height);
				addRectInSelection(r);
			}
		});
	}
	public MainImage(LinkedList<Layer> layers) {
		super();
		this.layers = layers;
		selection=null;
	}
	
	public MainImage(LinkedList<Layer> layers, Selection selection) {
		super();
		this.layers=layers;
		this.selection=selection;
	}
	
	public void addRectInSelection(Rectangle rect) {
		if(selection!=null) {
			selection.addRect(rect);
			repaint();
			revalidate();
		}
	}
	
	public void addLayer(Layer layer) {
		layers.add(layer);
	//	layer.readImage();
		if(layer.getBufferedImage().getWidth()>widthMax) {
			widthMax=layer.getBufferedImage().getWidth();
		}
		if(layer.getBufferedImage().getHeight()>heightMax) {
			heightMax=layer.getBufferedImage().getHeight();
		}
		if(layers.size()==1) {
			operatedImage= new Layer("image.bmp",layers.get(0).getTransparency(),layers.get(0).isActive(),layers.get(0).isVisible());
			repaint();
		}
	}
	
	public void writeSeleciton() {
		if(selection==null)return;
		try {
			File file=new File("selection.txt");
			file.createNewFile();
			FileWriter writer=new FileWriter(file);
			StringBuilder sb=new StringBuilder("<SelectionFile>\n");
			sb.append("\t<SelectionName>");
			sb.append(selection.getName());
			sb.append("</SelectionName>\n");
			if(selection.getSeleciton().size()>0) {
				for(Rectangle rect:selection.getSeleciton()) {
					sb.append("\t<Rectangle>\n");
					sb.append("\t\t<SelectionPositionX>"+rect.x+"</SelectionPositionX>\n");
					sb.append("\t\t<SelectionPositionY>"+rect.y+"</SelectionPositionY>\n");
					sb.append("\t\t<SelectionWidth>"+rect.width+"</SelectionWidth>\n");
					sb.append("\t\t<SelectionHeight>"+rect.height+"</SelectionHeight>\n");
					sb.append("\t</Rectangle>\n");
				}
			}
			sb.append("</SelectionFile>");
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void paint(Graphics g) {
		if(operatedImage!=null)
			if(operatedImage.getBufferedImage()!=null) {
				g.drawImage(operatedImage.getBufferedImage(), 0, 0, null);
				return;
			}
		if(layers.size()>0){
			g.drawImage(layers.get(0).getBufferedImage(), 0, 0, null);
		}
		if(selection!=null) {
			g.setColor(Color.BLACK);
			int i=0;
			for(Rectangle r:selection.rectangles) {
				g.drawRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
				System.out.println("Rect "+ i++);
			}
		}
	}
	public int getWidth() {
		return widthMax;
	}
	public int getHeight() {
		return heightMax;
	}
}
