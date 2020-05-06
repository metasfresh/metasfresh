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
import com.jgoodies.common.base.Strings;
import de.metas.location.CountryCode;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.commons.ShipperTestHelper;
import de.metas.shipper.gateway.dpd.logger.DpdDatabaseClientLogger;
import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import de.metas.shipper.gateway.dpd.model.DpdClientConfigRepository;
import de.metas.shipper.gateway.dpd.model.DpdNotificationChannel;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.DpdOrderType;
import de.metas.shipper.gateway.dpd.model.DpdPaperFormat;
import de.metas.shipper.gateway.dpd.model.DpdShipperProduct;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder_Log;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipper.gateway.spi.model.ShipperProduct;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@UtilityClass
class DpdTestHelper
{
	static final String LOGIN_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/LoginService/V2_0/";
	static final String SHIPMENT_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/";

	static final String DELIS_ID = "sandboxdpd";
	static final String DELIS_PASSWORD = "a";

	private static final CountryCode COUNTRY_CODE_DE = CountryCode.builder().alpha2("DE").alpha3("DEU").build();
	private static final CountryCode COUNTRY_CODE_AT = CountryCode.builder().alpha2("AT").alpha3("AUT").build();
	private static final CountryCode COUNTRY_CODE_CH = CountryCode.builder().alpha2("CH").alpha3("CHE").build();
	private static final CountryCode COUNTRY_CODE_US = CountryCode.builder().alpha2("US").alpha3("USA").build();

	private final DpdDraftDeliveryOrderCreator draftDeliveryOrderCreator = new DpdDraftDeliveryOrderCreator(new DpdClientConfigRepository());
	private final DpdDeliveryOrderRepository orderRepository = new DpdDeliveryOrderRepository();
	private final DpdShipperGatewayClient client = DpdShipperGatewayClient.builder()
			.config(DpdClientConfig.builder()
					.delisID(DpdTestHelper.DELIS_ID)
					.delisPassword(DpdTestHelper.DELIS_PASSWORD)
					.loginApiUrl(DpdTestHelper.LOGIN_SERVICE_API_URL)
					.shipmentServiceApiUrl(DpdTestHelper.SHIPMENT_SERVICE_API_URL)
					.trackingUrlBase(DpdConstants.TRACKING_URL)
					.build())
			.databaseLogger(DpdDatabaseClientLogger.instance)
			.build();

	static DeliveryOrder createDummyDeliveryOrderDEtoDE(final DpdShipperProduct serviceType)
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
				.customerReference(null)
				.shipperProduct(serviceType)
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

	static DeliveryOrder createDummyDeliveryOrderDEtoCH(final DpdShipperProduct serviceType)
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
				.customerReference(null)
				.shipperProduct(serviceType)
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

	static DeliveryOrder createDummyDeliveryOrderDEtoAT(final DpdShipperProduct serviceType)
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
				.customerReference(null)
				.shipperProduct(serviceType)
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

