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

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_User;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface IUserDAO extends ISingletonService
{
	/**
	 * Retrieves a user whose <code>Login</code> or <code>EMail</code> column equals the given <code>userId</code>.
	 *
	 * @return user; never return null
	 */
	org.compiere.model.I_AD_User retrieveLoginUserByUserId(String userId);

	org.compiere.model.I_AD_User getByPasswordResetCode(String passwordResetCode);

	@Nullable
	org.compiere.model.I_AD_User retrieveUserOrNull(Properties ctx, int adUserId);

	/**
	 * @deprecated please use {@link #getById(UserId)} instead
	 */
	@Deprecated
	default org.compiere.model.I_AD_User getById(final int adUserRepoId)
	{
		return getById(UserId.ofRepoId(adUserRepoId));
	}

	org.compiere.model.I_AD_User getById(UserId adUserId);

	<T extends org.compiere.model.I_AD_User> T getByIdInTrx(UserId userId, Class<T> modelClass);

	default org.compiere.model.I_AD_User getByIdInTrx(final UserId userId)
	{
		return getByIdInTrx(userId, org.compiere.model.I_AD_User.class);
	}

	org.compiere.model.I_AD_User getByIdInTrx(int adUserId);

	/**
	 * @return user's full name or <code>?</code> if no found
	 */
	String retrieveUserFullName(int userRepoId);

	String retrieveUserFullName(UserId userId);

	@Nullable
	UserId retrieveUserIdByEMail(String email, ClientId adClientId);

	/**
	 * @return all system(login) user IDs
	 */
	Set<UserId> retrieveSystemUserIds();

	boolean isSystemUser(UserId userId);

	@Nullable
	BPartnerId getBPartnerIdByUserId(final UserId userId);

	@Nullable
	UserId retrieveUserIdByLogin(String login);

	void save(I_AD_User user);

	Optional<I_AD_User> getCounterpartUser(
			@NonNull UserId sourceUserId,
			@NonNull OrgId targetOrgId);
}
