package es.upv.pros.pvalderas.saref.iotmicroservice.events;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import es.upv.pros.pvalderas.saref.iotmicroservice.utils.PropReader;

@Component
public class ResultPublisher {
	
	@Autowired
	private PropReader propReader;
	
	public void publishResult(String result, JSONObject command){
		
		try{
			String topic=command.getString(JSONCommand.instance)+"."+command.getString(JSONCommand.function).replaceAll(" ", "");
			String brokerType=propReader.getApplicationProperties().getProperty("events.broker.type");
			switch(brokerType){
				case RabbitMQBroker.ID: rabbitPublish(result, topic);break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void rabbitPublish(String result, String topic) throws IOException, TimeoutException {
		RabbitMQBroker rabbitMQ=new RabbitMQBroker(propReader);
		
		Channel channel=rabbitMQ.getResultCommandsChanel();
		channel.exchangeDeclare(RabbitMQBroker.RESULTS_EXCHANGE, BuiltinExchangeType.TOPIC);

		channel.basicPublish(RabbitMQBroker.RESULTS_EXCHANGE, topic, null, result.getBytes());

		channel.close();
		rabbitMQ.closeConnection();
		System.out.println("Results published to "+topic+" "+ result);
	}
}
