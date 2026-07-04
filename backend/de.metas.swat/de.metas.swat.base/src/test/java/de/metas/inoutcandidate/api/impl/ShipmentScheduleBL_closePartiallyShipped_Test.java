package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Regression test: when a single sales order's shipment schedules get shipped via TWO (or more)
 * separate {@code M_InOut} documents (e.g. a picking job split by the async shipment-generation
 * pipeline), a schedule that was already fully delivered by a <b>sibling</b> {@code M_InOut} must
 * not be wrongly closed when a later, unrelated sibling {@code M_InOut} of the same order completes.
 * <p>
 * {@link ShipmentScheduleBL#closePartiallyShipped_ShipmentSchedules(I_M_InOut)} only looked at the
 * lines of the <b>current</b> {@code M_InOut} to decide which schedules were "fully shipped" before
 * closing every other not-yet-closed schedule of the order — so a schedule fully delivered by a
 * previous, separate {@code M_InOut} was invisible to that check and got closed.
 */
public class ShipmentScheduleBL_closePartiallyShipped_Test
{
	private ShipmentScheduleBL shipmentScheduleBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleBL = (ShipmentScheduleBL)Services.get(IShipmentScheduleBL.class);
	}

	@Test
	public void closePartiallyShipped_ShipmentSchedules_doesNotCloseScheduleAlreadyFullyDeliveredBySiblingInOut()
	{
		final OrgId orgId = OrgId.ANY;

		// enable "close partially shipped schedules" for this org
		Services.get(ISysConfigBL.class).setValue(
				"M_ShipmentSchedule_Close_PartiallyShipped",
				true,
				ClientId.METASFRESH,
				orgId);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		save(order);

		// S_full: already fully delivered by a SIBLING M_InOut (not part of this test's inout at all)
		final I_C_OrderLine orderLineFull = newInstance(I_C_OrderLine.class);
		orderLineFull.setC_Order(order);
		save(orderLineFull);

		final int orderLineTableId = InterfaceWrapperHelper.getTableId(I_C_OrderLine.class);

		final I_M_ShipmentSchedule scheduleFull = newInstance(I_M_ShipmentSchedule.class);
		scheduleFull.setAD_Org_ID(orgId.getRepoId());
		scheduleFull.setC_Order_ID(order.getC_Order_ID());
		scheduleFull.setC_OrderLine_ID(orderLineFull.getC_OrderLine_ID());
		// M_ShipmentSchedule links to its order line via the generic (AD_Table_ID, Record_ID) reference
		scheduleFull.setAD_Table_ID(orderLineTableId);
		scheduleFull.setRecord_ID(orderLineFull.getC_OrderLine_ID());
		scheduleFull.setQtyOrdered(new BigDecimal("12"));
		scheduleFull.setQtyOrdered_Calculated(new BigDecimal("12")); // effective QtyOrdered (no override)
		scheduleFull.setQtyDelivered(new BigDecimal("12")); // fully delivered already, e.g. by a sibling InOut
		scheduleFull.setIsClosed(false);
		save(scheduleFull);

		// S_partial: shipped by the CURRENT InOut with MovementQty=8 of 12 ordered (partial delivery).
		// QtyDelivered is still 0 at TIMING_AFTER_COMPLETE - the QtyPicked->QtyDelivered shift for this
		// InOut happens in a runAfterCommit hook that fires AFTER closePartiallyShipped runs.
		final I_C_OrderLine orderLinePartial = newInstance(I_C_OrderLine.class);
		orderLinePartial.setC_Order(order);
		save(orderLinePartial);

		final I_M_ShipmentSchedule schedulePartial = newInstance(I_M_ShipmentSchedule.class);
		schedulePartial.setAD_Org_ID(orgId.getRepoId());
		schedulePartial.setC_Order_ID(order.getC_Order_ID());
		schedulePartial.setC_OrderLine_ID(orderLinePartial.getC_OrderLine_ID());
		schedulePartial.setAD_Table_ID(orderLineTableId);
		schedulePartial.setRecord_ID(orderLinePartial.getC_OrderLine_ID());
		schedulePartial.setQtyOrdered(new BigDecimal("12"));
		schedulePartial.setQtyOrdered_Calculated(new BigDecimal("12")); // effective QtyOrdered (no override)
		schedulePartial.setQtyDelivered(BigDecimal.ZERO); // not yet updated for THIS InOut at close-time
		schedulePartial.setIsClosed(false);
		save(schedulePartial);

		// the M_InOut being completed now contains ONLY S_partial's line (simulating the sibling-InOut split:
		// S_full was already delivered by a *different*, previously completed M_InOut)
		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setAD_Org_ID(orgId.getRepoId());
		inout.setIsSOTrx(true);
		save(inout);

		final I_M_InOutLine inoutLinePartial = newInstance(I_M_InOutLine.class);
		inoutLinePartial.setM_InOut(inout);
		inoutLinePartial.setC_OrderLine_ID(orderLinePartial.getC_OrderLine_ID());
		inoutLinePartial.setMovementQty(new BigDecimal("8"));
		save(inoutLinePartial);

		shipmentScheduleBL.closePartiallyShipped_ShipmentSchedules(inout);

		refresh(scheduleFull);
		refresh(schedulePartial);

		assertThat(scheduleFull.isClosed())
				.as("S_full is already fully delivered (by a sibling InOut) and must stay open")
				.isFalse();
		assertThat(schedulePartial.isClosed())
				.as("S_partial is not fully delivered and M_ShipmentSchedule_Close_PartiallyShipped=Y => must be closed")
				.isTrue();
	}
}
