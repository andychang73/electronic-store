package com.abstractionizer.electronicstore;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@Slf4j
@SpringBootApplication
@MapperScan("com.abstractionizer.electronicstore.storage.rdbms.mappers")
public class ElectronicStoreApplication {

	public static void main(String[] args) throws UnknownHostException {

		ConfigurableEnvironment env = SpringApplication.run(ElectronicStoreApplication.class, args).getEnvironment();
		String protocol = "http";
		if(Objects.nonNull(env.getProperty("server.ssl.key-store"))){
			protocol = "https";
		}
		log.info("\n------------------------------------------------------------\n\t" +
				"Application '{}' is running! Access URLs:\n\t" +
				"Local: \t\t{}://localhost:{}\n\t"+
				"External:\t{}://{}:{}\n\t"+
				"Profile(s):\t{}\n" +
						"------------------------------------------------------------\n",
				env.getProperty("spring.application.name"),
				protocol, env.getProperty("server.port"),
				protocol, InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
				env.getProperty("spring.profiles.active"));
	}

}
