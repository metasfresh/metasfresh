/*
 * #%L
 * de.metas.issue.tracking.everhour
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.issue.tracking.everhour.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

public interface EverhourConstants
{

	String EVERHOUR_API_VERSION = "1.2";

	@AllArgsConstructor
	@Getter
	enum CustomHeaders {
		API_KEY("X-Api-Key"),
		VERSION("X-Accept-Version");

		private final String value;
	}

	@AllArgsConstructor
	@Getter
	enum EverhourSysConfigs
	{
		READ_TIMEOUT("de.metas.issue.tracking.everhour.readTimeout", 6000 * 2),
		CONNECTION_TIMEOUT("de.metas.issue.tracking.everhour.connectionTimeout", 6000 * 2),
		MAX_TIME_TO_WAIT_FOR_LIMIT_RESET("de.metas.issue.tracking.everhour.maxTimeToWaitForLimitReset", 6000 * 2);

		private final String name;
		private final int defaultValue;
	}

	@AllArgsConstructor
	@Getter
	enum Endpoints
	{
		TEAM("team"),
		TIME("time");

		private final String value;
	}

	@AllArgsConstructor
	@Getter
	enum QueryParams
	{
		FROM("from"),
		TO("to"),
		DATE("date");

		private final String value;
	}

	@AllArgsConstructor
	@Getter
	enum TaskIdSource
	{
		GITHUB_ID(Pattern.compile("^gh:([0-9]+)$"));

		private final Pattern pattern;
	}

	String DATE_FORMAT = "yyyy-MM-dd";
	String BASE_URL = "https://api.everhour.com";
}
