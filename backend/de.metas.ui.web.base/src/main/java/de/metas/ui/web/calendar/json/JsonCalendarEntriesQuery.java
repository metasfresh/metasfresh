/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.calendar.json;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.CalendarGlobalId;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.project.ProjectId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonCalendarEntriesQuery
{
	@Nullable SimulationPlanId simulationId;
	@Nullable @Singular Set<CalendarGlobalId> calendarIds;
	@Nullable Set<CalendarResourceId> onlyResourceIds;
	@Nullable ProjectId onlyProjectId;
	@Nullable BPartnerId onlyCustomerId;
	@Nullable UserId onlyResponsibleId;

	@Nullable JsonDateTime startDate;
	@Nullable JsonDateTime endDate;

	@Builder.Default
	boolean skipAllocatedResources = true;
}
