package de.metas.user.api.impl;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_User_Substitute;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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

public class UserDAO implements IUserDAO
{
	private static final transient Logger logger = LogManager.getLogger(UserDAO.class);

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
			logger.info("More then one user found for UserId '{}': {}", userId, users);
			throw new AdempiereException("@" + MSG_MailOrUsernameNotFound + "@").markAsUserValidationError();
		}
		if (users.size() == 0)
		{
			throw new AdempiereException("@" + MSG_MailOrUsernameNotFound + "@").markAsUserValidationError();
		}

		return users.get(0);
	}

	@Override
	public I_AD_User getByPasswordResetCode(@NonNull final String passwordResetCode)
	{
		final I_AD_User user = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_PasswordResetCode, passwordResetCode)
				.create()
				.firstOnly(I_AD_User.class);

		if (user == null)
		{
			throw new AdempiereException("@PasswordResetCodeNoLongerValid@").markAsUserValidationError();
		}
		if (!passwordResetCode.equals(user.getPasswordResetCode()))
		{
			throw new AdempiereException("@PasswordResetCodeNoLongerValid@").markAsUserValidationError();
		}

		return user;
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
	public I_AD_User getById(@NonNull final UserId adUserId)
	{
		final I_AD_User user = retrieveUserOrNull(Env.getCtx(), adUserId.getRepoId());
		if (user == null)
		{
			throw new AdempiereException("No user found for ID=" + adUserId).markAsUserValidationError();
		}
		return user;
	}

	// NOTE: never cache it
	@Override
	public I_AD_User getByIdInTrx(final int adUserId)
	{
		final I_AD_User user = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, adUserId)
				.create()
				.firstOnlyNotNull(I_AD_User.class);
		if (user == null)
		{
			throw new AdempiereException("No user found for ID=" + adUserId).markAsUserValidationError();
		}
		return user;
	}

	@Override
	public String retrieveUserFullname(final int userRepoId)
	{
		final UserId userId = UserId.ofRepoIdOrNull(userRepoId);
		if (userId == null)
		{
			return "?";
		}
		return retrieveUserFullname(userId);
	}

	@Override
	public String retrieveUserFullname(final UserId userId)
	{
		if (userId == null)
		{
			return "?";
		}
		final String fullname = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, userId)
				.create()
				.first(I_AD_User.COLUMNNAME_Name, String.class);

		return !Check.isEmpty(fullname) ? fullname : "<" + userId.getRepoId() + ">";
	}

	@Override
	public UserId retrieveUserIdByEMail(@Nullable final String email, @NonNull final ClientId adClientId)
	{
		if (Check.isEmpty(email, true))
		{
			return null;
		}

		final String emailNorm = extractEMailAddressOrNull(email);
		if (emailNorm == null)
		{
			return null;
		}

		final Set<UserId> userIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_EMail, emailNorm)
				.addInArrayFilter(I_AD_User.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM, adClientId)
				.create()
				.listIds(UserId::ofRepoId);

		if (userIds.isEmpty())
		{
			return null;
		}
		else if (userIds.size() == 1)
		{
			return userIds.iterator().next();
		}
		else
		{
			// more than one user found for given mail.
			// shall not happen but it's better to return null instead of returning to first/random one,
			// because might be that some BL will link confidential infos to that (wrong) user/bpartner.
			logger.info("Found more than one user for email={} (normalized: {}) and clientId={}: {}. Returning null", email, emailNorm, adClientId, userIds);
			return null;
		}
	}

	private static final String extractEMailAddressOrNull(final String email)
	{
		try
		{
			return new InternetAddress(email).getAddress();
		}
		catch (final AddressException e)
		{
			logger.warn("Invalid email address `{}`. Returning null.", email, e);
			return null;
		}
	}

	@Override
	public Set<UserId> retrieveSystemUserIds()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsSystemUser, true)
				.orderByDescending(I_AD_User.COLUMNNAME_AD_User_ID)
				.create()
				.listIds(UserId::ofRepoId);
	}

	@Override
	public boolean isSystemUser(@NonNull final UserId userId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, userId)
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsSystemUser, true)
				.create()
				.anyMatch();
	}

	@Override
	public BPartnerId getBPartnerIdByUserId(@NonNull final UserId userId)
	{
		final I_AD_User userRecord = getById(userId.getRepoId());
		return BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID());
	}

	@Override
	public <T extends org.compiere.model.I_AD_User> T getByIdInTrx(final UserId userId, final Class<T> modelClass)
	{
		final T user = load(userId, modelClass);
		return user;
	}

	@Override
	public Set<UserId> getUserIdsByBPartnerId(final BPartnerId bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.orderBy(I_AD_User.COLUMNNAME_AD_User_ID)
				.create()
				.listIds(UserId::ofRepoId);
	}

	@Nullable
	public UserId retrieveUserIdByLogin(@NonNull final String login)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_Login, login)
				.create()
				.firstId(UserId::ofRepoIdOrNull);
	}
}
