package SafetyNetAlert.Repository;

import Config.Generated;
import SafetyNetAlert.Controller.Exception.*;

/**
 * methods related to the repository interface
 * @author Mougni
 *
 */
@Generated
public interface IRepository {
	Object findAll() throws PersonsNotFoundException;

	<T> T save(T firestations);

}
