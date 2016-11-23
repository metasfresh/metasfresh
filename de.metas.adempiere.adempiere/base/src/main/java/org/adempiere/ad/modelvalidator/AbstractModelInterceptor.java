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


import org.adempiere.ad.security.IUserLoginListener;
import org.compiere.model.I_AD_Client;

/**
 * Template class to be used when implementing custom {@link IModelInterceptor}s.
 *
 * @author tsa
 *
 */
public abstract class AbstractModelInterceptor implements IModelInterceptor, IUserLoginListener
{
	private int adClientId = -1;

	@Override
	public final void initialize(final IModelValidationEngine engine, final I_AD_Client client)
	{
		adClientId = client == null ? -1 : client.getAD_Client_ID();
		onInit(engine, client);
	}

	/**
	 * Called when interceptor is registered and needs to be initialized
	 *
	 * @param engine
	 * @param client
	 */
	protected abstract void onInit(final IModelValidationEngine engine, final I_AD_Client client);

	@Override
	public final int getAD_Client_ID()
	{
		return adClientId;
	}

	// NOTE: method signature shall be the same as org.adempiere.ad.security.IUserLoginListener.onUserLogin(int, int, int)
	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing
	}

	@Override
	public void beforeLogout(final org.compiere.model.I_AD_Session session)
	{
		// nothing
	}

	@Override
	public void afterLogout(final org.compiere.model.I_AD_Session session)
	{
		// nothing
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		// nothing
	}

	@Override
	public void onDocValidate(final Object model, final DocTimingType timing) throws Exception
	{
		// nothing
	}

}
