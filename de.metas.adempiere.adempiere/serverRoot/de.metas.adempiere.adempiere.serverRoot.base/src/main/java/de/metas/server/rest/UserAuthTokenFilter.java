package de.metas.server.rest;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserAuthToken;
import org.adempiere.ad.security.UserAuthTokenRepository;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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

@WebFilter({ "/api/*" })
@Profile(Profiles.PROFILE_App)
public class UserAuthTokenFilter implements Filter
{
	public static final String HEADER_Authorization = "Authorization";

	@Autowired
	private UserAuthTokenRepository userAuthTokenRepo;

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException
	{
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		final HttpServletResponse httpResponse = (HttpServletResponse)response;

		final UserAuthToken token;
		try
		{
			token = findUserAuthToken(httpRequest);
		}
		catch (final Exception ex)
		{
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getLocalizedMessage());
			return;
		}

		final Properties ctx = createContext(token);
		try (final IAutoCloseable ctxRestorer = Env.switchContext(ctx))
		{
			chain.doFilter(request, response);
		}
	}

	private UserAuthToken findUserAuthToken(final HttpServletRequest httpRequest)
	{
		final String authorizationString = httpRequest.getHeader(HEADER_Authorization);
		if (Check.isEmpty(authorizationString, true))
		{
			throw new AdempiereException("Provide token in `" + HEADER_Authorization + "` HTTP header");
		}
		else
		{
			return findUserAuthToken(authorizationString);
		}
	}

	private final UserAuthToken findUserAuthToken(final String authorizationString)
	{
		final String tokenString;
		if (authorizationString.startsWith("Token "))
		{
			tokenString = authorizationString.substring(5).trim();
		}
		else if (authorizationString.startsWith("Basic "))
		{
			final String userAndTokenString = new String(DatatypeConverter.parseBase64Binary(authorizationString.substring(5).trim()));
			final int index = userAndTokenString.indexOf(':');
			// final String username = userAndTokenString.substring(0, index);
			tokenString = userAndTokenString.substring(index + 1);
		}
		else
		{
			tokenString = authorizationString;
		}

		return userAuthTokenRepo.getByToken(tokenString);
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
