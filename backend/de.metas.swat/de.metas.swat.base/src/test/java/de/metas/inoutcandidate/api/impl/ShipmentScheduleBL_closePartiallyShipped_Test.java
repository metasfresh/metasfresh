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

import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
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

	private static void enableCloseIfPartiallyShipped(final OrgId orgId)
	{
		Services.get(ISysConfigBL.class).setValue(
				ShipmentScheduleBL.SYS_Config_M_ShipmentSchedule_Close_PartiallyShipped,
				true,
				ClientId.METASFRESH,
				orgId);
	}

	@Test
	public void closePartiallyShipped_ShipmentSchedules_doesNotCloseScheduleAlreadyFullyDeliveredBySiblingInOut()
	{
		final OrgId orgId = OrgId.ANY;

		// enable "close partially shipped schedules" for this org
		enableCloseIfPartiallyShipped(orgId);

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
		scheduleFull.setQtyDelivered(new BigDecimal("12")); // recompute already landed (not read by the close decision)
		scheduleFull.setIsClosed(false);
		save(scheduleFull);

		// the committed sibling delivery backing S_full: a completed M_InOut line (12) linked to S_full via
		// M_ShipmentSchedule_QtyPicked - this is what the close decision actually reads (the processed-line ledger)
		final I_M_InOut siblingInOut = newInstance(I_M_InOut.class);
		siblingInOut.setAD_Org_ID(orgId.getRepoId());
		siblingInOut.setIsSOTrx(true);
		siblingInOut.setDocStatus(DocStatus.Completed.getCode());
		save(siblingInOut);

		final I_M_InOutLine siblingLineFull = newInstance(I_M_InOutLine.class);
		siblingLineFull.setM_InOut(siblingInOut);
		siblingLineFull.setC_OrderLine_ID(orderLineFull.getC_OrderLine_ID());
		siblingLineFull.setMovementQty(new BigDecimal("12"));
		siblingLineFull.setProcessed(true);
		save(siblingLineFull);

		final I_M_ShipmentSchedule_QtyPicked allocFull = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		allocFull.setM_ShipmentSchedule_ID(scheduleFull.getM_ShipmentSchedule_ID());
		allocFull.setM_InOutLine_ID(siblingLineFull.getM_InOutLine_ID());
		allocFull.setQtyPicked(new BigDecimal("12"));
		save(allocFull);

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

	/**
	 * The async-stale variant of the sibling-InOut case. When one order's picked lines ship via ≥2
	 * separate {@code M_InOut} docs, each InOut's {@code QtyPicked→QtyDelivered} shift runs in its OWN
	 * deferred {@code runAfterCommit} hook. So when a later sibling InOut completes and runs
	 * {@code closePartiallyShipped}, a schedule fully shipped by an EARLIER sibling InOut can still have
	 * {@code QtyDelivered=0} — its recompute has not landed yet. The committed truth is the sibling's
	 * {@code M_InOutLine.MovementQty}; if the close trusts the stale {@code QtyDelivered} it wrongly
	 * closes the fully-shipped schedule, which nothing ever reopens.
	 */
	@Test
	public void closePartiallyShipped_ShipmentSchedules_doesNotCloseScheduleFullyDeliveredBySibling_whenQtyDeliveredNotYetRecomputed()
	{
		final OrgId orgId = OrgId.ANY;

		enableCloseIfPartiallyShipped(orgId);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		save(order);

		final int orderLineTableId = InterfaceWrapperHelper.getTableId(I_C_OrderLine.class);

		// S_full: fully shipped (12/12) by a SIBLING, already-completed M_InOut, but QtyDelivered is
		// still 0 because that sibling's QtyDelivered recompute has not landed yet (the race window).
		final I_C_OrderLine orderLineFull = newInstance(I_C_OrderLine.class);
		orderLineFull.setC_Order(order);
		save(orderLineFull);

		final I_M_ShipmentSchedule scheduleFull = newInstance(I_M_ShipmentSchedule.class);
		scheduleFull.setAD_Org_ID(orgId.getRepoId());
		scheduleFull.setC_Order_ID(order.getC_Order_ID());
		scheduleFull.setC_OrderLine_ID(orderLineFull.getC_OrderLine_ID());
		scheduleFull.setAD_Table_ID(orderLineTableId);
		scheduleFull.setRecord_ID(orderLineFull.getC_OrderLine_ID());
		scheduleFull.setQtyOrdered(new BigDecimal("12"));
		scheduleFull.setQtyOrdered_Calculated(new BigDecimal("12"));
		scheduleFull.setQtyDelivered(BigDecimal.ZERO); // stale: sibling's recompute not yet landed
		scheduleFull.setIsClosed(false);
		save(scheduleFull);

		// the sibling shipment that already delivered S_full's 12 (completed, not a reversal)
		final I_M_InOut siblingInOut = newInstance(I_M_InOut.class);
		siblingInOut.setAD_Org_ID(orgId.getRepoId());
		siblingInOut.setIsSOTrx(true);
		siblingInOut.setDocStatus(DocStatus.Completed.getCode());
		save(siblingInOut);

		final I_M_InOutLine siblingLineFull = newInstance(I_M_InOutLine.class);
		siblingLineFull.setM_InOut(siblingInOut);
		siblingLineFull.setC_OrderLine_ID(orderLineFull.getC_OrderLine_ID());
		siblingLineFull.setMovementQty(new BigDecimal("12"));
		siblingLineFull.setProcessed(true);
		save(siblingLineFull);

		// the committed picking->shipment allocation binding S_full to the sibling shipment line
		final I_M_ShipmentSchedule_QtyPicked allocFull = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		allocFull.setM_ShipmentSchedule_ID(scheduleFull.getM_ShipmentSchedule_ID());
		allocFull.setM_InOutLine_ID(siblingLineFull.getM_InOutLine_ID());
		allocFull.setQtyPicked(new BigDecimal("12"));
		save(allocFull);

		// S_partial: shipped 8/12 by the CURRENT InOut (its QtyDelivered is 0 at close-time too)
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
		schedulePartial.setQtyOrdered_Calculated(new BigDecimal("12"));
		schedulePartial.setQtyDelivered(BigDecimal.ZERO);
		schedulePartial.setIsClosed(false);
		save(schedulePartial);

		final I_M_InOut currentInOut = newInstance(I_M_InOut.class);
		currentInOut.setAD_Org_ID(orgId.getRepoId());
		currentInOut.setIsSOTrx(true);
		currentInOut.setDocStatus(DocStatus.Completed.getCode());
		save(currentInOut);

		final I_M_InOutLine currentLinePartial = newInstance(I_M_InOutLine.class);
		currentLinePartial.setM_InOut(currentInOut);
		currentLinePartial.setC_OrderLine_ID(orderLinePartial.getC_OrderLine_ID());
		currentLinePartial.setMovementQty(new BigDecimal("8"));
		// this InOut is completing NOW: its lines are not yet Processed at close-time (TIMING_AFTER_COMPLETE
		// fires before setProcessed), so this delivery is counted via MovementQty, not the processed-line ledger
		currentLinePartial.setProcessed(false);
		save(currentLinePartial);

		final I_M_ShipmentSchedule_QtyPicked allocPartial = newInstance(I_M_ShipmentSchedule_QtyPicked.class);
		allocPartial.setM_ShipmentSchedule_ID(schedulePartial.getM_ShipmentSchedule_ID());
		allocPartial.setM_InOutLine_ID(currentLinePartial.getM_InOutLine_ID());
		allocPartial.setQtyPicked(new BigDecimal("8"));
		save(allocPartial);

		shipmentScheduleBL.closePartiallyShipped_ShipmentSchedules(currentInOut);

		refresh(scheduleFull);
		refresh(schedulePartial);

		assertThat(scheduleFull.isClosed())
				.as("S_full is fully delivered by a sibling InOut (committed in M_InOutLine, QtyDelivered not yet recomputed) and must stay open")
				.isFalse();
		assertThat(schedulePartial.isClosed())
				.as("S_partial is not fully delivered => must be closed")
				.isTrue();
	}

	@Test
	public void closePartiallyShipped_ShipmentSchedules_closesCompletelyUnshippedSchedule()
	{
		final OrgId orgId = OrgId.ANY;

		// enable "close partially shipped schedules" for this org
		enableCloseIfPartiallyShipped(orgId);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		save(order);

		final int orderLineTableId = InterfaceWrapperHelper.getTableId(I_C_OrderLine.class);

		// S_unshipped: zero delivery anywhere (not persisted, not on ANY M_InOut line, not even the current one)
		final I_C_OrderLine orderLineUnshipped = newInstance(I_C_OrderLine.class);
		orderLineUnshipped.setC_Order(order);
		save(orderLineUnshipped);

		final I_M_ShipmentSchedule scheduleUnshipped = newInstance(I_M_ShipmentSchedule.class);
		scheduleUnshipped.setAD_Org_ID(orgId.getRepoId());
		scheduleUnshipped.setC_Order_ID(order.getC_Order_ID());
		scheduleUnshipped.setC_OrderLine_ID(orderLineUnshipped.getC_OrderLine_ID());
		scheduleUnshipped.setAD_Table_ID(orderLineTableId);
		scheduleUnshipped.setRecord_ID(orderLineUnshipped.getC_OrderLine_ID());
		scheduleUnshipped.setQtyOrdered(new BigDecimal("12"));
		scheduleUnshipped.setQtyOrdered_Calculated(new BigDecimal("12")); // effective QtyOrdered (no override)
		scheduleUnshipped.setQtyDelivered(BigDecimal.ZERO); // never delivered
		scheduleUnshipped.setIsClosed(false);
		save(scheduleUnshipped);

		// S_shipped: another schedule of the SAME order, fully shipped by the CURRENT InOut - only needed so
		// the order gets picked up by closePartiallyShipped_ShipmentSchedules (which derives the order(s) to
		// process from the current InOut's lines).
		final I_C_OrderLine orderLineShipped = newInstance(I_C_OrderLine.class);
		orderLineShipped.setC_Order(order);
		save(orderLineShipped);

		final I_M_ShipmentSchedule scheduleShipped = newInstance(I_M_ShipmentSchedule.class);
		scheduleShipped.setAD_Org_ID(orgId.getRepoId());
		scheduleShipped.setC_Order_ID(order.getC_Order_ID());
		scheduleShipped.setC_OrderLine_ID(orderLineShipped.getC_OrderLine_ID());
		scheduleShipped.setAD_Table_ID(orderLineTableId);
		scheduleShipped.setRecord_ID(orderLineShipped.getC_OrderLine_ID());
		scheduleShipped.setQtyOrdered(new BigDecimal("12"));
		scheduleShipped.setQtyOrdered_Calculated(new BigDecimal("12")); // effective QtyOrdered (no override)
		scheduleShipped.setQtyDelivered(BigDecimal.ZERO); // not yet updated for THIS InOut at close-time
		scheduleShipped.setIsClosed(false);
		save(scheduleShipped);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setAD_Org_ID(orgId.getRepoId());
		inout.setIsSOTrx(true);
		save(inout);

		final I_M_InOutLine inoutLineShipped = newInstance(I_M_InOutLine.class);
		inoutLineShipped.setM_InOut(inout);
		inoutLineShipped.setC_OrderLine_ID(orderLineShipped.getC_OrderLine_ID());
		inoutLineShipped.setMovementQty(new BigDecimal("12"));
		save(inoutLineShipped);

		shipmentScheduleBL.closePartiallyShipped_ShipmentSchedules(inout);

		refresh(scheduleUnshipped);

		assertThat(scheduleUnshipped.isClosed())
				.as("S_unshipped has zero delivery anywhere (persisted and current shipment) and M_ShipmentSchedule_Close_PartiallyShipped=Y => must be closed")
				.isTrue();
	}

	@Test
	public void closePartiallyShipped_ShipmentSchedules_doesNotCloseScheduleFullyDeliveredSolelyByCurrentInOut()
	{
		final OrgId orgId = OrgId.ANY;

		// enable "close partially shipped schedules" for this org
		enableCloseIfPartiallyShipped(orgId);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		save(order);

		final int orderLineTableId = InterfaceWrapperHelper.getTableId(I_C_OrderLine.class);

		// S_currentFull: fully delivered SOLELY by the CURRENT InOut's line (MovementQty=12 == QtyOrdered=12).
		// Persisted QtyDelivered is still 0 at TIMING_AFTER_COMPLETE (see timing note in the sibling test
		// above) - this exercises the "add this shipment's MovementQty on top of persisted QtyDelivered" path
		// for the CURRENT (non-split) InOut.
		final I_C_OrderLine orderLineCurrentFull = newInstance(I_C_OrderLine.class);
		orderLineCurrentFull.setC_Order(order);
		save(orderLineCurrentFull);

		final I_M_ShipmentSchedule scheduleCurrentFull = newInstance(I_M_ShipmentSchedule.class);
		scheduleCurrentFull.setAD_Org_ID(orgId.getRepoId());
		scheduleCurrentFull.setC_Order_ID(order.getC_Order_ID());
		scheduleCurrentFull.setC_OrderLine_ID(orderLineCurrentFull.getC_OrderLine_ID());
		scheduleCurrentFull.setAD_Table_ID(orderLineTableId);
		scheduleCurrentFull.setRecord_ID(orderLineCurrentFull.getC_OrderLine_ID());
		scheduleCurrentFull.setQtyOrdered(new BigDecimal("12"));
		scheduleCurrentFull.setQtyOrdered_Calculated(new BigDecimal("12")); // effective QtyOrdered (no override)
		scheduleCurrentFull.setQtyDelivered(BigDecimal.ZERO); // not yet updated for THIS InOut at close-time
		scheduleCurrentFull.setIsClosed(false);
		save(scheduleCurrentFull);

		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setAD_Org_ID(orgId.getRepoId());
		inout.setIsSOTrx(true);
		save(inout);

		final I_M_InOutLine inoutLineCurrentFull = newInstance(I_M_InOutLine.class);
		inoutLineCurrentFull.setM_InOut(inout);
		inoutLineCurrentFull.setC_OrderLine_ID(orderLineCurrentFull.getC_OrderLine_ID());
		inoutLineCurrentFull.setMovementQty(new BigDecimal("12"));
		save(inoutLineCurrentFull);

		shipmentScheduleBL.closePartiallyShipped_ShipmentSchedules(inout);

		refresh(scheduleCurrentFull);

		assertThat(scheduleCurrentFull.isClosed())
				.as("S_currentFull is fully delivered once THIS shipment's MovementQty (12) is added on top of persisted QtyDelivered (0) => must stay open")
				.isFalse();
	}
}
