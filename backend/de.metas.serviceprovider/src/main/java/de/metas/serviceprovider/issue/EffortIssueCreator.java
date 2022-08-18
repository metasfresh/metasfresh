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

package de.metas.serviceprovider.issue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.activity.repository.CreateActivityRequest;
import de.metas.issue.tracking.github.api.v3.model.CreateIssueRequest;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.service.GithubClient;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.label.IssueLabelService;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.github.GithubImporterConstants;
import de.metas.serviceprovider.github.GithubImporterService;
import de.metas.serviceprovider.github.GithubService;
import de.metas.serviceprovider.github.config.GithubConfigRepository;
import de.metas.serviceprovider.issue.activity.CostCenterActivityService;
import de.metas.serviceprovider.issue.importer.IssueImporterService;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.metas.serviceprovider.github.GithubConstants.COST_CENTER_LABEL_PREFIX;
import static de.metas.serviceprovider.github.config.GithubConfigName.ACCESS_TOKEN;

@Service
public class EffortIssueCreator
{
	private final IssueRepository issueRepository;
	private final CostCenterActivityService costCenterActivityService;
	private final IssueLabelService issueLabelService;
	private final ExternalProjectRepository externalProjectRepository;
	private final GithubConfigRepository githubConfigRepository;
	private final GithubClient githubClient;
	private final GithubService githubService;
	private final IssueImporterService issueImporterService;
	private final GithubImporterService githubImporterService;

	public EffortIssueCreator(
			@NonNull final IssueRepository issueRepository,
			@NonNull final CostCenterActivityService costCenterActivityService,
			@NonNull final IssueLabelService issueLabelService,
			@NonNull final ExternalProjectRepository externalProjectRepository,
			@NonNull final GithubConfigRepository githubConfigRepository,
			@NonNull final GithubClient githubClient,
			@NonNull final GithubService githubService,
			@NonNull final IssueImporterService issueImporterService,
			@NonNull final GithubImporterService githubImporterService)
	{
		this.issueRepository = issueRepository;
		this.costCenterActivityService = costCenterActivityService;
		this.issueLabelService = issueLabelService;
		this.externalProjectRepository = externalProjectRepository;
		this.githubConfigRepository = githubConfigRepository;
		this.githubClient = githubClient;
		this.githubService = githubService;
		this.issueImporterService = issueImporterService;
		this.githubImporterService = githubImporterService;
	}

	public void createFromIssue(@NonNull final IssueId issueId) throws JsonProcessingException
	{
		final IssueEntity issue = issueRepository.getById(issueId);

		final ExternalProjectReference effortExternalProjectReference = getEffortExternalProjectReference(issue.getExternalProjectReferenceId());

		final Issue githubEffortIssue = createGithubEffortIssue(effortExternalProjectReference, issue);

		// importGithubEffortIssue(effortExternalProjectReference, String.valueOf(githubEffortIssue.getNumber())); //todo fp
	}

	@NonNull
	private Issue createGithubEffortIssue(
			@NonNull final ExternalProjectReference externalProjectReference,
			@NonNull final IssueEntity issue) throws JsonProcessingException
	{
		final CreateIssueRequest createEffortIssueRequest = CreateIssueRequest.builder()
				.repositoryName(externalProjectReference.getExternalProjectReference())
				.repositoryOwner(externalProjectReference.getProjectOwner())
				.oAuthToken(githubConfigRepository.getConfigByNameAndOrg(ACCESS_TOKEN, externalProjectReference.getOrgId()))
				.title(issue.getName())
				.body(issue.getExternalIssueURL())
				.labels(getLabels(issue))
				.build();

		return githubClient.createIssue(createEffortIssueRequest);
	}

	private void importGithubEffortIssue(
			@NonNull final ExternalProjectReference externalProjectReference,
			@NonNull final String issueNumber)
	{
		final OrgId orgId = externalProjectReference.getOrgId();

		final ImportIssuesRequest importEffortIssuesRequest = ImportIssuesRequest.builder()
				.oAuthToken(githubConfigRepository.getConfigByNameAndOrg(ACCESS_TOKEN, orgId))
				.externalProjectReferenceId(externalProjectReference.getExternalProjectReferenceId())
				.repoId(externalProjectReference.getExternalProjectReference())
				.repoOwner(externalProjectReference.getProjectOwner())
				.externalProjectType(externalProjectReference.getExternalProjectType())
				.orgId(orgId)
				.githubIssueLinkMatcher(githubService.getDefaultLinkMatcher())
				.issueNoList(ImmutableList.of(issueNumber))
				.projectId(externalProjectReference.getProjectId())
				.build();

		issueImporterService.importIssues(ImmutableList.of(importEffortIssuesRequest), githubImporterService);
	}

