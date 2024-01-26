package es.upv.pros.pvalderas.saref.command.publisher.camunda;

import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaField;
import org.json.JSONObject;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import es.upv.pros.pvalderas.saref.command.publisher.events.JSONCommand;
import es.upv.pros.pvalderas.saref.command.publisher.events.RabbitMQBroker;
import es.upv.pros.pvalderas.saref.command.publisher.utils.PropReader;

public class CommandPublisher implements ExecutionListener//JavaDelegate
{

	PropReader propReader=new PropReader();
    
    public void notify(final DelegateExecution execution) {
    
    	
    	try {
			JSONObject command=new JSONObject();
			for(CamundaField field:execution.getBpmnModelElementInstance().getExtensionElements().getChildElementsByType(CamundaField.class)){
				if(field.getAttributeValue("name").equals("process")) command.put(JSONCommand.process,field.getAttributeValue("stringValue"));
				if(field.getAttributeValue("name").equals("device")) command.put(JSONCommand.device,field.getAttributeValue("stringValue"));
				if(field.getAttributeValue("name").equals("function")) command.put(JSONCommand.function,field.getAttributeValue("stringValue"));
			}	

			command.put(JSONCommand.instance, execution.getProcessInstanceId());

			String results=execution.getVariable("results")!=null?execution.getVariable("results").toString().toString():"";
			command.put(JSONCommand.inputs, results);
					
			String topic=command.getString("device").replaceAll(" ", "");
			
			if(isRabbitMQ()) {
				RabbitMQBroker rabbitMQ=new RabbitMQBroker(propReader);
				
				Channel channel=rabbitMQ.getTopicCommandsChanel();
				channel.exchangeDeclare(RabbitMQBroker.COMMANDS_EXCHANGE, BuiltinExchangeType.TOPIC);
				
				channel.basicPublish(RabbitMQBroker.COMMANDS_EXCHANGE, topic, null, command.toString().getBytes());
	
				channel.close();
				System.out.println("Command published to "+topic+" "+ command.toString());
				ExternalTaskService externalTaskService=execution.getProcessEngineServices().getExternalTaskService();
		
				System.out.println("Task locked: "+execution.getCurrentActivityId());
				new RabbitResultListener(rabbitMQ, externalTaskService, execution.getProcessInstanceId(), command.getString("function"), execution.getCurrentActivityId());
			}
    	}catch(Exception e) {
			e.printStackTrace();
		}
		
    }
    
    private boolean isRabbitMQ() {
    	String brokerType=propReader.getApplicationProperties().getProperty("events.broker.type");
		if(brokerType.equals(RabbitMQBroker.ID)) return true;
		return false;
    }
   
}