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

package de.metas.serviceprovider.budgetissue;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetailsRepository;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.model.I_S_Budget_Issue;
import de.metas.serviceprovider.model.I_S_ExternalIssueDetail;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BudgetIssueRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ExternalIssueDetailsRepository externalIssueDetailsRepository;

	public BudgetIssueRepository(final ExternalIssueDetailsRepository externalIssueDetailsRepository)
	{
		this.externalIssueDetailsRepository = externalIssueDetailsRepository;
	}

	public BudgetIssue store(final BudgetIssue budgetIssue)
	{
		final I_S_Budget_Issue record;

		if (budgetIssue.getBudgetIssueId() != null)
		{
			record = InterfaceWrapperHelper.load(budgetIssue.getBudgetIssueId(), I_S_Budget_Issue.class);
		}
		else
		{
			record = InterfaceWrapperHelper.newInstance(I_S_Budget_Issue.class);
		}
		record.setAD_Org_ID(budgetIssue.getOrgId().getRepoId());

		record.setName(budgetIssue.getName());
		record.setDescription(budgetIssue.getDescription());
		record.setBudget_Issue_Type(budgetIssue.getType().getValue());
		record.setProcessed(budgetIssue.isProcessed());

		record.setS_Current_Milestone_ID(NumberUtils.asInt(budgetIssue.getMilestoneId(), -1));

		record.setEstimatedEffort(budgetIssue.getEstimatedEffort());

		record.setBudgetedEffort(budgetIssue.getBudgetedEffort());

		record.setAD_User_ID(NumberUtils.asInt(budgetIssue.getAssigneeId(), -1));

		record.setExternalId(budgetIssue.getExternalIssueId());
		record.setExternalIssueNo(budgetIssue.getExternalIssueNo());
		record.setIssueURL(budgetIssue.getExternalIssueURL());

		InterfaceWrapperHelper.save(record);

		final BudgetIssueId budgetIssueId = BudgetIssueId.ofRepoId(record.getS_Budget_Issue_ID());
		budgetIssue.setBudgetIssueId(budgetIssueId);

		persistIssueDetails(budgetIssue);

		return budgetIssue;
	}

	public Optional<BudgetIssue> getEntityByExternalId(@NonNull final String externalIssueId)
	{
		 final I_S_Budget_Issue budget_issue = queryBL
				.createQueryBuilder(I_S_Budget_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Budget_Issue.COLUMNNAME_ExternalId, externalIssueId)
				.create()
				.firstOnly(I_S_Budget_Issue.class);

		 return budget_issue != null ? Optional.of(buildBudgetIssueEntity(budget_issue)) : Optional.empty();
	}

	private BudgetIssue buildBudgetIssueEntity(final I_S_Budget_Issue record)
	{

		return BudgetIssue.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.budgetIssueId(BudgetIssueId.ofRepoId(record.getS_Budget_Issue_ID()))
				.effortUomId(UomId.ofRepoId(record.getEffort_UOM_ID()))
				.milestoneId(MilestoneId.ofRepoIdOrNull(record.getS_Current_Milestone_ID()))
				.assigneeId(UserId.ofNullableRepoId(record.getAD_User_ID()))
				.name(record.getName())
				.description(record.getDescription())
				.type(BudgetIssueType.getTypeByValue(record.getBudget_Issue_Type()))
				.processed(record.isProcessed())
				.estimatedEffort(record.getEstimatedEffort())
				.budgetedEffort(record.getBudgetedEffort())
				.externalIssueId(record.getExternalId())
				.externalIssueNo(record.getExternalIssueNo())
				.externalIssueURL(record.getIssueURL())
				.build();
	}

	private void persistIssueDetails(final BudgetIssue budgetIssue)
	{
		final ImmutableList<I_S_ExternalIssueDetail> newIssueDetails = Check.isEmpty(budgetIssue.getExternalIssueDetails())
						? ImmutableList.of()
						: budgetIssue.getExternalIssueDetails()
							.stream()
							.map(detail -> externalIssueDetailsRepository.of(budgetIssue.getBudgetIssueId(), detail))
							.collect(ImmutableList.toImmutableList());

		final ImmutableList<I_S_ExternalIssueDetail> existingIssueDetails = externalIssueDetailsRepository
				.loadExternalIssueDetailsBy(budgetIssue.getBudgetIssueId());

		externalIssueDetailsRepository.persistIssueDetails(newIssueDetails, existingIssueDetails);
	}

}
