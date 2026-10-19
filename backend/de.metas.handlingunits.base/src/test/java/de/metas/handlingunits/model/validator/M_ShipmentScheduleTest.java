package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleUpdater;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link M_ShipmentSchedule#updateEffectiveValues(de.metas.handlingunits.model.I_M_ShipmentSchedule)},
 * which pushes the shipment schedule's effective ordered qty back into its sales order line.
 */
class M_ShipmentScheduleTest
{
	private M_ShipmentSchedule interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = new M_ShipmentSchedule(mock(ShipmentScheduleInvalidateBL.class), mock(ShipmentScheduleUpdater.class));
	}

	/**
	 * A closed schedule's effective ordered qty is its delivered qty (0 here), not what the customer ordered.
	 * Copying it into the order line wiped a reactivated order's QtyOrdered to 0, so the reservation
	 * reconcile at re-completion saw nothing to shrink against.
	 */
	@Test
	void closedSchedule_doesNotOverwriteOrderLineQtyOrdered()
	{
		final I_C_OrderLine orderLine = createOrderLine(new BigDecimal("75"));
		final I_M_ShipmentSchedule schedule = createSchedule(orderLine, true);

		interceptor.updateEffectiveValues(schedule);

		assertThat(load(orderLine.getC_OrderLine_ID(), I_C_OrderLine.class).getQtyOrdered()).isEqualByComparingTo("75");
	}

	/**
	 * Pins the existing behaviour for an open schedule: the effective ordered qty is still propagated.
	 */
	@Test
	void openSchedule_propagatesEffectiveQtyOrderedToOrderLine()
	{
		final I_C_OrderLine orderLine = createOrderLine(new BigDecimal("75"));
		final I_M_ShipmentSchedule schedule = createSchedule(orderLine, false);
		schedule.setQtyOrdered_Override(new BigDecimal("80"));
		saveRecord(schedule);

		interceptor.updateEffectiveValues(schedule);

		assertThat(load(orderLine.getC_OrderLine_ID(), I_C_OrderLine.class).getQtyOrdered()).isEqualByComparingTo("80");
	}

	private static I_C_OrderLine createOrderLine(final BigDecimal qtyOrdered)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setQtyEntered(qtyOrdered);
		orderLine.setQtyOrdered(qtyOrdered);
		saveRecord(orderLine);
		return orderLine;
	}

	private static I_M_ShipmentSchedule createSchedule(final I_C_OrderLine orderLine, final boolean closed)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setAD_Table_ID(getTableId(I_C_OrderLine.class));
		schedule.setRecord_ID(orderLine.getC_OrderLine_ID());
		schedule.setC_Order_ID(orderLine.getC_Order_ID());
		schedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		schedule.setQtyOrdered_Calculated(orderLine.getQtyOrdered());
		schedule.setQtyDelivered(BigDecimal.ZERO);
		schedule.setIsClosed(closed);
		saveRecord(schedule);
		return schedule;
	}
}
