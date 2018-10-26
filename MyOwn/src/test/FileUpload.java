package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig

public class FileUpload extends HttpServlet {
		public void doPost (HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");
	
	    // Create path components to save the file
	    final String path = "C:/apache-tomcat-9.0.10/webapps/data";
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);
	    System.out.println(fileName);
	
	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();
	
	    try {
	        out = new FileOutputStream(new File(path +"\\"+fileName));
	        filecontent = filePart.getInputStream();
	
	        int read = 0;
	        final byte[] bytes = new byte[1024];
	
	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        writer.println("New file " + fileName + " created ");
	        
	    } catch (FileNotFoundException fne) {
	    	fne.printStackTrace();
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());
	
	       
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	}
	
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	   
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
}

