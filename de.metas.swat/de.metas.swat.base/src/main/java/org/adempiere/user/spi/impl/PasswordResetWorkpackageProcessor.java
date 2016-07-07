package org.adempiere.user.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_AD_User;
import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.email.IMailBL;

public class PasswordResetWorkpackageProcessor implements IWorkpackageProcessor
{
	public static final int DEFAULT_SkipTimeoutOnConnectionError = 1000 * 60 * 15; // 15min

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		final List<I_AD_User> users = Services.get(IQueueDAO.class).retrieveItems(workpackage, I_AD_User.class, localTrxName);
		for (final I_AD_User user : users)
		{
			try
			{
				Services.get(IUserBL.class).createResetPasswordByEMailRequest(user);
			}
			catch (Exception e)
			{
				if (Services.get(IMailBL.class).isConnectionError(e))
				{
					throw WorkpackageSkipRequestException.createWithTimeoutAndThrowable(e.getLocalizedMessage(), DEFAULT_SkipTimeoutOnConnectionError, e);
				}
				else if (e instanceof RuntimeException)
				{
					throw (RuntimeException)e;
				}
				else
				{
					throw new AdempiereException(e);
				}
			}
		}

		return Result.SUCCESS;
	}
}
