package de.metas.shipper.gateway.derkurier.misc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.derkurier.DerKurierConstants;
import de.metas.shipper.gateway.derkurier.DerKurierDeliveryData;
import de.metas.shipper.gateway.derkurier.restapi.models.RequestParticipant;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
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
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DerKurierConstants.DATE_FORMAT);
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DerKurierConstants.TIME_FORMAT);

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
		final Address deliveryAddress = deliveryOrder.getDeliveryAddress();
		final DeliveryDate deliveryDate = deliveryOrder.getDeliveryDate();

		int posCounter = 1;

		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions())
		{
			final DerKurierDeliveryData derKurierDeliveryData = //
					DerKurierDeliveryData.ofDeliveryOrder(deliveryPosition);

			final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();

			final String csvLine = String.join(";",
					dateToString(deliveryOrder.getPickupDate().getDate()),
					derKurierDeliveryData.getCustomerNumber(),
					deliveryAddress.getCompanyName1(),
					deliveryAddress.getCompanyName2(),
					null,
					deliveryAddress.getCountry().getAlpha2(),
					deliveryAddress.getZipCode(),
					deliveryAddress.getCity(),
					String.join(" - ", deliveryAddress.getStreet1(), deliveryAddress.getStreet2()),
					deliveryAddress.getHouseNo(),
					derKurierDeliveryData.getStation(),
					dateToString(deliveryDate.getDate()),
					timeToString(deliveryDate.getTimeFrom()),
					timeToString(deliveryDate.getTimeTo()),
					intToString(posCounter),
					intToString(deliveryPosition.getGrossWeightKg()),
					intToString(packageDimensions.getLengthInCM()),
					intToString(packageDimensions.getWidthInCM()),
					intToString(packageDimensions.getHeightInCM()),
					derKurierDeliveryData.getParcelNumber(),
					deliveryOrder.getCustomerReference(),
					deliveryOrder.getOrderId().getOrderIdAsString(),
					"", // cReferenz
					deliveryOrder.getDeliveryContact().getPhoneAsString(),
					"", // EA
					"", // EB
					"", // EC
					"", // NN
					intToString(deliveryPosition.getNumberOfPackages()),
					deliveryOrder.getDeliveryContact().getEmailAddress());
			csv.add(csvLine);
			posCounter++;
		}
		return csv.build();
	}

	private static String dateToString(@Nullable final LocalDate date)
	{
		if (date == null)
		{
			return "";
		}
		return date.format(dateFormatter);
	}

	private static String timeToString(@Nullable final LocalTime time)
	{
		if (time == null)
		{
			return "";
		}
		return time.format(timeFormatter);
	}

	private static String intToString(final int integer)
	{
		return Integer.toString(integer);
	}
}
