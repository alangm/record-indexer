package shared.communication;

public class DownloadBatch_Params
{
	private String username;
	private String password;
	private int projectID;
	
	public DownloadBatch_Params(String username, String password, int projectID)
	{
		this.username = username;
		this.password = password;
		this.projectID = projectID;
	}

	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	public int getProjectID()
	{
		return projectID;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		
		result.append(username);
		result.append("\n");
		result.append(password);
		result.append("\n");
		result.append(projectID);
		result.append("\n");
		
		return result.toString();
	}
}
