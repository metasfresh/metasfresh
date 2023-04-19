package de.metas.util.web.security;

import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.Language;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.UserNotAuthorizedException;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.function.Supplier;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class UserAuthTokenService
{
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final UserAuthTokenRepository userAuthTokenRepo;

	private final CCache<UserId, UserInfo> userInfoById = CCache.<UserId, UserInfo>builder()
			.tableName(I_AD_User.Table_Name)
			.initialCapacity(50)
			.expireMinutes(30)
			.build();

	public UserAuthTokenService(
			@NonNull final UserAuthTokenRepository userAuthTokenRepo)
	{
		this.userAuthTokenRepo = userAuthTokenRepo;
	}

	public UserAuthToken getByToken(@NonNull final String token)
	{
		return userAuthTokenRepo.getByToken(token);
	}

	public void run(final Supplier<String> authTokenStringSupplier, @NonNull final UserAuthTokenRunnable runnable)
	{
		call(authTokenStringSupplier, () -> {
			runnable.run();
			return null;
		});
	}

	public <R> R call(final Supplier<String> authTokenStringSupplier, @NonNull final UserAuthTokenCallable<R> callable)
	{
		final UserAuthToken token;
		String authTokenString = null;
		try
		{
			authTokenString = authTokenStringSupplier.get();
			token = userAuthTokenRepo.getByToken(authTokenString);
		}
		catch (final Exception ex)
		{
			throw new UserNotAuthorizedException(authTokenString, ex);
		}

		final Properties ctx = createContext(token);
		try (final IAutoCloseable ignored = Env.switchContext(ctx))
		{
			return callable.call();
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private Properties createContext(final UserAuthToken token)
	{
		final IUserRolePermissions permissions = userRolePermissionsDAO.getUserRolePermissions(UserRolePermissionsKey.builder()
				.userId(token.getUserId())
				.roleId(token.getRoleId())
				.clientId(token.getClientId())
				.date(SystemTime.asDayTimestamp())
				.build());

		final UserInfo userInfo = getUserInfo(token.getUserId());

		final Properties ctx = Env.newTemporaryCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, permissions.getClientId().getRepoId());
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, OrgId.toRepoId(token.getOrgId()));
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, UserId.toRepoId(permissions.getUserId()));
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, RoleId.toRepoId(permissions.getRoleId()));
		Env.setContext(ctx, Env.CTXNAME_AD_Language, userInfo.getAdLanguage());
		return ctx;
	}

	public UserAuthToken getOrCreateNewToken(@NonNull final CreateUserAuthTokenRequest request)
	{
		return userAuthTokenRepo.getOrCreateNew(request);
	}

	private UserInfo getUserInfo(@NonNull final UserId userId)
	{
		return userInfoById.getOrLoad(userId, this::retrieveUserInfo);
	}

	private UserInfo retrieveUserInfo(@NonNull final UserId userId)
	{
		final I_AD_User user = userDAO.getById(userId);

		return UserInfo.builder()
				.userId(userId)
				.adLanguage(StringUtils.trimBlankToOptional(user.getAD_Language()).orElseGet(Language::getBaseAD_Language))
				.build();
	}

	//
	//
	//

	@Value
	@Builder
	private static class UserInfo
	{
		@NonNull UserId userId;
		@NonNull String adLanguage;
	}
}
