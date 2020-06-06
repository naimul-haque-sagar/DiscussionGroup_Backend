package discussion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DiscussionGroupApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscussionGroupApplication.class, args);
	}

}
