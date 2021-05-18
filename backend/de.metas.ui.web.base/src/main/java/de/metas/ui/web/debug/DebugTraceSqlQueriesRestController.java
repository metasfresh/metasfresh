/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.debug;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.session.UserSession;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(DebugTraceSqlQueriesRestController.ENDPOINT)
public class DebugTraceSqlQueriesRestController
{
	public static final String ENDPOINT = DebugRestController.ENDPOINT + "/traceSqlQueries";

	private final UserSession userSession;
	private final IQueryStatisticsLogger statisticsLogger;

	public DebugTraceSqlQueriesRestController(
			@NonNull final UserSession userSession,
			@NonNull final IQueryStatisticsLogger statisticsLogger)
	{
		this.userSession = userSession;
		this.statisticsLogger = statisticsLogger;
	}

	@GetMapping
	public void setTraceSqlQueries(
			@ApiParam("If Enabled, all SQL queries are logged with loglevel=WARN, or if the system property <code>" + IQueryStatisticsLogger.SYSTEM_PROPERTY_LOG_TO_SYSTEM_ERROR + "</code> is set to <code>true</code>, they will be written to std-err.")
			@RequestParam("enabled") final boolean enabled)
	{
		userSession.assertLoggedIn();

		if (enabled)
		{
			statisticsLogger.enableWithSqlTracing();
		}
		else
		{
			statisticsLogger.disable();
		}
	}

	@GetMapping("/top/byAverageDuration")
	@ApiOperation("Gets top SQL queries ordered by their average execution time (descending)")
	public Map<String, Object> getTopAverageDurationQueriesAsString()
	{
		userSession.assertLoggedIn();
		return queriesListToMap(statisticsLogger.getTopAverageDurationQueriesAsString());
	}

	@ApiOperation("Gets top SQL queries ordered by their total summed executon time (descending)")
	@GetMapping("/top/byTotalDuration")
	public Map<String, Object> getTopTotalDurationQueriesAsString()
	{
		userSession.assertLoggedIn();
		return queriesListToMap(statisticsLogger.getTopTotalDurationQueriesAsString());
	}

	@GetMapping("/top/byExecutionCount")
	@ApiOperation("Gets top SQL queries ordered by their execution count (descending)")
	public Map<String, Object> getTopCountQueriesAsString()
	{
		userSession.assertLoggedIn();
		return queriesListToMap(statisticsLogger.getTopCountQueriesAsString());
	}

	private static Map<String, Object> queriesListToMap(final String[] list)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("queries", ImmutableList.copyOf(list));
		return map;
	}
}
