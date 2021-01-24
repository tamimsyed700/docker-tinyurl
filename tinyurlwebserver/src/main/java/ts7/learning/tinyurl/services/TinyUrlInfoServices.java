package ts7.learning.tinyurl.services;

import java.util.List;

import ts7.learning.tinyurl.model.NewTinyUrlRequest;
import ts7.learning.tinyurl.model.TinyUrlKeyInfo;
import ts7.learning.tinyurl.model.TinyUrlResponse;

public interface TinyUrlInfoServices {

	List<TinyUrlKeyInfo> getAll();

	TinyUrlKeyInfo getById(String tinyUrlId);

	TinyUrlResponse saveOrUpdate(NewTinyUrlRequest saveInfo);

}
