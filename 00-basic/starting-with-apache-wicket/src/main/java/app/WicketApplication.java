package app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WicketApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(WicketApplication.class).run(args);
	}

}