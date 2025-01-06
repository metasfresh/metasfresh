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

import com.google.common.collect.ImmutableList;
import de.metas.calendar.conflicts.CalendarConflictsService;
import de.metas.calendar.conflicts.CalendarEntryConflicts;
import de.metas.calendar.continuous_query.CalendarContinuousQuery;
import de.metas.calendar.continuous_query.CalendarContinuousQueryDispatcher;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

@Service
public class MultiCalendarService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final CalendarServicesMap calendarServices;
	private final ImmutableList<CalendarConflictsService> calendarConflictsServices;

	private final CalendarContinuousQueryDispatcher continuousQueriesDispatcher;

	public MultiCalendarService(
			@NonNull final List<CalendarService> calendarServices,
			@NonNull final List<CalendarConflictsService> calendarConflictsServices)
	{
		this.calendarServices = CalendarServicesMap.of(calendarServices);
		this.calendarConflictsServices = ImmutableList.copyOf(calendarConflictsServices);
		this.continuousQueriesDispatcher = new CalendarContinuousQueryDispatcher(trxManager, this.calendarServices);
	}

	public Stream<CalendarRef> streamAvailableCalendars(@NonNull final UserId userId)
	{
		return calendarServices.stream()
				.flatMap(calendarService -> calendarService.streamAvailableCalendars(userId));
	}

	public Stream<CalendarEntry> query(@NonNull final CalendarQuery query)
	{
		return calendarServices.stream()
				.flatMap(calendarService -> calendarService.query(query));
	}

	public CalendarContinuousQuery continuousQuery(@NonNull final CalendarQuery query)
	{
		return continuousQueriesDispatcher.getOrCreate(query);
	}

	public CalendarEntry addEntry(@NonNull final CalendarEntryAddRequest request)
	{
		final CalendarService calendarService = calendarServices.getById(request.getCalendarId().getCalendarServiceId());
		return trxManager.callInThreadInheritedTrx(() -> {
			final CalendarEntry result = calendarService.addEntry(request);
			continuousQueriesDispatcher.onEntryAdded(result);
			return result;
		});
	}

	public CalendarEntryUpdateResult updateEntry(@NonNull final CalendarEntryUpdateRequest request)
	{
		final CalendarService calendarService = calendarServices.getById(request.getCalendarServiceId());
		return trxManager.callInThreadInheritedTrx(() -> {
			final CalendarEntryUpdateResult result = calendarService.updateEntry(request);
			continuousQueriesDispatcher.onEntryUpdated(result);
			return result;
		});
	}

	public void deleteEntryById(@NonNull final CalendarEntryId entryId, @Nullable SimulationPlanId simulationId)
	{
		final CalendarService calendarService = calendarServices.getById(entryId.getCalendarServiceId());
		trxManager.runInThreadInheritedTrx(() -> {
			calendarService.deleteEntryById(entryId, simulationId);
			continuousQueriesDispatcher.onEntryDeleted(entryId, simulationId);
		});
	}

	public void notifyEntryUpdated(@NonNull final CalendarEntryId entryId)
	{
		continuousQueriesDispatcher.onEntryUpdated(entryId);
	}

	public void notifyEntryDeleted(@NonNull final CalendarEntryId entryId)
	{
		continuousQueriesDispatcher.onEntryDeleted(entryId, null);
	}

	public CalendarEntryConflicts getConflicts(@NonNull final CalendarConflictsQuery query)
	{
		return calendarConflictsServices.stream()
				.map(conflictsService -> conflictsService.query(query))
				.reduce(CalendarEntryConflicts::mergeFrom)
				.orElse(CalendarEntryConflicts.EMPTY);
	}

	public void checkAllConflicts()
	{
		calendarConflictsServices.forEach(CalendarConflictsService::checkAllConflicts);
	}

}