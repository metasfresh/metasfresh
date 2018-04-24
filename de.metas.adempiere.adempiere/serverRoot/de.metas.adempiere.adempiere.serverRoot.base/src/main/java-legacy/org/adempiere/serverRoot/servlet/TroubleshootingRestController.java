package org.adempiere.serverRoot.servlet;

import java.util.concurrent.atomic.AtomicLong;

import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@RequestMapping("/test")
public class TroubleshootingRestController
{
	private static final Logger logger = LogManager.getLogger(TroubleshootingRestController.class);

	private final AtomicLong nextNotificationId = new AtomicLong(1);

	@GetMapping("/ping/notifications")
	public String pingNotifications()
	{
		final long id = nextNotificationId.getAndIncrement();

		final UserNotificationRequest request = UserNotificationRequest.builder()
				.broadcastToAllUsers(true)
				.subjectPlain("Notifications system test")
				.contentPlain("Please ignore this message. It was issued by server to check the notifications system (#" + id + ").")
				.build();
		Services.get(INotificationBL.class).notifyUser(request);

		final String message = "sent: " + request;
		logger.info("pingNotifications: {}", message);
		return message;
	}
}
