package de.metas.ui.web.window.shared.action;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

/*
 * #%L
 * metasfresh-webui-api
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
public final class ActionsList implements Serializable, Iterable<Action>
{
	@JsonCreator
	public static final ActionsList of(@JsonProperty("actions") final List<Action> actions)
	{
		if (actions == null || actions.isEmpty())
		{
			return EMPTY;
		}
		return new ActionsList(actions);
	}

	public static final ActionsList copyOf(final ActionsList actionsList)
	{
		// NOTE: we assume it's immutable
		return actionsList;
	}

	public static final transient ActionsList EMPTY = new ActionsList(ImmutableList.of());

	@JsonProperty("actions")
	private final List<Action> actions;

	private ActionsList(final Collection<Action> actions)
	{
		super();
		this.actions = ImmutableList.copyOf(actions);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(actions)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return actions.hashCode();
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

		if (obj.getClass() != getClass())
		{
			return false;
		}

		final ActionsList other = (ActionsList)obj;

		return Objects.equals(actions, other.actions);
	}

	@Override
	public Iterator<Action> iterator()
	{
		return actions.iterator();
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return actions.isEmpty();
	}
}
