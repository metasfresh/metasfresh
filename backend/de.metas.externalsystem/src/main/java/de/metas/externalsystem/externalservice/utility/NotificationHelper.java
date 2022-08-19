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

package de.metas.externalsystem.externalservice.utility;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.externalservice.authorization.StartupHouseKeepingTask;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserGroupId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

@UtilityClass
public class NotificationHelper
{
	private static final Logger log = LogManager.getLogger(StartupHouseKeepingTask.class);

	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_NOTIFICATION_USER_GROUP = "de.metas.externalsystem.externalservice.authorization.notificationUserGroupId";

	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_SUBJECT = AdMessageKey.of("External_Systems_Authorization_Subject");

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public static void sendErrorNotification(
			@NonNull final AdMessageKey errorMessage,
			@Nullable final String sysConfig,
			@Nullable final Exception exception)
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

		final ImmutableList<Object> messageParams = buildMessageParams(errorDetails, sysConfig);
		
		final UserNotificationRequest verificationFailureNotification = UserNotificationRequest.builder()
				.recipient(recipient)
				.subjectADMessage(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_SUBJECT)
				.contentADMessage(errorMessage)
				.contentADMessageParams(messageParams)
				.build();

		notificationBL.send(verificationFailureNotification);
	}
	
	@Nullable
	private ImmutableList<Object> buildMessageParams(@Nullable final String errorDetails, @Nullable final String param)
	{
		if(Check.isBlank(errorDetails) && Check.isBlank(param))
		{
			return null;
		}
		
		final ImmutableList.Builder<Object> paramsBuilder = ImmutableList.builder();
		if(Check.isNotBlank(param))
		{
			paramsBuilder.add(param);
		}

		if(Check.isNotBlank(errorDetails))
		{
			paramsBuilder.add(errorDetails);
		}
		
		return paramsBuilder.build();
	}
}
