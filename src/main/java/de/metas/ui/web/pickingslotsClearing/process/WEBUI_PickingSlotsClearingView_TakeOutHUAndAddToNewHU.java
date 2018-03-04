package de.metas.ui.web.pickingslotsClearing.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
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

public class WEBUI_PickingSlotsClearingView_TakeOutHUAndAddToNewHU extends PickingSlotsClearingViewBasedProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
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
	//
	private static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU, mandatory = true)
	private BigDecimal qtyCU;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isSingleSelectedPickingSlotRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one picking slots HU");
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedPickingSlotRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select an HU");
		}
		if (!pickingSlotRow.isTopLevelHU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select an top level HU");
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
	protected String doIt() throws Exception
	{
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCU);
		}

		final I_M_HU fromHU = getSingleSelectedPickingSlotTopLevelHU();
		final IAllocationSource source = HUListAllocationSourceDestination.of(fromHU)
				.setDestroyEmptyHUs(true);
		final IHUProducerAllocationDestination destination = createHUProducer();
		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(prepareUnloadRequest(fromHU, qtyCU).setForceQtyAllocation(true).create());

		// If the source HU was destroyed, then "remove" it from picking slots
		if (handlingUnitsBL.isDestroyedRefreshFirst(fromHU))
		{
			pickingCandidateService.inactivateForHUId(fromHU.getM_HU_ID());
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
