package de.metas.ui.web.pickingslotsClearing.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.pickingslotsClearing.PickingSlotsClearingView;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.HashSet;

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
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU, mandatory = true)
	private int qtyTU;

	//
	@Nullable
	private HUExtractedFromPickingSlotEvent huExtractedFromPickingSlotEvent;

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
			return handlingUnitsBL.getTUsCount(getTUOrAggregatedTU()).toInt();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final I_M_HU tuOrAggregatedHU = getTUOrAggregatedTU();
		final QtyTU qtyTU = QtyTU.ofInt(this.qtyTU);
		final QtyTU maxQtyTU = handlingUnitsBL.getTUsCount(tuOrAggregatedHU);
		if (qtyTU.isGreaterThan(maxQtyTU))
		{
			throw new AdempiereException("@QtyTU@ <= " + maxQtyTU);
		}

		final I_M_HU luHU = getTargetLU();

		//
		// If it's a top level TU then
		// first remove it from picking slots
		final boolean isTopLevelHU = handlingUnitsBL.isTopLevel(tuOrAggregatedHU);
		if (isTopLevelHU)
		{
			huPickingSlotBL.removeFromPickingSlotQueueRecursivelly(tuOrAggregatedHU);
			this.huExtractedFromPickingSlotEvent = HUExtractedFromPickingSlotEvent.builder()
					.huId(tuOrAggregatedHU.getM_HU_ID())
					.bpartnerId(tuOrAggregatedHU.getC_BPartner_ID())
					.bpartnerLocationId(tuOrAggregatedHU.getC_BPartner_Location_ID())
					.build();

		}

		final HashSet<HuId> huIdsDestroyedCollector = new HashSet<>();
		HUTransformService.builder()
				.emptyHUListener(EmptyHUListener.collectDestroyedHUIdsTo(huIdsDestroyedCollector))
				.build()
				.tuToExistingLU(tuOrAggregatedHU, qtyTU.toBigDecimal(), luHU);

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
		final PickingSlotsClearingView pickingSlotsClearingView = getPickingSlotsClearingView();
		pickingSlotsClearingView.invalidateAll();
		getPackingHUsView().invalidateAll();

		if (huExtractedFromPickingSlotEvent != null)
		{
			pickingSlotsClearingView.handleEvent(huExtractedFromPickingSlotEvent);
		}
	}

	@NonNull
	private I_M_HU getTargetLU()
	{
		final HUEditorRow packingHURow = getSingleSelectedPackingHUsRow();
		Check.assume(packingHURow.isLU(), "Pack to HU shall be a LU: {}", packingHURow);

		return handlingUnitsBL.getById(packingHURow.getHuId());
	}

	@NonNull
	private I_M_HU getTUOrAggregatedTU()
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedPickingSlotRow();
		Check.assume(pickingSlotRow.isTU(), "Picking slot HU shall be a TU: {}", pickingSlotRow);

		return handlingUnitsBL.getById(pickingSlotRow.getHuId());
	}
}
