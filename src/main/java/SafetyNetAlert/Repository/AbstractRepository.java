package SafetyNetAlert.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Config.Generated;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SafetyNetAlert.Model.SafetyAlerts;

/**
 * methods related to the abstract repository
 * @author Mougni
 *
 */

@Generated
public class AbstractRepository {

	static ObjectMapper mapper = new ObjectMapper();
	static SafetyAlerts safety = new SafetyAlerts();

	public static SafetyAlerts getSafety() {
		return safety;
	}

	public static void setSafety(SafetyAlerts safety) {
		AbstractRepository.safety = safety;
	}

	/**
	 * this method read a file that is put in parameter
	 * @param file represents the file that has to be read.
	 */
	public static void read(String file) {



		TypeReference<SafetyAlerts> type = new TypeReference<SafetyAlerts>() {
		};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/"+file);

		SafetyAlerts localData = new SafetyAlerts();
		try {
			localData = mapper.readValue(inputStream, type);
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
		safety = localData;
	}
	
//	@Override
//	public <Persons> Persons[] write(String file, Object object) {
//		
//		// charger l'objet des données a cette dans le service pour ensuite apperler cette fonction et 
//		// mettre cette classe en parametre
//		mapper.writeValue(file, object);
//		return (Persons[]) read(file, object);
//	}
//	
	
	
	
}
