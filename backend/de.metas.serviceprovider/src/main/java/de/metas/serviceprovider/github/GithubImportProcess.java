/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.github;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.github.config.GithubConfigRepository;
import de.metas.serviceprovider.github.link.GithubIssueLinkMatcher;
import de.metas.serviceprovider.issue.importer.IssueImporterService;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.model.I_S_ExternalProjectReference;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.stream.Stream;

import static de.metas.serviceprovider.external.ExternalSystem.GITHUB;
import static de.metas.serviceprovider.github.GithubImporterConstants.GitHubConfig.ACCESS_TOKEN;
import static de.metas.serviceprovider.github.GithubImporterConstants.GitHubConfig.LOOK_FOR_PARENT;

public class GithubImportProcess extends JavaProcess
{
	@Param(parameterName = I_S_ExternalProjectReference.COLUMNNAME_S_ExternalProjectReference_ID)
	private int externalProjectReferenceId;

	@Param(parameterName = "IssueNumbers")
	private String issueNumbers;

	@Param(parameterName = "DateFrom")
	private LocalDate dateFrom;

	private final ExternalProjectRepository externalProjectRepository = SpringContextHolder.instance.getBean(ExternalProjectRepository.class);
	private final IssueImporterService issueImporterService = SpringContextHolder.instance.getBean(IssueImporterService.class);
	private final GithubImporterService githubImporterService = SpringContextHolder.instance.getBean(GithubImporterService.class);
	private final GithubConfigRepository githubConfigRepository = SpringContextHolder.instance.getBean(GithubConfigRepository.class);


	@Override
	protected String doIt() throws Exception
	{
		final ImmutableList<ExternalProjectReference> allActiveGithubProjects =
				externalProjectRepository.getByExternalSystem(GITHUB);

		final GithubIssueLinkMatcher githubIssueLinkMatcher = getGithubLinkMatcher(allActiveGithubProjects);

		final ImmutableList<ImportIssuesRequest> importIssuesRequests =
				importAllProjects() ?  buildRequestsForAllRepos(allActiveGithubProjects, githubIssueLinkMatcher)
									:  ImmutableList.of(buildSpecificRequest(allActiveGithubProjects, issueNumbers, githubIssueLinkMatcher));

		issueImporterService.importIssues(importIssuesRequests, githubImporterService);

		return MSG_OK;
	}

	protected void setDateFrom(@NonNull final LocalDate dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	protected void setIssueNumbers(@NonNull final String issueNumbers)
	{
		this.issueNumbers = issueNumbers;
	}

	protected void setExternalProjectReferenceId(@NonNull final ExternalProjectReferenceId externalProjectReferenceId)
	{
		this.externalProjectReferenceId = externalProjectReferenceId.getRepoId();
	}

	@NonNull
	private ImmutableList<ImportIssuesRequest> buildRequestsForAllRepos(@NonNull final ImmutableList<ExternalProjectReference> githubProjects,
			                                                            @Nullable final GithubIssueLinkMatcher githubIssueLinkMatcher)
	{
		return githubProjects
				.stream()
				.map(externalProject -> buildImportIssueRequest(externalProject, ImmutableList.of(), githubIssueLinkMatcher))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImportIssuesRequest buildSpecificRequest(@NonNull final ImmutableList<ExternalProjectReference> allActiveGithubProjects,
													 @Nullable final String issueNumbers,
													 @Nullable final GithubIssueLinkMatcher githubIssueLinkMatcher)
	{
		final ExternalProjectReference externalProject =
				allActiveGithubProjects
						.stream()
						.filter(githubProject -> githubProject.getExternalProjectReferenceId().getRepoId() == this.externalProjectReferenceId)
						.findFirst()
						.orElseThrow(() -> new AdempiereException("The selected project is not a Github one!")
											.appendParametersToMessage()
											.setParameter("Selected externalProjectReferencedId", externalProjectReferenceId));

		final ImmutableList<String> issueNoList = Check.isNotBlank(issueNumbers)
				? Stream.of( issueNumbers.split(",") )
						.filter(Check::isNotBlank)
						.map(String::trim)
						.collect(ImmutableList.toImmutableList())
				: ImmutableList.of();

		return buildImportIssueRequest(externalProject, issueNoList, githubIssueLinkMatcher);
	}

	@NonNull
	private ImportIssuesRequest buildImportIssueRequest(@NonNull final ExternalProjectReference externalProjectReference,
														@NonNull final ImmutableList<String> issueNoList,
														@Nullable final GithubIssueLinkMatcher githubIssueLinkMatcher)
	{
		return ImportIssuesRequest.builder()
				.externalProjectType(externalProjectReference.getExternalProjectType())
				.externalProjectReferenceId(externalProjectReference.getExternalProjectReferenceId())
				.repoId(externalProjectReference.getExternalProjectReference())
				.repoOwner(externalProjectReference.getProjectOwner())
				.orgId(externalProjectReference.getOrgId())
				.projectId(externalProjectReference.getProjectId())
				.oAuthToken(githubConfigRepository.getValueByName(ACCESS_TOKEN.getName()))
				.issueNoList(issueNoList)
				.dateFrom(this.dateFrom)
				.githubIssueLinkMatcher(githubIssueLinkMatcher)
				.build();
	}

	@Nullable
	private GithubIssueLinkMatcher getGithubLinkMatcher(@NonNull final ImmutableList<ExternalProjectReference> externalProjectReferences)
	{
		final Boolean lookForParent = StringUtils.toBooleanOrNull(githubConfigRepository.getValueByName(LOOK_FOR_PARENT.getName()));

		if (!Boolean.TRUE.equals(lookForParent))
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

	private boolean importAllProjects()
	{
		return externalProjectReferenceId == 0;
	}
}
