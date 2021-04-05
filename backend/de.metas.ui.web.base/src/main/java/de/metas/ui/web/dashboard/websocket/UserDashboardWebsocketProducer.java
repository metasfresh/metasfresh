/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.dashboard.websocket;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.logging.LogManager;
import de.metas.ui.web.dashboard.UserDashboardDataRequest;
import de.metas.ui.web.dashboard.UserDashboardDataResponse;
import de.metas.ui.web.dashboard.UserDashboardDataService;
import de.metas.ui.web.dashboard.UserDashboardId;
import de.metas.ui.web.dashboard.UserDashboardItemDataResponse;
import de.metas.ui.web.dashboard.UserDashboardItemId;
import de.metas.ui.web.dashboard.json.JsonKPIDataResult;
import de.metas.ui.web.dashboard.json.KPIJsonOptions;
import de.metas.ui.web.dashboard.websocket.json.JSONDashboardChangedEventsList;
import de.metas.ui.web.dashboard.websocket.json.JSONDashboardItemDataChangedEvent;
import de.metas.ui.web.websocket.WebSocketProducer;
import de.metas.ui.web.websocket.WebsocketSubscriptionId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.SynchronizedMutable;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@ToString(onlyExplicitlyIncluded = true)
class UserDashboardWebsocketProducer implements WebSocketProducer
{
	private static final Logger logger = LogManager.getLogger(UserDashboardWebsocketProducer.class);
	private final UserDashboardDataService dashboardDataService;

	@ToString.Include
	private final UserDashboardId userDashboardId;

	private final SynchronizedMutable<Result> lastResultHolder = SynchronizedMutable.of(null);

	@Builder
	private UserDashboardWebsocketProducer(
			@NonNull final UserDashboardDataService dashboardDataService,
			@NonNull final UserDashboardId userDashboardId)
	{
		this.dashboardDataService = dashboardDataService;

		this.userDashboardId = userDashboardId;
	}

	@Override
	public void onNewSubscription(final @NonNull WebsocketSubscriptionId subscriptionId)
	{
		lastResultHolder.setValue(null);
		logger.trace("Last result reset because of new subscription({})", subscriptionId);
	}

	@Override
	public ImmutableList<JSONDashboardChangedEventsList> produceEvents(final JSONOptions jsonOpts)
	{
		final Result newResult = computeNewResult();
		final Result oldResult = lastResultHolder.setValueAndReturnPrevious(newResult);
		final ImmutableList<UserDashboardItemDataResponse> changesFromOldVersion = newResult.getChangesFromOldVersion(oldResult);
		final ImmutableList<JSONDashboardItemDataChangedEvent> events = toJson(changesFromOldVersion, jsonOpts);

		logger.trace("{}.produceEvents: New Result: {}", this, newResult);
		logger.trace("{}.produceEvents: Old Result: {}", this, oldResult);
		logger.trace("{}.produceEvents: Returning changes from old version: {}", this, events);

		if(events.isEmpty())
		{
			return  ImmutableList.of();
		}
		else
		{
			return ImmutableList.of(JSONDashboardChangedEventsList.builder()
					.events(events)
					.build());
		}
	}

	private ImmutableList<JSONDashboardItemDataChangedEvent> toJson(final List<UserDashboardItemDataResponse> itemDataList, final JSONOptions jsonOpts)
	{
		if (itemDataList.isEmpty())
		{
			return ImmutableList.of();
		}

		final KPIJsonOptions kpiJsonOpts = newKpiJsonOptions(jsonOpts);
		return itemDataList.stream().map(itemData -> toJson(itemData, kpiJsonOpts)).collect(ImmutableList.toImmutableList());
	}

	private JSONDashboardItemDataChangedEvent toJson(final UserDashboardItemDataResponse itemData, final KPIJsonOptions jsonOpts)
	{
		final JsonKPIDataResult jsonData = JsonKPIDataResult.of(itemData, jsonOpts);
		return JSONDashboardItemDataChangedEvent.of(userDashboardId, itemData.getItemId(), jsonData);
	}

	private Result computeNewResult()
	{
		final UserDashboardDataResponse data = dashboardDataService
				.getData(userDashboardId)
				.getAllItems(UserDashboardDataRequest.NOW);

		return Result.ofCollection(data.getItems());
	}

	private static KPIJsonOptions newKpiJsonOptions(final JSONOptions jsonOpts)
	{
		return KPIJsonOptions.builder()
				.adLanguage(jsonOpts.getAdLanguage())
				.zoneId(jsonOpts.getZoneId())
				.prettyValues(true)
				.build();
	}

	//
	//
	//
	//
	//

	@ToString
	private static class Result
	{
		public static Result ofMap(@NonNull final Map<UserDashboardItemId, UserDashboardItemDataResponse> map)
		{
			return !map.isEmpty() ? new Result(map) : EMPTY;
		}

		public static Result ofCollection(@NonNull final Collection<UserDashboardItemDataResponse> itemDataList)
		{
			return ofMap(Maps.uniqueIndex(itemDataList, UserDashboardItemDataResponse::getItemId));
		}

		private static final Result EMPTY = new Result(ImmutableMap.of());

		private final ImmutableMap<UserDashboardItemId, UserDashboardItemDataResponse> map;

		private Result(final Map<UserDashboardItemId, UserDashboardItemDataResponse> map)
		{
			this.map = ImmutableMap.copyOf(map);
		}

		public ImmutableList<UserDashboardItemDataResponse> getChangesFromOldVersion(@Nullable final Result oldResult)
		{
			if (oldResult == null)
			{
				return toList();
			}

			final ImmutableList.Builder<UserDashboardItemDataResponse> resultEffective = ImmutableList.builder();
			for (final Map.Entry<UserDashboardItemId, UserDashboardItemDataResponse> e : map.entrySet())
			{
				final UserDashboardItemId itemId = e.getKey();
				final UserDashboardItemDataResponse newValue = e.getValue();
				final UserDashboardItemDataResponse oldValue = oldResult.get(itemId);
				if (oldValue == null || !newValue.isSameDataAs(oldValue))
				{
					resultEffective.add(newValue);
				}
			}

			return resultEffective.build();
		}

		@Nullable
		private UserDashboardItemDataResponse get(final UserDashboardItemId itemId)
		{
			return map.get(itemId);
		}

		public ImmutableList<UserDashboardItemDataResponse> toList()
		{
			return ImmutableList.copyOf(map.values());
		}
	}
}
