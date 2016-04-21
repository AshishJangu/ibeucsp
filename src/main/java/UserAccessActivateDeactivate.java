/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prabhunath
 */
public class UserAccessActivateDeactivate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
             Class.forName("com.mysql.jdbc.Driver");
          Connection con=DriverManager.getConnection("jdbc:mysql://127.11.188.130:3306/ibeorcc","adminaHSV5Sy","iSVhA33vaX73");
             String user_email=request.getParameter("user_email_to_Activate");
             String query_activate="update users set status='True' where email='"+user_email+"'";
             String query_deactivate="update users set status='False' where email='"+user_email+"'";
             String query_to_check_status="select * from users where email='"+user_email+"'";
             Statement st=con.createStatement();
             String status="";
             String name="";
             String email="";
             String password="";
             ResultSet rs=st.executeQuery(query_to_check_status);
             if(rs.next())
             {
               status=rs.getString(6);
               name=rs.getString(1);
               email=rs.getString(2);
               password=rs.getString(3);
             }
             if(status.equals("False"))
             {
                int x=st.executeUpdate(query_activate);
                if(x>0)
                {
                    String query_to_add_in_time_list="insert into time_list_table values('"+name+"','"+email+"','"+password+"')";
                    st.executeUpdate(query_to_add_in_time_list);
                    String query_to_delete_from_revoked_list="delete from revoked_list_table where email='"+email+"' and password='"+password+"'";
                    st.executeUpdate(query_to_delete_from_revoked_list);
                    response.sendRedirect("user_facility_access.jsp");
                }
                else{
                    out.println("Unable to Activate or Deactivate!!");
                }
             }
             else
             {
                 int x=st.executeUpdate(query_deactivate);
                if(x>0)
                {
                    String query_to_add_revoked_list="insert into revoked_list_table values('"+name+"','"+email+"','"+password+"')";
                    st.executeUpdate(query_to_add_revoked_list);
                    String query_to_delete_from_time_list="delete from time_list_table where email='"+email+"' and password='"+password+"'";
                    st.executeUpdate(query_to_delete_from_time_list);
                    response.sendRedirect("user_facility_access.jsp");
                }
                else{
                    out.println("Unable to Activate or Deactivate!!");
                }
             }
                 
            
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
