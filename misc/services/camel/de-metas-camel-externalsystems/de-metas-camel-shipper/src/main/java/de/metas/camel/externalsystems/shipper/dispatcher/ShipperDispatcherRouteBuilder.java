package de.metas.camel.externalsystems.shipper.dispatcher;

import de.metas.camel.externalsystems.shipper.ShipperEndpoints;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ShipperDispatcherRouteBuilder extends RouteBuilder
{
	private static final String HEADER_shipperGatewayId = "shipperGatewayId";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class).to(direct(MF_ERROR_ROUTE_ID));

		from(ShipperEndpoints.shipmentRequestDispatcher())
				.routeId(ShipperEndpoints.shipmentRequestDispatcherRouteId())
				.process(this::extractShipperGatewayId)
				.streamCache("true")
				.toD(ShipperEndpoints.shipmentRequestFromHeader(HEADER_shipperGatewayId));
	}

	private void extractShipperGatewayId(@NonNull final Exchange exchange)
	{
		// TODO
	}
}
