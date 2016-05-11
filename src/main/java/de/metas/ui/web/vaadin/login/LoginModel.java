package de.metas.ui.web.vaadin.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.ALoginRes;
import org.compiere.model.I_AD_Language;
import org.compiere.model.MSession;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Login;
import org.compiere.util.ValueNamePair;

import com.google.common.collect.ImmutableList;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WebBrowser;
import com.vaadin.server.WrappedHttpSession;

import de.metas.hostkey.api.IHostKeyBL;
import de.metas.logging.MetasfreshLastError;
import de.metas.ui.web.base.session.UserPreference;
import de.metas.ui.web.vaadin.session.UserSession;

/*
 * #%L
 * test_vaadin
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

public class LoginModel
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ILanguageBL languageBL = Services.get(ILanguageBL.class);
	private final transient IHostKeyBL hostKeyBL = Services.get(IHostKeyBL.class);
	
	private final Properties ctx = Env.getCtx();
	private final transient Login loginService = new Login(ctx);

	private List<KeyNamePair> availableRoles = ImmutableList.of();
	private final List<Language> availableLanguages;

	private Language language;
	private static final String RESOURCE = ALoginRes.class.getName();
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE);

	public LoginModel()
	{
		super();

		//
		// Load languages
		final List<I_AD_Language> adLanguages = languageBL.getAvailableLanguages(ctx);
		final List<Language> availableLanguages = new ArrayList<>(adLanguages.size());
		for (final I_AD_Language adLanguage : adLanguages)
		{
			final Language language = Language.getLanguage(adLanguage.getAD_Language());
			if (language == null)
			{
				continue;
			}
			availableLanguages.add(language);
		}
		this.availableLanguages = ImmutableList.copyOf(availableLanguages);
		setLanguage(Language.getBaseLanguage());
	}
	
	private final UserSession getUserSession()
	{
		return UserSession.getCurrent();
	}

	private final UserPreference getUserPreference()
	{
		return getUserSession().getUserPreference();
	}

	public void authenticate(final String username, final String password)
	{
		// Reset:
		final UserSession userSession = getUserSession();
		userSession.setLoggedIn(false);
		availableRoles = ImmutableList.of();

		final MSession session = createMSession(loginService); // metas
		final KeyNamePair[] rolesKNPairs = loginService.getRoles(username, password);
		if (rolesKNPairs == null || rolesKNPairs.length == 0)
		{
			closeSessionWithError(session); // metas
			return; // note: we will not reach this point because the method throws exception
		}

		// Update state
		availableRoles = ImmutableList.copyOf(rolesKNPairs);

		//
		// Load preferences and export them to context
		final UserPreference userPreference = getUserPreference();
		userPreference.loadPreference(ctx);
		userPreference.updateContext(ctx);
	}

	public void loginComplete(final KeyNamePair role, final KeyNamePair client, final KeyNamePair org, final KeyNamePair warehouse)
	{
		//
		// Update context
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, role.getKey());
		Env.setContext(ctx, Env.CTXNAME_AD_Role_Name, role.getName());
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, client.getKey());
		final int orgId = org != null ? org.getKey() : Env.CTXVALUE_AD_Org_ID_System;
		final String orgName = org != null ? org.getName() : null;
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, orgId);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_Name, orgName);
		final int warehouseId = warehouse == null ? 0 : warehouse.getKey();
		Env.setContext(ctx, Env.CTXNAME_M_Warehouse_ID, warehouseId);


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
			final String printerName = null;
			final String msg = loginService.loadPreferences(org, warehouse, date, printerName);
			if (!Check.isEmpty(msg, true))
			{
				throw new AdempiereException(msg);
			}
		}

		//
		// Save user preferences
		final UserSession userSession = getUserSession();
		final UserPreference userPreference = userSession.getUserPreference();
		//userPreference.setProperty(UserPreference.P_LANGUAGE, Env.getContext(Env.getCtx(), UserPreference.LANGUAGE_NAME));
		userPreference.setProperty(UserPreference.P_ROLE, role.getKey());
		userPreference.setProperty(UserPreference.P_CLIENT, client.getKey());
		userPreference.setProperty(UserPreference.P_ORG, orgId);
		userPreference.setProperty(UserPreference.P_WAREHOUSE, warehouseId);
		userPreference.savePreference();

		//
		// Mark session as logged in
		userSession.setLoggedIn(true);
	}

	public List<KeyNamePair> getRoles()
	{
		return availableRoles;
	}

	private static final <T> ImmutableList<T> asListOrEmpty(final T[] arr)
	{
		if (arr == null || arr.length == 0)
		{
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(arr);
	}

	public List<KeyNamePair> getAD_Clients(final KeyNamePair role)
	{
		if (role == null)
		{
			return ImmutableList.of();
		}
		final KeyNamePair[] adClients = loginService.getClients(role);
		return asListOrEmpty(adClients);
	}

	public List<KeyNamePair> getAD_Orgs(final KeyNamePair client)
	{
		if (client == null)
		{
			return ImmutableList.of();
		}
		final KeyNamePair[] adOrgs = loginService.getOrgs(client);
		return asListOrEmpty(adOrgs);
	}

	public List<KeyNamePair> getM_Warehouses(final KeyNamePair org)
	{
		if (org == null)
		{
			return ImmutableList.of();
		}
		final KeyNamePair[] warehouses = loginService.getWarehouses(org);
		return asListOrEmpty(warehouses);
	}

	public void setLanguage(Language language)
	{
		if (language == null)
		{
			language = Language.getBaseLanguage();
		}

		Env.verifyLanguage(ctx, language);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, language.getAD_Language());

		final Locale locale = language.getLocale();
		Locale.setDefault(locale);
		resourceBundle = ResourceBundle.getBundle(RESOURCE, locale);

		this.language = language;
	}

	public List<Language> getAvailableLanguages()
	{
		return availableLanguages;
	}

	public Language getLanguage()
	{
		return language;
	}

	public ResourceBundle getResourceBundle()
	{
		return resourceBundle;
	}

	private final HttpSession getHttpSession()
	{
		final WrappedHttpSession wrappedSession = (WrappedHttpSession)VaadinSession.getCurrent().getSession();
		return wrappedSession.getHttpSession();
	}

	private MSession createMSession(final Login login)
	{
		final HttpSession httpSess = getHttpSession();
		final String webSessionId = httpSess.getId();

		final WebBrowser webBrowser = Page.getCurrent().getWebBrowser();
		String remoteAddr = webBrowser.getAddress();
		String remoteHost = remoteAddr;

		//
		// Check if we are behind proxy and if yes, get the actual client IP address
		// NOTE: when configuring apache, don't forget to activate reverse-proxy mode
		// see http://www.xinotes.org/notes/note/770/
		// final String forwardedFor = Executions.getCurrent().getHeader("X-Forwarded-For"); // FIXME
		final String forwardedFor = null;
		if (!Check.isEmpty(forwardedFor))
		{
			remoteAddr = forwardedFor;
			remoteHost = forwardedFor;
		}

		final MSession sessionPO = MSession.get(ctx, remoteAddr, remoteHost, webSessionId);

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
		login.setRemoteAddr(remoteAddr);
		login.setRemoteHost(remoteHost);
		login.setWebSession(webSessionId);

		return sessionPO;
	}

	private void closeSessionWithError(final MSession session)
	{
		String errmsg = null;
		final ValueNamePair vnp = MetasfreshLastError.retrieveError();
		if (vnp != null)
		{
			errmsg = msgBL.translate(ctx, vnp.getValue());
		}
		if (errmsg == null)
		{
			errmsg = msgBL.translate(ctx, "UserOrPasswordInvalid");
		}
		//
		session.logout();
		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, "");
		throw new AdempiereException(errmsg);

	}

	public boolean isShowWarehouseOnLogin()
	{
		return loginService.isShowWarehouseOnLogin();
	}
}
