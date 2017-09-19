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
import org.adempiere.ad.session.MFSession;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import lombok.NonNull;

/**
 * Wraps an {@link IModelInterceptor} and make it behave like an {@link ModelValidator}
 *
 * @author tsa
 *
 */
public final class ModelInterceptor2ModelValidatorWrapper implements ModelValidator, IUserLoginListener
{
	public static final ModelValidator wrapIfNeeded(final IModelInterceptor interceptor)
	{
		if (interceptor instanceof ModelValidator)
		{
			return (ModelValidator)interceptor;
		}
		else
		{
			return new ModelInterceptor2ModelValidatorWrapper(interceptor);
		}
	}

	private final IModelInterceptor interceptor;
	/** {@link #interceptor} casted as {@link IUserLoginListener} or null */
	private final IUserLoginListener userLoginListener;

	private ModelInterceptor2ModelValidatorWrapper(@NonNull final IModelInterceptor interceptor)
	{
		this.interceptor = interceptor;
		
		if (interceptor instanceof IUserLoginListener)
		{
			this.userLoginListener = (IUserLoginListener)interceptor;
		}
		else
		{
			this.userLoginListener = null;
		}
	}

	@Override
	public final String toString()
	{
		return interceptor.toString();
	}

	@Override
	public final int hashCode()
	{
		return interceptor.hashCode();
	}

	@Override
	public final boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof ModelInterceptor2ModelValidatorWrapper)
		{
			final ModelInterceptor2ModelValidatorWrapper wrapper = (ModelInterceptor2ModelValidatorWrapper)obj;
			return interceptor.equals(wrapper.interceptor);
		}
		else if (obj instanceof IModelInterceptor)
		{
			final IModelInterceptor interceptor2 = (IModelInterceptor)obj;
			return interceptor.equals(interceptor2);
		}

		return false;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		interceptor.initialize(engine, client);
	}

	@Override
	public final int getAD_Client_ID()
	{
		return interceptor.getAD_Client_ID();
	}

	@Override
	public final String modelChange(final PO po, final int changeTypeCode) throws Exception
	{
		final ModelChangeType changeType = ModelChangeType.valueOf(changeTypeCode);
		interceptor.onModelChange(po, changeType);
		return null;
	}

	@Override
	public final String docValidate(final PO po, final int timingCode) throws Exception
	{
		final DocTimingType timing = DocTimingType.valueOf(timingCode);
		interceptor.onDocValidate(po, timing);
		return null;
	}

	@Override
	public final String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		interceptor.onUserLogin(AD_Org_ID, AD_Role_ID, AD_User_ID);
		return null;
	}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		interceptor.onUserLogin(AD_Org_ID, AD_Role_ID, AD_User_ID);
	}

	@Override
	public void beforeLogout(final MFSession session)
	{
		if (userLoginListener != null)
		{
			userLoginListener.beforeLogout(session);
		}
	}

	@Override
	public void afterLogout(final MFSession session)
	{
		if (userLoginListener != null)
		{
			userLoginListener.afterLogout(session);
		}
	}

}
