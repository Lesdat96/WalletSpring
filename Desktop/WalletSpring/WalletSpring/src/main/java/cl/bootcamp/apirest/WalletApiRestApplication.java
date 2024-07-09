package cl.bootcamp.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import cl.bootcamp.apirest.config.*;

@SpringBootApplication(scanBasePackages = {"cl.bootcamp.apirest"}, exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "cl.bootcamp.apirest.dao")
public class WalletApiRestApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(WalletApiRestApplication.class, args);
		
	}
}
