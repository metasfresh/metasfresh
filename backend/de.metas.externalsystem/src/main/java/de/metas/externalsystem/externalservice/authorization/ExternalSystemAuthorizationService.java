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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.common.externalsystem.JsonExternalSystemMessageTypeEnum;
import de.metas.externalsystem.rabbitmq.custom.CustomMFToExternalSystemMessageSender;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.user.UserGroup;
import de.metas.user.UserGroupRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

@Service
public class ExternalSystemAuthorizationService
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	private final ObjectMapper objectMapper;
	private final UserAuthTokenRepository authTokenRepository;
	private final CustomMFToExternalSystemMessageSender customMessageSender;
	private final UserGroupRepository userGroupRepository;

	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_AUTH_TOKEN = "de.metas.externalsystem.externalservice.authorization.authToken";

	private static final String EXTERNAL_SYSTEM_AUTH_NOTIFICATION_SUBJECT = "External_Systems_Authorization_Subject";
	private static final String EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION = "ExternalSystem_Authorization_Verification_Error";
	private static final String EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND = "External_Systems_Authorization_SysConfig_Not_Found_Error";

	public ExternalSystemAuthorizationService(
			@NonNull final ObjectMapper objectMapper,
			@NonNull final UserAuthTokenRepository authTokenRepository,
			@NonNull final CustomMFToExternalSystemMessageSender customMessageSender,
			@NonNull final UserGroupRepository userGroupRepository
	)
	{
		this.objectMapper = objectMapper;
		this.authTokenRepository = authTokenRepository;
		this.customMessageSender = customMessageSender;
		this.userGroupRepository = userGroupRepository;
	}

	public void postAuthorizationReply()
	{
		final String token = sysConfigBL.getValue(SYS_CONFIG_EXTERNAL_SYSTEM_AUTH_TOKEN);

		if (token != null)
		{
			try
			{
				final UserAuthToken authToken = authTokenRepository.getByToken(token);

				final JsonExternalSystemMessagePayload payload = JsonExternalSystemMessagePayload.builder()
						.authToken(authToken.getAuthToken())
						.build();

				final JsonExternalSystemMessage message = JsonExternalSystemMessage.builder()
						.type(JsonExternalSystemMessageTypeEnum.AUTHORIZATION_REPLY)
						.payload(objectMapper.writeValueAsString(payload))
						.build();

				customMessageSender.send(message);
			} catch (final Exception e)
			{
				sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_SUBJECT, EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION);
			}
		} else {
			sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_SUBJECT, EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND);
		}
	}

	private void sendErrorNotification(final String subjectKey, final String contentKey)
	{
		final AdMessageKey notificationSubject = AdMessageKey.of(subjectKey);
		final AdMessageKey notificationContent = AdMessageKey.of(contentKey);

		final UserGroup userGroup = userGroupRepository.getUserGroupByName(ExternalSystemConstants.API_SETUP_USER_GROUP_NAME);

		final Recipient recipient = Recipient.group(userGroup.getId());

		final UserNotificationRequest verificationFailureNotification = UserNotificationRequest.builder()
				.recipient(recipient)
				.subjectADMessage(notificationSubject)
				.contentADMessage(notificationContent)
				.build();
		notificationBL.send(verificationFailureNotification);
	}
}
