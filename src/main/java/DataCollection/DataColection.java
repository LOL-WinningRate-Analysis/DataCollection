package DataCollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataColection {

	private final String MY_CONSTATNT = "constatnt";

	public static void main(String[] args) {
		SpringApplication.run(DataColection.class, args);
	}

}