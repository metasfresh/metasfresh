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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.util.Services;

public class ReceiptScheduleProducerFactoryTest extends ReceiptScheduleTestBase
{
	protected IReceiptScheduleProducerFactory receiptScheduleProducer;

	@Override
	protected void setup()
	{
		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory = new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		Services.registerService(IReceiptScheduleProducerFactory.class, receiptScheduleProducerFactory);
		
		receiptScheduleProducer = Services.get(IReceiptScheduleProducerFactory.class);
	}

	@Test
	public void createReceiptSchedulesTestNullPrev()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1);
		IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		List<I_M_ReceiptSchedule> rcs = producer.createOrUpdateReceiptSchedules(order, Collections.<I_M_ReceiptSchedule> emptyList());
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

		final IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		final List<I_M_ReceiptSchedule> rcs = producer.createOrUpdateReceiptSchedules(order, receiptSchedules);

		Assert.assertEquals(1, rcs.size());
	}

	@Test
	public void createReceiptSchedulesTestMorePrev()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1);

		List<I_M_ReceiptSchedule> receiptSchedules = new ArrayList<>();
		I_M_ReceiptSchedule rc = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 12);
		I_M_ReceiptSchedule rc2 = createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 12);
		I_M_ReceiptSchedule rc3 = createReceiptSchedule(bpartner1, warehouse1, date, product2_wh1, 12);
		receiptSchedules.add(rc);
		receiptSchedules.add(rc2);
		receiptSchedules.add(rc3);

		IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		List<I_M_ReceiptSchedule> rcs = producer.createOrUpdateReceiptSchedules(order, receiptSchedules);
		Assert.assertEquals(1, rcs.size());
	}

	@Test
	public void dataInOrderLineValidateTest()
	{
		final I_C_Order order = createOrder(warehouse1);
		final I_C_OrderLine ol = createOrderLine(order, product1_wh1);

		IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		List<I_M_ReceiptSchedule> rcs = producer.createOrUpdateReceiptSchedules(order, Collections.<I_M_ReceiptSchedule> emptyList());
		Assert.assertEquals(1, rcs.size());
		I_M_ReceiptSchedule rc = rcs.get(0);
		Assert.assertEquals("AD_Org_IDs do not match", ol.getAD_Org_ID(), rc.getAD_Org_ID());
		Assert.assertEquals("AD_Client_IDs do not match", ol.getAD_Client_ID(), rc.getAD_Client_ID());
		Assert.assertEquals("C_BPartner_IDs do not match", ol.getC_BPartner_ID(), rc.getC_BPartner_ID());
		Assert.assertEquals("C_BPartner_Location_IDs do not match", ol.getC_BPartner_Location_ID(), rc.getC_BPartner_Location_ID());
		Assert.assertEquals("C_Order_IDs do not match", ol.getC_Order_ID(), rc.getC_Order_ID());
		Assert.assertEquals("M_Warehouse_IDs do not match", ol.getM_Warehouse_ID(), rc.getM_Warehouse_ID());
		Assert.assertEquals("QtyDelivereds do not match", ol.getQtyDelivered(), receiptScheduleBL.getQtyMoved(rc));
		Assert.assertEquals("QtyOrdereds do not match", ol.getQtyOrdered(), rc.getQtyOrdered());

	}

	@Test
	public void dataInOrderValidateTest()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1);

		IReceiptScheduleProducer producer = receiptScheduleProducer.createProducer(I_C_Order.Table_Name, false);
		List<I_M_ReceiptSchedule> rcs = producer.createOrUpdateReceiptSchedules(order, Collections.<I_M_ReceiptSchedule> emptyList());
		Assert.assertEquals(1, rcs.size());
		I_M_ReceiptSchedule rc = rcs.get(0);
		Assert.assertEquals("AD_Org_IDs do not match", order.getAD_Org_ID(), rc.getAD_Org_ID());
		Assert.assertEquals("AD_Client_IDs do not match", order.getAD_Client_ID(), rc.getAD_Client_ID());
		Assert.assertEquals("AD_User_IDs do not match", order.getAD_User_ID(), rc.getAD_User_ID());
		Assert.assertEquals("C_BPartner_IDs do not match", order.getC_BPartner_ID(), rc.getC_BPartner_ID());
		Assert.assertEquals("C_BPartner_Location_IDs do not match", order.getC_BPartner_Location_ID(), rc.getC_BPartner_Location_ID());
		Assert.assertEquals("C_Order_IDs do not match", order.getC_Order_ID(), rc.getC_Order_ID());
		Assert.assertEquals("DeliveryRules do not match", order.getDeliveryRule(), rc.getDeliveryRule());
		Assert.assertEquals("M_Warehouse_IDs do not match", order.getM_Warehouse_ID(), rc.getM_Warehouse_ID());
	}

}
