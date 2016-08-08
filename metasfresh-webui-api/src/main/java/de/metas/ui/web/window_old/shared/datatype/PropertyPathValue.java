package de.metas.ui.web.window_old.shared.datatype;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

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
@JsonSerialize(using = PropertyPathValue_JSONSerializer.class)
@JsonDeserialize(using = PropertyPathValue_JSONDeserializer.class)
public final class PropertyPathValue implements Serializable
{
	public static final PropertyPathValue of(final PropertyPath propertyPath, final Object value)
	{
		return new PropertyPathValue(propertyPath, value);
	}

	private final PropertyPath propertyPath;
	private final Object value;

	private PropertyPathValue(final PropertyPath propertyPath, final Object value)
	{
		super();
		this.propertyPath = Preconditions.checkNotNull(propertyPath, "propertyPath");
		this.value = NullValue.isNull(value) ? null : value;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("propertyPath", propertyPath)
				.add("value", value)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(propertyPath, value);
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

		if (!(obj instanceof PropertyPathValue))
		{
			return false;
		}

		final PropertyPathValue other = (PropertyPathValue)obj;
		return Objects.equals(propertyPath, other.propertyPath)
				&& Objects.equals(value, other.value);
	}

	public PropertyPath getPropertyPath()
	{
		return propertyPath;
	}

	public Object getValue()
	{
		return value;
	}
}
