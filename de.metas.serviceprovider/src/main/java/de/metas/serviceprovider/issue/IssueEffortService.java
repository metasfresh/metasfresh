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

import com.google.common.annotations.VisibleForTesting;
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.util.Node;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
public class IssueEffortService
{
	private final IssueRepository issueRepository;

	public IssueEffortService(final IssueRepository issueRepository)
	{
		this.issueRepository = issueRepository;
	}

	public void addIssueEffort(@NonNull final IssueId issueId,@NonNull final Effort effort)
	{
		final IssueEntity issueEntity = issueRepository.getById(issueId, false);

		final Effort existingEffort = Effort.ofNullable(issueEntity.getIssueEffort());

		issueEntity.setIssueEffort(existingEffort.addNullSafe(effort).getHmm());

		issueRepository.saveWithoutDetails(issueEntity);
	}

	/**
	 *  Adds the given effort to all issues found in the upStream of the one identified by the given issue id.
	 *
	 * @see IssueEffortService#buildUpStreamIssueHierarchy(IssueId)
	 * @param issueId	starting point for the issues to which the effort should be added.
	 * @param effort	effort to add
	 */
	public void addAggregatedEffort(@NonNull final IssueId issueId, @NonNull final Effort effort)
	{
		final IssueHierarchy issueHierarchy = buildUpStreamIssueHierarchy(issueId);

		issueHierarchy.listIssues()
				.forEach(issueEntity -> {
					final Effort existingEffort = Effort.ofNullable(issueEntity.getAggregatedEffort());

					issueEntity.setAggregatedEffort(existingEffort.addNullSafe(effort).getHmm());

					issueRepository.saveWithoutDetails(issueEntity);
				});
	}

	/**
	 * Creates an IssueHierarchy containing only the nodes from
	 * the given issue to root.
	 *
	 * e.g
	 * Given the following issue hierarchy:
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 * when {@code buildUpStreamIssueHierarchy(8)}
	 * it will return IssueHierarchy(root=1) with nodes: [1,2,4,8]
	 *
	 * @param issueId Id to build up stream for.
	 * @return {@code IssueHierarchy} with all nodes from the given Id to root.
	 */
	@VisibleForTesting
	protected IssueHierarchy buildUpStreamIssueHierarchy(@NonNull final IssueId issueId)
	{
		final HashSet<IssueId> seenIds = new HashSet<>();

		Node<IssueEntity> currentNode = Node.of(issueRepository.getById(issueId, false), new ArrayList<>());
		seenIds.add(issueId);

		Optional<IssueId> parentIssueId = Optional.ofNullable(currentNode.getValue().getParentIssueId());

		while (parentIssueId.isPresent() && !seenIds.contains(parentIssueId.get()))
		{
			seenIds.add(parentIssueId.get()); //infinite loop protection

			final IssueEntity parentIssueEntity = issueRepository.getById(parentIssueId.get(), false);

			final Node<IssueEntity> parentNode = Node.of(parentIssueEntity, Collections.singletonList(currentNode));

			currentNode.setParent(parentNode);

			currentNode = parentNode;
			parentIssueId = Optional.ofNullable(currentNode.getValue().getParentIssueId());
		}

		return IssueHierarchy.of(currentNode);
	}
}
