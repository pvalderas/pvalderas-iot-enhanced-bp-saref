package es.upv.pros.pvalderas.iotmicroservice.alarm;
import org.springframework.stereotype.Component;

import es.upv.pros.pvalderas.iotmicroservice.alarm.backend.Backend;
import es.upv.pros.pvalderas.iotmicroservice.alarm.backend.CO2Level;
import es.upv.pros.pvalderas.iotmicroservice.alarm.backend.AlarmState;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFActuatingFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFFunctionController;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFMeterFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

@Component
@SAREFFunctionController
public class AlarmController {
	
	@SAREFActuatingFunction(name="Turn ON",
			description="Turns the alarm ON",
			resultType=Boolean.class)
	public CommandResult on() {
		CommandResult results=Backend.getCurrentInstance().turnOn();
		System.out.println("Turn ON executed. Result:"+ results.toJSON());
		return results;
	}
	
	@SAREFActuatingFunction(name="Turn OFF",
			description="Turns the alarm OFF",
			resultType=Boolean.class)
	public CommandResult off() {
		CommandResult results=Backend.getCurrentInstance().turnOff();
		System.out.println("Turn OFF executed. Result:"+ results.toJSON());
		return results;
	}
} 









