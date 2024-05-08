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

package de.metas.serviceprovider.issue;

import de.metas.quantity.Quantity;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.uom.UomId;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_01;
import static de.metas.serviceprovider.TestConstants.MOCK_DESCRIPTION;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_30;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ISSUE_NO;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_INSTANT;
import static de.metas.serviceprovider.TestConstants.MOCK_MILESTONE_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_PROJECT_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_SEARCH_KEY;
import static de.metas.serviceprovider.TestConstants.MOCK_USER_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_VALUE;

public class IssueTestHelper
{
	public static void buildAndStoreIssueRecord(
			@Nullable final IssueId parentIssueId,
			@NonNull final IssueId issueId,
			@Nullable final String issueEffort,
			@NonNull final UomId effortUomId,
			@Nullable final Instant latestActivityOnIssue,
			@Nullable final Instant latestActivityOnSubIssues)
	{
		final I_S_Issue record = InterfaceWrapperHelper.newInstance(I_S_Issue.class);

		record.setS_Issue_ID(issueId.getRepoId());
		record.setS_Parent_Issue_ID(NumberUtils.asInt(parentIssueId, -1));

		record.setAD_Org_ID(MOCK_ORG_ID.getRepoId());
		record.setName(MOCK_NAME);
		record.setValue(MOCK_SEARCH_KEY);
		record.setIssueType(IssueType.EXTERNAL.getValue());
		record.setStatus(Status.IN_PROGRESS.getCode());
		record.setEffort_UOM_ID(effortUomId.getRepoId());
		record.setAggregatedEffort(MOCK_EFFORT_1_30);
		record.setIssueEffort(issueEffort);
		record.setInvoiceableEffort(null);
		record.setLatestActivityOnSubIssues(latestActivityOnSubIssues != null ? Timestamp.from(latestActivityOnSubIssues) : null);
		record.setLatestActivity(latestActivityOnIssue != null ? Timestamp.from(latestActivityOnIssue) : null);

		InterfaceWrapperHelper.saveRecord(record);
	}

	public static IssueEntity createMockIssueEntity()
	{
		final I_C_UOM mockUOMRecord = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.saveRecord(mockUOMRecord);

		return IssueEntity.builder()
				.assigneeId(MOCK_USER_ID)
				.orgId(MOCK_ORG_ID)
				.externalProjectReferenceId(MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE)
				.projectId(MOCK_PROJECT_ID)
				.effortUomId(UomId.ofRepoId(mockUOMRecord.getC_UOM_ID()))
				.milestoneId(MOCK_MILESTONE_ID)
				.budgetedEffort(BigDecimal.ONE)
				.estimatedEffort(BigDecimal.ONE)
				.roughEstimation(BigDecimal.ONE)
				.issueEffort(Effort.ofNullable(MOCK_EFFORT_1_30))
				.aggregatedEffort(Effort.ofNullable(MOCK_EFFORT_1_30))
				.invoicableChildEffort(Quantity.zero(mockUOMRecord))
				.name(MOCK_NAME)
				.searchKey(MOCK_SEARCH_KEY)
				.description(MOCK_DESCRIPTION)
				.type(IssueType.EXTERNAL)
				.isEffortIssue(true)
				.latestActivityOnSubIssues(MOCK_INSTANT)
				.latestActivityOnIssue(MOCK_INSTANT)
				.plannedUATDate(MOCK_DATE_2020_03_01)
				.deliveryPlatform(MOCK_VALUE)
				.status(Status.IN_PROGRESS)
				.externalIssueNo(BigDecimal.valueOf(MOCK_EXTERNAL_ISSUE_NO))
				.externalIssueURL(MOCK_EXTERNAL_URL)
				.build();
	}
}
