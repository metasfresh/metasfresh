package de.metas.shipper.gateway.derkurier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DerKurierConfiguration
{
	@Bean
	public DerKurierShipperGatewayService goShipperGatewayService(final DerKurierDeliveryOrderRepository deliveryOrderRepository)
	{
		return new DerKurierShipperGatewayService(deliveryOrderRepository);
	}
}