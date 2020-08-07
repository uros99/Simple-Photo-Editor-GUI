package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Layer {
	private String path;
	private double transparency;
	private boolean active=true;
	private boolean visible=true;
	private BufferedImage bufferImage=null;
	
	public Layer(String path, double transparency, boolean active, boolean visible) {
		super();
		this.path = path;
		this.transparency = transparency;
		this.active = active;
		this.visible = visible;
	}
	
	public void readImage() {
		try {
			bufferImage=ImageIO.read(new File(this.path));
			}catch (IOException e) {
				return;
			}
	}
	public void setActive() {this.active=true;}
	public void resetActive() {this.active=false;}
	
	public void setVisible() {this.visible=true;}
	public void resetVisible() {this.visible=false;}

	public String getPath() {return path;}
	public double getTransparency() {return transparency;}
	public boolean isActive() {return active;}
	public boolean isVisible() {return visible;}
	public void setTransparency(double t) {this.transparency=t;}
	public BufferedImage getBufferedImage() {return bufferImage;}
	public void setBufferedImage(BufferedImage buff) {this.bufferImage=buff;}
}
