package image;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Dijalog extends Dialog{
	Label labelPath=new Label();
	TextField textPath = new TextField("Enter path");
	Label labelTransparency = new Label();
	TextField textTransparency = new TextField("Enter transparency");
	Label labelActivity = new Label();
	CheckboxGroup active;
	Checkbox activeBox;
	Checkbox disableBox;
	Label labelVisible = new Label();
	CheckboxGroup visible;
	Checkbox visibleBox;
	Checkbox invisibleBox;
	Button doneButton = new Button("Done");
	Layer layer=null;
	String path;
	MainWindow main;
	double transparency;
	boolean activeFlag;
	boolean visibleFlag;
	boolean[] flags;
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		textPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				path=textPath.getText();
				flags[0]=true;
			}
		});
		textTransparency.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transparency=Double.parseDouble(textTransparency.getText());
				flags[1]=true;
			}
		});
		activeBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(activeBox.getState())
					activeFlag=true;
				else
					activeFlag=false;
				flags[2]=true;
			}
		});
		disableBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(disableBox.getState())
					activeFlag=false;
				else
					activeFlag=true;
				flags[2]=true;
			}
		});
		visibleBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(visibleBox.getState())
					visibleFlag=true;
				else
					visibleFlag=false;
				flags[3]=true;
			}
		});
		invisibleBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(invisibleBox.getState())
					visibleFlag=false;
				else
					visibleFlag=true;
				flags[3]=true;
			}
		});
		doneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(flags[0] && flags[1] && flags[2] && flags[3]) {
					layer=new Layer(path,transparency,activeFlag,visibleFlag);
					layer.readImage();
					addLayer();
					textPath.setText("Enter path");
					textTransparency.setText("Enter transparency");
					flags=new boolean[]{false,false,false,false};
				}
			}
		});
		
	}
	public void addLayer() {
		main.addLayer(layer);
		main.changeFlag=true;
		setVisible(false);
	}
	public Dijalog(MainWindow frame) {
		super(frame,"Create Layer", true);
		main=frame;
		setLayout(new GridLayout(9,1));
		setBounds(100,100,300,500);
		Font font = new Font("Arial", Font.BOLD,10);
		labelPath.setFont(font);
		labelPath.setText("Type the path of image");
		add(labelPath);
		add(textPath);
		labelTransparency.setFont(font);
		labelTransparency.setText("Type transparency of image");
		add(labelTransparency);
		add(textTransparency);
		labelActivity.setFont(font);
		labelActivity.setText("Is layer active?");
		add(labelActivity);
		active=new CheckboxGroup();
		activeBox = new Checkbox("Yes", false, active);
		disableBox = new Checkbox("No",false,active);
		Panel activeLayers = new Panel();
		activeLayers.add(activeBox);
		activeLayers.add(disableBox);
		add(activeLayers);
		labelVisible.setFont(font);
		labelVisible.setText("Is layer visible?");
		add(labelVisible);
		visible=new CheckboxGroup();
		visibleBox = new Checkbox("Yes", false, visible);
		invisibleBox = new Checkbox("No",false,visible);
		Panel visibleLayers = new Panel();
		visibleLayers.add(visibleBox);
		visibleLayers.add(invisibleBox);
		add(visibleLayers);
		doneButton.setFont(font);
		doneButton.setSize(50,20);
	//	flags=new boolean[4];
		flags=new boolean[]{false,false,false,false};
		addListeners();
		add(doneButton);
	}
}
