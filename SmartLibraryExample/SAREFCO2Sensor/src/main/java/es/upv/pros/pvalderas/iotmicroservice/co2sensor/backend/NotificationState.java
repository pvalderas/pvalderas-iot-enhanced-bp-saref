package es.upv.pros.pvalderas.iotmicroservice.co2sensor.backend;

import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

public class NotificationState implements CommandResult{
	
	private String state;
	
	public NotificationState(String state) {
		this.state=state;
	}

	public String toJSON() {
		return "{\"state\":\""+state+"\"}";
	}

}
