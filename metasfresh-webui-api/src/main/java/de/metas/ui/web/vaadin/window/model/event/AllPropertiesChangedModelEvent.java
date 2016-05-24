package de.metas.ui.web.vaadin.window.model.event;

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

public class AllPropertiesChangedModelEvent extends ModelEvent
{
	public static final AllPropertiesChangedModelEvent of(final Object model)
	{
		return new AllPropertiesChangedModelEvent(model);
	}

	private AllPropertiesChangedModelEvent(final Object model)
	{
		super(model);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.toString();
	}
}
