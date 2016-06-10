package de.metas.ui.web.window.shared.event;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;

import de.metas.ui.web.window.shared.JSONObjectValueHolder;
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

@SuppressWarnings("serial")
public class PropertyChangedModelEvent extends ModelEvent
{
	public static final PropertyChangedModelEvent of(final PropertyPath propertyPath, final Object value, final Object valueOld)
	{
		return new PropertyChangedModelEvent(propertyPath, JSONObjectValueHolder.of(value), JSONObjectValueHolder.of(valueOld));
	}

	@JsonProperty("p")
	private final PropertyPath propertyPath;
	@JsonProperty("v")
	private final JSONObjectValueHolder value;
	@JsonProperty("vo")
	private final JSONObjectValueHolder valueOld;

	@JsonCreator
	private PropertyChangedModelEvent(
			@JsonProperty("p") final PropertyPath propertyPath //
			, @JsonProperty("v") final JSONObjectValueHolder value //
			, @JsonProperty("vo") final JSONObjectValueHolder valueOld //
	)
	{
		super();
		this.propertyPath = Preconditions.checkNotNull(propertyPath, "propertyPath not null");
		this.value = Preconditions.checkNotNull(value, "value");
		this.valueOld = Preconditions.checkNotNull(valueOld, "valueOld");
	}

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("propertyPath", propertyPath)
				.add("value", value)
				.add("valueOld", valueOld);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(propertyPath, value, valueOld);
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

		if (!(obj instanceof PropertyChangedModelEvent))
		{
			return false;
		}

		final PropertyChangedModelEvent other = (PropertyChangedModelEvent)obj;
		return Objects.equals(propertyPath, other.propertyPath)
				&& Objects.equals(value, other.value)
				&& Objects.equals(valueOld, other.valueOld);
	}

	@JsonIgnore
	public PropertyPath getPropertyPath()
	{
		return propertyPath;
	}

	@JsonIgnore
	public Object getValue()
	{
		return value.getValue();
	}

	@JsonIgnore
	public Object getValueOld()
	{
		return valueOld.getValue();
	}
}
