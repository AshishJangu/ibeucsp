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
                    <a href="csp_home.jsp">CSP HOME</a>
                </td>
                <td>
                    <a href="user_facility_access.jsp">USER FACILITY</a>
                </td>
                <td>
                     <a href="#">CLOUD DOWNLOADS</a>
                </td>
                <td>
                    <a href="key_request.jsp">KEY REQUEST</a>
                </td>
                <td>
                    <a href="logout.jsp"> LOGOUT</a>
                </td>
            </tr>
        </table>
        <hr>
        <div class="abs">
            <div class="welcome"><h2>MODIFY USER ACCESS</h2>
            
                <div class="innerdiv1">
                    <table>
                        <tr class="tbltr">
                            <td class="tblrow">
                                User Name
                            </td>
                            <td class="tblrow">
                                Email
                            </td>
                            <td class="tblrow">
                                Mobile
                            </td>
                            <td class="tblrow">
                                Place
                            </td>
                            <td class="tblrow">
                                Status
                            </td>
                         <!--   <td class="tblrow">
                                Activate
                            </td>
                            <td class="tblrow">
                                Deactivate
                            </td> -->
                        </tr>
                        
                        <%
                            //Class.forName("com.mysql.jdbc.Driver");
                            //Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ibeorcc","root","password");
                            String query="select * from users";
                            Statement st=con.createStatement();
                            ResultSet rs=st.executeQuery(query);
                            while(rs.next())
                            {
                            out.println("<tr style='text-align:center;'><td>'"+rs.getString(1)+"'</td>"
                                    + "<td>'"+rs.getString(2)+"'</td>"
                                    + "<td>'"+rs.getString(4)+"'</td>"
                                    + "<td>'"+rs.getString(5)+"'</td>"
                                    + "<td>'"+rs.getString(6)+"'</td>"
                                   // + "<td><input style='color:green;' type='submit' value='Activate'></td>"
                                   // + "<td><input style='color:red;' type='submit' value='Deactivate'></td>"
                                    + "</tr>");
                            }
                        %>
                        
                    </table>
                        <form method="POST" action="UserAccessActivateDeactivate">
                            <h3> Enter users Email to Activate/Deactivate</h3>
                            <table>
                                <tr>
                                    <td>Email: </td>
                                    <td><input type="text" name ="user_email_to_Activate"></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td><input type="submit" value="Activate/Deactivate" ></td>
                                </tr>
                            </table>
                        </form>
                </div>
            
            </div>
        </div>
        
        
    </center>
                        
                        <div style="border:1px solid white; background-color: green; width:100%; height:40px;margin-top: 100px;">
            
        </div>
    </body>
</html>