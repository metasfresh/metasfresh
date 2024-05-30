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
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactoryTestWrapper;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class OrderReceiptScheduleProducerTest extends ReceiptScheduleTestBase
{
	protected IReceiptScheduleProducer orderReceiptScheduleProducer;

	@Override
	protected void setup()
	{
		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory = new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		orderReceiptScheduleProducer = receiptScheduleProducerFactory.createProducer(I_C_Order.Table_Name, false);
	}

	protected void assertOrderMatches(final I_M_ReceiptSchedule rs, final I_C_Order fromOrder)
	{
		assertThat(rs.getAD_Org_ID()).as("AD_Org_IDs do not match").isEqualTo(fromOrder.getAD_Org_ID());
		assertThat(rs.getAD_Client_ID()).as("AD_Client_IDs do not match").isEqualTo(fromOrder.getAD_Client_ID());
		assertThat(rs.getC_Order_ID()).as("C_Order_IDs do not match").isEqualTo(fromOrder.getC_Order_ID());

		assertThat(rs.getC_BPartner_ID()).as("C_BPartner_IDs do not match").isEqualTo(fromOrder.getC_BPartner_ID());
		assertThat(rs.getC_BPartner_Location_ID()).as("C_BPartner_Location_IDs do not match").isEqualTo(fromOrder.getC_BPartner_Location_ID());
		assertThat(rs.getAD_User_ID()).as("AD_User_IDs do not match").isEqualTo(fromOrder.getAD_User_ID());

		assertThat(rs.getDeliveryRule()).as("DeliveryRule do not match").isEqualTo(fromOrder.getDeliveryRule());
		assertThat(rs.getDeliveryViaRule()).as("DeliveryViaRule do not match").isEqualTo(fromOrder.getDeliveryViaRule());
		assertThat(rs.getPriorityRule()).as("PriorityRule do not match").isEqualTo(fromOrder.getPriorityRule());

		assertThat(rs.getM_Warehouse_ID()).as("M_Warehouse_IDs do not match").isEqualTo(fromOrder.getM_Warehouse_ID());

		assertThat(rs.getDateOrdered()).as("DateOrdered do not match").isEqualTo(fromOrder.getDateOrdered());
		assertThat(rs.getMovementDate()).as("MovementDate do not match").isEqualTo(fromOrder.getDatePromised());
		assertThat(rs.getPOReference()).as("POReference do not match").isEqualTo(fromOrder.getPOReference());
	}

	protected void assertOrderLineMatches(final I_M_ReceiptSchedule rs, final I_C_OrderLine fromOrderLine)
	{
		assertThat(rs.getAD_Org_ID()).as("AD_Org_IDs do not match").isEqualTo(fromOrderLine.getAD_Org_ID());
		assertThat(rs.getAD_Client_ID()).as("AD_Client_IDs do not match").isEqualTo(fromOrderLine.getAD_Client_ID());
		assertThat(rs.getC_Order_ID()).as("C_Order_IDs do not match").isEqualTo(fromOrderLine.getC_Order_ID());
		assertThat(rs.getC_OrderLine_ID()).as("C_OrderLine_IDs do not match").isEqualTo(fromOrderLine.getC_OrderLine_ID());

		assertThat(rs.getC_BPartner_ID()).as("C_BPartner_IDs do not match").isEqualTo(fromOrderLine.getC_BPartner_ID());
		assertThat(rs.getC_BPartner_Location_ID()).as("C_BPartner_Location_IDs do not match").isEqualTo(fromOrderLine.getC_BPartner_Location_ID());

		assertThat(rs.getM_Warehouse_ID()).as("M_Warehouse_IDs do not match").isEqualTo(fromOrderLine.getM_Warehouse_ID());

		assertThat(receiptScheduleBL.getQtyMoved(rs)).as("QtyDelivered do not match").isEqualTo(fromOrderLine.getQtyDelivered());
		assertThat(rs.getQtyOrdered()).as("QtyOrdered do not match").isEqualTo(fromOrderLine.getQtyOrdered());

		assertThat(rs.getM_Product_ID()).as("M_Product_ID do not match").isEqualTo(fromOrderLine.getM_Product_ID());
		assertThat(rs.getC_UOM_ID()).as("C_UOM_ID do not match").isEqualTo(fromOrderLine.getC_UOM_ID());

		assertOrderLineDimensionMatches(rs, fromOrderLine);
	}

	private static void assertOrderLineDimensionMatches(final I_M_ReceiptSchedule rs, final I_C_OrderLine fromOrderLine)
	{
		final Dimension rsDimensionExpected = new OrderLineDimensionFactory()
				.getFromRecord(fromOrderLine)
				.toBuilder()
				.user1_ID(0)
				.user2_ID(0)
				.build();

		final Dimension rsDimension = new ReceiptScheduleDimensionFactoryTestWrapper().getFromRecord(rs);
		assertThat(rsDimension).usingRecursiveComparison().isEqualTo(rsDimensionExpected);
		assertThat(rsDimension).isEqualTo(rsDimensionExpected);
	}

	@Test
	public void createReceiptSchedulesTestNullPrev()
	{
		final I_C_Order order = createOrder(warehouse1);
		createOrderLine(order, product1_wh1); // we don't care for product
		final List<I_M_ReceiptSchedule> rcs = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, ImmutableList.of());
		assertThat(rcs).hasSize(1);
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
		assertThat(rcs).hasSize(1);
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
		assertThat(rcs).hasSize(1);
	}

	@Test
	public void dataInOrderValidateTest()
	{
		final I_C_Order order = createOrder(warehouse1);
		final I_C_OrderLine orderLine = createOrderLine(order, product1_wh1);
		orderLine.setC_Project_ID(9001);
		orderLine.setC_Campaign_ID(9002);
		orderLine.setC_Activity_ID(9003);
		orderLine.setC_OrderSO_ID(9004);
		orderLine.setM_SectionCode_ID(9005);
		orderLine.setUser1_ID(9006);
		orderLine.setUser2_ID(9007);
		orderLine.setUserElementString1("UserElementString1");
		orderLine.setUserElementString2("UserElementString2");
		orderLine.setUserElementString3("UserElementString3");
		orderLine.setUserElementString4("UserElementString4");
		orderLine.setUserElementString5("UserElementString5");
		orderLine.setUserElementString6("UserElementString6");
		orderLine.setUserElementString7("UserElementString7");
		InterfaceWrapperHelper.save(orderLine);

		final List<I_M_ReceiptSchedule> receiptSchedules = orderReceiptScheduleProducer.createOrUpdateReceiptSchedules(order, ImmutableList.of());
		assertThat(receiptSchedules).hasSize(1);

		final I_M_ReceiptSchedule receiptSchedule = receiptSchedules.get(0);
		assertOrderMatches(receiptSchedule, order);
		assertOrderLineMatches(receiptSchedule, orderLine);
	}
}
