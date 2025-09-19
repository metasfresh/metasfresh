package de.metas.camel.externalsystems.shipper;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.builder.endpoint.dsl.DirectEndpointBuilderFactory.DirectEndpointBuilder;
import org.jetbrains.annotations.NotNull;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@UtilityClass
public class ShipperEndpoints
{
	private static final String ROUTE_ID_ShipmentRequest = "Shipper-ShipmentRequest";

	public String shipmentRequestDispatcherRouteId() {return ROUTE_ID_ShipmentRequest;}

	public DirectEndpointBuilder shipmentRequestDispatcher() {return direct(shipmentRequestDispatcherRouteId());}

	public String shipmentRequestRouteId(final @NotNull String shipperGatewayId) {return ROUTE_ID_ShipmentRequest + "/" + shipperGatewayId;}

	public DirectEndpointBuilder shipmentRequest(@NonNull final String shipperGatewayId) {return direct(shipmentRequestRouteId(shipperGatewayId));}

	public DirectEndpointBuilder shipmentRequestFromHeader(@NonNull final String headerName) {return shipmentRequest("${header." + headerName + "}");}
}
