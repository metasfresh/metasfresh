package de.metas.ui.web.window.datatypes;

import java.util.Objects;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public abstract class LookupValue
{
	protected final Object id;
	protected final String displayName;

	LookupValue(final Object id, final String displayName)
	{
		super();
		if (id == null)
		{
			throw new NullPointerException("id");
		}
		this.id = id;
		this.displayName = displayName == null ? "" : displayName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("displayName", displayName)
				.toString();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof LookupValue))
		{
			return false;
		}

		final LookupValue other = (LookupValue)obj;
		return DataTypes.equals(id, other.id);
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(id);
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public final Object getId()
	{
		return id;
	}

	public abstract int getIdAsInt();

	public String getIdAsString()
	{
		return id.toString();
	}

	public static final class StringLookupValue extends LookupValue
	{
		public static final StringLookupValue of(final String value, final String displayName)
		{
			return new StringLookupValue(value, displayName);
		}
		
		private Integer idInt; // lazy

		private StringLookupValue(final String id, final String displayName)
		{
			super(id, displayName);
		}

		@Override
		public int getIdAsInt()
		{
			if (idInt == null)
			{
				idInt = Integer.parseInt((String)id);
			}
			return idInt;
		}
	}

	public static final class IntegerLookupValue extends LookupValue
	{
		public static final IntegerLookupValue of(final int id, final String displayName)
		{
			return new IntegerLookupValue(id, displayName);
		}
		
		private IntegerLookupValue(final int id, final String displayName)
		{
			super(id, displayName);
		}

		@Override
		public int getIdAsInt()
		{
			return (Integer)id;
		}
	}

}
