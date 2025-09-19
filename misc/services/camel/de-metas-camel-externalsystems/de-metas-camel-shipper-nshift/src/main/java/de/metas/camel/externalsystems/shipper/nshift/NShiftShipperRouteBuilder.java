package de.metas.camel.externalsystems.shipper.nshift;

import de.metas.camel.externalsystems.shipper.ShipperEndpoints;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class NShiftShipperRouteBuilder extends RouteBuilder
{
	public static String SHIPPER_GATEWAY_ID = "nshift";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class).to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(ShipperEndpoints.shipmentRequest(SHIPPER_GATEWAY_ID))
				.routeId(ShipperEndpoints.shipmentRequestRouteId(SHIPPER_GATEWAY_ID))
				.process(this::process);
	}
	
	private void process(@NonNull final Exchange exchange)
	{
		// TODO
	}
}
