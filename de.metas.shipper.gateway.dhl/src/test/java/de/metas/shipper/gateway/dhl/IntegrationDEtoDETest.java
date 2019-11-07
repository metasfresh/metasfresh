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

import de.metas.attachments.AttachmentEntryService;
import de.metas.customs.CustomsInvoiceRepository;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.dhl.logger.DhlDatabaseClientLogger;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlClientConfigRepository;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.dhl.model.DhlSequenceNumber;
import de.metas.shipper.gateway.dhl.model.DhlServiceType;
import de.metas.shipper.gateway.spi.ShipperTestHelper;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.ShipperTransportationId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class IntegrationDEtoDETest
{
	private static final String USER_NAME = "a";
	private static final String PASSWORD = "b";

	private DhlDraftDeliveryOrderCreator draftDeliveryOrderCreator;
	private final DhlDeliveryOrderRepository orderRepository = new DhlDeliveryOrderRepository(AttachmentEntryService.createInstanceForUnitTesting());
	private DhlShipperGatewayClient client;

	private final UomId dummyUom = UomId.ofRepoId(1);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DhlClientConfigRepository clientConfigRepository = new DhlClientConfigRepository();
		draftDeliveryOrderCreator = new DhlDraftDeliveryOrderCreator(clientConfigRepository, new CustomsInvoiceRepository());

		client = new DhlShipperGatewayClient(DhlClientConfig.builder()
				.baseUrl("https://cig.dhl.de/services/sandbox/soap")
				.applicationID(USER_NAME)
				.applicationToken(PASSWORD)
				.accountNumber("22222222220104") // special account number, depending on target country. why dhl why?????
				.signature("pass")
				.username("2222222222_01")
				.lengthUomId(dummyUom)
				.trackingUrlBase("dummy")
				.build(),
				DhlDatabaseClientLogger.instance);
	}

	@Test
	@DisplayName("Delivery Order DE -> DE + test persistence after all steps")
	void DEtoDEDraftDeliveryOrderCreatorAndPersistence()
	{
		// check 1: draft DO <->> initial dummy DO
		final DeliveryOrder initialDummyDeliveryOrder = DhlTestHelper.createDummyDeliveryOrderDEtoDE();
		final DeliveryOrder draftDeliveryOrder = createDraftDeliveryOrderFromDummy(initialDummyDeliveryOrder);
		assertEquals("nothing should be changed", initialDummyDeliveryOrder, draftDeliveryOrder);

		//
		// check 2: persisted DO <-> initial dummy DO => create updatedDummy DO
		final DeliveryOrder persistedDeliveryOrder = orderRepository.save(draftDeliveryOrder);
		DeliveryOrder updatedDummyDeliveryOrder = initialDummyDeliveryOrder.toBuilder()
				.repoId(persistedDeliveryOrder.getRepoId())
				.build();
		assertNull(updatedDummyDeliveryOrder.getCustomDeliveryData());
		assertEquals("only the repoId should change after the first persistence", updatedDummyDeliveryOrder, persistedDeliveryOrder);

		//
		// check 3: updated Dummy DO <-> retrieved DO from persistence
		final DeliveryOrder deserialisedDO = orderRepository.getByRepoId(updatedDummyDeliveryOrder.getRepoId());
		DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.builder()
				.detail(extractPackageIdAndSequenceNumberFromDO(deserialisedDO, 1))
				.detail(extractPackageIdAndSequenceNumberFromDO(deserialisedDO, 2))
				.detail(extractPackageIdAndSequenceNumberFromDO(deserialisedDO, 3))
				.detail(extractPackageIdAndSequenceNumberFromDO(deserialisedDO, 4))
				.detail(extractPackageIdAndSequenceNumberFromDO(deserialisedDO, 5))
				.build();
		updatedDummyDeliveryOrder = updatedDummyDeliveryOrder.toBuilder()
				.customDeliveryData(customDeliveryData)
				.build();
		assertEquals("only packageId and SequenceNumber should be modified", updatedDummyDeliveryOrder, deserialisedDO);

		//
		// check 4: run Client.completeDeliveryOrder
		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(deserialisedDO);
		customDeliveryData = DhlCustomDeliveryData.builder()
				.detail(extractFieldsAfterCompleteDeliveryOrder(customDeliveryData, completedDeliveryOrder, 1))
				.detail(extractFieldsAfterCompleteDeliveryOrder(customDeliveryData, completedDeliveryOrder, 2))
				.detail(extractFieldsAfterCompleteDeliveryOrder(customDeliveryData, completedDeliveryOrder, 3))
				.detail(extractFieldsAfterCompleteDeliveryOrder(customDeliveryData, completedDeliveryOrder, 4))
				.detail(extractFieldsAfterCompleteDeliveryOrder(customDeliveryData, completedDeliveryOrder, 5))
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
		final DeliveryOrder deserialisedCompletedDeliveryOrder = orderRepository.getByRepoId(updatedDummyDeliveryOrder.getRepoId());
		assertEquals("nothing should be modified", updatedDummyDeliveryOrder, deserialisedCompletedDeliveryOrder);
		assertSizeOfCustomDeliveryData(deserialisedCompletedDeliveryOrder);
	}

	private void assertSizeOfCustomDeliveryData(@NonNull final DeliveryOrder deliveryOrder)
	{
		assertNotNull(deliveryOrder.getCustomDeliveryData());
		assertEquals(5, DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getDetails().size());
	}

	private DhlCustomDeliveryDataDetail extractFieldsAfterCompleteDeliveryOrder(@NonNull final DhlCustomDeliveryData customDeliveryData, @NonNull final DeliveryOrder completedDeliveryOrder, final int packageId)
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

	private DhlCustomDeliveryDataDetail extractPackageIdAndSequenceNumberFromDO(@NonNull final DeliveryOrder deliveryOrder, final int packageId)
	{
		//noinspection ConstantConditions
		final DhlSequenceNumber sequenceNumber = DhlCustomDeliveryData.cast(deliveryOrder.getCustomDeliveryData()).getDetailByPackageId(packageId).getSequenceNumber();
		assertNotNull(sequenceNumber);
		return DhlCustomDeliveryDataDetail.builder()
				.packageId(packageId)
				.sequenceNumber(sequenceNumber)
				.build();
	}

	private DeliveryOrder createDraftDeliveryOrderFromDummy(@NonNull final DeliveryOrder deliveryOrder)
	{
		final DeliveryPosition deliveryPosition = deliveryOrder.getDeliveryPositions().get(0);

		//
		final Set<PackageId> mpackageIds = deliveryPosition.getPackageIds();

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
		final DhlServiceType detectedServiceType = (DhlServiceType)deliveryOrder.getServiceType();
		final int grossWeightInKg = deliveryPosition.getGrossWeightKg();
		final ShipperId shipperId = deliveryOrder.getShipperId();
		final ShipperTransportationId shipperTransportationId = deliveryOrder.getShipperTransportationId();
		final PackageDimensions packageDimensions = deliveryPosition.getPackageDimensions();

		final CustomDeliveryData customDeliveryData = null;

		final String customerReference = null;

		//noinspection ConstantConditions
		return draftDeliveryOrderCreator.createDeliveryOrderFromParams(
				mpackageIds,
				pickupFromBPartner,
				pickupFromLocation,
				pickupDate,
				deliverToBPartner,
				deliverToBPartnerLocationId,
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
