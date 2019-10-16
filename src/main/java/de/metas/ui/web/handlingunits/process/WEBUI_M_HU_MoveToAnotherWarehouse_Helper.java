package de.metas.ui.web.handlingunits.process;

import java.util.Comparator;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.movement.api.HUMovementResult;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Services;

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
 *
 */

public abstract class WEBUI_M_HU_MoveToAnotherWarehouse_Helper extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);

	private HUMovementResult movementResult = null;

	@Param(parameterName = I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, mandatory = true)
	private I_M_Warehouse warehouse;

	@Override
	@OverridingMethodsMustInvokeSuper
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		if (!streamSelectedHUIds(Select.ONLY_TOPLEVEL).findAny().isPresent())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		final boolean activeTopLevelHUsSelected = streamSelectedHUs(Select.ONLY_TOPLEVEL)
				.filter(huRecord -> huStatusBL.isPhysicalHU(huRecord))
				.findAny()
				.isPresent();
		if (!activeTopLevelHUsSelected)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no physical (etc active) hus selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, numericKey = true, lookupSource = LookupSource.lookup)
	public LookupValuesList getAvailableWarehouses(final LookupDataSourceContext evalCtx)
	{
		final List<org.compiere.model.I_M_Warehouse> warehousesToLoad = handlingUnitsDAO.retrieveWarehousesWhichContainNoneOf(streamSelectedHUs(Select.ONLY_TOPLEVEL).collect(ImmutableList.toImmutableList()));

		return warehousesToLoad.stream()
				.sorted(Comparator.comparing(org.compiere.model.I_M_Warehouse::getName))
				.map(warehouse -> IntegerLookupValue.of(warehouse.getM_Warehouse_ID(), warehouse.getName()))
				.filter(evalCtx.getFilterPredicate())
				.collect(LookupValuesList.collect());
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_M_HU> hus = streamSelectedHUs(Select.ONLY_TOPLEVEL).collect(ImmutableList.toImmutableList());

		assertHUsEligible();
		movementResult = huMovementBL.moveHUsToWarehouse(hus, warehouse);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		// Invalidate the view
		// On refresh we expect the HUs we moved, to not be present here anymore.
		if (movementResult != null)
		{
			getView().removeHUsAndInvalidate(movementResult.getHusMoved());
		}
	}

	public abstract void assertHUsEligible();

}
