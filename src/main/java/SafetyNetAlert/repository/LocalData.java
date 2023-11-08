package SafetyNetAlert.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import SafetyNetAlert.config.Generated;
import com.fasterxml.jackson.databind.ObjectMapper;


@Generated
public abstract class LocalData {
	ObjectMapper mapper = new ObjectMapper();
	public <T> T read(File file, T object) throws FileNotFoundException {

		InputStream inputStream = new FileInputStream(file);

		T localData = (T) new Object();
		try {
			localData = mapper.readValue(inputStream, (Class<T>) object);
		} catch (IOException exception) {

			System.out.println(exception);
			localData = null;
		} finally {

			try {
				inputStream.close();
			} catch (IOException ioException) {

				System.out.println(ioException);
				localData = null;
			}
		}

		System.out.println("hello: "+localData);
		return localData;
	}

	
	
}
