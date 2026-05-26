/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.split;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderLineSplitCommandTest
{
	private IOrderLineBL orderLineBL;
	private IOrderBL orderBL;
	private OrderLineSplitCommand command;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();

		orderLineBL = Mockito.mock(IOrderLineBL.class);
		orderBL = Mockito.mock(IOrderBL.class);
		command = new OrderLineSplitCommand(orderLineBL, orderBL);
	}

	@Test
	void rejectsWhenOrderNotCompleted()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("DR");
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		line.setQtyDelivered(BigDecimal.ZERO);
		line.setQtyInvoiced(BigDecimal.ZERO);
		save(line);

		final OrderLineId lineId = OrderLineId.ofRepoId(line.getC_OrderLine_ID());
		when(orderLineBL.getOrderLineById(lineId)).thenReturn(line);
		when(orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()))).thenReturn(order);

		assertThatThrownBy(() -> command.split(OrderLineSplitRequest.builder()
				.orderLineId(lineId)
				.qtyToSplitOff(new BigDecimal("2"))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("OrderLineSplit_OrderNotCompleted");
	}

	@Test
	void rejectsWhenQtyTooLarge()
	{
		// order CO, line ordered=10, try split qtyToSplitOff=10 (must be strictly < 10)
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		line.setQtyDelivered(BigDecimal.ZERO);
		line.setQtyInvoiced(BigDecimal.ZERO);
		save(line);

		final OrderLineId lineId = OrderLineId.ofRepoId(line.getC_OrderLine_ID());
		when(orderLineBL.getOrderLineById(lineId)).thenReturn(line);
		when(orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()))).thenReturn(order);

		assertThatThrownBy(() -> command.split(OrderLineSplitRequest.builder()
				.orderLineId(lineId)
				.qtyToSplitOff(new BigDecimal("10"))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("OrderLineSplit_QtyTooLarge");
	}

	@Test
	void rejectsWhenBelowDelivered()
	{
		// order CO, line ordered=10, delivered=6, split qtyToSplitOff=5 → newQty=5 < delivered=6
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		line.setQtyDelivered(new BigDecimal("6"));
		line.setQtyInvoiced(BigDecimal.ZERO);
		save(line);

		final OrderLineId lineId = OrderLineId.ofRepoId(line.getC_OrderLine_ID());
		when(orderLineBL.getOrderLineById(lineId)).thenReturn(line);
		when(orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()))).thenReturn(order);

		assertThatThrownBy(() -> command.split(OrderLineSplitRequest.builder()
				.orderLineId(lineId)
				.qtyToSplitOff(new BigDecimal("5"))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("OrderLineSplit_QtyBelowDelivered");
	}

	@Test
	void rejectsWhenBelowInvoiced()
	{
		// order CO, line ordered=10, invoiced=6, split qtyToSplitOff=5 → newQty=5 < invoiced=6
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setIsSOTrx(true);
		save(order);

		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setQtyOrdered(new BigDecimal("10"));
		line.setQtyDelivered(BigDecimal.ZERO);
		line.setQtyInvoiced(new BigDecimal("6"));
		save(line);

		final OrderLineId lineId = OrderLineId.ofRepoId(line.getC_OrderLine_ID());
		when(orderLineBL.getOrderLineById(lineId)).thenReturn(line);
		when(orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()))).thenReturn(order);

		assertThatThrownBy(() -> command.split(OrderLineSplitRequest.builder()
				.orderLineId(lineId)
				.qtyToSplitOff(new BigDecimal("5"))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("OrderLineSplit_QtyBelowInvoiced");
	}
}
