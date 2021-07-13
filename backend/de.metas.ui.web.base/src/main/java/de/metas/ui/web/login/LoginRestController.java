package de.metas.ui.web.login;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ILanguageBL;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.UserDashboardSessionContextHolder;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.login.exceptions.NotAuthenticatedException;
import de.metas.ui.web.login.json.JSONLoginAuthRequest;
import de.metas.ui.web.login.json.JSONLoginAuthResponse;
import de.metas.ui.web.login.json.JSONLoginRole;
import de.metas.ui.web.login.json.JSONResetPassword;
import de.metas.ui.web.login.json.JSONResetPasswordCompleteRequest;
import de.metas.ui.web.login.json.JSONResetPasswordRequest;
import de.metas.ui.web.notification.UserNotificationsService;
import de.metas.ui.web.session.UserPreference;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.session.UserSessionRepository;
import de.metas.ui.web.upload.WebuiImageId;
import de.metas.ui.web.upload.WebuiImageService;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;
import de.metas.util.web.security.UserAuthTokenService;
import lombok.NonNull;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;
import org.compiere.util.LoginContext;
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

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Set;

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
@RequestMapping(LoginRestController.ENDPOINT)
public class LoginRestController
{
	static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/login";

	private final IUserBL usersService = Services.get(IUserBL.class);
	private final ISessionBL sessionBL = Services.get(ISessionBL.class);
	private final ILanguageBL languageBL = Services.get(ILanguageBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final UserSession userSession;
	private final UserSessionRepository userSessionRepo;
	private final UserNotificationsService userNotificationsService;
	private final WebuiImageService imageService;
	private final UserAuthTokenService userAuthTokenService;
	private final UserDashboardSessionContextHolder userDashboardContextHolder;

	private final static AdMessageKey MSG_UserLoginInternalError = AdMessageKey.of("UserLoginInternalError");

	public LoginRestController(
			@NonNull final UserSession userSession,
			@NonNull final UserSessionRepository userSessionRepo,
			@NonNull final UserNotificationsService userNotificationsService,
			@NonNull final WebuiImageService imageService,
			@NonNull final UserAuthTokenService userAuthTokenService,
			@NonNull final UserDashboardSessionContextHolder userDashboardContextHolder)
	{
		this.userSession = userSession;
		this.userSessionRepo = userSessionRepo;
		this.userNotificationsService = userNotificationsService;
		this.imageService = imageService;
		this.userAuthTokenService = userAuthTokenService;
		this.userDashboardContextHolder = userDashboardContextHolder;
	}

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
				.getUserIdIfExists()
				.orElseThrow(NotAuthenticatedException::new);
	}

	@PostMapping("/authenticate")
	public JSONLoginAuthResponse authenticate(@RequestBody final JSONLoginAuthRequest request)
	{
		final JSONLoginAuthRequest.Type authType = request.getType();
		if (JSONLoginAuthRequest.Type.password.equals(authType))
		{
			if (Check.isEmpty(request.getUsername(), true))
			{
				throw new FillMandatoryException("Username");
			}
			if (Check.isEmpty(request.getPassword(), true))
			{
				throw new FillMandatoryException("Password");
			}

			return authenticate(request.getUsername(), request.getPasswordAsEncryptableString(), null);
		}
		else if (JSONLoginAuthRequest.Type.token.equals(authType))
		{
			final UserAuthToken tokenInfo = userAuthTokenService.getByToken(request.getToken());
			final I_AD_User user = usersService.getById(tokenInfo.getUserId());
			final String username = usersService.extractUserLogin(user);
			final HashableString password = usersService.extractUserPassword(user);
			final JSONLoginRole roleToLogin = JSONLoginRole.builder()
					.caption("role")
					.roleId(tokenInfo.getRoleId().getRepoId())
					.tenantId(tokenInfo.getClientId().getRepoId())
					.orgId(tokenInfo.getOrgId().getRepoId())
					.build();
			return authenticate(username, password, roleToLogin);
		}
		else
		{
			throw new AdempiereException("Unknown authentication type: " + authType);
		}
	}

	private JSONLoginAuthResponse authenticate(
			@NonNull final String username,
			@Nullable final HashableString password,
			@Nullable final JSONLoginRole role)
	{
		userSession.assertNotLoggedIn();

		final Login loginService = getLoginService();
		startMFSession(loginService);

		try
		{
			final Set<KeyNamePair> availableRoleKNPs = loginService.authenticate(username, password);
			final Set<JSONLoginRole> availableRoles;
			final JSONLoginRole roleToLogin;
			if (role != null)
			{
				roleToLogin = role;
				availableRoles = ImmutableSet.of(role);
			}
			else
			{
				availableRoles = createJSONLoginRoles(loginService, availableRoleKNPs);
				roleToLogin = availableRoles.size() == 1 ? availableRoles.iterator().next() : null;
			}

			if (roleToLogin != null)
			{
				loginComplete(roleToLogin);
				return JSONLoginAuthResponse.loginComplete(roleToLogin);
			}
			else
			{
				return JSONLoginAuthResponse.of(availableRoles);
			}
		}
		catch (final Exception ex)
		{
			userSession.setLoggedIn(false);
			destroyMFSession(loginService);
			throw convertToUserFriendlyException(ex);
		}
	}

	private AdempiereException convertToUserFriendlyException(final Exception ex)
	{
		AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);

