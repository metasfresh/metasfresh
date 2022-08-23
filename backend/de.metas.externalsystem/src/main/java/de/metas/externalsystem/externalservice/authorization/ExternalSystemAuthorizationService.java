/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.externalservice.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.common.externalsystem.JsonExternalSystemMessageType;
import de.metas.externalsystem.rabbitmq.custom.CustomMFToExternalSystemMessageSender;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_User;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalSystemAuthorizationService
{
	private static final Logger log = LogManager.getLogger(ExternalSystemAuthorizationService.class);

	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_AD_USER_ID = "de.metas.externalsystem.externalservice.authorization.AD_User_ID";
	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_AD_ROLE_ID = "de.metas.externalsystem.externalservice.authorization.AD_Role_ID";

	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION = AdMessageKey.of("ExternalSystem_Authorization_Verification_Error");
	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND = AdMessageKey.of("External_Systems_Authorization_SysConfig_Not_Found_Error");
	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_ROLE_NOT_FOUND = AdMessageKey.of("External_Systems_Authorization_Role_Not_Found_For_User");

	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull
	private final UserAuthTokenRepository authTokenRepository;
	@NonNull
	private final CustomMFToExternalSystemMessageSender customMessageSender;
	@NonNull
	private final ExternalSystemNotificationHelper externalsystemNotificationHelper;

	public ExternalSystemAuthorizationService(
			final @NonNull UserAuthTokenRepository authTokenRepository,
			final @NonNull CustomMFToExternalSystemMessageSender customMessageSender,
			final @NonNull ExternalSystemNotificationHelper externalsystemNotificationHelper)
	{
		this.authTokenRepository = authTokenRepository;
		this.customMessageSender = customMessageSender;
		this.externalsystemNotificationHelper = externalsystemNotificationHelper;
	}

	public void postAuthorizationReply()
	{
		try
		{
			final String authToken = getConfiguredAuthToken();

			final JsonExternalSystemMessage message = buildJsonExternalSystemMessage(authToken);

			customMessageSender.send(message);
		}
		catch (final ESMissingConfigurationException missingConfigException)
		{
			log.error(missingConfigException.getMessage(), missingConfigException);

			externalsystemNotificationHelper.sendNotification(missingConfigException.getAdMessageKey(), missingConfigException.getParams());
		}
		catch (final Exception e)
		{
			log.error(e.getMessage(), e);

			externalsystemNotificationHelper.sendNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION, ImmutableList.of(e.getMessage()));
		}
	}

	@NonNull
	private String getConfiguredAuthToken()
	{
		final I_AD_User configuredESUser = getConfiguredESUser();
		final UserId configuredUserId = UserId.ofRepoId(configuredESUser.getAD_User_ID());
		final RoleId roleId = getAndValidateRoleId(configuredUserId);

		final UserAuthToken userAuthToken = authTokenRepository.retrieveOptionalByUserAndRoleId(configuredUserId, roleId)
				.orElseGet(() -> createNewUserAuthToken(configuredESUser, roleId));

		return userAuthToken.getAuthToken();
	}

	@NonNull
	private UserAuthToken createNewUserAuthToken(@NonNull final I_AD_User user, @NonNull final RoleId givenRoleId)
	{
		final CreateUserAuthTokenRequest request = CreateUserAuthTokenRequest.builder()
				.userId(UserId.ofRepoId(user.getAD_User_ID()))
				.roleId(givenRoleId)
				.orgId(OrgId.ofRepoId(user.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(user.getAD_Client_ID()))
				.build();

		return authTokenRepository.createNew(request);
	}

	@NonNull
	private RoleId getAndValidateRoleId(@NonNull final UserId userId)
	{
		final RoleId givenRoleId = RoleId.ofRepoId(getSysConfigValue(SYS_CONFIG_EXTERNAL_SYSTEM_AD_ROLE_ID));

		final Role role = roleDAO.getById(givenRoleId);

		final List<Role> userRoles = roleDAO.getUserRoles(userId);

		final boolean hasNecessaryRole = userRoles.stream()
				.map(Role::getId)
				.anyMatch(roleId -> roleId.getRepoId() == givenRoleId.getRepoId());

		if (!hasNecessaryRole)
		{
			throw ESMissingConfigurationException.builder()
					.adMessageKey(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_ROLE_NOT_FOUND)
					.params(ImmutableList.of(role.getName(), userId.getRepoId()))
					.build();
		}

		return givenRoleId;
	}

	@NonNull
	private I_AD_User getConfiguredESUser()
	{
		final UserId configuredUserId = UserId.ofRepoId(getSysConfigValue(SYS_CONFIG_EXTERNAL_SYSTEM_AD_USER_ID));

		return userDAO.getById(configuredUserId);
	}

	@NonNull
	private Integer getSysConfigValue(@NonNull final String sysConfig)
	{
		final String sysConfigValueAsString = sysConfigBL.getValue(sysConfig);
		if (Check.isNotBlank(sysConfigValueAsString))
		{
			return Integer.parseInt(sysConfigValueAsString);
		}

		throw ESMissingConfigurationException.builder()
				.adMessageKey(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND)
				.params(ImmutableList.of(sysConfig))
				.build();
	}

	@NonNull
	private static JsonExternalSystemMessage buildJsonExternalSystemMessage(@NonNull final String authToken) throws JsonProcessingException
	{
		final JsonExternalSystemMessagePayload payload = JsonExternalSystemMessagePayload.builder()
				.authToken(authToken)
				.build();

		return JsonExternalSystemMessage.builder()
				.type(JsonExternalSystemMessageType.AUTHORIZATION_REPLY)
				.payload(JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(payload))
				.build();
	}
}
