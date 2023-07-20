package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.movement.generate.HUMovementGeneratorResult;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

/**
 * This is the common structure for a process that moves HUs from a warehouse to another.
 * Is extended by specific processes, depending of their particular requirements
 *
 * @author metas-dev <dev@metasfresh.com>
 */

abstract class WEBUI_M_HU_MoveToAnotherWarehouse_Template extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final LookupDataSource locatorLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_M_Locator.Table_Name);

	private HUMovementGeneratorResult movementResult = null;

	@Param(parameterName = I_M_Locator.COLUMNNAME_M_Warehouse_ID, mandatory = true)
	private WarehouseId moveToWarehouseId;

	@Param(parameterName = I_M_Locator.COLUMNNAME_M_Locator_ID, mandatory = true)
	private int moveToLocatorRepoId;

	@Override
	protected final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		final ImmutableList<I_M_HU> selectedHUs = getSelectedHUs();
		if (selectedHUs.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		final boolean activeTopLevelHUsSelected = selectedHUs.stream().anyMatch(huStatusBL::isPhysicalHU);
		if (!activeTopLevelHUsSelected)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no physical (e.g. active) hus selected");
		}

		final ProcessPreconditionsResolution eligible = checkHUsEligible(selectedHUs);
		if (eligible.isRejected())
		{
			return eligible;
		}

		return ProcessPreconditionsResolution.accept();
	}

	@OverridingMethodsMustInvokeSuper
	protected LookupValuesList getAvailableWarehouses(final LookupDataSourceContext evalCtx)
	{
		final ImmutableList<I_M_HU> selectedHUs = getSelectedHUs();
		final Set<LocatorId> eligibleLocatorIds = getEligibleLocatorIds(selectedHUs);
		final ImmutableSet<WarehouseId> eligibleWarehouseIds = eligibleLocatorIds.stream().map(LocatorId::getWarehouseId).collect(ImmutableSet.toImmutableSet());

		return warehouseDAO.getByIds(eligibleWarehouseIds)
				.stream()
				.sorted(Comparator.comparing(I_M_Warehouse::getName))
				.map(warehouse -> IntegerLookupValue.of(warehouse.getM_Warehouse_ID(), warehouse.getName()))
				.filter(evalCtx.getFilterPredicate())
				.collect(LookupValuesList.collect());
	}

	@OverridingMethodsMustInvokeSuper
	protected LookupValuesList getAvailableLocators(final LookupDataSourceContext evalCtx)
	{
		final WarehouseId selectedWarehouseId = this.moveToWarehouseId;
		if (selectedWarehouseId == null)
		{
			return LookupValuesList.EMPTY;
		}

		final ImmutableList<I_M_HU> selectedHUs = getSelectedHUs();
		final ImmutableSet<LocatorId> eligibleLocatorIds = getEligibleLocatorIds(selectedHUs)
				.stream()
				.filter(locatorId -> WarehouseId.equals(locatorId.getWarehouseId(), selectedWarehouseId))
				.collect(ImmutableSet.toImmutableSet());

		return locatorLookup.findByIdsOrdered(eligibleLocatorIds);
	}

	private Set<LocatorId> getEligibleLocatorIds(final ImmutableList<I_M_HU> hus)
	{
		if (hus.isEmpty())
		{
			return ImmutableSet.of();
		}

		final Set<LocatorId> huLocatorIds = handlingUnitsDAO.getLocatorIds(hus);

		// use the org of first HU to decide which warehouses to fetch (preserved already existing logic)
		final OrgId orgId = OrgId.ofRepoId(hus.get(0).getAD_Org_ID());
		final Set<WarehouseId> orgWarehouseIds = warehouseDAO.getWarehouseIdsByOrgId(orgId);
		final ImmutableSet<LocatorId> orgLocatorIds = warehouseDAO.getLocatorIdsByWarehouseIds(orgWarehouseIds);

		return Sets.difference(orgLocatorIds, huLocatorIds);
	}

	private LocatorId getMoveToLocatorId()
	{
		if (moveToWarehouseId == null)
		{
			throw new FillMandatoryException(I_M_Locator.COLUMNNAME_M_Warehouse_ID);
		}
		if (moveToLocatorRepoId <= 0)
		{
			throw new FillMandatoryException(I_M_Locator.COLUMNNAME_M_Locator_ID);
		}

		return LocatorId.ofRepoId(moveToWarehouseId, moveToLocatorRepoId);
	}

	protected final ImmutableList<I_M_HU> getSelectedHUs()
	{
		return streamSelectedHUs(Select.ONLY_TOPLEVEL).collect(ImmutableList.toImmutableList());
	}

	@Override
	protected String doIt()
	{
		final LocatorId locatorId = getMoveToLocatorId();

		final List<I_M_HU> hus = getSelectedHUs();
		if (hus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}
		checkHUsEligible(hus).throwExceptionIfRejected();

		movementResult = huMovementBL.moveHUsToLocator(hus, locatorId);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (movementResult != null)
		{
			getView().invalidateAll();
		}
	}

	public abstract ProcessPreconditionsResolution checkHUsEligible(final List<I_M_HU> hus);
}
