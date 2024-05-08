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

package de.metas.serviceprovider.external.label;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.model.I_S_IssueLabel;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class IssueLabelRepository
{
	private final IQueryBL queryBL;

	public IssueLabelRepository(final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	public void persistLabels(@NonNull final IssueId issueId,
							  @NonNull final ImmutableList<IssueLabel> issueLabels)
	{
		final ImmutableList<I_S_IssueLabel> newLabels =
				issueLabels
						.stream()
						.map(label -> of(issueId, label))
						.collect(ImmutableList.toImmutableList());

		final ImmutableList<I_S_IssueLabel> existingLabels = getRecordsByIssueId(issueId);

		persistLabels(newLabels, existingLabels);
	}

	@VisibleForTesting
	@NonNull
	ImmutableList<I_S_IssueLabel> getRecordsByIssueId(@NonNull final IssueId issueId)
	{
		return queryBL.createQueryBuilder(I_S_IssueLabel.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_IssueLabel.COLUMNNAME_S_Issue_ID, issueId.getRepoId())
				.create()
				.list()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private I_S_IssueLabel of(@NonNull final IssueId issueId, @NonNull final IssueLabel issueLabel)
	{
		final I_S_IssueLabel record = InterfaceWrapperHelper.newInstance(I_S_IssueLabel.class);

		record.setS_Issue_ID(issueId.getRepoId());
		record.setAD_Org_ID(issueLabel.getOrgId().getRepoId());
		record.setLabel(issueLabel.getValue());

		return record;
	}

	private void persistLabels(@NonNull final List<I_S_IssueLabel> newLabels,
			                   @NonNull final List<I_S_IssueLabel> existingLabels)
	{

		if (Check.isEmpty(newLabels))
		{
			InterfaceWrapperHelper.deleteAll(existingLabels);
			return;
		}

		final List<I_S_IssueLabel> recordsToInsert =
				newLabels
				.stream()
				.filter(label -> existingLabels
						.stream()
						.noneMatch(record -> areEqual(record, label))
				)
				.collect(Collectors.toList());

		final List<I_S_IssueLabel> recordsToDelete = existingLabels
				.stream()
				.filter(record -> newLabels
						.stream()
						.noneMatch(label -> areEqual(record, label))
				)
				.collect(Collectors.toList());

		InterfaceWrapperHelper.deleteAll(recordsToDelete);
		InterfaceWrapperHelper.saveAll(recordsToInsert);
	}

	private boolean areEqual(@NonNull final I_S_IssueLabel record1,
					         @NonNull final I_S_IssueLabel record2)
	{
		return record1.getLabel().equalsIgnoreCase(record2.getLabel());
	}
}
