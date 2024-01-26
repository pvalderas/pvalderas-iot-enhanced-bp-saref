package es.upv.pros.pvalderas.saref.iotmicroservice.events;

import java.util.Properties;

class MessageBroker {
	
	private String host;
	private String port;
	private String virtualHost;
	private String user;
	private String password;
	
	private String brokerType;


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBrokerType() {
		return brokerType;
	}

	public void setBrokerType(String brokerType) {
		this.brokerType = brokerType;
	}
	
	
	public void configMessageBroker(Properties props){
        setBrokerType(props.getProperty("events.broker.type"));
        setHost(props.getProperty("events.broker.host"));
        setVirtualHost(props.getProperty("events.broker.virtualHost"));
        setPort(props.getProperty("events.broker.port"));
        setUser(props.getProperty("events.broker.user"));
       setPassword(props.getProperty("events.broker.password")); 
    }
	
	
	
}
