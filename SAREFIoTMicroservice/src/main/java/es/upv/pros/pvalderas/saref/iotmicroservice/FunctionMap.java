package es.upv.pros.pvalderas.saref.iotmicroservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.springframework.stereotype.Component;

import es.upv.pros.pvalderas.saref.iotmicroservice.events.JSONCommand;

@Component
public class FunctionMap {

	private Object controller;
	private Hashtable<String, Method> functions=new  Hashtable<String, Method>();
	
	public void addFunction(String function, Method method){
		functions.put(function, method);
	}
	
	public Method getFunction(String function) {
		return functions.get(function);
	}

	public Object getFunctionController() {
		return controller;
	}

	public void setFunctionController(Object controller) {
		this.controller = controller;
	}
	
	public Object execute(String function) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m=functions.get(function);
		return (m.invoke(controller));
	}
	
}
