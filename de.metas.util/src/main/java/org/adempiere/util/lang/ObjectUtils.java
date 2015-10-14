package org.adempiere.util.lang;

/*
 * #%L
 * org.adempiere.util
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
import org.adempiere.util.text.ExtendedReflectionToStringBuilder;
import org.adempiere.util.text.RecursiveIndentedMultilineToStringStyle;

/**
 * Common operations on {@link Object}.
 * 
 * @author tsa
 * @see java.util.Objects
 * @see org.apache.commons.lang3.ObjectUtils
 * @see com.google.common.base.Objects
 */
public final class ObjectUtils
{
	/**
	 * To be used in {@link Object#toString()} implementations.
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toString(final Object obj)
	{
		return new ExtendedReflectionToStringBuilder(obj, RecursiveIndentedMultilineToStringStyle.instance)
				.toString();
	}

	/**
	 * Tests whether two objects are equals.
	 * 
	 * <p>
	 * It takes care of the null case. Thus, it is helpful to implement Object.equals.
	 * 
	 * <p>
	 * Notice: it uses compareTo if BigDecimal is found. So, in this case, a.equals(b) might not be the same as Objects.equals(a, b).
	 * 
	 * <p>
	 * If both a and b are Object[], they are compared item-by-item.
	 * 
	 * NOTE: this is a copy paste from org.zkoss.lang.Objects.equals(Object, Object)
	 * 
	 * @see Check#equals(Object, Object)
	 */
	public boolean equals(final Object a, Object b)
	{
		return Check.equals(a, b);
	}

	private ObjectUtils()
	{
		super();
	}
}
