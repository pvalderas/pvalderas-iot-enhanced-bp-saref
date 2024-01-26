package es.upv.pros.pvalderas.iotmicroservice.accesscontroller;
import org.springframework.stereotype.Component;

import es.upv.pros.pvalderas.iotmicroservice.accesscontroller.backend.Backend;
import es.upv.pros.pvalderas.iotmicroservice.accesscontroller.backend.CO2Level;
import es.upv.pros.pvalderas.iotmicroservice.accesscontroller.backend.AccessState;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFActuatingFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFFunctionController;
import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFMeterFunction;
import es.upv.pros.pvalderas.saref.iotmicroservice.events.CommandResult;

@Component
@SAREFFunctionController
public class AccessControllerController {
	
	@SAREFActuatingFunction(name="Allow Access",
			description="Allow access to people",
			resultType=Boolean.class)
	public CommandResult allow() {
		CommandResult results=Backend.getCurrentInstance().allowAccess();
		System.out.println("Allow Access executed. Result:"+ results.toJSON());
		return results;
	}
	
	@SAREFActuatingFunction(name="Deny Access",
			description="Does not allow access to people",
			resultType=Boolean.class)
	public CommandResult deny() {
		CommandResult results=Backend.getCurrentInstance().denyAccess();
		System.out.println("Deny Access executed. Result:"+ results.toJSON());
		return results;
	}
} 









