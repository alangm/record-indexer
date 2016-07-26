package shared.model;

import importer.*;
import server.database.*;

import java.util.*;
import java.sql.*;

import org.w3c.dom.*;

public class User
{
	private Integer id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Integer recordsIndexed;
	private Integer batchID;
	
	
	public User()
	{
		userName = null;
		password = null;
		firstName = null;
		lastName = null;
		email = null;
		recordsIndexed = 0;
		batchID = -1;
	}
	public User(int id, String userName, String password, String firstName,
				String lastName, String email, int recordsIndexed, int batchID)
	{
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.recordsIndexed = recordsIndexed;
		this.batchID = batchID;
		
	}
	public User(Element userElement)
	{
		DataImporter di = new DataImporter();
		
		userName = di.getValue((Element)userElement.getElementsByTagName("username").item(0));
		password = di.getValue((Element)userElement.getElementsByTagName("password").item(0));
		firstName = di.getValue((Element)userElement.getElementsByTagName("firstname").item(0));
		lastName = di.getValue((Element)userElement.getElementsByTagName("lastname").item(0));
		email = di.getValue((Element)userElement.getElementsByTagName("email").item(0));
		recordsIndexed = Integer.parseInt(di.getValue((Element)userElement.getElementsByTagName("indexedrecords").item(0)));
		batchID = -1;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getRecordsIndexed() {
		return recordsIndexed;
	}
	public void setRecordsIndexed(Integer recordsIndexed) {
		this.recordsIndexed = recordsIndexed;
	}
	public Integer getBatchID() {
		return batchID;
	}
	public void setBatchID(Integer batchID) {
		this.batchID = batchID;
	}
}
