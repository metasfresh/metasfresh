package de.metas.ui.web.pickingslotsClearing.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
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
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.FillMandatoryException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU)
	private BigDecimal qtyCUsPerTU;

	private static final String PARAM_IsAllQty = "IsAllQty";
	@Param(parameterName = PARAM_IsAllQty)
	private boolean isAllQty;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		//
		// Validate the picking slots clearing selected row (left side)
		// i.e. the ones which we will Take Out
		final List<PickingSlotRow> pickingSlotRows = getSelectedPickingSlotRows();
		if (pickingSlotRows.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select some picking slots CUs");
		}
		for (final PickingSlotRow pickingSlotRow : pickingSlotRows)
		{
			if (!pickingSlotRow.isCU())
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("select a CU");
			}
		}

		//
		// Validate the packing HUs selected row (right side)
		// i.e. the HU where we will pack to
		if (!isSingleSelectedPackingHUsRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one TU to pack to");
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
		if (PARAM_IsAllQty.equals(parameter.getColumnName()))
		{
			return getSelectedPickingSlotRows().size() > 1;
		}
		else if (PARAM_QtyCUsPerTU.equals(parameter.getColumnName()))
		{
			final List<PickingSlotRow> pickingSlotRows = getSelectedPickingSlotRows();
			if (pickingSlotRows.size() != 1)
			{
				return DEFAULT_VALUE_NOTAVAILABLE;
			}

			return pickingSlotRows.get(0).getHuQtyCU();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final List<I_M_HU> fromCUs = getSourceCUs();
		final IAllocationSource source = HUListAllocationSourceDestination.of(fromCUs)
				.setDestroyEmptyHUs(true);

		final IAllocationDestination destination = HUListAllocationSourceDestination.of(getTargetTU());

		final HULoader huLoader = HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false);

		//
		// Unload CU/CUs and Load to selected TU
		final List<Integer> huIdsDestroyedCollector = new ArrayList<>();
		if (fromCUs.size() == 1)
		{
			huLoader.load(prepareUnloadRequest(fromCUs.get(0), getQtyCUsPerTU())
					.setForceQtyAllocation(true)
					.addEmptyHUListener(EmptyHUListener.doBeforeDestroyed(hu -> huIdsDestroyedCollector.add(hu.getM_HU_ID())))
					.create());
		}
		else
		{
			final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
			final IMutableHUContext huContext = huContextFactory.createMutableHUContext();
			huContext.addEmptyHUListener(EmptyHUListener.doBeforeDestroyed(hu -> huIdsDestroyedCollector.add(hu.getM_HU_ID())));

			huLoader.unloadAllFromSource(huContext);
		}

		// Remove from picking slots all destroyed HUs
		pickingCandidateService.inactivateForHUIds(HuId.fromRepoIds(huIdsDestroyedCollector));

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

	private BigDecimal getQtyCUsPerTU()
	{
		if (isAllQty)
		{
			return getSingleSelectedPickingSlotRow().getHuQtyCU();
		}
		else
		{
			final BigDecimal qtyCU = this.qtyCUsPerTU;
			if (qtyCU == null || qtyCU.signum() <= 0)
			{
				throw new FillMandatoryException(PARAM_QtyCUsPerTU);
			}
			return qtyCU;
		}
	}

	private List<I_M_HU> getSourceCUs()
	{
		return getSelectedPickingSlotRows()
				.stream()
				.peek(huRow -> Check.assume(huRow.isCU(), "row {} shall be a CU", huRow))
				.map(PickingSlotRow::getHuId)
				.distinct()
				.map(huId -> load(huId, I_M_HU.class))
				.collect(ImmutableList.toImmutableList());
	}

	private I_M_HU getTargetTU()
	{
		final HUEditorRow huRow = getSingleSelectedPackingHUsRow();
		Check.assume(huRow.isTU(), "row {} shall be a TU", huRow);
		return huRow.getM_HU();
	}
}
