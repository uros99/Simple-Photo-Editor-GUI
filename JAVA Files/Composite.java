package image;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Composite {
	public class Operation{
		String name;
		String param="No params";
		Operation(String n){name=n;}
		Operation(String n,String p){
			name=n;
			param=p;
		}
	}
	LinkedList<Operation> operations= new LinkedList();
	MainWindow main;
	
	public Composite(MainWindow frame) {
		main=frame;
	}
	
	public void addOperation(String name, String param) {
		Operation op;
		if(param==null)
			op=new Operation(name);
		else
			op=new Operation(name,param);
		operations.add(op);
	}
	
	public void writeInFile() {
		try {
		File file=new File("Composite.fun");
		file.createNewFile();
		FileWriter writer=new FileWriter(file);
		StringBuilder sb=new StringBuilder();
		sb.append("<CompositeFunction>\n");
		for(Operation op:operations) {
			sb.append("\t<function>").append(op.name).append("</function>\n");
			sb.append("\t\t<param>").append(op.param).append("</param>\n");
		}
		sb.append("</CompositeFunction>");
		writer.write(sb.toString());
		writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void removeAllOperations() {
		for(int i=0;i<operations.size();i++) {
			operations.remove(i);
		}
	}
}
