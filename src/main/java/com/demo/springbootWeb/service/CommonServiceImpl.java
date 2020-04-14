package com.demo.springbootWeb.service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service(CommonService.SERVICE_NAME)
public class CommonServiceImpl implements CommonService {

	@Override
	public List<String> getLogNameList() throws Exception {
		List<String> list = new ArrayList<>();
		String path = getLogPath();
		if (!"".equals(path)) {
			File logFile = new File(path);
			if (logFile != null && logFile.isDirectory()) {
				list = Arrays.asList(logFile.list());
			}
		}
		return list;
	}

	@Override
	public String getLogPath() throws Exception {
		String path = "";
		try (InputStream logStream = getClass().getClassLoader().getResourceAsStream("log4j2.yml")) {
			// 读取日志配置文件log4j2.yml 获取日志存放路径
			Yaml yaml = new Yaml();
			Map<String, Map<String, Object>> log4j2Map = (Map) yaml.load(logStream);
			Map<String, Object> paramMap = log4j2Map.get("Configuration");
			Map<String, List<Map<String, String>>> properties = (Map<String, List<Map<String, String>>>) paramMap.get("Properties");
			List<Map<String, String>> property = properties.get("Property");
			Stream<Map<String, String>> pathStream = property.stream().filter(item -> {
				return "log.path".equals(item.get("name"));
			});
			Map<String, String> pathMap = pathStream.findFirst().get();
			path = pathMap.get("value");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return path;
	};
	
}
