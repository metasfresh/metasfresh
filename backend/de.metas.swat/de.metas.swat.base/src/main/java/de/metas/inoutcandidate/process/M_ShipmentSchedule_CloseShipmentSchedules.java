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
import java.util.Objects;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
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
	private static final AdMessageKey MSG_CANNOT_CLOSE_UNFINISHED_PICKING = AdMessageKey.of("M_ShipmentSchedule_CannotClose_UnfinishedPicking");

	// Impl lives in de.metas.handlingunits.base (M_Picking_Job*), which swat.base does not depend on; resolved via Spring.
	private final IShipmentSchedulePickingInfoService pickingInfoService = SpringContextHolder.instance.getBean(IShipmentSchedulePickingInfoService.class);

	@Override
	protected String doIt() throws Exception
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter = getProcessInfo().getQueryFilterOrElseFalse();

		// 1) Hard-block, all-or-nothing, over the FULL user selection: if any selected schedule still has an
		// unfinished (Drafted) picking job, reject the whole close and close NOTHING. The unfinished-picking check
		// is a subquery filter folded into the selection query, so the offending schedules come from a single query
		// (no id round-trip / in-memory intersection).
		final List<I_M_ShipmentSchedule> offendingSchedules = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter)
				.filter(pickingInfoService.newUnfinishedPickingFilter())
				.create()
				.list();
		if (!offendingSchedules.isEmpty())
		{
			final String offendingIdentifiers = toHumanReadableIdentifiersCsv(offendingSchedules);
			throw new AdempiereException(MSG_CANNOT_CLOSE_UNFINISHED_PICKING, offendingIdentifiers).markAsUserValidationError();
		}

		// 2) Close the eligible schedules. Two guards, both as folded subquery filters so they re-evaluate against
		// live DB state (this is a SEPARATE query execution from the check above — re-applying the unfinished-picking
		// filter here, negated, closes the TOCTOU window: a schedule that becomes picking-busy between the two
		// queries is safely skipped rather than wrongly closed, the very bug this process prevents). Among the
		// picking-clean selection, only schedules with QtyPickList=0 are closed; picked-but-unshipped qty is skipped
		// (the pre-existing eligibility rule). Streamed (not materialized) to avoid loading a large selection.
		final Iterator<I_M_ShipmentSchedule> selectionIterator = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter)
				.filter(pickingInfoService.newUnfinishedPickingFilter().negate())
				.create()
				.iterate(I_M_ShipmentSchedule.class);

		boolean anyClosed = false;
		while (selectionIterator.hasNext())
		{
			final I_M_ShipmentSchedule schedule = selectionIterator.next();
			if (isEligibleForClose(schedule))
			{
				shipmentScheduleBL.closeShipmentSchedule(schedule);
				anyClosed = true;
			}
		}

		if (!anyClosed)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return MSG_OK;
	}

	/**
	 * Mirrors the pre-existing SQL {@code QtyPickList = 0} eligibility filter EXACTLY: only schedules whose
	 * {@code QtyPickList} is set to zero are closed. A NULL {@code QtyPickList} is NOT eligible — the SQL {@code = 0}
	 * predicate excludes NULLs — so the raw column value is read via {@link InterfaceWrapperHelper#getValueOrNull}:
	 * the generated {@code getQtyPickList()} masks NULL as {@link BigDecimal#ZERO} and would wrongly include those rows.
	 */
	@VisibleForTesting
	static boolean isEligibleForClose(final I_M_ShipmentSchedule schedule)
	{
		final BigDecimal qtyPickList = InterfaceWrapperHelper.getValueOrNull(schedule, I_M_ShipmentSchedule.COLUMNNAME_QtyPickList);
		return qtyPickList != null && qtyPickList.signum() == 0;
	}

	/**
	 * @return a comma-separated, human-readable identifier list for the offending schedules: each schedule's
	 * 		order {@code DocumentNo} when it references one, else its {@code M_ShipmentSchedule_ID} as a fallback.
	 * 		Orders are batch-loaded once (never one-by-one per schedule).
	 */
	@VisibleForTesting
	static String toHumanReadableIdentifiersCsv(final List<I_M_ShipmentSchedule> offendingSchedules)
	{
		final ImmutableSet<OrderId> orderIds = offendingSchedules.stream()
				.map(schedule -> OrderId.ofRepoIdOrNull(schedule.getC_Order_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final Map<OrderId, String> documentNoByOrderId = Services.get(IOrderDAO.class).getByIds(orderIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(order -> OrderId.ofRepoId(order.getC_Order_ID()), I_C_Order::getDocumentNo));

		return offendingSchedules.stream()
				.map(schedule -> toHumanReadableIdentifier(schedule, documentNoByOrderId))
				.distinct()
				.collect(Collectors.joining(", "));
	}

	@VisibleForTesting
	static String toHumanReadableIdentifier(final I_M_ShipmentSchedule schedule, final Map<OrderId, String> documentNoByOrderId)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(schedule.getC_Order_ID());
		final String documentNo = orderId != null ? documentNoByOrderId.get(orderId) : null;
		return documentNo != null ? documentNo : "M_ShipmentSchedule_ID=" + schedule.getM_ShipmentSchedule_ID();
	}
}
