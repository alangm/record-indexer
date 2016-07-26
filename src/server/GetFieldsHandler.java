package server;

import shared.communication.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.xml.ws.handler.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.sun.net.httpserver.*;

public class GetFieldsHandler implements HttpHandler
{
	Facade facade = new Facade();
	
	@SuppressWarnings({ "restriction" })
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		try
		{
			XStream x = new XStream(new DomDriver());		// Like JSON but better I guess...
			GetFields_Params params = (GetFields_Params) x.fromXML(exchange.getRequestBody());	// Get Serialized Object
			
			GetFields_Result result = facade.getFields(params);		// Get info from facade
			
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
