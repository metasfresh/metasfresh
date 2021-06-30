/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.user;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.stream.Stream;

public final class UserGroupsCollection
{
	@NonNull
	public static UserGroupsCollection of(@Nullable final Collection<UserGroupUserAssignment> assignments)
	{
		if (Check.isEmpty(assignments))
		{
			return EMPTY;
		}
		else
		{
			return new UserGroupsCollection(assignments);
		}
	}

	private static final UserGroupsCollection EMPTY = new UserGroupsCollection();

	private final ImmutableSet<UserGroupUserAssignment> assignments;

	private UserGroupsCollection()
	{
		assignments = ImmutableSet.of();
	}

	private UserGroupsCollection(final Collection<UserGroupUserAssignment> assignments)
	{
		Check.assumeNotEmpty(assignments, "assignments is not empty");

		this.assignments = assignments.stream().collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public Stream<UserGroupUserAssignment> streamAssignmentsFor(@NonNull final UserGroupId userGroupId, @NonNull final Instant targetDate)
	{
		return assignments.stream()
				.filter(userGroupUserAssignment -> userGroupUserAssignment.getUserGroupId().equals(userGroupId))
				.filter(userGroupUserAssignment -> userGroupUserAssignment.getValidDates().contains(targetDate));
	}

}
