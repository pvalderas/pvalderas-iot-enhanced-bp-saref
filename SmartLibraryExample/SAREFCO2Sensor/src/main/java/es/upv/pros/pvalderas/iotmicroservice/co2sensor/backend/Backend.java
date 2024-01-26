package es.upv.pros.pvalderas.iotmicroservice.co2sensor.backend;

public class Backend {
	
	private static Backend backend;
	
	public static Backend getCurrentInstance() {
		if(backend==null) backend=new Backend();
		return backend;
	}
	
	public CO2Level getCO2Level() {
		return new CO2Level();
	}

	public NotificationState startNotications(String interval) {
		return new NotificationState("ON");
	}
	
	public NotificationState stopNotications() {
		return new NotificationState("OFF");
	}
}
