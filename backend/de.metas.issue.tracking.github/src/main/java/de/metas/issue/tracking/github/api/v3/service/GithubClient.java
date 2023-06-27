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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.issue.tracking.github.api.v3.GitHubApiConstants;
import de.metas.issue.tracking.github.api.v3.model.CreatWebhookRequest;
import de.metas.issue.tracking.github.api.v3.model.CreateIssueRequest;
import de.metas.issue.tracking.github.api.v3.model.FetchIssueByIdRequest;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.model.ResourceState;
import de.metas.issue.tracking.github.api.v3.model.RetrieveIssuesRequest;
import de.metas.issue.tracking.github.api.v3.service.rest.RestService;
import de.metas.issue.tracking.github.api.v3.service.rest.info.Request;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.Endpoint.HOOKS;
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

		final Request getRequest = Request.builder()
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

		final Request getRequest = Request.builder()
				.baseURL(GITHUB_BASE_URI)
				.pathVariables(pathVariables)
				.oAuthToken(fetchIssueByIdRequest.getOAuthToken())
				.build();

		return restService.performGet(getRequest, Issue.class).getBody();
	}

	@NonNull
	public String createWebhook(@NonNull final CreatWebhookRequest request) throws JsonProcessingException
	{
		try
		{
			final Map<String, String> configs = ImmutableMap.of("content_type", "json",
																"url", request.getWebhookURL(),
																"secret", request.getWebhookSecret());

			final Map<String, Object> body = ImmutableMap.of("active", true,
															 "events", request.mapEvents(GitHubApiConstants.Events::getValue),
															 "config", configs);

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(body);

			final List<String> pathVariables = ImmutableList.of(REPOS.getValue(),
																request.getRepositoryOwner(),
																request.getRepositoryName(),
																HOOKS.getValue());

			final Request postRequest = Request.builder()
					.baseURL(GITHUB_BASE_URI)
					.pathVariables(pathVariables)
					.oAuthToken(request.getOAuthToken())
					.requestBody(requestBody)
					.build();

			return restService.performPost(postRequest, String.class).getBody();
		}
		catch (final HttpClientErrorException.UnprocessableEntity e)
		{
			final String errorMessage = getErrorMessageIfAny(e).orElse(e.getMessage());

			throw new AdempiereException(errorMessage)
					.markAsUserValidationError();
		}
	}

	@NonNull
	public Issue createIssue(@NonNull final CreateIssueRequest request) throws JsonProcessingException
	{
		try
		{
			final ImmutableMap.Builder<String, Object> body = ImmutableMap.builder();
			body.put("title", request.getTitle());

			if (Check.isNotBlank(request.getBody()))
			{
				body.put("body", request.getBody());
			}

			if (request.getLabels() != null || !request.getLabels().isEmpty())
			{
				body.put("labels", request.getLabels());
			}

			final String requestBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(body.build());

			final List<String> pathVariables = ImmutableList.of(REPOS.getValue(),
																request.getRepositoryOwner(),
																request.getRepositoryName(),
																ISSUES.getValue());

			final Request postRequest = Request.builder()
					.baseURL(GITHUB_BASE_URI)
					.pathVariables(pathVariables)
					.oAuthToken(request.getOAuthToken())
					.requestBody(requestBody)
					.build();

			return restService.performPost(postRequest, Issue.class).getBody();
		}
		catch (final HttpClientErrorException.UnprocessableEntity e)
		{
			final String errorMessage = getErrorMessageIfAny(e).orElse(e.getMessage());

			throw new AdempiereException(errorMessage)
					.markAsUserValidationError();
		}
	}

	@NonNull
	private Optional<String> getErrorMessageIfAny(@NonNull final HttpClientErrorException.UnprocessableEntity e) throws JsonProcessingException
	{
		final JsonNode jsonNode = JsonObjectMapperHolder.sharedJsonObjectMapper().readTree(e.getResponseBodyAsString());

		final JsonNode errors = jsonNode.at("/errors");

		if (errors == null || !errors.isArray())
		{
			return Optional.empty();
		}

		final String errorMessage = StreamSupport.stream(errors.spliterator(), false)
				.map(messageNode -> messageNode.at("/message"))
				.filter(Objects::nonNull)
				.map(JsonNode::asText)
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(";"));

		return Optional.of(errorMessage);
	}
}
