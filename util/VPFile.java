package vporel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VPFile {
	
	public static String read(File file) throws FileNotFoundException{
        Scanner scanner = new Scanner(file, "utf-8"); // utf-8 : encodage des caractères
		String data = "";
        if(scanner.hasNextLine()) //Test pour la récupération de la première ligne
        	while(scanner.hasNextLine()) 
	        	data += scanner.nextLine();
	    scanner.close();	
        
		return data;
	}
	
	public static String read(String filePath) throws FileNotFoundException{
        return read(new File(filePath));
	}
	
	public static void write(File file, String data) {
		
	}
}
