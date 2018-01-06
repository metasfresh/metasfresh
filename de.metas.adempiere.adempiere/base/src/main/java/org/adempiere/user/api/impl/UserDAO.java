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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_User_Substitute;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;

public class UserDAO implements IUserDAO
{
	private static final transient Logger logger = LogManager.getLogger(UserDAO.class);

	private static final String MSG_MailOrUsernameNotFound = "MailOrUsernameNotFound";

	@Override
	public I_AD_User retrieveLoginUserByUserId(final String userId)
	{
		final IQueryBuilder<I_AD_User> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsSystemUser, true);

		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addEqualsFilter(I_AD_User.COLUMNNAME_Login, userId)
				.addEqualsFilter(I_AD_User.COLUMNNAME_EMail, userId);

		final List<I_AD_User> users = queryBuilder.create().list();
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
	public I_AD_User retrieveLoginUserByUserIdAndPassword(String userId, final String password)
	{
		final I_AD_User user = retrieveLoginUserByUserId(userId);
		if (!Objects.equals(password, user.getPassword()))
		{
			throw new AdempiereException("@UserOrPasswordInvalid@");
		}
		return user;
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
	public I_AD_User retrieveUserOrNull(@CacheCtx final Properties ctx, final int adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, adUserId)
				.create()
				.firstOnly(I_AD_User.class);
	}

	@Override
	public I_AD_User retrieveUser(final int adUserId)
	{
		final I_AD_User user = retrieveUserOrNull(Env.getCtx(), adUserId);
		if (user == null)
		{
			throw new AdempiereException("No user found for ID=" + adUserId);
		}
		return user;
	}

	// NOTE: never cache it
	@Override
	public I_AD_User retrieveUserInTrx(final int adUserId)
	{
		final I_AD_User user = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, adUserId)
				.create()
				.firstOnlyNotNull(I_AD_User.class);
		if (user == null)
		{
			throw new AdempiereException("No user found for ID=" + adUserId);
		}
		return user;
	}

	@Override
	public I_AD_User retrieveDefaultUser(I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner, true);

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_AD_User.class)
				.addColumn(I_AD_User.COLUMNNAME_AD_User_ID, false)
				.createQueryOrderBy();

		return queryBL.createQueryBuilder(I_AD_User.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsDefaultContact, true)
				.create()
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.first(I_AD_User.class);

	}

	@Override
	public String retrieveUserFullname(final int adUserId)
	{
		final String fullname = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, adUserId)
				.create()
				.first(I_AD_User.COLUMNNAME_Name, String.class);
		return !Check.isEmpty(fullname) ? fullname : "?";
	}

	@Override
	public List<Integer> retrieveSystemUserIds()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsSystemUser, true)
				.orderBy().addColumn(I_AD_User.COLUMNNAME_AD_User_ID, false).endOrderBy()
				.create()
				.listIds();
	}
}
