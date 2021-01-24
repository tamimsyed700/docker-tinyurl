package ts7.learning.tinyurl.repository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = { "ts7.learning.*" })
@PropertySource("classpath:keygeneration.properties")
public class KeyGenerationConfig {

	@Value("${tinyurl.kgs.numberofkeys}")
	private int numberOfKeys;

	@Value("${tinyurl.kgs.sizeofuniquecharset}")
	private int sizeOfKeys;

	@Value("${tinyurl.kgs.charset}")
	private String charSet;

	@Value("${tinyurl.kgs.filename}")
	private String fileName;

	@Value("${tinyurl.kgs.encoding}")
	private String encoding;

	public int getNumberOfKeys() {
		return numberOfKeys;
	}

	public int getSizeOfKeys() {
		return sizeOfKeys;
	}

	public String getCharSet() {
		return charSet;
	}

	public String getFileName() {
		return fileName;
	}

	public String getEncoding() {
		return encoding;
	}

	// To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public String toString() {
		return "KeyGenerationConfig [numberOfKeys=" + numberOfKeys + ", sizeOfKeys=" + sizeOfKeys + ", charSet="
				+ charSet + ", fileName=" + fileName + ", encoding=" + encoding + "]";
	}

}