		if (!metasfreshException.isUserValidationError())
		{
			final AdIssueId adIssueId = errorManager.createIssue(ex);
			metasfreshException = new AdempiereException(MSG_UserLoginInternalError, String.valueOf(adIssueId.getRepoId()));
			metasfreshException.initCause(ex);
			metasfreshException.markAsUserValidationError();
		}
		return metasfreshException;
	}

	private static Set<JSONLoginRole> createJSONLoginRoles(final Login loginService, final Set<KeyNamePair> availableRoles)
	{
		if (availableRoles.isEmpty())
		{
			return ImmutableSet.of();
		}

		final LoginContext ctx = loginService.getCtx();
		final ImmutableSet.Builder<JSONLoginRole> jsonRoles = ImmutableSet.builder();
		for (final KeyNamePair role : availableRoles)
		{
			final RoleId roleId = RoleId.ofRepoId(role.getKey());
			final UserId userId = ctx.getUserId();
			for (final KeyNamePair tenant : loginService.getAvailableClients(roleId, userId))
			{
				final ClientId clientId = ClientId.ofRepoId(tenant.getKey());

				final Set<KeyNamePair> availableOrgs = loginService.getAvailableOrgs(roleId, userId, clientId);
				for (final KeyNamePair org : availableOrgs)
				{
					// If there is more than one available Org, then skip the "*" org
					final OrgId orgId = OrgId.ofRepoIdOrAny(org.getKey());
					if (availableOrgs.size() > 1 && orgId.isAny())
					{
						continue;
					}

					final String caption = Joiner.on(", ").join(role.getName(), tenant.getName(), org.getName());
					final JSONLoginRole jsonRole = JSONLoginRole.of(caption, roleId.getRepoId(), clientId.getRepoId(), orgId.getRepoId());
					jsonRoles.add(jsonRole);

				}
			}
		}

		return jsonRoles.build();
	}

	private void startMFSession(final Login loginService)
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
		final MFSession session = sessionBL.getCurrentOrCreateNewSession(ctx.getSessionContext());
		session.setRemote_Addr(remoteAddr, remoteHost);
		session.setWebSessionId(webSessionId);

		// Update Login helper
		loginService.setRemoteAddr(remoteAddr);
		loginService.setRemoteHost(remoteHost);
		loginService.setWebSessionId(webSessionId);

	}

	private void destroyMFSession(final Login loginService)
	{
		userDashboardContextHolder.clearSessionContext(userSession.getSessionId());

		sessionBL.logoutCurrentSession();

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
			if (msg != null && !Check.isBlank(msg))
			{
				throw new AdempiereException(msg);
			}
		}

		//
		// Load preferences
		{
			final String msg = loginService.loadPreferences(org, null);
			if (!Check.isEmpty(msg, true))
			{
				throw new AdempiereException(msg);
			}
		}

		//
		// Save user preferences
		final LoginContext loginCtx = loginService.getCtx();
		// userPreference.setProperty(UserPreference.P_LANGUAGE, Env.getContext(Env.getCtx(), UserPreference.LANGUAGE_NAME));
		userPreference.setProperty(UserPreference.P_ROLE, RoleId.toRepoId(loginCtx.getRoleId()));
		userPreference.setProperty(UserPreference.P_CLIENT, ClientId.toRepoId(loginCtx.getClientId()));
		userPreference.setProperty(UserPreference.P_ORG, OrgId.toRepoId(loginCtx.getOrgId()));
		userPreference.setProperty(UserPreference.P_WAREHOUSE, WarehouseId.toRepoId(loginCtx.getWarehouseId()));
		userPreference.savePreference();

		//
		userSessionRepo.load(userSession);

		//
		// Mark session as logged in
		userSession.setLoggedIn(true);

		//
		// Enable user notifications
		userNotificationsService.enableForSession(
				userSession.getSessionId(),
				userSession.getLoggedUserId(),
				JSONOptions.of(userSession));

		userDashboardContextHolder.putSessionContext(
				userSession.getSessionId(),
				KPIDataContext.ofUserSession(userSession));
	}

	@GetMapping("/isLoggedIn")
	public boolean isLoggedIn()
	{
		return userSession.isLoggedIn();
	}

	@GetMapping("/availableLanguages")
	public JSONLookupValuesList getAvailableLanguages()
	{
		return languageBL.getAvailableLanguages()
				.toValueNamePairs()
				.stream()
				.map(JSONLookupValue::ofNamePair)
				.collect(JSONLookupValuesList.collect())
				.setDefaultValue(userSession.getAD_Language());
	}

	@GetMapping("/logout")
	public void logout()
	{
		userSession.assertLoggedIn();

		final Login loginService = getLoginService();
		destroyMFSession(loginService);
	}

	@PostMapping("/resetPassword")
	public void resetPasswordRequest(@RequestBody final JSONResetPasswordRequest request)
	{
		userSession.assertNotLoggedIn();

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

		final I_AD_User user = usersService.getByPasswordResetCode(token);

		final String userADLanguage = user.getAD_Language();
		if (userADLanguage != null && !Check.isBlank(userADLanguage))
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
		final I_AD_User user = usersService.getByPasswordResetCode(token);

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

		final I_AD_User user = usersService.resetPassword(token, request.getPassword());
		final String username = usersService.extractUserLogin(user);
		final HashableString password = usersService.extractUserPassword(user);
		return authenticate(username, password, null);
	}
}
