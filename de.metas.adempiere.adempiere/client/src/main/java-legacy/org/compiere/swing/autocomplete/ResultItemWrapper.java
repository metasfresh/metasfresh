package org.compiere.swing.autocomplete;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

/**
 * An {@link ResultItem} implementation which wraps a given object.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ValueType>
 */
public class ResultItemWrapper<ValueType> implements ResultItem
{
	private final ValueType value;

	public ResultItemWrapper(final ValueType value)
	{
		super();
		Check.assumeNotNull(value, "value not null");
		this.value = value;
	}

	public final ValueType getValue()
	{
		return value;
	}

	@Override
	public String getText()
	{
		return value.toString();
	}

	@Override
	public String toString()
	{
		return getText();
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(value)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final ResultItemWrapper<ValueType> other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.value, other.value)
				.isEqual();
	}

}
