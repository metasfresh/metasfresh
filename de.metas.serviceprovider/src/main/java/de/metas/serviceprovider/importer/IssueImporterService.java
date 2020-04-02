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

package de.metas.serviceprovider.importer;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueType;
import de.metas.serviceprovider.milestone.Milestone;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.milestone.MilestoneRepository;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static de.metas.serviceprovider.importer.ImportConstants.IMPORT_LOG_MESSAGE_PREFIX;

@Service
public class IssueImporterService
{
	private static final Logger log = LogManager.getLogger(IssueImporterService.class);

	private final ImportIssuesQueue importIssuesQueue;
	private final MilestoneRepository milestoneRepository;
	private final IssueRepository issueRepository;
	private final ITrxManager trxManager =  Services.get(ITrxManager.class);

	public IssueImporterService(final ImportIssuesQueue importIssuesQueue, final MilestoneRepository milestoneRepository, final IssueRepository issueRepository)
	{
		this.importIssuesQueue = importIssuesQueue;
		this.milestoneRepository = milestoneRepository;
		this.issueRepository = issueRepository;
	}

	public void importIssues(@NonNull final ImmutableList<ImportIssuesRequest> requestList,
			                 @NonNull final ImportService importService)
	{
		final CompletableFuture completableFuture =
				CompletableFuture.runAsync(() -> importService.start(requestList));

		while (!completableFuture.isDone() || !importIssuesQueue.isEmpty())
		{
			final ImmutableList<ImportIssueInfo> issueInfos = importIssuesQueue.drainAll();
			issueInfos.forEach(issue -> trxManager.runInNewTrx(localTrx -> importIssue(issue)));
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

			final Optional<IssueEntity> existingEffortIssue =
					issueRepository.getEntityByExternalId(importIssueInfo.getExternalIssueId());

			final IssueEntity issueEntity;
			issueEntity = existingEffortIssue
					.map(issue -> mergeIssueInfoWithEntity(importIssueInfo, issue))
					.orElseGet(() -> buildIssue(importIssueInfo));

			issueRepository.save(issueEntity);
		}
		catch (final Exception e)
		{
			Loggables.withLogger(log, Level.ERROR)
					.addLog(" {} *** Error while importing issue: {}, errorMessage: {}",
							IMPORT_LOG_MESSAGE_PREFIX, importIssueInfo.toString(), e.getMessage(), e);
		}
	}

	private void importMilestone(@NonNull final Milestone milestone)
	{
		if (milestone.getExternalId() != null)
		{
			milestoneRepository.getRepoIdByExternalId(milestone.getExternalId())
					.ifPresent(milestone::setMilestoneId);
		}

		milestoneRepository.save(milestone);
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
				.externalIssueId(importIssueInfo.getExternalIssueId())
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
				.description(importIssueInfo.getDescription())
				.externalIssueId(importIssueInfo.getExternalIssueId())
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

}
