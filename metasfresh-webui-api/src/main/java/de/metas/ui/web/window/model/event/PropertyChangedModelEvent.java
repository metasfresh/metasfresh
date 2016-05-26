package de.metas.ui.web.window.model.event;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;

import de.metas.ui.web.window.shared.datatype.PropertyPath;

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

public class PropertyChangedModelEvent extends ModelEvent
{
	public static final PropertyChangedModelEvent of(final Object model, final PropertyPath propertyPath, final Object value, final Object valueOld)
	{
		return new PropertyChangedModelEvent(model, propertyPath, value, valueOld);
	}

	private final PropertyPath propertyPath;
	private final Object value;
	private final Object valueOld;

	private PropertyChangedModelEvent(final Object model, final PropertyPath propertyPath, final Object value, final Object valueOld)
	{
		super();
		this.propertyPath = Preconditions.checkNotNull(propertyPath, "propertyPath not null");
		this.value = value;
		this.valueOld = valueOld;
	}

	@Override
	protected void toString(ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("propertyPath", propertyPath)
				.add("value", value)
				.add("valueOld", valueOld);
	}

	public PropertyPath getPropertyPath()
	{
		return propertyPath;
	}

	public Object getValue()
	{
		return value;
	}

	public Object getValueOld()
	{
		return valueOld;
	}
}
