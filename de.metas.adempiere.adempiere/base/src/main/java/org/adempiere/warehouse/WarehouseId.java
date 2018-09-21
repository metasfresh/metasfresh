package org.adempiere.warehouse;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.lang.RepoIdAware;
import de.metas.util.Check;
import lombok.Value;

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

@Value
public class WarehouseId implements RepoIdAware
{
	int repoId;

	public static WarehouseId ofRepoId(final int repoId)
	{
		return new WarehouseId(repoId);
	}

	public static WarehouseId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new WarehouseId(repoId) : null;
	}

	public static int toRepoId(final WarehouseId warehouseId)
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

	private WarehouseId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}
}
