/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.common.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;

@UtilityClass
public class EmptyUtil
{
	public boolean isEmpty(@Nullable final Object value)
	{
		if (value == null)
		{
			return true;
		}

		if (value instanceof String)
		{
			return isEmpty((String)value, true);
		}
		if (value instanceof Object[])
		{
			return isEmpty((Object[])value);
		}
		if (value instanceof BigDecimal)
		{
			return isEmpty((BigDecimal)value);
		}
		if (value instanceof Collection<?>)
		{
			return isEmpty((Collection<?>)value);
		}

		return false;
	}

	public boolean isEmpty(@Nullable final String str)
	{
		return isEmpty(str, false);
	}

	/**
	 * @return return true if the string is null, has length 0, or contains only whitespace.
	 */
	public boolean isBlank(@Nullable final String str)
	{
		return isEmpty(str, true);
	}

	/**
	 * @return return true if the string is not null, has length > 0, and does not contain only whitespace.
	 */
	public boolean isNotBlank(@Nullable final String str)
	{
		return !isEmpty(str, true);
	}

	/**
	 * Is String Empty
	 *
	 * @param str             string
	 * @param trimWhitespaces trim whitespaces
	 * @return true if >= 1 char
	 */
	public boolean isEmpty(@Nullable final String str, final boolean trimWhitespaces)
	{
		if (str == null)
		{
			return true;
		}
		if (trimWhitespaces)
		{
			return str.trim().length() == 0;
		}
		else
		{
			return str.length() == 0;
		}
	}    // isEmpty

	/**
	 * @return true if bd is null or bd.signum() is zero
	 */
	public boolean isEmpty(@Nullable final BigDecimal bd)
	{
		return bd == null || bd.signum() == 0;
	}

	/**
	 * @return true if the array is null or it's length is zero.
	 */
	public <T> boolean isEmpty(@Nullable final T[] arr)
	{
		return arr == null || arr.length == 0;
	}

	/**
	 * @return true if given collection is <code>null</code> or it has no elements
	 */
	public boolean isEmpty(@Nullable final Collection<?> collection)
	{
		return collection == null || collection.isEmpty();
	}
}
