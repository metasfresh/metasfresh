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

import de.metas.util.Check;

/**
 * Helper class for building {@link Object#equals(Object)} method.
 *
 * This class is similar with apache commons EqualsBuilder, but much more simple.
 *
 * @author tsa
 *
 */
public class EqualsBuilder
{
	private boolean isEqual = true;

	public EqualsBuilder()
	{
		super();
	}

	public EqualsBuilder append(final BigDecimal value1, final BigDecimal value2)
	{
		if (!isEqual)
		{
			return this;
		}

		if (value1 == value2)
		{
			return this;
		}
		if (value1 == null || value2 == null)
		{
			isEqual = false;
			return this;
		}

		if (value1.compareTo(value2) != 0)
		{
			isEqual = false;
			return this;
		}

		return this;
	}

	// added for optimization (avoid autoboxing and equals())
	public EqualsBuilder append(final boolean value1, final boolean value2)
	{
		if (!isEqual)
		{
			return this;
		}
		if (value1 != value2)
		{
			isEqual = false;
		}
		return this;
	}

	// added for optimization (avoid autoboxing and equals())
	public EqualsBuilder append(final int value1, final int value2)
	{
		if (!isEqual)
		{
			return this;
		}
		if (value1 != value2)
		{
			isEqual = false;
		}
		return this;
	}

	public <T> EqualsBuilder append(final T value1, final T value2)
	{
		if (!isEqual)
		{
			return this;
		}

		if (!Check.equals(value1, value2))
		{
			isEqual = false;
		}

		return this;
	}

	/**
	 * Append values by reference, so values will be considered equal only if <code>value1 == value2</code>.
	 *
	 * @param value1
	 * @param value2
	 * @return this
	 */
	public <T> EqualsBuilder appendByRef(final T value1, final T value2)
	{
		if (!isEqual)
		{
			return this;
		}

		if (value1 != value2)
		{
			isEqual = false;
		}

		return this;
	}

	public EqualsBuilder append(final Map<String, Object> map1, final Map<String, Object> map2, final boolean handleEmptyAsNull)
	{
		if (!isEqual)
		{
			return this;
		}

		if (handleEmptyAsNull)
		{
			final Object value1 = map1 == null || map1.isEmpty() ? null : map1;
			final Object value2 = map2 == null || map2.isEmpty() ? null : map2;
			return append(value1, value2);
		}

		return append(map1, map2);
	}

	public boolean isEqual()
	{
		return isEqual;
	}

	/**
	 * Checks and casts <code>other</code> to same class as <code>obj</code>.
	 *
	 * This method shall be used as first statement in an {@link Object#equals(Object)} implementation. <br/>
	 * <br/>
	 * Example:
	 *
	 * <pre>
	 * public boolean equals(Object obj)
	 * {
	 * 	if (this == obj)
	 * 	{
	 * 		return true;
	 * 	}
	 * 	final MyClass other = EqualsBuilder.getOther(this, obj);
	 * 	if (other == null)
	 * 	{
	 * 		return false;
	 * 	}
	 * 
	 * 	return new EqualsBuilder()
	 * 			.append(prop1, other.prop1)
	 * 			.append(prop2, other.prop2)
	 * 			.append(prop3, other.prop3)
	 * 			// ....
	 * 			.isEqual();
	 * }
	 * </pre>
	 *
	 * @param thisObj this object
	 * @param obj other object
	 * @return <code>other</code> casted to same class as <code>obj</code> or null if <code>other</code> is null or does not have the same class
	 */
	public static <T> T getOther(final T thisObj, final Object obj)
	{
		if (thisObj == null)
		{
			throw new IllegalArgumentException("obj is null");
		}

		if (thisObj == obj)
		{
			@SuppressWarnings("unchecked")
			final T otherCasted = (T)obj;
			return otherCasted;
		}

		if (obj == null)
		{
			return null;
		}

		if (thisObj.getClass() != obj.getClass())
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final T otherCasted = (T)obj;
		return otherCasted;
	}
}
