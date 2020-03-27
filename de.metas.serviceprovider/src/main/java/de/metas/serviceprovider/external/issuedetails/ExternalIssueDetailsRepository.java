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

package de.metas.serviceprovider.external.issuedetails;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.budgetissue.BudgetIssueId;
import de.metas.serviceprovider.effortissue.EffortIssueId;
import de.metas.serviceprovider.model.I_S_ExternalIssueDetail;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ExternalIssueDetailsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_S_ExternalIssueDetail of(@NonNull final BudgetIssueId budgetIssueId, @NonNull final ExternalIssueDetail externalIssueDetail)
	{
		final I_S_ExternalIssueDetail record = InterfaceWrapperHelper.newInstance(I_S_ExternalIssueDetail.class);

		record.setS_Budget_Issue_ID(budgetIssueId.getRepoId());

		mapIssueDetails(record, externalIssueDetail);
		return record;
	}

	public I_S_ExternalIssueDetail of(@NonNull final EffortIssueId effortIssueId, @NonNull final ExternalIssueDetail externalIssueDetail)
	{
		final I_S_ExternalIssueDetail record = InterfaceWrapperHelper.newInstance(I_S_ExternalIssueDetail.class);

		record.setS_Effort_Issue_ID(effortIssueId.getRepoId());

		mapIssueDetails(record, externalIssueDetail);
		return record;
	}

	public ImmutableList<I_S_ExternalIssueDetail> loadExternalIssueDetailsBy(final BudgetIssueId budgetIssueId)
	{
		return queryBL.createQueryBuilder(I_S_ExternalIssueDetail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalIssueDetail.COLUMNNAME_S_Budget_Issue_ID, budgetIssueId.getRepoId())
				.create()
				.list()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<I_S_ExternalIssueDetail> loadExternalIssueDetailsBy(final EffortIssueId effortIssueId)
	{
		return queryBL.createQueryBuilder(I_S_ExternalIssueDetail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalIssueDetail.COLUMNNAME_S_Effort_Issue_ID, effortIssueId)
				.create()
				.list()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	public void persistIssueDetails(final List<I_S_ExternalIssueDetail> newDetails,
			                         final List<I_S_ExternalIssueDetail> existingDetails)
	{

		if (Check.isEmpty(newDetails))
		{
			InterfaceWrapperHelper.deleteAll(existingDetails);
			return;
		}

		final List<I_S_ExternalIssueDetail> recordsToInsert = newDetails
				.stream()
				.filter(issueDetail -> existingDetails
						.stream()
						.noneMatch(record -> areEqual(record, issueDetail))
				)
				.collect(Collectors.toList());

		final List<I_S_ExternalIssueDetail> recordsToDelete = existingDetails
				.stream()
				.filter(record -> existingDetails
						.stream()
						.noneMatch(detail -> areEqual(record, detail))
				)
				.collect(Collectors.toList());

		InterfaceWrapperHelper.deleteAll(recordsToDelete);
		InterfaceWrapperHelper.saveAll(recordsToInsert);
	}

	private boolean areEqual(final I_S_ExternalIssueDetail record1, final I_S_ExternalIssueDetail record2)
	{
		return record1.getDetailValue().equalsIgnoreCase(record2.getDetailValue())
				&&  record2.getType().equalsIgnoreCase(record2.getType());
	}

	private void mapIssueDetails(@NonNull final I_S_ExternalIssueDetail record,
			                     @NonNull final ExternalIssueDetail externalIssueDetail)
	{
		record.setDetailValue(externalIssueDetail.getDetailValue());
		record.setType(externalIssueDetail.getType().getValue());
		record.setAD_Org_ID(externalIssueDetail.getOrgId().getRepoId());
	}
}
