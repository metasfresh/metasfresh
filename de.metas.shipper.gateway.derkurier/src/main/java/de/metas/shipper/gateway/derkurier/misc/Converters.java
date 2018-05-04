package de.metas.shipper.gateway.derkurier.misc;

import static de.metas.shipper.gateway.derkurier.DerKurierConstants.STREET_DELIMITER;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.derkurier.DerKurierConstants;
import de.metas.shipper.gateway.derkurier.DerKurierDeliveryData;
import de.metas.shipper.gateway.derkurier.restapi.models.RequestParticipant;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.OrderId;
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

@Service
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

		final Address deliveryAddress = Check.assumeNotNull(deliveryOrder.getDeliveryAddress(),
				"The given deliveryOrder's deliveryAddress may not be null; deliveryOrder={}", deliveryOrder);

		final DeliveryDate deliveryDate = Check.assumeNotNull(deliveryOrder.getDeliveryDate(),
				"The given deliveryOrder's deliveryDate may not be null; deliveryOrder={}", deliveryOrder);

		final OrderId orderId = Check.assumeNotNull(deliveryOrder.getOrderId(),
				"The given deliveryOrder's orderId may not be null; deliveryOrder={}", deliveryOrder);

		final Optional<ContactPerson> deliveryContact = Optional.ofNullable(deliveryOrder.getDeliveryContact());
		final ImmutableList.Builder<String> csv = ImmutableList.builder();
		int posCounter = 1;
		for (final DeliveryPosition deliveryPosition : deliveryOrder.getDeliveryPositions())
		{
			final DerKurierDeliveryData derKurierDeliveryData = //
					DerKurierDeliveryData.ofDeliveryOrder(deliveryPosition);

			final Optional<PackageDimensions> packageDimensions = Optional.ofNullable(deliveryPosition.getPackageDimensions());

			final String csvLine = Joiner
					.on(";")
					.skipNulls()
					.join(
							dateToString(deliveryOrder.getPickupDate().getDate()),
							derKurierDeliveryData.getCustomerNumber(),
							deliveryAddress.getCompanyName1(),
							deliveryAddress.getCompanyName2(),
							"", // FirmaD3
							deliveryAddress.getCountry().getAlpha2(),
							deliveryAddress.getZipCode(),
							deliveryAddress.getCity(),
							joinStreet1AndStreet2(deliveryAddress),
							deliveryAddress.getHouseNo(),
							derKurierDeliveryData.getStation(),
							dateToString(deliveryDate.getDate()),
							timeToString(deliveryDate.getTimeFrom()),
							timeToString(deliveryDate.getTimeTo()),
							intToString(posCounter),
							intToString(deliveryPosition.getGrossWeightKg()),
							intToString(packageDimensions.map(PackageDimensions::getLengthInCM)),
							intToString(packageDimensions.map(PackageDimensions::getWidthInCM)),
							intToString(packageDimensions.map(PackageDimensions::getHeightInCM)),
							derKurierDeliveryData.getParcelNumber(),
							deliveryOrder.getCustomerReference(),
							orderId.getOrderIdAsString(),
							"", // cReferenz
							stringToString(deliveryContact.map(ContactPerson::getPhoneAsStringOrNull)),
							"", // EA
							"", // EB
							"", // EC
							"", // NN
							intToString(deliveryPosition.getNumberOfPackages()),
							stringToString(deliveryContact.map(ContactPerson::getEmailAddress)));
			csv.add(csvLine);
			posCounter++;
		}
		return csv.build();
	}

	public String joinStreet1AndStreet2(@NonNull final Address deliveryAddress)
	{
		return Joiner
				.on(STREET_DELIMITER)
				.skipNulls()
				.join(deliveryAddress.getStreet1(), deliveryAddress.getStreet2());
	}

	/**
	 * @return a pair with street1 being left and street2 being right. Both might be {@code null}.
	 */
	public IPair<String, String> splitIntoStreet1AndStreet2(@NonNull final String streets1And2)
	{
		final List<String> list = Splitter.on(STREET_DELIMITER)
				.limit(2)
				.splitToList(streets1And2);

		return ImmutablePair.of(
				list.size() > 0 ? list.get(0) : null,
				list.size() > 1 ? list.get(1) : null);
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

	private static String intToString(@NonNull final Optional<Integer> integer)
	{
		if (integer.isPresent())
		{
			return Integer.toString(integer.get());
		}
		return "";
	}

	private static String intToString(@Nullable final Integer integer)
	{
		if (integer == null)
		{
			return "";
		}
		return Integer.toString(integer);
	}

	private static String stringToString(@NonNull final Optional<String> string)
	{
		if (string.isPresent())
		{
			return string.get();
		}
		return "";
	}
}
