package de.metas.ui.web.vaadin.window.model;

import java.util.Collection;

import com.google.gwt.thirdparty.guava.common.base.Strings;

import de.metas.ui.web.vaadin.window.PropertyDescriptor;

/*
 * #%L
 * metasfresh-webui
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

public final class TraceHelper
{
	private TraceHelper()
	{
		super();
	}

	public static final String toStringRecursivelly(final PropertyDescriptor descriptor)
	{
		final int level = 0;
		return toStringRecursivelly(descriptor, level);
	}

	public static final String toStringRecursivelly(final PropertyDescriptor descriptor, final int level)
	{
		final String indentStr = Strings.repeat("\t", level);
		final StringBuilder sb = new StringBuilder();
		sb.append("\n").append(indentStr).append(descriptor.toString());

		final Collection<PropertyDescriptor> childPropertyDescriptors = descriptor.getChildPropertyDescriptors();
		for (final PropertyDescriptor childPropertyValue : childPropertyDescriptors)
		{
			sb.append(toStringRecursivelly(childPropertyValue, level + 1));
		}

		return sb.toString();
	}

	public static final String toStringRecursivelly(final PropertyValue propertyValue)
	{
		final int level = 0;
		return toStringRecursivelly(propertyValue, level);
	}

	public static final String toStringRecursivelly(final Iterable<PropertyValue> propertyValues)
	{
		final StringBuilder sb = new StringBuilder();
		final int level = 0;
		for (final PropertyValue propertyValue : propertyValues)
		{
			sb.append(toStringRecursivelly(propertyValue, level));
		}
		return sb.toString();
	}

	public static final String toStringRecursivelly(final PropertyValue propertyValue, final int level)
	{
		final String indentStr = Strings.repeat("\t", level);
		final StringBuilder sb = new StringBuilder();
		sb.append("\n").append(indentStr).append(propertyValue.toString());

		final Collection<PropertyValue> childPropertyValues = propertyValue.getChildPropertyValues().values();
		for (final PropertyValue childPropertyValue : childPropertyValues)
		{
			sb.append(toStringRecursivelly(childPropertyValue, level + 1));
		}

		return sb.toString();
	}

}
