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


import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

/**
 * Defines permission access
 * 
 * @author tsa
 *
 */
public class Access
{
	public static final Access LOGIN = ofName("LOGIN");
	public static final Access READ = ofName("READ");
	public static final Access WRITE = ofName("WRITE");
	public static final Access REPORT = ofName("REPORT");
	public static final Access EXPORT = ofName("EXPORT");

	public static Access ofName(final String accessName)
	{
		return new Access(accessName);
	}

	private String name;
	private int hashcode = 0;

	private Access(final String name)
	{
		super();
		Check.assumeNotEmpty(name, "name not empty");
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
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
		final Access other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.name, other.name)
				.isEqual();
	}

	public String getName()
	{
		return name;
	}
}
