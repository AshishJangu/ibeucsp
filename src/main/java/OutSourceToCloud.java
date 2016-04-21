/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import javax.crypto.Cipher;

/**
 *
 * @author Prabhunath
 */
public class OutSourceToCloud extends HttpServlet {

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
            
            Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://127.11.188.130:3306/ibeorcc","adminaHSV5Sy","iSVhA33vaX73");
             String file_name=request.getParameter("file_name");
             String owner_email=request.getParameter("owner_email");
             String query_about_data="select * from userfile where email='"+owner_email+"' and file_name='"+file_name+"'";
             Statement st=con.createStatement();
             ResultSet rs=st.executeQuery(query_about_data);
             String public_key,private_key,uploaded_on="",encrypted_data,original_data="", size="";
             if(rs.next())
             {
                 size=rs.getString(3);
                 original_data=rs.getString(4);
                 uploaded_on=rs.getString(5);
             }
            
            if (!areKeysPresent()) {
        generateKey();
      }
      final String originalText = original_data;//"Text to be encrypted ";
      ObjectInputStream inputStream = null;

      // Encrypt the string using the public key
      inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
      final PublicKey publicKey = (PublicKey) inputStream.readObject();
      final byte[] cipherText = encrypt(originalText, publicKey);

      // Decrypt the cipher text using the private key.
      inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
      final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
      final String plainText = decrypt(cipherText, privateKey);

      // Printing the Original, Encrypted and Decrypted Text
      //System.out.println("Original Text: " + originalText);
     // System.out.println("Encrypted Text: " +cipherText.toString());
     // System.out.println("Decrypted Text: " + plainText);
     public_key=publicKey.toString();
     private_key=privateKey.toString();
     
     String q="insert into csp_files values(?,?,?,?,?,?,?,?)";  
       PreparedStatement pst=con.prepareStatement(q);
     
     
     byte pkey1[]=publicKey.getEncoded();
          byte prkey1[]=privateKey.getEncoded();
    
     String outsource_query="insert into csp_files values('"+file_name+"','"+owner_email+"','"+pkey1+"','"+prkey1+"','"+uploaded_on+"','"+cipherText+"','"+original_data+"','"+size+"')";
      //out.println(outsource_query);
      
      pst.setString(1,file_name);
      pst.setString(2,owner_email);
      pst.setBytes(3,pkey1);
      pst.setBytes(4,prkey1);
      pst.setDate(5, Date.valueOf(uploaded_on));
      pst.setBytes(6,cipherText);
      pst.setString(7,original_data);
      pst.setString(8,size);
      
     File   eoutput = new File("E:\\Cloud\\"+owner_email+"\\"+file_name);
     OutputStream outputStream=new FileOutputStream(eoutput);
     outputStream.write(cipherText);
     outputStream.close();
     out.println(outsource_query);
     //int x=st.executeUpdate(outsource_query);
     int x=pst.executeUpdate();
     out.println("HELLO THERE!!");
     if(x>0)
     {
         response.sendRedirect("outsource_key.jsp?FileUploaded Sucessfully!!");
        // out.println("Inserted!!");
     }
     else
     {
         out.println("Error occured!!");
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

    
    //<<<<<-------------------------------------Program TO Encrypt and Decrypt Using RSA Algorithm.---------------------------------------->>>>>
    
    
    
    public static final String ALGORITHM = "RSA";

  /**
   * String to hold the name of the private key file.
   */
  public static final String PRIVATE_KEY_FILE = "C:/keys/private.key";

  /**
   * String to hold name of the public key file.
   */
  public static final String PUBLIC_KEY_FILE = "C:/keys/public.key";

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
