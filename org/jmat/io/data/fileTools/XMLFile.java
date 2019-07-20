package jmat.io.data.fileTools;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import java.io.*;


/**
 * <p>Titre : Java MAtrix Tools</p>
 * <p>Description : </p>
 * <p>Société : IRSN</p>
 * @author Yann RICHET
 * @version 1.0
 */

public class XMLFile {

	public static Element fromFile(File file)  {
		try {
			SAXBuilder b = new SAXBuilder();
			Document d = b.build(file);
			return d.getRootElement();
		} catch (Exception e) {
			System.out.println("File " + file.getName() + " is unreadable.");
			return null;
		}
	}

	public static void toFile(File file,Element e) {
		Document doc = new Document(e);
		XMLOutputter op = new XMLOutputter();

		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			op.output(doc,bw);
			bw.close();
		} catch (IOException ex) {
			System.out.println("File " + file.getName() + " is unwritable.");
		}
	}

}

