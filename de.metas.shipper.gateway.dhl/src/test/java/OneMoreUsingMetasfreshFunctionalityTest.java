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
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("makes ACTUAL calls to dhl api and needs auth")
class OneMoreUsingMetasfreshFunctionalityTest
{
	//	ShipperGatewayFacade shipperGatewayFacade;
	//	ShipperGatewayServicesRegistry shipperGatewayServicesRegistry = SpringContextHolder.instance.getBean(ShipperGatewayServicesRegistry.class);
	//	private DhlDraftDeliveryOrderCreator dhlDraftDeliveryOrderCreator;
	private DhlDeliveryOrderRepository dhlDeliveryOrderRepository;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init(); // how do i add adempiere Test Helper?
		//		dhlDraftDeliveryOrderCreator = new DhlDraftDeliveryOrderCreator();
		dhlDeliveryOrderRepository = new DhlDeliveryOrderRepository();
	}

	@Test
	void createDOPersistThenSendItToDHL()
	{
		// not sure how to create the DraftDeliveryOrderRequest yet
		//		dhlDraftDeliveryOrderCreator.createDraftDeliveryOrder()

		// persist the DO
		final DeliveryOrder deliveryOrder = dhlDeliveryOrderRepository.save(DhlTestUtil.createDummyDeliveryOrder());

		System.out.println(deliveryOrder.getRepoId());
		System.out.println(deliveryOrder);
		final DeliveryOrderId deliveryOrderRepoId = DeliveryOrderId.ofRepoId(deliveryOrder.getRepoId());

		// todo implement retrieving the DO from PO
		System.out.println(dhlDeliveryOrderRepository.getByRepoId(deliveryOrderRepoId));

	}
}
