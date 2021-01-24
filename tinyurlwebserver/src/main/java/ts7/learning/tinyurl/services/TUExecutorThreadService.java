package ts7.learning.tinyurl.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 
 * Tiny URL Executor Thread Service
 * 
 * @author allah
 *
 */
@Service
public class TUExecutorThreadService {
	@Bean("fixedThreadPool")
	public ExecutorService fixedThreadPool() {
		return Executors.newFixedThreadPool(50);
	}

	@Bean("singleThreaded")
	public ExecutorService singleThreadedExecutor() {
		return Executors.newSingleThreadExecutor();
	}

	@Bean("cachedThreadPool")
	public ExecutorService cachedThreadPool() {
		return Executors.newCachedThreadPool();
	}

	@Bean("worksteelThreadPool")
	public ExecutorService worksteelThreadPool() {
		return Executors.newWorkStealingPool();
	}

	@Bean("scheduledThreadPool")
	public ExecutorService scheduledThreadPool() {
		return Executors.newScheduledThreadPool(10);
	}
	
	@Autowired
    @Qualifier("fixedThreadPool")
    private ExecutorService executorService;
    
    public <T> Future<T> executeAsynTask(Callable<T> callable) {
        return executorService.submit(callable);
    }
}
