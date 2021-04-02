package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.IESSystem;
import de.metas.i18n.ExplainedOptional;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.UserDashboardRepository.DashboardItemPatchPath;
import de.metas.ui.web.dashboard.UserDashboardRepository.UserDashboardKey;
import de.metas.ui.web.dashboard.json.JSONDashboard;
import de.metas.ui.web.dashboard.json.JSONDashboardItem;
import de.metas.ui.web.dashboard.json.JsonKPI;
import de.metas.ui.web.dashboard.json.JsonKPIDataResult;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest;
import de.metas.ui.web.dashboard.json.KPIJsonOptions;
import de.metas.ui.web.dashboard.websocket.UserDashboardWebsocketSender;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONPatchEvent;
import de.metas.util.Services;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

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
@RequestMapping(WebConfig.ENDPOINT_ROOT + "/dashboard")
public class DashboardRestController
{
	private final IESSystem esSystem = Services.get(IESSystem.class);
	private final UserSession userSession;
	private final UserDashboardRepository dashboardRepo;
	private final UserDashboardDataService dashboardDataService;
	private final UserDashboardWebsocketSender websocketSender;

	public DashboardRestController(
			@NonNull final UserSession userSession,
			@NonNull final UserDashboardRepository dashboardRepo,
			@NonNull final UserDashboardDataService dashboardDataService,
			@NonNull final WebsocketSender websocketSender)
	{
		this.userSession = userSession;
		this.dashboardRepo = dashboardRepo;
		this.dashboardDataService = dashboardDataService;
		this.websocketSender = new UserDashboardWebsocketSender(websocketSender);
	}

	private JSONOptions newJSONOpts()
	{
		return JSONOptions.of(userSession);
	}

	private JSONDocumentLayoutOptions newJSONLayoutOptions()
	{
		return JSONDocumentLayoutOptions.of(userSession);
	}

	private boolean isElasticSearchEnabled()
	{
		return esSystem.getEnabled().isTrue();
	}

	private ExplainedOptional<UserDashboard> getUserDashboard()
	{
		if (!isElasticSearchEnabled())
		{
			return ExplainedOptional.emptyBecause("Elasticsearch feature is not active");
		}
		else
		{
			// TODO: assert readable by current user
			final UserDashboard userDashboard = dashboardRepo.getUserDashboard(UserDashboardKey.of(userSession.getClientId())).orElse(null);
			if (userDashboard == null)
			{
				return ExplainedOptional.emptyBecause("User has no dashboard");
			}
			else
			{
				return ExplainedOptional.of(userDashboard);
			}
		}
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

		return getUserDashboard()
				.map(dashboard -> toJSONDashboard(dashboard, widgetType))
				.orElse(JSONDashboard.EMPTY);
	}

	private JSONDashboard toJSONDashboard(@NonNull final UserDashboard userDashboard, @NonNull final DashboardWidgetType widgetType)
	{
		final WebsocketTopicName websocketEndpoint = userDashboard.getWebsocketEndpoint();
		return JSONDashboard.builder()
				.jsonOpts(newJSONLayoutOptions())
				.items(userDashboard.getItems(widgetType))
				.websocketEndpoint(websocketEndpoint != null ? websocketEndpoint.getAsString() : null)
				.build();
	}

