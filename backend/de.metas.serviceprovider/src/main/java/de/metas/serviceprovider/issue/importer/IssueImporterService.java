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

package de.metas.serviceprovider.issue.importer;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.activity.repository.ActivityRepository;
import de.metas.externalreference.ExternalId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.label.IssueLabelService;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.github.GithubImporterConstants;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueType;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.issue.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.issue.importer.info.ImportMilestoneInfo;
import de.metas.serviceprovider.milestone.Milestone;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.milestone.MilestoneRepository;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static de.metas.serviceprovider.issue.Status.INVOICED;
import static de.metas.serviceprovider.issue.importer.ImportConstants.IMPORT_LOG_MESSAGE_PREFIX;

@Service
public class IssueImporterService
{
	private static final Logger log = LogManager.getLogger(IssueImporterService.class);

	private final ImportQueue<ImportIssueInfo> importIssuesQueue;
	private final MilestoneRepository milestoneRepository;
	private final IssueRepository issueRepository;
	private final ExternalReferenceRepository externalReferenceRepository;
	private final ITrxManager trxManager;
	private final IssueLabelService issueLabelService;
	private final ActivityRepository activityRepository;

	public IssueImporterService(
			final ImportQueue<ImportIssueInfo> importIssuesQueue,
			final MilestoneRepository milestoneRepository,
			final IssueRepository issueRepository,
			final ExternalReferenceRepository externalReferenceRepository,
			final ITrxManager trxManager,
			final IssueLabelService issueLabelService,
			final ActivityRepository activityRepository)
	{
		this.importIssuesQueue = importIssuesQueue;
		this.milestoneRepository = milestoneRepository;
		this.issueRepository = issueRepository;
		this.externalReferenceRepository = externalReferenceRepository;
		this.trxManager = trxManager;
		this.issueLabelService = issueLabelService;
		this.activityRepository = activityRepository;
	}

	@NonNull
	public ImmutableSet<IssueId> importIssues(
			@NonNull final ImmutableList<ImportIssuesRequest> requestList,
			@NonNull final IssueImporter issueImporter)
	{
		final CompletableFuture<Void> completableFuture =
				CompletableFuture.runAsync(() -> issueImporter.start(requestList));

		final ArrayList<IssueId> importedIdsCollector = new ArrayList<>();

		while (!completableFuture.isDone() || !importIssuesQueue.isEmpty())
		{
			final ImmutableList<ImportIssueInfo> issueInfos = importIssuesQueue.drainAll();

			issueInfos.forEach(issue -> trxManager.runInNewTrx(() -> importIssue(issue, importedIdsCollector)));

			issueRepository.invalidateCacheForIds(ImmutableList.copyOf(importedIdsCollector));
		}

		if (completableFuture.isCompletedExceptionally())
		{
			extractAndPropagateAdempiereException(completableFuture);
		}

		return ImmutableSet.copyOf(importedIdsCollector);
	}

	@VisibleForTesting
	void importIssue(
			@NonNull final ImportIssueInfo importIssueInfo,
			@NonNull final List<IssueId> importedIdsCollector)
	{
		try
		{
			final IssueId existingIssueId = getIssueIdByExternalId(importIssueInfo.getExternalIssueId(), importIssueInfo.getOrgId());
			final Optional<IssueEntity> existingEffortIssue = existingIssueId != null
					? Optional.of(issueRepository.getById(existingIssueId))
					: Optional.empty();

			final boolean processed = existingEffortIssue.map(IssueEntity::isProcessed).orElse(false);
			if (processed)
			{
				log.debug("Skip updating already processed issue: {}", existingEffortIssue);
				return;
			}

			final ImportIssueInfo importIssueInfoWithCustomLabels = appendCustomLabels(importIssueInfo, existingEffortIssue);

			if (importIssueInfoWithCustomLabels.getMilestone() != null)
			{
				importMilestone(importIssueInfoWithCustomLabels.getMilestone());
			}

			final IssueEntity issueEntity = existingEffortIssue
					.map(issue -> mergeIssueInfoWithEntity(importIssueInfoWithCustomLabels, issue))
					.orElseGet(() -> buildIssue(importIssueInfoWithCustomLabels));

			issueRepository.save(issueEntity);

			if (!existingEffortIssue.isPresent())
			{
				final ExternalReference issueExternalRef = ExternalReference
						.builder()
						.externalSystem(importIssueInfoWithCustomLabels.getExternalIssueId().getExternalSystem())
						.externalReferenceType(ExternalServiceReferenceType.ISSUE_ID)
						.externalReference(importIssueInfoWithCustomLabels.getExternalIssueId().getId())
						.orgId(issueEntity.getOrgId())
						.recordId(issueEntity.getIssueId().getRepoId())
						.build();

				externalReferenceRepository.save(issueExternalRef);
			}

			issueLabelService.persistLabels(issueEntity.getIssueId(), importIssueInfoWithCustomLabels.getIssueLabels());

			importedIdsCollector.add(issueEntity.getIssueId());
		}
		catch (final Exception e)
		{
			Loggables.withLogger(log, Level.ERROR)
					.addLog(" {} *** Error while importing issue: {}, errorMessage: {}",
							IMPORT_LOG_MESSAGE_PREFIX, importIssueInfo.toString(), e.getMessage(), e);
		}
	}

