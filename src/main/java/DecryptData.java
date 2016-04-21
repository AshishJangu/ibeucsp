/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static java.awt.SystemColor.window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.*;
import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 *
 * @author Prabhunath
 */
public class DecryptData extends HttpServlet {

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
            out.println("<html>\n" +
"    <head>\n" +
"        <link type=\"text/css\" href=\"CSS/main.css\" rel=\"stylesheet\" />\n" +
"    </head>\n" +
"    <body>\n" +
"    <center>\n" +
"        <div class=\"head\">\n" +
"            <h2>Identity Based Encryption using KU-CSP</h2>\n" +
"        </div>\n" +
"        \n" +
"        <hr>\n" +
"        \n" +
"    </center>\n" +
"    </body>\n" +
"</html>");
            HttpSession se=request.getSession();
            Class.forName("com.mysql.jdbc.Driver");
          Connection con=DriverManager.getConnection("jdbc:mysql://127.11.188.130:3306/ibeorcc","adminaHSV5Sy","iSVhA33vaX73");
            String Email=(String)se.getAttribute("u_email");
            String fname=(String)se.getAttribute("file_name");
            String private_key=request.getParameter("private_key");
            
            String query="select private_key from Mailed where email='"+Email+"' and sent_key='"+private_key.trim()+"'";
          //  out.println(query);
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(query);
            
            PrivateKey privateKey1=null;
            if(rs.next())
            {
                
                Blob pvt_blob=rs.getBlob(1);
                privateKey1 = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(pvt_blob.getBytes(1, (int)pvt_blob.length())));
               
            }
            //---------------  Reading Encrypted Files----------------
            
            String path="E:\\Cloud\\"+Email.trim()+"\\"+fname.trim();
            // out.println("PATH..."+path);
            FileReader fileReader = new FileReader(path);
           
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String data="";
            
            while((line = bufferedReader.readLine()) != null) {
                data+=line;
            }   
            bufferedReader.close();
            //out.println("<br>Data in file : "+data+"<br>");
            Path path1 = Paths.get(path);
            byte[] data1 = Files.readAllBytes(path1);
            String plaintext=decrypt(data1, privateKey1);
            //out.println("<br>Your File Data is: "+plaintext+"<hr>");
            File   eoutput = new File("C:\\Users\\Prabhunath\\Documents\\NetBeansProjects\\Identity_Based_Encryption\\web\\Downloaded\\"+fname.trim());
            OutputStream outputStream=new FileOutputStream(eoutput);
            outputStream.write(plaintext.getBytes());
            outputStream.close();
            out.println("<center><br><br><br><br><a style='width:100px;' href='Downloaded/"+fname.trim()+"' download target=\"_blank\"><div class=\"download\">Download</div></a>");
           
           //-------------- Deleting after downloading..----------------- 
            
            String delete_from_Mailed="delete from Mailed where email='"+Email+"' and filename='"+fname.trim()+"' and sent_key !='"+private_key.trim()+"'";
            int x=st.executeUpdate(delete_from_Mailed);
            if(x>0)
            {
               // out.println("<hr>Database Updated<hr>");
            }
            
            //-----------------------------------------------------------
            out.println("<br><br><a href='user_profile.jsp?File Downloaded Sucessfully.' ><div >Go Back to Profile!!</div></a>");
            
            
            out.println("</center><div style='margin-top:200px; border:1px solid white; background-color:green;border-radius:12px; width:100%; height:50px;'> </div>");
            
            
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



//--------------------------RSA FOR DECRYPTION--------------------------------
    
    public static final String ALGORITHM = "RSA";

  /**
   * String to hold the name of the private key file.
   */
  public static final String PRIVATE_KEY_FILE = "keys/private.key";

  /**
   * String to hold name of the public key file.
   */
  public static final String PUBLIC_KEY_FILE = "keys/public.key";

  /**
   * Generate key which contains a pair of private and public key using 1024
   * bytes. Store the set of keys in Prvate.key and Public.key files.
   * 
   * @throws NoSuchAlgorithmException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public static void generateKey() {
    try {
      final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
      keyGen.initialize(1024);
      final KeyPair key = keyGen.generateKeyPair();

      File privateKeyFile = new File(PRIVATE_KEY_FILE);
      File publicKeyFile = new File(PUBLIC_KEY_FILE);

      // Create files to store public and private key
      if (privateKeyFile.getParentFile() != null) {
        privateKeyFile.getParentFile().mkdirs();
      }
      privateKeyFile.createNewFile();

      if (publicKeyFile.getParentFile() != null) {
        publicKeyFile.getParentFile().mkdirs();
      }
      publicKeyFile.createNewFile();

      // Saving the Public key in a file
      ObjectOutputStream publicKeyOS = new ObjectOutputStream(
          new FileOutputStream(publicKeyFile));
      publicKeyOS.writeObject(key.getPublic());
      publicKeyOS.close();

      // Saving the Private key in a file
      ObjectOutputStream privateKeyOS = new ObjectOutputStream(
          new FileOutputStream(privateKeyFile));
      privateKeyOS.writeObject(key.getPrivate());
      privateKeyOS.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * The method checks if the pair of public and private key has been generated.
   * 
   * @return flag indicating if the pair of keys were generated.
   */
  public static boolean areKeysPresent() {

    File privateKey = new File(PRIVATE_KEY_FILE);
    File publicKey = new File(PUBLIC_KEY_FILE);

    if (privateKey.exists() && publicKey.exists()) {
      return true;
    }
    return false;
  }

  /**
   * Encrypt the plain text using public key.
   * 
   * @param text
   *          : original plain text
   * @param key
   *          :The public key
   * @return Encrypted text
   * @throws java.lang.Exception
   */
  public static byte[] encrypt(String text, PublicKey key) {
    byte[] cipherText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance(ALGORITHM);
      // encrypt the plain text using the public key
      cipher.init(Cipher.ENCRYPT_MODE, key);
      cipherText = cipher.doFinal(text.getBytes());
      System.out.println("Key used to encrypt:"+key.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }

  /**
   * Decrypt text using private key.
   * 
   * @param text
   *          :encrypted text
   * @param key
   *          :The private key
   * @return plain text
   * @throws java.lang.Exception
   */
  public static String decrypt(byte[] text, PrivateKey key) {
    byte[] dectyptedText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance(ALGORITHM);

      // decrypt the text using the private key
      cipher.init(Cipher.DECRYPT_MODE, key);
      dectyptedText = cipher.doFinal(text);
      System.out.println("Key used to Decrypt :"+key);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return new String(dectyptedText);
  }
 
}
