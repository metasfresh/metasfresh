package org.adempiere.util.text;

/*
 * #%L
 * de.metas.util
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


import java.lang.reflect.Field;

import org.adempiere.util.text.annotation.ToStringBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Extension of {@link ReflectionToStringBuilder} which adds some more functionality.
 * <ul>
 * <li>support for {@link ToStringBuilder} annotation
 * </ul>
 * @author tsa
 *
 */
public class ExtendedReflectionToStringBuilder extends org.apache.commons.lang3.builder.ReflectionToStringBuilder
{
	public ExtendedReflectionToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer)
	{
		super(object, style, buffer);
	}

	public ExtendedReflectionToStringBuilder(Object object, ToStringStyle style)
	{
		super(object, style);
	}

	public ExtendedReflectionToStringBuilder(Object object)
	{
		super(object);
	}

	public <T> ExtendedReflectionToStringBuilder(T object, ToStringStyle style, StringBuffer buffer, Class<? super T> reflectUpToClass, boolean outputTransients, boolean outputStatics)
	{
		super(object, style, buffer, reflectUpToClass, outputTransients, outputStatics);
	}

	@Override
	protected boolean accept(Field field)
	{
		if (!super.accept(field))
		{
			return false;
		}

		final ToStringBuilder toStringBuilder = field.getAnnotation(ToStringBuilder.class);
		if (toStringBuilder != null && toStringBuilder.skip())
		{
			return false;
		}

		return true;
	}
}
