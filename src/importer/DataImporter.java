package importer;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

import org.apache.commons.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.rmi.ServerException;
import java.sql.SQLException;

import shared.model.*;
import server.database.*;

public class DataImporter
{	
	static Database db = new Database();
	
	public static void main(String[] args)
	{
		try
		{	
			Database.initialize();
			db.startTransaction();
			
			copyFiles(args[0]);
			importData(args[0]);
			
			db.endTransaction(true);
		}
		catch (ParserConfigurationException e){
			System.out.println("Could not import data! -- ParserConfigurationException");
			e.printStackTrace();
			return;
		} catch (SAXException e) {
			System.out.println("Could not import data! -- SAXException");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println("Could not import data! -- IOException");
			e.printStackTrace();
			return;
		} catch (SQLException e) {
			System.out.println("Could not import data! -- SQLException");
			e.printStackTrace();
			return;
		}
		
	}
	
	public static void copyFiles(String filename)
	{
		try
		{
			File file = new File(filename);
			File dest = new File("records");
			
			File emptydb = new File("database" + File.separator + "empty" + File.separator + "RecordIndexerDatabase.sqlite");
			File currentdb = new File("database" + File.separator + "RecordIndexerDatabase.sqlite");
			
			
			//(**APACHE**)
		 
			//	We make sure that the directory we are copying is not the the destination
			//	directory.  Otherwise, we delete the directories we are about to copy.
			if(!file.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
			{
				FileUtils.deleteDirectory(dest);
			}	
			//	Copy the directories (recursively) from our source to our destination.
			FileUtils.copyDirectory(file.getParentFile(), dest);
			
			//	Overwrite my existing *.sqlite database with an empty one.  Now, my
			//	database is completely empty and ready to load with data.
			FileUtils.copyFile(emptydb, currentdb);
			
			// (**APACHE**)
		}
		catch (IOException e) {
			System.out.println("Could not copy files! -- IOException");
			return;
		}
	}
	
	/*public static void clearDatabase() throws ServerException, SQLException
	{
		UserAccess userDAO = new UserAccess(db);
		ProjectAccess projectDAO = new ProjectAccess(db);
		BatchAccess batchDAO = new BatchAccess(db);
		FieldAccess fieldDAO = new FieldAccess(db);
		RecordAccess recordDAO = new RecordAccess(db);
		
		userDAO.clearAll();
		projectDAO.clearAll();
		batchDAO.clearAll();
		fieldDAO.clearAll();
		recordDAO.clearAll();
	}*/
	
	public static void importData(String xmlFileName) throws ParserConfigurationException, SAXException, IOException, SQLException
	{
		File xmlFile = new File(xmlFileName); 
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		
		Element root = doc.getDocumentElement();
		IndexerData indexerData = new IndexerData(root);
		
		for(int i=0; i<indexerData.getUsers().size(); i++)
		{
			User u = indexerData.getUsers().get(i);
			indexerData.setUserAtIndex(i, importUser(u));
		}
		for(int i=0; i<indexerData.getProjects().size(); i++)
		{
			Project currentProject = indexerData.getProjects().get(i);
			currentProject = importProject(currentProject);
			for(int j=0; j<currentProject.getFields().size(); j++)
			{
				Field currentField = currentProject.getFields().get(i);
				currentProject.getFields().set(i, importField(currentField));
			}
			for(int j=0; j<currentProject.getImages().size(); j++)
			{
				Batch currentBatch = currentProject.getImages().get(i);
				currentBatch.setProjectID(currentProject.getId());		// update the batch's Project ID
				currentBatch = importBatch(currentBatch);
				for(int k=0; k<currentBatch.getRecords().size(); k++)
				{
					Record currentRecord = currentBatch.getRecords().get(k);
					importRecord(currentRecord);
				}
			}
		}
	}
	
	public static ArrayList<Element> getChildElements(Node node)
	{
		ArrayList<Element> result = new ArrayList<Element>();

		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE)
			{
				result.add((Element)child);
			}
		}

		return result;
	}
	
	public String getValue(Element element)
	{
		String result = "";
		Node child = element.getFirstChild();
		result = child.getNodeValue();
		return result;
	}

	private static User importUser(User u) throws ServerException, SQLException
	{
		return db.getUserDAO().insert(u);
	}
	private static Project importProject(Project p) throws ServerException, SQLException
	{
		return db.getProjectDAO().insert(p);
	}
	private static Field importField(Field f) throws ServerException, SQLException
	{
		return db.getFieldDAO().insert(f);
	}
	private static Batch importBatch(Batch b) throws ServerException, SQLException
	{
		return db.getBatchDAO().insert(b);
	}
	private static Record importRecord(Record r) throws ServerException, SQLException
	{
		return db.getRecordDAO().insert(r);
	}
	
	private static void updateBatch(Batch b)
	{
		db.getBatchDAO().update(b);
	}
}