	@GetMapping("/kpis/available")
	public List<JsonKPI> getKPIsAvailableToAdd(
			@RequestParam(name = "firstRow", required = false, defaultValue = "0") final int firstRow,
			@RequestParam(name = "pageLength", required = false, defaultValue = "0") final int pageLength)
	{
		userSession.assertLoggedIn();

		final Collection<KPI> kpis = dashboardRepo.getKPIsAvailableToAdd();

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

	private JSONDashboardItem addDashboardItem(final JsonUserDashboardItemAddRequest jsonRequest, final DashboardWidgetType widgetType)
	{
		userSession.assertLoggedIn();

		final UserDashboardItemAddRequest request = UserDashboardItemAddRequest.of(jsonRequest, widgetType, userSession.getAD_Language());
		final UserDashboardItemId itemId = dashboardRepo.addUserDashboardItem(getUserDashboard().get(), request);

		//
		// Notify on websocket
		final UserDashboard dashboard = getUserDashboard().get();
		websocketSender.sendDashboardItemsOrderChangedEvent(dashboard, widgetType);

		// Return newly created item
		final UserDashboardItem targetIndicatorItem = dashboard.getItemById(widgetType, itemId);
		return JSONDashboardItem.of(targetIndicatorItem, newJSONLayoutOptions());
	}

	@GetMapping("/kpis/{itemId}/data")
	public JsonKPIDataResult getKPIData( //
										 @PathVariable final int itemId //
			, @RequestParam(name = "fromMillis", required = false, defaultValue = "0") @ApiParam("interval rage start, in case ofValueAndField temporal data") final long fromMillis //
			, @RequestParam(name = "toMillis", required = false, defaultValue = "0") @ApiParam("interval rage end, in case ofValueAndField temporal data") final long toMillis //
			, @RequestParam(name = "prettyValues", required = false, defaultValue = "true") @ApiParam("if true, the server will format the values") final boolean prettyValues //
	)
	{
		return getKPIData(
				DashboardWidgetType.KPI,
				UserDashboardItemId.ofRepoId(itemId),
				fromMillis > 0 ? Instant.ofEpochMilli(fromMillis) : null,
				toMillis > 0 ? Instant.ofEpochMilli(toMillis) : null,
				prettyValues);
	}

	@GetMapping("/targetIndicators/{itemId}/data")
	public JsonKPIDataResult getTargetIndicatorData( //
													 @PathVariable final int itemId //
			, @RequestParam(name = "fromMillis", required = false, defaultValue = "0") @ApiParam("interval rage start, in case ofValueAndField temporal data") final long fromMillis //
			, @RequestParam(name = "toMillis", required = false, defaultValue = "0") @ApiParam("interval rage end, in case ofValueAndField temporal data") final long toMillis //
			, @RequestParam(name = "prettyValues", required = false, defaultValue = "true") @ApiParam("if true, the server will format the values") final boolean prettyValues //
	)
	{
		return getKPIData(
				DashboardWidgetType.TargetIndicator,
				UserDashboardItemId.ofRepoId(itemId),
				fromMillis > 0 ? Instant.ofEpochMilli(fromMillis) : null,
				toMillis > 0 ? Instant.ofEpochMilli(toMillis) : null,
				prettyValues);
	}

	private JsonKPIDataResult getKPIData(
			@NonNull final DashboardWidgetType widgetType,
			@NonNull final UserDashboardItemId itemId,
			@Nullable final Instant from,
			@Nullable final Instant to,
			final boolean prettyValues)
	{
		userSession.assertLoggedIn();

		final UserDashboardId dashboardId = getUserDashboard().get().getId();
		final KPIDataResult result = dashboardDataService.getData(dashboardId)
				.getItemData(UserDashboardItemDataRequest.builder()
						.widgetType(widgetType)
						.itemId(itemId)
						.from(from)
						.to(to)
						.build());

		return JsonKPIDataResult.of(
				result,
				itemId,
				newKPIJsonOptions()
						.prettyValues(prettyValues)
						.build());
	}

	private KPIJsonOptions.KPIJsonOptionsBuilder newKPIJsonOptions()
	{
		final JSONOptions jsonOptions = JSONOptions.of(userSession);
		return KPIJsonOptions.builder()
				.adLanguage(jsonOptions.getAdLanguage())
				.zoneId(jsonOptions.getZoneId());
	}

	@DeleteMapping("/kpis/{itemId}")
	public void deleteKPIItem(@PathVariable("itemId") final int itemId)
	{
		deleteDashboardItem(DashboardWidgetType.KPI, UserDashboardItemId.ofRepoId(itemId));
	}

	@DeleteMapping("/targetIndicators/{itemId}")
	public void deleteTargetIndicatorItem(@PathVariable("itemId") final int itemId)
	{
		deleteDashboardItem(DashboardWidgetType.TargetIndicator, UserDashboardItemId.ofRepoId(itemId));
	}

	private void deleteDashboardItem(final DashboardWidgetType widgetType, final UserDashboardItemId itemId)
	{
		userSession.assertLoggedIn();

		dashboardRepo.deleteUserDashboardItem(getUserDashboard().get(), widgetType, itemId);

		websocketSender.sendDashboardItemsOrderChangedEvent(
				getUserDashboard().get(),
				widgetType);
	}

	@PatchMapping("/kpis/{itemId}")
	public JSONDashboardItem changeKPIItem(@PathVariable("itemId") final int itemId, @RequestBody final List<JSONPatchEvent<DashboardItemPatchPath>> events)
	{
		return changeDashboardItem(DashboardWidgetType.KPI, UserDashboardItemId.ofRepoId(itemId), events);
	}

	@PatchMapping("/targetIndicators/{itemId}")
	public JSONDashboardItem changeTargetIndicatorItem(@PathVariable("itemId") final int itemId, @RequestBody final List<JSONPatchEvent<DashboardItemPatchPath>> events)
	{
		return changeDashboardItem(DashboardWidgetType.TargetIndicator, UserDashboardItemId.ofRepoId(itemId), events);
	}

	private JSONDashboardItem changeDashboardItem(
			final DashboardWidgetType widgetType,
			final UserDashboardItemId itemId,
			final List<JSONPatchEvent<DashboardItemPatchPath>> events)
	{
		userSession.assertLoggedIn();

		//
		// Change the dashboard item
		final UserDashboardItemChangeRequest request = UserDashboardItemChangeRequest.of(widgetType, itemId, userSession.getAD_Language(), events);
		final UserDashboardItemChangeResult changeResult = dashboardRepo.changeUserDashboardItem(getUserDashboard().get(), request);

		//
		// Notify on websocket
		final UserDashboard dashboard = getUserDashboard().get();
		websocketSender.sendDashboardItemChangedEvent(dashboard, changeResult);

		// Return the changed item
		{
			final UserDashboardItem item = dashboard.getItemById(widgetType, itemId);
			return JSONDashboardItem.of(item, newJSONLayoutOptions());
		}
	}
}
