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
import de.metas.attachments.AttachmentEntryService;
import de.metas.shipper.gateway.dhl.logger.DhlDatabaseClientLogger;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryData;
import de.metas.shipper.gateway.dhl.model.DhlCustomDeliveryDataDetail;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class IntegrationDEtoATTest
{
	private static final String USER_NAME = "a";
	private static final String PASSWORD = "b";

	private DhlDeliveryOrderRepository deliveryOrderRepository;

	private DhlShipperGatewayClient client;

	private final UomId dummyUom = UomId.ofRepoId(1);

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init(); // how do i add adempiere Test Helper?

		deliveryOrderRepository = new DhlDeliveryOrderRepository(AttachmentEntryService.createInstanceForUnitTesting());
		client = new DhlShipperGatewayClient(DhlClientConfig.builder()
				.baseUrl("https://cig.dhl.de/services/sandbox/soap")
				.applicationID(USER_NAME)
				.applicationToken(PASSWORD)
				.accountNumber("22222222225301") // special account number, depending on target country. why dhl why?????
				.signature("pass")
				.username("2222222222_01")
				.lengthUomId(dummyUom)
				.trackingUrlBase("dummy")
				.build(),
				DhlDatabaseClientLogger.instance);
	}

	//	@Disabled("this is broken currently and i have no idea how to fix it")
	//	@Test
	//	void testDeliveryOrderPersistence()
	//	{
	//		final DeliveryOrder originalDO = deliveryOrderRepository.save(DhlTestHelper.createDummyDeliveryOrderDEtoCH());
	//
	//		final DeliveryOrder deserialisedDO = deliveryOrderRepository.getByRepoId(DeliveryOrderId.ofRepoId(originalDO.getRepoId()));
	//		assertEquals(originalDO, deserialisedDO); // not equal because DeliveryOrder.customDeliveryData is changed
	//	}

	@Test
	void createDOPersistThenSendItToDHL()
	{
		// persist the DO
		final DeliveryOrder deliveryOrder = deliveryOrderRepository.save(DhlTestHelper.createDummyDeliveryOrderDEtoAT());

		final DeliveryOrderId deliveryOrderRepoId = DeliveryOrderId.ofRepoId(deliveryOrder.getRepoId());
		final DeliveryOrder deserialisedDO = deliveryOrderRepository.getByRepoId(deliveryOrderRepoId);

		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(deserialisedDO);
		final DeliveryOrder savedCompletedDeliveryOrder = deliveryOrderRepository.save(completedDeliveryOrder);

		final DhlCustomDeliveryData customDeliveryData = DhlCustomDeliveryData.cast(savedCompletedDeliveryOrder.getCustomDeliveryData());

		//noinspection ConstantConditions
		assertEquals(5, customDeliveryData.getDetails().size());

		//
		//		dumpPdfsToDisk(customDeliveryData.getDetails());
	}

	private void dumpPdfsToDisk(final ImmutableList<DhlCustomDeliveryDataDetail> details)
	{
		details.forEach(it -> {
			try
			{
				//noinspection ConstantConditions
				Files.write(Paths.get("C:", "a", Long.toString(System.currentTimeMillis()) + ".pdf"), it.getPdfLabelData());
			}
			catch (IOException ignore)
			{
			}
		});
	}
}
