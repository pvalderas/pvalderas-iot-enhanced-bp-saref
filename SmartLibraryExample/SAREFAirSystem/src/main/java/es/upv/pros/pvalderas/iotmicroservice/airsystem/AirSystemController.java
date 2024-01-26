package es.upv.pros.pvalderas.iotmicroservice.airsystem;
import org.springframework.stereotype.Component;

import es.upv.pros.pvalderas.iotmicroservice.airsystem.backend.Backend;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFActuatingFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFFunctionController;
import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

@Component
@SAREFFunctionController
public class AirSystemController {

	
	@SAREFActuatingFunction(name="Start",
			description="Turns the air system ON",
			resultType=Boolean.class)
	public CommandResult start() {
		CommandResult results=Backend.getCurrentInstance().start();
		System.out.println("start air system executed. Result:"+ results.toJSON());
		return results;
	}
	
	@SAREFActuatingFunction(name="Stop",
			description="Turns the air system CO2 OFF",
			resultType=Boolean.class)
	public CommandResult stop() {
		CommandResult results=Backend.getCurrentInstance().stop();
		System.out.println("stop air system executed. Result:"+ results.toJSON());
		return results;
	}
} 