	private void importMilestone(@NonNull final ImportMilestoneInfo importMilestoneInfo)
	{
		final Milestone milestone = buildMilestone(importMilestoneInfo);

		final boolean isNew = milestone.getMilestoneId() == null;

		milestoneRepository.save(milestone);

		importMilestoneInfo.setMilestoneId(milestone.getMilestoneId());

		if (isNew)
		{
			final ExternalReference externalReference = ExternalReference
					.builder()
					.externalSystem(importMilestoneInfo.getExternalId().getExternalSystem())
					.externalReferenceType(ExternalServiceReferenceType.MILESTONE_ID)
					.externalReference(importMilestoneInfo.getExternalId().getId())
					.recordId(milestone.getMilestoneId().getRepoId())
					.orgId(milestone.getOrgId())
					.build();

			externalReferenceRepository.save(externalReference);
		}
	}

	private Milestone buildMilestone(@NonNull final ImportMilestoneInfo milestone)
	{
		return Milestone.builder()
				.milestoneId(getMilestoneIdByExternalId(milestone.getExternalId(), milestone.getOrgId()))
				.name(milestone.getName())
				.description(milestone.getDescription())
				.externalURL(milestone.getExternalURL())
				.processed(milestone.isProcessed())
				.dueDate(milestone.getDueDate())
				.value(milestone.getName())
				.orgId(milestone.getOrgId())
				.build();
	}

	@NonNull
	private IssueEntity buildIssue(@NonNull final ImportIssueInfo importIssueInfo)
	{
		final MilestoneId milestoneId = importIssueInfo.getMilestone() != null
				? importIssueInfo.getMilestone().getMilestoneId()
				: null;

		final IssueId parentIssueId = importIssueInfo.getParentIssueId() != null
				? importIssueInfo.getParentIssueId()
				: getIssueIdByExternalId(importIssueInfo.getExternalParentIssueId(), importIssueInfo.getOrgId());

		return IssueEntity.builder()
				.clientId(Env.getClientId())
				.orgId(importIssueInfo.getOrgId())
				.externalProjectReferenceId(importIssueInfo.getExternalProjectReferenceId())
				.projectId(importIssueInfo.getProjectId())
				.parentIssueId(parentIssueId)
				.assigneeId(importIssueInfo.getAssigneeId())
				.milestoneId(milestoneId)
				.name(importIssueInfo.getName())
				.searchKey(importIssueInfo.getSearchKey())
				.description(importIssueInfo.getDescription())
				.type(IssueType.EXTERNAL)
				.isEffortIssue(importIssueInfo.isEffortIssue())
				.estimatedEffort(importIssueInfo.getEstimation())
				.budgetedEffort(importIssueInfo.getBudget())
				.roughEstimation(importIssueInfo.getRoughEstimation())
				.effortUomId(importIssueInfo.getEffortUomId())
				.externalIssueNo(NumberUtils.asBigDecimal(importIssueInfo.getExternalIssueNo()))
				.externalIssueURL(importIssueInfo.getExternalIssueURL())
				.issueEffort(Effort.ZERO)
				.aggregatedEffort(Effort.ZERO)
				.status(importIssueInfo.getStatus())
				.deliveryPlatform(importIssueInfo.getDeliveryPlatform())
				.plannedUATDate(importIssueInfo.getPlannedUATDate())
				.deliveredDate(importIssueInfo.getDeliveredDate())
				.externallyUpdatedAt(importIssueInfo.getUpdatedAt())
				.build();
	}

