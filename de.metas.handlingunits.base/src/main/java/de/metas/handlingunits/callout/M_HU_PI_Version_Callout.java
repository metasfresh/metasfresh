package de.metas.handlingunits.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;

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


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;

public class M_HU_PI_Version_Callout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_M_HU_PI_Version version = calloutRecord.getModel(I_M_HU_PI_Version.class);
		final I_M_HU_PI huPI = version.getM_HU_PI();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU_PI_Version huPIVersion = handlingUnitsDAO.retrievePICurrentVersionOrNull(huPI);

		// setting the new records flag to true, unless another version of the same M_HU_PI has it already set
		if (huPIVersion == null)
		{
			version.setIsCurrent(true);
		}
		else
		{
			version.setIsCurrent(false);
		}
	}

	@Override
	public void onSave(final ICalloutRecord calloutRecord)
	{
		// NOTE: because the data was not refreshed in the window after the code form the index was called
		calloutRecord.dataRefreshAll();
	}
}
