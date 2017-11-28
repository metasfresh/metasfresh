package de.metas.material.event.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@EqualsAndHashCode
public final class AttributesKey
{
	@JsonCreator
	public static final AttributesKey of(final String attributesKeyString)
	{
		if (attributesKeyString == null)
		{
			return NONE;
		}
		final String attributesKeyStringNorm = attributesKeyString.trim();
		if (attributesKeyStringNorm.isEmpty())
		{
			return NONE;
		}

		return new AttributesKey(attributesKeyStringNorm);
	}

	public static final AttributesKey NONE = new AttributesKey("");

	private final String attributesKeyString;

	private AttributesKey(@NonNull final String attributesKeyString)
	{
		// don't allow NULL because within the DB we have an index on this and NULL values are trouble with indexes

		this.attributesKeyString = attributesKeyString;
	}

	/**
	 * @deprecated Please use {@link #getAsString()}.
	 */
	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	@NonNull
	public String getAsString()
	{
		return attributesKeyString;
	}

	public boolean isNone()
	{
		return this == NONE;
	}
}
