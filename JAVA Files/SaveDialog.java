package image;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SaveDialog extends Dialog {
	MainWindow main;
	TextField path=new TextField("Enter path");
	String pathFile;
	boolean flag=false;
	Button doneButton = new Button("OK");
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
			
		});
		path.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				flag=true;
				pathFile=path.getText();
			}
			
		});
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flag==true) {
					flag=false;
					setVisible(false);
					path.setText("Enter Path");
					main.loader.saveProject(pathFile);
				//	main.whereToSave=pathFile;
				}
			}
		});
	}
	public String getPath() {return pathFile;}
	public SaveDialog(MainWindow frame) {
		super(frame,"Save Project",true);
		main=frame;
		setBounds(100,100,300,100);
		setLayout(new GridLayout(1,2));
		add(path);
		add(doneButton);
		addListeners();
	//	setVisible(true);
	}
}
