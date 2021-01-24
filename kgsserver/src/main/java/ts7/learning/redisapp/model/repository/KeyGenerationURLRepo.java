package ts7.learning.redisapp.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ts7.learning.redisapp.model.RedisKeyGenerationBean;

public interface KeyGenerationURLRepo extends CrudRepository<RedisKeyGenerationBean, String> {

	List<RedisKeyGenerationBean> findByIsActive(boolean isActive);

}
