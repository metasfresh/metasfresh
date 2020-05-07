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

import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.timebooking.Effort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class IssueEffortService
{
	private final IssueRepository issueRepository;
	private final IssueHierarchyService issueHierarchyService;

	public IssueEffortService(final IssueRepository issueRepository, final IssueHierarchyService issueHierarchyService)
	{
		this.issueRepository = issueRepository;
		this.issueHierarchyService = issueHierarchyService;
	}

	public void addIssueEffort(@NonNull final IssueId issueId,@NonNull final Effort effort)
	{
		final IssueEntity issueEntity = issueRepository.getById(issueId, false);

		final Effort existingEffort = Effort.ofNullable(issueEntity.getIssueEffort());

		issueEntity.setIssueEffort(existingEffort.addNullSafe(effort).getHmm());

		issueRepository.saveWithoutLabels(issueEntity);
	}

	/**
	 *  Adds the given effort to all issues found in the upStream of the one identified by the given issue id.
	 *
	 * @see IssueHierarchyService#buildUpStreamIssueHierarchy(IssueId)
	 * @param issueId	starting point for the issues to which the effort should be added.
	 * @param effort	effort to add
	 */
	public void addAggregatedEffort(@NonNull final IssueId issueId, @NonNull final Effort effort)
	{
		final IssueHierarchy issueHierarchy = issueHierarchyService.buildUpStreamIssueHierarchy(issueId);

		issueHierarchy.listIssues()
				.forEach(issueEntity -> {
					final Effort existingEffort = Effort.ofNullable(issueEntity.getAggregatedEffort());

					issueEntity.setAggregatedEffort(existingEffort.addNullSafe(effort).getHmm());

					issueRepository.saveWithoutLabels(issueEntity);
				});
	}
}
