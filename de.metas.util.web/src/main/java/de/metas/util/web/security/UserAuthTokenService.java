package de.metas.util.web.security;

import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserAuthToken;
import org.adempiere.ad.security.UserAuthTokenRepository;
import org.adempiere.ad.security.UserNotAuthorizedException;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Supplier;

import lombok.NonNull;

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
	@Autowired
	private UserAuthTokenRepository userAuthTokenRepo;

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
		try (final IAutoCloseable ctxRestorer = Env.switchContext(ctx))
		{
			return callable.call();
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private final Properties createContext(final UserAuthToken token)
	{
		final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		final IUserRolePermissions userRolePermissions = userRolePermissionsDAO.retrieveUserRolePermissions(UserRolePermissionsKey.builder()
				.adUserId(token.getUserId())
				.adRoleId(token.getRoleId())
				.adClientId(token.getClientId())
				.date(SystemTime.asDayTimestamp())
				.build());

		final Properties ctx = Env.newTemporaryCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, userRolePermissions.getAD_Client_ID());
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, token.getOrgId());
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, userRolePermissions.getAD_User_ID());
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, userRolePermissions.getAD_Role_ID());
		// TODO: set other properties like language, warehouse etc...
		return ctx;
	}
}
