package de.metas.event;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.session.MFSession;
import org.compiere.util.Ini;

import de.metas.util.Services;

/**
 * Module activator which is initializing the {@link IEventBus} infrastructure.
 *
 * @author tsa
 *
 */
public final class EventBusAdempiereInterceptor extends AbstractModuleInterceptor
{
	public static final transient EventBusAdempiereInterceptor instance = new EventBusAdempiereInterceptor();

	private EventBusAdempiereInterceptor()
	{
		super();
	}

	@Override
	public void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		if (Ini.isSwingClient())
		{
			Services.get(IEventBusFactory.class).initEventBussesWithGlobalListeners();
		}
	}

	@Override
	public void beforeLogout(final MFSession session)
	{
		if (Ini.isSwingClient())
		{
			Services.get(IEventBusFactory.class).destroyAllEventBusses();
		}
	}

}
