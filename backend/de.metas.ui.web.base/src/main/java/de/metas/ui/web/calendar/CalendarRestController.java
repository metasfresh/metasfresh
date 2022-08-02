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

package de.metas.ui.web.calendar;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarConflictsQuery;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryAddRequest;
import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.CalendarEntryUpdateRequest;
import de.metas.calendar.CalendarEntryUpdateResult;
import de.metas.calendar.CalendarQuery;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.MultiCalendarService;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.util.CoalesceUtil;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceGroupId;
import de.metas.ui.web.calendar.json.JsonCalendarConflict;
import de.metas.ui.web.calendar.json.JsonCalendarConflictsQueryResponse;
import de.metas.ui.web.calendar.json.JsonCalendarEntriesQuery;
import de.metas.ui.web.calendar.json.JsonCalendarEntriesQueryResponse;
import de.metas.ui.web.calendar.json.JsonCalendarEntry;
import de.metas.ui.web.calendar.json.JsonCalendarEntryAddRequest;
import de.metas.ui.web.calendar.json.JsonCalendarEntryUpdateRequest;
import de.metas.ui.web.calendar.json.JsonCalendarEntryUpdateResult;
import de.metas.ui.web.calendar.json.JsonCalendarRef;
import de.metas.ui.web.calendar.json.JsonDateTime;
import de.metas.ui.web.calendar.json.JsonGetAvailableCalendarsResponse;
import de.metas.ui.web.calendar.json.JsonGetAvailableSimulationsResponse;
import de.metas.ui.web.calendar.json.JsonSimulationCreateRequest;
import de.metas.ui.web.calendar.json.JsonSimulationRef;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import io.swagger.annotations.Api;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_Resource_Group;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Api
@RestController
@RequestMapping(WebConfig.ENDPOINT_ROOT + "/calendars")
public class CalendarRestController
{
	private final UserSession userSession;
	private final MultiCalendarService calendarService;
	private final SimulationPlanService simulationService;

	private final LookupDataSource bpartnerLookup;
	private final LookupDataSource resourceLookup;
	private final LookupDataSource resourceGroupLookup;
	private final LookupDataSource projectLookup;

