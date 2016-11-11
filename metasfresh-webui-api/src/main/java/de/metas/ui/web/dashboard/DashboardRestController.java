package de.metas.ui.web.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.dashboard.json.JSONDashboard;
import de.metas.ui.web.dashboard.json.JSONDashboardChanges;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@Deprecated
	public JSONDashboard getUserDashboard()
	{
		return getKPIs();
	}

	@RequestMapping(value = "/kpis", method = RequestMethod.GET)
	public JSONDashboard getKPIs()
	{
		userSession.assertLoggedIn();

		final UserDashboard userDashboard = userDashboardRepo.getUserDashboard();
		return JSONDashboard.of(userDashboard.getKPIItems(), newJSONOpts().build());
	}

	@RequestMapping(value = "/", method = RequestMethod.PATCH)
	@Deprecated
	public void changeUserDashboard(@RequestBody final JSONDashboardChanges jsonDashboardChanges)
	{
		changeKPIs(jsonDashboardChanges);
	}

	@RequestMapping(value = "/kpis", method = RequestMethod.PATCH)
	public void changeKPIs(@RequestBody final JSONDashboardChanges jsonDashboardChanges)
	{
		userSession.assertLoggedIn();

		userDashboardRepo.changeUserDashboardKPIs(jsonDashboardChanges);
	}

	@RequestMapping(value = "/targetIndicators", method = RequestMethod.GET)
	public JSONDashboard getTargetIndicators()
	{
		userSession.assertLoggedIn();

		final UserDashboard userDashboard = userDashboardRepo.getUserDashboard();
		return JSONDashboard.of(userDashboard.getTargetIndicatorItems(), newJSONOpts().build());
	}

}
