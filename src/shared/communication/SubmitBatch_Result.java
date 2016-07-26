package shared.communication;

import shared.model.*;

public class SubmitBatch_Result
{
	private boolean success;
	
	public SubmitBatch_Result(boolean success)
	{
		this.success = success;		// Probably not...
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
		if(success)
		{
			return "TRUE";
		}
		return "FAILED";
	}
}
