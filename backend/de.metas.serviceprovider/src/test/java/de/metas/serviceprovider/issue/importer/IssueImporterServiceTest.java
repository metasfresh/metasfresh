/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.label.IssueLabelRepository;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueType;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.issue.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.milestone.MilestoneRepository;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(AdempiereTestWatcher.class)
class IssueImporterServiceTest
{
	private IssueImporterService issueImporterService;
	private IssueRepository issueRepository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IModelCacheInvalidationService modelCacheInvalidationService = Services.get(IModelCacheInvalidationService.class);
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

		issueRepository = new IssueRepository(queryBL, modelCacheInvalidationService);
		issueImporterService = new IssueImporterService(
				new ImportQueue<>(100, "logPrefix"),
				new MilestoneRepository(),
				issueRepository,
				new ExternalReferenceRepository(queryBL),
				trxManager,
				adReferenceDAO,
				new IssueLabelRepository(queryBL)
		);
	}

	@Nested
	public class importIssue
	{
		private final ImportIssueInfo initialImportIssueInfo = ImportIssueInfo.builder()
				.externalProjectReferenceId(ExternalProjectReferenceId.ofRepoId(1))
				.status(Status.PENDING)
				.orgId(OrgId.ofRepoId(1))
				.externalProjectType(ExternalProjectType.BUDGET)
				.effortUomId(UomId.ofRepoId(1))
				.name("test issue")
				.externalIssueId(ExternalId.of(ExternalSystem.GITHUB, "1"))
				.issueLabels(ImmutableList.of())
				.build();
		private final IssueEntity expectedIssue = IssueEntity.builder()
				.issueId(null) // will be set later
				.status(Status.PENDING)
				.orgId(OrgId.ofRepoId(1))
				.effortUomId(UomId.ofRepoId(1))
				.budgetedEffort(new BigDecimal("0"))
				.estimatedEffort(new BigDecimal("0"))
				.roughEstimation(new BigDecimal("0"))
				.issueEffort(Effort.ZERO)
				.aggregatedEffort(Effort.ZERO)
				.name("test issue")
				.searchKey("test issue")
				.type(IssueType.EXTERNAL)
				.isEffortIssue(false)
				.processed(false)
				.externalIssueNo(new BigDecimal("0"))
				.externalProjectReferenceId(ExternalProjectReferenceId.ofRepoId(1))
				.build();

		@Test
		public void createNewIssue()
		{
			final List<IssueId> importedIdsCollector = new ArrayList<>();
			issueImporterService.importIssue(initialImportIssueInfo, importedIdsCollector);
			final IssueId issueId = CollectionUtils.singleElement(importedIdsCollector);

			Assertions.assertThat(issueRepository.getById(issueId))
					.usingRecursiveComparison()
					.isEqualTo(expectedIssue.toBuilder().issueId(issueId).build());
		}

		@Test
		public void updateNotProcessedIssue()
		{
			// Create new Issue
			final IssueId issueId;
			{
				final List<IssueId> importedIdsCollector = new ArrayList<>();
				issueImporterService.importIssue(initialImportIssueInfo, importedIdsCollector);
				issueId = CollectionUtils.singleElement(importedIdsCollector);
			}

			// Update
			{
				final List<IssueId> importedIdsCollector = new ArrayList<>();
				issueImporterService.importIssue(
						initialImportIssueInfo.toBuilder()
								.roughEstimation(new BigDecimal("123"))
								.build(),
						importedIdsCollector);
				Assertions.assertThat(importedIdsCollector).containsExactly(issueId);

				Assertions.assertThat(issueRepository.getById(issueId))
						.usingRecursiveComparison()
						.isEqualTo(expectedIssue.toBuilder()
								.issueId(issueId)
								.roughEstimation(new BigDecimal("123"))
								.build());
			}
		}

		@Test
		public void doNotUpdateProcessedIssue()
		{
			// Create new Issue
			final IssueId issueId;
			{
				final List<IssueId> importedIdsCollector = new ArrayList<>();
				issueImporterService.importIssue(initialImportIssueInfo, importedIdsCollector);
				issueId = CollectionUtils.singleElement(importedIdsCollector);
			}

			// Mark issue as processed
			{
				final I_S_Issue issueRecord = InterfaceWrapperHelper.load(issueId, I_S_Issue.class);
				issueRecord.setProcessed(true);
				InterfaceWrapperHelper.saveRecord(issueRecord);
			}

			// Try to update
			{
				final List<IssueId> importedIdsCollector = new ArrayList<>();
				issueImporterService.importIssue(
						initialImportIssueInfo.toBuilder()
								.roughEstimation(new BigDecimal("123"))
								.build(),
						importedIdsCollector);
				Assertions.assertThat(importedIdsCollector).isEmpty();

				Assertions.assertThat(issueRepository.getById(issueId))
						.usingRecursiveComparison()
						.isEqualTo(expectedIssue.toBuilder()
								.issueId(issueId)
								.processed(true)
								.build());
			}
		}
	}
}