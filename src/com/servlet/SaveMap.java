package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.word.WordUtil;
import sun.misc.BASE64Decoder;
import org.apache.log4j.Logger;

public class SaveMap extends HttpServlet {
	private static final long serialVersionUID = 72665029324680979L;
	final Logger log = Logger.getLogger(SaveMap.class);
	
    public void init() throws ServletException {  
       
    } 
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	
    }
 
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	String a = request.getParameter("a");
        try {
            String[] url = a.split(",");
            String u = url[1];
            log.info("a is "+a);
            log.info("u is "+u);
            // Base64解码
            byte[] b = new BASE64Decoder().decodeBuffer(u);
            WordUtil wordUtil = new WordUtil();
            String path = wordUtil.getPath(); 
            log.info("path is "+path);
            // 生成图片
            OutputStream out = new FileOutputStream(new File(path+"/map.png"));
            out.write(b);
            out.flush();
            out.close();
            
        } catch (Exception e) {
        	log.error(e.toString());
        }
    }

}
