package image;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainWindow extends Frame implements Runnable{
	MainImage image;
	Loader loader;
	Thread nit=new Thread();
	Panel imageCentar=new Panel(new BorderLayout());
	Panel buttonCenter=new Panel();
//	Panel operations = new Panel(new GridLayout(20,1));
	private Choice listOfOperations = new Choice();
	Composite compOperation;
	private Dialog createLayer = new Dijalog(this);
	private Dialog transDialog = new TransparencyDijalog(this);
	private Button buttonLayer = new Button("ADD LAYER");
	private int numberOfLayers=0;
	private MenuBar menuBar=new MenuBar();
	private Menu checkBox=new Menu("Active Layers");
	private Menu checkBoxVisible=new Menu("Visible Layers");
	private Menu transparency=new Menu("Transparency");
	private MenuItem menuItemTrans=new MenuItem("Change");
	private Menu showAll= new Menu("Show Layers Transparency");
	private MainWindow curr=this;
	boolean changeFlag=false;
	boolean doOperation=false;
	boolean selectionFlag=false;
//	String whereToSave=null;
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				nit.interrupt();
				dispose();
			}
		});
		listOfOperations.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				String[] sb = {"Log","Abs","ShadesofGray", "BlackandWhite","Median", "Inversion"};
				String nameOfOperation=listOfOperations.getItem(listOfOperations.getSelectedIndex());
				if(nameOfOperation==sb[0] || nameOfOperation==sb[1] || nameOfOperation==sb[2] || nameOfOperation==sb[3]
						|| nameOfOperation==sb[4] || nameOfOperation==sb[5]) {
					compOperation.addOperation(nameOfOperation, null);
				}else {
					OperationDialog op = new OperationDialog(curr,listOfOperations.getItem(listOfOperations.getSelectedIndex()));
					op.setVisible(true);
				}
				doOperation=true;
			}
			
		});
		buttonLayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				createLayer.setVisible(true);
			}
			
		});
		menuItemTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				transDialog.setVisible(true);
			}
			
		});
	}
	private void addArithmeticOperations() {
		listOfOperations.add("Operation");
		listOfOperations.add("Add");
		listOfOperations.add("Sub");
		listOfOperations.add("Mul");
		listOfOperations.add("Abs");
		listOfOperations.add("Div");
		listOfOperations.add("InvSub");
		listOfOperations.add("InvDiv");
		listOfOperations.add("Log");
		listOfOperations.add("Max");
		listOfOperations.add("Min");
		listOfOperations.add("Power");
		listOfOperations.add("Inversion");
		listOfOperations.add("ShadesofGray");
		listOfOperations.add("BlackandWhite");
		listOfOperations.add("Median");
		
		Button deleteSelection= new Button("DELETE SELECTION");
		deleteSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				image.selection=null;
				selectionFlag=false;
			}
		});
		
		Button fun = new Button("Apply");
		fun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(doOperation) {
					doOperation=false;
					loader.callAnotherProject();
					
					curr.repaint();
					curr.revalidate();
					compOperation.removeAllOperations();
				}
			}
		});
		fun.setSize(10,10);
		Panel east=new Panel(new GridLayout(20,1));
		east.add(deleteSelection);
		Panel pick=new Panel();
		pick.add(listOfOperations);
		pick.add(fun);
		east.add(pick);
		east.add(deleteSelection);
	//	operations.add(pick);
		super.add(BorderLayout.EAST,east);
	}
	private void addMenu() {
		Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 10);
		menuBar.setFont(font); setMenuBar(menuBar);
		menuBar.add(checkBox);
		menuBar.add(checkBoxVisible);
		transparency.add(menuItemTrans);
		menuBar.add(transparency);
		menuBar.add(showAll);
		Menu project=new Menu("Project");
		MenuItem saveProject= new MenuItem("Save Project");
		saveProject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveDialog save=new SaveDialog(curr);
				save.setVisible(true);
			}
		});
		project.add(saveProject);
		project.addSeparator();
		MenuItem openProject = new MenuItem("Open Project");
		openProject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReadDialog read = new ReadDialog(curr);
				read.setVisible(true);
			}
		});
		project.add(openProject);
		menuBar.add(project);
	}
	public void azurirajMenu() {
		for(int i=numberOfLayers; i<image.layers.size();i++) {
			CheckboxMenuItem checkBoxItem = new CheckboxMenuItem("Layer "+(i+1),image.layers.get(i).isActive());
			checkBox.add(checkBoxItem);
			checkBox.addSeparator();
			checkBoxItem.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					String pattern="Layer\\s(\\d)";
					String tmp=checkBoxItem.getLabel();
					Pattern p=Pattern.compile(pattern);
					Matcher m=p.matcher(tmp);
					if(m.find()) {
						if(checkBoxItem.getState()) {
							image.layers.get((Integer.parseInt(m.group(1)))-1).setActive();
						}
						else {
							image.layers.get((Integer.parseInt(m.group(1)))-1).resetActive();
						}
					}
				}
			});
			CheckboxMenuItem checkBoxVisi = new CheckboxMenuItem("Layer "+(i+1),image.layers.get(i).isVisible());
			checkBoxVisible.add(checkBoxVisi);
			checkBoxVisible.addSeparator();
			checkBoxVisi.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					String pattern="Layer\\s(\\d)";
					String tmp=checkBoxVisi.getLabel();
					Pattern p=Pattern.compile(pattern);
					Matcher m=p.matcher(tmp);
					if(m.find()) {
						if(checkBoxVisi.getState()) {
							image.layers.get((Integer.parseInt(m.group(1)))-1).setVisible();
						}
						else {
							image.layers.get((Integer.parseInt(m.group(1)))-1).resetVisible();
						}
					}
				}
			});
			MenuItem tmpMenu=new MenuItem("Layer: "+(i+1)+" "+image.layers.get(i).getTransparency());
			showAll.add(tmpMenu);
			showAll.addSeparator();
		}
		numberOfLayers=image.layers.size();
	}
	public void azurirajTransparency() {
		showAll.removeAll();
		for(int i=0;i<image.layers.size();i++) {
			MenuItem tmpMenu=new MenuItem("Layer: "+(i+1)+" "+image.layers.get(i).getTransparency());
			showAll.add(tmpMenu);
			showAll.addSeparator();
		}
	}
	public MainWindow() {
		super("Photo Editor");
		setVisible(true);
		setSize(1000,750);
		setResizable(true);
		setBackground(Color.LIGHT_GRAY);
		addArithmeticOperations();
		addListeners();
		this.image = new MainImage();
		this.compOperation = new Composite(this);
		this.loader=new Loader(this);
		imageCentar.add(BorderLayout.CENTER,image);
		add(BorderLayout.CENTER,imageCentar);
		buttonLayer.setVisible(true);
		buttonLayer.setSize(50, 20);
		buttonCenter.add(BorderLayout.WEST,buttonLayer);
		Button buttonMerge = new Button("MERGE LAYERS");
		buttonMerge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loader.merge();
			}
		});
		Button select = new Button("ADD SELECTION");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!selectionFlag) {
					selectionFlag=true;
					SelectionDialog s=new SelectionDialog(curr);
					s.setVisible(true);
				}
			}
		});
		buttonCenter.add(BorderLayout.CENTER,buttonMerge);
		buttonCenter.add(BorderLayout.EAST,select);
		add(BorderLayout.SOUTH,buttonCenter);
		addMenu();
		nit.start();
	}
	public void addLayer(Layer layer) {
		image.addLayer(layer);
		imageCentar.setSize(image.getWidth(),image.getHeight());
		setSize(image.getWidth()+150,image.getHeight()+100);
		azurirajMenu();
		repaint();
	}
	
	@Override
	public void run() {
		while(!nit.isInterrupted()) {
			repaint();
			revalidate();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		if(image!=null) {
			if(image.operatedImage!=null) {
				image.operatedImage.readImage();
			}
			if(image.layers.size()>0)
				image.layers.get(0).readImage();
			image.repaint();
			image.revalidate();
		}
	}
	
	public static void main(String[] argv) {
		new MainWindow();
	}
	
}
