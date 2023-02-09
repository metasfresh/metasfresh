/*
 * #%L
 * de.metas.issue.tracking.github
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

package de.metas.issue.tracking.github.api.v3;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GitHubApiConstants
{
	@AllArgsConstructor
	@Getter
	enum CustomHeaders
	{
		RATE_LIMIT_REMAINING("X-RateLimit-Remaining"),
		RATE_LIMIT_RESET("X-RateLimit-Reset");

		private final String value;
	}

	@Getter
	@AllArgsConstructor
	enum Endpoint
	{
		REPOS("repos"),
		ISSUES("issues"),
		HOOKS("hooks");

		private final String value;
	}

	@AllArgsConstructor
	@Getter
	enum QueryParam
	{
		STATE("state"),
		PER_PAGE("per_page"),
		PAGE("page"),
		SINCE("since");

		private final String value;
	}

	@AllArgsConstructor
	@Getter
	enum GithubSysConfig
	{
		READ_TIMEOUT("de.metas.issue.tracking.github.readTimeout", 6000 * 2),
		CONNECTION_TIMEOUT("de.metas.issue.tracking.github.connectionTimeout", 6000 * 2),
		MAX_TIME_TO_WAIT_FOR_LIMIT_RESET("de.metas.issue.tracking.github.maxTimeToWaitForLimitReset", 6000 * 2);

		private final String name;
		private final int defaultValue;
	}

	@AllArgsConstructor
	@Getter
	enum Events
	{
		ISSUES("issues");

		private final String value;
	}


	String GITHUB_BASE_URI = "https://api.github.com";
	String GITHUB_API_VERSION = "application/vnd.github.v3+json";
	String UTC_TIMEZONE = "UTC";
}
