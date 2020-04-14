package com.demo.springbootWeb.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.springbootWeb.service.CommonService;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	CommonService commonService;
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping("log")
	public String log(Model model) {
		try {
			List<String> logNameList = commonService.getLogNameList();
			model.addAttribute("logNameList", logNameList);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("获取log文件名集合error", ex);
		}
		return "index";
	}
	
	@RequestMapping("downloadLog")
	public HttpServletResponse downloadLog(String logName, HttpServletResponse response) {
		InputStream fis = null;
		ServletOutputStream outputStream = null;
		OutputStream toClient = null;
		try {
			String logPath = commonService.getLogPath();
			String path = logPath + File.separator + logName;
			// path是指欲下载的文件的路径。
			File file = new File(path);
			if (file != null && file.exists()) {
				// 取得文件名。
				String filename = file.getName();
				// 以流的形式下载文件。
				fis = new BufferedInputStream(new FileInputStream(path));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				// 清空response
				response.reset();
				// 设置response的Header
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
				response.addHeader("Content-Length", "" + file.length());
				outputStream = response.getOutputStream();
				toClient = new BufferedOutputStream(outputStream);
				response.setContentType("application/octet-stream");
				response.setCharacterEncoding("UTF-8");
				toClient.write(buffer);
				toClient.flush();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error("关闭连接fis异常", e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error("关闭连接outputStream异常", e);
				}
			}
			if (toClient != null) {
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error("关闭连接toClient异常", e);
				}
			}
		}
		return response;
	}	

}
