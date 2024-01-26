package es.upv.pros.pvalderas.saref.iotmicroservice.utils;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PropReader {
	
	private Properties application;

	public Properties getApplicationProperties(){
		if(application==null) {
			YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
		    yamlFactory.setResources(new ClassPathResource("application.yml"));
		    application=yamlFactory.getObject();
		}
	    return application;
    }
}
