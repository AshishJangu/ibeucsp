<html>
    
    <body>
    <center>
        <%@include file="header.jsp" %>
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
        <%
        HttpSession se= request.getSession();
        String pkg_user=(String)se.getAttribute("pkg_user");
        String password =(String)se.getAttribute("password");
        out.println("<h2>Welcome: "+pkg_user+"</h2>");
        %>
        <div class="abs">
            <div class="welcome" style="background-image: url('images/wel1.jpg');background-repeat: no-repeat;background-size: 1200px 300px;">.<br>.
           
            </div>
        </div>
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>