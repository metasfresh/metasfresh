package de.metas.ui.web.pickingslotsClearing.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
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

public class WEBUI_PickingSlotsClearingView_TakeOutHUAndAddToHU extends PickingSlotsClearingViewBasedProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	// services
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
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
		// Make sure we have something selected on left side and right side view
		if (!isSingleSelectedPickingSlotRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one picking slots HU");
		}
		if (!isSingleSelectedPackingHUsRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one HU to pack to");
		}

		//
		// Validate the picking slots clearing selected row
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
		// Validate the packing HUs selected row
		final HUEditorRow packingHURow = getSingleSelectedPackingHUsRow();
		if (!packingHURow.isTopLevel())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select an top level HU to pack too");
		}

		//
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_QtyCU.equals(parameter.getColumnName()))
		{
			final I_M_HU fromHU = getFromHU();
			final IHUContext huContext = huContextFactory.createMutableHUContext();
			final IHUStorage fromHUStorage = huContext.getHUStorageFactory()
					.getStorage(fromHU);
			final I_M_Product product = fromHUStorage.getSingleProductOrNull();
			if (product == null)
			{
				return BigDecimal.ZERO;
			}
			return fromHUStorage.getProductStorage(product).getQty();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final I_M_HU fromHU = getFromHU();
		final IAllocationSource source = HUListAllocationSourceDestination.of(fromHU)
				.setDestroyEmptyHUs(true);
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(getTargetHU());
		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(createAllocationRequest(fromHU));

		// If the source HU was destroyed, then "remove" it from picking slots
		if (handlingUnitsBL.isDestroyedRefreshFirst(fromHU))
		{
			pickingCandidateService.inactivateForHUId(fromHU.getM_HU_ID());
		}

		// Invalidate views
		getPickingSlotsClearingView().invalidateAll();
		getPackingHUsView().invalidateAll();

		return MSG_OK;
	}

	private I_M_HU getFromHU()
	{
		final PickingSlotRow huRow = getSingleSelectedPickingSlotRow();
		Check.assume(huRow.isTopLevelHU(), "row {} shall be a top level HU", huRow);
		final I_M_HU fromHU = InterfaceWrapperHelper.load(huRow.getHuId(), I_M_HU.class);
		return fromHU;
	}

	private I_M_HU getTargetHU()
	{
		final HUEditorRow huRow = getSingleSelectedPackingHUsRow();
		Check.assume(huRow.isTopLevel(), "row {} shall be a top level HU", huRow);
		return huRow.getM_HU();
	}

	private IAllocationRequest createAllocationRequest(final I_M_HU fromHU)
	{
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCU);
		}

		final IHUContext huContext = huContextFactory.createMutableHUContext();

		final IHUStorage fromHUStorage = huContext.getHUStorageFactory()
				.getStorage(fromHU);
		final I_M_Product product = fromHUStorage.getSingleProductOrNull();
		if (product == null)
		{
			throw new AdempiereException("Cannot determine the product to transfer from " + fromHU);
		}
		final IHUProductStorage productStorage = fromHUStorage.getProductStorage(product);

		return AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(qtyCU, productStorage.getC_UOM())
				.setDateAsToday()
				.setFromReferencedModel(null)
				.setForceQtyAllocation(false)
				.create();
	}
}
