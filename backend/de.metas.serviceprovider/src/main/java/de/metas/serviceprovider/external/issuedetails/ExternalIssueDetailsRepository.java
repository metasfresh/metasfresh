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
import de.metas.organization.OrgId;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.model.I_S_ExternalIssueDetail;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ExternalIssueDetailsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ImmutableList<ExternalIssueDetail> getByIssueId(@NonNull final IssueId issueId)
	{
		return getRecordsByIssueId(issueId)
				.stream()
				.map(this::of)
				.collect(ImmutableList.toImmutableList());
	}

	public void persistIssueDetails(@NonNull final IssueId issueId,
									@NonNull final ImmutableList<ExternalIssueDetail> externalIssueDetails)
	{
		final ImmutableList<I_S_ExternalIssueDetail> newIssueDetails =
				externalIssueDetails
						.stream()
						.map(detail -> of(issueId, detail))
						.collect(ImmutableList.toImmutableList());

		final ImmutableList<I_S_ExternalIssueDetail> existingIssueDetails = getRecordsByIssueId(issueId);

		persistIssueDetails(newIssueDetails, existingIssueDetails);
	}

	@NonNull
	private ImmutableList<I_S_ExternalIssueDetail> getRecordsByIssueId(@NonNull final IssueId issueId)
	{
		return queryBL.createQueryBuilder(I_S_ExternalIssueDetail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalIssueDetail.COLUMNNAME_S_Issue_ID, issueId.getRepoId())
				.create()
				.list()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private I_S_ExternalIssueDetail of(@NonNull final IssueId issueId, @NonNull final ExternalIssueDetail externalIssueDetail)
	{
		final I_S_ExternalIssueDetail record = InterfaceWrapperHelper.newInstance(I_S_ExternalIssueDetail.class);

		record.setS_Issue_ID(issueId.getRepoId());

		record.setDetailValue(externalIssueDetail.getValue());
		record.setType(externalIssueDetail.getType().getValue());
		record.setAD_Org_ID(externalIssueDetail.getOrgId().getRepoId());

		return record;
	}

	private ExternalIssueDetail of(@NonNull final I_S_ExternalIssueDetail record)
	{
		final Optional<ExternalIssueDetailType> externalIssueDetailType = ExternalIssueDetailType.getTypeByValue(record.getType());

		if(!externalIssueDetailType.isPresent())
		{
			throw new AdempiereException("No ExternalIssueDetailType found!").appendParametersToMessage()
					.setParameter("I_S_ExternalIssueDetail", record);
		}

		return ExternalIssueDetail.builder()
				.type(externalIssueDetailType.get())
				.value(record.getDetailValue())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.build();
	}

	private void persistIssueDetails(@NonNull final List<I_S_ExternalIssueDetail> newDetails,
			                        @NonNull final List<I_S_ExternalIssueDetail> existingDetails)
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

	private boolean areEqual(@NonNull final I_S_ExternalIssueDetail record1,
					         @NonNull final I_S_ExternalIssueDetail record2)
	{
		return record1.getDetailValue().equalsIgnoreCase(record2.getDetailValue())
				&&  record2.getType().equalsIgnoreCase(record2.getType());
	}
}
