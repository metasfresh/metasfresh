/*
 * #%L
 * de.metas.shipper.gateway.dpd
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
import com.jgoodies.common.base.Strings;
import de.metas.attachments.AttachmentEntryService;
import de.metas.shipper.gateway.commons.ShipperTestHelper;
import de.metas.shipper.gateway.dpd.logger.DpdDatabaseClientLogger;
import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import de.metas.shipper.gateway.dpd.model.DpdClientConfigRepository;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder_Log;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipper.gateway.spi.model.ServiceType;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Disabled("Makes ACTUAL calls to DPD api and needs auth")
public class IntegrationDEtoATTest
{
	private static final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

	private final DpdDraftDeliveryOrderCreator draftDeliveryOrderCreator = new DpdDraftDeliveryOrderCreator(new DpdClientConfigRepository());
	private final DpdDeliveryOrderRepository orderRepository = new DpdDeliveryOrderRepository(attachmentEntryService);
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

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@DisplayName("Delivery Order DE -> AT + test persistence after all steps")
	void testAllSteps()
	{
		// check 1: draft DO <->> initial dummy DO
		final DeliveryOrder initialDummyDeliveryOrder = DpdTestHelper.createDummyDeliveryOrderDEtoAT();
		final DeliveryOrder draftDeliveryOrder = createDraftDeliveryOrderFromDummy(initialDummyDeliveryOrder);
		assertEquals("nothing should be changed", initialDummyDeliveryOrder, draftDeliveryOrder);
		assertEquals(5, draftDeliveryOrder.getDeliveryOrderLines().size());

		//
		// check 2: persisted DO <-> initial dummy DO => create updatedDummy DO
		final DeliveryOrder persistedDeliveryOrder = orderRepository.save(draftDeliveryOrder);
		DeliveryOrder updatedDummyDeliveryOrder = initialDummyDeliveryOrder.toBuilder()
				.repoId(persistedDeliveryOrder.getRepoId())
				.build();
		assertNotNull(updatedDummyDeliveryOrder.getCustomDeliveryData());
		assertEquals("only the repoId should change after the first persistence", updatedDummyDeliveryOrder, persistedDeliveryOrder);
		assertEquals(5, persistedDeliveryOrder.getDeliveryOrderLines().size());
		assertEquals(5, updatedDummyDeliveryOrder.getDeliveryOrderLines().size());

		//
		// check 3: updated Dummy DO <-> retrieved DO from persistence
		final DeliveryOrder deserialisedDO = orderRepository.getByRepoId(updatedDummyDeliveryOrder.getRepoId());
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
		final DeliveryOrder deserialisedCompletedDeliveryOrder = orderRepository.getByRepoId(updatedDummyDeliveryOrder.getRepoId());
		assertEquals(5, updatedDummyDeliveryOrder.getDeliveryOrderLines().size());
		assertEquals(5, deserialisedCompletedDeliveryOrder.getDeliveryOrderLines().size());
		assertEquals("nothing should be modified", updatedDummyDeliveryOrder, deserialisedCompletedDeliveryOrder);
		assertTrue(Strings.isNotBlank(deserialisedCompletedDeliveryOrder.getTrackingNumber()));
		//noinspection ConstantConditions
		assertTrue(DpdOrderCustomDeliveryData.cast(deserialisedCompletedDeliveryOrder.getCustomDeliveryData()).getPdfData().length > 1);

		//
		// check 7: check the attachment exists
		final TableRecordReference deliveryOrderRef = TableRecordReference.of(I_DPD_StoreOrder.Table_Name, deserialisedCompletedDeliveryOrder.getRepoId().getRepoId());
		assertNotNull(attachmentEntryService.getByFilenameOrNull(deliveryOrderRef, deserialisedCompletedDeliveryOrder.getTrackingNumber() + ".pdf"));

		//
		// check 8: expect 2 database logs: 1 for login and 2 for createShipment
		assertEquals("there should be 2 database request logs", 2, Services.get(IQueryBL.class)
				.createQueryBuilder(I_DPD_StoreOrder_Log.class)
				.create()
				.count());

		ShipperTestHelper.dumpPdfToDisk(customDeliveryData.getPdfData());
	}

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
		final int deliverToBPartnerLocationId = 0;
		final String deliverToPhoneNumber = deliveryOrder.getDeliveryContact().getSimplePhoneNumber();

		//
		final ServiceType detectedServiceType = deliveryOrder.getServiceType();
		final ShipperId shipperId = deliveryOrder.getShipperId();
		final ShipperTransportationId shipperTransportationId = deliveryOrder.getShipperTransportationId();

		//
		final ImmutableList<DeliveryOrderLine> deliveryOrderLines = deliveryOrder.getDeliveryOrderLines();

		//noinspection OptionalGetWithoutIsPresent
		final Integer allPackagesGrossWeightKg = deliveryOrderLines.stream().map(DeliveryOrderLine::getGrossWeightKg).reduce(Integer::sum).get();

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
				deliverToBPartnerLocationId,
				deliverToLocation,
				deliverToPhoneNumber,
				detectedServiceType,
				allPackagesGrossWeightKg,
				shipperId,
				shipperTransportationId,
				customerReference,
				DpdOrderCustomDeliveryData.cast(customDeliveryData),
				deliveryOrderLines
		);
	}
}
