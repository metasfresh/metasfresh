package de.metas.notification;

import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import org.adempiere.ad.dao.QueryLimit;

import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface INotificationRepository extends ISingletonService
{
	UserNotification save(UserNotificationRequest request);

	boolean deleteById(int notificationId);

	void deleteAllByUserId(UserId userId);

	boolean markAsReadById(int notificationId);

	void markAllAsReadByUserId(UserId adUserId);

	List<UserNotification> getByUserId(UserId adUserId, QueryLimit limit);

	int getTotalCountByUserId(UserId adUserId);

	int getUnreadCountByUserId(UserId adUserId);
}
