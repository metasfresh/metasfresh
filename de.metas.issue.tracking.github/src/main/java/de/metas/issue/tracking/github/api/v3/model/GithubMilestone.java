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

package de.metas.issue.tracking.github.api.v3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.UTC_TIMEZONE;

@Value
@JsonDeserialize(builder = GithubMilestone.GithubMilestoneBuilder.class)
public class GithubMilestone
{
	String id;

	String htmlUrl;

	String title;

	String description;

	String state;

	LocalDateTime dueDate;

	@Builder
	@JsonIgnoreProperties(ignoreUnknown = true)
	public GithubMilestone(@JsonProperty("id") final String id,
			@JsonProperty("html_url") final String htmlUrl,
			@JsonProperty("title") final String title,
			@JsonProperty("description") final String description,
			@JsonProperty("state") final String state,
			@JsonProperty("due_on") final String dueDate)
	{
		this.id = id;
		this.htmlUrl = htmlUrl;
		this.title = title;
		this.description = description;
		this.state = state;
		this.dueDate = dueDate != null
				? Instant.parse(dueDate).atZone(ZoneId.of(UTC_TIMEZONE)).toLocalDateTime()
				: null;
	}
}


