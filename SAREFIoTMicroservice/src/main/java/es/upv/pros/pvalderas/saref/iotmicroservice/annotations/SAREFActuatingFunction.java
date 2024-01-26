package es.upv.pros.pvalderas.saref.iotmicroservice.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SAREFActuatingFunction {

	public String name();
	public Class resultType();
	public String description();
}
