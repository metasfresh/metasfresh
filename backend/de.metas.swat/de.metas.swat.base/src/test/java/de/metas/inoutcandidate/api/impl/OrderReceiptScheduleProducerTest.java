package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class OrderReceiptScheduleProducerTest extends ReceiptScheduleTestBase
{
	protected IReceiptScheduleProducer orderReceiptScheduleProducer;

	@Override
	protected void setup()
	{
		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory = new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		Services.registerService(IReceiptScheduleProducerFactory.class, receiptScheduleProducerFactory);

		orderReceiptScheduleProducer = createReceiptScheduleProducer();
	}

	protected IReceiptScheduleProducer createReceiptScheduleProducer()
	{
		return Services.get(IReceiptScheduleProducerFactory.class)
				.createProducer(I_C_Order.Table_Name, false);
	}

	protected void assertOrderMatches(final I_M_ReceiptSchedule rc, final I_C_Order fromOrder)
	{
		Assertions.assertEquals( fromOrder.getAD_Org_ID(),  rc.getAD_Org_ID(), "AD_Org_IDs do not match");
		Assertions.assertEquals( fromOrder.getAD_Client_ID(),  rc.getAD_Client_ID(), "AD_Client_IDs do not match");
		Assertions.assertEquals( fromOrder.getC_Order_ID(),  rc.getC_Order_ID(), "C_Order_IDs do not match");

		Assertions.assertEquals( fromOrder.getC_BPartner_ID(),  rc.getC_BPartner_ID(), "C_BPartner_IDs do not match");
		Assertions.assertEquals( fromOrder.getC_BPartner_Location_ID(),  rc.getC_BPartner_Location_ID(), "C_BPartner_Location_IDs do not match");
		Assertions.assertEquals( fromOrder.getAD_User_ID(),  rc.getAD_User_ID(), "AD_User_IDs do not match");

		Assertions.assertEquals( fromOrder.getDeliveryRule(),  rc.getDeliveryRule(), "DeliveryRule do not match");
		Assertions.assertEquals( fromOrder.getDeliveryViaRule(),  rc.getDeliveryViaRule(), "DeliveryViaRule do not match");
		Assertions.assertEquals( fromOrder.getPriorityRule(),  rc.getPriorityRule(), "PriorityRule do not match");

		Assertions.assertEquals( fromOrder.getM_Warehouse_ID(),  rc.getM_Warehouse_ID(), "M_Warehouse_IDs do not match");

		Assertions.assertEquals( fromOrder.getDateOrdered(),  rc.getDateOrdered(), "DateOrdered do not match");
		Assertions.assertEquals( fromOrder.getDatePromised(),  rc.getMovementDate(), "MovementDate do not match");
		Assertions.assertEquals( fromOrder.getPOReference(),  rc.getPOReference(), "POReference do not match");
	}

	protected void assertOrderLineMatches(final I_M_ReceiptSchedule rc, final I_C_OrderLine fromOrderLine)
	{
		Assertions.assertEquals( fromOrderLine.getAD_Org_ID(),  rc.getAD_Org_ID(), "AD_Org_IDs do not match");
		Assertions.assertEquals( fromOrderLine.getAD_Client_ID(),  rc.getAD_Client_ID(), "AD_Client_IDs do not match");
		Assertions.assertEquals( fromOrderLine.getC_Order_ID(),  rc.getC_Order_ID(), "C_Order_IDs do not match");
		Assertions.assertEquals( fromOrderLine.getC_OrderLine_ID(),  rc.getC_OrderLine_ID(), "C_OrderLine_IDs do not match");

		Assertions.assertEquals( fromOrderLine.getC_BPartner_ID(),  rc.getC_BPartner_ID(), "C_BPartner_IDs do not match");
		Assertions.assertEquals( fromOrderLine.getC_BPartner_Location_ID(),  rc.getC_BPartner_Location_ID(), "C_BPartner_Location_IDs do not match");

		Assertions.assertEquals( fromOrderLine.getM_Warehouse_ID(),  rc.getM_Warehouse_ID(), "M_Warehouse_IDs do not match");

		Assertions.assertEquals( fromOrderLine.getQtyDelivered(),  receiptScheduleBL.getQtyMoved(rc), "QtyDelivereds do not match");
		Assertions.assertEquals( fromOrderLine.getQtyOrdered(),  rc.getQtyOrdered(), "QtyOrdereds do not match");

		Assertions.assertEquals( fromOrderLine.getM_Product_ID(),  rc.getM_Product_ID(), "M_Product_ID do not match");
		Assertions.assertEquals( fromOrderLine.getC_UOM_ID(),  rc.getC_UOM_ID(), "C_UOM_ID do not match");
	}

	@Test
	public void createReceiptSchedulesTestNullPrev()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1); // we don't care for product
		final List<I_M_ReceiptSchedule> rcs = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, ImmutableList.of());
		Assertions.assertEquals(1, rcs.size());
	}

	@Test
	public void createReceiptSchedulesTestOnePrev()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1);
		final List<I_M_ReceiptSchedule> receiptSchedules = new ArrayList<>();
		final I_M_ReceiptSchedule rc = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 12);
		receiptSchedules.add(rc);
		final List<I_M_ReceiptSchedule> rcs = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, receiptSchedules);
		Assertions.assertEquals(1, rcs.size());
	}

	@Test
	public void createReceiptSchedulesTestMorePrev()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1);
		final List<I_M_ReceiptSchedule> receiptSchedules = new ArrayList<>();
		final I_M_ReceiptSchedule rc = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 12);
		final I_M_ReceiptSchedule rc2 = createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 12);
		final I_M_ReceiptSchedule rc3 = createReceiptSchedule(bpartner1, warehouse1, date, product2_wh1, 12);
		receiptSchedules.add(rc);
		receiptSchedules.add(rc2);
		receiptSchedules.add(rc3);

		final List<I_M_ReceiptSchedule> rcs = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, receiptSchedules);
		Assertions.assertEquals(1, rcs.size());
	}

	@Test
	public void dataInOrderValidateTest()
	{
		final I_C_Order order = createOrder(warehouse1);
		final I_C_OrderLine ol = createOrderLine(order, product1_wh1);

		final List<I_M_ReceiptSchedule> rcs = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, ImmutableList.of());
		Assertions.assertEquals(1, rcs.size());

		final I_M_ReceiptSchedule rc = rcs.getFirst();
		assertOrderMatches(rc, order);
		assertOrderLineMatches(rc, ol);
	}
}
