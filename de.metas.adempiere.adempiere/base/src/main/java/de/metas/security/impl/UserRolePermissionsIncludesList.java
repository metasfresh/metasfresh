package de.metas.security.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableList;

import de.metas.security.RoleId;

/**
 * List of {@link UserRolePermissionsInclude}s.
 *
 * @author tsa
 *
 */
@Immutable
final class UserRolePermissionsIncludesList implements Iterable<UserRolePermissionsInclude>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final ImmutableList<UserRolePermissionsInclude> includes;

	private UserRolePermissionsIncludesList(final Builder builder)
	{
		includes = builder.getIncludes();
	}

	public Builder asNewBuilder()
	{
		return builder()
				.addAll(includes);
	}

	@Override
	public Iterator<UserRolePermissionsInclude> iterator()
	{
		return includes.iterator();
	}

	public boolean isEmpty()
	{
		return includes.isEmpty();
	}

	public Set<RoleId> getAllRoleIdsIncluding(final RoleId adRoleId)
	{
		final Set<RoleId> adRoleIds = new LinkedHashSet<>();
		adRoleIds.add(adRoleId);

		for (final UserRolePermissionsInclude include : includes)
		{
			include.collectRoleIds(adRoleIds);
		}
		return adRoleIds;
	}

	public static final class Builder
	{
		private final List<UserRolePermissionsInclude> includes = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public ImmutableList<UserRolePermissionsInclude> getIncludes()
		{
			return ImmutableList.copyOf(includes);
		}

		public UserRolePermissionsIncludesList build()
		{
			return new UserRolePermissionsIncludesList(this);
		}

		public Builder add(final UserRolePermissionsInclude include)
		{
			if (!includes.contains(include))
			{
				includes.add(include);
			}
			return this;
		}

		public Builder addAll(final Iterable<UserRolePermissionsInclude> includes)
		{
			for (final UserRolePermissionsInclude include : includes)
			{
				add(include);
			}
			return this;
		}

		public boolean isEmpty()
		{
			return includes.isEmpty();
		}
	}
}
