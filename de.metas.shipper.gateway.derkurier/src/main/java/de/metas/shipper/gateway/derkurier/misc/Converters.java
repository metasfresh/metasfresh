package de.metas.shipper.gateway.derkurier.misc;

import java.time.LocalTime;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.derkurier.restapi.models.RequestParticipant;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
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

public class Converters
{
	public RoutingRequest createRoutingRequestFrom(@NonNull final DeliveryOrder deliveryOrder)
	{
		return RoutingRequest.builder()
				.sender(createSender(deliveryOrder))
				.consignee(createConsignee(deliveryOrder))
				.desiredDeliveryDate(deliveryOrder.getDeliveryDate().getDate())
				.build();
	}

	public RequestParticipant createSender(@NonNull final DeliveryOrder deliveryOrder)
	{
		final LocalTime timeFrom = deliveryOrder.getPickupDate().getTimeFrom();
		final LocalTime timeTo = deliveryOrder.getPickupDate().getTimeTo();

		final Address pickupAddress = deliveryOrder.getPickupAddress();

		final RequestParticipant consignee = RequestParticipant.builder()
				.country(pickupAddress.getCountry().getAlpha2())
				.zip(pickupAddress.getZipCode())
				.timeFrom(timeFrom)
				.timeTo(timeTo)
				.build();
		return consignee;
	}

	public RequestParticipant createConsignee(@NonNull final DeliveryOrder deliveryOrder)
	{
		final LocalTime timeFrom = deliveryOrder.getDeliveryDate().getTimeFrom();
		final LocalTime timeTo = deliveryOrder.getDeliveryDate().getTimeTo();

		final Address deliveryAddress = deliveryOrder.getDeliveryAddress();

		final RequestParticipant consignee = RequestParticipant.builder()
				.country(deliveryAddress.getCountry().getAlpha2())
				.zip(deliveryAddress.getZipCode())
				.timeFrom(timeFrom)
				.timeTo(timeTo)
				.build();
		return consignee;
	}

	public List<String> createCsv(@NonNull final DeliveryOrder deliveryOrder)
	{
		final ImmutableList.Builder<String> csv = ImmutableList.builder();
		// TODO
		return csv.build();
	}
}
