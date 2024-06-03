package de.metas.ui.web.websocket;

import de.metas.device.websocket.DeviceWebsocketNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DeviceWebsocketProducerFactoryConfig
{
	@Bean
	@Primary
	public DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy() {return WebsocketTopicNames.DEVICES_NAMING_STRATEGY;}
}
