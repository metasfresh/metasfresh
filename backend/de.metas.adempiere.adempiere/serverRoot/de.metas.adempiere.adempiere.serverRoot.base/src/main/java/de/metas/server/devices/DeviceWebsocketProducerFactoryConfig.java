package de.metas.server.devices;

import de.metas.device.websocket.DeviceWebsocketNamingStrategy;
import de.metas.util.web.MetasfreshRestAPIConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DeviceWebsocketProducerFactoryConfig
{
	public static final DeviceWebsocketNamingStrategy DEVICES_NAMING_STRATEGY = new DeviceWebsocketNamingStrategy(MetasfreshRestAPIConstants.WEBSOCKET_ENDPOINT_V2 + "/devices");

	@Bean
	@Primary
	public DeviceWebsocketNamingStrategy deviceWebsocketNamingStrategy() {return DEVICES_NAMING_STRATEGY;}
}
