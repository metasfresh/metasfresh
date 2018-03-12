package de.metas.handlingunits.materialtracking.spi.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUAssignmentDAO.HuAssignment;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class PPOrderMInOutLineRetrievalService implements IPPOrderMInOutLineRetrievalService
{

	@Override
	public List<I_M_InOutLine> provideIssuedInOutLines(final I_PP_Cost_Collector issueCostCollector)
	{
		final Set<I_M_InOutLine> result = new TreeSet<>(ModelByIdComparator.getInstance());

		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		List<HuAssignment> huAssignmentsForModel = huAssignmentDAO.retrieveTUHUAssignmentsForModelQuery(issueCostCollector)
				.create()
				.stream()
				.map(HuAssignment::ofDataRecordAllowMissingHU)
				.collect(ImmutableList.toImmutableList());

		if (huAssignmentsForModel.isEmpty())
		{
			// fallback
			huAssignmentsForModel = huAssignmentDAO.retrieveHUAssignmentPojosForModel(issueCostCollector);
		}

		for (final HuAssignment huAssignment : huAssignmentsForModel)
		{
			final I_M_HU hu = huAssignment.getLowestLevelHU();
			if (hu == null)
			{
				continue;
			}

			final I_M_InOutLine inoutLine = Services.get(IHUInOutDAO.class).retrieveCompletedReceiptLineOrNull(hu);
			if (inoutLine == null || !inoutLine.getM_InOut().isProcessed())
			{
				// there is no iol
				// or it's not processed (which should not happen)
				continue;
			}
			result.add(inoutLine);
		}
		return new ArrayList<>(result);
	}

	@Override
	public Map<Integer, BigDecimal> retrieveIolAndQty(final I_PP_Order ppOrder)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);

		final Map<Integer, BigDecimal> iolMap = new HashMap<>();

		final List<I_PP_Cost_Collector> costCollectors = ppCostCollectorDAO.retrieveForOrder(ppOrder);

		for (final I_PP_Cost_Collector costCollector : costCollectors)
		{
			if (!ppCostCollectorBL.isMaterialIssue(costCollector, true))
			{
				continue;
			}

			final List<I_M_HU_Assignment> huAssignmentsForModel = huAssignmentDAO.retrieveHUAssignmentsForModel(costCollector);

			final Map<Integer, I_M_InOutLine> id2iol = new HashMap<>();

			for (final I_M_HU_Assignment assignment : huAssignmentsForModel)
			{
				final I_M_HU hu = assignment.getM_HU();
				final I_M_InOutLine inoutLine = huInOutDAO.retrieveCompletedReceiptLineOrNull(hu);
				if (inoutLine == null || !inoutLine.getM_InOut().isProcessed())
				{
					// there is no iol
					// or it's not processed (which should not happen)
					continue;
				}
				id2iol.put(inoutLine.getM_InOutLine_ID(), inoutLine);
			}

			BigDecimal qtyToAllocate = costCollector.getMovementQty();
			for (final I_M_InOutLine inoutLine : id2iol.values())
			{
				final BigDecimal qty = qtyToAllocate.min(inoutLine.getMovementQty());
				iolMap.put(inoutLine.getM_InOutLine_ID(), qty);
				qtyToAllocate = qtyToAllocate.subtract(inoutLine.getMovementQty()).max(BigDecimal.ZERO);
			}

			if (qtyToAllocate.signum() > 0)
			{
				Loggables.get().addLog("PROBLEM: PP_Cost_Collector {0} of PP_Order {1} has a remaining unallocated qty of {2}!", costCollector, ppOrder, qtyToAllocate);
			}
		}

		return iolMap;
	}
}
