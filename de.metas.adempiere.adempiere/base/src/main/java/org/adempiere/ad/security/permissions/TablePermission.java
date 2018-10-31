package org.adempiere.ad.security.permissions;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/**
 * Defines permissiom on a particular {@link TableResource}.
 *
 * @author tsa
 *
 */
public final class TablePermission extends AbstractPermission
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final Set<Access> ALL_ACCESSES = ImmutableSet.of(
			Access.READ
			, Access.WRITE
			, Access.REPORT
			, Access.EXPORT
			);

	public static final TablePermission NONE = builder()
			.setResource(TableResource.ANY_TABLE)
			.removeAllAccesses()
			.build();

	public static final TablePermission ALL = builder()
			.setResource(TableResource.ANY_TABLE)
			.addAccesses(ALL_ACCESSES)
			.build();

	private final TableResource resource;
	private final Set<Access> accesses;

	private TablePermission(final Builder builder)
	{
		super();

		resource = builder.getResource();
		accesses = ImmutableSet.copyOf(builder.accesses);
	}

	@Override
	public TableResource getResource()
	{
		return resource;
	}

	public final Builder asNewBuilder()
	{
		return builder()
				.setFrom(this);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ resource
				+ ", " + accesses
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(resource)
				.append(accesses)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final TablePermission other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(resource, other.resource)
				.append(accesses, other.accesses)
				.isEqual();
	}

	@Override
	public Permission mergeWith(final Permission permissionFrom)
	{
		final TablePermission tablePermissionFrom = checkCompatibleAndCast(permissionFrom);
		return asNewBuilder()
				.addAccesses(tablePermissionFrom.accesses)
				.build();
	}

	public int getAD_Table_ID()
	{
		return resource.getAD_Table_ID();
	}

	@Override
	public boolean hasAccess(final Access access)
	{
		return accesses.contains(access);
	}

	public static final class Builder
	{
		private TableResource resource;
		private final Set<Access> accesses = new LinkedHashSet<>();

		private Builder()
		{
			super();
		}

		public TablePermission build()
		{
			return new TablePermission(this);
		}

		public Builder setResource(final TableResource resource)
		{
			this.resource = resource;
			return this;
		}

		public final TableResource getResource()
		{
			Check.assumeNotNull(resource, "resource not null");
			return resource;
		}

		public final Builder addAccess(final Access access)
		{
			accesses.add(access);
			return this;
		}

		public final Builder removeAccess(final Access access)
		{
			accesses.remove(access);
			return this;
		}

		public final Builder setAccesses(final Set<Access> acceses)
		{
			accesses.clear();
			accesses.addAll(acceses);
			return this;
		}

		public final Builder addAccesses(final Set<Access> acceses)
		{
			accesses.addAll(acceses);
			return this;
		}

		public final Builder removeAllAccesses()
		{
			accesses.clear();
			return this;
		}

		public Builder setFrom(final TablePermission tablePermission)
		{
			setResource(tablePermission.getResource());
			setAccesses(tablePermission.accesses);
			return this;
		}
	}
}
