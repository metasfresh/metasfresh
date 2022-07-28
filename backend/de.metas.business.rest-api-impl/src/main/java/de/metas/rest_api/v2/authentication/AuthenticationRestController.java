package de.metas.rest_api.v2.authentication;

import de.metas.Profiles;
import de.metas.common.rest_api.v2.authentication.JsonAuthRequest;
import de.metas.common.rest_api.v2.authentication.JsonAuthResponse;
import de.metas.common.rest_api.v2.i18n.JsonMessages;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.rest_api.v2.i18n.I18NRestController;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import de.metas.util.web.security.UserAuthTokenService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.compiere.util.Login;
import org.compiere.util.LoginAuthenticateResponse;
import org.compiere.util.LoginContext;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(AuthenticationRestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class AuthenticationRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/auth";

	private final Logger logger = LogManager.getLogger(AuthenticationRestController.class);
	private final IUserBL userBL = Services.get(IUserBL.class);
	private final UserAuthTokenService userAuthTokenService;
	private final I18NRestController i18nRestController;

	public AuthenticationRestController(
			@NonNull final UserAuthTokenService userAuthTokenService,
			@NonNull final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration,
			@NonNull final I18NRestController i18nRestController)
	{
		this.userAuthTokenService = userAuthTokenService;
		this.i18nRestController = i18nRestController;

		userAuthTokenFilterConfiguration.excludePathContaining(ENDPOINT);
	}

	@PostMapping
	public ResponseEntity<JsonAuthResponse> authenticate(@RequestBody @NonNull final JsonAuthRequest request)
	{
		try
		{
			final LoginContext loginCtx = new LoginContext(Env.newTemporaryCtx());
			loginCtx.setWebui(true);
			final Login loginService = new Login(loginCtx);

			final LoginAuthenticateResponse loginAuthResult = loginService.authenticate(
					request.getUsername(),
					HashableString.ofPlainValue(request.getPassword()));

			final UserId userId = loginAuthResult.getUserId();
			final RoleId roleId = loginAuthResult.getSingleRole()
					.map(Role::getId)
					.orElseThrow(() -> new AdempiereException("Multiple roles are not supported. Make sure user has only one role assigned"));
			final ClientId clientId = getClientId(loginService, userId, roleId);
			final OrgId orgId = getOrgId(loginService, userId, roleId, clientId);

			final UserAuthToken userAuthToken = userAuthTokenService.getOrCreateNewToken(CreateUserAuthTokenRequest.builder()
					.userId(userId)
					.clientId(clientId)
					.orgId(orgId)
					.roleId(roleId)
					.description("Created by " + AuthenticationRestController.class.getName())
					.build());

			final Language userLanguage = userBL.getUserLanguage(userAuthToken.getUserId());
			final String adLanguage = userLanguage.getAD_Language();
			final JsonMessages messages = i18nRestController.getMessages(null, adLanguage);

			return ResponseEntity.ok(
					JsonAuthResponse.ok(userAuthToken.getAuthToken())
							.userId(userId.getRepoId())
							.language(adLanguage)
							.messages(messages.getMessages())
							.build());
		}
		catch (final Exception ex)
		{
			// IMPORTANT: don't return 401 because the frontend + chrome + CORS + axios fuck it up
			// and return an undefined response to their callers

			if (AdempiereException.isUserValidationError(ex))
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(JsonAuthResponse.error(AdempiereException.extractMessage(ex)));
			}
			else
			{
				logger.warn("Failed authenticating user {}", request.getUsername(), ex);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(JsonAuthResponse.error("Authentication error. Pls contact the system administrator."));
			}
		}

	}

	private static ClientId getClientId(
			@NonNull final Login loginService,
			@NonNull final UserId userId,
			@NonNull final RoleId roleId)
	{
		final Set<ClientId> clientIds = loginService.getAvailableClients(roleId, userId);
		if (clientIds == null || clientIds.isEmpty())
		{
			throw new AdempiereException("User is not assigned to an AD_Client");
		}
		else if (clientIds.size() != 1)
		{
			throw new AdempiereException("User is assigned to more than one AD_Client");
		}
		else
		{
			return clientIds.iterator().next();
		}
	}

	private static OrgId getOrgId(final Login loginService, final UserId userId, final RoleId roleId, final ClientId clientId)
	{
		final Set<OrgId> orgIds = loginService.getAvailableOrgs(roleId, userId, clientId);
		final OrgId orgId;
		if (orgIds == null || orgIds.isEmpty())
		{
			throw new AdempiereException("User is not assigned to an AD_Org");
		}
		else if (orgIds.size() != 1)
		{
			// if there are more Orgs, we are going with organization "*"
			orgId = OrgId.ANY;
		}
		else
		{
			orgId = orgIds.iterator().next();
		}
		return orgId;
	}
}
