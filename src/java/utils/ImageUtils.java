package utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Altysh
 */
public class ImageUtils {
    public static String getFileName(final Part part) {
for (String content : part.getHeader("content-disposition").split(";")) {
if (content.trim().startsWith("filename")) {
return content.substring(
content.indexOf('=') + 1).trim().replace("\"", "");
}
}
return null;
}
    public static byte[] getBytesFromFile(final Part part)
    {byte[] bytes =null;
        InputStream in = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            in = part.getInputStream();
            byte[] buf = new byte[1024];
            int iterator = 0;
            while ((iterator = in.read(buf)) >= 0) {
                baos.write(buf, 0, iterator);
                
            }
            in.close();
             bytes = baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
              return bytes;
        }
      
    }
}
