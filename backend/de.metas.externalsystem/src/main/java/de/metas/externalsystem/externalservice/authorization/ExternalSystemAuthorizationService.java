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

import de.metas.JsonObjectMapperHolder;
import de.metas.Profiles;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.common.externalsystem.JsonExternalSystemMessageType;
import de.metas.externalsystem.rabbitmq.custom.CustomMFToExternalSystemMessageSender;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.user.UserGroupId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
@Profile(Profiles.PROFILE_App)
public class ExternalSystemAuthorizationService
{
	private static final Logger log = LogManager.getLogger(ExternalSystemAuthorizationService.class);

	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_AUTH_TOKEN = "de.metas.externalsystem.externalservice.authorization.authToken";
	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_NOTIFICATION_USER_GROUP = "de.metas.externalsystem.externalservice.authorization.notificationUserGroupId";

	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_SUBJECT = AdMessageKey.of("External_Systems_Authorization_Subject");
	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION = AdMessageKey.of("ExternalSystem_Authorization_Verification_Error");
	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND = AdMessageKey.of("External_Systems_Authorization_SysConfig_Not_Found_Error");

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull
	private final UserAuthTokenRepository authTokenRepository;
	@NonNull
	private final CustomMFToExternalSystemMessageSender customMessageSender;

	public ExternalSystemAuthorizationService(
			@NonNull final UserAuthTokenRepository authTokenRepository,
			@NonNull final CustomMFToExternalSystemMessageSender customMessageSender
	)
	{
		this.authTokenRepository = authTokenRepository;
		this.customMessageSender = customMessageSender;
	}

	public void postAuthorizationReply()
	{
		try
		{
			final String authToken = getAuthToken().orElse(null);

			if (authToken == null)
			{
				return;
			}

			final JsonExternalSystemMessagePayload payload = JsonExternalSystemMessagePayload.builder()
					.authToken(authToken)
					.build();

			final JsonExternalSystemMessage message = JsonExternalSystemMessage.builder()
					.type(JsonExternalSystemMessageType.AUTHORIZATION_REPLY)
					.payload(JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(payload))
					.build();

			customMessageSender.send(message);
		}
		catch (final Exception e)
		{
			log.error(e.getMessage(), e);
			sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION, e);
		}
	}

	@NonNull
	private Optional<String> getAuthToken()
	{
		final String token = sysConfigBL.getValue(SYS_CONFIG_EXTERNAL_SYSTEM_AUTH_TOKEN);

		if (Check.isBlank(token))
		{
			sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND, null);
			return Optional.empty();
		}

		final UserAuthToken authToken = authTokenRepository.getByToken(token);

		return Optional.of(authToken.getAuthToken());
	}

	private void sendErrorNotification(@NonNull final AdMessageKey errorMessage, @Nullable final Exception exception)
	{
		final int externalSystemAPIUserGroup = sysConfigBL.getIntValue(SYS_CONFIG_EXTERNAL_SYSTEM_NOTIFICATION_USER_GROUP, -1);

		final UserGroupId userGroupId = UserGroupId.ofRepoIdOrNull(externalSystemAPIUserGroup);

		if (userGroupId == null)
		{
			log.error("No AD_UserGroup is configured to be in charge of the ExternalSystem services authorization!");
			log.error(msgBL.getMsg(Env.getAD_Language(), errorMessage), exception);
			return;
		}

		final String errorDetails = Optional.ofNullable(exception)
				.map(Util::dumpStackTraceToString)
				.orElse(null);

		final Recipient recipient = Recipient.group(userGroupId);

		final UserNotificationRequest verificationFailureNotification = UserNotificationRequest.builder()
				.recipient(recipient)
				.subjectADMessage(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_SUBJECT)
				.contentADMessage(errorMessage)
				.contentADMessageParam(errorDetails)
				.build();

		notificationBL.send(verificationFailureNotification);
	}
}
