/*
 * #%L
 * de.metas.business
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

package de.metas.inout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

@Value
public class ShipmentScheduleId implements RepoIdAware
{
	private static final String M_SHIPMENT_SCHEDULE_TABLE_NAME = "M_ShipmentSchedule";

	@JsonCreator
	public static ShipmentScheduleId ofRepoId(final int repoId)
	{
		return new ShipmentScheduleId(repoId);
	}

	public static ShipmentScheduleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static ImmutableSet<Integer> toIntSet(@NonNull final Collection<ShipmentScheduleId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ids.stream().map(ShipmentScheduleId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	public static ImmutableSet<ShipmentScheduleId> fromIntSet(@NonNull final Collection<Integer> repoIds)
	{
		if (repoIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return repoIds.stream().map(ShipmentScheduleId::ofRepoIdOrNull).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
	}

	int repoId;

	private ShipmentScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_ShipmentSchedule_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ShipmentScheduleId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(M_SHIPMENT_SCHEDULE_TABLE_NAME, getRepoId());
	}

	public static TableRecordReferenceSet toTableRecordReferenceSet(@NonNull final Collection<ShipmentScheduleId> ids)
	{
		return TableRecordReferenceSet.of(M_SHIPMENT_SCHEDULE_TABLE_NAME, ids);
	}
}
