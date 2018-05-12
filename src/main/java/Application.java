

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.nidis")
@EnableJpaRepositories(basePackages = "com.nidis.repositories")
@EntityScan(basePackages = "com.nidis.models")
@EnableHypermediaSupport(type = HAL)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
