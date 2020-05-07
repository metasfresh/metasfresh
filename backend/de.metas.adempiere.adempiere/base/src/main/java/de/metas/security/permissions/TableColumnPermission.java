package de.metas.security.permissions;

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

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

@Immutable
public final class TableColumnPermission extends AbstractPermission
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final Set<Access> ALL_ACCESSES = ImmutableSet.of(
			Access.READ
			, Access.WRITE
			);

	public static final TableColumnPermission ALL = builder()
			.setResource(TableColumnResource.ANY)
			.addAccesses(ALL_ACCESSES)
			.build();

	public static final TableColumnPermission NONE = builder()
			.setResource(TableColumnResource.ANY)
			.removeAllAccesses()
			.build();

	private final TableColumnResource resource;
	private final Set<Access> accesses;
	private int hashcode = 0;

	private TableColumnPermission(final Builder builder)
	{
		super();

		this.resource = builder.resource;
		Check.assumeNotNull(this.resource, "resource not null");

		this.accesses = ImmutableSet.copyOf(builder.accesses);
	}

	@Override
	public TableColumnResource getResource()
	{
		return resource;
	}

	public final Builder asNewBuilder()
	{
		return builder()
				.setFrom(this);
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
					.append(resource)
					.append(accesses)
					.toHashcode();
		}
		return hashcode;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final TableColumnPermission other = EqualsBuilder.getOther(this, obj);
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
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ resource
				+ ", " + accesses
				+ "]";
	}

	@Override
	public boolean hasAccess(final Access access)
	{
		return accesses.contains(access);
	}

	@Override
	public Permission mergeWith(final Permission permissionFrom)
	{
		final TableColumnPermission columnPermissionFrom = checkCompatibleAndCast(permissionFrom);
		return asNewBuilder()
				.addAccesses(columnPermissionFrom.accesses)
				.build();
	}

	public int getAD_Table_ID()
	{
		return resource.getAD_Table_ID();
	}

	public int getAD_Column_ID()
	{
		return resource.getAD_Column_ID();
	}

	public static class Builder
	{
		private TableColumnResource resource;
		private final Set<Access> accesses = new LinkedHashSet<>();

		public TableColumnPermission build()
		{
			return new TableColumnPermission(this);
		}

		public Builder setFrom(final TableColumnPermission columnPermission)
		{
			setResource(columnPermission.getResource());
			setAccesses(columnPermission.accesses);
			return this;
		}

		public Builder setResource(final TableColumnResource resource)
		{
			this.resource = resource;
			return this;
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
	}
}
