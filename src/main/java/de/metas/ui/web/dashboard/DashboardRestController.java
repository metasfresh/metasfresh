package de.metas.ui.web.dashboard;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.elasticsearch.client.Client;
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

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.UserDashboardRepository.DashboardPatchPath;
import de.metas.ui.web.dashboard.UserDashboardRepository.UserDashboardKey;
import de.metas.ui.web.dashboard.json.JSONDashboard;
import de.metas.ui.web.dashboard.json.JSONDashboardItem;
import de.metas.ui.web.dashboard.json.JsonKPI;
import de.metas.ui.web.dashboard.json.JsonUserDashboardItemAddRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONPatchEvent;
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

	@Autowired
	private UserSession userSession;
	@Autowired
	private UserDashboardRepository userDashboardRepo;
	@Autowired
	private Client elasticsearchClient;

	private JSONOptions newJSONOpts()
	{
		return JSONOptions.of(userSession);
	}

	private UserDashboard getUserDashboardForReading()
	{
		final UserDashboard dashboard = userDashboardRepo.getUserDashboard(UserDashboardKey.of(userSession.getAD_Client_ID()));
		// TODO: assert readable by current user
		return dashboard;
	}

	private UserDashboard getUserDashboardForWriting()
	{
		final UserDashboard dashboard = userDashboardRepo.getUserDashboard(UserDashboardKey.of(userSession.getAD_Client_ID()));
		// TODO: assert writable by current user
		return dashboard;
	}

	@GetMapping("/kpis")
	public JSONDashboard getKPIsDashboard()
	{
		userSession.assertLoggedIn();

		final UserDashboard userDashboard = getUserDashboardForReading();
		return JSONDashboard.of(userDashboard.getItems(DashboardWidgetType.KPI), newJSONOpts());
	}

	@GetMapping("/kpis/available")
	public List<JsonKPI> getKPIsAvailableToAdd()
	{
		userSession.assertLoggedIn();

		final Collection<KPI> kpis = userDashboardRepo.getKPIsAvailableToAdd();

		final JSONOptions jsonOpts = newJSONOpts();
		return kpis.stream()
				.map(kpi -> JsonKPI.of(kpi, jsonOpts))
				.sorted(Comparator.comparing(JsonKPI::getCaption))
				.collect(ImmutableList.toImmutableList());
	}

	@PostMapping("/kpis/new")
	public JSONDashboardItem addKPIItem(@RequestBody final JsonUserDashboardItemAddRequest request)
	{
		userSession.assertLoggedIn();

		final int itemId = userDashboardRepo.addUserDashboardItem(getUserDashboardForWriting(), DashboardWidgetType.KPI, request);

		final UserDashboardItem kpiItem = getUserDashboardForReading().getItemById(DashboardWidgetType.KPI, itemId);
		return JSONDashboardItem.of(kpiItem, newJSONOpts());
	}

	@PatchMapping("/kpis")
	public void changeKPIsDashboard(@RequestBody final List<JSONPatchEvent<DashboardPatchPath>> events)
	{
		userSession.assertLoggedIn();

		userDashboardRepo.changeDashboardItems(getUserDashboardForWriting(), DashboardWidgetType.KPI, events);
	}

	private final KPIDataResult getKPIData(final UserDashboardItem dashboardItem, final long fromMillis, final long toMillis, final boolean prettyValues)
	{
		final KPI kpi = dashboardItem.getKPI();
		final TimeRange timeRange = dashboardItem.getTimeRangeDefaults().createTimeRange(fromMillis, toMillis);

		return KPIDataLoader.newInstance(elasticsearchClient, kpi)
				.setTimeRange(timeRange)
				.setFormatValues(prettyValues)
				.retrieveData()
				.setItemId(dashboardItem.getId());
	}

	@GetMapping("/kpis/{itemId}/data")
	public KPIDataResult getKPIData( //
			@PathVariable final int itemId //
			, @RequestParam(name = "fromMillis", required = false, defaultValue = "0") @ApiParam("interval rage start, in case of temporal data") final long fromMillis //
			, @RequestParam(name = "toMillis", required = false, defaultValue = "0") @ApiParam("interval rage end, in case of temporal data") final long toMillis //
			, @RequestParam(name = "prettyValues", required = false, defaultValue = "true") @ApiParam("if true, the server will format the values") final boolean prettyValues //
	)
	{
		userSession.assertLoggedIn();

		final UserDashboardItem dashboardItem = getUserDashboardForReading()
				.getItemById(DashboardWidgetType.KPI, itemId);

		return getKPIData(dashboardItem, fromMillis, toMillis, prettyValues);
	}

	@DeleteMapping("/kpis/{itemId}")
	public void deleteKPIItem(@PathVariable("itemId") final int itemId)
	{
		userSession.assertLoggedIn();

		final UserDashboard dashboard = getUserDashboardForWriting();
		userDashboardRepo.deleteUserDashboardItem(dashboard, DashboardWidgetType.KPI, itemId);
	}

	@PostMapping("/targetIndicators/new")
	public JSONDashboardItem addTargetIndicatorItem(@RequestBody final JsonUserDashboardItemAddRequest request)
	{
		userSession.assertLoggedIn();

		final int itemId = userDashboardRepo.addUserDashboardItem(getUserDashboardForWriting(), DashboardWidgetType.TargetIndicator, request);

		final UserDashboardItem targetIndicatorItem = getUserDashboardForReading().getItemById(DashboardWidgetType.TargetIndicator, itemId);
		return JSONDashboardItem.of(targetIndicatorItem, newJSONOpts());
	}

	@PatchMapping("/targetIndicators")
	public void changeTargetIndicatorsDashboard(@RequestBody final List<JSONPatchEvent<DashboardPatchPath>> events)
	{
		userSession.assertLoggedIn();

		final UserDashboard dashboard = getUserDashboardForWriting();
		userDashboardRepo.changeDashboardItems(dashboard, DashboardWidgetType.TargetIndicator, events);
	}

	@GetMapping("/targetIndicators/available")
	public List<JsonKPI> getTargetIndicatorsAvailableToAdd()
	{
		userSession.assertLoggedIn();

		final Collection<KPI> kpis = userDashboardRepo.getTargetIndicatorsAvailableToAdd();

		final JSONOptions jsonOpts = newJSONOpts();
		return kpis.stream()
				.map(kpi -> JsonKPI.of(kpi, jsonOpts))
				.sorted(Comparator.comparing(JsonKPI::getCaption))
				.collect(ImmutableList.toImmutableList());
	}

	@GetMapping("/targetIndicators")
	public JSONDashboard getTargetIndicatorsDashboard()
	{
		userSession.assertLoggedIn();

		final UserDashboard userDashboard = getUserDashboardForReading();
		return JSONDashboard.of(userDashboard.getItems(DashboardWidgetType.TargetIndicator), newJSONOpts());
	}

	@DeleteMapping("/targetIndicators/{itemId}")
	public void deleteTargetIndicatorItem(@PathVariable("itemId") final int itemId)
	{
		userSession.assertLoggedIn();

		final UserDashboard dashboard = getUserDashboardForWriting();
		userDashboardRepo.deleteUserDashboardItem(dashboard, DashboardWidgetType.TargetIndicator, itemId);
	}

	@GetMapping("/targetIndicators/{itemId}/data")
	public KPIDataResult getTargetIndicatorData( //
			@PathVariable final int itemId //
			, @RequestParam(name = "fromMillis", required = false, defaultValue = "0") @ApiParam("interval rage start, in case of temporal data") final long fromMillis //
			, @RequestParam(name = "toMillis", required = false, defaultValue = "0") @ApiParam("interval rage end, in case of temporal data") final long toMillis //
			, @RequestParam(name = "prettyValues", required = false, defaultValue = "true") @ApiParam("if true, the server will format the values") final boolean prettyValues //
	)
	{
		userSession.assertLoggedIn();

		final UserDashboardItem dashboardItem = getUserDashboardForReading()
				.getItemById(DashboardWidgetType.TargetIndicator, itemId);

		return getKPIData(dashboardItem, fromMillis, toMillis, prettyValues);
	}
}
