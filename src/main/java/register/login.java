package register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginForm")
public class login extends HttpServlet 
{
	protected void doPost(HttpServletRequest req , HttpServletResponse resp) throws ServletException , IOException
	{
		PrintWriter out=resp.getWriter();
		 resp.setContentType("text/html");
		
       String myemail=req.getParameter("email1");
       String mypass=req.getParameter("pass1");
       
       try{
    	   
    	   Class.forName("com.mysql.cj.jdbc.Driver");
    	   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet_project","root","root");
    	   PreparedStatement ps=con.prepareStatement("select * from register where email=? and password=?");
    	   ps.setString(1,myemail);
    	   ps.setString(2, mypass);
    	   
    	   ResultSet rs= ps.executeQuery();
    	   if(rs.next()) {
    		   
    		   HttpSession session=req.getSession();
    		   session.setAttribute("session_name", rs.getString("name"));
    		   RequestDispatcher rd=req.getRequestDispatcher("/Profile.jsp");
    		   rd.include(req, resp);
    		   
    	   }else {
    		   out.print("<h3 style='color:red'>Email id and Password Didn't match </h3>");
    		   RequestDispatcher rd= req.getRequestDispatcher("/Login.jsp");
    		   rd.include(req, resp);
    		   
    	   }
    	   
    	   
    	   
       }catch(Exception e) {
    	   e.printStackTrace();
		   out.print("<h3 style='color:red'>Error Occured "+e.getMessage()+ "</h3>");
		   RequestDispatcher rd= req.getRequestDispatcher("/Login.jsp");
		   rd.include(req, resp);
       }
   }
	
}
