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
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.importer.IssueImporterService;
import de.metas.serviceprovider.importer.info.ImportIssuesRequest;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

import static de.metas.serviceprovider.external.project.ExternalSystem.GITHUB;
import static de.metas.serviceprovider.github.GithubImporterConstants.GitHubImporterSysConfig.ACCESS_TOKEN;

public class GithubImportProcess extends JavaProcess
{
	private final String PARAM_NAME_EXTERNAL_PROJECT_REFERENCE_ID = "S_ExternalProjectReference_ID";
	private final String PARAM_NAME_ISSUE_NO = "IssueNumbers";

	private int externalProjectReferenceId;
	private ImmutableList<String> issueNoList;

	private final ExternalProjectRepository externalProjectRepository = SpringContextHolder.instance.getBean(ExternalProjectRepository.class);
	private final IssueImporterService issueImporterService = SpringContextHolder.instance.getBean(IssueImporterService.class);
	private final GithubImporterService githubImporterService = SpringContextHolder.instance.getBean(GithubImporterService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);


	@Override protected String doIt() throws Exception
	{
		getParameters().forEach(param -> {
			if (param.getParameterName().equalsIgnoreCase(PARAM_NAME_EXTERNAL_PROJECT_REFERENCE_ID))
			{
				externalProjectReferenceId = param.getParameterAsInt();
			}
			else if (param.getParameterName().equalsIgnoreCase(PARAM_NAME_ISSUE_NO))
			{
				issueNoList = Check.isNotBlank(param.getParameterAsString())
						? ImmutableList.copyOf(param.getParameterAsString().split(","))
						: ImmutableList.of();
			}
		});

		final ImmutableList<ImportIssuesRequest> importIssuesRequests =
				importAll() ?  buildRequestsForAllRepos()
							:  ImmutableList.of(buildSpecificRequest(externalProjectReferenceId, issueNoList));

		issueImporterService.importIssues(importIssuesRequests, githubImporterService);

		return MSG_OK;
	}

	private ImmutableList<ImportIssuesRequest> buildRequestsForAllRepos()
	{
		return externalProjectRepository.loadExternalProjectsBySystem(GITHUB)
				.stream()
				.map(externalProject -> buildImportIssueRequest(externalProject, ImmutableList.of()))
				.collect(ImmutableList.toImmutableList());
	}

	private ImportIssuesRequest buildSpecificRequest(final int externalProjectReferenceId, final ImmutableList<String> issueNoList)
	{
		final ExternalProjectReference externalProject =
				externalProjectRepository.getById(ExternalProjectReferenceId.ofRepoId(externalProjectReferenceId));

		return buildImportIssueRequest(externalProject, issueNoList);
	}

	private ImportIssuesRequest buildImportIssueRequest(final ExternalProjectReference externalProjectReference,
			final ImmutableList<String> issueNoList)
	{
		return ImportIssuesRequest.builder()
				.externalProjectType(externalProjectReference.getExternalProjectType())
				.repoId(externalProjectReference.getExternalProjectReference())
				.repoOwner(externalProjectReference.getProjectOwner())
				.orgId(externalProjectReference.getOrgId())
				.oAuthToken(sysConfigBL.getValue(ACCESS_TOKEN.getName()))
				.issueNoList(issueNoList)
				.build();
	}

	private boolean importAll()
	{
		return externalProjectReferenceId == 0 && issueNoList.isEmpty();
	}
}
