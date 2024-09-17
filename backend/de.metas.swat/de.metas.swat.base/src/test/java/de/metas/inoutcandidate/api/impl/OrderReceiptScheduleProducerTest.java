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
import org.junit.Assert;
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
		Assert.assertEquals("AD_Org_IDs do not match", fromOrder.getAD_Org_ID(), rc.getAD_Org_ID());
		Assert.assertEquals("AD_Client_IDs do not match", fromOrder.getAD_Client_ID(), rc.getAD_Client_ID());
		Assert.assertEquals("C_Order_IDs do not match", fromOrder.getC_Order_ID(), rc.getC_Order_ID());

		Assert.assertEquals("C_BPartner_IDs do not match", fromOrder.getC_BPartner_ID(), rc.getC_BPartner_ID());
		Assert.assertEquals("C_BPartner_Location_IDs do not match", fromOrder.getC_BPartner_Location_ID(), rc.getC_BPartner_Location_ID());
		Assert.assertEquals("AD_User_IDs do not match", fromOrder.getAD_User_ID(), rc.getAD_User_ID());

		Assert.assertEquals("DeliveryRule do not match", fromOrder.getDeliveryRule(), rc.getDeliveryRule());
		Assert.assertEquals("DeliveryViaRule do not match", fromOrder.getDeliveryViaRule(), rc.getDeliveryViaRule());
		Assert.assertEquals("PriorityRule do not match", fromOrder.getPriorityRule(), rc.getPriorityRule());

		Assert.assertEquals("M_Warehouse_IDs do not match", fromOrder.getM_Warehouse_ID(), rc.getM_Warehouse_ID());

		Assert.assertEquals("DateOrdered do not match", fromOrder.getDateOrdered(), rc.getDateOrdered());
		Assert.assertEquals("MovementDate do not match", fromOrder.getDatePromised(), rc.getMovementDate());
		Assert.assertEquals("POReference do not match", fromOrder.getPOReference(), rc.getPOReference());
	}

	protected void assertOrderLineMatches(final I_M_ReceiptSchedule rc, final I_C_OrderLine fromOrderLine)
	{
		Assert.assertEquals("AD_Org_IDs do not match", fromOrderLine.getAD_Org_ID(), rc.getAD_Org_ID());
		Assert.assertEquals("AD_Client_IDs do not match", fromOrderLine.getAD_Client_ID(), rc.getAD_Client_ID());
		Assert.assertEquals("C_Order_IDs do not match", fromOrderLine.getC_Order_ID(), rc.getC_Order_ID());
		Assert.assertEquals("C_OrderLine_IDs do not match", fromOrderLine.getC_OrderLine_ID(), rc.getC_OrderLine_ID());

		Assert.assertEquals("C_BPartner_IDs do not match", fromOrderLine.getC_BPartner_ID(), rc.getC_BPartner_ID());
		Assert.assertEquals("C_BPartner_Location_IDs do not match", fromOrderLine.getC_BPartner_Location_ID(), rc.getC_BPartner_Location_ID());

		Assert.assertEquals("M_Warehouse_IDs do not match", fromOrderLine.getM_Warehouse_ID(), rc.getM_Warehouse_ID());

		Assert.assertEquals("QtyDelivereds do not match", fromOrderLine.getQtyDelivered(), receiptScheduleBL.getQtyMoved(rc));
		Assert.assertEquals("QtyOrdereds do not match", fromOrderLine.getQtyOrdered(), rc.getQtyOrdered());

		Assert.assertEquals("M_Product_ID do not match", fromOrderLine.getM_Product_ID(), rc.getM_Product_ID());
		Assert.assertEquals("C_UOM_ID do not match", fromOrderLine.getC_UOM_ID(), rc.getC_UOM_ID());
	}

	@Test
	public void createReceiptSchedulesTestNullPrev()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1); // we don't care for product
		final List<I_M_ReceiptSchedule> rcs = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, ImmutableList.of());
		Assert.assertEquals(1, rcs.size());
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
		Assert.assertEquals(1, rcs.size());
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
		Assert.assertEquals(1, rcs.size());
	}

	@Test
	public void dataInOrderValidateTest()
	{
		final I_C_Order order = createOrder(warehouse1);
		final I_C_OrderLine ol = createOrderLine(order, product1_wh1);

		final List<I_M_ReceiptSchedule> rcs = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, ImmutableList.of());
		Assert.assertEquals(1, rcs.size());

		final I_M_ReceiptSchedule rc = rcs.get(0);
		assertOrderMatches(rc, order);
		assertOrderLineMatches(rc, ol);
	}
}
