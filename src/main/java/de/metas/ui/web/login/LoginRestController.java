package de.metas.ui.web.login;

import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;
import org.compiere.util.LoginContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ILanguageBL;
import de.metas.ui.web.base.session.UserPreference;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.login.exceptions.NotAuthenticatedException;
import de.metas.ui.web.login.json.JSONLoginAuthRequest;
import de.metas.ui.web.login.json.JSONLoginAuthResponse;
import de.metas.ui.web.login.json.JSONLoginRole;
import de.metas.ui.web.login.json.JSONResetPassword;
import de.metas.ui.web.login.json.JSONResetPasswordCompleteRequest;
import de.metas.ui.web.login.json.JSONResetPasswordRequest;
import de.metas.ui.web.notification.UserNotificationsService;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.session.UserSessionRepository;
import de.metas.ui.web.upload.WebuiImageId;
import de.metas.ui.web.upload.WebuiImageService;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

	@Autowired
	private UserSessionRepository userSessionRepo;

	@Autowired
	private UserNotificationsService userNotificationsService;

	@Autowired
	private WebuiImageService imageService;

	private Login getLoginService()
	{
		final LoginContext loginCtx = new LoginContext(Env.getCtx());
		loginCtx.setWebui(true);
		return new Login(loginCtx);
	}

	private void assertAuthenticated()
	{
		getLoginService()
				.getCtx()
				.getAD_User_ID_IfExists()
				.orElseThrow(() -> new NotAuthenticatedException());
	}

	@PostMapping("/authenticate")
	public JSONLoginAuthResponse authenticate(@RequestBody final JSONLoginAuthRequest request)
	{
		return authenticate(request.getUsername(), request.getPasswordAsEncryptableString());
	}

	private JSONLoginAuthResponse authenticate(final String username, final HashableString password)
	{
		userSession.assertNotLoggedIn();

		final Login loginService = getLoginService();
		startMFSession(loginService);

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
			destroyMFSession(loginService);
			throw AdempiereException.wrapIfNeeded(ex);
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

	private static MFSession startMFSession(final Login loginService)
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
		final MFSession session = Services.get(ISessionBL.class).getCurrentOrCreateNewSession(ctx.getSessionContext());
		session.setRemote_Addr(remoteAddr, remoteHost);
		session.setWebSessionId(webSessionId);

		// Update Login helper
		loginService.setRemoteAddr(remoteAddr);
		loginService.setRemoteHost(remoteHost);
		loginService.setWebSession(webSessionId);

		return session;
	}

	private static void destroyMFSession(final Login loginService)
	{
		Services.get(ISessionBL.class).logoutCurrentSession();

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

	@PostMapping("/loginComplete")
	public void loginComplete(@RequestBody final JSONLoginRole loginRole)
	{
		assertAuthenticated();
		userSession.assertNotLoggedIn();

		final KeyNamePair role = KeyNamePair.of(loginRole.getRoleId());
		final KeyNamePair tenant = KeyNamePair.of(loginRole.getTenantId());
		final KeyNamePair org = KeyNamePair.of(loginRole.getOrgId());

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
			final String msg = loginService.loadPreferences(org, date);
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
		userSessionRepo.load(userSession);

		//
		// Mark session as logged in
		userSession.setLoggedIn(true);

		//
		// Enable user notifications
		userNotificationsService.enableForSession(userSession.getSessionId(), userSession.getAD_User_ID(), userSession.getAD_Language());
	}

	@GetMapping("/isLoggedIn")
	public boolean isLoggedIn()
	{
		return userSession.isLoggedIn();
	}

	@GetMapping("/availableLanguages")
	public JSONLookupValuesList getAvailableLanguages()
	{
		return Services.get(ILanguageBL.class).getAvailableLanguages()
				.toValueNamePairs()
				.stream()
				.map(JSONLookupValue::ofNamePair)
				.collect(JSONLookupValuesList.collect())
				.setDefaultValue(userSession.getAD_Language());
	}

	@GetMapping("/logout")
	public void logout(final HttpServletRequest request)
	{
		userSession.assertLoggedIn();

		final Login loginService = getLoginService();
		destroyMFSession(loginService);
	}

	@PostMapping("/resetPassword")
	public void resetPasswordRequest(@RequestBody final JSONResetPasswordRequest request)
	{
		userSession.assertNotLoggedIn();

		final IUserBL usersService = Services.get(IUserBL.class);
		usersService.createResetPasswordByEMailRequest(request.getEmail());
	}

	@GetMapping("/resetPassword/{token}")
	@Deprecated
	public JSONResetPassword getResetPasswordInfo(@PathVariable("token") final String token)
	{
		return resetPasswordInitByToken(token);
	}

	@PostMapping("/resetPassword/{token}/init")
	public JSONResetPassword resetPasswordInitByToken(@PathVariable("token") final String token)
	{
		userSession.assertNotLoggedIn();

		final IUserDAO usersRepo = Services.get(IUserDAO.class);
		final I_AD_User user = usersRepo.getByPasswordResetCode(token);

		final String userADLanguage = user.getAD_Language();
		if (!Check.isEmpty(userADLanguage, true))
		{
			userSession.setAD_Language(userADLanguage);
		}

		return JSONResetPassword.builder()
				.fullname(user.getName())
				.email(user.getEMail())
				.token(token)
				.build();
	}

	@GetMapping("/resetPassword/{token}/avatar")
	public ResponseEntity<byte[]> getUserAvatar(
			@PathVariable("token") final String token,
			@RequestParam(name = "maxWidth", required = false, defaultValue = "-1") final int maxWidth,
			@RequestParam(name = "maxHeight", required = false, defaultValue = "-1") final int maxHeight)
	{
		final IUserDAO usersRepo = Services.get(IUserDAO.class);
		final I_AD_User user = usersRepo.getByPasswordResetCode(token);

		final WebuiImageId avatarId = WebuiImageId.ofRepoIdOrNull(user.getAvatar_ID());
		if (avatarId == null)
		{
			return ResponseEntity.notFound().build();
		}

		return imageService.getWebuiImage(avatarId, maxWidth, maxHeight)
				.toResponseEntity();
	}

	@PostMapping("/resetPassword/{token}")
	public JSONLoginAuthResponse resetPasswordComplete(
			@PathVariable("token") final String token,
			@RequestBody final JSONResetPasswordCompleteRequest request)
	{
		userSession.assertNotLoggedIn();

		if (!Objects.equals(token, request.getToken()))
		{
			throw new AdempiereException("@Invalid@ @PasswordResetCode@");
		}

		final IUserBL usersService = Services.get(IUserBL.class);
		final I_AD_User user = usersService.resetPassword(token, request.getPassword());

		final String username = user.getEMail();
		final HashableString password = usersService.getUserPassword(user);
		return authenticate(username, password);
	}
}
