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

import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Issue.IssueBuilder.class)
public class Issue
{
	@JsonProperty("id")
	String id;

	@JsonProperty("html_url")
	String htmlUrl;

	@JsonProperty("number")
	Integer number;

	@JsonProperty("title")
	String title;

	@JsonProperty("labels")
	List<Label> labelList;

	@JsonProperty("state")
	String state;

	@JsonProperty("assignee")
	User assignee;

	@JsonProperty("milestone")
	GithubMilestone githubMilestone;

	@JsonProperty("body")
	String body;

	@JsonProperty("pull_request")
	PullRequest pullRequest;

	public boolean isPullRequest()
	{
		return pullRequest != null;
	}
}


