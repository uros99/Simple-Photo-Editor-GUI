package image;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SelectionDialog extends Dialog{
	private MainWindow main;
	TextField name=new TextField("Write name");
	String nameSelect;
	Button doneButton = new Button("OK");
	boolean flag=false;
	private void addListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
			
		});
		name.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameSelect=name.getText();
				flag=true;
			}
		});
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flag) {
					flag=false;
					addName();
				}
			}
		});
	}
	
	public void addName() {
		main.image.selection=new Selection(nameSelect);
	//	main.image.add(main.image.selection);
		super.setVisible(false);
	}
	public SelectionDialog(MainWindow frame) {
		super(frame,"Selection",true);
		main=frame;
		setBounds(100,100,300,100);
		setLayout(new FlowLayout());
		add(name);
		add(doneButton);
		addListener();
	}
}