	@NonNull
	private IssueEntity mergeIssueInfoWithEntity(
			@NonNull final ImportIssueInfo importIssueInfo,
			@NonNull final IssueEntity existingEffortIssue)
	{
		final IssueId parentIssueId = importIssueInfo.getParentIssueId() != null
				? importIssueInfo.getParentIssueId()
				: getIssueIdByExternalId(importIssueInfo.getExternalParentIssueId(), importIssueInfo.getOrgId());

		final MilestoneId milestoneId = importIssueInfo.getMilestone() != null
				? importIssueInfo.getMilestone().getMilestoneId()
				: null;

		//INVOICED can be set only from mf, after the issue was invoiced, so it should be preserved
		final Status status = INVOICED.equals(existingEffortIssue.getStatus())
				? INVOICED
				: importIssueInfo.getStatus();

		final IssueEntity mergedIssueEntity = existingEffortIssue
				.toBuilder()
				.name(importIssueInfo.getName())
				.parentIssueId(parentIssueId)
				.externalProjectReferenceId(importIssueInfo.getExternalProjectReferenceId())
				.projectId(importIssueInfo.getProjectId())
				.assigneeId(importIssueInfo.getAssigneeId())
				.isEffortIssue(importIssueInfo.isEffortIssue())
				.description(importIssueInfo.getDescription())
				.externalIssueNo(NumberUtils.asBigDecimal(importIssueInfo.getExternalIssueNo()))
				.externalIssueURL(importIssueInfo.getExternalIssueURL())
				.milestoneId(milestoneId)
				.status(status)
				.deliveryPlatform(importIssueInfo.getDeliveryPlatform())
				.plannedUATDate(importIssueInfo.getPlannedUATDate())
				.roughEstimation(importIssueInfo.getRoughEstimation())
				.deliveredDate(importIssueInfo.getDeliveredDate())
				.budgetedEffort(importIssueInfo.getBudget())
				.externallyUpdatedAt(importIssueInfo.getUpdatedAt())
				.build();

		mergedIssueEntity.setEstimatedEffortIfNotSet(importIssueInfo.getEstimation());

		return mergedIssueEntity;
	}

	@Nullable
	private IssueId getIssueIdByExternalId(@Nullable final ExternalId externalId,
			@NonNull final OrgId orgId)
	{
		if (externalId == null)
		{
			return null;
		}

		final Integer issueId = externalReferenceRepository.getReferencedRecordIdOrNullBy(
				ExternalReferenceQuery.builder()
						.orgId(orgId)
						.externalSystem(externalId.getExternalSystem())
						.externalReference(externalId.getId())
						.externalReferenceType(ExternalServiceReferenceType.ISSUE_ID)
						.build());

		return IssueId.ofRepoIdOrNull(issueId);
	}

	@Nullable
	private MilestoneId getMilestoneIdByExternalId(@NonNull final ExternalId externalId,
			@NonNull final OrgId orgId)
	{
		final Integer milestoneId = externalReferenceRepository.getReferencedRecordIdOrNullBy(
				ExternalReferenceQuery.builder()
						.orgId(orgId)
						.externalSystem(externalId.getExternalSystem())
						.externalReference(externalId.getId())
						.externalReferenceType(ExternalServiceReferenceType.MILESTONE_ID)
						.build());

		return MilestoneId.ofRepoIdOrNull(milestoneId);
	}

	private void extractAndPropagateAdempiereException(final CompletableFuture completableFuture)
	{
		try
		{
			completableFuture.get();
		}
		catch (final ExecutionException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex.getCause());
		}
		catch (final InterruptedException ex1)
		{
			throw AdempiereException.wrapIfNeeded(ex1);
		}
	}

	@NonNull
	public ImportIssueInfo appendCustomLabels(@NonNull final ImportIssueInfo importIssueInfo, @NonNull final Optional<IssueEntity> existingIssueEntity)
	{
		if (importIssueInfo.isEffortIssue())
		{
			return importIssueInfo;
		}

		final IssueLabel costCenterLabel = getOrCreateCostCenterLabel(importIssueInfo, existingIssueEntity);

		final boolean containsCostCenterLabel = !importIssueInfo.filterLabels(costCenterLabel::equals).isEmpty();

		if (containsCostCenterLabel)
		{
			return importIssueInfo;
		}

		final ImmutableList<IssueLabel> allLabels = Stream.concat(importIssueInfo.getIssueLabels().stream(), Stream.of(costCenterLabel))
				.collect(ImmutableList.toImmutableList());

		return importIssueInfo.withIssueLabels(allLabels);
	}

	@NonNull
	private IssueLabel getOrCreateCostCenterLabel(@NonNull final ImportIssueInfo importIssueInfo, @NonNull final Optional<IssueEntity> existingIssue)
	{
		final IssueLabel costCenterLabel = importIssueInfo.getSingleLabel(label -> label.matchesType(GithubImporterConstants.LabelType.COST_CENTER))
				.orElse(null);

		if (costCenterLabel != null)
		{
			return costCenterLabel;
		}

		return existingIssue
				.filter(entity -> entity.getCostCenterActivityId() != null)
				.map(IssueEntity::getCostCenterActivityId)
				.map(activityRepository::getById)
				.map(activity -> IssueLabel.builder()
						.value(GithubImporterConstants.LabelType.COST_CENTER.wrapValue(activity.getValue()))
						.orgId(activity.getOrgId())
						.build())
				.orElseGet(() -> IssueLabel.builder()
						.value(GithubImporterConstants.LabelType.COST_CENTER.wrapValue(importIssueInfo.getRepositoryName() + "_" + importIssueInfo.getExternalIssueNo()))
						.orgId(importIssueInfo.getOrgId())
						.build());
	}
}
