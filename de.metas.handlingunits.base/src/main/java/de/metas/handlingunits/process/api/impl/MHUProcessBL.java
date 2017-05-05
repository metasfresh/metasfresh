package de.metas.handlingunits.process.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import org.adempiere.util.Services;

import de.metas.handlingunits.model.I_M_HU_Process;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.process.api.IMHUProcessBL;
import de.metas.handlingunits.process.api.IMHUProcessDAO;

public class MHUProcessBL implements IMHUProcessBL
{

	@Override
	public boolean processFitsType(final int adProcessId, final String selectedHUUnitType)
	{
		final I_M_HU_Process huProcess = Services.get(IMHUProcessDAO.class).retrieveHUProcess(adProcessId);
		if(huProcess == null)
		{
			// no HU process
			return false;
		}

		// Check if the give HU Unit type fits the M_HU_Process equivalent flag

		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(selectedHUUnitType))
		{
			if (huProcess.isApplyLU())
			{
				return true;
			}
		}

		if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(selectedHUUnitType))
		{
			if (huProcess.isApplyTU())
			{
				return true;
			}
		}

		if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(selectedHUUnitType))
		{
			if (huProcess.isApplyVirtualPI())
			{
				return true;
			}
		}

		// If none of the flags fit, then the process (or report) was not designed for it.
		return false;
	}
}
