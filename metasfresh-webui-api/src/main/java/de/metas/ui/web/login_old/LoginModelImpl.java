package de.metas.ui.web.login_old;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Language;
import org.compiere.model.MSession;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.KeyNamePairList;
import org.compiere.util.Language;
import org.compiere.util.Login;
import org.compiere.util.LoginContext;
import org.compiere.util.ValueNamePair;
import org.compiere.util.ValueNamePairList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.ImmutableList;

import de.metas.hostkey.api.IHostKeyBL;
import de.metas.logging.MetasfreshLastError;
import de.metas.ui.web.base.session.UserPreference;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window_old.shared.login.LoginAuthRequest;
import de.metas.ui.web.window_old.shared.login.LoginAuthResponse;
import de.metas.ui.web.window_old.shared.login.LoginCompleteRequest;

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

public class LoginModelImpl implements LoginModel
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ILanguageBL languageBL = Services.get(ILanguageBL.class);
	private final transient IHostKeyBL hostKeyBL = Services.get(IHostKeyBL.class);

	private final transient Login _loginService;
	
	@Autowired
	private UserSession userSession;
	private final ValueNamePairList _availableLanguages;
	private ValueNamePair _language;
	
	public LoginModelImpl()
	{
		super();
		Env.autowireBean(this);

		final Properties ctx = userSession.getCtx();
		_loginService = new Login(ctx);

		//
		// Load languages
		final List<I_AD_Language> adLanguages = languageBL.getAvailableLanguages(ctx);
		final List<ValueNamePair> availableLanguages = new ArrayList<>(adLanguages.size());
		final Language baseLanguage = Language.getBaseLanguage();
		ValueNamePair defaultLanguageVNP = null;
		for (final I_AD_Language adLanguage : adLanguages)
		{
			final String adLanguageCode = adLanguage.getAD_Language();
			final Language language = Language.getLanguage(adLanguageCode);
			if (language == null)
			{
				continue;
			}

			final ValueNamePair languageVNP = ValueNamePair.of(adLanguageCode, adLanguage.getName());
			availableLanguages.add(languageVNP);

			if (adLanguage.equals(baseLanguage))
			{
				defaultLanguageVNP = languageVNP;
			}
		}

		if (defaultLanguageVNP == null && !availableLanguages.isEmpty())
		{
			defaultLanguageVNP = availableLanguages.get(0);
		}

		this._availableLanguages = ValueNamePairList.of(availableLanguages);
		setLanguage(defaultLanguageVNP);
	}

	private final UserSession getUserSession()
	{
		return userSession;
	}
	
	private final Login getLoginService()
	{
		return _loginService;
	}

	private final UserPreference getUserPreference()
	{
		return getUserSession().getUserPreference();
	}

	@Override
	public LoginAuthResponse authenticate(final LoginAuthRequest request)
	{
		// Reset:
		final UserSession userSession = getUserSession();
		userSession.setLoggedIn(false);

		final Login loginService = getLoginService();
		final MSession session = createMSession(loginService); // metas
		final Set<KeyNamePair> rolesKNPairs = loginService.authenticate(request.getUsername(), request.getPassword());
		if (rolesKNPairs.isEmpty())
		{
			closeSessionWithError(session); // metas
			return LoginAuthResponse.NULL; // note: we will not reach this point because the method throws exception
		}

		// Update state
		final List<KeyNamePair> availableRoles = ImmutableList.copyOf(rolesKNPairs);

		//
		// Load preferences and export them to context
		final LoginContext ctx = loginService.getCtx();
		final UserPreference userPreference = getUserPreference();
		userPreference.loadPreference(ctx.getSessionContext());
		userPreference.updateContext(ctx.getSessionContext());

		return LoginAuthResponse.of(availableRoles);
	}

	@Override
	public void loginComplete(final LoginCompleteRequest request)
	{
		final KeyNamePair role = request.getRole();
		final KeyNamePair client = request.getClient();
		final KeyNamePair org = request.getOrg();
		final KeyNamePair warehouse = request.getWarehouse();

		//
		// Update context
		final Login loginService = getLoginService();

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
		final UserSession userSession = getUserSession();
		final UserPreference userPreference = userSession.getUserPreference();
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

	@Override
	public KeyNamePairList getAD_Clients(final int adRoleId)
	{
		if (adRoleId < 0)
		{
			return KeyNamePairList.of();
		}

		final KeyNamePair role = KeyNamePair.of(adRoleId, "AD_Role_ID=" + adRoleId);
		final Set<KeyNamePair> adClients = getLoginService().setRoleAndGetClients(role);
		return KeyNamePairList.of(adClients);
	}

	@Override
	public KeyNamePairList getAD_Orgs(final int adClientId)
	{
		if (adClientId < 0)
		{
			return KeyNamePairList.of();
		}
		final KeyNamePair client = KeyNamePair.of(adClientId, "AD_Client_ID=" + adClientId);
		final Set<KeyNamePair> adOrgs = getLoginService().setClientAndGetOrgs(client);
		return KeyNamePairList.of(adOrgs);
	}

	@Override
	public KeyNamePairList getM_Warehouses(final int adOrgId)
	{
		if (adOrgId < 0)
		{
			return KeyNamePairList.of();
		}
		final KeyNamePair org = KeyNamePair.of(adOrgId, "AD_Org_ID=" + adOrgId);
		final Set<KeyNamePair> warehouses = getLoginService().getWarehouses(org);
		return KeyNamePairList.of(warehouses);
	}

	@Override
	public ValueNamePair setLanguage(final ValueNamePair languageVNP)
	{
		Language language = null;
		if (languageVNP != null)
		{
			final String adLanguage = languageVNP.getValue();
			language = Language.getLanguage(adLanguage);
		}
		if (language == null)
		{
			language = Language.getBaseLanguage();
		}

		final Login loginService = getLoginService();
		final LoginContext ctx = loginService.getCtx();
		Env.verifyLanguage(language);
		ctx.setAD_Language(language.getAD_Language());

		 getUserSession().setAD_Language(language.getAD_Language());

		final ValueNamePair languageVNP_Actual = ValueNamePair.of(language.getAD_Language(), language.getName());
		this._language = languageVNP_Actual;
		return languageVNP_Actual;
	}

	@Override
	public ValueNamePairList getAvailableLanguages()
	{
		return _availableLanguages;
	}

	@Override
	public ValueNamePair getLanguage()
	{
		return _language;
	}

	private MSession createMSession(final Login login)
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

		final Login loginService = getLoginService();
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
		login.setRemoteAddr(remoteAddr);
		login.setRemoteHost(remoteHost);
		login.setWebSession(webSessionId);

		return sessionPO;
	}

	private void closeSessionWithError(final MSession session)
	{
		final Login loginService = getLoginService();

		String errmsg = null;
		final ValueNamePair vnp = MetasfreshLastError.retrieveError();
		if (vnp != null)
		{
			errmsg = msgBL.translate(Env.getCtx(), vnp.getValue());
		}
		if (errmsg == null)
		{
			errmsg = msgBL.translate(Env.getCtx(), "UserOrPasswordInvalid");
		}
		//
		session.logout();
		loginService.getCtx().resetAD_Session_ID();
		
		throw new AdempiereException(errmsg);
	}

	@Override
	public boolean isShowWarehouseOnLogin()
	{
		return getLoginService().isShowWarehouseOnLogin();
	}
}
