package com.word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;
import org.apache.log4j.Logger;


import sun.misc.BASE64Encoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class WordUtil {
	private Configuration configuration = null;
	final Logger log = Logger.getLogger(WordUtil.class);

	public WordUtil() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	public void createWord(String templetName, String infilePathName, String outfilePathName, Map<String, Object> dataMap) {
		configuration.setClassForTemplateLoading(this.getClass(), infilePathName); // FTL文件所存在的位置
		configuration.setDefaultEncoding("UTF-8");
		
		Template t = null;
		try {
			// 获取模版文件
			 t = configuration.getTemplate(templetName,"UTF-8");
		} catch (IOException e) {
			log.error(e.toString());
		}
		// 生成文件的路径和名称
		File outFile = new File(outfilePathName);
		Writer out = null;
		try {
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				log.error(e.toString());
			}
		} catch (FileNotFoundException e1) {
			log.error(e1.toString());
		}

		try {
			t.process(dataMap, out);
			if(out != null){
	            out.close();
	        }
		} catch (TemplateException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
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
			log.error(e.toString());
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
	public String getPath() {
		String nowpath = System.getProperty("user.home");
		log.info("nowpath is "+nowpath);
		String catalinahome = System.getProperty("catalina.home");
		log.info("catalina home is "+catalinahome);
		String catalinabase = System.getProperty("catalina.base");
		log.info("catalina base is "+catalinabase);
		String path = nowpath.replace("bin", "webapps");
		log.info("path is "+path);
		path += "/"+"word";
		log.info("path is "+path);
		File tmp = new File(path);
		if (!tmp.exists()) {
			tmp.mkdirs();
		}
		return path;
	}
}
