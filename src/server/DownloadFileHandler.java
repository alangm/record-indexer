package server;

import java.nio.file.*;
import java.io.*;

import com.sun.net.httpserver.*;

@SuppressWarnings("restriction")
public class DownloadFileHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange)
	{		
		try
		{
			String fileName = "./Record"+File.separator+exchange.getRequestURI().getPath();
			File file = new File(fileName);
	
			exchange.sendResponseHeaders(200, 0);
			Files.copy(file.toPath(), exchange.getResponseBody());
			
			exchange.getResponseBody().close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
