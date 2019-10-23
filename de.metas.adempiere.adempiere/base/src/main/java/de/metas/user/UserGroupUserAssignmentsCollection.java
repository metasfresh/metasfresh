package de.metas.user;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Range;

import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

final class UserGroupUserAssignmentsCollection
{
	public static UserGroupUserAssignmentsCollection of(final Collection<UserGroupUserAssignment> assignments)
	{
		if (assignments.isEmpty())
		{
			return EMPTY;
		}
		else
		{
			return new UserGroupUserAssignmentsCollection(assignments);
		}
	}

	private static final UserGroupUserAssignmentsCollection EMPTY = new UserGroupUserAssignmentsCollection();

	private final ImmutableSetMultimap<UserGroupId, Range<Instant>> validDates;

	private UserGroupUserAssignmentsCollection()
	{
		validDates = ImmutableSetMultimap.of();
	}

	private UserGroupUserAssignmentsCollection(final Collection<UserGroupUserAssignment> assignments)
	{
		Check.assumeNotEmpty(assignments, "assignments is not empty");
		assertSameUserId(assignments);

		validDates = assignments.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						UserGroupUserAssignment::getUserGroupId,// keyFunction,
						UserGroupUserAssignment::getValidDates // valueFunction
				));
	}

	private static void assertSameUserId(final Collection<UserGroupUserAssignment> assignments)
	{
		final ImmutableSet<UserId> userIds = assignments.stream().map(UserGroupUserAssignment::getUserId).collect(ImmutableSet.toImmutableSet());
		if (userIds.size() > 1)
		{
			throw new AdempiereException("More than one user found for " + assignments);
		}
	}

	public ImmutableSet<UserGroupId> getAssignedGroupIds(@NonNull final Instant date)
	{
		final Set<UserGroupId> allUserGroupIds = validDates.asMap().keySet();
		return allUserGroupIds.stream()
				.filter(userGroupId -> hasGroupAssignment(userGroupId, date))
				.collect(ImmutableSet.toImmutableSet());
	}

	private boolean hasGroupAssignment(@NonNull final UserGroupId userGroupId, @NonNull final Instant date)
	{
		return validDates
				.get(userGroupId)
				.stream()
				.anyMatch(range -> range.contains(date));
	}
}
