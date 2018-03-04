package de.metas.ui.web.pickingslotsClearing.process;

import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_PickingSlotsClearingView_TakeOutMultiHUsAndAddToNewHU extends PickingSlotsClearingViewBasedProcess implements IProcessPrecondition
{
	// services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@Autowired
	private PickingCandidateService pickingCandidateService;

	//
	// parameters
	private static final String PARAM_M_HU_PI_ID = I_M_HU_PI.COLUMNNAME_M_HU_PI_ID;
	@Param(parameterName = PARAM_M_HU_PI_ID, mandatory = true)
	private I_M_HU_PI targetHUPI;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final List<PickingSlotRow> pickingSlotRows = getSelectedPickingSlotRows();
		if (pickingSlotRows.size() <= 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select more than one HU");
		}

		final Set<PickingSlotRowId> rootRowIds = getRootRowIdsForSelectedPickingSlotRows();
		if (rootRowIds.size() > 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("all selected HU rows shall be from one picking slot");
		}

		for (final PickingSlotRow pickingSlotRow : pickingSlotRows)
		{
			if (!pickingSlotRow.isPickedHURow())
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("select an HU");
			}
			if (!pickingSlotRow.isTopLevelHU())
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("select an top level HU");
			}
		}

		//
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_M_HU> fromHUs = getSelectedPickingSlotTopLevelHUs();

		final IAllocationSource source = HUListAllocationSourceDestination.of(fromHUs)
				.setDestroyEmptyHUs(true);
		final IHUProducerAllocationDestination destination = createHUProducer();
		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.unloadAllFromSource();

		// If the source HU was destroyed, then "remove" it from picking slots
		final ImmutableSet<Integer> destroyedHUIds = fromHUs.stream()
				.filter(handlingUnitsBL::isDestroyedRefreshFirst)
				.map(I_M_HU::getM_HU_ID)
				.collect(ImmutableSet.toImmutableSet());
		if (!destroyedHUIds.isEmpty())
		{
			pickingCandidateService.inactivateForHUIds(destroyedHUIds);
		}

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		// Invalidate views
		getPickingSlotsClearingView().invalidateAll();
		getPackingHUsView().invalidateAll();
	}

	private IHUProducerAllocationDestination createHUProducer()
	{
		final PickingSlotRow pickingRow = getRootRowForSelectedPickingSlotRows();
		return createNewHUProducer(pickingRow, targetHUPI);
	}
}
