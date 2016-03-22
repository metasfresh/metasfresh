package org.adempiere.user.api.impl;

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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_User_Substitute;
import org.compiere.model.Query;
import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.util.CacheCtx;

public class UserDAO implements IUserDAO
{
	private static final transient Logger logger = LogManager.getLogger(UserDAO.class);

	@Override
	public I_AD_User retrieveLoginUserByUserId(final Properties ctx, final String userId)
	{
		final String whereClause = "(" + I_AD_User.COLUMNNAME_Login + "=? OR " + I_AD_User.COLUMNNAME_EMail + "=?" + ")"
				+ " AND " + I_AD_User.COLUMNNAME_AD_Client_ID + " > 0" // Security Protection
		;
		final List<I_AD_User> users = new Query(ctx, I_AD_User.Table_Name, whereClause, null)
				.setParameters(userId, userId)
				.setOnlyActiveRecords(true)
				.list(I_AD_User.class);
		if (users.size() > 1)
		{
			logger.info("More then one user found for UserId '{}': {}", new Object[] { userId, users });
			throw new AdempiereException("@" + MSG_MailOrUsernameNotFound + "@");
		}
		if (users.size() == 0)
		{
			throw new AdempiereException("@" + MSG_MailOrUsernameNotFound + "@");
		}

		return users.get(0);
	}

	@Override
	public I_AD_User retrieveUserByPasswordResetCode(final Properties ctx, final String passwordResetCode)
	{
		String whereClause = I_AD_User.COLUMNNAME_PasswordResetCode + "=?";
		return new Query(ctx, I_AD_User.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(passwordResetCode)
				.firstOnly(I_AD_User.class);
	}

	@Override
	public List<I_AD_User> retrieveUsersSubstitudedBy(final I_AD_User user)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(user);
		final String trxName = InterfaceWrapperHelper.getTrxName(user);

		final Timestamp date = SystemTime.asDayTimestamp();
		final int adUserId = user.getAD_User_ID();

		return retrieveUsersSubstitudedBy(ctx, adUserId, date, trxName);
	}

	@Override
	public List<I_AD_User> retrieveUsersSubstitudedBy(final Properties ctx, final int adUserId, final Timestamp date, final String trxName)
	{
		final String wc = "EXISTS ("
				+ " select 1"
				+ " from " + I_AD_User_Substitute.Table_Name + " us"
				+ " where us." + I_AD_User_Substitute.COLUMNNAME_Substitute_ID + "=?"
				+ " AND us." + I_AD_User_Substitute.COLUMNNAME_AD_User_ID + "=AD_User.AD_User_ID"
				+ " AND us." + I_AD_User_Substitute.COLUMNNAME_IsActive + "=?"
				+ " AND (us." + I_AD_User_Substitute.COLUMNNAME_ValidFrom + " IS NULL OR us." + I_AD_User_Substitute.COLUMNNAME_ValidFrom + " <= ?)"
				+ " AND (us." + I_AD_User_Substitute.COLUMNNAME_ValidTo + " IS NULL OR us." + I_AD_User_Substitute.COLUMNNAME_ValidTo + ">= ?)"
				+ ")";

		final List<I_AD_User> users = new Query(ctx, I_AD_User.Table_Name, wc, trxName)
				.setParameters(adUserId, true, date, date)
				.setOrderBy(I_AD_User.COLUMNNAME_AD_User_ID)
				.setOnlyActiveRecords(true)
				.list(I_AD_User.class);

		return users;
	}

	@Override
	@Cached(cacheName = I_AD_User.Table_Name)
	public I_AD_User retrieveUser(@CacheCtx final Properties ctx, final int adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, adUserId)
				.create()
				.firstOnly(I_AD_User.class);
	}
}
