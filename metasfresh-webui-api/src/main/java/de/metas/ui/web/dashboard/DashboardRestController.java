package de.metas.ui.web.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.json.JSONDashboard;
import de.metas.ui.web.dashboard.json.JSONDashboardChanges;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
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

	private JSONOptions.Builder newJSONOpts()
	{
		return JSONOptions.builder()
				.setUserSession(userSession);
	}

	@GetMapping("/kpis")
	public JSONDashboard getKPIsDashboard()
	{
		userSession.assertLoggedIn();

		final UserDashboard userDashboard = userDashboardRepo.getUserDashboard();
		return JSONDashboard.of(userDashboard.getKPIItems(), newJSONOpts().build());
	}

	@PatchMapping("/kpis")
	public void changeKPIsDashboard(@RequestBody final JSONDashboardChanges jsonDashboardChanges)
	{
		userSession.assertLoggedIn();

		userDashboardRepo.changeUserDashboardKPIs(jsonDashboardChanges);
	}

	@GetMapping("/kpis/{itemId}/data")
	public KPIData getKPIData( //
			@PathVariable final int itemId //
			, @RequestParam("fromMillis") @ApiParam("interval rage start, in case of temporal data") final long fromMillis //
			, @RequestParam("toMillis") @ApiParam("interval rage end, in case of temporal data") final long toMillis //
	)
	{
		return userDashboardRepo.getUserDashboard()
				.getKPIItemById(itemId)
				.getKPI()
				.retrieveData(fromMillis, toMillis);
	}

	@GetMapping("/targetIndicators")
	public JSONDashboard getTargetIndicatorsDashboard()
	{
		userSession.assertLoggedIn();

		final UserDashboard userDashboard = userDashboardRepo.getUserDashboard();
		return JSONDashboard.of(userDashboard.getTargetIndicatorItems(), newJSONOpts().build());
	}

	@GetMapping("/targetIndicators/{itemId}/data")
	public KPIData getTargetIndicatorData( //
			@PathVariable final int itemId //
			, @RequestParam("fromMillis") @ApiParam("interval rage start, in case of temporal data") final long fromMillis //
			, @RequestParam("toMillis") @ApiParam("interval rage end, in case of temporal data") final long toMillis //
	)
	{
		return userDashboardRepo.getUserDashboard()
				.getTargetIndicatorItemById(itemId)
				.getKPI()
				.retrieveData(fromMillis, toMillis);
	}
}
