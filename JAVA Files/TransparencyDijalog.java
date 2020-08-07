package image;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TransparencyDijalog extends Dialog{
	private MainWindow main;
	private Label labelLayer=new Label();
	private TextField textLayer = new TextField("Enter layer's number");
	private Label labelTransparency = new Label();
	private TextField textTransparency = new TextField("Enter new transparency");
	private Button doneButton = new Button("DONE");
	private int index;
	private double transparency;
	private boolean flags[];
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		textLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				index=Integer.parseInt(textLayer.getText());
				flags[0]=true;
			}
		});
		textTransparency.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				transparency=Double.parseDouble(textTransparency.getText());
				flags[1]=true;
			}
		});
		doneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(flags[0] && flags[1]) {
					changeTransparency();
					textLayer.setText("Enter layer's number");
					textTransparency.setText("Enter new transparency");
					flags=new boolean[]{false,false};
				}
			}
		});
	}
	private void changeTransparency() {
		main.image.layers.get(index-1).setTransparency(transparency);
		main.azurirajTransparency();
		setVisible(false);
	}
	public TransparencyDijalog(MainWindow mainWindow) {
		super(mainWindow,"Set transparency", true);
		main=mainWindow;
		setLayout(new GridLayout(5,1));
		setBounds(100,100,300,500);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		Font font = new Font("Arial", Font.BOLD,10);
		labelLayer.setFont(font);
		labelLayer.setText("Layer's Number");
		add(labelLayer);
		add(textLayer);
		labelTransparency.setFont(font);
		labelTransparency.setText("Layer's Transparency");
		add(labelTransparency);
		add(textTransparency);
		doneButton.setFont(font);
		add(doneButton);
		flags=new boolean[] {false,false};
		addListeners();
	}

}
