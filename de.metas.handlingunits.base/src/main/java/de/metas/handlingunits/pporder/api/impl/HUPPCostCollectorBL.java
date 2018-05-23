package de.metas.handlingunits.pporder.api.impl;

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

import java.util.Collection;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.pporder.api.IHUPPCostCollectorBL;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;

public class HUPPCostCollectorBL implements IHUPPCostCollectorBL
{
	@Override
	public I_PP_Cost_Collector createReceipt(final IReceiptCostCollectorCandidate candidate, final I_M_HU hu)
	{
		Preconditions.checkNotNull(candidate, "candidate is null");
		Preconditions.checkNotNull(hu, "hu is null");

		// services
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Create & process the receipt cost collector
		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.create(ppCostCollectorBL.createReceipt(candidate), I_PP_Cost_Collector.class);

		// Assign the HU to cost collector
		assignHUs(cc, ImmutableList.of(hu));

		//
		// Activate the HU (assuming it was Planning)
		handlingUnitsBL.setHUStatusActive(ImmutableList.of(hu));

		return cc;
	}

	@Override
	public void assignHUs(final org.eevolution.model.I_PP_Cost_Collector cc, final Collection<I_M_HU> husToAssign)
	{
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentBL.assignHUs(cc, husToAssign);
	}

	@Override
	public void assertNoHUAssignments(final org.eevolution.model.I_PP_Cost_Collector cc)
	{
		Services.get(IHUAssignmentDAO.class).assertNoHUAssignmentsForModel(cc);
	}

	/**
	 * @return assigned top level HUs (i.e. the HUs which were assigned to original cost collector).
	 */
	@Override
	public List<I_M_HU> getTopLevelHUs(final org.eevolution.model.I_PP_Cost_Collector cc)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final List<I_M_HU> hus = huAssignmentDAO.retrieveTopLevelHUsForModel(cc);
		return hus;
	}

	@Override
	public void restoreTopLevelHUs(final I_PP_Cost_Collector costCollector)
	{
		Check.assumeNotNull(costCollector, "costCollector not null");

		//
		// Retrieve the HUs which were assigned to original cost collector
		final List<I_M_HU> hus = getTopLevelHUs(costCollector);
		if (hus.isEmpty())
		{
			return;
		}

		//
		// Get the snapshot ID.
		// Make sure it exists, else we would not be able to restore the HUs.
		final String snapshotId = costCollector.getSnapshot_UUID();
		if (Check.isEmpty(snapshotId, true))
		{
			throw new HUException("@NotFound@ @Snapshot_UUID@ (" + costCollector + ")");
		}

		final IContextAware context = InterfaceWrapperHelper.getContextAware(costCollector);
		Services.get(IHUSnapshotDAO.class).restoreHUs()
				.setContext(context)
				.setSnapshotId(snapshotId)
				.setDateTrx(costCollector.getMovementDate())
				.setReferencedModel(costCollector)
				.addModels(hus)
				.restoreFromSnapshot();

	}
}
