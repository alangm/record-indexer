package shared.communication;

import shared.model.*;

import java.util.*;

public class SubmitBatch_Params
{
	String username;
	String password;
	int batchID;
	ArrayList<Field> fields;	// Probably not this...
	ArrayList<Record> records;
	
	public SubmitBatch_Params(String username, String password, int batchID, String fields)
	{
		this.username = username;
		this.password = password;
		this.batchID = batchID;
		// Parse through fields and add appropriately...?
	}
	
	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	public int getBatchID()
	{
		return batchID;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		
		result.append(username);
		result.append("\n");
		result.append(password);
		result.append("\n");
		result.append(batchID);
		result.append("\n");
		// TODO add the rest of the stuff...
		
		return result.toString();
	}
}
