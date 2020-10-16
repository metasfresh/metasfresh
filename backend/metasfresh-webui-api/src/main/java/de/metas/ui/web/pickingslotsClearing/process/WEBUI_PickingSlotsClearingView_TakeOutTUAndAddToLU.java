package de.metas.ui.web.pickingslotsClearing.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
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
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

public class WEBUI_PickingSlotsClearingView_TakeOutTUAndAddToLU
		extends PickingSlotsClearingViewBasedProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@Autowired
	private PickingCandidateService pickingCandidateService;

	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU, mandatory = true)
	private int qtyTU;

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
		if (!packingHURow.isLU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select a LU to pack too");
		}

		//
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_QtyTU.equals(parameter.getColumnName()))
		{
			return handlingUnitsBL.getTUsCount(getTU()).toInt();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final I_M_HU tuHU = getTU();
		final QtyTU qtyTU = QtyTU.ofInt(this.qtyTU);
		final QtyTU maxQtyTU = handlingUnitsBL.getTUsCount(tuHU);
		if (qtyTU.isGreaterThan(maxQtyTU))
		{
			throw new AdempiereException("@QtyTU@ <= " + maxQtyTU);
		}

		final I_M_HU luHU = getTargetLU();

		final List<Integer> huIdsDestroyedCollector = new ArrayList<>();

		HUTransformService.builder()
				.emptyHUListener(EmptyHUListener.doBeforeDestroyed(hu -> huIdsDestroyedCollector.add(hu.getM_HU_ID())))
				.build()
				.tuToExistingLU(tuHU, qtyTU.toBigDecimal(), luHU);

		// Remove from picking slots all destroyed HUs
		pickingCandidateService.inactivateForHUIds(HuId.fromRepoIds(huIdsDestroyedCollector));

		return MSG_OK;
	}

	@Nullable
	private I_M_HU getTargetLU()
	{
		final HUEditorRow packingHURow = getSingleSelectedPackingHUsRow();
		Check.assume(packingHURow.isLU(), "Pack to HU shall be a LU: {}", packingHURow);
		return packingHURow.getM_HU();
	}

	private I_M_HU getTU()
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedPickingSlotRow();
		Check.assume(pickingSlotRow.isTU(), "Picking slot HU shall be a TU: {}", pickingSlotRow);
		return handlingUnitsBL.getById(pickingSlotRow.getHuId());
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
}
