package es.upv.pros.pvalderas.saref.command.publisher.events;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import es.upv.pros.pvalderas.saref.command.publisher.utils.PropReader;

public class RabbitMQBroker extends MessageBroker{
	
	
		public static final String ID="rabbitmq";

		public static final String COMMANDS_EXCHANGE="commands";
		public static final String RESULTS_EXCHANGE="results";
		
		public RabbitMQBroker(PropReader propReader) {
			configMessageBroker(propReader.getApplicationProperties());
		}
		
		private Connection connection;
		
		private Connection getConnection() throws IOException, TimeoutException{
			if(connection==null) {
				ConnectionFactory factory = new ConnectionFactory();
				factory.setHost(getHost());
				if(getPort()!=null) factory.setPort(Integer.parseInt(getPort()));
				if(getVirtualHost()!=null) factory.setVirtualHost(getVirtualHost());
				if(getUser()!=null) factory.setUsername(getUser());
				if(getPassword()!=null) factory.setPassword(getPassword());
				connection = factory.newConnection();
			}
			return connection;
		}
		
		public Channel getTopicCommandsChanel() throws IOException, TimeoutException{
			Channel channel = getConnection().createChannel();
			channel.exchangeDeclare(RabbitMQBroker.COMMANDS_EXCHANGE, BuiltinExchangeType.TOPIC);
			return channel;
		}
		
		public Channel getResultCommandsChanel() throws IOException, TimeoutException{
			Channel channel = getConnection().createChannel();
			channel.exchangeDeclare(RabbitMQBroker.RESULTS_EXCHANGE, BuiltinExchangeType.TOPIC);
			return channel;
		}

		public void closeConnection() throws IOException {
			connection.close();
		}
}
