package importer;

import shared.model.*;

import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import shared.model.Project;
import shared.model.User;

public class IndexerData
{
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Project> projects = new ArrayList<Project>();
	
	public IndexerData(Element root) throws ParserConfigurationException, SAXException, IOException
	{
		ArrayList<Element> rootElements = DataImporter.getChildElements(root);
		 
		ArrayList<Element> userElements = DataImporter.getChildElements(rootElements.get(0));
		for(Element userElement : userElements)
		{
			users.add(new User(userElement));
		}
		
		ArrayList<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));
		for(Element projectElement : projectElements)
		{
			projects.add(new Project(projectElement));
		}
	}
	
	

	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public ArrayList<Project> getProjects() {
		return projects;
	}
	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	public void setUserAtIndex(int i, User u) {
		users.set(i, u);
	}
	public void setProjectAtIndex(int i, Project p) {
		projects.set(i, p);
	}
}