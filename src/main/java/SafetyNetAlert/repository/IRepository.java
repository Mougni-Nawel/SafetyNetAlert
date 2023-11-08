package SafetyNetAlert.repository;

import SafetyNetAlert.config.Generated;
import SafetyNetAlert.controller.exception.*;

/**
 * methods related to the repository interface
 * @author Mougni
 *
 */
@Generated
public interface IRepository {
	Object findAll() throws NotFoundException;

	<T> T save(T firestations);

}
