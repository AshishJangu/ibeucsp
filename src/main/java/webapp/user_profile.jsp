<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
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
       out.println("<h3>Welcome :"+email+"</h3>");
       String password=(String)se.getAttribute("User_Password");
       String query="select * from users where email='"+email+"' and password='"+password+"'";
       Statement pst=con.createStatement();
       ResultSet rs=pst.executeQuery(query);
       
        %>
        <hr>
        <div class="abs">
            <div class="welcome" style="background-color:green;background-image: url('Images/wel2.jpg');background-repeat: no-repeat;background-size:1350px 300px;">
                <p style='color:white;'>WELCOME TO USER PROFILE</p>
            
            
            
            <%
            while(rs.next())
            {
                out.println("<h2 style='color:red;'>Name:'"+rs.getString(1)+"' </h2>");
            }
            String status_rl="select * from revoked_list_table where email='"+email+"' and password='"+password+"'";
            String status_tl="select * from time_list_table where email='"+email+"' and password='"+password+"'";
            ResultSet rl=pst.executeQuery(status_rl);
            if(rl.next())
            {
                out.println("<h2 style='color:white;display:inline;'>Account Status: </h2><h2 style='color:red;display:inline;'>INACTIVE</h2><br><br><br><br><br><br><br><p style='color:red;'>Servicess are not Allowed!!</p>");
                se.setAttribute("STATUS", "INACTIVE");
            }
            ResultSet tl=pst.executeQuery(status_tl);
            if(tl.next())
            {
                out.println("<h2 style='color:white;display:inline;'>Account Status: </h2><h2 style='color:green;display:inline;'>ACTIVE</h2><br><br><br><br><br><br><br><p style='color:green;'>Servicess are Allowed!!</p>");
                se.setAttribute("STATUS", "ACTIVE");
            }
            
            %>
            
            </div>
        </div>
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>