	@NonNull
	private List<String> getLabels(@NonNull final IssueEntity issue)
	{
		final List<IssueLabel> issueLabels = issueLabelService.getByIssueId(issue.getIssueId());

		final ImmutableList.Builder<IssueLabel> issueLabelCollector = ImmutableList.builder();

		matchLabel(issueLabels, GithubImporterConstants.LabelType.CUSTOMER)
				.ifPresent(issueLabelCollector::add);

		matchLabel(issueLabels, GithubImporterConstants.LabelType.BUDGET)
				.ifPresent(issueLabelCollector::add);

		issueLabelCollector.add(getCostCenterIssueLabel(issue, issueLabels));

		return issueLabelCollector.build()
				.stream()
				.map(IssueLabel::getValue)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private IssueLabel getCostCenterIssueLabel(@NonNull final IssueEntity issue, @NonNull final List<IssueLabel> issueLabelList)
	{
		final Optional<IssueLabel> costCenterLabelOpt = matchLabel(issueLabelList, GithubImporterConstants.LabelType.COST_CENTER);

		if (costCenterLabelOpt.isPresent())
		{
			final IssueLabel existingCostCenterLabel = costCenterLabelOpt.get();

			final String costCenterValue = existingCostCenterLabel.getLabelValue(GithubImporterConstants.LabelType.COST_CENTER)
					.orElseThrow(() -> new AdempiereException("Cost center label value should not be empty at this stage"));

			getOrCreateCostCenter(existingCostCenterLabel.getOrgId(), costCenterValue, costCenterValue);

			return existingCostCenterLabel;
		}

		final IssueLabel costCenterLabel = createCostCenterLabelFromIssue(issue);

		final String costCenterValue = costCenterLabel.getLabelValue(GithubImporterConstants.LabelType.COST_CENTER)
				.orElseThrow(() -> new AdempiereException("Cost center label value should not be empty at this stage"));

		Services.get(ITrxManager.class).runInNewTrx((() -> getOrCreateCostCenter(issue.getOrgId(), costCenterValue, issue.getName())));

		return costCenterLabel;
	}

	private void getOrCreateCostCenter(@NonNull final OrgId orgId, @NonNull final String value, @NonNull final String name)
	{
		final CreateActivityRequest createActivityRequest = CreateActivityRequest.builder()
				.orgId(orgId)
				.value(value)
				.name(name)
				.build();

		costCenterActivityService.getOrCreateCostCenter(createActivityRequest);
	}

	@NonNull
	private IssueLabel createCostCenterLabelFromIssue(@NonNull final IssueEntity issue)
	{
		final IssueLabel newCostCenterLabel = IssueLabel.builder()
				.orgId(issue.getOrgId())
				.value(COST_CENTER_LABEL_PREFIX + issue.getSearchKey())
				.build();

		issueLabelService.createMissingRefListForLabels(ImmutableList.of(newCostCenterLabel));

		return newCostCenterLabel;
	}

	@NonNull
	private ExternalProjectReference getEffortExternalProjectReference(@NonNull final ExternalProjectReferenceId externalProjectReferenceId)
	{
		final ExternalProjectReference externalProjectReferenceBudget = externalProjectRepository.getById(externalProjectReferenceId);

		return Optional.ofNullable(externalProjectReferenceBudget.getExternalProjectReferenceEffortId())
				.map(externalProjectRepository::getById)
				.orElseThrow(() -> new AdempiereException("No external project reference effort set on external project reference")
						.appendParametersToMessage()
						.setParameter("ExternalProjectReferenceId", externalProjectReferenceId));
	}

	@NonNull
	private static Optional<IssueLabel> matchLabel(@NonNull final List<IssueLabel> issueLabels, @NonNull final GithubImporterConstants.LabelType labelType)
	{
		return issueLabels.stream()
				.filter(label -> label.matchesType(labelType))
				.findFirst();
	}
}
