package shared.communication;

import shared.model.User;

public class ValidateUser_Result
{
	Boolean success;
	Boolean failed;
	String firstname;
	String lastname;
	int recordsIndexed;
	
	public ValidateUser_Result()
	{
		success = false;
		failed = false;
		firstname = "";
		lastname = "";
		recordsIndexed = -1;
	}
	public ValidateUser_Result(User u)
	{
		if(u != null)
		{
			success = true;
			failed = false;
			firstname = u.getFirstName();
			lastname = u.getLastName();
			recordsIndexed = u.getRecordsIndexed();
		}
		else
		{
			success = false;
			failed = false;
			firstname = "";
			lastname = "";
			recordsIndexed = -1;
		}
	}
	
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean getFailed() {
		return failed;
	}
	public void setFailed(Boolean failed) {
		this.failed = failed;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getRecordsIndexed() {
		return recordsIndexed;
	}
	public void setRecordsIndexed(int recordsIndexed) {
		this.recordsIndexed = recordsIndexed;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();

		if(failed)
		{
			result.append("FAILED\n");
		}
		else if(success)
		{
			result.append("TRUE\n");
			result.append(firstname);
			result.append("\n");
			result.append(lastname);
			result.append("\n");
			result.append(recordsIndexed);
			result.append("\n");
		}
		else
		{
			result.append("FALSE\n");
		}
		
		
		return result.toString();
	}
}
