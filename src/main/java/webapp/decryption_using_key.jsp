<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<html>
    <body>
    <center>
        <%@include file="header.jsp" %>
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
        Class.forName("com.mysql.jdbc.Driver");
       HttpSession se=request.getSession();
       String Email=(String)se.getAttribute("u_email");
       String fname=(String)se.getAttribute("file_name");
       se.setAttribute("u_email",Email);
       se.setAttribute("file_name",fname);        
       out.println("</center><hr><table style='width:30%;height:40px;background-color:black;color:white;border:1px solid white;border-radius:12px;'><tr><td>Email: </td><td>"+Email+"</td></tr><tr><td>Key Sent For File Name:</td><td>"+fname+"</td></tr></table><center>");
       %>
       
        <hr>
        <div style="border:2px solid green; height:400px;border-radius:30px; background-image:url('Images/encrypt.jpg');background-size: 1350px 400px;background-repeat: no-repeat; ">
            <div class="innerdiv" style="display:table;">
                 <h2 style="color:white;">Enter Key </h2>
            </td>
              <td>    
                   
                <table style="background-color:white;color:red;opacity: 0.6; ">
                    <form method="POST" action="DecryptData">
                        <tr>
                            <td>Enter Private Key Sent to your Mail:</td> <td><input style="background-color:white;" type="text" name="private_key"></td>
                             </tr>
                              
                              <tr>
                                  <td></td> <td><input type="submit" value="Submit Key!!"></td>
                        </tr>
                            
                        
                    </form>
                </table>  
              </td>
                     </tr>
                 
               </div>
            .
        </div>
        
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>