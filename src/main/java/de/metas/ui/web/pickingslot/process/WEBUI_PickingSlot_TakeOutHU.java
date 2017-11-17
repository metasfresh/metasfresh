package de.metas.ui.web.pickingslot.process;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.pickingslot.AggregationPickingSlotView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public class WEBUI_PickingSlot_TakeOutHU extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final List<Integer> huIdsRemoved = new ArrayList<>();

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedDocumentIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!selectedRowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow row = PickingSlotRow.cast(getSingleSelectedRow());
		if (!row.isTopLevelHU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select a top level HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		//
		// Get the HU
		final PickingSlotRow row = PickingSlotRow.cast(getSingleSelectedRow());
		Preconditions.checkState(row.isTopLevelHU(), "row %s shall be a top level HU", row);
		final I_M_HU hu = InterfaceWrapperHelper.load(row.getHuId(), I_M_HU.class);
		final String huStatus = hu.getHUStatus();

		//
		// Remove the HU from it's picking slot
		Services.get(IHUPickingSlotBL.class).removeFromPickingSlotQueueRecursivelly(hu);

		//
		// Move the HU to an after picking locator
		final I_M_Locator afterPickingLocator = Services.get(IHUWarehouseDAO.class).suggestAfterPickingLocator(hu.getM_Locator());
		if (afterPickingLocator.getM_Locator_ID() != hu.getM_Locator_ID())
		{
			final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
			huMovementBL.moveHUsToLocator(ImmutableList.of(hu), afterPickingLocator);

			//
			// FIXME: workaround to restore HU's HUStatus (i.e. which was changed from Picked to Active by the meveHUsToLocator() method, indirectly).
			// See https://github.com/metasfresh/metasfresh-webui-api/issues/678#issuecomment-344876035, that's the stacktrace where the HU status was set to Active.
			InterfaceWrapperHelper.refresh(hu, ITrx.TRXNAME_ThreadInherited);
			if (!Objects.equal(huStatus, hu.getHUStatus()))
			{
				final IHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
				Services.get(IHandlingUnitsBL.class).setHUStatus(huContext, hu, huStatus);
				save(hu);
			}
		}

		huIdsRemoved.add(hu.getM_HU_ID());

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		//
		// Invalidate the views
		// Expectation: the HU shall disappear from picking slots view (left side) and shall appear on after picking HUs view (right side).
		final AggregationPickingSlotView pickingSlotsView = getView(AggregationPickingSlotView.class);
		pickingSlotsView.invalidateAll();
		invalidateView(pickingSlotsView.getViewId());
		//
		final HUEditorView afterPickingHUsView = pickingSlotsView.getAfterPickingHUViewOrNull();
		if (afterPickingHUsView != null)
		{
			afterPickingHUsView.addHUIdsAndInvalidate(huIdsRemoved);
		}
	}

}
