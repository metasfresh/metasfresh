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
import de.metas.serviceprovider.timebooking.Effort;
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

			issueHierarchy.listIssues().forEach(issue -> {
						issue.addAggregatedEffort(request.getCurrentEffort());
						issue.updateLatestActivityOnSubIssues(request.getLatestActivity());

						issueRepository.save(issue);
					}
			);
		}

		if (request.getOldParentId() != null)
		{
			final IssueHierarchy issueHierarchy = issueRepository.buildUpStreamIssueHierarchy(request.getOldParentId());

			issueHierarchy.getUpStreamForId(request.getOldParentId())
					.forEach(oldParent -> {

						if (request.getOldEffort() != null)
						{
							oldParent.addAggregatedEffort(request.getOldEffort().negate());
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

		issueEntity.updateLatestActivityOnIssue(request.getBookedDate());

		issueRepository.save(issueEntity);

		if(issueEntity.getParentIssueId() != null)
		{
			issueRepository.buildUpStreamIssueHierarchy(issueEntity.getParentIssueId())
					.listIssues()
					.forEach(parentIssue ->
					{
						parentIssue.addAggregatedEffort(request.getBookedEffort());
						parentIssue.updateLatestActivityOnSubIssues(request.getBookedDate());
						issueRepository.save(parentIssue);
					});
		}
	}

	public void recomputeIssueProgress(@NonNull final IssueId issueId, @NonNull final Effort movedEffort)
	{
		final ImmutableList<TimeBooking> timeBookings = timeBookingRepository.getAllByIssueId(issueId);

		final Instant latestActivityDate = timeBookings.stream()
				.map(TimeBooking::getBookedDate)
				.max(Instant::compareTo)
				.orElse(null);

		final IssueEntity issueEntity = issueRepository.getById(issueId);

		issueEntity.addIssueEffort(movedEffort.negate());
		issueEntity.addAggregatedEffort(movedEffort.negate());
		issueEntity.setLatestActivityOnIssue(latestActivityDate);

		issueRepository.save(issueEntity);

		if (issueEntity.getParentIssueId() != null)
		{
			issueRepository.buildUpStreamIssueHierarchy(issueEntity.getParentIssueId())
					.getUpStreamForId(issueEntity.getParentIssueId())
					.forEach(parentIssue ->
					{
						recomputeLatestActivityOnSubIssues(parentIssue);
						parentIssue.addAggregatedEffort(movedEffort.negate());

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
}
