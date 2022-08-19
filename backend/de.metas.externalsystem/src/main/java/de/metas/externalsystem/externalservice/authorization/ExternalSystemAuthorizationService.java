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
import de.metas.JsonObjectMapperHolder;
import de.metas.Profiles;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.common.externalsystem.JsonExternalSystemMessageType;
import de.metas.externalsystem.externalservice.utility.NotificationHelper;
import de.metas.externalsystem.rabbitmq.custom.CustomMFToExternalSystemMessageSender;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(Profiles.PROFILE_App)
public class ExternalSystemAuthorizationService
{
	private static final Logger log = LogManager.getLogger(ExternalSystemAuthorizationService.class);

	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION = AdMessageKey.of("ExternalSystem_Authorization_Verification_Error");

	private final IStartupHouseKeepingTask startupHouseKeepingTask = SpringContextHolder.instance.getBean(IStartupHouseKeepingTask.class);

	@NonNull
	private final CustomMFToExternalSystemMessageSender customMessageSender;

	public ExternalSystemAuthorizationService(@NonNull final CustomMFToExternalSystemMessageSender customMessageSender)
	{
		this.customMessageSender = customMessageSender;
	}

	public void postAuthorizationReply()
	{
		try
		{
			final String authToken = startupHouseKeepingTask.getAuthToken().orElse(null);

			if (authToken == null)
			{
				return;
			}

			final JsonExternalSystemMessage message = buildJsonExternalSystemMessage(authToken);

			customMessageSender.send(message);
		}
		catch (final Exception e)
		{
			log.error(e.getMessage(), e);
			NotificationHelper.sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_VERIFICATION, null, e);
		}
	}

	@NonNull
	private JsonExternalSystemMessage buildJsonExternalSystemMessage(@NonNull final String authToken) throws JsonProcessingException
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
