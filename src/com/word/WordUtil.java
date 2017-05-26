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

import sun.misc.BASE64Encoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class WordUtil {
	private Configuration configuration = null;

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
			e.printStackTrace();
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
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			t.process(dataMap, out);
			if(out != null){
	            out.close();
	        }
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
	public String getPath() {
		String nowpath = System.getProperty("user.dir");
		String path = nowpath.replace("bin", "webapps");
		path += "\\"+"TestWeb"+"\\"+"word";
		File tmp = new File(path);
		if (!tmp.exists()) {
			tmp.mkdirs();
		}
		return path;
	}
}
