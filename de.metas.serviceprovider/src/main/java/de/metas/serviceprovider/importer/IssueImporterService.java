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
import de.metas.serviceprovider.budgetissue.BudgetIssue;
import de.metas.serviceprovider.budgetissue.BudgetIssueRepository;
import de.metas.serviceprovider.budgetissue.BudgetIssueType;
import de.metas.serviceprovider.effortissue.EffortIssue;
import de.metas.serviceprovider.effortissue.EffortIssueRepository;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.importer.info.ImportIssuesRequest;
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
	private final BudgetIssueRepository budgetIssueRepository;
	private final EffortIssueRepository effortIssueRepository;
	private final ITrxManager trxManager =  Services.get(ITrxManager.class);

	public IssueImporterService(final ImportIssuesQueue importIssuesQueue, final MilestoneRepository milestoneRepository, final BudgetIssueRepository budgetIssueRepository, final EffortIssueRepository effortIssueRepository)
	{
		this.importIssuesQueue = importIssuesQueue;
		this.milestoneRepository = milestoneRepository;
		this.budgetIssueRepository = budgetIssueRepository;
		this.effortIssueRepository = effortIssueRepository;
	}

	public void importIssues(final ImmutableList<ImportIssuesRequest> requestList, final ImportService importService)
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
			if (ExternalProjectType.BUDGET.equals(importIssueInfo.getExternalProjectType()))
			{
				importBudgetIssue(importIssueInfo);
			}
			else
			{
				importEffortIssue(importIssueInfo);
			}
		}
		catch (final Exception e)
		{
			Loggables.withLogger(log, Level.ERROR)
					.addLog(IMPORT_LOG_MESSAGE_PREFIX +"*** Error while importing issue: {}, errorMessage: {}",importIssueInfo.toString(), e.getMessage(), e);
		}
	}

	@NonNull
	private Optional<MilestoneId> importMilestone(@NonNull final Milestone milestone)
	{
		if (milestone.getExternalId() != null)
		{
			milestoneRepository.getRepoIdByExternalId(milestone.getExternalId())
					.ifPresent(milestone::setMilestoneId);
		}

		final MilestoneId milestoneId = milestoneRepository.store(milestone);

		return Optional.of(milestoneId);
	}

	private void importBudgetIssue(@NonNull final ImportIssueInfo importIssueInfo)
	{
		if (importIssueInfo.getMilestone() != null)
		{
			final Optional<MilestoneId> milestoneId = importMilestone(importIssueInfo.getMilestone());
			milestoneId.ifPresent(id -> importIssueInfo.getMilestone().setMilestoneId(id));
		}

		final Optional<BudgetIssue> existingBudgetIssue =
				budgetIssueRepository.getEntityByExternalId(importIssueInfo.getExternalIssueId());

		final BudgetIssue budgetIssue;
		budgetIssue = existingBudgetIssue
				.map(issue -> updateExistingBudgetIssue(issue, importIssueInfo))
				.orElseGet(() -> buildBudgetIssue(importIssueInfo));

		budgetIssueRepository.store(budgetIssue);
	}

	private BudgetIssue updateExistingBudgetIssue(@NonNull final BudgetIssue existingBudgetIssue,
			                                      @NonNull final ImportIssueInfo importIssueInfo)
	{
		if (importIssueInfo.getMilestone() != null
				&& importIssueInfo.getMilestone().getMilestoneId() != null)
		{
			existingBudgetIssue.setMilestoneIdIfNull(importIssueInfo.getMilestone().getMilestoneId());
		}
		existingBudgetIssue.setAssigneeIdIfNull(importIssueInfo.getAssigneeId());
		existingBudgetIssue.setDescriptionIfNull(importIssueInfo.getDescription());
		existingBudgetIssue.setBudgetedEffortIfNull(importIssueInfo.getBudget());
		existingBudgetIssue.setEstimatedEffortIfNull(importIssueInfo.getEstimation());

		existingBudgetIssue.setExternalIssueId(importIssueInfo.getExternalIssueId());
		existingBudgetIssue.setExternalIssueNo(importIssueInfo.getExternalIssueNo());
		existingBudgetIssue.setExternalIssueURL(importIssueInfo.getExternalIssueURL());
		existingBudgetIssue.setExternalIssueDetails(importIssueInfo.getExternalIssueDetails());

		return existingBudgetIssue;
	}

	private BudgetIssue buildBudgetIssue(@NonNull final ImportIssueInfo importIssueInfo)
	{
		final MilestoneId milestoneId = importIssueInfo.getMilestone() != null
				? importIssueInfo.getMilestone().getMilestoneId()
				: null;

		return BudgetIssue.builder()
				.name(importIssueInfo.getName())
				.description(importIssueInfo.getDescription())
				.assigneeId(importIssueInfo.getAssigneeId())
				.milestoneId(milestoneId)
				.type(BudgetIssueType.EXTERNAL)
				.externalIssueId(importIssueInfo.getExternalIssueId())
				.externalIssueNo(importIssueInfo.getExternalIssueNo())
				.externalIssueURL(importIssueInfo.getExternalIssueURL())
				.effortUomId(importIssueInfo.getEffortUomId())
				.estimatedEffort(importIssueInfo.getEstimation())
				.budgetedEffort(importIssueInfo.getBudget())
				.processed(importIssueInfo.isProcessed())
				.orgId(importIssueInfo.getOrgId())
				.externalIssueDetails(importIssueInfo.getExternalIssueDetails())
				.build();
	}

	private void importEffortIssue(@NonNull final ImportIssueInfo importIssueInfo)
	{
		if (importIssueInfo.getMilestone() != null)
		{
			final Optional<MilestoneId> milestoneId = importMilestone(importIssueInfo.getMilestone());
			milestoneId.ifPresent(milestoneId1 -> importIssueInfo.getMilestone().setMilestoneId(milestoneId1));
		}

		final Optional<EffortIssue> existingEffortIssue =
				effortIssueRepository.getEntityByExternalId(importIssueInfo.getExternalIssueId());

		final EffortIssue effortIssue;
		effortIssue = existingEffortIssue
				.map(issue -> updateExistingEffortIssue(importIssueInfo, issue))
				.orElseGet(() -> buildEffortIssue(importIssueInfo));

		effortIssueRepository.store(effortIssue);
	}

	private EffortIssue buildEffortIssue(@NonNull final ImportIssueInfo importIssueInfo)
	{
		final MilestoneId milestoneId = importIssueInfo.getMilestone() != null
				? importIssueInfo.getMilestone().getMilestoneId()
				: null;

		return EffortIssue.builder()
				.name(importIssueInfo.getName())
				.description(importIssueInfo.getDescription())
				.assigneeId(importIssueInfo.getAssigneeId())
				.milestoneId(milestoneId)
				.externalIssueId(importIssueInfo.getExternalIssueId())
				.externalIssueNo(importIssueInfo.getExternalIssueNo())
				.externalIssueURL(importIssueInfo.getExternalIssueURL())
				.estimatedEffort(importIssueInfo.getEstimation())
				.budgetedEffort(importIssueInfo.getBudget())
				.effortUomId(importIssueInfo.getEffortUomId())
				.processed(importIssueInfo.isProcessed())
				.orgId(importIssueInfo.getOrgId())
				.externalIssueDetailList(importIssueInfo.getExternalIssueDetails())
				.build();
	}

	private EffortIssue updateExistingEffortIssue(@NonNull final ImportIssueInfo importIssueInfo,
			                                      @NonNull final EffortIssue existingEffortIssue)
	{
		if (importIssueInfo.getMilestone() != null
				&& importIssueInfo.getMilestone().getMilestoneId() != null)
		{
			existingEffortIssue.setMilestoneIdIfNull(importIssueInfo.getMilestone().getMilestoneId());
		}
		existingEffortIssue.setAssigneeIdIfNull(importIssueInfo.getAssigneeId());
		existingEffortIssue.setDescriptionIfNull(importIssueInfo.getDescription());
		existingEffortIssue.setBudgetedEffortIfNull(importIssueInfo.getBudget());
		existingEffortIssue.setEstimatedEffortIfNull(importIssueInfo.getEstimation());

		existingEffortIssue.setExternalIssueId(importIssueInfo.getExternalIssueId());
		existingEffortIssue.setExternalIssueNo(importIssueInfo.getExternalIssueNo());
		existingEffortIssue.setExternalIssueURL(importIssueInfo.getExternalIssueURL());
		existingEffortIssue.setExternalIssueDetailList(importIssueInfo.getExternalIssueDetails());

		return existingEffortIssue;
	}
}
