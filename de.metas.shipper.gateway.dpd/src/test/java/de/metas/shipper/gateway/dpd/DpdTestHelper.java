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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
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
import de.metas.shipper.gateway.dpd.model.DpdCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.DpdNotificationChannel;
import de.metas.shipper.gateway.dpd.model.DpdOrderType;
import de.metas.shipper.gateway.dpd.model.DpdPaperFormat;
import de.metas.shipper.gateway.dpd.model.DpdServiceType;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@UtilityClass
class DpdTestHelper
{
	public static final String LOGIN_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/LoginService/V2_0/";
	public static final String SHIPMENT_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/";

	private static final CountryCode COUNTRY_CODE_DE = CountryCode.builder().alpha2("DE").alpha3("DEU").build();
	private static final CountryCode COUNTRY_CODE_CH = CountryCode.builder().alpha2("CH").alpha3("CHE").build();
	private static final CountryCode COUNTRY_CODE_AT = CountryCode.builder().alpha2("AT").alpha3("AUT").build();

	static DeliveryOrder createDummyDeliveryOrderDEtoDE()
	{
		return DeliveryOrder.builder()
				.repoId(987654321)
				// shipper
				.pickupAddress(Address.builder()
						.companyName1("TheBestPessimist Inc.")
						.companyName2("The Second Shipper Company Name")
						.street1("Eduard-Otto-Straße")
						.street2("Street Name 2 ")
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
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(5)
						.packageDimensions(PackageDimensions.builder()
								.heightInCM(10)
								.lengthInCM(10)
								.widthInCM(10)
								.build())
						.packageIds(createPackageIDs())
						.grossWeightKg(1)
						.build())
				.customerReference(null)
				.serviceType(DpdServiceType.DPD_CLASSIC)
				.customDeliveryData(DpdCustomDeliveryData.builder()
						.orderType(DpdOrderType.CONSIGNMENT)
						// .sendingDepot()// this is null and only set in the client, after login is done
						.paperFormat(DpdPaperFormat.PAPER_FORMAT_A6)
						.printerLanguage(DpdConstants.DEFAULT_PRINTER_LANGUAGE)
						.notificationChannel(DpdNotificationChannel.EMAIL)
						.build())
				.build();
	}

	private static Iterable<? extends PackageId> createPackageIDs()
	{
		return Arrays.asList(11, 22, 33, 44, 55)
				.stream()
				.map(PackageId::ofRepoId)
				.collect(ImmutableList.toImmutableList());
	}

	static DeliveryOrder createDummyDeliveryOrderDEtoCH()
	{
		return DeliveryOrder.builder()
				.repoId(987654321)
				// shipper
				.pickupAddress(Address.builder()
						.companyName1("TheBestPessimist Inc.")
						.companyName2("The Second Shipper Company Name")
						.street1("Eduard-Otto-Straße")
						.street2("Street Name 2 ")
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
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(5)
						.packageDimensions(PackageDimensions.builder()
								.heightInCM(10)
								.lengthInCM(10)
								.widthInCM(10)
								.build())
						.packageIds(createPackageIDs())
						.grossWeightKg(1)
						.build())
				.customerReference(null)
				.serviceType(DpdServiceType.DPD_CLASSIC)
				.customDeliveryData(DpdCustomDeliveryData.builder()
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
				.repoId(987654321)
				// shipper
				.pickupAddress(Address.builder()
						.companyName1("TheBestPessimist Inc.")
						.companyName2("The Second Shipper Company Name")
						.street1("Eduard-Otto-Straße")
						.street2("Street Name 2 ")
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
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(5)
						.packageDimensions(PackageDimensions.builder()
								.heightInCM(10)
								.lengthInCM(10)
								.widthInCM(10)
								.build())
						.packageIds(createPackageIDs())
						.grossWeightKg(1)
						.build())
				.customerReference(null)
				.serviceType(DpdServiceType.DPD_CLASSIC)
				.customDeliveryData(DpdCustomDeliveryData.builder()
						.orderType(DpdOrderType.CONSIGNMENT)
						// .sendingDepot()// this is null and only set in the client, after login is done
						.paperFormat(DpdPaperFormat.PAPER_FORMAT_A6)
						.printerLanguage(DpdConstants.DEFAULT_PRINTER_LANGUAGE)
						.notificationChannel(DpdNotificationChannel.EMAIL)
						.build())
				.build();
	}

	static void dumpPdfToDisk(final byte[] pdf)
	{
		try
		{
			Files.write(Paths.get("C:", "a", Instant.now().toString().replace(":", ".") + ".pdf"), pdf);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

}
