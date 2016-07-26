package shared.communication;

import shared.model.*;

public class GetSampleImage_Result
{
	private boolean success = false;
	private String filename;
	// Might need the whole URL here...?...maybe...probably not, though... i think...
	
	public GetSampleImage_Result()
	{
		success = true;
		filename = "";
	}
	public GetSampleImage_Result(Batch image)
	{
		success = true;
		filename = image.getFileName();
	}
	
	public boolean getSuccess()
	{
		return success;
	}
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	public String getFilename()
	{
		return filename;
	}
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		
		if(success)
		{
			result.append(filename);
			result.append("\n");
		}
		else
		{
			result.append("FAILED");
		}
		
		return result.toString();
	}
}
