package es.upv.pros.pvalderas.iotmicroservice.accesscontroller.backend;

public class Backend {
	
	private static Backend backend;
	
	public static Backend getCurrentInstance() {
		if(backend==null) backend=new Backend();
		return backend;
	}
	
	public AccessState denyAccess() {
		return new AccessState("FORBIDDEN");
	}
	
	public AccessState allowAccess() {
		return new AccessState("ALLOWED");
	}
}
