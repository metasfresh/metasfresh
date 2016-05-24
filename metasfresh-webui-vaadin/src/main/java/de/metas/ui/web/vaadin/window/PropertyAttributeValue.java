package de.metas.ui.web.vaadin.window;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class PropertyAttributeValue
{
	public PropertyAttributeValue of(final PropertyAttributeName name)
	{
		return new PropertyAttributeValue(name);
	}

	private final PropertyAttributeName name;
	private Object value;

	private PropertyAttributeValue(final PropertyAttributeName name)
	{
		super();
		this.name = name;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add(name.toString(), value)
				.toString();
	}

	public PropertyAttributeName getName()
	{
		return name;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(final Object value)
	{
		this.value = value;
	}

}
