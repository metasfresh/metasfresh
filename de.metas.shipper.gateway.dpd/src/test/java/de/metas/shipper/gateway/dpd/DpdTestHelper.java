/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOS E. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.shipper.gateway.dpd;

import com.google.common.collect.ImmutableList;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dpd.model.DpdNotificationChannel;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.DpdOrderType;
import de.metas.shipper.gateway.dpd.model.DpdPaperFormat;
import de.metas.shipper.gateway.dpd.model.DpdServiceType;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.ShipperTransportationId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@UtilityClass
class DpdTestHelper
{
	static final String LOGIN_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/LoginService/V2_0/";
	static final String SHIPMENT_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/";

	static final String DELIS_ID = "sandboxdpd";
	static final String DELIS_PASSWORD = "a";

	private static final CountryCode COUNTRY_CODE_DE = CountryCode.builder().alpha2("DE").alpha3("DEU").build();
	private static final CountryCode COUNTRY_CODE_CH = CountryCode.builder().alpha2("CH").alpha3("CHE").build();
	private static final CountryCode COUNTRY_CODE_AT = CountryCode.builder().alpha2("AT").alpha3("AUT").build();

	static DeliveryOrder createDummyDeliveryOrderDEtoDE()
	{
		return DeliveryOrder.builder()
				// shipper
				.pickupAddress(Address.builder()
						.companyName1("TheBestPessimist Inc.")
						.companyName2("The Second Shipper Company Name")
						.street1("Eduard-Otto-Straße")
						.street2(null)
						.houseNo("10")
						.zipCode("53129")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.now().plusDays(1)) // always tomorrow!
						.timeFrom(LocalTime.of(12, 34))
						.timeTo(LocalTime.of(21, 9))
						.build())
				// receiver
				.deliveryAddress(Address.builder()
						.companyName1("Berlin National Library?????")
						.companyName2("")
						.street1("Unter den Linden")
						.houseNo("8")
						.zipCode("10117")
						.city("Berlin")
						.country(COUNTRY_CODE_DE)
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress("cristian.pasat@metasfresh.com")
						.simplePhoneNumber("+10-012-345689")
						.build())
				.deliveryOrderLines(createDeliveryOrderLines(ImmutableList.of(11, 22, 33, 44, 55)))
				.allPackagesGrossWeightInKg(5)
				.customerReference(null)
				.serviceType(DpdServiceType.DPD_CLASSIC)
				.shipperId(ShipperId.ofRepoId(1))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(1))
				.customDeliveryData(DpdOrderCustomDeliveryData.builder()
						.orderType(DpdOrderType.CONSIGNMENT)
						// .sendingDepot()// this is null and only set in the client, after login is done
						.paperFormat(DpdPaperFormat.PAPER_FORMAT_A6)
						.printerLanguage(DpdConstants.DEFAULT_PRINTER_LANGUAGE)
						.notificationChannel(DpdNotificationChannel.EMAIL)
						.build())
				.build();
	}

	static DeliveryOrder createDummyDeliveryOrderDEtoCH()
	{
		return DeliveryOrder.builder()
				// shipper
				.pickupAddress(Address.builder()
						.companyName1("TheBestPessimist Inc.")
						.companyName2("The Second Shipper Company Name")
						.street1("Eduard-Otto-Straße")
						.street2(null)
						.houseNo("10")
						.zipCode("53129")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.now().plusDays(1)) // always tomorrow!
						.timeFrom(LocalTime.of(12, 34))
						.timeTo(LocalTime.of(21, 9))
						.build())
				// international outside UE (CH) receiver
				.deliveryAddress(Address.builder()
						.companyName1("burker king")
						.companyName2("din lucerna")
						.street1("Zentralstrasse")
						.houseNo("3")
						.zipCode("6003")
						.city("Luzern")
						.country(COUNTRY_CODE_CH)
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress("cristian.pasat@metasfresh.com")
						.simplePhoneNumber("+10-012-345689")
						.build())
				.deliveryOrderLines(createDeliveryOrderLines(ImmutableList.of(11, 22, 33, 44, 55)))
				.allPackagesGrossWeightInKg(5)
				.customerReference(null)
				.serviceType(DpdServiceType.DPD_CLASSIC)
				.shipperId(ShipperId.ofRepoId(1))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(1))
				.customDeliveryData(DpdOrderCustomDeliveryData.builder()
						.orderType(DpdOrderType.CONSIGNMENT)
						// .sendingDepot()// this is null and only set in the client, after login is done
						.paperFormat(DpdPaperFormat.PAPER_FORMAT_A6)
						.printerLanguage(DpdConstants.DEFAULT_PRINTER_LANGUAGE)
						.notificationChannel(DpdNotificationChannel.EMAIL)
						.build())
				.build();
	}

	static DeliveryOrder createDummyDeliveryOrderDEtoAT()
	{
		return DeliveryOrder.builder()
				// shipper
				.pickupAddress(Address.builder()
						.companyName1("TheBestPessimist Inc.")
						.companyName2("The Second Shipper Company Name")
						.street1("Eduard-Otto-Straße")
						.street2(null)
						.houseNo("10")
						.zipCode("53129")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.now().plusDays(1)) // always tomorrow!
						.timeFrom(LocalTime.of(12, 34))
						.timeTo(LocalTime.of(21, 9))
						.build())
				// international inside UE (AT) receiver
				.deliveryAddress(Address.builder()
						.companyName1("NOVAPARK Wohlfühlhotel Graz")
						.companyName2("")
						.street1("Fischeraustraße")
						.houseNo("22")
						.zipCode("8051")
						.city("Graz")
						.country(COUNTRY_CODE_AT)
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress("cristian.pasat@metasfresh.com")
						.simplePhoneNumber("+10-012-345689")
						.build())
				.deliveryOrderLines(createDeliveryOrderLines(ImmutableList.of(11, 22, 33, 44, 55)))
				.allPackagesGrossWeightInKg(5)
				.customerReference(null)
				.serviceType(DpdServiceType.DPD_CLASSIC)
				.shipperId(ShipperId.ofRepoId(1))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(1))
				.customDeliveryData(DpdOrderCustomDeliveryData.builder()
						.orderType(DpdOrderType.CONSIGNMENT)
						// .sendingDepot()// this is null and only set in the client, after login is done
						.paperFormat(DpdPaperFormat.PAPER_FORMAT_A6)
						.printerLanguage(DpdConstants.DEFAULT_PRINTER_LANGUAGE)
						.notificationChannel(DpdNotificationChannel.EMAIL)
						.build())
				.build();
	}

	@NonNull
	private static List<DeliveryOrderLine> createDeliveryOrderLines(@NonNull final ImmutableList<Integer> packageIds)
	{
		final ImmutableList.Builder<DeliveryOrderLine> deliveryOrderLinesBuilder = ImmutableList.builder();
		for (final Integer packageId : packageIds)
		{
			final DeliveryOrderLine deliveryOrderLine = DeliveryOrderLine.builder()
					// .repoId()
					.content("the epic package " + packageId + " description")
					.grossWeightKg(1) // same as in de.metas.shipper.gateway.commons.ShipperGatewayFacade.computeGrossWeightInKg: we assume it's in Kg
					.packageDimensions(PackageDimensions.builder()
							.heightInCM(10)
							.lengthInCM(10)
							.widthInCM(10)
							.build())
					// .customDeliveryData()
					.packageId(PackageId.ofRepoId(packageId))
					.build();

			deliveryOrderLinesBuilder.add(deliveryOrderLine);
		}
		return deliveryOrderLinesBuilder.build();
	}

}
