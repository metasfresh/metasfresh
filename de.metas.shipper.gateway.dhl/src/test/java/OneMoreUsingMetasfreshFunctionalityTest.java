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

import de.metas.shipper.gateway.dhl.DhlDeliveryOrderRepository;
import de.metas.shipper.gateway.dhl.DhlShipperGatewayClient;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class OneMoreUsingMetasfreshFunctionalityTest
{
	private DhlDeliveryOrderRepository dhlDeliveryOrderRepository;

	private DhlShipperGatewayClient client;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init(); // how do i add adempiere Test Helper?
		dhlDeliveryOrderRepository = new DhlDeliveryOrderRepository();
		client = new DhlShipperGatewayClient(DhlClientConfig.builder()
				.baseUrl("https://cig.dhl.de/services/sandbox/soap")
				.applicationID("a") // secret
				.applicationToken("b") // secret
				.accountNumber("22222222220104")
				.signature("pass")
				.username("2222222222_01")
				.build());
	}

	@Test
	void testDeliveryOrderPersistence()
	{
		final DeliveryOrder originalDO = dhlDeliveryOrderRepository.save(DhlTestUtil.createDummyDeliveryOrder());

		final DeliveryOrder deserialisedDO = dhlDeliveryOrderRepository.getByRepoId(DeliveryOrderId.ofRepoId(originalDO.getRepoId()));
		assertEquals(originalDO, deserialisedDO);
	}

	@Test
	void createDOPersistThenSendItToDHL()
	{
		// persist the DO
		final DeliveryOrder deliveryOrder = dhlDeliveryOrderRepository.save(DhlTestUtil.createDummyDeliveryOrder());

		final DeliveryOrderId deliveryOrderRepoId = DeliveryOrderId.ofRepoId(deliveryOrder.getRepoId());
		final DeliveryOrder deserialisedDO = dhlDeliveryOrderRepository.getByRepoId(deliveryOrderRepoId);

		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(deserialisedDO);
//		deliveryOrderRepo.save(completedDeliveryOrder);
//
//		final List<PackageLabels> packageLabelsList = client.getPackageLabelsList(completedDeliveryOrder);
//		printLabels(completedDeliveryOrder, packageLabelsList, deliveryOrderRepo);
	}
}
