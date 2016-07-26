package server;

import shared.communication.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.xml.ws.handler.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.sun.net.httpserver.*;

public class DownloadBatchHandler implements HttpHandler
{
	Facade facade = new Facade();
	
	@SuppressWarnings({ "restriction" })
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		try
		{
			XStream x = new XStream(new DomDriver());		// Like JSON but better I guess...
			DownloadBatch_Params params = (DownloadBatch_Params) x.fromXML(exchange.getRequestBody());	// Get Serialized Object
			
			DownloadBatch_Result result = facade.downloadBatch(params);		// Get info from facade
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			x.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		catch(Exception e)		// might need to change this exception type?
		{
			exchange.sendResponseHeaders(500, -1);
			exchange.getResponseBody().close();
			e.printStackTrace();
			return;
		}	
	}
}
