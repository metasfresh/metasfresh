/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.service.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.organization.ClientAndOrgId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Optional;

@EqualsAndHashCode
class SysConfigMap
{
	private final ImmutableMap<String, SysConfigEntry> entries;

	public SysConfigMap(@NonNull final ImmutableMap<String, SysConfigEntry> entries)
	{
		this.entries = entries;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("size", entries.size())
				.toString();
	}

	public ImmutableList<String> getNamesForPrefix(@NonNull final String prefix, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return entries.values()
				.stream()
				.filter(entry -> entry.isNameStartsWith(prefix))
				.filter(entry -> entry.isClientAndOrgMatching(clientAndOrgId))
				.map(SysConfigEntry::getName)
				.collect(ImmutableList.toImmutableList());
	}

	public Optional<String> getValueAsString(@NonNull final String name, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final SysConfigEntry entry = entries.get(name);
		return entry != null ? entry.getValueAsString(clientAndOrgId) : Optional.empty();
	}
}
