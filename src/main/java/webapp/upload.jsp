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
       // Class.forName("com.mysql.jdbc.Driver");
       HttpSession se=request.getSession();
       //Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ibeorcc","root","password");
       String email=(String)se.getAttribute("User_Email");
       out.println("<h3>Welcome @"+email+"</h3>");
       String password=(String)se.getAttribute("User_Password");
       String query="select * from users where email='"+email+"' and password='"+password+"'";
       Statement pst=con.createStatement();
       ResultSet rs=pst.executeQuery(query);
       String status=(String)se.getAttribute("STATUS");
       if(status.equals("INACTIVE"))
       {
           response.sendRedirect("user_profile.jsp");
       }
       %>
        <hr>
        <div style="border:2px solid green; height:300px;border-radius:30px; background-image:url('Images/file_upload1.png');background-size: 1350px 300px;background-repeat: no-repeat; ">
            <div class="innerdiv2">
                 <h2><u>Upload Your File to Cloud</u></h2>
                <table>
                    <form method="post" action="FileUpload">
                        <tr>
                            <td>Select file: </td>
                            <td><input type="file" name="file_name"></td>
                             
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="Upload on cloud"></td>
                        </tr>
                    </form>
                </table>
            </div>
            
             <%
                    String name=request.getParameter("file_name");
                    //out.println("<p style='color:black;'>Hi there!!</p>");
                    if(session.getAttribute("uploadMessage") != null)
                    {
                        //out.println("<p style='color:black;'>Hi there!!</p>");
                    
                    String message = (String)se.getAttribute("uploadMessage");
                    out.println("<p style='color:black;'>"+message+"</p>");
                    }
                    
                %>
            .
        </div>
        
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>