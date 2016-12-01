package de.metas.ui.web.window.datatypes;

import java.util.Objects;

import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

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
	public static final Object normalizeId(final Object idObj, final boolean numericKey)
	{
		if (idObj == null)
		{
			return null;
		}

		if (numericKey)
		{
			if (idObj instanceof Number)
			{
				final int idInt = ((Number)idObj).intValue();
				if (idInt < 0)
				{
					return null;
				}
				return idInt;
			}

			final String idStr = idObj.toString().trim();
			if (idStr.isEmpty())
			{
				return null;
			}

			final int idInt = Integer.parseInt(idObj.toString());
			if (idInt < 0)
			{
				return null;
			}
			return idInt;
		}
		else
		{
			return idObj.toString();
		}
	}

	public static final LookupValue fromObject(final Object id, final String displayName)
	{
		if (id == null)
		{
			return null;
		}
		if (id instanceof Integer)
		{
			return new IntegerLookupValue((int)id, displayName);
		}
		else
		{
			return new StringLookupValue(id.toString(), displayName);
		}
	}
	
	public static final LookupValue fromNamePair(final NamePair namePair)
	{
		if (namePair == null)
		{
			return null;
		}
		else if (namePair instanceof ValueNamePair)
		{
			final ValueNamePair vnp = (ValueNamePair)namePair;
			return StringLookupValue.of(vnp.getValue(), vnp.getName());
		}
		else if (namePair instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)namePair;
			return IntegerLookupValue.of(knp.getKey(), knp.getName());
		}
		else
		{
			// shall not happen
			throw new IllegalArgumentException("Unknown namePair: " + namePair + " (" + namePair.getClass() + ")");
		}
	}

	protected final Object id;
	protected final String displayName;

	private LookupValue(final Object id, final String displayName)
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

		public static final StringLookupValue unknown(final String value)
		{
			return new StringLookupValue(value, "<" + value + ">");
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

		public static final IntegerLookupValue of(final StringLookupValue stringLookupValue)
		{
			if (stringLookupValue == null)
			{
				return null;
			}
			return new IntegerLookupValue(stringLookupValue.getIdAsInt(), stringLookupValue.getDisplayName());
		}

		public static final IntegerLookupValue unknown(final int id)
		{
			return new IntegerLookupValue(id, "<" + id + ">");
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
