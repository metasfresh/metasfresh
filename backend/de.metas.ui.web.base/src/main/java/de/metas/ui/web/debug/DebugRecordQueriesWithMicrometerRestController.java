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

package de.metas.ui.web.debug;

import de.metas.ui.web.session.UserSession;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DebugRecordQueriesWithMicrometerRestController.ENDPOINT)
public class DebugRecordQueriesWithMicrometerRestController
{
	public static final String ENDPOINT = DebugRestController.ENDPOINT + "/recordSqlQueriesWithMicrometer";

	private final UserSession userSession;
	private final IQueryStatisticsLogger statisticsLogger;

	public DebugRecordQueriesWithMicrometerRestController(
			@NonNull final UserSession userSession,
			@NonNull final IQueryStatisticsLogger statisticsLogger)
	{
		this.userSession = userSession;
		this.statisticsLogger = statisticsLogger;
	}

	@GetMapping
	public void setRecordSqlQueriesWithMicrometer(
			@Parameter(description = "If Enabled, all SQL queries execution times are recorded with micrometer")
			@RequestParam("enabled") final boolean enabled)
	{
		userSession.assertLoggedIn();

		if (enabled)
		{
			statisticsLogger.enableRecordWithMicrometer();
		}
		else
		{
			statisticsLogger.disableRecordWithMicrometer();
		}
	}
}
