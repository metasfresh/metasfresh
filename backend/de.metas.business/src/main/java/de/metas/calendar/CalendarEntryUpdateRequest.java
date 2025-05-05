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
import de.metas.calendar.util.CalendarDateRange;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class CalendarEntryUpdateRequest
{
	@NonNull CalendarEntryId entryId;
	@Nullable SimulationPlanId simulationId;
	
	@NonNull UserId updatedByUserId;
	@NonNull CalendarDateRange dateRange;

	@Nullable CalendarResourceId resourceId;
	@Nullable String title;
	@Nullable String description;

	public CalendarGlobalId getCalendarId()
	{
		return getEntryId().getCalendarId();
	}

	public CalendarServiceId getCalendarServiceId()
	{
		return getEntryId().getCalendarServiceId();
	}
}
