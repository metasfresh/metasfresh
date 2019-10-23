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


import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.text.annotation.ToStringBuilder;

import de.metas.util.Check;

/**
 * A permission which is in same time a resource too.
 * 
 * To be used for implementing misc permissions which can exist or not.
 * 
 * @author tsa
 *
 */
@Immutable
public final class ResourceAsPermission extends AbstractPermission implements Resource
{
	public static final Permission ofName(final String name)
	{
		return new ResourceAsPermission(name);
	}

	private final String name;
	private int hashcode = 0;
	@ToStringBuilder(skip = true)
	private final Object _resourceId;

	private ResourceAsPermission(final String name)
	{
		super();
		Check.assumeNotEmpty(name, "name not empty");
		this.name = name;
		this._resourceId = getClass().getName() + "." + name;
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen
		return "@" + name + "@";
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(name)
					.toHashcode();
		}
		return hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final ResourceAsPermission other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.name, other.name)
				.isEqual();
	}

	@Override
	public Resource getResource()
	{
		return this;
	}

	@Override
	public boolean hasAccess(final Access access)
	{
		// TODO: return accesses.contains(access);
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Permission mergeWith(Permission accessFrom)
	{
		checkCompatibleAndCast(accessFrom);
		return this;
	}
}
