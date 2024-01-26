package es.upv.pros.pvalderas.saref.iotmicroservice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFActuatingFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFDevice;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFFunctionController;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFMeterFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandListener;


@Component
public class ClientStarter implements ApplicationRunner {
	 
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CommandListener commandListener;
	
	@Autowired
	private FunctionMap functionMap;
	
	 
    @Override
    public void run(ApplicationArguments args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException, TimeoutException, JSONException  {
   
		Class mainClass=context.getBeansWithAnnotation(SAREFDevice.class).values().iterator().next().getClass().getSuperclass();
		 
		if(mainClass!=null){
			Annotation classAnnotation= mainClass.getDeclaredAnnotation(SAREFDevice.class);
		    String commandTopic =(String)classAnnotation.annotationType().getMethod("name").invoke(classAnnotation);
		    commandTopic=commandTopic.replaceAll(" ", "");
			System.out.println("Setting up SAREF Device for listening requests in '"+commandTopic+"' topic");	
			if(commandTopic!=null) {
	    		commandListener.register(commandTopic);
	    		Map<String,Object> functionController=context.getBeansWithAnnotation(SAREFFunctionController.class);
	    		if(functionController.values().isEmpty()) {
	    			System.out.println("ERROR: A SAREF Controller is required");
	    		}else if(functionController.values().size()>1) {
	    			System.out.println("ERROR: Only one SAREF Controller can be defined");
				}else {
		    		findFunctions(functionController);
		    		System.out.println("Setting up OK");
	    		}
	    	}else {
	    		System.out.println("ERROR: SAREF Device name is not defined");
	    	}
		}
         
    }
    
    
    private void findFunctions(Map<String,Object> functionController) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, JSONException{
	    Object obj=functionController.values().iterator().next();
	    functionMap.setFunctionController(obj);
	    
    	Method methods[]=obj.getClass().getMethods(); 
        for(Method m:methods){
	       	Annotation function = m.getDeclaredAnnotation(SAREFActuatingFunction.class);
	       	if(function==null) function=m.getDeclaredAnnotation(SAREFMeterFunction.class);
	       	
	       	if(function!=null){
	       		String functionName=(String) function.annotationType().getMethod("name").invoke(function);
	       		functionMap.addFunction(functionName, m);
	        }
	    
        }

    }
   
}
