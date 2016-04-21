<html>
    
    <body>
    <center>
        <%@include file="header.jsp" %>
        <table class="tbl">
            <tr>
                <td>
                    <a href="index.jsp">HOME</a>
                </td>
                <td>
                     <a href="pkglogin.jsp">PKG</a>
                </td>
                <td>
                     <a href="csplogin.jsp">KU-CSP</a>
                </td>
                <td>
                    <a href="userlogin.jsp"> USER</a>
                </td>
                <td>
                    <a href="userregister.jsp"> REGISTRATION</a>
                </td>
            </tr>
        </table>
        <hr>
        <div class="form" style="width:90%;">
            <div class="form" style="width:30%; background-color:green;background-image: url('Images/user1.jpg'); background-repeat: no-repeat; background-size:400px 300px;">.</div>
            <div class="form" style="width:30%;margin-top: 20px;">
                <br><br>   
                <h3>USER Login</h3>
                <hr style="width:200px;">
                <table>
                    
                    <form method="POST" action="UserLogin">
                        
                        <tr>
                            <td>USER EMAIL</td><td><input type="text" name="email"></td>
                        </tr>
                        <tr>
                            <td>PASSWORD</td><td><input type="password" name="password" ></td>
                        </tr>
                        <tr>
                            <td></td><td><input type="submit" value="LOGIN"></td>
                        </tr>
                        <tr>
                            <td>New User ?</td><td><a href="userregister.jsp">Register</a></td>
                        </tr>
                    </form>
                </table>
                <%
                    String name=request.getParameter("email");
                    //out.println("<p style='color:black;'>Hi there!!</p>");
                    if(session.getAttribute("loginmsg") != null)
                    {
                        //out.println("<p style='color:black;'>Hi there!!</p>");
                    HttpSession se=request.getSession();
                    String loginmessage = (String)se.getAttribute("loginmsg");
                    out.println("<p style='color:black;'>"+loginmessage+"</p>");
                    }
                    
                %>
            </div>
            <div class="form" style="width:32.7%; background-color:green;background-image: url('Images/user2.png'); background-repeat: no-repeat; background-size:400px 400px;">.</div>
        </div>
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>