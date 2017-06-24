package de.metas.ui.web.window.datatypes.json;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import io.swagger.annotations.ApiModel;
import lombok.Value;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONPatchEvent<PathType>
{
	@ApiModel("operation")
	public static enum JSONOperation
	{
		replace;
	}

	@JsonProperty("op")
	private final JSONOperation op;
	@JsonProperty("path")
	private final PathType path;
	@JsonProperty("value")
	private final Object value;

	private JSONPatchEvent(
			@JsonProperty("op") final JSONOperation op,
			@JsonProperty("path") final PathType path,
			@JsonProperty("value") final Object value)
	{
		this.op = op;
		this.path = path;
		this.value = value;
	}

	public boolean isReplace()
	{
		return op == JSONOperation.replace;
	}

	public String getValueAsString()
	{
		return value != null ? value.toString() : null;
	}

	public Boolean getValueAsBoolean(final Boolean defaultValue)
	{
		return DisplayType.toBoolean(value, defaultValue);
	}

	public int getValueAsInteger(final int defaultValueIfNull)
	{
		if (value == null)
		{
			return defaultValueIfNull;
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else
		{
			return Integer.parseInt(value.toString());
		}
	}

	public List<Integer> getValueAsIntegersList()
	{
		if (value == null)
		{
			return ImmutableList.of();
		}

		if (value instanceof List<?>)
		{
			@SuppressWarnings("unchecked")
			final List<Integer> intList = (List<Integer>)value;
			return intList;
		}
		else if (value instanceof String)
		{
			return ImmutableList.copyOf(DocumentIdsSelection.ofCommaSeparatedString(value.toString()).toIntSet());
		}
		else
		{
			throw new AdempiereException("Cannot convert value to int list").setParameter("event", this);
		}
	}

	public <ET extends Enum<ET>> ET getValueAsEnum(final Class<ET> enumType)
	{
		if (value == null)
		{
			return null;
		}

		if (enumType.isAssignableFrom(value.getClass()))
		{
			@SuppressWarnings("unchecked")
			final ET valueConv = (ET)value;
			return valueConv;
		}
		else if (value instanceof String)
		{
			return Enum.valueOf(enumType, value.toString());
		}
		else
		{
			throw new AdempiereException("Cannot convert value " + value + " to " + enumType);
		}
	}
}
