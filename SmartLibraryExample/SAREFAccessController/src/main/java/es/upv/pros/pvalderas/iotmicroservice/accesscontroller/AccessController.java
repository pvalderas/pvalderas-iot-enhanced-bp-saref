package es.upv.pros.pvalderas.iotmicroservice.accesscontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFDevice;


@SpringBootApplication(scanBasePackages = {"es.upv.pros.pvalderas.iotmicroservice.accesscontroller","es.upv.pros.pvalderas.saref.iotmicroservice"})
@SAREFDevice(name="Access Controller")
public class AccessController {
	public static void main(String[] args) {
		SpringApplication.run(AccessController.class, args);
	}	
}

