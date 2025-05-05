/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pickingslotsClearing.process;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.impl.LULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.compiere.SpringContextHolder;

import java.util.Objects;

public class WEBUI_PickingSlotsClearingView_TakeOutTUsAndAddToNewLUs extends PickingSlotsClearingViewBasedProcess implements IProcessPrecondition
{

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isAtLeastOneTopLevelTUSelected())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Select a picking slot or a top level TU.");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedPickingSlotRow();

		final ImmutableSet<HuId> huIds;

		if (pickingSlotRow.isPickingSlotRow())
		{
			huIds = pickingSlotRow.getIncludedRows()
					.stream()
					.filter(PickingSlotRow::isTopLevelTU)
					.map(PickingSlotRow::getHuId)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			huIds = ImmutableSet.of(pickingSlotRow.getHuId());
		}

		//1. filter out the selected HUs which don't qualify for processing
		final ImmutableSet<I_M_HU> hus = huIds.stream()
				.filter(Objects::nonNull)
				.map(handlingUnitsBL::getById)
				.filter(handlingUnitsBL::isTopLevel)
				.filter(handlingUnitsBL::isTransportUnit)
				.filter(hu -> !handlingUnitsBL.isEmptyStorage(hu))
				.filter(this::isPackToLuAllowed)
				.collect(ImmutableSet.toImmutableSet());

		Loggables.withLogger(log, Level.DEBUG).addLog("*** doIt(): start processing the following HUs: {}", hus);

		if (hus.isEmpty())
		{
			throw new AdempiereException("None of the selected HUs could be loaded to an LU!");
		}

		//2.remove qualified HUs from the picking slot
		hus.forEach(huPickingSlotBL::removeFromPickingSlotQueueRecursivelly);

		//3. move to an after picking locator if not already there
		hus.forEach(this::moveToAfterPickingLocator);

		//4. inactivate related picking candidates
		pickingCandidateService.inactivateForHUIds(hus.stream().map(I_M_HU::getM_HU_ID).map(HuId::ofRepoId).collect(ImmutableSet.toImmutableSet()));

		//5. compress the qualified HUs on TUs
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(PlainContextAware.newWithThreadInheritedTrx());
		final LULoader luLoader = new LULoader(huContext);

		hus.forEach(luLoader::addTU);
		luLoader.close();

		Loggables.withLogger(log, Level.DEBUG).addLog("*** doIt(): created the following HUs: {}" , luLoader.getLU_HUs());

		return MSG_OK;
	}

	private boolean isAtLeastOneTopLevelTUSelected()
	{
		if (!isSingleSelectedPickingSlotRow())
		{
			return false;
		}

		final PickingSlotRow selectedPickingSlot = getSingleSelectedPickingSlotRow();

		return selectedPickingSlot.isTopLevelTU()
				|| (selectedPickingSlot.isPickingSlotRow()
				    && selectedPickingSlot.getIncludedRows().stream().anyMatch(PickingSlotRow::isTopLevelTU));

	}

	private boolean isPackToLuAllowed(final I_M_HU hu)
	{
		final I_M_HU_PI_Version mHuPiVersion = hu.getM_HU_PI_Version();

		if (mHuPiVersion == null
				|| mHuPiVersion.getHU_UnitType() != null && !mHuPiVersion.getHU_UnitType().equals(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit))
		{
			return false;
		}

		final BPartnerId bPartnerId = IHandlingUnitsBL.extractBPartnerIdOrNull(hu);

		final I_M_HU_PI_Item actualLUItem = handlingUnitsDAO.retrieveDefaultParentPIItem(mHuPiVersion.getM_HU_PI(), X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bPartnerId);

		return actualLUItem != null;
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
