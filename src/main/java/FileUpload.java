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
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author Prabhunath
 */
public class FileUpload extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FileUpload</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FileUpload at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");*/
            HttpSession se= request.getSession();
            String status=(String)se.getAttribute("STATUS");
            if(status.equals("ACTIVE"))
            {
            String user_email=(String)se.getAttribute("User_Email");
            out.println("<h1>Welcome "+user_email+"</h1>");
            String originalText ="";
            String filename=request.getParameter("file_name");
            File file_name=(File) request.getPart("file_name");
           String path="C:\\Users\\JANGU\\Desktop\\MyFiles\\"+filename;
           out.println("File Name:"+filename+"  <br>Path: "+path+"<br>");
           //---------------------------------------------------------
           
           
           
           File Orig_file= new File("Files");
     

      // Create files to store public and private key
      if (Orig_file.getParentFile() != null) {
        Orig_file.getParentFile().mkdirs();
        out.println("<br>Files DIRECTORY CREATED<br>");
      }
      Orig_file.createNewFile();
 
       String dest="Files\\";
        
                System.out.println("Directory is created!");
             File a=new File(dest,""+filename);
                 FileUtils.copyFile(file_name,a);
         
           
           //------------------------------------------------------------
           
           FileReader fileReader = new FileReader("Files\\"+filename);
              
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String data="";
            while((line = bufferedReader.readLine()) != null) {
                data+=line;
            }   
            bufferedReader.close();
            out.println("Data in file : "+data+"<br>");
           
           //--------------------------------------------------------------
           
           
           
           
           //--------------- Program to create Folder----------------
           File theDir = new File("..\\images\\"+user_email);

            // if the directory does not exist, create it
            if (!theDir.exists()) {
               // System.out.println("creating directory: " + fileName);
                boolean result = false;

                try{
                    theDir.mkdir();
                    result = true;
                } 
                catch(SecurityException see){
                    //handle it
                }        
                
            }
           
           
           
           
           
            
            originalText=data;
            if (!areKeysPresent()) {
        // Method generates a pair of keys using the RSA algorithm and stores it
        // in their respective files
            generateKey();
         }
      
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
      out.println("<br>Original Text: " + originalText);
      out.println("<br>Encrypted Text: " +cipherText.toString());
      out.println("<br>Decrypted Text: " + plainText);
      out.println("<br>Public key: " + publicKey);
      out.println("<br>Private key: "+privateKey); 
      Class.forName("com.mysql.jdbc.Driver");
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
      String formattedDate = sdf.format(date);
     Connection con=DriverManager.getConnection("jdbc:mysql://127.11.188.130:3306/ibeorcc","adminaHSV5Sy","iSVhA33vaX73");
      String query ="insert into userfile values('"+user_email+"','"+filename+"','"+data.length()+"','"+data+"','"+formattedDate+"')";
     
      
      
      out.println("<hr><hr>query= "+query);
      Statement st= con.createStatement();
      int x=st.executeUpdate(query);
      if(x>0)
      {
          out.println("File Uploaded succes!!");
          se.setAttribute("uploadMessage", "File Uploaded succes!!");
          response.sendRedirect("upload.jsp");
      }
        }
        else
            {
                response.sendRedirect("user_profile.jsp");
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

  /**
   * Test the EncryptionUtil
   */


}
