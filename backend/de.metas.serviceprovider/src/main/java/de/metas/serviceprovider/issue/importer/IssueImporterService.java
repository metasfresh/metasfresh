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
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.external.reference.ExternalReference;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.external.reference.GetReferencedIdRequest;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueType;
import de.metas.serviceprovider.issue.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.issue.importer.info.ImportMilestoneInfo;
import de.metas.serviceprovider.milestone.Milestone;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.milestone.MilestoneRepository;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

	public IssueImporterService(final ImportQueue<ImportIssueInfo> importIssuesQueue, final MilestoneRepository milestoneRepository, final IssueRepository issueRepository, final ExternalReferenceRepository externalReferenceRepository, final ITrxManager trxManager)
	{
		this.importIssuesQueue = importIssuesQueue;
		this.milestoneRepository = milestoneRepository;
		this.issueRepository = issueRepository;
		this.externalReferenceRepository = externalReferenceRepository;
		this.trxManager = trxManager;
	}

	public void importIssues(@NonNull final ImmutableList<ImportIssuesRequest> requestList,
			                 @NonNull final IssueImporter issueImporter)
	{
		final CompletableFuture completableFuture =
				CompletableFuture.runAsync(() -> issueImporter.start(requestList));

		while (!completableFuture.isDone() || !importIssuesQueue.isEmpty())
		{
			final ImmutableList<ImportIssueInfo> issueInfos = importIssuesQueue.drainAll();
			issueInfos.forEach(issue -> trxManager.runInNewTrx( () -> importIssue(issue)));
		}

		if (completableFuture.isCompletedExceptionally())
		{
			extractAndPropagateAdempiereException(completableFuture);
		}
	}

	private void importIssue(@NonNull final ImportIssueInfo importIssueInfo)
	{
		try
		{
			if (importIssueInfo.getMilestone() != null)
			{
				importMilestone(importIssueInfo.getMilestone());
			}

			final IssueId issueId = getIssueIdByExternalId(importIssueInfo.getExternalIssueId());

			final Optional<IssueEntity> existingEffortIssue = issueId != null
					? Optional.of(issueRepository.getById(issueId, false))
					: Optional.empty();

			final IssueEntity issueEntity;
			issueEntity = existingEffortIssue
					.map(issue -> mergeIssueInfoWithEntity(importIssueInfo, issue))
					.orElseGet(() -> buildIssue(importIssueInfo));

			issueRepository.saveWithDetails(issueEntity);

			if (!existingEffortIssue.isPresent())
			{
				final ExternalReference issueExternalRef = ExternalReference
						.builder()
						.externalSystem(importIssueInfo.getExternalIssueId().getExternalSystem())
						.externalReferenceType(ExternalReferenceType.ISSUE_ID)
						.externalReference(importIssueInfo.getExternalIssueId().getId())
						.orgId(issueEntity.getOrgId())
						.recordId(issueEntity.getIssueId().getRepoId())
						.build();

				externalReferenceRepository.save(issueExternalRef);
			}
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
					.externalReferenceType(ExternalReferenceType.MILESTONE_ID)
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
				.milestoneId(getMilestoneIdByExternalId(milestone.getExternalId()))
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

		return IssueEntity.builder()
				.orgId(importIssueInfo.getOrgId())
				.projectId(importIssueInfo.getProjectId())
				.assigneeId(importIssueInfo.getAssigneeId())
				.milestoneId(milestoneId)
				.name(importIssueInfo.getName())
				.searchKey( importIssueInfo.getSearchKey() )
				.description(importIssueInfo.getDescription())
				.type(IssueType.EXTERNAL)
				.isEffortIssue(ExternalProjectType.EFFORT.equals(importIssueInfo.getExternalProjectType()))
				.processed(importIssueInfo.isProcessed())
				.estimatedEffort(importIssueInfo.getEstimation())
				.budgetedEffort(importIssueInfo.getBudget())
				.effortUomId(importIssueInfo.getEffortUomId())
				.externalIssueNo(importIssueInfo.getExternalIssueNo())
				.externalIssueURL(importIssueInfo.getExternalIssueURL())
				.externalIssueDetails(importIssueInfo.getExternalIssueDetails())
				.build();
	}

	@NonNull
	private IssueEntity mergeIssueInfoWithEntity(@NonNull final ImportIssueInfo importIssueInfo,
			@NonNull final IssueEntity existingEffortIssue)
	{
		final IssueEntity mergedIssueEntity = existingEffortIssue.toBuilder()
				.projectId(importIssueInfo.getProjectId())
				.processed(importIssueInfo.isProcessed())
				.description(importIssueInfo.getDescription())
				.externalIssueNo(importIssueInfo.getExternalIssueNo())
				.externalIssueURL(importIssueInfo.getExternalIssueURL())
				.externalIssueDetails(importIssueInfo.getExternalIssueDetails())
				.build();

		if (importIssueInfo.getMilestone() != null && importIssueInfo.getMilestone().getMilestoneId() != null)
		{
			mergedIssueEntity.setMilestoneId(importIssueInfo.getMilestone().getMilestoneId());
		}

		mergedIssueEntity.setAssigneeIdIfNull(importIssueInfo.getAssigneeId());
		mergedIssueEntity.setBudgetedEffortIfNull(importIssueInfo.getBudget());
		mergedIssueEntity.setEstimatedEffortIfNull(importIssueInfo.getEstimation());

		return mergedIssueEntity;
	}


	@Nullable
	private IssueId getIssueIdByExternalId(@NonNull final ExternalId externalId)
	{
		final Integer issueId = externalReferenceRepository.getReferencedRecordIdOrNullBy(
				GetReferencedIdRequest.builder()
						.externalSystem(externalId.getExternalSystem())
						.externalReference(externalId.getId())
						.externalReferenceType(ExternalReferenceType.ISSUE_ID)
						.build());

		return IssueId.ofRepoIdOrNull(issueId);
	}

	@Nullable
	private MilestoneId getMilestoneIdByExternalId(@NonNull final ExternalId externalId)
	{
		final Integer milestoneId = externalReferenceRepository.getReferencedRecordIdOrNullBy(
				GetReferencedIdRequest.builder()
						.externalSystem(externalId.getExternalSystem())
						.externalReference(externalId.getId())
						.externalReferenceType(ExternalReferenceType.MILESTONE_ID)
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
}
