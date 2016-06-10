package de.metas.ui.web.window.shared;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.shared.datatype.NullValue;

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

@JsonSerialize(using = JSONObjectValueHolder_JSONSerializer.class)
@JsonDeserialize(using = JSONObjectValueHolder_JSONDeserializer.class)
@SuppressWarnings("serial")
public final class JSONObjectValueHolder implements Serializable
{
	public static final JSONObjectValueHolder of(final Object value)
	{
		if (NullValue.isNull(value))
		{
			return NULL;
		}
		else if (value instanceof JSONObjectValueHolder)
		{
			return (JSONObjectValueHolder)value;
		}

		return new JSONObjectValueHolder(value);
	}

	private static final JSONObjectValueHolder NULL = new JSONObjectValueHolder(null);

	private final Object value;

	private JSONObjectValueHolder(final Object value)
	{
		super();
		this.value = value;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(value)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(value);
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

		if (!(obj instanceof JSONObjectValueHolder))
		{
			return false;
		}

		final JSONObjectValueHolder other = (JSONObjectValueHolder)obj;
		return Objects.equals(value, other.value);
	}

	public Object getValue()
	{
		return value;
	}
}
