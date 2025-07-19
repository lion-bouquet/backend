package kr.ac.kumoh.likelion.bouquet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BouquetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BouquetApplication.class, args);
	}

}
