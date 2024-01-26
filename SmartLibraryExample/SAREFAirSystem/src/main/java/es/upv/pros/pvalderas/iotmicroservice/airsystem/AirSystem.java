package es.upv.pros.pvalderas.iotmicroservice.airsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFDevice;


@SpringBootApplication(scanBasePackages = {"es.upv.pros.pvalderas.iotmicroservice.airsystem","es.upv.pros.pvalderas.saref.iotmicroservice"})
@SAREFDevice(name="Air Renewal System")
public class AirSystem {
	public static void main(String[] args) {
		SpringApplication.run(AirSystem.class, args);
	}	
}

