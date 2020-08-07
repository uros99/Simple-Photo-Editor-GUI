package image;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OperationDialog extends Dialog{
	MainWindow frame;
	Label name;
	Label nameParam = new Label("Enter parametar");
	TextField param = new TextField();
	Button doneButton = new Button("OK");
	boolean flag = false;
	String paramString;
	
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
			
		});
		param.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				flag=true;
				paramString=param.getText();	
			}
			
		});
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(flag) {
					setVisible(false);
					frame.compOperation.addOperation(name.getText(), paramString);
				}
			}
			
		});
	}
	public OperationDialog(MainWindow frame, String operation) {
		super(frame,"Add Param",true);
		setLayout(new GridLayout(4,1));
		setBounds(100,100,200,400);
		name=new Label(operation);
		this.frame=frame;
		add(name);
		add(nameParam);
		add(param);
		add(doneButton);
		addListeners();
	}
}
