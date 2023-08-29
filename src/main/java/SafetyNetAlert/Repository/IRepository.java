package SafetyNetAlert.Repository;

import Config.Generated;
import SafetyNetAlert.Controller.Exception.*;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * methods related to the repository interface
 * @author Mougni
 *
 */
@Generated
public interface IRepository {
	<T> Object findAll() throws PersonsNotFoundException;

	<T> T save(T firestations);


	//<T> T findByIds(String firstName, String lastName);

	//<T> void deleteByIds(String firstName, String lastName);

}
