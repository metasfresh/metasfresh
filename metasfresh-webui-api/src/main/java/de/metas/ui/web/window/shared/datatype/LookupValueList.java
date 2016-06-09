package de.metas.ui.web.window.shared.datatype;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

@SuppressWarnings("serial")
public final class LookupValueList implements Serializable
{
	@JsonCreator
	public static final LookupValueList of(@JsonProperty("l") final List<LookupValue> list)
	{
		if (list == null || list.isEmpty())
		{
			return EMPTY;
		}
		return new LookupValueList(list);
	}

	public static final LookupValueList of(LookupValue value)
	{
		return new LookupValueList(ImmutableList.of(value));
	}

	private static final LookupValueList EMPTY = new LookupValueList(ImmutableList.of());

	@JsonProperty("l")
	private final List<LookupValue> list;

	private LookupValueList(final List<LookupValue> list)
	{
		super();
		this.list = ImmutableList.copyOf(list);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(list)
				.toString();
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(list);
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
		if (!(obj instanceof LookupValueList))
		{
			return false;
		}

		final LookupValueList other = (LookupValueList)obj;
		return Objects.equals(list, other.list);
	}

	@JsonIgnore
	public List<LookupValue> toList()
	{
		return list;
	}

}
