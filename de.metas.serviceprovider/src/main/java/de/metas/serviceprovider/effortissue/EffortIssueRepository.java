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

package de.metas.serviceprovider.effortissue;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetailsRepository;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.model.I_S_Effort_Issue;
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
public class EffortIssueRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ExternalIssueDetailsRepository externalIssueDetailsRepository;

	public EffortIssueRepository(final ExternalIssueDetailsRepository externalIssueDetailsRepository)
	{
		this.externalIssueDetailsRepository = externalIssueDetailsRepository;
	}

	public EffortIssue store(@NonNull final EffortIssue effortIssue)
	{
		final I_S_Effort_Issue record = effortIssue.getEffortIssueId() != null
				? InterfaceWrapperHelper.load(effortIssue.getEffortIssueId(), I_S_Effort_Issue.class)
				: InterfaceWrapperHelper.newInstance(I_S_Effort_Issue.class);

		record.setAD_Org_ID(effortIssue.getOrgId().getRepoId());

		record.setName(effortIssue.getName());
		record.setValue(effortIssue.getName());
		record.setDescription(effortIssue.getDescription());

		record.setAD_User_ID(NumberUtils.asInt(effortIssue.getAssigneeId(), -1));
		record.setS_Milestone_ID(NumberUtils.asInt(effortIssue.getMilestoneId(), -1));
		record.setEstimatedEffort(effortIssue.getEstimatedEffort());
		record.setBudgetedEffort(effortIssue.getBudgetedEffort());
		record.setEffort_UOM_ID(effortIssue.getEffortUomId().getRepoId());

		record.setProcessed(effortIssue.isProcessed());

		record.setExternalId(effortIssue.getExternalIssueId());
		record.setIssueURL(effortIssue.getExternalIssueURL());
		record.setExternalIssueNo(effortIssue.getExternalIssueNo());

		InterfaceWrapperHelper.save(record);

		effortIssue.setEffortIssueId(EffortIssueId.ofRepoId(record.getS_Effort_Issue_ID()));

		persistIssueDetails(effortIssue);

		return effortIssue;
	}

	public Optional<EffortIssue> getEntityByExternalId(@NonNull final String externalIssueId)
	{

		final I_S_Effort_Issue effort_issue = queryBL
				.createQueryBuilder(I_S_Effort_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Effort_Issue.COLUMNNAME_ExternalId, externalIssueId)
				.create()
				.firstOnly(I_S_Effort_Issue.class);

		return effort_issue != null ? Optional.of(buildEffortIssueEntity(effort_issue)) : Optional.empty();
	}


	private EffortIssue buildEffortIssueEntity(final I_S_Effort_Issue record)
	{
		return EffortIssue.builder()
				.effortIssueId(EffortIssueId.ofRepoId(record.getS_Effort_Issue_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.milestoneId(MilestoneId.ofRepoIdOrNull(record.getS_Milestone_ID()))
				.assigneeId(UserId.ofNullableRepoId(record.getAD_User_ID()))
				.name(record.getName())
				.description(record.getDescription())
				.processed(record.isProcessed())
				.estimatedEffort(record.getEstimatedEffort())
				.budgetedEffort(record.getBudgetedEffort())
				.effortUomId(UomId.ofRepoId(record.getEffort_UOM_ID()))
				.externalIssueId(record.getExternalId())
				.externalIssueNo(record.getExternalIssueNo())
				.externalIssueURL(record.getIssueURL())
				.build();
	}

	private void persistIssueDetails(final EffortIssue effortIssue)
	{
		final ImmutableList<I_S_ExternalIssueDetail> newIssueDetails = Check.isEmpty(effortIssue.getExternalIssueDetailList())
				? ImmutableList.of()
				: effortIssue.getExternalIssueDetailList()
						.stream()
						.map(detail -> externalIssueDetailsRepository.of(effortIssue.getEffortIssueId(), detail))
						.collect(ImmutableList.toImmutableList());


		final ImmutableList<I_S_ExternalIssueDetail> existingIssueDetails = externalIssueDetailsRepository
				.loadExternalIssueDetailsBy(effortIssue.getEffortIssueId());

		externalIssueDetailsRepository.persistIssueDetails(newIssueDetails, existingIssueDetails);
	}
}
