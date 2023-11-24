package com.camilo.database;

import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DatabaseApplication {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}
	
	@PostConstruct
	private void initDb() {
		log.info("****** Creating and inserting into Products table ******");
	    String sqlStatements[] = {
				"drop table users if exists",
	    		"drop table products if exists",
				"CREATE TABLE users ("
						+ "id serial ,"
						+ "name varchar(32) ,"
						+ "username varchar(32) ,"
						+ "password varchar(254))",
				"CREATE TABLE products ("
						+ "id bigint(20) NOT NULL,"
						+ "description varchar(255) DEFAULT NULL,"
						+ "image_path varchar(255) DEFAULT NULL,"
						+ "price double NOT NULL,"
						+ "name varchar(255) DEFAULT NULL)",

				"INSERT INTO users (id, name, username, password) VALUES ('1', 'camilo', 'camilo', '$2a$12$m6Lhnbj4BBUpzziv80gY.OIiobqM0eFaIXjLHz/tWM5JnceVEAWnC')",
				"INSERT INTO users (id, name, username, password) VALUES ('2', 'esteban', 'otroUsername', '$2a$12$m6Lhnbj4BBUpzziv80gY.OIiobqM0eFaIXjLHz/tWM5JnceVEAWnC')",

				"INSERT INTO products (id, description, image_path, price, name) VALUES ('1', 'primera descripcion', 'img/product1.png', '1807', 'producto 1')",
				"INSERT INTO products (id, description, image_path, price, name) VALUES ('2', 'segunda descripcion', 'img/product2.png', '5000', 'producto 2')",
	    };

	    Arrays.asList(sqlStatements).forEach(sql -> {
			jdbcTemplate.execute(sql);
	    });
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseaServer() throws SQLException {
	    return Server.createTcpServer(
	      "-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
	}

}
