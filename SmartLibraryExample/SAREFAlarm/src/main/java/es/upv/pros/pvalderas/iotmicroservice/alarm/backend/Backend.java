package es.upv.pros.pvalderas.iotmicroservice.alarm.backend;

public class Backend {
	
	private static Backend backend;
	
	public static Backend getCurrentInstance() {
		if(backend==null) backend=new Backend();
		return backend;
	}
	
	public AlarmState turnOn() {
		return new AlarmState("ON");
	}
	
	public AlarmState turnOff() {
		return new AlarmState("OFF");
	}
}
