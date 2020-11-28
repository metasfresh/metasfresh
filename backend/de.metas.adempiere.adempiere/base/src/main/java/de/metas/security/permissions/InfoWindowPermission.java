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

import de.metas.util.Check;

/**
 * Info Window access permission.
 * 
 * @author tsa
 *
 */
@Immutable
public final class InfoWindowPermission extends AbstractPermission implements Resource
{
	public static final Permission ofInfoWindowKey(final String infoWindowKey)
	{
		return new InfoWindowPermission(infoWindowKey);
	}

	private final String infoWindowKey;

	private InfoWindowPermission(final String infoWindowKey)
	{
		super();
		Check.assumeNotEmpty(infoWindowKey, "infoWindowKey not empty");
		this.infoWindowKey = infoWindowKey;
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen
		return "@" + infoWindowKey + "@";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(infoWindowKey)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final InfoWindowPermission other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.infoWindowKey, other.infoWindowKey)
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
		checkCompatibleAndCast(accessFrom); // just to validate it
		return this;
	}
}
