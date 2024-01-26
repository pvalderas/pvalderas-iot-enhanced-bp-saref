package es.upv.pros.pvalderas.iotmicroservice.alarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.upv.pros.pvalderas.saref.iotmicroservice.annotations.SAREFDevice;


@SpringBootApplication(scanBasePackages = {"es.upv.pros.pvalderas.iotmicroservice.alarm","es.upv.pros.pvalderas.saref.iotmicroservice"})
@SAREFDevice(name="Alarm")
public class Alarm {
	public static void main(String[] args) {
		SpringApplication.run(Alarm.class, args);
	}	
}

