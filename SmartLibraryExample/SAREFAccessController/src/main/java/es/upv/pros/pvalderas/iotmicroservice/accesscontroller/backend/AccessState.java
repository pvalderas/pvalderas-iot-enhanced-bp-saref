package es.upv.pros.pvalderas.iotmicroservice.accesscontroller.backend;

import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

public class AccessState implements CommandResult{
	
	private String state;
	
	public AccessState(String state) {
		this.state=state;
	}

	public String toJSON() {
		return "{\"state\":\""+state+"\"}";
	}

}
