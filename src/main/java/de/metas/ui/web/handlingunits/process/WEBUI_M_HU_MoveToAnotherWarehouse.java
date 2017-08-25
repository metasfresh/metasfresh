package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.movement.api.HUMovementResult;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * #2144
 * HU editor: Move selected HUs to another warehouse
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_M_HU_MoveToAnotherWarehouse extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private static final String MSG_NoSelectedHU = "NoHUSelected";

	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	@Param(parameterName = I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, mandatory = true)
	private I_M_Warehouse warehouse;

	private HUMovementResult movementResult = null;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Set<Integer> huIds = getSelectedHUIds();
		if (huIds.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_NoSelectedHU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		
		final List<I_M_HU> hus = getSelectedHUs();
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
