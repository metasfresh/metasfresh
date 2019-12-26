package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;
import java.util.List;

import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.uom.UomId;
import de.metas.util.Services;

public class OrderLineReceiptScheduleProducerTest extends ReceiptScheduleTestBase
{
	private IReceiptScheduleProducerFactory receiptScheduleProducer;

	@Override
	protected void setup()
	{
		receiptScheduleProducer = Services.get(IReceiptScheduleProducerFactory.class);
	}

	/**
	 * Regression Test for http://dewiki908/mediawiki/index.php/05940_Wareneingang_Lagerumbuchung fresh needs the destination warehouse to be set from m_product. This is currently implemented in
	 * swat, but might change there. This test is in fresh to make sure we notice that sth's wrong for fresh when things are changes in swat.
	 */
	@Test
	public void createReceiptScheduleFromOrderLine()
	{
		final I_M_Warehouse orderWarehouse = createWarehouse("WH_Order");

		final I_M_Warehouse productWarehouse = createWarehouse("WH_Product");
		final I_M_Locator productWarehouseLocator = createLocator(productWarehouse);

		final I_C_UOM stockUOMRecord = BusinessTestHelper.createUOM("StockUOM");
		final I_M_Product product = createProduct("Test Product", UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()), productWarehouseLocator);

		final I_C_Order order = createOrder(orderWarehouse);
		createOrderLine(order, product);

		final IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		final List<I_M_ReceiptSchedule> receiptSched = producer.createOrUpdateReceiptSchedules(order, Collections.<I_M_ReceiptSchedule> emptyList());

		Assert.assertEquals("Invalid M_Warehouse_ID", orderWarehouse.getM_Warehouse_ID(), receiptSched.get(0).getM_Warehouse_ID());
		Assert.assertEquals("Invalid M_Warehouse_Override_ID", 0, receiptSched.get(0).getM_Warehouse_Override_ID());
		Assert.assertEquals("Invalid M_Warehouse_Dest_ID", productWarehouse.getM_Warehouse_ID(), receiptSched.get(0).getM_Warehouse_Dest_ID());
	}
}
