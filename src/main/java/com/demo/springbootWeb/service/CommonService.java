package com.demo.springbootWeb.service;

import java.util.List;

public interface CommonService {
	public static final String SERVICE_NAME = "CommonService";

	/**
	 * 获取log文件名集合
	 * @return
	 * @throws Exception
	 */
	List<String> getLogNameList() throws Exception;

	/**
	 * 获取log配置文件中的存放地址
	 * @return
	 * @throws Exception
	 */
	String getLogPath() throws Exception;

}
