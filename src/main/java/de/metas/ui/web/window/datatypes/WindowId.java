package de.metas.ui.web.window.datatypes;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;

import lombok.EqualsAndHashCode;

/*
 * #%L
 * metasfresh-webui-api
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

@EqualsAndHashCode
public final class WindowId
{
	public static final WindowId fromJson(final String json)
	{
		return new WindowId(json);
	}

	public static final WindowId fromNullableJson(final String json)
	{
		if (json == null)
		{
			return null;
		}
		return new WindowId(json);
	}

	public static final WindowId of(final int windowIdInt)
	{
		return new WindowId(windowIdInt);
	}

	public static WindowId of(final DocumentId documentTypeId)
	{
		if (documentTypeId.isInt())
		{
			return new WindowId(documentTypeId.toInt());
		}
		else
		{
			return new WindowId(documentTypeId.toJson());
		}
	}

	public static final WindowId ofIntOrNull(final int windowIdInt)
	{
		if (windowIdInt <= 0)
		{
			return null;
		}
		return new WindowId(windowIdInt);
	}

	private final String value;
	private transient int valueInt = -1; // lazy

	@JsonCreator
	private WindowId(final String value)
	{
		Check.assumeNotEmpty(value, "value is not empty");
		this.value = value;
	}

	private WindowId(final int valueInt)
	{
		Preconditions.checkArgument(valueInt > 0, "invalid valueInt: %s", valueInt);
		this.valueInt = valueInt;
		value = String.valueOf(valueInt);
	}

	@Override
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		return value;
	}

	public int toInt()
	{
		int valueInt = this.valueInt;
		if (valueInt < 0)
		{
			try
			{
				valueInt = Integer.parseInt(value);
				this.valueInt = valueInt;
			}
			catch (final NumberFormatException ex)
			{
				throw new AdempiereException("WindowId cannot be converted to int: " + this, ex);
			}
		}
		return valueInt;
	}

	public int toIntOr(final int fallbackValue)
	{
		try
		{
			return toInt();
		}
		catch (Exception ex)
		{
			return fallbackValue;
		}
	}

	public boolean isInt()
	{
		try
		{
			toInt();
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	public DocumentId toDocumentId()
	{
		return DocumentId.of(value);
	}
}
