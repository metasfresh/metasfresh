package de.metas.notification.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_NotificationGroup;
import org.compiere.util.CCache;

import com.google.common.collect.ImmutableMap;

import de.metas.notification.INotificationGroupNameRepository;
import de.metas.notification.NotificationGroupName;

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

public class NotificationGroupNameRepository implements INotificationGroupNameRepository
{
	private final CCache<Integer, ImmutableMap<Integer, NotificationGroupName>> notificationGroupNames = CCache.newCache(I_AD_NotificationGroup.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	@Override
	public NotificationGroupName getById(final int notificationGroupId)
	{
		return getNotificationGroupInternalNamesById().get(notificationGroupId);
	}

	private ImmutableMap<Integer, NotificationGroupName> getNotificationGroupInternalNamesById()
	{
		return notificationGroupNames.getOrLoad(0, this::retrieveNotificationGroupInternalNamesById);
	}

	private ImmutableMap<Integer, NotificationGroupName> retrieveNotificationGroupInternalNamesById()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_NotificationGroup.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						I_AD_NotificationGroup::getAD_NotificationGroup_ID,
						notificationGroupRecord -> NotificationGroupName.of(notificationGroupRecord.getInternalName())));
	}

}
