package org.adempiere.ad.modelvalidator;

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


import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.security.IUserLoginListener;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Session;

public class CompositeModelInterceptor implements IModelInterceptor, IUserLoginListener
{
	private final CopyOnWriteArrayList<IModelInterceptor> interceptors = new CopyOnWriteArrayList<>();
	private final CopyOnWriteArrayList<IUserLoginListener> userLoginListeners = new CopyOnWriteArrayList<>();
	private int adClientId = -1;

	public void addModelInterceptor(final IModelInterceptor interceptor)
	{
		Check.assumeNotNull(interceptor, "interceptor not null");
		interceptors.addIfAbsent(interceptor);

		if (interceptor instanceof IUserLoginListener)
		{
			userLoginListeners.addIfAbsent((IUserLoginListener)interceptor);
		}
	}

	public void removeModelInterceptor(final IModelInterceptor interceptor)
	{
		Check.assumeNotNull(interceptor, "interceptor not null");
		interceptors.remove(interceptor);

		if (interceptor instanceof IUserLoginListener)
		{
			userLoginListeners.remove(interceptor);
		}
	}

	@Override
	public void initialize(final IModelValidationEngine engine, final I_AD_Client client)
	{
		if (client != null)
		{
			adClientId = client.getAD_Client_ID();
		}
		for (final IModelInterceptor interceptor : interceptors)
		{
			interceptor.initialize(engine, client);
		}
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		for (final IModelInterceptor interceptor : interceptors)
		{
			interceptor.onModelChange(model, changeType);
		}
	}

	@Override
	public void onDocValidate(final Object model, final DocTimingType timing) throws Exception
	{
		for (final IModelInterceptor interceptor : interceptors)
		{
			interceptor.onDocValidate(model, timing);
		}
	}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// NOTE: we use "interceptors" instead of userLoginListeners because the "onUserLogin" it's implemented on IModelInterceptor level
		for (final IModelInterceptor interceptor : interceptors)
		{
			interceptor.onUserLogin(AD_Org_ID, AD_Role_ID, AD_User_ID);
		}
	}

	@Override
	public void beforeLogout(final I_AD_Session session)
	{
		for (final IUserLoginListener listener : userLoginListeners)
		{
			listener.beforeLogout(session);
		}
	}

	@Override
	public void afterLogout(final I_AD_Session session)
	{
		for (final IUserLoginListener listener : userLoginListeners)
		{
			listener.afterLogout(session);
		}
	}
}
