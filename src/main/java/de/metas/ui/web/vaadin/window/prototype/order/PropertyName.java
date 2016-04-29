package de.metas.ui.web.vaadin.window.prototype.order;

import java.io.Serializable;
import java.util.Objects;

import com.google.common.base.Preconditions;

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

@SuppressWarnings("serial")
public final class PropertyName implements Serializable
{
	public static final PropertyName of(final String name)
	{
		return new PropertyName(name);
	}

	private final String name;

	private PropertyName(final String name)
	{
		super();
		this.name = Preconditions.checkNotNull(name, "name shall not be null");
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}

		final PropertyName other = (PropertyName)obj;
		return Objects.equals(name, other.name);
	}

	/** @return human friendly, localized name */
	public String getCaption()
	{
		// TODO: implement it. atm this method is really a placeholder. In future it might be that we will really move it from here.
		return toString();
	}
}
