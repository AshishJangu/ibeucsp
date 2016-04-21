<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://127.11.188.130:3306/ibeorcc","adminaHSV5Sy","iSVhA33vaX73");

%>