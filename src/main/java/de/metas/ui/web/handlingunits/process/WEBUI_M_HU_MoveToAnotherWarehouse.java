package de.metas.ui.web.handlingunits.process;

import java.util.Comparator;
import java.util.List;

import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsDAO;
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

/**
 * #2144
 * HU editor: Move selected HUs to another warehouse
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_M_HU_MoveToAnotherWarehouse extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Param(parameterName = I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, mandatory = true)
	private I_M_Warehouse warehouse;

	private HUMovementResult movementResult = null;

	@Override
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

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, numericKey = true, lookupSource = LookupSource.lookup)
	public LookupValuesList getAvailableWarehouses()
	{
		final List<org.compiere.model.I_M_Warehouse> warehousesToLoad = handlingUnitsDAO.retrieveWarehousesWhichContainNoneOf(streamSelectedHUs(Select.ONLY_TOPLEVEL).collect(ImmutableList.toImmutableList()));

		return warehousesToLoad.stream()
				.sorted(Comparator.comparing(org.compiere.model.I_M_Warehouse::getName))
				.map(warehouse -> IntegerLookupValue.of(warehouse.getM_Warehouse_ID(), warehouse.getName()))
				.collect(LookupValuesList.collect());
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_M_HU> hus = streamSelectedHUs(Select.ONLY_TOPLEVEL).collect(ImmutableList.toImmutableList());
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
}
