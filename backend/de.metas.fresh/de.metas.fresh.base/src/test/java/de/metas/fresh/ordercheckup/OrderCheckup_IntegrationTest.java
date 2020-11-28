package de.metas.fresh.ordercheckup;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;

/**
 * Test case:
 * <ul>
 * <li>create an order with products
 * <li>generate {@link I_C_Order_MFGWarehouse_Report}s
 * <li>enqueue all reports to printing
 * <li>assert: reports were correctly generated
 * <li>assert: printing queue item has the right user to print set
 * </ul>
 *
 * @author tsa
 *
 */
public class OrderCheckup_IntegrationTest
{
	private OrderCheckupTestHelper helper;
	private Masterdata masterdata;

	@Before
	public void init()
	{
		helper = new OrderCheckupTestHelper();
		helper.init();

		masterdata = helper.createMasterdata();
	}

	@Test
	public void test()
	{
		final I_C_Order order = helper.createSalesOrder(masterdata.plant01.warehouse01.warehouse);

		final I_C_OrderLine ol1 = helper.createOrderLine(order, masterdata.plant01.warehouse01.product01);
		final I_C_OrderLine ol2 = helper.createOrderLine(order, masterdata.plant01.warehouse01.product02);
		final I_C_OrderLine ol3 = helper.createOrderLine(order, masterdata.plant01.warehouse02.product01);
		final I_C_OrderLine ol4 = helper.createOrderLine(order, masterdata.plant01.warehouse02.product02);
		//
		final I_C_OrderLine ol5 = helper.createOrderLine(order, masterdata.plant02.warehouse01.product01);
		final I_C_OrderLine ol6 = helper.createOrderLine(order, masterdata.plant02.warehouse01.product02);
		final I_C_OrderLine ol7 = helper.createOrderLine(order, masterdata.plant02.warehouse02.product01);
		final I_C_OrderLine ol8 = helper.createOrderLine(order, masterdata.plant02.warehouse02.product02);

		// Generate reports:
		helper.generateReportsAndEnqueueToPrinting(order);

		//
		// Expectations
		// masterdata.plant01.assertPlantReportOrderLines(ol1, ol2, ol3, ol4); // old expectation, now we expect ALL lines
		masterdata.plant01.assertPlantReportOrderLines(ol1, ol2, ol3, ol4, ol5, ol6, ol7, ol8);
		masterdata.plant01.warehouse01.assertWarehouseReportOrderLines(ol1, ol2);
		masterdata.plant01.warehouse02.assertWarehouseReportOrderLines(ol3, ol4);

		// masterdata.plant02.assertPlantReportOrderLines(ol5, ol6, ol7, ol8); // old expectation, now we expect ALL lines
		masterdata.plant01.assertPlantReportOrderLines(ol1, ol2, ol3, ol4, ol5, ol6, ol7, ol8);
		masterdata.plant02.warehouse01.assertWarehouseReportOrderLines(ol5, ol6);
		masterdata.plant02.warehouse02.assertWarehouseReportOrderLines(ol7, ol8);
	}

}
