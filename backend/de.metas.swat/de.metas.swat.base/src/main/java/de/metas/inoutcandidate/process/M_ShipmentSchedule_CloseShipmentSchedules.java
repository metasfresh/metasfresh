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
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
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

	@Override
	protected String doIt() throws Exception
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter = getProcessInfo().getQueryFilterOrElseFalse();

		// 1) Hard-block, all-or-nothing, over the FULL user selection: if any selected schedule still has an
		// unfinished (Drafted) picking job, reject the whole close and close NOTHING (AC1, AC6).
		final List<I_M_ShipmentSchedule> fullSelection = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter)
				.create()
				.list();
		assertNoneHasUnfinishedPicking(fullSelection);

		// 2) Unchanged pre-existing eligibility: among the (now proven picking-clean) selection, only schedules
		// with QtyPickList=0 are actually closed; schedules with picked-but-unshipped qty are silently skipped.
		final Iterator<I_M_ShipmentSchedule> schedulesToUpdateIterator = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_QtyPickList, BigDecimal.ZERO)
				.create()
				.iterate(I_M_ShipmentSchedule.class);

		if (!schedulesToUpdateIterator.hasNext())
		{
			throw new AdempiereException("@NoSelection@");
		}

		while (schedulesToUpdateIterator.hasNext())
		{
			final I_M_ShipmentSchedule schedule = schedulesToUpdateIterator.next();

			shipmentScheduleBL.closeShipmentSchedule(schedule);
		}

		return MSG_OK;
	}

	/**
	 * Rejects the whole close (all-or-nothing) when at least one schedule in {@code fullSelection} is still
	 * referenced by an unfinished (Drafted) picking job.
	 */
	private void assertNoneHasUnfinishedPicking(final List<I_M_ShipmentSchedule> fullSelection)
	{
		final ImmutableSet<ShipmentScheduleId> selectedScheduleIds = fullSelection.stream()
				.map(schedule -> ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final IShipmentSchedulePickingInfoService pickingInfoService = SpringContextHolder.instance.getBean(IShipmentSchedulePickingInfoService.class);
		final Set<ShipmentScheduleId> offendingScheduleIds = pickingInfoService.retrieveScheduleIdsWithUnfinishedPicking(selectedScheduleIds);

		if (offendingScheduleIds.isEmpty())
		{
			return;
		}

		final List<I_M_ShipmentSchedule> offendingSchedules = fullSelection.stream()
				.filter(schedule -> offendingScheduleIds.contains(ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID())))
				.collect(Collectors.toList());

		final String offendingIdentifiers = toHumanReadableIdentifiersCsv(offendingSchedules);

		throw new AdempiereException(MSG_CANNOT_CLOSE_UNFINISHED_PICKING, offendingIdentifiers).markAsUserValidationError();
	}

	/**
	 * @return a comma-separated, human-readable identifier list for the offending schedules: each schedule's
	 * 		order {@code DocumentNo} when it references one, else its {@code M_ShipmentSchedule_ID} as a fallback.
	 * 		Orders are batch-loaded once (never one-by-one per schedule).
	 */
	private String toHumanReadableIdentifiersCsv(final List<I_M_ShipmentSchedule> offendingSchedules)
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

	private String toHumanReadableIdentifier(final I_M_ShipmentSchedule schedule, final Map<OrderId, String> documentNoByOrderId)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(schedule.getC_Order_ID());
		final String documentNo = orderId != null ? documentNoByOrderId.get(orderId) : null;
		return documentNo != null ? documentNo : "M_ShipmentSchedule_ID=" + schedule.getM_ShipmentSchedule_ID();
	}
}
