package de.metas.ui.web.window.shared.datatype;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

@SuppressWarnings("serial")
public class LookupValue implements Serializable
{
	@JsonCreator
	public static final LookupValue of(@JsonProperty("id") final Object id, @JsonProperty("n") final String displayName, @JsonProperty("ln") final String longDisplayName)
	{
		return new LookupValue(id, displayName, longDisplayName);
	}

	public static final LookupValue of(final Object id, final String displayName)
	{
		final String longDisplayName = null;
		return new LookupValue(id, displayName, longDisplayName);
	}

	public static final LookupValue unknownId(final int id)
	{
		return LookupValue.of(id, "<" + id + ">");
	}

	public static final LookupValue cast(final Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof LookupValue)
		{
			return (LookupValue)obj;
		}
		else
		{
			throw new IllegalArgumentException("Cannot cast '" + obj + "' (" + obj.getClass() + ") to " + LookupValue.class);
		}
	}

	@JsonProperty("id")
	private final Object id;
	@JsonProperty("n")
	private final String displayName;
	@JsonProperty("ln")
	private final String longDisplayName;

	private LookupValue(final Object id, final String displayName, final String longDisplayName)
	{
		super();

		if (id == null)
		{
			throw new NullPointerException("id");
		}
		else if (id instanceof Integer)
		{
			this.id = id;
		}
		else if (id instanceof String)
		{
			this.id = id;
		}
		else
		{
			throw new IllegalArgumentException("Invalid id type: " + id + " (" + id.getClass() + ")");
		}

		this.displayName = displayName;
		this.longDisplayName = longDisplayName;
	}

	@Override
	public String toString()
	{
		return displayName;
	}

	@Override
	public boolean equals(Object obj)
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
		return Objects.equals(this.id, other.id);
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(this.id);
	}

	public Object getId()
	{
		return id;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getLongDisplayName()
	{
		return longDisplayName;
	}
}
