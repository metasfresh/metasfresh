package de.metas.ui.web.dashboard;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.IESSystem;
import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.UserDashboardRepository.DashboardItemPatchPath;
import de.metas.ui.web.dashboard.UserDashboardRepository.UserDashboardKey;
import de.metas.ui.web.dashboard.json.JSONDashboard;
import de.metas.ui.web.dashboard.json.JSONDashboardChangedEventsList;
import de.metas.ui.web.dashboard.json.JSONDashboardChangedEventsList.JSONDashboardChangedEventsListBuilder;
import de.metas.ui.web.dashboard.json.JSONDashboardItem;
import de.metas.ui.web.dashboard.json.JSONDashboardItemChangedEvent;
import de.metas.ui.web.dashboard.json.JSONDashboardOrderChangedEvent;
import de.metas.ui.web.dashboard.json.JsonKPI;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONPatchEvent;
import de.metas.util.Services;
import io.swagger.annotations.ApiParam;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@RestController
@RequestMapping(value = DashboardRestController.ENDPOINT)
public class DashboardRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/dashboard";

	private static final Logger logger = LogManager.getLogger(DashboardRestController.class);
	@Autowired
	private UserSession userSession;
	@Autowired
	private UserDashboardRepository userDashboardRepo;
	@Autowired
	private Client elasticsearchClient;
	@Autowired
	private WebsocketSender websocketSender;

	private JSONOptions newJSONOpts()
	{
		return JSONOptions.of(userSession);
	}

	private JSONDocumentLayoutOptions newJSONLayoutOptions()
	{
		return JSONDocumentLayoutOptions.of(userSession);
	}

	private UserDashboard getUserDashboardForReading()
	{
		if (!isElasticSearchEnabled())
		{
			return UserDashboard.EMPTY;
		}

		final UserDashboard dashboard = userDashboardRepo.getUserDashboard(UserDashboardKey.of(userSession.getClientId()));
		// TODO: assert readable by current user
		return dashboard;
	}

	private boolean isElasticSearchEnabled()
	{
		return Services.get(IESSystem.class).isEnabled();
	}

	private UserDashboard getUserDashboardForWriting()
	{
		if (!isElasticSearchEnabled())
		{
			return UserDashboard.EMPTY;
		}

		final UserDashboard dashboard = userDashboardRepo.getUserDashboard(UserDashboardKey.of(userSession.getClientId()));
		// TODO: assert writable by current user
		return dashboard;
	}

	private void sendEvents(final UserDashboard dashboard, final JSONDashboardChangedEventsList events)
	{
		if (events.isEmpty())
		{
			return;
		}

		final WebsocketTopicName websocketEndpoint = dashboard.getWebsocketEndpoint();
		websocketSender.convertAndSend(websocketEndpoint, events);
		logger.trace("Notified WS {}: {}", websocketEndpoint, events);
	}

	@GetMapping("/kpis")
	public JSONDashboard getKPIsDashboard()
	{
		return getJSONDashboard(DashboardWidgetType.KPI);
	}

	@GetMapping("/targetIndicators")
	public JSONDashboard getTargetIndicatorsDashboard()
	{
		return getJSONDashboard(DashboardWidgetType.TargetIndicator);
	}

	public JSONDashboard getJSONDashboard(final DashboardWidgetType widgetType)
	{
		userSession.assertLoggedIn();

		final UserDashboard userDashboard = getUserDashboardForReading();
		final WebsocketTopicName websocketEndpoint = userDashboard.getWebsocketEndpoint();
		return JSONDashboard.of(
				userDashboard.getItems(widgetType),
				websocketEndpoint != null ? websocketEndpoint.getAsString() : null,
				newJSONLayoutOptions());
	}

	@GetMapping("/kpis/available")
	public List<JsonKPI> getKPIsAvailableToAdd(
			@RequestParam(name = "firstRow", required = false, defaultValue = "0") final int firstRow,
			@RequestParam(name = "pageLength", required = false, defaultValue = "0") final int pageLength)
	{
		userSession.assertLoggedIn();

		final Collection<KPI> kpis = userDashboardRepo.getKPIsAvailableToAdd();

		final JSONOptions jsonOpts = newJSONOpts();
		return kpis.stream()
				.map(kpi -> JsonKPI.of(kpi, jsonOpts))
				.sorted(Comparator.comparing(JsonKPI::getCaption))
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : Integer.MAX_VALUE)
				.collect(ImmutableList.toImmutableList());
	}

	@PostMapping("/kpis/new")
	public JSONDashboardItem addKPIItem(@RequestBody final JsonUserDashboardItemAddRequest jsonRequest)
	{
		return addDashboardItem(jsonRequest, DashboardWidgetType.KPI);
	}

	@PostMapping("/targetIndicators/new")
	public JSONDashboardItem addTargetIndicatorItem(@RequestBody final JsonUserDashboardItemAddRequest jsonRequest)
	{
		return addDashboardItem(jsonRequest, DashboardWidgetType.TargetIndicator);
	}

	private final JSONDashboardItem addDashboardItem(final JsonUserDashboardItemAddRequest jsonRequest, final DashboardWidgetType widgetType)
	{
		userSession.assertLoggedIn();

		final UserDashboardItemAddRequest request = UserDashboardItemAddRequest.of(jsonRequest, widgetType, userSession.getAD_Language());
		final int itemId = userDashboardRepo.addUserDashboardItem(getUserDashboardForWriting(), request);

		//
		// Notify on websocket
		final UserDashboard dashboard = getUserDashboardForReading();
		sendEvents(dashboard, JSONDashboardChangedEventsList.builder()
				.event(JSONDashboardOrderChangedEvent.of(dashboard.getId(), widgetType, dashboard.getItemIds(widgetType)))
				.build());

		// Return newly created item
		final UserDashboardItem targetIndicatorItem = dashboard.getItemById(widgetType, itemId);
		return JSONDashboardItem.of(targetIndicatorItem, newJSONLayoutOptions());
	}

	@GetMapping("/kpis/{itemId}/data")
	public KPIDataResult getKPIData( //
			@PathVariable final int itemId //
			, @RequestParam(name = "fromMillis", required = false, defaultValue = "0") @ApiParam("interval rage start, in case of temporal data") final long fromMillis //
			, @RequestParam(name = "toMillis", required = false, defaultValue = "0") @ApiParam("interval rage end, in case of temporal data") final long toMillis //
			, @RequestParam(name = "prettyValues", required = false, defaultValue = "true") @ApiParam("if true, the server will format the values") final boolean prettyValues //
	)
	{
		return getKPIData(DashboardWidgetType.KPI, itemId, fromMillis, toMillis, prettyValues);
	}

	@GetMapping("/targetIndicators/{itemId}/data")
	public KPIDataResult getTargetIndicatorData( //
			@PathVariable final int itemId //
			, @RequestParam(name = "fromMillis", required = false, defaultValue = "0") @ApiParam("interval rage start, in case of temporal data") final long fromMillis //
			, @RequestParam(name = "toMillis", required = false, defaultValue = "0") @ApiParam("interval rage end, in case of temporal data") final long toMillis //
			, @RequestParam(name = "prettyValues", required = false, defaultValue = "true") @ApiParam("if true, the server will format the values") final boolean prettyValues //
	)
	{
		return getKPIData(DashboardWidgetType.TargetIndicator, itemId, fromMillis, toMillis, prettyValues);
	}

	private final KPIDataResult getKPIData(final DashboardWidgetType widgetType, final int itemId, final long fromMillis, final long toMillis, final boolean prettyValues)
	{
		userSession.assertLoggedIn();

		final UserDashboardItem dashboardItem = getUserDashboardForReading()
				.getItemById(widgetType, itemId);

		final KPI kpi = dashboardItem.getKPI();
		final TimeRange timeRange = dashboardItem.getTimeRangeDefaults().createTimeRange(fromMillis, toMillis);

		final JSONOptions jsonOptions = JSONOptions.of(userSession);
		return KPIDataLoader.newInstance(elasticsearchClient, kpi, jsonOptions)
				.setTimeRange(timeRange)
				.setFormatValues(prettyValues)
				.retrieveData()
				.setItemId(dashboardItem.getId());
	}

	@DeleteMapping("/kpis/{itemId}")
	public void deleteKPIItem(@PathVariable("itemId") final int itemId)
	{
		deleteDashboardItem(DashboardWidgetType.KPI, itemId);
	}

	@DeleteMapping("/targetIndicators/{itemId}")
	public void deleteTargetIndicatorItem(@PathVariable("itemId") final int itemId)
	{
		deleteDashboardItem(DashboardWidgetType.TargetIndicator, itemId);
	}

	private void deleteDashboardItem(final DashboardWidgetType widgetType, final int itemId)
	{
		userSession.assertLoggedIn();

		userDashboardRepo.deleteUserDashboardItem(getUserDashboardForWriting(), widgetType, itemId);

		//
		// Notify on websocket
		final UserDashboard dashboard = getUserDashboardForReading();
		sendEvents(dashboard, JSONDashboardChangedEventsList.builder()
				.event(JSONDashboardOrderChangedEvent.of(dashboard.getId(), widgetType, dashboard.getItemIds(widgetType)))
				.build());
	}

	@PatchMapping("/kpis/{itemId}")
	public JSONDashboardItem changeKPIItem(@PathVariable("itemId") final int itemId, @RequestBody final List<JSONPatchEvent<DashboardItemPatchPath>> events)
	{
		return changeDashboardItem(DashboardWidgetType.KPI, itemId, events);
	}

	@PatchMapping("/targetIndicators/{itemId}")
	public JSONDashboardItem changeTargetIndicatorItem(@PathVariable("itemId") final int itemId, @RequestBody final List<JSONPatchEvent<DashboardItemPatchPath>> events)
	{
		return changeDashboardItem(DashboardWidgetType.TargetIndicator, itemId, events);
	}

	private final JSONDashboardItem changeDashboardItem(final DashboardWidgetType widgetType, final int itemId, final List<JSONPatchEvent<DashboardItemPatchPath>> events)
	{
		userSession.assertLoggedIn();

		//
		// Chage the dashboard item
		final UserDashboardItemChangeRequest request = UserDashboardItemChangeRequest.of(widgetType, itemId, userSession.getAD_Language(), events);
		final UserDashboardItemChangeResult changeResult = userDashboardRepo.changeUserDashboardItem(getUserDashboardForWriting(), request);

		//
		// Notify on websocket
		final UserDashboard dashboard = getUserDashboardForReading();
		{
			final JSONDashboardChangedEventsListBuilder eventBuilder = JSONDashboardChangedEventsList.builder()
					.event(JSONDashboardItemChangedEvent.of(changeResult.getDashboardId(), changeResult.getItemId()));

			if (changeResult.isPositionChanged())
			{
				eventBuilder.event(JSONDashboardOrderChangedEvent.of(changeResult.getDashboardId(), changeResult.getDashboardWidgetType(), changeResult.getDashboardOrderedItemIds()));
			}

			sendEvents(dashboard, eventBuilder.build());
		}

		// Return the changed item
		{
			final UserDashboardItem item = dashboard.getItemById(widgetType, itemId);
			return JSONDashboardItem.of(item, newJSONLayoutOptions());
		}
	}
}
