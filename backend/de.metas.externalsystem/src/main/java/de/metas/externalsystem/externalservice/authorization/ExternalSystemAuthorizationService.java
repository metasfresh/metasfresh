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

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.externalsystem.rabbitmq.authorization.CustomMFToExternalSystemMessageSender;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

@Service
public class ExternalSystemAuthorizationService
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final UserAuthTokenRepository authTokenRepository;
	private final CustomMFToExternalSystemMessageSender fromMFMessageSender;

	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_AUTH_TOKEN = "EXTERNAL_SYSTEM_AUTHORIZATION_TOKEN";

	public ExternalSystemAuthorizationService(
			@NonNull final UserAuthTokenRepository authTokenRepository,
			@NonNull final CustomMFToExternalSystemMessageSender fromMFMessageSender
	)
	{
		this.authTokenRepository = authTokenRepository;
		this.fromMFMessageSender = fromMFMessageSender;
	}

	public void postAuthorizationReply()
	{
		final String token = sysConfigBL.getValue(SYS_CONFIG_EXTERNAL_SYSTEM_AUTH_TOKEN);

		if (token != null)
		{
			try
			{
				final UserAuthToken authToken = authTokenRepository.getByToken(token);

				final JsonExternalSystemMessage message = JsonExternalSystemMessage.builder()
						.type(ExternalSystemConstants.FROM_MF_AUTHORIZATION_REPLY_MESSAGE_TYPE)
						.payload(JsonExternalSystemMessagePayload.of(authToken.getAuthToken()))
						.build();

				fromMFMessageSender.send(message);
			} catch (Exception e)
			{
				final UserNotificationRequest request = UserNotificationRequest.builder()
						.recipientUserId(Env.getLoggedUserId())
						.subjectPlain("Metasfresh Authorization Token for External Systems")
						.contentPlain("The authorization token could not be verified by metasfresh using sysconfig. This is the error that was thrown : " + e.getMessage() + " .")
						.noEmail(false)
						.build();
				Services.get(INotificationBL.class).send(request);
			}
		}

	}
}
