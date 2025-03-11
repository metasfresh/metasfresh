package de.metas.notification.impl;

import de.metas.cache.CCache;
import de.metas.notification.INotificationGroupRepository;
import de.metas.notification.NotificationGroup;
import de.metas.notification.NotificationGroupCC;
import de.metas.notification.NotificationGroupCCs;
import de.metas.notification.NotificationGroupId;
import de.metas.notification.NotificationGroupName;
import de.metas.notification.Recipient;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_NotificationGroup;
import org.compiere.model.I_AD_NotificationGroup_CC;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

public class NotificationGroupRepository implements INotificationGroupRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CCache<Integer, NotificationGroupMap> notificationGroupNames = CCache.<Integer, NotificationGroupMap>builder()
			.tableName(I_AD_NotificationGroup.Table_Name)
			.additionalTableNameToResetFor(I_AD_NotificationGroup_CC.Table_Name)
			.initialCapacity(1)
			.build();

	@Override
	public Optional<NotificationGroupName> getNameById(@NonNull final NotificationGroupId notificationGroupId) {return getMap().getNameById(notificationGroupId);}

	@Override
	public Optional<NotificationGroupId> getNotificationGroupId(final NotificationGroupName notificationGroupName) {return getMap().getIdByName(notificationGroupName);}

	@Override
	public Optional<NotificationGroup> getNotificationGroupByName(final NotificationGroupName notificationGroupName) {return getMap().getByName(notificationGroupName);}

	@Override
	public Set<NotificationGroupName> getActiveNames() {return getMap().getNames();}

	private NotificationGroupMap getMap() {return notificationGroupNames.getOrLoad(0, this::retrieveMap);}

	private NotificationGroupMap retrieveMap()
	{
		final Map<NotificationGroupId, NotificationGroupCCs> ccsByGroupId = queryBL.createQueryBuilderOutOfTrx(I_AD_NotificationGroup_CC.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						record -> NotificationGroupId.ofRepoId(record.getAD_NotificationGroup_ID()),
						Collectors.mapping(NotificationGroupRepository::extractRecipient, NotificationGroupCCs.collect())
				));

		return queryBL.createQueryBuilderOutOfTrx(I_AD_NotificationGroup.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toNotificationGroup(record, ccsByGroupId))
				.collect(NotificationGroupMap.collect());
	}

	private static NotificationGroupCC toNotificationGroupCC(final I_AD_NotificationGroup_CC record)
	{
		return NotificationGroupCC.builder()
				.recipient(extractRecipient(record))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.build();

	}

	private static Recipient extractRecipient(final I_AD_NotificationGroup_CC record)
	{
		return Recipient.user(UserId.ofRepoId(record.getAD_User_ID()));
	}

	private static NotificationGroup toNotificationGroup(final I_AD_NotificationGroup record, Map<NotificationGroupId, NotificationGroupCCs> ccsById)
	{
		final NotificationGroupId id = NotificationGroupId.ofRepoId(record.getAD_NotificationGroup_ID());

		return NotificationGroup.builder()
				.id(id)
				.name(NotificationGroupName.of(record.getInternalName()))
				.ccs(ccsById.getOrDefault(id, NotificationGroupCCs.EMPTY))
				.build();
	}
}
