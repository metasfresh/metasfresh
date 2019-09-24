package de.metas.handlingunits.receiptschedule.integrationtest;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Arrays;

import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.shipper.gateway.commons.ShipperGatewayServicesRegistry;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		StartupListener.class,
		ShutdownListener.class,
		ShipperGatewayServicesRegistry.class})
public class HUReceiptProcess_StandardCase_IntegrationTest extends AbstractHUReceiptProcessIntegrationTest
{
	private void prepareMasterData()
	{
		piTU_Item_Product.setQty(new BigDecimal("10")); // Qty CU/TU
		InterfaceWrapperHelper.save(piTU_Item_Product);

		piLU_Item.setQty(new BigDecimal("2")); // Qty TU/LU
		InterfaceWrapperHelper.save(piLU_Item);
	}

	@Override
	protected void step10_createOrders()
	{
		prepareMasterData();

		final BigDecimal qty = new BigDecimal("20");
		final BigDecimal qtyTUExpected = new BigDecimal("2"); // 20/10

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class, contextGlobal);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setC_BPartner_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
		order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		order.setAD_Org_ID(warehouse.getAD_Org_ID());
		InterfaceWrapperHelper.save(order);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, order);
		orderLine.setC_Order(order);
		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setC_UOM_ID(productUOM.getC_UOM_ID());
		orderLine.setQtyEntered(qty);
		orderLine.setQtyOrdered(qty);
		orderLine.setM_HU_PI_Item_Product_ID(piTU_Item_Product.getM_HU_PI_Item_Product_ID());
		orderLine.setQtyEnteredTU(qtyTUExpected);
		InterfaceWrapperHelper.save(orderLine);

		orders = Arrays.asList(order);
		orderLines = Arrays.asList(orderLine);
	}

	@Override
	protected void step20_createReceiptSchedules()
	{
		super.step20_createReceiptSchedules();
	}

	@Override
	protected void step20_createReceiptSchedules_ValidateCreatedReceiptSchedules()
	{
		// final I_M_ReceiptSchedule receiptSchedule =
		assertSingletonAndGet("receipt schedules", receiptSchedules);
	}
}
