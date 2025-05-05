package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.RepoIdAware;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

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
 * <p>
 * Event sent by frontend when the user wants to change some fields.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Schema(description = "document-change-event")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONDocumentChangedEvent
{
	@JsonCreator
	public static JSONDocumentChangedEvent of(@JsonProperty("op") final JSONOperation operation, @JsonProperty("path") final String path, @JsonProperty("value") final Object value)
	{
		return new JSONDocumentChangedEvent(operation, path, value);
	}

	public static JSONDocumentChangedEvent replace(final String fieldName, final Object valueJson)
	{
		return new JSONDocumentChangedEvent(JSONOperation.replace, fieldName, valueJson);
	}

	@Schema(description = "operation")
	public enum JSONOperation
	{
		replace
	}

	@JsonProperty("op") JSONOperation operation;
	@JsonProperty("path") String path;
	@JsonProperty("value") Object value;

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

	@SuppressWarnings("unused")
	public List<Integer> getValueAsIntegersList()
	{
		if (value == null)
		{
			return ImmutableList.of();
		}

		if (value instanceof List<?>)
		{
			@SuppressWarnings("unchecked") final List<Integer> intList = (List<Integer>)value;
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

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
	public Optional<BigDecimal> getValueAsBigDecimalOptional()
	{
		return Optional.ofNullable(getValueAsBigDecimal(null));
	}

	private static BigDecimal toBigDecimal(@Nullable final Object value)
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
			@SuppressWarnings("unchecked") final Map<String, Object> map = (Map<String, Object>)value;
			return JSONLookupValue.stringLookupValueFromJsonMap(map);
		}
		else if (value instanceof JSONLookupValue)
		{
			final JSONLookupValue json = (JSONLookupValue)value;
			return json.toIntegerLookupValue();
		}
		else
		{
			throw new AdempiereException("Cannot convert value '" + value + "' (" + value.getClass() + ") to " + IntegerLookupValue.class);
		}
	}

	@Nullable
	public <T extends ReferenceListAwareEnum> T getValueAsEnum(@NonNull final Class<T> enumType)
	{
		if (value == null)
		{
			return null;
		}
		else if (enumType.isInstance(value))
		{
			//noinspection unchecked
			return (T)value;
		}

		final String valueStr;
		if (value instanceof Map)
		{
			@SuppressWarnings("unchecked") final Map<String, Object> map = (Map<String, Object>)value;
			final LookupValue.StringLookupValue lookupValue = JSONLookupValue.stringLookupValueFromJsonMap(map);
			valueStr = lookupValue.getIdAsString();
		}
		else if (value instanceof JSONLookupValue)
		{
			final JSONLookupValue json = (JSONLookupValue)value;
			valueStr = json.getKey();
		}
		else
		{
			valueStr = value.toString();
		}

		try
		{
			return ReferenceListAwareEnums.ofNullableCode(valueStr, enumType);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed converting `" + value + "` (" + value.getClass().getSimpleName() + ") to " + enumType.getSimpleName(), ex);
		}

	}

}
