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
import de.metas.security.user_2fa.User2FAService;
import de.metas.security.user_2fa.totp.OTP;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.UserDashboardSessionContextHolder;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.login.exceptions.NotLoggedInException;
import de.metas.ui.web.login.json.JSONLoginAuth2FARequest;
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
import lombok.RequiredArgsConstructor;
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
import org.compiere.util.LoginAuthenticateResponse;
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
import java.util.ArrayList;
import java.util.Comparator;
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
@RequiredArgsConstructor
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
	private final User2FAService user2FAService;
	private final static AdMessageKey MSG_UserLoginInternalError = AdMessageKey.of("UserLoginInternalError");

	private static final Comparator<JSONLoginRole> ROLES_ORDERING = Comparator.<JSONLoginRole, Integer>comparing(role -> RoleId.isRegular(role.getRoleId()) ? 0 : 100) // Regular roles first
			.thenComparing(JSONLoginRole::getCaption); // by caption

	private Login getLoginService()
	{
		final LoginContext loginCtx = new LoginContext(Env.getCtx());
		loginCtx.setWebui(true);

		final Login loginService = new Login(loginCtx);
		loginService.setUser2FAService(user2FAService);
		return loginService;
	}

	@PostMapping("/authenticate")
	public JSONLoginAuthResponse authenticate(@RequestBody final JSONLoginAuthRequest request)
	{
		final JSONLoginAuthRequest.Type authType = request.getType();
		if (JSONLoginAuthRequest.Type.password.equals(authType))
		{
			if (Check.isBlank(request.getUsername()))
			{
				throw new FillMandatoryException("Username");
			}
			if (Check.isBlank(request.getPassword()))
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
			@Nullable final JSONLoginRole roleToLogin)
	{
		userSession.assertNotLoggedIn();

		final Login loginService = getLoginService();
		startMFSession(loginService);

		try
		{
			final LoginAuthenticateResponse authResponse = loginService.authenticate(username, password);
			if (authResponse.is2FARequired())
			{
				return JSONLoginAuthResponse.requires2FA();
			}

			final List<Role> availableRolesList = authResponse.getAvailableRoles();
			return continueAuthenticationSelectingRole(loginService, availableRolesList, roleToLogin);
		}
		catch (final Exception ex)
		{
			userSession.setLoggedIn(false);
			destroyMFSession(loginService);
			throw convertToUserFriendlyException(ex);
		}
	}

	@PostMapping("/2fa")
	public JSONLoginAuthResponse authenticate2FA(@RequestBody final JSONLoginAuth2FARequest request)
	{
		userSession.assertNotLoggedIn();

		final OTP otp = OTP.ofString(request.getCode());
		final Login loginService = getLoginService();
		try
		{
			final LoginAuthenticateResponse authResponse = loginService.authenticate2FA(otp);
			return continueAuthenticationSelectingRole(loginService, authResponse.getAvailableRoles(), null);
		}
		catch (final Exception ex)
		{
			throw convertToUserFriendlyException(ex);
		}
	}

	private JSONLoginAuthResponse continueAuthenticationSelectingRole(
			@NonNull final Login loginService,
			@NonNull final List<Role> availableRoles,
			@Nullable final JSONLoginRole roleToLogin)
	{
		final List<JSONLoginRole> jsonAvailableRoles;
		final JSONLoginRole roleToLoginEffective;
		if (roleToLogin != null)
		{
			roleToLoginEffective = roleToLogin;
			jsonAvailableRoles = ImmutableList.of(roleToLogin);
		}
		else
		{
			jsonAvailableRoles = createJSONLoginRoles(loginService, availableRoles);
			roleToLoginEffective = jsonAvailableRoles.size() == 1 ? jsonAvailableRoles.get(0) : null;
		}

		if (roleToLoginEffective != null)
		{
			loginComplete(roleToLoginEffective);
			return JSONLoginAuthResponse.loginComplete(roleToLoginEffective);
		}
		else
		{
			return JSONLoginAuthResponse.of(jsonAvailableRoles);
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

		final ArrayList<JSONLoginRole> result = new ArrayList<>();
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

		result.sort(ROLES_ORDERING);

		return result;
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
		userSession.assertNotLoggedIn();

		final Login loginService = getLoginService();
		if (!loginService.isAuthenticated())
		{
			throw new NotLoggedInException();
		}

		final RoleId roleId = RoleId.ofRepoId(loginRole.getRoleId());
		final ClientId clientId = ClientId.ofRepoId(loginRole.getTenantId());
		final OrgId orgId = OrgId.ofRepoId(loginRole.getOrgId());

		//
		// Update context
		// TODO: optimize
		loginService.setRoleAndGetClients(roleId);
		loginService.setClientAndGetOrgs(clientId);

		//
		// Load preferences and export them to context
		final LoginContext loginCtx = loginService.getCtx();
		final UserPreference userPreference = userSession.getUserPreference();
		userPreference.loadPreference(loginCtx.getSessionContext());
		userPreference.updateContext(loginCtx.getSessionContext());

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
			if (!Check.isBlank(msg))
			{
				throw new AdempiereException(msg);
			}
		}

		//
		// Save user preferences
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
