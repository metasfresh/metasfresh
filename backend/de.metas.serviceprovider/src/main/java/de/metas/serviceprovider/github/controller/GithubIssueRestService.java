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

package de.metas.serviceprovider.github.controller;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.external.project.GetExternalProjectRequest;
import de.metas.serviceprovider.github.GithubImporterService;
import de.metas.serviceprovider.github.GithubService;
import de.metas.serviceprovider.github.config.GithubConfigRepository;
import de.metas.serviceprovider.issue.importer.IssueImporterService;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static de.metas.serviceprovider.github.config.GithubConfigName.ACCESS_TOKEN;

@Service
public class GithubIssueRestService
{
	private final static Logger logger = LogManager.getLogger(GithubIssueRestService.class);

	private final IssueImporterService issueImporterService;
	private final GithubImporterService githubImporterService;
	private final GithubConfigRepository githubConfigRepository;
	private final ExternalProjectRepository externalProjectRepository;
	private final GithubService githubService;

	public GithubIssueRestService(
			@NonNull final IssueImporterService issueImporterService,
			@NonNull final GithubImporterService githubImporterService,
			@NonNull final GithubConfigRepository githubConfigRepository,
			@NonNull final ExternalProjectRepository externalProjectRepository,
			@NonNull final GithubService githubService)
	{
		this.issueImporterService = issueImporterService;
		this.githubImporterService = githubImporterService;
		this.githubConfigRepository = githubConfigRepository;
		this.externalProjectRepository = externalProjectRepository;
		this.githubService = githubService;
	}

	public void syncIssue(@NonNull final String githubPayload, @NonNull final OrgId orgId) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException
	{
		final SyncGithubIssueRequest syncGithubIssueRequest = getRequest(githubPayload);

		if (!syncGithubIssueRequest.isSyncRequired())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Returning! Github action={}. Nothing to be done.", syncGithubIssueRequest.getAction());
			return;
		}

		final ExternalProjectReference externalProjectReference = resolveExternalProjectReference(orgId, syncGithubIssueRequest);

		final ImportIssuesRequest importIssuesRequest = ImportIssuesRequest.builder()
				.oAuthToken(githubConfigRepository.getConfigByNameAndOrg(ACCESS_TOKEN, orgId))
				.externalProjectReferenceId(externalProjectReference.getExternalProjectReferenceId())
				.repoId(externalProjectReference.getExternalProjectReference())
				.repoOwner(externalProjectReference.getProjectOwner())
				.externalProjectType(externalProjectReference.getExternalProjectType())
				.orgId(orgId)
				.githubIssueLinkMatcher(githubService.getDefaultLinkMatcher())
				.issueNoList(ImmutableList.of(syncGithubIssueRequest.getIssueNumber()))
				.projectId(externalProjectReference.getProjectId())
				.build();

		issueImporterService.importIssues(ImmutableList.of(importIssuesRequest), githubImporterService);
	}

	@NonNull
	private ExternalProjectReference resolveExternalProjectReference(@NonNull final OrgId orgId, @NonNull final SyncGithubIssueRequest syncGithubIssueRequest)
	{
		final GetExternalProjectRequest getExternalProjectRequest = GetExternalProjectRequest.builder()
				.externalSystem(ExternalSystem.GITHUB)
				.orgId(orgId)
				.externalProjectOwner(syncGithubIssueRequest.getOwner())
				.externalReference(syncGithubIssueRequest.getRepositoryName())
				.build();

		return externalProjectRepository.getByRequestOptional(getExternalProjectRequest)
				.orElseThrow(() -> new AdempiereException("No S_ExternalProjectReference found!")
						.appendParametersToMessage()
						.setParameter("GetExternalProjectRequest", getExternalProjectRequest));
	}

	@NonNull
	private static SyncGithubIssueRequest getRequest(@NonNull final String githubPayload) throws JsonProcessingException
	{
		final JsonNode githubPayloadNode = JsonObjectMapperHolder
				.sharedJsonObjectMapper()
				.readTree(githubPayload);

		return SyncGithubIssueRequest.from(githubPayloadNode);
	}
}
