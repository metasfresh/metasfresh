/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.calendar;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Objects;

@Value
@Builder
@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class CalendarQuery
{
	@Nullable SimulationPlanId simulationId;

	@NonNull @Singular ImmutableSet<CalendarServiceId> onlyCalendarServiceIds;
	@NonNull @Singular ImmutableSet<CalendarGlobalId> onlyCalendarIds;
	@NonNull @Singular ImmutableSet<CalendarResourceId> onlyResourceIds;
	@Nullable Instant startDate;
	@Nullable Instant endDate;

	public boolean isMatchingSimulationId(@Nullable final SimulationPlanId simulationId)
	{
		return simulationId == null // actual data is matching any simulation
				|| SimulationPlanId.equals(this.simulationId, simulationId);
	}

	public boolean isMatchingCalendarServiceId(@NonNull final CalendarServiceId calendarServiceId)
	{
		return onlyCalendarServiceIds.isEmpty() || onlyCalendarServiceIds.contains(calendarServiceId);
	}

	public boolean isMatchingCalendarId(@NonNull final CalendarGlobalId calendarId)
	{
		return onlyCalendarIds.isEmpty() || onlyCalendarIds.contains(calendarId);
	}

	public boolean isMatchingResourceId(@NonNull final CalendarResourceId resourceId)
	{
		return onlyResourceIds.isEmpty() || onlyResourceIds.contains(resourceId);
	}

	public boolean isMatchingDateRange(@NonNull final CalendarDateRange dateRange)
	{
		return dateRange.isConnectedTo(this.startDate, this.endDate);
	}

	public boolean isMatchingEntry(@NonNull final CalendarEntry entry)
	{
		return isMatchingSimulationId(entry.getSimulationId())
				&& isMatchingCalendarServiceId(entry.getCalendarServiceId())
				&& isMatchingCalendarId(entry.getCalendarId())
				&& isMatchingResourceId(entry.getResourceId())
				&& isMatchingDateRange(entry.getDateRange());
	}

	public <T extends RepoIdAware> ImmutableSet<T> getOnlyResourceIdsOfType(@NonNull final Class<T> clazz)
	{
		return onlyResourceIds
				.stream()
				.map(calendarResourceId -> calendarResourceId.toRepoIdOrNull(clazz))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
