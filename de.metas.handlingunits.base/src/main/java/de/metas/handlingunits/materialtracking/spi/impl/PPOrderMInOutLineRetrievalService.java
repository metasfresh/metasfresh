package de.metas.handlingunits.materialtracking.spi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.document.service.IDocActionBL;
import org.adempiere.util.Services;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.handlingunits.IHUAssignmentDAO;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		List<I_M_HU_Assignment> huAssignmentsForModel = huAssignmentDAO.retrieveTUHUAssignmentsForModelQuery(issueCostCollector)
				.create()
				.list();
		if (huAssignmentsForModel.isEmpty())
		{
			// fallback
			huAssignmentsForModel = huAssignmentDAO.retrieveHUAssignmentsForModel(issueCostCollector);
		}

		for (final I_M_HU_Assignment huAssignment : huAssignmentsForModel)
		{
			final I_M_HU hu;
			if (huAssignment.getVHU_ID() > 0)
			{
				hu = huAssignment.getVHU();
			}
			else if (huAssignment.getM_TU_HU_ID() > 0)
			{
				hu = huAssignment.getM_TU_HU();
			}
			else if (huAssignment.getM_LU_HU_ID() > 0)
			{
				hu = huAssignment.getM_LU_HU();
			}
			else if (huAssignment.getM_HU_ID() > 0)
			{
				hu = huAssignment.getM_HU();
			}
			else
			{
				continue;
			}

			final boolean topLevel = false; // we want the most detailed info, in case VHUs were rearranged.
			final List<I_M_InOutLine> iolsForHU = huAssignmentDAO.retrieveModelsForHU(hu, I_M_InOutLine.class, topLevel);
			for (final I_M_InOutLine iol : iolsForHU)
			{
				if (!docActionBL.isStatusCompletedOrClosed(iol.getM_InOut()))
				{
					continue;
				}
				result.add(iol);
			}
		}
		return new ArrayList<I_M_InOutLine>(result);
	}

}
