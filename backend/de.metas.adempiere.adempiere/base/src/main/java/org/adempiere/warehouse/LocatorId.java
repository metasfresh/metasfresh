package org.adempiere.warehouse;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_Locator;

import javax.annotation.Nullable;
import java.util.Collection;
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

@Value
public class LocatorId implements RepoIdAware
{
	int repoId;
	WarehouseId warehouseId;

	public static LocatorId ofRepoId(@NonNull final WarehouseId warehouseId, final int repoId)
	{
		return new LocatorId(repoId, warehouseId);
	}

	public static LocatorId ofRepoId(final int warehouseRepoId, final int repoId)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRepoId);
		return ofRepoId(warehouseId, repoId);
	}

	@Nullable
	public static LocatorId ofRepoIdOrNull(@Nullable final WarehouseId warehouseId, final int repoId)
	{
		if (repoId <= 0)
		{
			return null;
		}

		if (warehouseId == null)
		{
			throw new IllegalArgumentException("Inconsistent state: warehouseId is null but locator's repoId=" + repoId);
		}

		return ofRepoId(warehouseId, repoId);
	}

	@Nullable
	public static LocatorId ofRepoIdOrNull(final int warehouseRepoId, final int repoId)
	{
		if (repoId <= 0)
		{
			return null;
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(warehouseRepoId);
		if (warehouseId == null)
		{
			throw new IllegalArgumentException("Inconsistent state: warehouseId is null but locator's repoId=" + repoId);
		}

		return ofRepoId(warehouseId, repoId);
	}

	@Nullable
	public static LocatorId ofRecordOrNull(@Nullable final I_M_Locator locatorRecord)
	{
		if (locatorRecord == null)
		{
			return null;
		}
		return ofRecord(locatorRecord);
	}

	public static LocatorId ofRecord(@NonNull final I_M_Locator locatorRecord)
	{
		return ofRepoId(
				WarehouseId.ofRepoId(locatorRecord.getM_Warehouse_ID()),
				locatorRecord.getM_Locator_ID());
	}

	public static int toRepoId(@Nullable final LocatorId locatorId)
	{
		return locatorId != null ? locatorId.getRepoId() : -1;
	}

	public static Set<Integer> toRepoIds(final Collection<LocatorId> locatorIds)
	{
		if (locatorIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		return locatorIds.stream().map(LocatorId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	public static boolean equalsByRepoId(final int repoId1, final int repoId2)
	{
		final int repoId1Norm = repoId1 > 0 ? repoId1 : -1;
		final int repoId2Norm = repoId2 > 0 ? repoId2 : -1;
		return repoId1Norm == repoId2Norm;
	}

	private LocatorId(final int repoId, @NonNull final WarehouseId warehouseId)
	{
		Check.assumeGreaterThanZero(repoId, "M_Locator_ID");
		this.repoId = repoId;
		this.warehouseId = warehouseId;
	}

	@JsonValue
	public String toJson()
	{
		return warehouseId.getRepoId() + "_" + repoId;
	}
}
