package de.metas.security.permissions.record_access.handlers;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import de.metas.security.permissions.record_access.RecordAccess;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class RecordAccessChangeEvent
{
	public static RecordAccessChangeEvent accessGrant(final RecordAccess access)
	{
		return builder().accessGrant(access).build();
	}

	public static RecordAccessChangeEvent accessGrants(final Collection<RecordAccess> accesses)
	{
		return builder().accessGrants(accesses).build();
	}

	public static RecordAccessChangeEvent accessRevoke(final RecordAccess access)
	{
		return builder().accessRevoke(access).build();
	}

	public static RecordAccessChangeEvent accessRevokes(final Collection<RecordAccess> accesses)
	{
		return builder().accessRevokes(accesses).build();
	}

	@JsonProperty("accessGrants")
	private Set<RecordAccess> accessGrants;
	@JsonProperty("accessRevokes")
	private Set<RecordAccess> accessRevokes;

	@JsonCreator
	@Builder
	private RecordAccessChangeEvent(
			@JsonProperty("accessGrants") @Singular final Set<RecordAccess> accessGrants,
			@JsonProperty("accessRevokes") @Singular final Set<RecordAccess> accessRevokes)
	{
		this.accessGrants = accessGrants != null ? ImmutableSet.copyOf(accessGrants) : ImmutableSet.of();
		this.accessRevokes = accessRevokes != null ? ImmutableSet.copyOf(accessRevokes) : ImmutableSet.of();
	}

	public boolean isEmpty()
	{
		return accessGrants.isEmpty() && accessRevokes.isEmpty();
	}

}
