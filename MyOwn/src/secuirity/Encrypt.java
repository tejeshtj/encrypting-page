package secuirity;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/encrypt")
public class Encrypt extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String password=req.getParameter("pass");
		PrintWriter out=resp.getWriter();
		String generatedPassword = null;
		Properties prop=new Properties();
		FileReader fr=new FileReader("E:\\saltstring.properties");//change according to the property file n give ur salt string
		prop.load(fr);
		final String salt=prop.getProperty("salt");
		 try {
	         MessageDigest md = MessageDigest.getInstance("SHA-512");
	         md.update(salt.getBytes(StandardCharsets.UTF_8));
	         byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
	         StringBuilder sb = new StringBuilder();
	         for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	         }
	         generatedPassword = sb.toString();
	         out.println("<h1>your encrypted password is "+generatedPassword+"        "+generatedPassword.length()+"</h1");
	        } 
	       catch (NoSuchAlgorithmException e){
	        e.printStackTrace();
	       }
	    
	}

}
