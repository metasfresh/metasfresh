package org.compiere.util;

import java.util.Collection;
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
public final class KeyNamePairList
{
	@JsonCreator
	public static final KeyNamePairList of(@JsonProperty("l") final Collection<KeyNamePair> values)
	{
		if (values == null || values.isEmpty())
		{
			return EMPTY;
		}

		return new KeyNamePairList(values);
	}
	
	public static final KeyNamePairList of(final KeyNamePair[] arr)
	{
		if (arr == null || arr.length == 0)
		{
			return EMPTY;
		}
		return new KeyNamePairList(ImmutableList.copyOf(arr));
	}

	
	public static final KeyNamePairList of()
	{
		return EMPTY;
	}

	public static final KeyNamePairList EMPTY = new KeyNamePairList(ImmutableList.<KeyNamePair> of());

	@JsonProperty("l")
	private final List<KeyNamePair> values;

	private KeyNamePairList(final Collection<KeyNamePair> values)
	{
		super();
		this.values = ImmutableList.copyOf(values);
	}

	public List<KeyNamePair> getValues()
	{
		return values;
	}
}
