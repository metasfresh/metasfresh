package de.metas.ui.web.login;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ILanguageBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.UserDashboardSessionContextHolder;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.login.exceptions.NotLoggedInException;
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
import org.adempiere.service.IClientDAO;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
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
import java.util.List;
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
	private final IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
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
				.orElseThrow(NotLoggedInException::new);
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
			final List<Role> availableRolesList = loginService.authenticate(username, password).getAvailableRoles();
			final List<JSONLoginRole> jsonAvailableRoles;
			final JSONLoginRole roleToLogin;
			if (role != null)
			{
				roleToLogin = role;
				jsonAvailableRoles = ImmutableList.of(role);
			}
			else
			{
				jsonAvailableRoles = createJSONLoginRoles(loginService, availableRolesList);
				roleToLogin = jsonAvailableRoles.size() == 1 ? jsonAvailableRoles.iterator().next() : null;
			}

			if (roleToLogin != null)
			{
				loginComplete(roleToLogin);
				return JSONLoginAuthResponse.loginComplete(roleToLogin);
			}
			else
			{
				return JSONLoginAuthResponse.of(jsonAvailableRoles);
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

	private List<JSONLoginRole> createJSONLoginRoles(final Login loginService, final List<Role> availableRoles)
	{
		if (availableRoles.isEmpty())
		{
			return ImmutableList.of();
		}

		final LoginContext ctx = loginService.getCtx();
		final UserId userId = ctx.getUserId();

		final Joiner captionJoiner = Joiner.on(", ");

		final ImmutableList.Builder<JSONLoginRole> result = ImmutableList.builder();
		for (final Role role : availableRoles)
		{
			final RoleId roleId = role.getId();
			for (final ClientId clientId : loginService.getAvailableClients(roleId, userId))
			{
				final String clientName = clientDAO.getClientNameById(clientId);
				final Set<OrgId> availableOrgsIds = loginService.getAvailableOrgs(roleId, userId, clientId);
				for (final OrgId orgId : availableOrgsIds)
				{
					// If there is more than one available Org, then skip the "*" org
					if (availableOrgsIds.size() > 1 && orgId.isAny())
					{
						continue;
					}

					final String orgName = orgDAO.getOrgName(orgId);
					final String caption = captionJoiner.join(role.getName(), clientName, orgName);

					result.add(JSONLoginRole.builder()
							.caption(caption)
							.roleId(roleId.getRepoId())
							.tenantId(clientId.getRepoId())
							.orgId(orgId.getRepoId())
							.build());
				}
			}
		}

		return result.build();
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

		final RoleId roleId = RoleId.ofRepoId(loginRole.getRoleId());
		final ClientId clientId = ClientId.ofRepoId(loginRole.getTenantId());
		final OrgId orgId = OrgId.ofRepoId(loginRole.getOrgId());

		//
		// Update context
		final Login loginService = getLoginService();

		// TODO: optimize
		loginService.setRoleAndGetClients(roleId);
		loginService.setClientAndGetOrgs(clientId);

		//
		// Load preferences and export them to context
		final LoginContext ctx = loginService.getCtx();
		final UserPreference userPreference = userSession.getUserPreference();
		userPreference.loadPreference(ctx.getSessionContext());
		userPreference.updateContext(ctx.getSessionContext());

		//
		// Validate login: fires login complete model interceptors
		{
			final String msg = loginService.validateLogin(orgId);
			if (msg != null && !Check.isBlank(msg))
			{
				throw new AdempiereException(msg);
			}
		}

		//
		// Load preferences
		{
			final String msg = loginService.loadPreferences(orgId, null);
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
				.setDefaultId(userSession.getAD_Language());
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
			return imageService.getEmptyImage();
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
