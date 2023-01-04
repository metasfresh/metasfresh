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

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserGroupId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class ExternalSystemNotificationHelper
{
	private static final Logger log = LogManager.getLogger(ExternalSystemNotificationHelper.class);

	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_NOTIFICATION_USER_GROUP = "de.metas.externalsystem.externalservice.authorization.notificationUserGroupId";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public void sendNotification(
			@NonNull final AdMessageKey messageKey,
			@Nullable final List<Object> params)
	{
		final int externalSystemAPIUserGroup = sysConfigBL.getIntValue(SYS_CONFIG_EXTERNAL_SYSTEM_NOTIFICATION_USER_GROUP, -1);

		final UserGroupId userGroupId = UserGroupId.ofRepoIdOrNull(externalSystemAPIUserGroup);

		if (userGroupId == null)
		{
			log.error("No AD_UserGroup is configured to be in charge of the ExternalSystem services authorization! Just dumping the notification here: {}",
					  msgBL.getMsg(Env.getAD_Language(), messageKey, params));

			return;
		}

		final Recipient recipient = Recipient.group(userGroupId);

		final UserNotificationRequest verificationFailureNotification = UserNotificationRequest.builder()
				.recipient(recipient)
				.contentADMessage(messageKey)
				.contentADMessageParams(params)
				.build();

		notificationBL.send(verificationFailureNotification);
	}
}
