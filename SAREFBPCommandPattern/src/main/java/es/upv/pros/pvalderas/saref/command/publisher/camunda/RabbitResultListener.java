package es.upv.pros.pvalderas.saref.command.publisher.camunda;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import es.upv.pros.pvalderas.saref.command.publisher.events.RabbitMQBroker;

public class RabbitResultListener {
	
	public RabbitResultListener(RabbitMQBroker rabbitMQ, ExternalTaskService externalTaskService, String instanceId, String operation, String activityId) throws IOException, TimeoutException{
		this.addListener(rabbitMQ, externalTaskService, instanceId, operation, activityId);
	}
	
	private void addListener(RabbitMQBroker rabbitMQ, ExternalTaskService externalTaskService, String instanceId, String operation, String activityId) throws IOException, TimeoutException {
				
		String topic=instanceId+"."+operation.replaceAll(" ", "");

		Channel channel=rabbitMQ.getResultCommandsChanel();
			
		String COLA_CONSUMER = channel.queueDeclare().getQueue();
		channel.queueBind(COLA_CONSUMER, RabbitMQBroker.RESULTS_EXCHANGE, topic);

		Consumer consumer = new DefaultConsumer(channel) {
			 @Override
			 public void handleDelivery(String consumerTag, Envelope envelope, 
					 					AMQP.BasicProperties properties, byte[] body) throws IOException {
					

					String results=new String(body);
	
					System.out.println("Received: "+results);
					
					boolean done=false;
					while(!done) {
						List<LockedExternalTask> tasks=externalTaskService.fetchAndLock(1, "ResultListener").topic("command", 60L * 1000L).execute();
						for(LockedExternalTask task:tasks) {
							if(task.getProcessInstanceId().equals(instanceId) && task.getActivityId().equals(activityId)) {
								Hashtable<String,Object> resultVar=new Hashtable<String,Object>();
								resultVar.put("results", results);
								externalTaskService.complete(task.getId(),"ResultListener", resultVar);
								done=true;
								System.out.println("Task unlocked: "+task.getActivityId());
							}else {
								externalTaskService.unlock(task.getId());
							}
						}
					}

					try {
						channel.close();
						rabbitMQ.closeConnection();
					} catch (IOException | TimeoutException e) {
						e.printStackTrace();
					}
					
			 }
		 };
		channel.basicConsume(COLA_CONSUMER, true, consumer);
		
    }

}