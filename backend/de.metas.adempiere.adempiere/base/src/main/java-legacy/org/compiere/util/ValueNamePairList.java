package org.compiere.util;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Immutable
public final class ValueNamePairList
{
	@JsonCreator
	public static final ValueNamePairList of(@JsonProperty("l") final List<ValueNamePair> values)
	{
		if (values == null || values.isEmpty())
		{
			return EMPTY;
		}

		return new ValueNamePairList(values);
	}
	
	public static final ValueNamePairList of(final ValueNamePair[] arr)
	{
		if (arr == null || arr.length == 0)
		{
			return EMPTY;
		}
		return new ValueNamePairList(ImmutableList.copyOf(arr));
	}

	
	public static final ValueNamePairList of()
	{
		return EMPTY;
	}

	public static final ValueNamePairList EMPTY = new ValueNamePairList(ImmutableList.<ValueNamePair> of());

	@JsonProperty("l")
	private final List<ValueNamePair> values;

	private ValueNamePairList(final List<ValueNamePair> values)
	{
		super();
		this.values = ImmutableList.copyOf(values);
	}

	public List<ValueNamePair> getValues()
	{
		return values;
	}

}