	static DeliveryOrder createDummyDeliveryOrderDEtoUS()
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
						.companyName1("Howard University")
						.companyName2("")
						.street1("Sixth Street NW")
						.houseNo("2400")
						.zipCode("20059")
						.city("Washington DC")
						.country(COUNTRY_CODE_US)
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress("cristian.pasat@metasfresh.com")
						.simplePhoneNumber("+10-012-345689")
						.build())
				.deliveryOrderLines(createDeliveryOrderLines(ImmutableList.of(11, 22, 33, 44, 55)))
				.customerReference(null)
				.shipperProduct(DpdShipperProduct.DPD_CLASSIC)
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

	// package level, for testing
	void testAllSteps(final DeliveryOrder initialDeliveryOrder)
	{
		// check 1: draft DO <->> initial dummy DO
		final DeliveryOrder draftDeliveryOrder = createDraftDeliveryOrderFromDummy(initialDeliveryOrder);
		assertEquals("nothing should be changed", initialDeliveryOrder, draftDeliveryOrder);
		assertEquals(5, draftDeliveryOrder.getDeliveryOrderLines().size());

		//
		// check 2: persisted DO <-> initial dummy DO => create updatedDummy DO
		final DeliveryOrder persistedDeliveryOrder = orderRepository.save(draftDeliveryOrder);
		DeliveryOrder updatedDummyDeliveryOrder = initialDeliveryOrder.toBuilder()
				.id(persistedDeliveryOrder.getId())
				.build();
		assertNotNull(updatedDummyDeliveryOrder.getCustomDeliveryData());
		assertEquals("only the repoId should change after the first persistence", updatedDummyDeliveryOrder, persistedDeliveryOrder);
		assertEquals(5, persistedDeliveryOrder.getDeliveryOrderLines().size());
		assertEquals(5, updatedDummyDeliveryOrder.getDeliveryOrderLines().size());

		//
		// check 3: updated Dummy DO <-> retrieved DO from persistence
		final DeliveryOrder deserialisedDO = orderRepository.getByRepoId(updatedDummyDeliveryOrder.getId());
		assertEquals("nothing should be changed", updatedDummyDeliveryOrder, deserialisedDO);
		assertEquals(5, deserialisedDO.getDeliveryOrderLines().size());

		//
		// check 4: run Client.completeDeliveryOrder
		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(deserialisedDO);
		assertNotNull(completedDeliveryOrder.getCustomDeliveryData());

		final DpdOrderCustomDeliveryData customDeliveryData = DpdOrderCustomDeliveryData.cast(updatedDummyDeliveryOrder.getCustomDeliveryData())
				.toBuilder()
				.pdfData(DpdOrderCustomDeliveryData.cast(completedDeliveryOrder.getCustomDeliveryData()).getPdfData())
				.build();
		updatedDummyDeliveryOrder = updatedDummyDeliveryOrder.toBuilder()
				.trackingNumber(completedDeliveryOrder.getTrackingNumber())
				.trackingUrl(completedDeliveryOrder.getTrackingUrl())
				.customDeliveryData(customDeliveryData)
				.build();
		assertEquals("only awb, pdf label data and tracking url should be modified", updatedDummyDeliveryOrder, completedDeliveryOrder);
		assertEquals(5, completedDeliveryOrder.getDeliveryOrderLines().size());
		assertEquals(5, updatedDummyDeliveryOrder.getDeliveryOrderLines().size());
		assertTrue(Strings.isNotBlank(completedDeliveryOrder.getTrackingNumber()));
		//noinspection ConstantConditions
		assertTrue(DpdOrderCustomDeliveryData.cast(completedDeliveryOrder.getCustomDeliveryData()).getPdfData().length > 1);

		//
		// check 5: persist the completed delivery order: nothing should be modified
		final DeliveryOrder savedCompletedDeliveryOrder = orderRepository.save(completedDeliveryOrder);
		assertEquals("nothing should be modified", updatedDummyDeliveryOrder, savedCompletedDeliveryOrder);
		assertEquals(5, savedCompletedDeliveryOrder.getDeliveryOrderLines().size());

		//
		// check 6: retrieve the persisted completed DO. nothing should be modified
		final DeliveryOrder deserialisedCompletedDeliveryOrder = orderRepository.getByRepoId(updatedDummyDeliveryOrder.getId());
		assertEquals(5, updatedDummyDeliveryOrder.getDeliveryOrderLines().size());
		assertEquals(5, deserialisedCompletedDeliveryOrder.getDeliveryOrderLines().size());
		assertEquals("nothing should be modified", updatedDummyDeliveryOrder, deserialisedCompletedDeliveryOrder);
		assertTrue(Strings.isNotBlank(deserialisedCompletedDeliveryOrder.getTrackingNumber()));
		//noinspection ConstantConditions
		assertTrue(DpdOrderCustomDeliveryData.cast(deserialisedCompletedDeliveryOrder.getCustomDeliveryData()).getPdfData().length > 1);

		//
		// check 7: expect 2 database logs: 1 for login and 2 for createShipment
		assertEquals("there should be 2 database request logs", 2, Services.get(IQueryBL.class)
				.createQueryBuilder(I_DPD_StoreOrder_Log.class)
				.create()
				.count());

		//
		// check 8: there's no way to test that label printing => create attachment works. :(
	}

	@SuppressWarnings("deprecation")
	@NonNull
	private DeliveryOrder createDraftDeliveryOrderFromDummy(@NonNull final DeliveryOrder deliveryOrder)
	{
		//
		final I_C_BPartner pickupFromBPartner = ShipperTestHelper.createBPartner(deliveryOrder.getPickupAddress());
		final I_C_Location pickupFromLocation = ShipperTestHelper.createLocation(deliveryOrder.getPickupAddress());
		final PickupDate pickupDate1 = deliveryOrder.getPickupDate();
		final LocalDate pickupDate = pickupDate1.getDate();
		final LocalTime timeFrom = pickupDate1.getTimeFrom();
		final LocalTime timeTo = pickupDate1.getTimeTo();

		//
		final I_C_BPartner deliverToBPartner = ShipperTestHelper.createBPartner(deliveryOrder.getDeliveryAddress());
		//noinspection deprecation,ConstantConditions
		deliverToBPartner.setEMail(deliveryOrder.getDeliveryContact().getEmailAddress());
		final I_C_Location deliverToLocation = ShipperTestHelper.createLocation(deliveryOrder.getDeliveryAddress());
		final String deliverToPhoneNumber = deliveryOrder.getDeliveryContact().getSimplePhoneNumber();

		//
		final ShipperProduct detectedShipperProduct = deliveryOrder.getShipperProduct();
		final ShipperId shipperId = deliveryOrder.getShipperId();
		final ShipperTransportationId shipperTransportationId = deliveryOrder.getShipperTransportationId();

		//
		final ImmutableList<DeliveryOrderLine> deliveryOrderLines = deliveryOrder.getDeliveryOrderLines();

		//
		final String customerReference = deliveryOrder.getCustomerReference();

		final CustomDeliveryData customDeliveryData = deliveryOrder.getCustomDeliveryData();
		assertNotNull(customDeliveryData);

		return draftDeliveryOrderCreator.createDeliveryOrderFromParams(
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				timeFrom,
				timeTo,
				deliverToBPartner,
				deliverToLocation,
				deliverToPhoneNumber,
				detectedShipperProduct,
				shipperId,
				shipperTransportationId,
				customerReference,
				DpdOrderCustomDeliveryData.cast(customDeliveryData),
				deliveryOrderLines
		);
	}

}
