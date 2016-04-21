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
            <div class="form" style="width:30%; background-color:green; background-image: url('Images/user_2.png'); background-repeat: no-repeat; background-size:400px 300px;">.</div>
            <div class="form" style="width:30%;margin-top: 20px;">
                <br><br><br><br>    
                <h3>PKG Login</h3>
                <hr style="width:200px;">
                <table>
                    
                    <form method="POST" action="PKGLogin">
                        
                        <tr>
                            <td>USER NAME</td><td><input type="text" name="user_name"></td>
                        </tr>
                        <tr>
                            <td>PASSWORD</td><td><input type="password" name="password" ></td>
                        </tr>
                        <tr>
                            <td></td><td><input type="submit" value="LOGIN"></td>
                        </tr>
                    </form>
                </table>
            </div>
            <div class="form" style="width:33%; background-color:green;background-image: url('Images/user_2.png'); background-repeat: no-repeat; background-size:400px 300px; ">.</div>
        </div>
        <hr>
        <div class="footer">
            
        </div>
    </center>
    </body>
</html>