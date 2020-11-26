package de.metas.user.api;

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

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.BPartnerId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import org.adempiere.service.ClientId;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface IUserDAO extends ISingletonService
{
	String MSG_MailOrUsernameNotFound = "MailOrUsernameNotFound";

	/**
	 * Retrieves a user whose <code>Login</code> or <code>EMail</code> column equals the given <code>userId</code>.
	 *
	 * @param userId
	 * @return user; never return null
	 */
	I_AD_User retrieveLoginUserByUserId(String userId);

	I_AD_User getByPasswordResetCode(String passwordResetCode);

	List<I_AD_User> retrieveUsersSubstitudedBy(Properties ctx, int adUserId, Timestamp date, String trxName);

	I_AD_User retrieveUserOrNull(Properties ctx, int adUserId);

	/**
	 * @deprecated please use {@link #getById(UserId)} instead
	 */
	@Deprecated
	default I_AD_User getById(final int adUserRepoId)
	{
		return getById(UserId.ofRepoId(adUserRepoId));
	}

	I_AD_User getById(UserId adUserId);

	<T extends org.compiere.model.I_AD_User> T getByIdInTrx(UserId userId, Class<T> modelClass);

	default org.compiere.model.I_AD_User getByIdInTrx(final UserId userId)
	{
		return getByIdInTrx(userId, org.compiere.model.I_AD_User.class);
	}

	I_AD_User getByIdInTrx(int adUserId);

	/**
	 * @return user's full name or <code>?</code> if no found
	 */
	String retrieveUserFullname(int userRepoId);

	String retrieveUserFullname(UserId userId);

	UserId retrieveUserIdByEMail(String email, ClientId adClientId);

	/**
	 * @return all system(login) user IDs
	 */
	Set<UserId> retrieveSystemUserIds();

	boolean isSystemUser(UserId userId);

	BPartnerId getBPartnerIdByUserId(final UserId userId);

	Set<UserId> getUserIdsByBPartnerId(BPartnerId bpartnerId);

	UserId retrieveUserIdByLogin(String login);
}
