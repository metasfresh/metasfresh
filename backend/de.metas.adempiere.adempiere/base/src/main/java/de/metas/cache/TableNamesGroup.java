package de.metas.cache;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

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
@ToString
public class TableNamesGroup
{
	@Getter
	private final String groupId;
	@Getter
	private final ImmutableSet<String> tableNames;

	@Builder(toBuilder = true)
	private TableNamesGroup(
			@NonNull final String groupId,
			@NonNull @Singular final Set<String> tableNames)
	{
		Check.assumeNotEmpty(groupId, "this.groupId is not empty");
		tableNames.forEach(tableName -> Check.assumeNotEmpty(tableName, "tableName is not empty"));

		this.groupId = groupId;
		this.tableNames = ImmutableSet.copyOf(tableNames);
	}
}
