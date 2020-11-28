package de.metas.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.config.EnableAdminServer;




@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootAdminApplication.class, args);
	}
}