package es.upv.pros.pvalderas.iotmicroservice.airsystem.backend;

public class Backend {
	
	private static Backend backend;
	
	public static Backend getCurrentInstance() {
		if(backend==null) backend=new Backend();
		return backend;
	}

	public NotificationState start() {
		return new NotificationState("ON");
	}
	
	public NotificationState stop() {
		return new NotificationState("OFF");
	}
}
