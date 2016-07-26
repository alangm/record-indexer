package shared.model;

import importer.*;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;

public class Batch
{
	private Integer id;
	private String filename;
	private File file;
	private Integer status;		// 0 = Not checked out or finished; 1 = checked out; 2 = finished;
	private Integer projectID;
	private ArrayList<Record> records;
	
	
	public Batch(int id, String filename, Object records, int status, int projectID)
	{
		this.id = id;
		this.filename = filename;
		this.file = new File(filename);
		this.status = status;
		this.projectID = projectID;
		this.records = (ArrayList<Record>)records;
	}
	public Batch(Element batchElement)
	{
		records = new ArrayList<Record>();
		DataImporter di = new DataImporter();
		filename = di.getValue((Element)batchElement.getElementsByTagName("file").item(0));
		file = new File(filename);
		status = 0;
		projectID = -1;
		
		Element recordsElement = (Element)batchElement.getElementsByTagName("records").item(0);
		if(recordsElement != null)
		{
			NodeList recordElements = recordsElement.getElementsByTagName("record");
			for(int i=0; i<recordElements.getLength(); i++)
			{
				records.add(new Record((Element)recordElements.item(0)));
			}
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return filename;
	}
	public void setFileName(String filename) {
		this.filename = filename;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public ArrayList<Record> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<Record> records) {
		this.records = records;
	}
}
