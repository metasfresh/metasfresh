/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.issue.tracking.github.api.v3.GitHubApiConstants;
import de.metas.issue.tracking.github.api.v3.model.CreatWebhookRequest;
import de.metas.issue.tracking.github.api.v3.service.GithubClient;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.github.config.GithubConfigRepository;
import de.metas.serviceprovider.github.link.GithubIssueLinkMatcher;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static de.metas.common.rest_api.v2.APIConstants.GITHUB_ISSUE_CONTROLLER;
import static de.metas.common.rest_api.v2.APIConstants.GITHUB_ISSUE_CONTROLLER_SYNC_ENDPOINT;
import static de.metas.serviceprovider.external.ExternalSystem.GITHUB;
import static de.metas.serviceprovider.github.config.GithubConfigName.ACCESS_TOKEN;
import static de.metas.serviceprovider.github.config.GithubConfigName.GITHUB_SECRET;
import static de.metas.serviceprovider.github.config.GithubConfigName.LOOK_FOR_PARENT;
import static de.metas.util.web.MetasfreshRestAPIConstants.ENDPOINT_API_V2;

@Service
public class GithubService
{
	private static final String APPLICATION_BASE_URL = "APPLICATION_BASE_URL";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final GithubConfigRepository githubConfigRepository;
	private final ExternalProjectRepository externalProjectRepository;
	private final GithubClient githubClient;

	public GithubService(
			@NonNull final GithubConfigRepository githubConfigRepository,
			@NonNull final ExternalProjectRepository externalProjectRepository,
			@NonNull final GithubClient githubClient)
	{
		this.githubConfigRepository = githubConfigRepository;
		this.externalProjectRepository = externalProjectRepository;
		this.githubClient = githubClient;
	}

	public void createSyncIssuesWebhook(@NonNull final ExternalProjectReferenceId externalProjectReferenceId) throws JsonProcessingException
	{
		final ExternalProjectReference externalProjectReference = externalProjectRepository.getById(externalProjectReferenceId);

		final String token = githubConfigRepository.getConfigByNameAndOrg(ACCESS_TOKEN, externalProjectReference.getOrgId());
		final String githubSecret = githubConfigRepository.getConfigByNameAndOrg(GITHUB_SECRET, externalProjectReference.getOrgId());

		final String applicationBaseUrl = StringUtils.trimBlankToNull(sysConfigBL.getValue(APPLICATION_BASE_URL));

		if (Check.isBlank(applicationBaseUrl))
		{
			throw new AdempiereException("Application url is not configured! There is no value set for APPLICATION_BASE_URL sys config.");
		}

		final String payloadUrl = applicationBaseUrl + ENDPOINT_API_V2 + GITHUB_ISSUE_CONTROLLER + GITHUB_ISSUE_CONTROLLER_SYNC_ENDPOINT;

		final CreatWebhookRequest creatIssuesWebhookRequest = CreatWebhookRequest.builder()
				.repositoryOwner(externalProjectReference.getProjectOwner())
				.repositoryName(externalProjectReference.getExternalProjectReference())
				.webhookURL(payloadUrl)
				.webhookSecret(githubSecret)
				.oAuthToken(token)
				.events(ImmutableList.of(GitHubApiConstants.Events.ISSUES))
				.build();

		githubClient.createWebhook(creatIssuesWebhookRequest);
	}

	@Nullable
	public GithubIssueLinkMatcher getDefaultLinkMatcher()
	{
		final ImmutableList<ExternalProjectReference> allActiveGithubProjects = externalProjectRepository.getByExternalSystem(GITHUB);

		return getGithubLinkMatcher(allActiveGithubProjects);
	}

	@Nullable
	public GithubIssueLinkMatcher getGithubLinkMatcher(@NonNull final ImmutableList<ExternalProjectReference> externalProjectReferences)
	{
		//dev-note: considering LOOK_FOR_PARENT a system wide config
		final boolean lookForParent = githubConfigRepository.getOptionalConfigByNameAndOrg(LOOK_FOR_PARENT, OrgId.ANY)
				.map(StringUtils::toBoolean)
				.orElse(false);

		if (!lookForParent)
		{
			return null;
		}

		final ImmutableSet.Builder<String> owners = ImmutableSet.builder();
		final ImmutableSet.Builder<String> projects = ImmutableSet.builder();

		externalProjectReferences.forEach(projectRef ->
										  {
											  owners.add(projectRef.getProjectOwner());
											  projects.add(projectRef.getExternalProjectReference());
										  });

		return GithubIssueLinkMatcher.of(owners.build(), projects.build());
	}
}
