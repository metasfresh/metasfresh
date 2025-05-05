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

package de.metas.shipper.gateway.dhl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.CurrencyRepository;
import de.metas.customs.CustomsInvoiceRepository;
import de.metas.handlingunits.impl.InOutPackageRepository;
import de.metas.location.CountryCode;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.commons.ShipperTestHelper;
import de.metas.shipper.gateway.dhl.logger.DhlDatabaseClientLogger;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlClientConfigRepository;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.dhl.model.DhlShipperProduct;
import de.metas.shipper.gateway.dhl.model.I_Dhl_ShipmentOrder_Log;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@UtilityClass
class DhlTestHelper
{
	static final String USER_NAME = "sandy_sandbox";
	static final String PASSWORD = "pass";
	static final String APP_TOKEN = "TXzU0LrbOdFj3xAW7CMD7kGvyhz2Uowj";

	private static final CountryCode COUNTRY_CODE_DE = CountryCode.builder().alpha2("DE").alpha3("DEU").build();
	private static final CountryCode COUNTRY_CODE_CH = CountryCode.builder().alpha2("CH").alpha3("CHE").build();
	private static final CountryCode COUNTRY_CODE_AT = CountryCode.builder().alpha2("AT").alpha3("AUT").build();

	// special account number, depending on target country. why dhl why?????
	static final String ACCOUNT_NUMBER_DE = "22222222220104";
	static final String ACCOUNT_NUMBER_AT = "22222222225301";
	static final String ACCOUNT_NUMBER_CH = "22222222225301";
	public static final String BASE_URL = "https://api-sandbox.dhl.com";
	public static final PackageId PACKAGE_ID_1 = PackageId.ofRepoId(1);

