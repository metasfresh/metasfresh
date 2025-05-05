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

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.user.UserId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

public interface CalendarService
{
	CalendarServiceId getCalendarServiceId();

	Stream<CalendarRef> streamAvailableCalendars(UserId userId);

	Stream<CalendarEntry> query(CalendarQuery query);

	CalendarEntry addEntry(CalendarEntryAddRequest request);

	CalendarEntryUpdateResult updateEntry(CalendarEntryUpdateRequest request);

	void deleteEntryById(
			@NonNull CalendarEntryId entryId,
			@Nullable SimulationPlanId simulationId);

	Optional<CalendarEntry> getEntryById(
			@NonNull CalendarEntryId entryId,
			@Nullable SimulationPlanId simulationId);
}
