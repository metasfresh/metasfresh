package de.metas.ui.web.service.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Location;
import de.metas.adempiere.service.ILocationBL;
import de.metas.ui.web.service.ILocationService;
import de.metas.ui.web.window.shared.datatype.LookupValue;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class LocationService implements ILocationService
{
	// FIXME: this service will be deleted when task "FRESH-287 Precalculate C_Location display address" will be implemented

	@Override
	public LookupValue findLookupValueById(final Object recordId)
	{
		if (recordId == null)
		{
			return null;
		}
		final int locationId = (int)recordId;
		if (locationId <= 0)
		{
			return null;
		}

		final I_C_Location location = InterfaceWrapperHelper.create(Env.getCtx(), locationId, I_C_Location.class, ITrx.TRXNAME_None);
		if (location == null)
		{
			return null;
		}

		final String displayName = Services.get(ILocationBL.class).mkAddress(location);
		return LookupValue.of(locationId, displayName);
	}
}
