package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.UserDashboardRepository.DashboardItemPatchPath;
import de.metas.ui.web.dashboard.UserDashboardRepository.UserDashboardKey;
import de.metas.ui.web.dashboard.json.JSONDashboard;
import de.metas.ui.web.dashboard.json.JSONDashboardItem;
import de.metas.ui.web.dashboard.json.JsonKPI;
import de.metas.ui.web.dashboard.json.JsonKPIDataResult;
import de.metas.ui.web.dashboard.json.JsonKPIZoomInfoDetails;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest;
import de.metas.ui.web.dashboard.json.KPIJsonOptions;
import de.metas.ui.web.dashboard.websocket.UserDashboardWebsocketProducerFactory;
import de.metas.ui.web.dashboard.websocket.UserDashboardWebsocketSender;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.kpi.data.KPIDataResult;
import de.metas.ui.web.kpi.data.KPIZoomIntoDetailsInfo;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONPatchEvent;
import de.metas.websocket.producers.WebSocketProducersRegistry;
import de.metas.websocket.sender.WebsocketSender;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
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
import java.util.Objects;

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
	private static final Logger logger = LogManager.getLogger(DashboardRestController.class);
	private final UserSession userSession;
	private final UserDashboardRepository dashboardRepo;
	private final UserDashboardDataService dashboardDataService;
	private final UserDashboardWebsocketSender websocketSender;
	private final IViewsRepository viewRepo;

	public DashboardRestController(
			@NonNull final UserSession userSession,
			@NonNull final UserDashboardRepository dashboardRepo,
			@NonNull final UserDashboardDataService dashboardDataService,
			@NonNull final WebSocketProducersRegistry websocketProducersRegistry,
			@NonNull final WebsocketSender websocketSender,
			@NonNull final IViewsRepository viewRepo)
	{
		this.userSession = userSession;
		this.dashboardRepo = dashboardRepo;
		this.dashboardDataService = dashboardDataService;
		this.viewRepo = viewRepo;
		this.websocketSender = new UserDashboardWebsocketSender(websocketSender, websocketProducersRegistry);
	}

	private ExplainedOptional<UserDashboard> getUserDashboard()
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

	@GetMapping("/kpis")
	public JSONDashboard getKPIsDashboard() {return getJSONDashboard(DashboardWidgetType.KPI);}

	@GetMapping("/targetIndicators")
	public JSONDashboard getTargetIndicatorsDashboard() {return getJSONDashboard(DashboardWidgetType.TargetIndicator);}

	private JSONDashboard getJSONDashboard(@NonNull final DashboardWidgetType widgetType)
	{
		userSession.assertLoggedIn();
		final KPIJsonOptions jsonOpts = newKPIJsonOptions();

		final ExplainedOptional<UserDashboard> optionalDashboard = getUserDashboard();
		if (!optionalDashboard.isPresent())
		{
			return JSONDashboard.EMPTY.withNoDashboardReason(optionalDashboard.getExplanation().translate(jsonOpts.getAdLanguage()));
		}

		final UserDashboard dashboard = optionalDashboard.get();
		final KPIDataContext kpiDataContext = KPIDataContext.ofUserSession(userSession);

		final UserDashboardDataResponse data = dashboardDataService
				.getData(dashboard.getId())
				.getAllItems(UserDashboardDataRequest.builder()
						.widgetType(widgetType)
						.context(kpiDataContext)
						.build());

		return toJSONDashboard(
				dashboard,
				widgetType,
				data,
				jsonOpts);

	}

	private JSONDashboard toJSONDashboard(
			@NonNull final UserDashboard userDashboard,
			@NonNull final DashboardWidgetType widgetType,
			@NonNull final UserDashboardDataResponse data,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		return JSONDashboard.builder()
				.items(toJSONDashboardItemsNoFail(userDashboard.getItems(widgetType), data, jsonOpts))
				.websocketEndpoint(UserDashboardWebsocketProducerFactory.createWebsocketTopicName(userSession.getSessionId()).getAsString())
				.build();
	}

	private static ImmutableList<JSONDashboardItem> toJSONDashboardItemsNoFail(
			@NonNull final Collection<UserDashboardItem> items,
			@NonNull final UserDashboardDataResponse data,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		return items.stream()
				.map(item -> toJSONDashboardItemNoFail(item, data, jsonOpts))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static JSONDashboardItem toJSONDashboardItemNoFail(
			@NonNull final UserDashboardItem item,
			@NonNull final UserDashboardDataResponse data,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		try
		{
			final UserDashboardItemDataResponse itemData = data.getItemById(item.getId()).orElse(null);
			return JSONDashboardItem.of(item, itemData, jsonOpts);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} to JSON. Skipped", item, ex);
			return null;
		}
	}

	@GetMapping("/kpis/available")
	public List<JsonKPI> getKPIsAvailableToAdd(
			@RequestParam(name = "firstRow", required = false, defaultValue = "0") final int firstRow,
			@RequestParam(name = "pageLength", required = false, defaultValue = "0") final int pageLength)
	{
		userSession.assertLoggedIn();

		final Collection<KPI> kpis = dashboardRepo.getKPIsAvailableToAdd();

		final KPIDataContext kpiDataContext = KPIDataContext.ofUserSession(userSession);
		final KPIJsonOptions jsonOpts = newKPIJsonOptions();
		return kpis.stream()
				.map(kpi -> toJsonKPI(kpi, kpiDataContext, jsonOpts))
				.sorted(Comparator.comparing(JsonKPI::getCaption))
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : Integer.MAX_VALUE)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private JsonKPI toJsonKPI(@NonNull final KPI kpi, @NonNull final KPIDataContext kpiDataContext, @NonNull final KPIJsonOptions jsonOpts)
	{
		KPIDataResult sampleData = null;
		try
		{
			sampleData = dashboardDataService.getKPIData(kpi.getId(), kpiDataContext);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching sample data for {}, context={}", kpi, kpiDataContext, ex);
		}

		return JsonKPI.of(kpi, sampleData, jsonOpts);
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
		final UserDashboardItem item = dashboard.getItemById(widgetType, itemId);
		final UserDashboardItemDataResponse data = dashboardDataService.getData(dashboard.getId())
				.getItemData(UserDashboardItemDataRequest.builder()
						.itemId(item.getId())
						.widgetType(widgetType)
						.context(KPIDataContext.ofUserSession(userSession))
						.build());
		return JSONDashboardItem.of(item, data, newKPIJsonOptions());
	}

	@GetMapping("/kpis/{itemId}/data")
	public JsonKPIDataResult getKPIData(
			@PathVariable("itemId") final int itemId,
			@RequestParam(name = "fromMillis", required = false, defaultValue = "0") @Parameter(description = "interval rage start, in case ofValueAndField temporal data") final long fromMillis,
			@RequestParam(name = "toMillis", required = false, defaultValue = "0") @Parameter(description = "interval rage end, in case ofValueAndField temporal data") final long toMillis,
			@RequestParam(name = "prettyValues", required = false, defaultValue = "true") @Parameter(description = "if true, the server will format the values") final boolean prettyValues)
	{
		return getKPIData(
				DashboardWidgetType.KPI,
				UserDashboardItemId.ofRepoId(itemId),
				fromMillis > 0 ? Instant.ofEpochMilli(fromMillis) : null,
				toMillis > 0 ? Instant.ofEpochMilli(toMillis) : null,
				prettyValues);
	}

	@GetMapping("/targetIndicators/{itemId}/data")
	public JsonKPIDataResult getTargetIndicatorData(
			@PathVariable("itemId") final int itemId,
			@RequestParam(name = "fromMillis", required = false, defaultValue = "0") @Parameter(description = "interval rage start, in case ofValueAndField temporal data") final long fromMillis,
			@RequestParam(name = "toMillis", required = false, defaultValue = "0") @Parameter(description = "interval rage end, in case ofValueAndField temporal data") final long toMillis,
			@RequestParam(name = "prettyValues", required = false, defaultValue = "true") @Parameter(description = "if true, the server will format the values") final boolean prettyValues)
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
		final UserDashboardItemDataResponse itemData = dashboardDataService
				.getData(dashboardId)
				.getItemData(UserDashboardItemDataRequest.builder()
						.widgetType(widgetType)
						.itemId(itemId)
						.context(KPIDataContext.builderFromUserSession(userSession)
								.from(from)
								.to(to)
								.build())
						.build());

		return JsonKPIDataResult.of(
				itemData,
				newKPIJsonOptions().withPrettyValues(prettyValues));
	}

	private KPIJsonOptions newKPIJsonOptions()
	{
		return KPIJsonOptions.builder()
				.adLanguage(userSession.getAD_Language())
				.prettyValues(true)
				.zoneId(userSession.getTimeZone())
				.debugShowColumnNamesForCaption(userSession.isShowColumnNamesForCaption())
				.build();
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

	@GetMapping("/targetIndicators/{itemId}/details")
	public JsonKPIZoomInfoDetails getTargetIndicatorDetails(
			@PathVariable("itemId") final int itemRepoId)
	{
		userSession.assertLoggedIn();

		final UserDashboardItemId itemId = UserDashboardItemId.ofRepoId(itemRepoId);
		final KPIZoomIntoDetailsInfo detailsInfo = getKPIZoomDetailsInfo(itemId);

		final ViewId viewId = createView(detailsInfo);

		return JsonKPIZoomInfoDetails.builder()
				.windowId(viewId.getWindowId().toJson())
				.viewId(viewId.toJson())
				.build();
	}

	private KPIZoomIntoDetailsInfo getKPIZoomDetailsInfo(@NonNull final UserDashboardItemId itemId)
	{
		final UserDashboardId dashboardId = getUserDashboard().get().getId();
		return dashboardDataService
				.getData(dashboardId)
				.getZoomIntoDetailsInfo(UserDashboardItemDataRequest.builder()
						.widgetType(DashboardWidgetType.TargetIndicator)
						.itemId(itemId)
						.context(KPIDataContext.ofUserSession(userSession))
						.build())
				.orElseThrow(() -> new AdempiereException("Details not available"));
	}

	private ViewId createView(final KPIZoomIntoDetailsInfo detailsInfo)
	{
		final WindowId targetWindowId = getTargetWindowId(detailsInfo);

		return viewRepo.createView(CreateViewRequest.builder(targetWindowId)
						.addStickyFilters(DocumentFilter.builder()
								.filterId("userDashboardItem")
								.caption(detailsInfo.getFilterCaption())
								.addParameter(DocumentFilterParam.ofSqlWhereClause(detailsInfo.getSqlWhereClause()))
								.build())
						.setUseAutoFilters(true)
						.build())
				.getViewId();
	}

	private WindowId getTargetWindowId(final KPIZoomIntoDetailsInfo detailsInfo)
	{
		final WindowId targetWindowId = detailsInfo.getTargetWindowId();
		if (targetWindowId != null)
		{
			return targetWindowId;
		}

		return RecordWindowFinder.findAdWindowId(detailsInfo.getTableName())
				.map(WindowId::of)
				.orElseThrow(() -> new AdempiereException("No window available to show the details"));
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
			final UserDashboardItemDataResponse data = dashboardDataService.getData(dashboard.getId())
					.getItemData(UserDashboardItemDataRequest.builder()
							.itemId(item.getId())
							.widgetType(widgetType)
							.context(KPIDataContext.ofUserSession(userSession))
							.build());
			return JSONDashboardItem.of(item, data, newKPIJsonOptions());
		}
	}
}
