package de.metas.ui.web.login;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MSession;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;
import org.compiere.util.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.base.session.UserPreference;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.login.exceptions.NotAuthenticatedException;
import de.metas.ui.web.login.json.JSONLoginAuthResponse;
import de.metas.ui.web.login.json.JSONLoginRole;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@RestController
@RequestMapping(value = LoginRestController.ENDPOINT)
public class LoginRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/login";

	@Autowired
	private UserSession userSession;

	private Login getLoginService()
	{
		return new Login(userSession.getCtx());
	}

	private void assertAuthenticated()
	{
		getLoginService()
				.getCtx()
				.getAD_User_ID_IfExists()
				.orElseThrow(() -> new NotAuthenticatedException());
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public JSONLoginAuthResponse authenticate(
			@RequestParam("username") final String username //
			, @RequestParam("password") final String password //
	)
	{
		userSession.assertNotLoggedIn();

		final Login loginService = getLoginService();
		final MSession session = createMSession(loginService);

		try
		{
			final Set<KeyNamePair> availableRoles = loginService.authenticate(username, password);

			//
			// Create JSON roles
			final Set<JSONLoginRole> jsonRoles = createJSONLoginRoles(loginService, availableRoles);

			if (jsonRoles.size() == 1)
			{
				final JSONLoginRole loginRole = jsonRoles.iterator().next();
				loginComplete(loginRole);

				return JSONLoginAuthResponse.loginComplete(loginRole);
			}

			return JSONLoginAuthResponse.of(jsonRoles);
		}
		catch (final Exception ex)
		{
			userSession.setLoggedIn(false);
			destroySession(loginService, session);
			throw Throwables.propagate(ex);
		}
	}

	private Set<JSONLoginRole> createJSONLoginRoles(final Login loginService, final Set<KeyNamePair> availableRoles)
	{
		if (availableRoles.isEmpty())
		{
			return ImmutableSet.of();
		}

		final LoginContext ctx = loginService.getCtx();
		final ImmutableSet.Builder<JSONLoginRole> jsonRoles = ImmutableSet.builder();
		for (final KeyNamePair role : availableRoles)
		{
			final int AD_Role_ID = role.getKey();
			final int AD_User_ID = ctx.getAD_User_ID();
			for (final KeyNamePair tenant : loginService.getAvailableClients(AD_Role_ID, AD_User_ID))
			{
				final int AD_Client_ID = tenant.getKey();

				final Set<KeyNamePair> availableOrgs = loginService.getAvailableOrgs(AD_Role_ID, AD_User_ID, AD_Client_ID);
				for (final KeyNamePair org : availableOrgs)
				{
					// If there is more than one available Org, then skip the "*" org
					if (availableOrgs.size() > 1 && org.getKey() == Env.CTXVALUE_AD_Org_ID_Any)
					{
						continue;
					}

					final String caption = Joiner.on(", ").join(role.getName(), tenant.getName(), org.getName());
					final JSONLoginRole jsonRole = JSONLoginRole.of(caption, AD_Role_ID, AD_Client_ID, org.getKey());
					jsonRoles.add(jsonRole);

				}
			}
		}

		return jsonRoles.build();
	}

	private static MSession createMSession(final Login loginService)
	{
		final HttpServletRequest httpRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		final HttpSession httpSess = httpRequest.getSession();
		final String webSessionId = httpSess.getId();
		//
		// final WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
		String remoteAddr = httpRequest.getRemoteAddr();
		String remoteHost = httpRequest.getRemoteHost();

		//
		// Check if we are behind proxy and if yes, get the actual client IP address
		// NOTE: when configuring apache, don't forget to activate reverse-proxy mode
		// see http://www.xinotes.org/notes/note/770/
		final String forwardedFor = httpRequest.getHeader("X-Forwarded-For");
		if (!Check.isEmpty(forwardedFor))
		{
			remoteAddr = forwardedFor;
			remoteHost = forwardedFor;
		}

		final LoginContext ctx = loginService.getCtx();
		final MSession sessionPO = MSession.get(ctx.getSessionContext(), remoteAddr, remoteHost, webSessionId);

		// Set HostKey
		// FIXME: commented out because this one is not working when running over websockets (i.e. HttpServletResponse does not exists)
		// see https://dev.vaadin.com/ticket/11808
		// @formatter:off
//		final I_AD_Session session = InterfaceWrapperHelper.create(sessionPO, I_AD_Session.class);
//		HttpCookieHostKeyStorage.createUpdateHostKey();
//		final String hostKey = hostKeyBL.getHostKey();
//		session.setHostKey(hostKey);
//		InterfaceWrapperHelper.save(session);
		// @formatter:on

		// Update Login helper
		loginService.setRemoteAddr(remoteAddr);
		loginService.setRemoteHost(remoteHost);
		loginService.setWebSession(webSessionId);

		return sessionPO;
	}

	private static void destroySession(final Login loginService, final MSession session)
	{
		if (session != null)
		{
			session.logout();
		}

		if (loginService != null)
		{
			loginService.getCtx().resetAD_Session_ID();
		}

		//
		// Destroy http session
		final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		final HttpSession httpSession = servletRequestAttributes.getRequest().getSession(false);
		if (httpSession != null)
		{
			httpSession.invalidate();
		}
	}

	@RequestMapping(value = "/loginComplete", method = RequestMethod.POST)
	public void loginComplete(@RequestBody final JSONLoginRole loginRole)
	{
		assertAuthenticated();
		userSession.assertNotLoggedIn();

		final KeyNamePair role = KeyNamePair.of(loginRole.getRoleId());
		final KeyNamePair tenant = KeyNamePair.of(loginRole.getTenantId());
		final KeyNamePair org = KeyNamePair.of(loginRole.getOrgId());
		final KeyNamePair warehouse = null;

		//
		// Update context
		final Login loginService = getLoginService();

		// TODO: optimize
		loginService.setRoleAndGetClients(role);
		loginService.setClientAndGetOrgs(tenant);

		//
		// Load preferences and export them to context
		final LoginContext ctx = loginService.getCtx();
		final UserPreference userPreference = userSession.getUserPreference();
		userPreference.loadPreference(ctx.getSessionContext());
		userPreference.updateContext(ctx.getSessionContext());

		//
		// Validate login: fires login complete model interceptors
		{
			final String msg = loginService.validateLogin(org);
			if (!Check.isEmpty(msg, true))
			{
				throw new AdempiereException(msg);
			}
		}

		//
		// Load preferences
		{
			final java.sql.Timestamp date = null;
			final String msg = loginService.loadPreferences(org, warehouse, date);
			if (!Check.isEmpty(msg, true))
			{
				throw new AdempiereException(msg);
			}
		}

		//
		// Save user preferences
		final LoginContext loginCtx = loginService.getCtx();
		// userPreference.setProperty(UserPreference.P_LANGUAGE, Env.getContext(Env.getCtx(), UserPreference.LANGUAGE_NAME));
		userPreference.setProperty(UserPreference.P_ROLE, loginCtx.getAD_Role_ID());
		userPreference.setProperty(UserPreference.P_CLIENT, loginCtx.getAD_Client_ID());
		userPreference.setProperty(UserPreference.P_ORG, loginCtx.getAD_Org_ID());
		userPreference.setProperty(UserPreference.P_WAREHOUSE, loginCtx.getM_Warehouse_ID());
		userPreference.savePreference();

		//
		// Mark session as logged in
		userSession.setLoggedIn(true);
	}

	@RequestMapping(value = "/language", method = RequestMethod.PUT)
	public String setAD_Language(@RequestBody final String adLanguage)
	{
		userSession.setAD_Language(adLanguage);
		final String adLanguageNew = userSession.getAD_Language();

		return adLanguageNew;
	}

	@RequestMapping(value = "/language", method = RequestMethod.GET)
	public String getAD_Language()
	{
		return userSession.getAD_Language();
	}

	@RequestMapping(value = "/availableLanguages", method = RequestMethod.GET)
	public JSONLookupValuesList getAvailableLanguages()
	{
		return Services.get(ILanguageBL.class).getAvailableLanguages(userSession.getCtx())
				.stream()
				.map(adLanguageObj -> JSONLookupValue.of(adLanguageObj.getAD_Language(), adLanguageObj.getName()))
				.collect(JSONLookupValuesList.collect())
				.setDefaultValue(userSession.getAD_Language());
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(final HttpServletRequest request)
	{
		userSession.assertLoggedIn();

		final Login loginService = getLoginService();
		final MSession session = MSession.get(userSession.getCtx(), false);
		destroySession(loginService, session);
	}
}
