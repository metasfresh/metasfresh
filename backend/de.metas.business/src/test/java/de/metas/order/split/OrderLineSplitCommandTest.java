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

import de.metas.document.engine.IDocumentBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
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
import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class OrderLineSplitCommandTest
{
	private IOrderLineBL orderLineBL;
	private IOrderBL orderBL;
	private IOrderDAO orderDAO;
	private IDocumentBL documentBL;
	private IOrderLineSplitListener splitListener;
	private OrderLineSplitCommand command;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();

		orderLineBL = Mockito.mock(IOrderLineBL.class);
		orderBL = Mockito.mock(IOrderBL.class);
		orderDAO = Mockito.mock(IOrderDAO.class);
		documentBL = Mockito.mock(IDocumentBL.class);
		splitListener = Mockito.mock(IOrderLineSplitListener.class);

		command = new OrderLineSplitCommand(orderLineBL, orderBL, orderDAO, documentBL, splitListener);
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

	/**
	 * Happy-path test using AdempiereTestHelper (in-memory POJOWrapper environment).
	 * <p>
	 * Scenario: CO order, line ordered=10, delivered=8.
	 * Split qtyToSplitOff=2 → original.QtyEntered=8, new line QtyEntered=2, new line C_Project_ID=0.
	 * <p>
	 * NOTE: interceptors do NOT fire in the AdempiereTestHelper environment (no Spring context),
	 * so QtyOrdered is NOT recomputed from QtyEntered. This test validates field-copying, qty
	 * reduction, and project clearing — the coordination logic — not interceptor side-effects.
	 */
	@Test
	void splitsLineSuccessfully_whenAllValidationsPass()
	{
		// Given: completed order with a line that has project set, qty=10, delivered=8
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus("CO");
		order.setIsSOTrx(true);
		save(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final I_C_OrderLine originalLine = newInstance(I_C_OrderLine.class);
		originalLine.setC_Order_ID(order.getC_Order_ID());
		originalLine.setQtyOrdered(new BigDecimal("10"));
		originalLine.setQtyEntered(new BigDecimal("10"));
		originalLine.setQtyDelivered(new BigDecimal("8"));
		originalLine.setQtyInvoiced(BigDecimal.ZERO);
		originalLine.setLine(10);
		originalLine.setC_Project_ID(42);
		originalLine.setPriceEntered(new BigDecimal("5.00"));
		originalLine.setPriceActual(new BigDecimal("5.00"));
		originalLine.setPriceList(new BigDecimal("6.00"));
		originalLine.setPriceLimit(new BigDecimal("4.00"));
		originalLine.setDiscount(new BigDecimal("0"));
		originalLine.setLineNetAmt(new BigDecimal("50.00"));
		save(originalLine);

		final OrderLineId originalLineId = OrderLineId.ofRepoId(originalLine.getC_OrderLine_ID());

		// Mock the DAO/BL that are not really used via in-memory helpers
		when(orderLineBL.getOrderLineById(originalLineId)).thenReturn(originalLine);
		when(orderBL.getById(orderId)).thenReturn(order);
		// orderDAO.retrieveOrderLines returns just the original line (for computeNextLineNo)
		when(orderDAO.retrieveOrderLines(orderId)).thenReturn(Collections.singletonList(originalLine));

		// When
		final OrderLineSplitResult result = command.split(OrderLineSplitRequest.builder()
				.orderLineId(originalLineId)
				.qtyToSplitOff(new BigDecimal("2"))
				.build());

		// Then: result IDs are populated
		assertThat(result.getOriginalOrderLineId()).isEqualTo(originalLineId);
		assertThat(result.getNewOrderLineId()).isNotNull();
		assertThat(result.getNewOrderLineId()).isNotEqualTo(originalLineId);

		// Original line: QtyEntered reduced from 10 to 8
		final I_C_OrderLine reloadedOriginal = load(originalLine.getC_OrderLine_ID(), I_C_OrderLine.class);
		assertThat(reloadedOriginal.getQtyEntered()).isEqualByComparingTo(new BigDecimal("8"));

		// New line: QtyEntered = 2, C_Project_ID = 0, pricing copied from original
		final I_C_OrderLine newLine = load(result.getNewOrderLineId().getRepoId(), I_C_OrderLine.class);
		assertThat(newLine.getQtyEntered()).isEqualByComparingTo(new BigDecimal("2"));
		assertThat(newLine.getQtyDelivered()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(newLine.getC_Project_ID()).isZero();
		assertThat(newLine.getPriceEntered()).isEqualByComparingTo(new BigDecimal("5.00"));
		assertThat(newLine.getPriceActual()).isEqualByComparingTo(new BigDecimal("5.00"));
		assertThat(newLine.getLine()).isEqualTo(20); // next step-10 after line 10

		// Listener was called to shrink reservations on the original line.
		// Shipment-schedule + invoice-candidate creation for the new line is delegated
		// to the order-completion cascade (verified at cucumber level on CI).
		Mockito.verify(splitListener).onOriginalLineReduced(originalLineId);

		// IDocumentBL is invoked twice: reactivate (before the line edits) + re-complete (after).
		Mockito.verify(documentBL).processEx(order, "RE");
		Mockito.verify(documentBL).processEx(order, "CO");
	}
}
