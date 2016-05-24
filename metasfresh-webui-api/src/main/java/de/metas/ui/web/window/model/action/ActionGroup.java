package de.metas.ui.web.window.model.action;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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

public final class ActionGroup
{
	public static final ActionGroup of(final String caption)
	{
		if (caption == null || caption.trim().isEmpty())
		{
			return NONE;
		}
		return new ActionGroup(caption);
	}

	public static final ActionGroup NONE = new ActionGroup();

	private final String caption;

	private ActionGroup(final String caption)
	{
		super();
		this.caption = caption;
	}

	private ActionGroup()
	{
		super();
		caption = "";
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(caption);
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
		if (!(obj instanceof ActionGroup))
		{
			return false;
		}

		final ActionGroup other = (ActionGroup)obj;
		return Objects.equal(caption, other.caption);
	}

	public String getCaption()
	{
		return caption;
	}
}
