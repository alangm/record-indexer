package shared.model;

import importer.*;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;

public class Record
{
	private int id;
	private ArrayList<String> values = new ArrayList<String>();
	
	public Record(){}
	public Record(ArrayList<String> values)
	{
		this.values = values;
	}
	public Record(Element recordElement)
	{
		DataImporter di = new DataImporter();
		Element valuesElement = (Element)recordElement.getElementsByTagName("values").item(0);
		NodeList valueElements = valuesElement.getElementsByTagName("value");
		for(int i=0; i<valueElements.getLength(); i++)
		{
			values.add(valueElements.item(i).getTextContent());		// Yeah...??
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<String> getValues() {
		return values;
	}
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
}
