package es.upv.pros.pvalderas.saref.iotmicroservice.events;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeoutException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import es.upv.pros.pvalderas.saref.iotmicroservice.FunctionMap;
import es.upv.pros.pvalderas.saref.iotmicroservice.utils.PropReader;

 
@Component
public class CommandListener {
	
	@Autowired
	private FunctionMap functionMap;
	
	@Autowired
	private PropReader propReader;
	
	@Autowired
	private ResultPublisher resultPublisher;
		
		
	public void register(String iotDevice) throws IOException, TimeoutException{
		
		String brokerType=propReader.getApplicationProperties().getProperty("events.broker.type");
		
		switch(brokerType){
			case RabbitMQBroker.ID: rabbitmqRegisterEvent(iotDevice); break;
		}
	
	}
	
	private void rabbitmqRegisterEvent(String topic) throws IOException, TimeoutException{	
		RabbitMQBroker rabbitMQ=new RabbitMQBroker(propReader);
		
		Channel channel=rabbitMQ.getTopicCommandsChanel();
		String COLA_CONSUMER = channel.queueDeclare().getQueue();
		channel.queueBind(COLA_CONSUMER, RabbitMQBroker.COMMANDS_EXCHANGE, topic);

		Consumer consumer = new DefaultConsumer(channel) {
			 @Override
			 public void handleDelivery(String consumerTag, Envelope envelope, 
					 					AMQP.BasicProperties properties, byte[] body) throws IOException {
					
					try {
						
						String message=new String(body);
						JSONObject command = new JSONObject(message);
						String function=command.getString(JSONCommand.function);
						String results=((CommandResult)functionMap.execute(function)).toJSON();
						resultPublisher.publishResult(results, command);
					
					} catch (JSONException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
			 }
		 };
		channel.basicConsume(COLA_CONSUMER, true, consumer);
	}
	
}
