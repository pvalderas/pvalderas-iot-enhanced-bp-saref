package es.upv.pros.pvalderas.iotmicroservice.co2sensor.backend;

import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

public class CO2Level implements CommandResult{

	public String toJSON() {
		return "{\"level\":24}";
	}

}
