package shared.communication;

public class ValidateUser_Params
{
	private String username;
	private String password;
	
	public ValidateUser_Params(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		
		result.append(username);
		result.append("\n");
		result.append(password);
		result.append("\n");
		
		return result.toString();
	}
}
