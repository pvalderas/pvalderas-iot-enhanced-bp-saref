package es.upv.pros.pvalderas.iotmicroservice.co2sensor;
import org.springframework.stereotype.Component;

import es.upv.pros.pvalderas.iotmicroservice.co2sensor.backend.Backend;
import es.upv.pros.pvalderas.iotmicroservice.co2sensor.backend.CO2Level;
import es.upv.pros.pvalderas.iotmicroservice.co2sensor.backend.NotificationState;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFActuatingFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFFunctionController;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFMeterFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

@Component
@SAREFFunctionController
public class CO2SensorController {
	@SAREFMeterFunction(name="Get CO2 Level",
				description="Return the Level of CO2 when requested",
				resultType=CO2Level.class)
	public CommandResult getCO2() {
		CommandResult results=Backend.getCurrentInstance().getCO2Level();
		System.out.println("Get CO2 Level executed. Result:"+ results.toJSON());
		return results;
	}
	
	@SAREFActuatingFunction(name="Start Notifications",
			description="Turns the automatic measurement of CO2 ON",
			resultType=Boolean.class)
	public CommandResult startNotifications() {
		CommandResult results=Backend.getCurrentInstance().startNotications("");
		System.out.println("Start Notifications executed. Result:"+ results.toJSON());
		return results;
	}
	
	@SAREFActuatingFunction(name="Stop Notifications",
			description="Turns the automatic measurement of CO2 OFF",
			resultType=Boolean.class)
	public CommandResult stopNotifications() {
		CommandResult results=Backend.getCurrentInstance().stopNotications();
		System.out.println("Stop Notifications executed. Result:"+ results.toJSON());
		return results;
	}
} 









