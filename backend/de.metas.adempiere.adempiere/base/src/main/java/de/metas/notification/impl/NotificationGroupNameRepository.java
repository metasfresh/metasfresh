package de.metas.notification.impl;

import com.google.common.collect.ImmutableBiMap;
import de.metas.cache.CCache;
import de.metas.notification.INotificationGroupNameRepository;
import de.metas.notification.NotificationGroupName;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_NotificationGroup;

import javax.annotation.Nullable;
import java.util.Set;

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
	private final CCache<Integer, ImmutableBiMap<Integer, NotificationGroupName>> notificationGroupNames = CCache.newCache(I_AD_NotificationGroup.Table_Name, 1, CCache.EXPIREMINUTES_Never);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public NotificationGroupName getById(final int notificationGroupId)
	{
		return getNotificationGroupInternalNamesById().get(notificationGroupId);
	}

	@Override
	public int getNotificationGroupId(final NotificationGroupName notificationGroupName)
	{
		return getNotificationGroupInternalNamesById().inverse().getOrDefault(notificationGroupName, -1);
	}

	@Override
	public Set<NotificationGroupName> getAll()
	{
		return getNotificationGroupInternalNamesById().values();
	}

	@Override
	@Cached
	@Nullable
	public UserId getDeadletterUserId(final NotificationGroupName notificationGroupName)
	{
		final Integer first = queryBL
				.createQueryBuilderOutOfTrx(I_AD_NotificationGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_NotificationGroup.COLUMNNAME_InternalName, notificationGroupName.getValueAsString())
				.create()
				.first(I_AD_NotificationGroup.COLUMNNAME_Deadletter_User_ID, Integer.class);
		return first == null ? null : UserId.ofRepoIdOrNull(first);
	}

	@Override
	public boolean isRestrictToOrgBPUsers(final NotificationGroupName notificationGroupName)
	{
		return false;
	}

	private ImmutableBiMap<Integer, NotificationGroupName> getNotificationGroupInternalNamesById()
	{
		return notificationGroupNames.getOrLoad(0, this::retrieveNotificationGroupInternalNamesById);
	}

	private ImmutableBiMap<Integer, NotificationGroupName> retrieveNotificationGroupInternalNamesById()
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_NotificationGroup.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableBiMap.toImmutableBiMap(
						I_AD_NotificationGroup::getAD_NotificationGroup_ID,
						notificationGroupRecord -> NotificationGroupName.of(notificationGroupRecord.getInternalName())));
	}

}
