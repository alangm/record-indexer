package shared.communication;

import java.util.*;

import shared.model.*;

public class GetProjects_Result
{
	private ArrayList<Project> projects;
	boolean success = true;
	
	public GetProjects_Result()
	{
		projects = new ArrayList<Project>();
		success = true;
	}
	public GetProjects_Result(ArrayList<Project> projects)
	{
		this.projects = projects;
		success = true;
	}
	
	public ArrayList<Project> getProjects()
	{
		return projects;
	}
	public boolean getSuccess()
	{
		return success;
	}
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		
		if(success)
		{
			for(int i=0; i<projects.size(); i++)
			{
				result.append(projects.get(i).getId());
				result.append("\n");
				result.append(projects.get(i).getTitle());
				result.append("\n");
			}
		}
		else
		{
			result.append("FAILED");
		}
		
		return result.toString();
	}
}
