package org.adempiere.util.lang;

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

import java.math.BigDecimal;
import java.util.Map;

/**
 * Helper class for building {@link Object#hashCode()} method.
 * 
 * This class is similar with apache commons HashCodeBuilder, but much more simple.
 * 
 * @author tsa
 * 
 */
public class HashcodeBuilder
{
	private final static int prime = 31;

	private int hashcode = 0;

	public HashcodeBuilder()
	{
		super();
	}

	public HashcodeBuilder append(Object value)
	{
		return appendHashcode(value == null ? 0 : value.hashCode());
	}

	/**
	 * Converts given {@link BigDecimal} to {@link Double} and appends the {@link Double}'s hash code.
	 * 
	 * We do this, because 2 big decimals which are numerically equal but have different scales, will have 2 different hashes.
	 * 
	 * @param value
	 * 
	 * @see http://stackoverflow.com/questions/14311644/normalizing-bigdecimals-hash-code-howto
	 */
	public HashcodeBuilder append(final BigDecimal value)
	{
		if (value == null)
		{
			return appendHashcode(0);
		}

		final Double d = value.doubleValue();
		return appendHashcode(d.hashCode());
	}

	public HashcodeBuilder appendHashcode(final int hashcodeToAppend)
	{
		hashcode = prime * hashcode + hashcodeToAppend;
		return this;
	}

	public HashcodeBuilder append(Map<?, ?> map, boolean handleEmptyMapAsNull)
	{
		if (handleEmptyMapAsNull && (map == null || map.isEmpty()))
		{
			return append((Object)null);
		}

		return append(map);
	}

	public int toHashcode()
	{
		return hashcode;
	}

	/**
	 * Sames as {@link #toHashcode()} because:
	 * <ul>
	 * <li>we want to avoid bugs like calling this method instead of {@link #toHashcode()}
	 * <li>the real hash code of this object does not matter
	 * </ul>
	 * 
	 * @deprecated Please use {@link #toHashcode()}. This method is present here, just to avoid some mistakes.
	 */
	@Deprecated
	@Override
	public int hashCode()
	{
		return toHashcode();
	}
}
