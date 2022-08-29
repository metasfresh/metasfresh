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
import de.metas.issue.tracking.github.api.v3.model.CreateIssueRequest;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.service.GithubClient;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.label.IssueLabelService;
import de.metas.serviceprovider.external.label.LabelCollection;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.github.config.GithubConfigRepository;
import de.metas.serviceprovider.github.service.GithubIssueService;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.metas.serviceprovider.github.config.GithubConfigName.ACCESS_TOKEN;

@Service
public class EffortIssueCreator
{
	private final IssueRepository issueRepository;
	private final IssueLabelService issueLabelService;
	private final ExternalProjectRepository externalProjectRepository;
	private final GithubConfigRepository githubConfigRepository;
	private final GithubClient githubClient;
	private final GithubIssueService githubIssueService;

	public EffortIssueCreator(
			@NonNull final IssueRepository issueRepository,
			@NonNull final IssueLabelService issueLabelService,
			@NonNull final ExternalProjectRepository externalProjectRepository,
			@NonNull final GithubConfigRepository githubConfigRepository,
			@NonNull final GithubClient githubClient,
			@NonNull final GithubIssueService githubIssueService)
	{
		this.issueRepository = issueRepository;
		this.issueLabelService = issueLabelService;
		this.externalProjectRepository = externalProjectRepository;
		this.githubConfigRepository = githubConfigRepository;
		this.githubClient = githubClient;
		this.githubIssueService = githubIssueService;
	}

	public void createFromBudgetIssue(@NonNull final IssueId budgetIssueId) throws JsonProcessingException
	{
		final IssueEntity budgetIssue = issueRepository.getById(budgetIssueId);

		if (budgetIssue.isEffortIssue())
		{
			throw new AdempiereException("S_Issue must be of type BUDGET.")
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", budgetIssueId.getRepoId());
		}

		final Issue githubEffortIssue = createGithubEffortIssue(budgetIssue);

		githubIssueService.importByURL(budgetIssue.getOrgId(), githubEffortIssue.getHtmlUrl());
	}

	@NonNull
	private Issue createGithubEffortIssue(@NonNull final IssueEntity budgetIssue) throws JsonProcessingException
	{
		final ExternalProjectReference effortExternalProjectReference = getEffortExternalProjectReference(budgetIssue.getExternalProjectReferenceId());

		final CreateIssueRequest createEffortIssueRequest = CreateIssueRequest.builder()
				.repositoryName(effortExternalProjectReference.getExternalProjectReference())
				.repositoryOwner(effortExternalProjectReference.getProjectOwner())
				.oAuthToken(getGithubAuthToken(effortExternalProjectReference.getOrgId()))
				.title(budgetIssue.getName())
				.body(budgetIssue.getExternalIssueURL())
				.labels(getLabels(budgetIssue))
				.build();

		return githubClient.createIssue(createEffortIssueRequest);
	}

	@NonNull
	private List<String> getLabels(@NonNull final IssueEntity issue)
	{
		final LabelCollection issueLabelCollection = issueLabelService.getByIssueId(issue.getIssueId());

		final ImmutableList<GithubImporterConstants.LabelType> labelTypeToImport = ImmutableList.of(GithubImporterConstants.LabelType.COST_CENTER,
																									GithubImporterConstants.LabelType.CUSTOMER,
																									GithubImporterConstants.LabelType.BUDGET);
		return issueLabelCollection.streamByType(labelTypeToImport)
				.map(IssueLabel::getValue)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ExternalProjectReference getEffortExternalProjectReference(@NonNull final ExternalProjectReferenceId externalProjectReferenceId)
	{
		final ExternalProjectReference externalProjectReferenceBudget = externalProjectRepository.getById(externalProjectReferenceId);

		return Optional.ofNullable(externalProjectReferenceBudget.getExternalProjectReferenceEffortId())
				.map(externalProjectRepository::getById)
				.orElseThrow(() -> new AdempiereException("No external project reference effort set on external project reference")
						.appendParametersToMessage()
						.setParameter("ExternalProjectReferenceId", externalProjectReferenceId.getRepoId())
						.markAsUserValidationError());
	}

	@NonNull
	private String getGithubAuthToken(@NonNull final OrgId orgId)
	{
		return githubConfigRepository.getConfigByNameAndOrg(ACCESS_TOKEN, orgId);
	}
}
