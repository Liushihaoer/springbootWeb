package com.demo.springbootWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
	private static Logger log = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		log.trace("trace------------------------------------");
		log.debug("debug------------------------------------");
		log.info("info------------------------------------");
		log.warn("warn------------------------------------");
		log.error("error------------------------------------");
	}
}
