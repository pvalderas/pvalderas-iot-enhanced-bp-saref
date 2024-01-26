package es.upv.pros.pvalderas.iotmicroservice.alarm.backend;

import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

public class AlarmState implements CommandResult{
	
	private String state;
	
	public AlarmState(String state) {
		this.state=state;
	}

	public String toJSON() {
		return "{\"state\":\""+state+"\"}";
	}

}
