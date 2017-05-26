package com.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.word.WordUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SaveImage extends HttpServlet {
	private static final long serialVersionUID = -1915463532411657451L;
	 
    public void init() throws ServletException {  
       
    } 
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	
    }
 
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
    	String a = request.getParameter("a");
        try {
            String[] url = a.split(",");
            String u = url[1];
            // Base64解码
            byte[] b = new BASE64Decoder().decodeBuffer(u);
            WordUtil wordUtil = new WordUtil();
            String fileStr = wordUtil.getPath(); 
            // 生成图片
            OutputStream out = new FileOutputStream(new File(fileStr+"\\test.png"));
            out.write(b);
            out.flush();
            out.close();
            
            //数据模拟，如果是真实编写，可以建service层，dao层进行数据的获取
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap = getData();
            //生产word
            wordUtil.createWord("2.ftl", "/com/word", fileStr+"\\test.doc", dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Map<String, Object> getData() {
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	WordUtil wordUtil = new WordUtil();
    	String fileStr = wordUtil.getPath();
    	
    	dataMap.put("image", getImageStr(fileStr+"\\test.png"));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 2; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("xuhao", i);
			map.put("neirong", "内容"+i);
			list.add(map);
			
		}
		dataMap.put("list", list);
		dataMap.put("info", "测试");
		return dataMap;
    }
    public String getImageStr(String imgFile) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
}
