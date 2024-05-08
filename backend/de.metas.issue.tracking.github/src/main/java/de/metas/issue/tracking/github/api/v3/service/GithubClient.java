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

package de.metas.issue.tracking.github.api.v3.service;

import com.google.common.collect.ImmutableList;
import de.metas.issue.tracking.github.api.v3.model.FetchIssueByIdRequest;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.model.ResourceState;
import de.metas.issue.tracking.github.api.v3.model.RetrieveIssuesRequest;
import de.metas.issue.tracking.github.api.v3.service.rest.RestService;
import de.metas.issue.tracking.github.api.v3.service.rest.info.GetRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.Endpoint.ISSUES;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.Endpoint.REPOS;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.GITHUB_BASE_URI;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.QueryParam.PAGE;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.QueryParam.PER_PAGE;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.QueryParam.SINCE;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.QueryParam.STATE;

@Component
public class GithubClient
{
	private final RestService restService;

	public GithubClient(final RestService restService)
	{
		this.restService = restService;
	}

	public ImmutableList<Issue> fetchIssues(@NonNull final RetrieveIssuesRequest retrieveIssuesRequest)
	{
		final ImmutableList<Issue> issueList;

		final List<String> pathVariables = Arrays.asList(
				REPOS.getValue(),
				retrieveIssuesRequest.getRepositoryOwner(),
				retrieveIssuesRequest.getRepositoryId(),
				ISSUES.getValue());

		final LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add(STATE.getValue(), ResourceState.ALL.getValue());
		queryParams.add(PAGE.getValue(), String.valueOf(retrieveIssuesRequest.getPageIndex()));
		queryParams.add(PER_PAGE.getValue(), String.valueOf(retrieveIssuesRequest.getPageSize()));

		if (retrieveIssuesRequest.getDateFrom() != null)
		{
			final String since = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(retrieveIssuesRequest.getDateFrom().atStartOfDay(ZoneOffset.UTC));

			queryParams.add(SINCE.getValue(), since);
		}

		final GetRequest getRequest = GetRequest.builder()
				.baseURL(GITHUB_BASE_URI)
				.pathVariables(pathVariables)
				.queryParameters(queryParams)
				.oAuthToken(retrieveIssuesRequest.getOAuthToken())
				.build();

		issueList = ImmutableList.copyOf(restService.performGet(getRequest, Issue[].class).getBody());

		return issueList.stream()
				.filter(issue -> !issue.isPullRequest())
				.collect(ImmutableList.toImmutableList());
	}

	public Issue fetchIssueById(@NonNull final FetchIssueByIdRequest fetchIssueByIdRequest)
	{
		final List<String> pathVariables = Arrays.asList(
				REPOS.getValue(),
				fetchIssueByIdRequest.getRepositoryOwner(),
				fetchIssueByIdRequest.getRepositoryId(),
				ISSUES.getValue(),
				fetchIssueByIdRequest.getIssueNumber());

		final GetRequest getRequest = GetRequest.builder()
				.baseURL(GITHUB_BASE_URI)
				.pathVariables(pathVariables)
				.oAuthToken(fetchIssueByIdRequest.getOAuthToken())
				.build();

		return restService.performGet(getRequest, Issue.class).getBody();
	}
}
