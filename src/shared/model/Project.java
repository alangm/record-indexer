package shared.model;

import importer.*;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Project
{
	private int id;
	private String title;
	private int recordsPerImage;
	private int firstYCoord;
	private int recordHeight;
	
	ArrayList<Field> fields = new ArrayList<Field>();
	ArrayList<Batch> images = new ArrayList<Batch>();
	ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
	ArrayList<Integer> imageIDs = new ArrayList<Integer>();
	
	
	public Project()
	{
		id				= -1;
		title			= null;
		recordsPerImage	= -1;
		firstYCoord		= -1;
		recordHeight	= -1;
	}
	@SuppressWarnings("unchecked")
	public Project(int id, String title, int recordsPerImage, int firstYCoord,
			int recordHeight, Object fields, Object images)		// Objects?
	{
		this.id = id;
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
		
		// Problems Here:
		
		this.fieldIDs = (ArrayList<Integer>)fields;		// suppressed warnings...
		this.imageIDs = (ArrayList<Integer>)images;
		
		for(int i=0; i<fieldIDs.size(); i++)
		{
			fieldIDs.add(fieldIDs.get(i));
		}
		for(int i=0; i<imageIDs.size(); i++)
		{
			imageIDs.add(imageIDs.get(i));
		}
	}
	public Project(Element projectElement) throws ParserConfigurationException, SAXException, IOException
	{
		DataImporter di = new DataImporter();
		title = di.getValue((Element)projectElement.getElementsByTagName("title").item(0));
		recordsPerImage = Integer.parseInt(di.getValue((Element)projectElement.getElementsByTagName("recordsperimage").item(0)));
		firstYCoord = Integer.parseInt(di.getValue((Element)projectElement.getElementsByTagName("firstycoord").item(0)));
		recordHeight = Integer.parseInt(di.getValue((Element)projectElement.getElementsByTagName("recordheight").item(0)));
		
		Element fieldsElement = (Element)projectElement.getElementsByTagName("fields").item(0);
		NodeList fieldElements = fieldsElement.getElementsByTagName("field");
		for(int i=0; i<fieldElements.getLength(); i++)
		{
			fields.add(new Field((Element)fieldElements.item(i)));
		}
		
		Element imagesElement = (Element)projectElement.getElementsByTagName("images").item(0);
		NodeList imageElements = imagesElement.getElementsByTagName("image");
		for(int i=0; i<imageElements.getLength(); i++)
		{
			Batch b = new Batch((Element)imageElements.item(i));
			images.add(b);
		}
		
		for(int i=0; i<fields.size(); i++)
		{
			fieldIDs.add(fields.get(i).getId());
		}
		for(int i=0; i<images.size(); i++)
		{
			imageIDs.add(images.get(i).getId());
		}
	}
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public int getRecordsPerImage(){
		return recordsPerImage;
	}
	public void setRecordsPerImage(int recordsPerImage){
		this.recordsPerImage = recordsPerImage;
	}
	public int getFirstYCoord(){
		return firstYCoord;
	}
	public void setFirstYCoord(int firstYCoord){
		this.firstYCoord = firstYCoord;
	}
	public int getRecordHeight(){
		return recordHeight;
	}
	public void setRecordHeight(int recordHeight){
		this.recordHeight = recordHeight;
	}
	public ArrayList<Field> getFields() {
		return fields;
	}
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	public ArrayList<Batch> getImages() {
		return images;
	}
	public void setImages(ArrayList<Batch> images) {
		this.images = images;
	}
	public ArrayList<Integer> getFieldIDs() {
		return fieldIDs;
	}
	public ArrayList<Integer> getImageIDs() {
		return imageIDs;
	}
}