	public CalendarRestController(
			@NonNull final UserSession userSession,
			@NonNull final MultiCalendarService calendarService,
			@NonNull final SimulationPlanService simulationService)
	{
		this.userSession = userSession;
		this.calendarService = calendarService;
		this.simulationService = simulationService;

		this.bpartnerLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
		this.resourceLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_S_Resource.Table_Name);
		this.resourceGroupLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_S_Resource_Group.Table_Name);
		this.projectLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
	}

	@GetMapping("/available")
	public JsonGetAvailableCalendarsResponse getAvailableCalendars()
	{
		userSession.assertLoggedIn();
		final UserId loggedUserId = userSession.getLoggedUserId();
		final String adLanguage = userSession.getAD_Language();

		final ImmutableList<JsonCalendarRef> jsonCalendars = calendarService.streamAvailableCalendars(loggedUserId)
				.map(calendarRef -> JsonCalendarRef.of(calendarRef, adLanguage))
				.collect(ImmutableList.toImmutableList());

		return JsonGetAvailableCalendarsResponse.builder()
				.calendars(jsonCalendars)
				.build();
	}

	@PostMapping("/queryEntries")
	public JsonCalendarEntriesQueryResponse queryEntries(
			@RequestBody(required = false) @Nullable final JsonCalendarEntriesQuery request)
	{
		userSession.assertLoggedIn();

		final ZoneId timeZone = userSession.getTimeZone();
		final String adLanguage = userSession.getAD_Language();

		final CalendarQuery query = toCalendarQuery(request);
		final ImmutableList<JsonCalendarEntry> jsonEntries = calendarService.query(query)
				.map(entry -> JsonCalendarEntry.of(entry, timeZone, adLanguage))
				.collect(ImmutableList.toImmutableList());

		return JsonCalendarEntriesQueryResponse.builder()
				.query(toResponseResolvedQuery(query, adLanguage))
				.entries(jsonEntries)
				.build();
	}

	private static CalendarQuery toCalendarQuery(@Nullable final JsonCalendarEntriesQuery query)
	{
		final CalendarQuery.CalendarQueryBuilder result = CalendarQuery.builder();

		if (query != null)
		{
			result.simulationId(query.getSimulationId());
			result.onlyCalendarIds(query.getCalendarIds());

			if (query.getStartDate() != null)
			{
				result.startDate(query.getStartDate().toInstant());
			}

			if (query.getEndDate() != null)
			{
				result.endDate(query.getEndDate().toInstant());
			}

			final Set<CalendarResourceId> onlyResourceIds = query.getOnlyResourceIds();
			result.resourceIds(onlyResourceIds != null && !onlyResourceIds.isEmpty()
					? InSetPredicate.only(onlyResourceIds)
					: InSetPredicate.any());

			result.onlyProjectId(query.getOnlyProjectId());

			result.onlyCustomerId(query.getOnlyCustomerId());
		}

		return result.build();
	}

	private JsonCalendarEntriesQueryResponse.ResolvedQuery toResponseResolvedQuery(@NonNull final CalendarQuery query, @NonNull final String adLanguage)
	{
		return JsonCalendarEntriesQueryResponse.ResolvedQuery.builder()
				.simulationId(query.getSimulationId())
				.onlyResources(toJSONLookupValueFromResourceIds(query.getResourceIds(), adLanguage))
				.onlyProject(JSONLookupValue.ofNullableLookupValue(projectLookup.findById(query.getOnlyProjectId()), adLanguage))
				.onlyCustomer(JSONLookupValue.ofNullableLookupValue(bpartnerLookup.findById(query.getOnlyCustomerId()), adLanguage))
				.build();
	}

	private List<JSONLookupValue> toJSONLookupValueFromResourceIds(@Nullable final InSetPredicate<CalendarResourceId> resourceIds, @NonNull final String adLanguage)
	{
		if (resourceIds == null || resourceIds.isAny())
		{
			return null;
		}

		return resourceIds.toSet()
				.stream()
				.map(calendarResourceId -> toJSONLookupValue(calendarResourceId, adLanguage))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private JSONLookupValue toJSONLookupValue(@NonNull final CalendarResourceId calendarResourceId, @NonNull final String adLanguage)
	{
		final ResourceId resourceId = calendarResourceId.toRepoIdOrNull(ResourceId.class);
		if (resourceId != null)
		{
			return JSONLookupValue.ofNullableLookupValue(resourceLookup.findById(resourceId), adLanguage);
		}

		final ResourceGroupId resourceGroupId = calendarResourceId.toRepoIdOrNull(ResourceGroupId.class);
		if (resourceGroupId != null)
		{
			return JSONLookupValue.ofNullableLookupValue(resourceGroupLookup.findById(resourceGroupId), adLanguage);
		}

		return null;
	}

	@PostMapping("/entries/add")
	public JsonCalendarEntryUpdateResult addCalendarEntry(@RequestBody @NonNull final JsonCalendarEntryAddRequest request)
	{
		userSession.assertLoggedIn();

		final CalendarEntry calendarEntry = calendarService.addEntry(CalendarEntryAddRequest.builder()
				.userId(userSession.getLoggedUserId())
				.simulationId(request.getSimulationId())
				.calendarId(request.getCalendarId())
				.resourceId(request.getResourceId())
				.title(request.getTitle())
				.description(request.getDescription())
				.dateRange(CalendarDateRange.builder()
						.startDate(request.getStartDate().toInstant())
						.endDate(request.getEndDate().toInstant())
						.allDay(request.isAllDay())
						.build())
				.build());

		return JsonCalendarEntryUpdateResult.ofChangedEntry(calendarEntry, userSession.getTimeZone(), userSession.getAD_Language());
	}

	@PostMapping("/entries/{entryId}")
	public JsonCalendarEntryUpdateResult updateCalendarEntry(
			@PathVariable("entryId") @NonNull final String entryIdStr,
			@RequestBody @NonNull final JsonCalendarEntryUpdateRequest request)
	{
		userSession.assertLoggedIn();

		final CalendarEntryUpdateResult result = calendarService.updateEntry(CalendarEntryUpdateRequest.builder()
				.entryId(CalendarEntryId.ofString(entryIdStr))
				.simulationId(request.getSimulationId())
				.updatedByUserId(userSession.getLoggedUserId())
				.resourceId(request.getResourceId())
				.title(request.getTitle())
				.description(request.getDescription())
				.dateRange(extractDateRange(request))
				.build());

		return JsonCalendarEntryUpdateResult.of(result, userSession.getTimeZone(), userSession.getAD_Language());
	}

	private static CalendarDateRange extractDateRange(final JsonCalendarEntryUpdateRequest request)
	{
		final JsonDateTime startDate = request.getStartDate();
		final JsonDateTime endDate = request.getEndDate();
		final Boolean isAllDay = request.getIsAllDay();
		if (startDate != null && endDate != null && isAllDay != null)
		{
			return CalendarDateRange.builder()
					.startDate(startDate.toInstant())
					.endDate(endDate.toInstant())
					.allDay(isAllDay)
					.build();
		}
		else if (CoalesceUtil.countNotNulls(startDate, endDate, isAllDay) > 0)
		{
			throw new AdempiereException("Please specify startDate, endDate and isAllDay or don't specify any of those");
		}
		else
		{
			return null;
		}
	}

	@DeleteMapping("/entries/{entryId}")
	public void deleteCalendarEntry(
			@PathVariable("entryId") @NonNull final String entryIdStr,
			@RequestParam(name = "simulationId", required = false) final String simulationIdStr)
	{
		userSession.assertLoggedIn();
		calendarService.deleteEntryById(
				CalendarEntryId.ofString(entryIdStr),
				SimulationPlanId.ofNullableObject(simulationIdStr));
	}

	@PostMapping("/simulations/new")
	public JsonSimulationRef createNewSimulation(@RequestBody @NonNull final JsonSimulationCreateRequest request)
	{
		userSession.assertLoggedIn();

		final SimulationPlanRef simulationRef = simulationService.createNewSimulation(
				request.getName(),
				request.getCopyFromSimulationId(),
				userSession.getLoggedUserId());

		return JsonSimulationRef.of(simulationRef);
	}

	@GetMapping("/simulations")
	public JsonGetAvailableSimulationsResponse getDraftSimulations(
			@RequestParam(name = "alwaysIncludeId", required = false) final String alwaysIncludeIdStr
	)
	{
		userSession.assertLoggedIn();

		final SimulationPlanId alwaysIncludeId = SimulationPlanId.ofNullableObject(alwaysIncludeIdStr);

		return JsonGetAvailableSimulationsResponse.builder()
				.simulations(
						simulationService.getAllDrafts(alwaysIncludeId)
								.stream()
								.map(JsonSimulationRef::of)
								.sorted(Comparator.comparing(JsonSimulationRef::getName))
								.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@GetMapping("/queryConflicts")
	@PostMapping("/queryConflicts")
	public JsonCalendarConflictsQueryResponse queryConflicts(
			@RequestParam(name = "simulationId", required = false) final String simulationIdStr,
			@RequestParam(name = "onlyResourceIds", required = false) final String onlyResourceIdsStr)
	{
		userSession.assertLoggedIn();

		final CalendarConflictsQuery query = CalendarConflictsQuery.builder()
				.simulationId(SimulationPlanId.ofNullableObject(simulationIdStr))
				.resourceIds(CalendarResourceId.ofCommaSeparatedString(onlyResourceIdsStr)
						.map(InSetPredicate::only)
						.orElse(InSetPredicate.any()))
				.build();

		final ImmutableList<JsonCalendarConflict> jsonConflicts = calendarService.getConflicts(query)
				.stream()
				.map(JsonCalendarConflict::of)
				.collect(ImmutableList.toImmutableList());

		return JsonCalendarConflictsQueryResponse.builder()
				.conflicts(jsonConflicts)
				.build();
	}

}
