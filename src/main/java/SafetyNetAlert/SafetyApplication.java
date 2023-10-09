package SafetyNetAlert;

import java.io.IOException;

import Config.Generated;
import SafetyNetAlert.Repository.AbstractRepository;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Generated
public class SafetyApplication {

	private static final Logger logger = LogManager.getLogger(SafetyApplication.class);

	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
		SpringApplication.run(SafetyApplication.class, args);
		AbstractRepository.read("data.json");
		logger.info("Lancement de l'application");
	}



}
