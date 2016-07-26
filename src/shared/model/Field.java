package shared.model;

import importer.*;

import java.io.*;
import java.sql.Array;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Field
{
	private int id;
	private String title;
	private Integer number;
	private Integer xcoord;
	private Integer width;
	private File helphtmlFile;
	private String helphtml;
	private File knownDataFile;
	private ArrayList<String> knownData = new ArrayList<String>();
	
	
	public Field()
	{
		title = null;
		xcoord = -1;
		width = -1;
		helphtmlFile = null;
		helphtml = null;
		knownDataFile = null;
		knownData = null;
	}
	public Field(String title, int xcoord, int width, String helphtml, Array knownData)
	{
		this.title = title;
		this.xcoord = xcoord;
		this.width = width;
		this.helphtml = helphtml;
		this.knownData = (ArrayList<String>)knownData;
	}
	public Field(int xcoord, int width, String helphtmlFileName,
				 String knownDataFileName) throws ParserConfigurationException, SAXException, IOException
	{
		try
		{
			this.xcoord = xcoord;
			this.width = width;
			
			helphtmlFile = new File("records"+File.separator+"fieldhelp"+
									File.separator+helphtmlFileName);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(helphtmlFile);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			title = root.getElementsByTagName("title").item(0).getTextContent();
			helphtml = root.getElementsByTagName("p").item(0).getTextContent();
			
			knownDataFile = new File("records"+File.separator+"knowndata"+
									 File.separator+knownDataFileName);
			Scanner scanner = new Scanner(knownDataFile);
			scanner.useDelimiter(",(\\s)*");
			while(scanner.hasNext())
			{
				String str = scanner.next();
				knownData.add(str);
			}
			scanner.close();
			
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File Not Found!");
		} catch (ParserConfigurationException e) {
			throw new ParserConfigurationException("Could not parse htmlhelp file!");
		}
	}
	public Field(Element fieldElement) throws ParserConfigurationException, SAXException, IOException
	{	
		try
		{
			DataImporter di = new DataImporter();
			title = di.getValue((Element)fieldElement.getElementsByTagName("title").item(0));
			xcoord = Integer.parseInt(di.getValue((Element)fieldElement.getElementsByTagName("xcoord").item(0)));
			width = Integer.parseInt(di.getValue((Element)fieldElement.getElementsByTagName("width").item(0)));
			
			helphtmlFile = new File( "records"+File.separator+di.getValue((Element)fieldElement.getElementsByTagName("helphtml").item(0)) );
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(helphtmlFile);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			helphtml = root.getElementsByTagName("p").item(0).getTextContent();
			
			NodeList knowndataElements = fieldElement.getElementsByTagName("knowndata");
			if(knowndataElements.getLength() > 0)
			{
				knownDataFile = new File( di.getValue((Element)knowndataElements.item(0)) );
				Scanner knownDataScanner = new Scanner(new BufferedReader(new FileReader("records"+File.separator+knownDataFile)));
				while(knownDataScanner.hasNext())
				{
					knownData.add(knownDataScanner.next());
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Integer getXcoord() {
		return xcoord;
	}
	public void setXcoord(Integer xcoord) {
		this.xcoord = xcoord;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public File getHelphtmlFile() {
		return helphtmlFile;
	}
	public void setHelphtmlFile(File helphtmlFile) {
		this.helphtmlFile = helphtmlFile;
	}
	public String getHelphtml() {
		return helphtml;
	}
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}
	public File getKnownDataFile() {
		return knownDataFile;
	}
	public void setKnownDataFile(File knownDataFile) {
		this.knownDataFile = knownDataFile;
	}
	public ArrayList<String> getKnownData() {
		return knownData;
	}
	public void setKnownData(ArrayList<String> knownData) {
		this.knownData = knownData;
	}
}
