package de.metas.shipper.gateway.derkurier;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.derkurier.misc.Converters;
import de.metas.shipper.gateway.derkurier.restapi.models.Routing;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder.DeliveryOrderBuilder;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DerKurierClient implements ShipperGatewayClient
{

	@VisibleForTesting
	@Getter(value = AccessLevel.PACKAGE)
	private final RestTemplate restTemplate;

	private final Converters converters;

	public DerKurierClient(
			@NonNull final RestTemplate restTemplate,
			@NonNull final Converters converters)
	{
		this.restTemplate = restTemplate;
		this.converters = converters;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DerKurierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public DeliveryOrder createDeliveryOrder(@NonNull final DeliveryOrder draftDeliveryOrder) throws ShipperGatewayException
	{
		throw new UnsupportedOperationException("DerKurierClient.createDeliveryOrder is not implemented");
	}

	@VisibleForTesting
	Routing postRoutingRequest(@NonNull final RoutingRequest routingRequest)
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity<RoutingRequest> entity = new HttpEntity<>(routingRequest, httpHeaders);
		final ResponseEntity<Routing> result = restTemplate.exchange("/routing/request", HttpMethod.POST, entity, Routing.class);

		return result.getBody();
	}

	@Override
	public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final RoutingRequest routingRequest = converters.createRoutingRequestFrom(deliveryOrder);
		final Routing routing = postRoutingRequest(routingRequest);

		return createDeliveryOrderFromResponse(routing, deliveryOrder);
	}

	private DeliveryOrder createDeliveryOrderFromResponse(
			@NonNull final Routing routing,
			@NonNull final DeliveryOrder originalDeliveryOrder)
	{
		final OrderId orderId = OrderId.of(
				getShipperGatewayId(),
				Integer.toString(originalDeliveryOrder.getRepoId()));

		final DerKurierDeliveryOrderData derKurierDeliveryOrderData = //
				new DerKurierDeliveryOrderData(
						routing.getConsignee().getStationFormatted());

		final DeliveryOrderBuilder builder = originalDeliveryOrder.toBuilder()
				.orderId(orderId)
				.customDeliveryOrderData(derKurierDeliveryOrderData)

				// Pickup
				.pickupDate(PickupDate.builder()
						.date(routing.getSendDate())
						.timeTo(routing.getSender().getPickupUntil())
						.build())

				// Delivery
				.deliveryDate(DeliveryDate.builder()
						.date(routing.getDeliveryDate())
						.timeFrom(routing.getConsignee().getEarliestTimeOfDelivery())
						.build());

		return builder.build();
	}

	@Override
	public DeliveryOrder voidDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		// TODO probably nothing - need to check
		return null;
	}

	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		// TODO https://leoz.derkurier.de:13000/rs/api/v1/document/label does not yet work,
		// so we need to fire up our own jasper report and print them

		return null;
	}

}
