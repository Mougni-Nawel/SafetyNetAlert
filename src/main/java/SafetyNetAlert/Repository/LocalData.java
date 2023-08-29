package SafetyNetAlert.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import Config.Generated;
import com.fasterxml.jackson.databind.ObjectMapper;


@Generated
public abstract class LocalData {
	ObjectMapper mapper = new ObjectMapper();
	public <T> T read(File file, T object) throws FileNotFoundException {
		

		//JSON file to Java object
//		String fileName = "D:\\Mougni\\Documents\\BAC+3 JAVA\\projets\\P5\\safety3\\src\\main\\resources";
		

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

//		return localData;
		
		System.out.println("hello: "+localData);
		return localData;
	}
	
	public <T> T write(File file, T object) throws IOException {
		
		// charger l'objet des données a cette dans le service pour ensuite apperler cette fonction et 
		// mettre cette classe en parametre
		mapper.writeValue(file, object);
		return read(file, object);
	}
	
	
	
}
