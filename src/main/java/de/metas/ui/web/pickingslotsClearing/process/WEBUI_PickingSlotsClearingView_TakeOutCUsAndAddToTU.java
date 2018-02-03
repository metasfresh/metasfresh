package de.metas.ui.web.pickingslotsClearing.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class WEBUI_PickingSlotsClearingView_TakeOutCUsAndAddToTU extends PickingSlotsClearingViewBasedProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	// services
	@Autowired
	private PickingCandidateService pickingCandidateService;

	//
	// parameters
	private static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal qtyCU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		//
		// Validate the picking slots clearing selected row (left side)
		if (!isSingleSelectedPickingSlotRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one picking slots HU");
		}
		final PickingSlotRow pickingSlotRow = getSingleSelectedPickingSlotRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select an HU");
		}
		if (!pickingSlotRow.isTU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select a TU");
		}

		//
		// Validate the packing HUs selected row (right side)
		if (!isSingleSelectedPackingHUsRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one HU to pack to");
		}
		final HUEditorRow packingHURow = getSingleSelectedPackingHUsRow();
		if (!packingHURow.isTU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select a TU to pack too");
		}

		//
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_QtyCU.equals(parameter.getColumnName()))
		{
			final I_M_HU fromHU = getSingleSelectedPickingSlotTopLevelHU();
			return retrieveQtyCU(fromHU);
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCU);
		}

		final I_M_HU fromTU = getSourceTU();
		final IAllocationSource source = HUListAllocationSourceDestination.of(fromTU)
				.setDestroyEmptyHUs(true);

		final IAllocationDestination destination = HUListAllocationSourceDestination.of(getTargetTU());

		final List<Integer> huIdsDestroyedCollector = new ArrayList<>();

		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(prepareUnloadRequest(fromTU, qtyCU)
						.setForceQtyAllocation(true)
						.addEmptyHUListener(EmptyHUListener.doBeforeDestroyed(hu -> huIdsDestroyedCollector.add(hu.getM_HU_ID())))
						.create());

		// Remove from picking slots all destroyed HUs
		pickingCandidateService.inactivateForHUIds(huIdsDestroyedCollector);

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

	private I_M_HU getSourceTU()
	{
		final PickingSlotRow huRow = getSingleSelectedPickingSlotRow();
		Check.assume(huRow.isTU(), "row {} shall be a TU", huRow);
		return load(huRow.getHuId(), I_M_HU.class);
	}

	private I_M_HU getTargetTU()
	{
		final HUEditorRow huRow = getSingleSelectedPackingHUsRow();
		Check.assume(huRow.isTU(), "row {} shall be a TU", huRow);
		return huRow.getM_HU();
	}
}