	static DeliveryOrder createDummyDeliveryOrderDEtoDE()
	{
		return DeliveryOrder.builder()
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
						.build())
				// receiver
				.deliveryAddress(Address.builder()
						.companyName1("DHL Packet gmbh")
						.companyName2("The Second Receiver Company Name")
						.street1("Charles-de-Gaulle-Str.")
						.houseNo("20")
						.zipCode("53113")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.deliveryContact(ContactPerson.builder()
						.emailAddress("tbp@tbp.com")
						.simplePhoneNumber("+10-012-345689")
						.build())
				.deliveryOrderLines(ImmutableList.of(DeliveryOrderLine.builder()
						.packageDimensions(PackageDimensions.builder()
								.heightInCM(10)
								.lengthInCM(10)
								.widthInCM(10)
								.build())
						.packageId(PACKAGE_ID_1)
						.grossWeightKg(BigDecimal.ONE)
						.build()))
				.customerReference(null)
				.shipperProduct(DhlShipperProduct.Dhl_Paket)
				.shipperId(ShipperId.ofRepoId(1))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(1))
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
						.street2("Street Name 2 ")
						.houseNo("10")
						.zipCode("53129")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.now().plusDays(1)) // always tomorrow!
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
						.emailAddress("tbp@tbp.com")
						.simplePhoneNumber("+10-012-345689")
						.build())
				.deliveryOrderLines(ImmutableList.of(DeliveryOrderLine.builder()
						.packageDimensions(PackageDimensions.builder()
								.heightInCM(10)
								.lengthInCM(10)
								.widthInCM(10)
								.build())
						.packageId(PACKAGE_ID_1)
						.grossWeightKg(BigDecimal.ONE)
						.build()))
				.customerReference(null)
				.shipperProduct(DhlShipperProduct.Dhl_PaketInternational)
				.shipperId(ShipperId.ofRepoId(1))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(1))
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
						.street2("Street Name 2 ")
						.houseNo("10")
						.zipCode("53129")
						.city("Bonn")
						.country(COUNTRY_CODE_DE)
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.now().plusDays(1)) // always tomorrow!
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
						.emailAddress("tbp@tbp.com")
						.simplePhoneNumber("+10-012-345689")
						.build())
				.deliveryOrderLines(ImmutableList.of(DeliveryOrderLine.builder()
						.packageDimensions(PackageDimensions.builder()
								.heightInCM(10)
								.lengthInCM(10)
								.widthInCM(10)
								.build())
						.packageId(PACKAGE_ID_1)
						.grossWeightKg(BigDecimal.ONE)
						.build()))
				.customerReference(null)
				.shipperProduct(DhlShipperProduct.Dhl_PaketInternational)
				.shipperId(ShipperId.ofRepoId(1))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(1))
				.build();
	}

	void testAllSteps(final DeliveryOrder initialDummyDeliveryOrder, final String accountNumber)
	{
		//
		// create all structures

		final DhlClientConfigRepository clientConfigRepository = new DhlClientConfigRepository();
		final DhlDraftDeliveryOrderCreator draftDeliveryOrderCreator = new DhlDraftDeliveryOrderCreator(clientConfigRepository, new CustomsInvoiceRepository());
		final DhlDeliveryOrderRepository orderRepository = new DhlDeliveryOrderRepository();
		final DhlDeliveryOrderService orderService = new DhlDeliveryOrderService(new InOutPackageRepository(), new CurrencyRepository(), orderRepository);

		final UomId dummyUom = UomId.ofRepoId(1);

		final DhlClientConfig config = DhlClientConfig.builder()
				.baseUrl(BASE_URL)
				.applicationID(USER_NAME)
				.applicationToken(APP_TOKEN)
				.accountNumber(accountNumber) // special account number, depending on target country. why dhl why?????
				.signature(PASSWORD)
				.username(USER_NAME)
				.lengthUomId(dummyUom)
				.trackingUrlBase("dummy")
				.build();
		final RestTemplate restTemplateBuilder = new RestTemplateBuilder()
				.rootUri(config.getBaseUrl())
				.basicAuthentication(config.getUsername(), config.getSignature())
				.build();
		final DhlShipperGatewayClient client = new DhlShipperGatewayClient(config,
				DhlDatabaseClientLogger.instance, restTemplateBuilder);

		//
		// check 1: draft DO <->> initial dummy DO
		final DeliveryOrder draftDeliveryOrder = createDraftDeliveryOrderFromDummy(initialDummyDeliveryOrder, draftDeliveryOrderCreator);
		assertEquals("nothing should be changed", initialDummyDeliveryOrder, draftDeliveryOrder);

		//
		// check 2: persisted DO <-> initial dummy DO => create updatedDummy DO
		final DeliveryOrder persistedDeliveryOrder = orderRepository.save(draftDeliveryOrder);
		DeliveryOrder updatedDummyDeliveryOrder = initialDummyDeliveryOrder.toBuilder()
				.id(persistedDeliveryOrder.getId())
				.build();
		assertNull(updatedDummyDeliveryOrder.getCustomDeliveryData());
		assertEquals("only the repoId should change after the first persistence", updatedDummyDeliveryOrder, persistedDeliveryOrder);

		//
		// check 3: updated Dummy DO <-> retrieved DO from persistence
		final DeliveryOrder deserialisedDO = orderService.getByRepoId(updatedDummyDeliveryOrder.getId());
		DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.builder()
				.detail(extractPackageIdAndSequenceNumberFromDO(deserialisedDO, PACKAGE_ID_1))
				.build();
		updatedDummyDeliveryOrder = updatedDummyDeliveryOrder.toBuilder()
				.customDeliveryData(customDeliveryData)
				.build();
		assertEquals("only packageId and SequenceNumber should be modified", updatedDummyDeliveryOrder, deserialisedDO);

		//
		// check 4: run Client.completeDeliveryOrder
		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(deserialisedDO);
		customDeliveryData = DhlCustomDeliveryData.builder()
				.detail(extractFieldsAfterCompleteDeliveryOrder(customDeliveryData, completedDeliveryOrder, PACKAGE_ID_1))
				.build();
		updatedDummyDeliveryOrder = updatedDummyDeliveryOrder.toBuilder()
				.customDeliveryData(customDeliveryData)
				.build();
		assertEquals("only awb, pdf label data and tracking url should be modified", updatedDummyDeliveryOrder, completedDeliveryOrder);
		assertSizeOfCustomDeliveryData(completedDeliveryOrder);

		//
		// check 5: persist the completed delivery order: nothing should be modified
		final DeliveryOrder savedCompletedDeliveryOrder = orderRepository.save(completedDeliveryOrder);
		assertEquals("nothing should be modified", updatedDummyDeliveryOrder, savedCompletedDeliveryOrder);
		assertSizeOfCustomDeliveryData(savedCompletedDeliveryOrder);

		//
		// check 6: retrieve the persisted completed DO. nothing should be modified
		final DeliveryOrder deserialisedCompletedDeliveryOrder = orderService.getByRepoId(updatedDummyDeliveryOrder.getId());
		assertEquals("nothing should be modified", updatedDummyDeliveryOrder, deserialisedCompletedDeliveryOrder);
		assertSizeOfCustomDeliveryData(deserialisedCompletedDeliveryOrder);

		//
		// check 7: expect 1 database log: the one for createShipment
		assertEquals("there should be 1 database request logs", 1, Services.get(IQueryBL.class)
				.createQueryBuilder(I_Dhl_ShipmentOrder_Log.class)
				.create()
				.count());

		//
		// check 8: there's no way to test that label printing => create attachment works. :(
	}

	private void assertSizeOfCustomDeliveryData(@NonNull final DeliveryOrder deliveryOrder)
	{
		assertNotNull(deliveryOrder.getCustomDeliveryData());
		assertEquals(5, DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getDetails().size());
	}

	private DhlCustomDeliveryDataDetail extractFieldsAfterCompleteDeliveryOrder(@NonNull final DhlCustomDeliveryData customDeliveryData, @NonNull final DeliveryOrder completedDeliveryOrder, final PackageId packageId)
	{
		assertNotNull(completedDeliveryOrder.getCustomDeliveryData());

		final DhlCustomDeliveryDataDetail dhlCustomDeliveryDataDetail = DhlCustomDeliveryData.cast(completedDeliveryOrder.getCustomDeliveryData()).getDetailByPackageId(packageId);
		final String awb = dhlCustomDeliveryDataDetail.getAwb();
		final byte[] pdfLabelData = dhlCustomDeliveryDataDetail.getPdfLabelData();
		final String trackingUrl = dhlCustomDeliveryDataDetail.getTrackingUrl();
		assertNotNull(awb);
		assertNotNull(pdfLabelData);
		assertNotNull(trackingUrl);

		final DhlCustomDeliveryDataDetail detailByPackageId = customDeliveryData.getDetailByPackageId(packageId);
		return detailByPackageId.toBuilder()
				.awb(awb)
				.pdfLabelData(pdfLabelData)
				.trackingUrl(trackingUrl)
				.build();
	}

	private DhlCustomDeliveryDataDetail extractPackageIdAndSequenceNumberFromDO(@NonNull final DeliveryOrder deliveryOrder, final PackageId packageId)
	{
		//noinspection ConstantConditions
		final DhlSequenceNumber sequenceNumber = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getDetailByPackageId(packageId).getSequenceNumber();
		assertNotNull(sequenceNumber);
		return DhlCustomDeliveryDataDetail.builder()
				.packageId(packageId)
				.sequenceNumber(sequenceNumber)
				.build();
	}

	private DeliveryOrder createDraftDeliveryOrderFromDummy(@NonNull final DeliveryOrder deliveryOrder, @NonNull final DhlDraftDeliveryOrderCreator draftDeliveryOrderCreator)
	{
		final DeliveryOrderLine firstDeliveryOrderLine = deliveryOrder.getDeliveryOrderLines().get(0);

		//
		final Set<PackageId> packageIds = ImmutableSet.of(firstDeliveryOrderLine.getPackageId());

		//
		final I_C_BPartner pickupFromBPartner = ShipperTestHelper.createBPartner(deliveryOrder.getPickupAddress());
		final I_C_Location pickupFromLocation = ShipperTestHelper.createLocation(deliveryOrder.getPickupAddress());
		final LocalDate pickupDate = deliveryOrder.getPickupDate().getDate();

		//
		final I_C_BPartner deliverToBPartner = ShipperTestHelper.createBPartner(deliveryOrder.getDeliveryAddress());
		//noinspection deprecation,ConstantConditions
		deliverToBPartner.setEMail(deliveryOrder.getDeliveryContact().getEmailAddress());
		final I_C_Location deliverToLocation = ShipperTestHelper.createLocation(deliveryOrder.getDeliveryAddress());
		final int deliverToBPartnerLocationId = 0;
		final String deliverToPhoneNumber = deliveryOrder.getDeliveryContact().getSimplePhoneNumber();

		//
		final DhlShipperProduct detectedServiceType = (DhlShipperProduct)deliveryOrder.getShipperProduct();
		final BigDecimal grossWeightInKg = firstDeliveryOrderLine.getGrossWeightKg();
		final ShipperId shipperId = deliveryOrder.getShipperId();
		final ShipperTransportationId shipperTransportationId = deliveryOrder.getShipperTransportationId();
		final PackageDimensions packageDimensions = firstDeliveryOrderLine.getPackageDimensions();

		final CustomDeliveryData customDeliveryData = null;

		final String customerReference = null;

		//noinspection ConstantConditions
		return draftDeliveryOrderCreator.createDeliveryOrderFromParams(
				packageIds,
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				deliverToBPartner,
				//deliverToBPartnerLocationId,
				deliverToLocation,
				deliverToPhoneNumber,
				detectedServiceType,
				grossWeightInKg,
				shipperId,
				customerReference,
				shipperTransportationId,
				packageDimensions,
				customDeliveryData);
	}
}
