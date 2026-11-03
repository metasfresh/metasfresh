package de.metas.inoutcandidate.process;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentSchedulePickingInfoService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

public class M_ShipmentSchedule_CloseShipmentSchedules extends JavaProcess
{
	/** exactly-one offender: the specific message that names the offending schedule's order */
	private static final AdMessageKey MSG_CANNOT_CLOSE_UNFINISHED_PICKING = AdMessageKey.of("M_ShipmentSchedule_CannotClose_UnfinishedPicking");
	/** two-or-more offenders: the generic message that does NOT enumerate the schedules (huge-selection optimization) */
	private static final AdMessageKey MSG_CANNOT_CLOSE_UNFINISHED_PICKINGS = AdMessageKey.of("M_ShipmentSchedule_CannotClose_UnfinishedPickings");
	/** no unfinished picking, but the WHOLE selection is ineligible: every selected schedule is already processed or still has a picked-but-unshipped qty (QtyPickList &gt; 0) */
	private static final AdMessageKey MSG_CANNOT_CLOSE_NOT_ELIGIBLE = AdMessageKey.of("M_ShipmentSchedule_CannotClose_NotEligible");

	/**
	 * Row cap of the offending-schedules query: fetching a third row would add nothing, because the rejection message
	 * only distinguishes "exactly one" (named) from "two or more" (generic).
	 *
	 * @see #assertNoOffendingSchedules(IQueryFilter)
	 */
	private static final int MAX_OFFENDING_SCHEDULES_TO_DISTINGUISH = 2;

	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	// Impl lives in de.metas.handlingunits.base (M_Picking_Job*), which swat.base does not depend on; resolved via Spring.
	private final IShipmentSchedulePickingInfoService pickingInfoService = SpringContextHolder.instance.getBean(IShipmentSchedulePickingInfoService.class);

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter = getProcessInfo().getQueryFilterOrElseFalse();

		// 1) Hard-block, all-or-nothing, over the FULL user selection: if any selected schedule still has an
		// unfinished (Drafted) picking job, reject the whole close and close NOTHING.
		assertNoOffendingSchedules(userSelectionFilter);

		// 2) Close the eligible schedules. Two guards, both as folded subquery/SQL filters so they re-evaluate against
		// live DB state (this is a SEPARATE query execution from the check above — re-applying the unfinished-picking
		// filter here, negated, closes the TOCTOU window: a schedule that becomes picking-busy between the two
		// queries is safely skipped rather than wrongly closed, the very bug this process prevents). Among the
		// picking-clean selection, only schedules with QtyPickList=0 are closed; picked-but-unshipped qty is skipped
		// (the pre-existing eligibility rule, expressed here as the SQL predicate QtyPickList=0 which correctly
		// EXCLUDES NULL). Streamed (not materialized) to avoid loading a large selection.
		final Iterator<I_M_ShipmentSchedule> selectionIterator = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_QtyPickList, BigDecimal.ZERO)
				.filter(pickingInfoService.newUnfinishedPickingFilter().negate())
				.create()
				.iterate(I_M_ShipmentSchedule.class);

		if (!selectionIterator.hasNext())
		{
			// The user DID select schedules, but none is eligible to close: every selected schedule is either
			// already processed (Processed=true, filtered out by the base selection query) or still has a
			// picked-but-unshipped qty (QtyPickList > 0). "@NoSelection@" ("nothing selected") is misleading here,
			// so raise a friendly message that explains why nothing was closed.
			throw new AdempiereException(MSG_CANNOT_CLOSE_NOT_ELIGIBLE);
		}

		while (selectionIterator.hasNext())
		{
			shipmentScheduleBL.closeShipmentSchedule(selectionIterator.next());
		}

		return MSG_OK;
	}

	/**
	 * Rejects the whole close (all-or-nothing) if any selected schedule still has an unfinished (Drafted) picking
	 * job. The unfinished-picking check is a subquery filter folded into the selection query, so the offending
	 * schedules come from a single query (no id round-trip / in-memory intersection). The query is capped at
	 * {@link #MAX_OFFENDING_SCHEDULES_TO_DISTINGUISH} because the rejection message only needs to distinguish
	 * "exactly one" (named) from "two or more" (generic):
	 * <ul>
	 *     <li>none offending → do nothing;</li>
	 *     <li>exactly one → the specific {@link #MSG_CANNOT_CLOSE_UNFINISHED_PICKING} naming that schedule's order;</li>
	 *     <li>two or more → the generic {@link #MSG_CANNOT_CLOSE_UNFINISHED_PICKINGS}, which does NOT enumerate the
	 *         schedules (the huge-selection optimization: no per-schedule order load for a potentially huge set).</li>
	 * </ul>
	 */
	private void assertNoOffendingSchedules(final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter)
	{
		final List<I_M_ShipmentSchedule> offendingSchedules = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter)
				.filter(pickingInfoService.newUnfinishedPickingFilter())
				.setLimit(MAX_OFFENDING_SCHEDULES_TO_DISTINGUISH)
				.create()
				.list();

		if (offendingSchedules.isEmpty())
		{
			return;
		}

		if (offendingSchedules.size() == 1)
		{
			final I_M_ShipmentSchedule offendingSchedule = offendingSchedules.get(0);
			throw new AdempiereException(MSG_CANNOT_CLOSE_UNFINISHED_PICKING, toHumanReadableIdentifier(offendingSchedule, resolveDocumentNoByOrderId(offendingSchedule)));
		}

		throw new AdempiereException(MSG_CANNOT_CLOSE_UNFINISHED_PICKINGS);
	}

	/**
	 * @return a one-entry {@code OrderId -> DocumentNo} map for the single offending schedule's order (never a batch
	 * 		load): the schedule's order {@code DocumentNo} when it references an existing order, else an empty map so
	 * 		{@link #toHumanReadableIdentifier(I_M_ShipmentSchedule, Map)} falls back to the {@code M_ShipmentSchedule_ID}.
	 */
	@VisibleForTesting
	Map<OrderId, String> resolveDocumentNoByOrderId(final I_M_ShipmentSchedule schedule)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(schedule.getC_Order_ID());
		if (orderId == null)
		{
			return ImmutableMap.of();
		}

		return orderDAO.getByIds(ImmutableSet.of(orderId))
				.stream()
				.collect(ImmutableMap.toImmutableMap(order -> OrderId.ofRepoId(order.getC_Order_ID()), I_C_Order::getDocumentNo));
	}

	@VisibleForTesting
	static String toHumanReadableIdentifier(final I_M_ShipmentSchedule schedule, final Map<OrderId, String> documentNoByOrderId)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(schedule.getC_Order_ID());
		final String documentNo = orderId != null ? documentNoByOrderId.get(orderId) : null;
		return documentNo != null ? documentNo : "M_ShipmentSchedule_ID=" + schedule.getM_ShipmentSchedule_ID();
	}
}
