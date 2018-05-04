package de.metas.shipper.gateway.derkurier;

import static de.metas.shipper.gateway.derkurier.DerKurierConstants.SHIPPER_GATEWAY_ID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.derkurier.DerKurierDeliveryData.DerKurierDeliveryDataBuilder;
import de.metas.shipper.gateway.derkurier.misc.DerKurierServiceType;
import de.metas.shipper.gateway.derkurier.restapi.models.RequestParticipant;
import de.metas.shipper.gateway.derkurier.restapi.models.RoutingRequest;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.DeliveryDate;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrder.DeliveryOrderBuilder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PickupDate;

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

public class DerKurierTestTools
{
	private static final CountryCode COUNTRY_CODE_DE = CountryCode.builder().alpha2("DE").alpha3("DEU").build();

	public static DeliveryOrder createTestDeliveryOrderwithOneLine()
	{
		final DeliveryOrderBuilder deliveryOrderBuilder = prepareDeliveryOrderBuilder();
		final DerKurierDeliveryDataBuilder derKurierDeliveryDataBuilder = prepatreDerKurierDeliveryDataBuilder();

		final DeliveryOrder deliveryOrder = deliveryOrderBuilder
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(5)
						.packageIds(ImmutableList.of(1, 2, 3, 4, 5))
						.grossWeightKg(1)
						.customDeliveryData(derKurierDeliveryDataBuilder.parcelNumber("parcelnumber1").build())
						.build())
				.build();
		return deliveryOrder;
	}

	public static DeliveryOrder createTestDeliveryOrder()
	{
		final DeliveryOrderBuilder deliveryOrderBuilder = prepareDeliveryOrderBuilder();
		final DerKurierDeliveryDataBuilder derKurierDeliveryDataBuilder = prepatreDerKurierDeliveryDataBuilder();

		final DeliveryOrder deliveryOrder = deliveryOrderBuilder
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(5)
						.packageIds(ImmutableList.of(1, 2, 3, 4, 5))
						.grossWeightKg(1)
						.customDeliveryData(derKurierDeliveryDataBuilder.parcelNumber("parcelnumber1").build())
						.build())
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(1)
						.packageIds(ImmutableList.of(6, 7))
						.grossWeightKg(2)
						.customDeliveryData(derKurierDeliveryDataBuilder.parcelNumber("parcelnumber2").build())
						.build())
				.build();
		return deliveryOrder;
	}

	public static DerKurierDeliveryDataBuilder prepatreDerKurierDeliveryDataBuilder()
	{
		final DerKurierDeliveryDataBuilder derKurierDeliveryDataBuilder = DerKurierDeliveryData
				.builder()
				.customerNumber("customerNumber-12345")
				.station("030");
		return derKurierDeliveryDataBuilder;
	}

	public static DeliveryOrderBuilder prepareDeliveryOrderBuilder()
	{
		final DeliveryOrderBuilder deliveryOrderBuilder = DeliveryOrder.builder()
				.orderId(OrderId.of(SHIPPER_GATEWAY_ID, "1234"))
				.serviceType(DerKurierServiceType.OVERNIGHT)
				.pickupAddress(Address.builder()
						.companyName1("from company")
						.street1("street 1")
						.houseNo("1")
						.zipCode("12345")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.of(2018, Month.JANUARY, 8))
						.build())
				.deliveryDate(DeliveryDate.builder()
						.date(LocalDate.of(2018, Month.JANUARY, 9))
						.timeFrom(LocalTime.of(9, 0))
						.timeTo(LocalTime.of(17, 30))
						.build())
				.deliveryAddress(Address.builder()
						.companyName1("to company")
						.companyDepartment("leer")
						.street1("street 1")
						.street2("street 2")
						.houseNo("1")
						.zipCode("54321")
						.city("Köln")
						.country(COUNTRY_CODE_DE)
						.build())
				.customerReference("some info for customer");
		return deliveryOrderBuilder;
	}

	public static RoutingRequest createRoutingRequest()
	{
		final RequestParticipant sender = RequestParticipant.builder()
				.country("DE")
				.zip("50969")
				.timeFrom(LocalTime.of(10, 20, 30, 40)) // here we provide localtime up to the nano-second, but the web-service only wants hour:minute
				.timeTo(LocalTime.of(11, 21, 31, 41))
				.build();

		final RoutingRequest routingRequest = RoutingRequest.builder()
				.sendDate(LocalDate.now())
				.sender(sender)
				.build();
		return routingRequest;
	}
}
