package discussion;

import discussion.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class DiscussionGroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscussionGroupApplication.class, args);
	}

}
