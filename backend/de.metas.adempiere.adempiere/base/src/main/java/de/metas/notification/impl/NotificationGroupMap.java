package de.metas.notification.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.notification.NotificationGroup;
import de.metas.notification.NotificationGroupId;
import de.metas.notification.NotificationGroupName;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

class NotificationGroupMap
{
	@NonNull public static final NotificationGroupMap EMPTY = new NotificationGroupMap(ImmutableList.of());

	@NonNull private final ImmutableMap<NotificationGroupId, NotificationGroup> byId;
	@NonNull private final ImmutableMap<NotificationGroupName, NotificationGroup> byName;

	private NotificationGroupMap(@NonNull final Collection<NotificationGroup> collection)
	{
		byId = Maps.uniqueIndex(collection, NotificationGroup::getId);
		byName = Maps.uniqueIndex(collection, NotificationGroup::getName);
	}

	private static NotificationGroupMap ofCollection(@NonNull final Collection<NotificationGroup> collection)
	{
		return collection.isEmpty() ? EMPTY : new NotificationGroupMap(collection);
	}

	public static Collector<NotificationGroup, ?, NotificationGroupMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(NotificationGroupMap::ofCollection);
	}

	public Set<NotificationGroupName> getNames()
	{
		return byName.keySet();
	}

	public Optional<NotificationGroupName> getNameById(@NonNull final NotificationGroupId notificationGroupId)
	{
		return Optional.ofNullable(byId.get(notificationGroupId)).map(NotificationGroup::getName);
	}

	public Optional<NotificationGroupId> getIdByName(final NotificationGroupName name)
	{
		return getByName(name).map(NotificationGroup::getId);
	}

	public Optional<NotificationGroup> getByName(final NotificationGroupName name)
	{
		return Optional.ofNullable(byName.get(name));
	}
}
