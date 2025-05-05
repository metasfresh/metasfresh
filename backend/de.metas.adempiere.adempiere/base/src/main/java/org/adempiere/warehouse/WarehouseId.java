package org.adempiere.warehouse;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.business
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class WarehouseId implements RepoIdAware
{
	public static final WarehouseId MAIN = new WarehouseId(540008);

	@JsonCreator
	public static WarehouseId ofRepoId(final int repoId)
	{
		if (repoId == MAIN.repoId)
		{
			return MAIN;
		}
		else
		{
			return new WarehouseId(repoId);
		}
	}

	@Nullable
	public static WarehouseId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<WarehouseId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final WarehouseId warehouseId)
	{
		return warehouseId != null ? warehouseId.getRepoId() : -1;
	}

	public static Set<Integer> toRepoIds(final Collection<WarehouseId> warehouseIds)
	{
		return warehouseIds.stream()
				.map(WarehouseId::toRepoId)
				.filter(id -> id > 0)
				.collect(ImmutableSet.toImmutableSet());
	}

	int repoId;

	private WarehouseId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Warehouse_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final WarehouseId id1, @Nullable final WarehouseId id2)
	{
		return Objects.equals(id1, id2);
	}
}
