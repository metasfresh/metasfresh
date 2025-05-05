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
import de.metas.activity.repository.ActivityRepository;
import de.metas.ad_reference.ADReferenceService;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.externalreference.ExternalId;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.label.IssueLabelRepository;
import de.metas.serviceprovider.external.label.IssueLabelService;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueType;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.issue.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.milestone.MilestoneRepository;
import de.metas.serviceprovider.model.I_S_ExternalProjectReference;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.X_S_ExternalProjectReference;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static de.metas.serviceprovider.TestConstants.MOCK_CLIENT_ID;

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
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		issueRepository = new IssueRepository(queryBL, ModelCacheInvalidationService.newInstanceForUnitTesting());

		final ExternalSystems externalSystems = new ExternalSystems();
		externalSystems.registerExternalSystem(ExternalSystem.GITHUB);

		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();
		externalReferenceTypes.registerType(ExternalServiceReferenceType.ISSUE_ID);

		final ExternalReferenceRepository externalReferenceRepository = new ExternalReferenceRepository(queryBL, externalSystems, externalReferenceTypes);

		issueImporterService = new IssueImporterService(
				new ImportQueue<>(100, "logPrefix"),
				new MilestoneRepository(),
				issueRepository,
				externalReferenceRepository,
				trxManager,
				new IssueLabelService(ADReferenceService.newMocked(), new IssueLabelRepository(queryBL)),
				new ActivityRepository()
		);
	}

	@Nested
	public class importIssue
	{
		private ImportIssueInfo initialImportIssueInfo;

		private IssueEntity expectedIssue;

		@BeforeEach
		void beforeEach()
		{
			final I_S_ExternalProjectReference mockExternalProjectReference = InterfaceWrapperHelper.newInstance(I_S_ExternalProjectReference.class);
			mockExternalProjectReference.setAD_Org_ID(1);
			mockExternalProjectReference.setExternalReference("ExternalReference");
			mockExternalProjectReference.setExternalProjectOwner("ExternalProjectOwner");
			mockExternalProjectReference.setExternalSystem(X_S_ExternalProjectReference.EXTERNALSYSTEM_Github);
			mockExternalProjectReference.setProjectType(X_S_ExternalProjectReference.PROJECTTYPE_Budget);

			InterfaceWrapperHelper.save(mockExternalProjectReference);

			final I_C_UOM mockUOMRecord = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
			InterfaceWrapperHelper.saveRecord(mockUOMRecord);

			final Instant updatedAt = Instant.parse("2022-08-24T13:24:05Z");

			initialImportIssueInfo = ImportIssueInfo.builder()
					.status(Status.PENDING)
					.orgId(OrgId.ofRepoId(1))
					.externalProjectType(ExternalProjectType.BUDGET)
					.effortUomId(UomId.ofRepoId(mockUOMRecord.getC_UOM_ID()))
					.repositoryName("ExternalReference")
					.name("test issue")
					.externalIssueNo(11111)
					.externalIssueId(ExternalId.of(ExternalSystem.GITHUB, "1"))
					.issueLabels(ImmutableList.of())
					.externalProjectReferenceId(ExternalProjectReferenceId.ofRepoId(mockExternalProjectReference.getS_ExternalProjectReference_ID()))
					.updatedAt(updatedAt)
					.build();

			expectedIssue = IssueEntity.builder()
					.issueId(null) // will be set later
					.status(Status.PENDING)
					.clientId(MOCK_CLIENT_ID)
					.orgId(OrgId.ofRepoId(1))
					.effortUomId(UomId.ofRepoId(mockUOMRecord.getC_UOM_ID()))
					.budgetedEffort(new BigDecimal("0"))
					.estimatedEffort(new BigDecimal("0"))
					.roughEstimation(new BigDecimal("0"))
					.issueEffort(Effort.ZERO)
					.aggregatedEffort(Effort.ZERO)
					.invoicableChildEffort(Quantity.zero(mockUOMRecord))
					.name("test issue")
					.searchKey("11111 test issue")
					.type(IssueType.EXTERNAL)
					.isEffortIssue(false)
					.processed(false)
					.externalIssueNo(new BigDecimal("11111"))
					.externalProjectReferenceId(ExternalProjectReferenceId.ofRepoId(mockExternalProjectReference.getS_ExternalProjectReference_ID()))
					.externallyUpdatedAt(updatedAt)
					.invoiceableHours(BigDecimal.ZERO)
					.build();
		}

		@Test
		void createNewIssue()
		{
			final List<IssueId> importedIdsCollector = new ArrayList<>();
			issueImporterService.importIssue(initialImportIssueInfo, importedIdsCollector);
			final IssueId issueId = CollectionUtils.singleElement(importedIdsCollector);

			Assertions.assertThat(issueRepository.getById(issueId))
					.usingRecursiveComparison()
					.isEqualTo(expectedIssue.toBuilder().issueId(issueId).build());
		}

		@Test
		void updateNotProcessedIssue()
		{
			// Create new Issue
			final IssueId issueId;
			{
				final List<IssueId> importedIdsCollector = new ArrayList<>();
				issueImporterService.importIssue(initialImportIssueInfo.toBuilder()
														 .budget(BigDecimal.TEN)
														 .build(),
												 importedIdsCollector);

				issueId = CollectionUtils.singleElement(importedIdsCollector);

				Assertions.assertThat(issueRepository.getById(issueId).getBudgetedEffort()).isEqualTo(BigDecimal.TEN);
			}

			// Update
			{
				final List<IssueId> importedIdsCollector = new ArrayList<>();
				issueImporterService.importIssue(
						initialImportIssueInfo.toBuilder()
								.roughEstimation(BigDecimal.valueOf(123))
								.budget(BigDecimal.valueOf(8))
								.build(),
						importedIdsCollector);
				Assertions.assertThat(importedIdsCollector).containsExactly(issueId);

				Assertions.assertThat(issueRepository.getById(issueId))
						.usingRecursiveComparison()
						.isEqualTo(expectedIssue.toBuilder()
										   .issueId(issueId)
										   .roughEstimation(BigDecimal.valueOf(123))
										   .budgetedEffort(BigDecimal.valueOf(8))
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