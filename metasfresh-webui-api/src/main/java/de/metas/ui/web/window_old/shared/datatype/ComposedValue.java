package de.metas.ui.web.window_old.shared.datatype;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public final class ComposedValue implements Serializable
{
	@JsonCreator
	public static final ComposedValue of(@JsonProperty("id") final Object id, @JsonProperty("n") final String displayName, @JsonProperty("ln") final String longDisplayName)
	{
		return new ComposedValue(id, displayName, longDisplayName);
	}

	public static final ComposedValue cast(final Object valueObj)
	{
		return (ComposedValue)valueObj;
	}

	@JsonProperty("id")
	private final Object id;
	@JsonProperty("n")
	private final String displayName;
	@JsonProperty("ln")
	private final String longDisplayName;

	private ComposedValue(final Object id, final String displayName, final String longDisplayName)
	{
		super();
		this.id = id;
		this.displayName = displayName;
		this.longDisplayName = longDisplayName;
	}

	@Override
	public String toString()
	{
		return displayName;
	}

	public Object getId()
	{
		return id;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getLongDisplayName()
	{
		return longDisplayName;
	}
}
