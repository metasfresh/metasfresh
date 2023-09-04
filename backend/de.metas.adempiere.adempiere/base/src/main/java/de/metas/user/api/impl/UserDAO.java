package de.metas.user.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.AdMessageKey;
import de.metas.job.JobId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Optional;
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
	private static final Logger logger = LogManager.getLogger(UserDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AdMessageKey MSG_MailOrUsernameNotFound = AdMessageKey.of("MailOrUsernameNotFound");

	@Override
	public org.compiere.model.I_AD_User retrieveLoginUserByUserId(final String userId)
	{
		final IQueryBuilder<org.compiere.model.I_AD_User> queryBuilder = queryBL
				.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsSystemUser, true);

		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_Login, userId)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_EMail, userId);

		final List<org.compiere.model.I_AD_User> users = queryBuilder.create().list();
		if (users.size() > 1)
		{
			logger.info("More then one user found for UserId '{}': {}", userId, users);
			throw new AdempiereException(MSG_MailOrUsernameNotFound).markAsUserValidationError();
		}
		else if (users.isEmpty())
		{
			throw new AdempiereException(MSG_MailOrUsernameNotFound).markAsUserValidationError();
		}
		else
		{
			return users.get(0);
		}
	}

	@Override
	public org.compiere.model.I_AD_User getByPasswordResetCode(@NonNull final String passwordResetCode)
	{
		final org.compiere.model.I_AD_User user = queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_PasswordResetCode, passwordResetCode)
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
	@Cached(cacheName = org.compiere.model.I_AD_User.Table_Name)
	public org.compiere.model.I_AD_User retrieveUserOrNull(@CacheCtx final Properties ctx, final int adUserId)
	{
		return queryBL
				.createQueryBuilder(I_AD_User.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID, adUserId)
				.create()
				.firstOnly(I_AD_User.class);
	}

	@Override
	public org.compiere.model.I_AD_User getById(@NonNull final UserId adUserId)
	{
		final org.compiere.model.I_AD_User user = retrieveUserOrNull(Env.getCtx(), adUserId.getRepoId());
		if (user == null)
		{
			throw new AdempiereException("No user found for ID=" + adUserId).markAsUserValidationError();
		}
		return user;
	}

	// NOTE: never cache it
	@Override
	public org.compiere.model.I_AD_User getByIdInTrx(final int adUserId)
	{
		final org.compiere.model.I_AD_User user = queryBL
				.createQueryBuilder(I_AD_User.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID, adUserId)
				.create()
				.firstOnly(I_AD_User.class);
		if (user == null)
		{
			throw new AdempiereException("No user found for ID=" + adUserId).markAsUserValidationError();
		}
		return user;
	}

	@Override
	public String retrieveUserFullName(final int userRepoId)
	{
		final UserId userId = UserId.ofRepoIdOrNull(userRepoId);
		if (userId == null)
		{
			return "?";
		}
		return retrieveUserFullName(userId);
	}

	@Override
	public String retrieveUserFullName(final UserId userId)
	{
		if (userId == null)
		{
			return "?";
		}
		final String fullname = queryBL
				.createQueryBuilder(I_AD_User.class)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID, userId)
				.create()
				.first(org.compiere.model.I_AD_User.COLUMNNAME_Name, String.class);

		return fullname != null && !Check.isEmpty(fullname) ? fullname : "<" + userId.getRepoId() + ">";
	}

	@Override
	public UserId retrieveUserIdByEMail(@Nullable final String email, @NonNull final ClientId adClientId)
	{
		if (email == null || Check.isBlank(email))
		{
			return null;
		}

		final String emailNorm = extractEMailAddressOrNull(email);
		if (emailNorm == null)
		{
			return null;
		}

		final Set<UserId> userIds = queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_EMail, emailNorm)
				.addInArrayFilter(org.compiere.model.I_AD_User.COLUMNNAME_AD_Client_ID, ClientId.SYSTEM, adClientId)
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

	@Nullable
	private static String extractEMailAddressOrNull(final String email)
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
		return queryBL.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsSystemUser, true)
				.orderByDescending(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID)
				.create()
				.listIds(UserId::ofRepoId);
	}

	@Override
	public boolean isSystemUser(@NonNull final UserId userId)
	{
		return queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID, userId)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsSystemUser, true)
				.create()
				.anyMatch();
	}

	@Override
	public BPartnerId getBPartnerIdByUserId(@NonNull final UserId userId)
	{
		final org.compiere.model.I_AD_User userRecord = getById(userId);
		return BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID());
	}

	@Override
	public <T extends org.compiere.model.I_AD_User> T getByIdInTrx(final UserId userId, final Class<T> modelClass)
	{
		return load(userId, modelClass);
	}

	@Override
	@Nullable
	public UserId retrieveUserIdByLogin(@NonNull final String login)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_User.class)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_Login, login)
				.create()
				.firstId(UserId::ofRepoIdOrNull);
	}

	@Override
	public void save(@NonNull final org.compiere.model.I_AD_User user)
	{
		InterfaceWrapperHelper.save(user);
	}

	@Override
	public Optional<I_AD_User> getCounterpartUser(
			@NonNull final UserId sourceUserId,
			@NonNull final OrgId targetOrgId)
	{
		final OrgMappingId orgMappingId = getOrgMappingId(sourceUserId).orElse(null);
		if (orgMappingId == null)
		{
			return Optional.empty();
		}

		final UserId targetUserId = queryBL.createQueryBuilder(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_Org_Mapping_ID, orgMappingId)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_Org_ID, targetOrgId)
				.orderByDescending(I_AD_User.COLUMNNAME_AD_User_ID)
				.create()
				.firstId(UserId::ofRepoIdOrNull);

		if (targetUserId == null)
		{
			return Optional.empty();
		}

		return Optional.of(getById(targetUserId));
	}

	@Override
	public ImmutableSet<UserId> retrieveUsersByJobId(@NonNull final JobId jobId)
	{
		return queryBL.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_Job_ID, jobId)
				.create()
				.listIds(UserId::ofRepoId);
	}

	@NonNull
	public ImmutableSet<UserId> retrieveUserIdsByExternalId(@NonNull final String externalId, @NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_ExternalId, externalId)
				.addInArrayFilter(I_AD_User.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.create()
				.listIds(UserId::ofRepoId);
	}

	private Optional<OrgMappingId> getOrgMappingId(@NonNull final UserId sourceUserId)
	{
		final I_AD_User sourceUserRecord = getById(sourceUserId);
		return OrgMappingId.optionalOfRepoId(sourceUserRecord.getAD_Org_Mapping_ID());
	}
}
