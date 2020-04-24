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
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.issue.importer.IssueImporterService;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.model.I_S_ExternalProjectReference;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.stream.Stream;

import static de.metas.serviceprovider.external.ExternalSystem.GITHUB;
import static de.metas.serviceprovider.github.GithubImporterConstants.GitHubImporterSysConfig.ACCESS_TOKEN;

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
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);


	@Override
	protected String doIt() throws Exception
	{
		final ImmutableList<ImportIssuesRequest> importIssuesRequests =
				importAllProjects() ?  buildRequestsForAllRepos()
									:  ImmutableList.of(buildSpecificRequest(issueNumbers));

		issueImporterService.importIssues(importIssuesRequests, githubImporterService);

		return MSG_OK;
	}

	protected void setDateFrom(@NonNull final LocalDate dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	@NonNull
	private ImmutableList<ImportIssuesRequest> buildRequestsForAllRepos()
	{
		return externalProjectRepository.getByExternalSystem(GITHUB)
				.stream()
				.map(externalProject -> buildImportIssueRequest(externalProject, ImmutableList.of()))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImportIssuesRequest buildSpecificRequest(@Nullable final String issueNumbers)
	{
		final ExternalProjectReference externalProject =
				externalProjectRepository.getById(ExternalProjectReferenceId.ofRepoId(this.externalProjectReferenceId));

		final ImmutableList<String> issueNoList = Check.isNotBlank(issueNumbers)
				? Stream.of( issueNumbers.split(",") )
						.filter(Check::isNotBlank)
						.map(String::trim)
						.collect(ImmutableList.toImmutableList())
				: ImmutableList.of();

		return buildImportIssueRequest(externalProject, issueNoList);
	}

	@NonNull
	private ImportIssuesRequest buildImportIssueRequest(@NonNull final ExternalProjectReference externalProjectReference,
														@NonNull final ImmutableList<String> issueNoList)
	{
		return ImportIssuesRequest.builder()
				.externalProjectType(externalProjectReference.getExternalProjectType())
				.repoId(externalProjectReference.getExternalProjectReference())
				.repoOwner(externalProjectReference.getProjectOwner())
				.orgId(externalProjectReference.getOrgId())
				.projectId(externalProjectReference.getProjectId())
				.oAuthToken(sysConfigBL.getValue(ACCESS_TOKEN.getName()))
				.issueNoList(issueNoList)
				.dateFrom(this.dateFrom)
				.build();
	}

	private boolean importAllProjects()
	{
		return externalProjectReferenceId == 0;
	}
}
