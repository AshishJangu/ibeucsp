<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<html>

    <body>
    <center>
        <%@include file="header.jsp" %>
        <%@include file="connection.jsp" %>
        <table class="tbl">
            <tr>
                <td>
                    <a href="user_profile.jsp">USER HOME</a>
                </td>
                <td>
                     <a href="upload.jsp">UPLOAD </a>
                </td>
                <td>
                     <a href="user_fileview.jsp">FILE VIEW</a>
                </td>
                <td>
                    <a href="logout.jsp"> LOGOUT</a>
                </td>
            </tr>
        </table>
        <%
        //Class.forName("com.mysql.jdbc.Driver");
       HttpSession se=request.getSession();
       //Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ibeorcc","root","password");
       String email=(String)se.getAttribute("User_Email");
       out.println("<h3>Welcome @"+email+"</h3>");
       String password=(String)se.getAttribute("User_Password");
       String query="select email,file_name,size,upload_date from userfile";
       Statement pst=con.createStatement();
       ResultSet rs=pst.executeQuery(query);
      String status=(String)se.getAttribute("STATUS");
      if(status.equals("INACTIVE"))
      {
          response.sendRedirect("user_profile.jsp");
      }

       %>
       
        <hr>
        <div style="border:2px solid green; height:400px;border-radius:30px; background-image:url('images/encrypt.jpg');background-size: 1350px 400px;background-repeat: no-repeat; ">
            
            <div class="innerdiv" style="display:table;">
                 <h2 style="color:white;">Uploaded Files</h2>
                 
                 <table>
                     <tr><td>
                <table style="text-align:center;color:white;width:800px;border:2px solid white; border-radius:12px; ">
                    <tr><td>User Email</td><td>File Name</td><td>Size </td><td>Uploaded on Date </td></tr>
                    <%
                    while(rs.next())
                    {
                        out.println("<tr style='background-color:grey;color:greenyellow; '><td>"+rs.getString(1)+"</td><td> "+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td></tr>");
                    }
       %>
                </table>
            
            </td>
              <td>    
                   
                <table style="background-color:white;color:red;opacity: 0.6; ">
                    <form method="POST" action="SendKeyToMail">
                        <tr>
                            <td>File Name</td> <td><input style="background-color:white;" type="text" name="file_name"></td>
                             </tr>
                              <tr>
                            <td>User Email</td> <td><input type="text" name="user_mail"></td>
                             </tr>
                              <tr>
                            <td></td> <td><input type="submit" value="Request Key!!"></td>
                        </tr>
                        
                    </form>
                </table>  
              </td>
                     </tr>
                 </table>
               </div>
            .
        </div>
        
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>