package es.upv.pros.pvalderas.iotmicroservice.co2sensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFDevice;


@SpringBootApplication(scanBasePackages = {"es.upv.pros.pvalderas.iotmicroservice.co2Sensor","es.upv.pros.pvalderas.saref.iotmicroservice"})
@SAREFDevice(name="CO2 Sensor")
public class CO2Sensor {
	public static void main(String[] args) {
		SpringApplication.run(CO2Sensor.class, args);
	}	
}

