package ts7.learning.tinyurl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ts7.learning.tinyurl.model.TinyUrlKeyInfo;

/**
 * 
 * This the CRUD repo for the Tiny Url app.
 * 
 * @author tamim
 *
 */
public interface TinyUrlRepository extends CrudRepository<TinyUrlKeyInfo, String> {
	List<TinyUrlKeyInfo> findByIsActive(boolean isActive);
}
