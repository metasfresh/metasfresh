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

package de.metas.serviceprovider.issue.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.util.Node;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Optional;

public class IssueHierarchy
{
	private final Node<IssueEntity> root;

	public static IssueHierarchy of(@NonNull final Node<IssueEntity> root)
	{
		return new IssueHierarchy(root);
	}

	/**
	 * @see Node#listAllNodesBelow()
	 */
	public ImmutableList<IssueEntity> listIssues()
	{
		return root.listAllNodesBelow()
				.stream()
				.map(Node::getValue)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean hasNodeForId(@NonNull final IssueId issueId)
	{
		return listIssues().stream().map(IssueEntity::getIssueId).anyMatch(issueId::equals);
	}

	public ImmutableList<IssueEntity> getUpStreamForId(@NonNull final IssueId issueId)
	{
		final Optional<IssueEntity> issue = getIssueForId(issueId);

		if (!issue.isPresent())
		{
			return ImmutableList.of();
		}

		return this.root.getNode(issue.get())
				.map(Node::getUpStream)
				.orElse(new ArrayList<>())
				.stream()
				.map(Node::getValue)
				.collect(ImmutableList.toImmutableList());
	}

	private Optional<IssueEntity> getIssueForId(@NonNull final IssueId issueId)
	{
		return listIssues()
				.stream()
				.filter(issueEntity -> issueId.equalsNullSafe(issueEntity.getIssueId()))
				.findFirst();
	}

	private IssueHierarchy(final Node<IssueEntity> root)
	{
		this.root = root;
	}
}
