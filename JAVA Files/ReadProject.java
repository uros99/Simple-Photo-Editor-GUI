package image;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadProject {
	MainWindow main;
	File file;
	BufferedReader br;
	String text;
	public ReadProject(MainWindow frame) {
		main=frame;
	}
	public void readProject(String path) {
		try {
		file=new File(path);
		br = new BufferedReader(new FileReader(file));
		String begin="<MyProject>";
		String end="</MyProject>";
		String header="<header>";
		String endOfHeader="</header>";
		String selection="\t<selection>";
		String beginOfLayers="\t<arrayofLayers>";

		text=br.readLine();
		Pattern p = Pattern.compile(begin);
		Matcher m=p.matcher(text);
		if(m.find()) {
			text=br.readLine();
			Pattern p1=p.compile(header);
			m=p1.matcher(text);
			if(m.find()) {
				text=br.readLine();
				Pattern p2 = p.compile(selection);
				m=p2.matcher(text);
				if(m.find()) {
					readSelection();
					text=br.readLine();
				}
				Pattern p3=p.compile(beginOfLayers);
				m=p3.matcher(text);
				if(m.find()) {
					readLayers();
					text=br.readLine();
				}
				Pattern p4=p.compile(endOfHeader);
				m=p4.matcher(text);
				m.find();
				Pattern p5=p.compile(end);
				m=p5.matcher(text);
				m.find();
				main.loader.merge();
				main.azurirajMenu();
			}
		}
	//	String 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readLayers() throws IOException {
		Pattern path=Pattern.compile("\t\t\t<path>([^<]*)</path>");
		Pattern opacity=Pattern.compile("\t\t\t<transparency>(\\d+)</transparency>");
		Pattern width=Pattern.compile("\t\t\t<width>(\\d+)</width>");
		Pattern height=Pattern.compile("\t\t\t<height>(\\d+)</height>");
		Pattern visibleFlag=Pattern.compile("\t\t\t<visibleFlag>(\\d)</visibleFlag>");
		Pattern activeFlag=Pattern.compile("\t\t\t<activeFlag>(\\d)</activeFlag>");
		Pattern endofLayers=Pattern.compile("\t</arrayofLayers>");
		Pattern layer=Pattern.compile("\t\t<layer>");
		Pattern end= Pattern.compile("\t\t</layer>");
		
		while((text=br.readLine())!=null){
			Matcher m = endofLayers.matcher(text);
			if(m.find()) return;
			m = layer.matcher(text);
			if(m.matches()) {
					text=br.readLine();
					m=path.matcher(text);
					if(m.matches()) {
					String fname=m.group(1);
					text=br.readLine();
					m=opacity.matcher(text);
					if(m.matches()) {
						int transparency = Integer.parseInt(m.group(1));
						text=br.readLine();
						m=visibleFlag.matcher(text);
						if(m.matches()) {
							int visible=Integer.parseInt(m.group(1));
							text=br.readLine();
							m=activeFlag.matcher(text);
							if(m.matches()) {
								int active=Integer.parseInt(m.group(1));
								Layer l = new Layer(fname,transparency,true,true);
								if(visible==0) l.resetVisible();
								if(active==0) l.resetActive();
								l.readImage();
								main.image.addLayer(l);
								text= br.readLine();	
							}
						}
					}
				}
			}
		}
	}
	private void readSelection() throws IOException {
		Pattern name=Pattern.compile("\t\t<name>([^<]*)</name>");
		Pattern endofSelection=Pattern.compile("\t</selection>");
		Pattern rectPosition=Pattern.compile("\t\t<RectanglePosition>(\\d+),(\\d+)</RectanglePosition>.*");
		Pattern rectDimension=Pattern.compile("\t\t<RectangleDimension>(\\d+),(\\d+)</RectangleDimension>.*");
		text= br.readLine();
		Matcher m = name.matcher(text);
		if(m.find()) {
			main.image.selection = new Selection(m.group(1));
			main.selectionFlag=true;
			while((text=br.readLine()) !=null) {
				m=endofSelection.matcher(text);
				if(m.matches()) return;
				m=rectPosition.matcher(text);
				if(m.matches()) {
					int x=Integer.parseInt(m.group(1));
					int y=Integer.parseInt(m.group(2));
					text=br.readLine();
					m=rectDimension.matcher(text);
					if(m.matches()){
						int width=Integer.parseInt(m.group(1));
						int height=Integer.parseInt(m.group(2));
						Rectangle r=new Rectangle(x,y,width,height);
						main.image.addRectInSelection(r);
					}
				}
			}
		}
	}
}
