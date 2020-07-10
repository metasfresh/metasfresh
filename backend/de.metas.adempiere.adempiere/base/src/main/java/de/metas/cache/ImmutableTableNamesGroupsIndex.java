package de.metas.cache;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.jgoodies.common.base.Objects;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
class ImmutableTableNamesGroupsIndex
{
	public static ImmutableTableNamesGroupsIndex EMPTY = new ImmutableTableNamesGroupsIndex();

	public static ImmutableTableNamesGroupsIndex ofCollection(final Collection<TableNamesGroup> groups)
	{
		return new ImmutableTableNamesGroupsIndex(groups);
	}

	private static final String DEFAULT_GROUP_ID = "default";
	private final ImmutableMap<String, TableNamesGroup> groupsById;
	private final ImmutableSet<String> tableNames;

	private ImmutableTableNamesGroupsIndex(final Collection<TableNamesGroup> groups)
	{
		this.groupsById = Maps.uniqueIndex(groups, TableNamesGroup::getGroupId);

		tableNames = groups.stream()
				.flatMap(group -> group.getTableNames().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableTableNamesGroupsIndex()
	{
		groupsById = ImmutableMap.of();
		tableNames = ImmutableSet.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(groupsById)
				.toString();
	}

	public ImmutableSet<String> getTableNames()
	{
		return tableNames;
	}

	public boolean containsTableName(final String tableName)
	{
		return tableNames.contains(tableName);
	}

	public ImmutableTableNamesGroupsIndex addingToDefaultGroup(@NonNull final String tableName)
	{
		return addingToDefaultGroup(ImmutableSet.of(tableName));
	}

	public ImmutableTableNamesGroupsIndex addingToDefaultGroup(@NonNull final Collection<String> tableNames)
	{
		if (tableNames.isEmpty())
		{
			return this;
		}

		final TableNamesGroup defaultGroupExisting = groupsById.get(DEFAULT_GROUP_ID);
		final TableNamesGroup defaultGroupNew;
		if (defaultGroupExisting != null)
		{
			defaultGroupNew = defaultGroupExisting.toBuilder()
					.tableNames(tableNames)
					.build();
		}
		else
		{
			defaultGroupNew = TableNamesGroup.builder()
					.groupId(DEFAULT_GROUP_ID)
					.tableNames(tableNames)
					.build();
		}

		if (Objects.equals(defaultGroupExisting, defaultGroupNew))
		{
			return this;
		}

		return replacingGroup(defaultGroupNew);
	}

	public ImmutableTableNamesGroupsIndex replacingGroup(@NonNull final TableNamesGroup groupToAdd)
	{
		if (groupsById.isEmpty())
		{
			return new ImmutableTableNamesGroupsIndex(ImmutableList.of(groupToAdd));
		}

		final ArrayList<TableNamesGroup> newGroups = new ArrayList<>(groupsById.size() + 1);
		boolean added = false;
		for (final TableNamesGroup group : groupsById.values())
		{
			if (Objects.equals(group.getGroupId(), groupToAdd.getGroupId()))
			{
				newGroups.add(groupToAdd);
				added = true;
			}
			else
			{
				newGroups.add(group);
			}
		}

		if (!added)
		{
			newGroups.add(groupToAdd);
			added = true;
		}

		return new ImmutableTableNamesGroupsIndex(newGroups);
	}
}
