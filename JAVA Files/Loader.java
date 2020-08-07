package image;

import java.io.File;
import java.io.IOException;

public class Loader {
	MainWindow main;
	private void writeSelectionFile() {
		if(main.image.selection!=null)
			main.image.writeSeleciton();
	}
	private void writeCompositeFile() {
		if(main.compOperation.operations.size()>0)
			main.compOperation.writeInFile();
	}
	public Loader(MainWindow frame) {
		main=frame;
	}
	public void callAnotherProject() {
		writeSelectionFile();
		writeCompositeFile();
		
		StringBuilder file=new StringBuilder();
		File filePath = new File("Proba\\Debug\\Proba.exe");
		String aps=filePath.getAbsolutePath();
		file.append(filePath);
		System.out.println(aps);
		for(Layer l:main.image.layers) {
			file.append(" ").append(l.getPath()).append(" ").append(l.getTransparency()).append(" ")
				.append(l.isActive()).append(" ").append(l.isVisible());
		}
		if(main.compOperation.operations.size()>0) {
			File compositePath = new File("Composite.fun");
			String compositeAps = compositePath.getAbsolutePath();
			System.out.println(compositeAps);
			file.append(" ").append(compositeAps);
		}
		if(main.image.selection!=null) {
			File selectionPath = new File("selection.txt");
			String selectionAps = selectionPath.getAbsolutePath();
			System.out.println(selectionPath);
			file.append(" ").append(selectionPath);
		}
		System.out.println(file.toString());
		Runtime runtime=Runtime.getRuntime();
		try {
			Process process = runtime.exec(file.toString());
		//	process.waitFor();
			System.out.println("process ended");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void merge() {
		StringBuilder file=new StringBuilder("Proba\\Debug\\Proba.exe");
		for(Layer l:main.image.layers) {
			file.append(" ").append(l.getPath()).append(" ").append(l.getTransparency()).append(" ")
				.append(l.isActive()).append(" ").append(l.isVisible());
		}
		Runtime runtime=Runtime.getRuntime();
		try {
			Process process = runtime.exec(file.toString());
		//	process.waitFor();
			System.out.println("process ended");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveProject(String path) {
		writeSelectionFile();
		writeCompositeFile();
		
		StringBuilder file=new StringBuilder("Proba\\Debug\\Proba.exe");
		file.append(" save").append(" ").append(path);
		for(Layer l:main.image.layers) {
			file.append(" ").append(l.getPath()).append(" ").append(l.getTransparency()).append(" ")
				.append(l.isActive()).append(" ").append(l.isVisible());
		}
		if(main.compOperation.operations.size()>0) {
			file.append(" ..\\..\\Composite.fun");
		}
		if(main.image.selection!=null) {
			file.append(" ..\\..\\selection.txt");
		}
		Runtime runtime=Runtime.getRuntime();
		try {
			Process process = runtime.exec(file.toString());
		//	process.waitFor();
			System.out.println("process ended");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
