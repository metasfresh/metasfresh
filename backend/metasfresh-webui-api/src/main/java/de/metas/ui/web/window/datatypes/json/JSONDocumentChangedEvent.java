package de.metas.ui.web.window.datatypes.json;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.util.lang.RepoIdAware;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;
import lombok.Value;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Document changed event.
 *
 * Event sent by frontend when the user wants to change some fields.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ApiModel("document-change-event")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDocumentChangedEvent
{
	@JsonCreator
	public static final JSONDocumentChangedEvent of(@JsonProperty("op") final JSONOperation operation, @JsonProperty("path") final String path, @JsonProperty("value") final Object value)
	{
		return new JSONDocumentChangedEvent(operation, path, value);
	}

	public static final JSONDocumentChangedEvent replace(final String fieldName, final Object valueJson)
	{
		return new JSONDocumentChangedEvent(JSONOperation.replace, fieldName, valueJson);
	}

	@ApiModel("operation")
	public enum JSONOperation
	{
		replace;
	}

	@JsonProperty("op")
	private final JSONOperation operation;
	@JsonProperty("path")
	private final String path;
	@JsonProperty("value")
	private final Object value;

	public boolean isReplace()
	{
		return operation == JSONOperation.replace;
	}

	public void assertReplaceOperation()
	{
		if (!isReplace())
		{
			throw new AdempiereException("Replace operation was expected for " + this);
		}
	}

	public String getValueAsString(final String defaultValueIfNull)
	{
		return value != null ? value.toString() : defaultValueIfNull;
	}

	public Boolean getValueAsBoolean(final Boolean defaultValue)
	{
		return DisplayType.toBoolean(value, defaultValue);
	}

	public int getValueAsInteger(final int defaultValueIfNull)
	{
		final Integer valueInt = DataTypes.convertToInteger(value);
		return valueInt != null ? valueInt : defaultValueIfNull;
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

	public <T extends RepoIdAware> T getValueAsId(@NonNull final IntFunction<T> mapper)
	{
		final int repoId = getValueAsInteger(-1);
		return mapper.apply(repoId);
	}

	public BigDecimal getValueAsBigDecimal()
	{
		return toBigDecimal(value);
	}

	public BigDecimal getValueAsBigDecimal(final BigDecimal defaultValueIfNull)
	{
		return value != null ? toBigDecimal(value) : defaultValueIfNull;
	}

	private static BigDecimal toBigDecimal(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else
		{
			final String valueStr = value.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}
			return new BigDecimal(valueStr);
		}
	}

	public ZonedDateTime getValueAsZonedDateTime()
	{
		return DateTimeConverters.fromObjectToZonedDateTime(value);
	}

	public LocalDate getValueAsLocalDate()
	{
		return DateTimeConverters.fromObjectToLocalDate(value);
	}

	public IntegerLookupValue getValueAsIntegerLookupValue()
	{
		return DataTypes.convertToIntegerLookupValue(value);
	}

	public LookupValue getValueAsStringLookupValue()
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof Map)
		{
			@SuppressWarnings("unchecked")
			final Map<String, Object> map = (Map<String, Object>)value;
			return JSONLookupValue.stringLookupValueFromJsonMap(map);
		}
		else if (value instanceof JSONLookupValue)
		{
			final JSONLookupValue json = (JSONLookupValue)value;
			if (json == null)
			{
				return null;
			}
			return json.toIntegerLookupValue();
		}
		else
		{
			throw new AdempiereException("Cannot convert value '" + value + "' (" + value.getClass() + ") to " + IntegerLookupValue.class);
		}
	}

}
