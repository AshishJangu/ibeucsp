<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<html>
    <body>
    <center>
         <%@include file="header.jsp" %>
          <%@include file="connection.jsp" %>
        <table class="tbl">
            <tr>
                <td>
                    <a href="pkg_home.jsp">PKG HOME</a>
                </td>
                <td>
                    <a href="outsource_key.jsp">OUTSOURCE KEY</a>
                </td>
                <td>
                    <a href="private_key.jsp">PRIVATE KEY</a>
                </td>
                <td>
                    <a href="logout.jsp"> LOGOUT</a>
                </td>
            </tr>
        </table>
        <hr>
        <div class="abs">
            <div class="welcome"><h2 style="color:red;">SEND OUTSOUREKEY</h2>
            
                <div class="innerdiv1">
                    <table>
                        <tr class="tbltr">
                            <td class="tblrow">
                                File Name
                            </td>
                            <td class="tblrow">
                                Owner
                            </td>
                            <td class="tblrow">
                                File Size
                            </td>
                            <td class="tblrow">
                                Uploaded On
                            </td>
                          <!--  <td class="tblrow">
                                Request key
                            </td>  -->
                        </tr>
<%
                        String query="select * from userfile";
                        Statement st=con.createStatement();
                        ResultSet rs=st.executeQuery(query);
                        while(rs.next())
                        {
                            out.println("<tr style='text-align:center;'><td>"+rs.getString("file_name")+"</td><td>"+rs.getString("email")+"</td><td>"+
                            rs.getString("size")+"</td><td>"+rs.getString("upload_date")+"</td></tr>");
                        }
   
%>
                        
                    </table>
                        <form method="POST" action="OutSourceToCloud">
                            <table>
                                <tr>
                                    <td>File Name: </td><td><input type='text' name='file_name'></td>
                                </tr>
                                <tr>
                                    <td>Owner Email: </td><td><input type='text' name='owner_email'></td>
                                </tr>
                                <tr>
                                    <td></td><td><input type='submit' value='Out Source Key To Cloud'></td>
                                </tr>
                            </table>
                        </form>
                </div>
            
            </div>
        </div>
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>