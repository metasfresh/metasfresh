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

package de.metas.serviceprovider.issue;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.issue.interceptor.AddIssueProgressRequest;
import de.metas.serviceprovider.issue.interceptor.HandleParentChangedRequest;
import de.metas.serviceprovider.timebooking.TimeBooking;
import de.metas.serviceprovider.timebooking.TimeBookingRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
public class IssueService
{
	private final IssueRepository issueRepository;
	private final TimeBookingRepository timeBookingRepository;

	public IssueService(final IssueRepository issueRepository, final TimeBookingRepository timeBookingRepository)
	{
		this.issueRepository = issueRepository;
		this.timeBookingRepository = timeBookingRepository;
	}

	public void handleParentChanged(@NonNull final HandleParentChangedRequest request)
	{

		if (request.getCurrentParentId() != null)
		{
			final IssueHierarchy issueHierarchy = issueRepository.buildUpStreamIssueHierarchy(request.getCurrentParentId());

			issueHierarchy
					.getUpStreamForId(request.getCurrentParentId())
					.forEach(issue -> {
						issue.addAggregatedEffort(request.getCurrentAggregatedEffort());
						issue.addInvoiceableChildEffort(request.getCurrentInvoicableEffort());
						recomputeLatestActivityOnSubIssues(issue);

						issueRepository.save(issue);
					}
			);
		}

		if (request.getOldParentId() != null)
		{
			final IssueHierarchy issueHierarchy = issueRepository.buildUpStreamIssueHierarchy(request.getOldParentId());

			issueHierarchy.getUpStreamForId(request.getOldParentId())
					.forEach(oldParent -> {

						if (request.getOldAggregatedEffort() != null)
						{
							oldParent.addAggregatedEffort(request.getOldAggregatedEffort().negate());
						}
						if (request.getOldInvoicableEffort() != null)
						{
							oldParent.addInvoiceableChildEffort(request.getOldInvoicableEffort().negate());
						}
						recomputeLatestActivityOnSubIssues(oldParent);

						issueRepository.save(oldParent);
					});
		}
	}

	public void addIssueProgress(@NonNull final AddIssueProgressRequest request)
	{
		final IssueEntity issueEntity = issueRepository.getById(request.getIssueId());

		issueEntity.addIssueEffort(request.getBookedEffort());
		issueEntity.addAggregatedEffort(request.getBookedEffort());

		recomputeLatestActivityOnIssue(issueEntity);

		issueRepository.save(issueEntity);

		if(issueEntity.getParentIssueId() != null)
		{
			issueRepository
					.buildUpStreamIssueHierarchy(issueEntity.getParentIssueId())
					.getUpStreamForId(issueEntity.getParentIssueId())
					.forEach(parentIssue ->
					{
						parentIssue.addAggregatedEffort(request.getBookedEffort());

						recomputeLatestActivityOnSubIssues(parentIssue);

						issueRepository.save(parentIssue);
					});
		}
	}

	private void recomputeLatestActivityOnSubIssues(@NonNull final IssueEntity issueEntity)
	{
		final ImmutableList<IssueEntity> subIssues = issueRepository.getDirectlyLinkedSubIssues(issueEntity.getIssueId());

		final Instant mostRecentActivity = subIssues
				.stream()
				.map(IssueEntity::getLatestActivity)
				.filter(Objects::nonNull)
				.max(Instant::compareTo)
				.orElse(null);

		issueEntity.setLatestActivityOnSubIssues(mostRecentActivity);
	}

	private void recomputeLatestActivityOnIssue(@NonNull final IssueEntity issueEntity)
	{
		final ImmutableList<TimeBooking> timeBookings = timeBookingRepository.getAllByIssueId(issueEntity.getIssueId());

		final Instant latestActivityDate = timeBookings.stream()
				.map(TimeBooking::getBookedDate)
				.max(Instant::compareTo)
				.orElse(null);

		issueEntity.setLatestActivityOnIssue(latestActivityDate);
	}
}
