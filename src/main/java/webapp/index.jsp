<html>
    <style>
            
        </style>
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
        <div class="abs">
            <h2 style="text-align:left;">Abstract:</h2>
           <p style="font-size: 18px;">
                Identity-Based Encryption (IBE) is an alternative to public key encryption which simplifies the public key at 
                Public Key Infrastructure (PKI). However, the only drawback of IBE is the overhead computation at Private Key 
                Generator (PKG) during revocation of user. In traditional PKI setting efficient revocation has been well studied,
                but the cumbersome management of private keys is precisely the burden that IBE strives to alleviate. In this paper
                objective to tackle the critical issue of identity revocation, we introduce outsourcing computation into IBE for 
                the propose to reduce the overburden at PKG. Our scheme offloads most of the key generation related operations 
                during key-issuing and key-update processes to a Key Update Cloud Service Provider, leaving only a constant number 
                of simple operations for PKG and users to perform locally. This goal is achieved by using KU-CSP: we employ 
                a hybrid private key which is a combination of identity component and time component for each user.
            </p>
            
            <hr>
            <table><tr><td>
            <div class="guide">
               
            </div></td>
            <td>
                <b>
                 Name:M. Rajshekharan<br>       
                Designation:Assistant Professor<br> 
                Area: Computer Science and Engineering<br>
                </b>
                    </td>
            </tr>
            </table>
            
            <hr>
            <table><tr><td>
            <div class="jangu">
                
            </div></td><td><b>Name:Ashish Jangu<br>ID No: 1031210332<br>Dept:CSE</b></td>
                <td>
            <div class="pny">
                
            </div></td><td><b>Name:Prabhunath Yadav<br>ID No: 1031210334<br>Dept:CSE</b></td>
                </tr>
                </table>
            
        </div>
        <hr>
        <div class="footer">
            <b> Final Year Project ,B.Tech CSE, YEAR:2012-2016</b>
        </div>
    </center>
    </body>
</html>