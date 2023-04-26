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

package de.metas.serviceprovider.github.service;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.external.project.GetExternalProjectRequest;
import de.metas.serviceprovider.github.GithubIdSearchKey;
import de.metas.serviceprovider.github.GithubImporterService;
import de.metas.serviceprovider.github.GithubService;
import de.metas.serviceprovider.github.config.GithubConfigRepository;
import de.metas.serviceprovider.github.link.GithubIssueLink;
import de.metas.serviceprovider.github.link.GithubIssueLinkResolver;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.importer.IssueImporterService;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static de.metas.serviceprovider.github.config.GithubConfigName.ACCESS_TOKEN;

@Service
public class GithubIssueService
{
	private final ExternalProjectRepository externalProjectRepository;
	private final GithubConfigRepository githubConfigRepository;
	private final IssueImporterService issueImporterService;
	private final GithubImporterService githubImporterService;
	private final GithubService githubService;
	private final IssueRepository issueRepository;

	public GithubIssueService(
			@NonNull final ExternalProjectRepository externalProjectRepository,
			@NonNull final GithubConfigRepository githubConfigRepository,
			@NonNull final IssueImporterService issueImporterService,
			@NonNull final GithubImporterService githubImporterService,
			@NonNull final GithubService githubService,
			@NonNull final IssueRepository issueRepository)
	{
		this.externalProjectRepository = externalProjectRepository;
		this.githubConfigRepository = githubConfigRepository;
		this.issueImporterService = issueImporterService;
		this.githubImporterService = githubImporterService;
		this.githubService = githubService;
		this.issueRepository = issueRepository;
	}

	@NonNull
	public Optional<IssueId> importByURL(@NonNull final OrgId orgId, @NonNull final String issueURL)
	{
		final GithubIssueLink githubIssueLink = GithubIssueLinkResolver.resolve(issueURL)
				.orElseThrow(() -> new AdempiereException("Could not resolve GithubIssueLink from url")
						.appendParametersToMessage()
						.setParameter("issueURL", issueURL));

		final GithubIdSearchKey githubIdSearchKey = githubIssueLink.getGithubIdSearchKey();

		final ExternalProjectReference githubProjectReference = resolveExternalProjectReference(orgId, githubIdSearchKey.getRepositoryOwner(), githubIdSearchKey.getRepository());

		importIssuesFor(githubProjectReference, ImmutableList.of(githubIdSearchKey.getIssueNo()));

		return issueRepository.getByExternalURLOptional(issueURL)
				.map(IssueEntity::getIssueId);
	}

	@NonNull
	private Set<IssueId> importIssuesFor(
			@NonNull final ExternalProjectReference externalProjectReference,
			@NonNull final ImmutableList<String> issueNoList)
	{
		final ImportIssuesRequest importIssuesRequest = ImportIssuesRequest.builder()
				.oAuthToken(getGithubAuthToken(externalProjectReference.getOrgId()))
				.externalProjectReferenceId(externalProjectReference.getExternalProjectReferenceId())
				.repoId(externalProjectReference.getExternalProjectReference())
				.repoOwner(externalProjectReference.getProjectOwner())
				.externalProjectType(externalProjectReference.getExternalProjectType())
				.orgId(externalProjectReference.getOrgId())
				.githubIssueLinkMatcher(githubService.getDefaultLinkMatcher())
				.issueNoList(issueNoList)
				.projectId(externalProjectReference.getProjectId())
				.build();

		return issueImporterService.importIssues(ImmutableList.of(importIssuesRequest), githubImporterService);
	}

	@NonNull
	private ExternalProjectReference resolveExternalProjectReference(
			@NonNull final OrgId orgId,
			@NonNull final String owner,
			@NonNull final String repositoryName)
	{

		final GetExternalProjectRequest getExternalProjectRequest = GetExternalProjectRequest.builder()
				.externalSystem(ExternalSystem.GITHUB)
				.orgId(orgId)
				.externalProjectOwner(owner)
				.externalReference(repositoryName)
				.build();

		return externalProjectRepository.getByRequestOptional(getExternalProjectRequest)
				.orElseThrow(() -> new AdempiereException("No S_ExternalProjectReference found!")
						.appendParametersToMessage()
						.setParameter("orgId", orgId.getRepoId())
						.setParameter("owner", owner)
						.setParameter("repositoryName", repositoryName));
	}

	@NonNull
	private String getGithubAuthToken(@NonNull final OrgId orgId)
	{
		return githubConfigRepository.getConfigByNameAndOrg(ACCESS_TOKEN, orgId);
	}
}
