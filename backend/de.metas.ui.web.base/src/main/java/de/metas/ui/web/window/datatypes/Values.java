package de.metas.ui.web.window.datatypes;

import de.metas.currency.Amount;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONRange;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.NamePair;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

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
 * Misc JSON values converters.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public final class Values
{
	/**
	 * Invokes {@link #valueToJsonObject(Object, JSONOptions, UnaryOperator)} with {@link UnaryOperator#identity()}.
	 */
	public static Object valueToJsonObject(
			@Nullable final Object value,
			@NonNull final JSONOptions jsonOpts)
	{
		return valueToJsonObject(value, jsonOpts, UnaryOperator.identity());
	}

	/**
	 * Convert value to JSON.
	 *
	 * @param value may be {@code null}. In that case, {@link JSONNullValue} is returned.
	 * @param fallbackMapper mapper called when value could not be converted to JSON; takes as input the <code>value</code>
	 * @return JSON value
	 */
	@NonNull
	public static Object valueToJsonObject(
			@Nullable final Object value,
			@NonNull final JSONOptions jsonOpts,
			@NonNull final UnaryOperator<Object> fallbackMapper)
	{
		if (JSONNullValue.isNull(value))
		{
			return JSONNullValue.instance;
		}
		else if (value instanceof java.util.Date)
		{
			final Instant valueDate = ((Date)value).toInstant();
			return DateTimeConverters.toJson(valueDate, jsonOpts.getZoneId());
		}
		else if (value instanceof LocalDate)
		{
			return localDateToJson((LocalDate)value);
		}
		else if (value instanceof LocalTime)
		{
			return DateTimeConverters.toJson((LocalTime)value);
		}
		else if (value instanceof ZonedDateTime)
		{
			return DateTimeConverters.toJson((ZonedDateTime)value, jsonOpts.getZoneId());
		}
		else if (value instanceof Instant)
		{
			return DateTimeConverters.toJson((Instant)value, jsonOpts.getZoneId());
		}
		else if (value instanceof DateRangeValue)
		{
			final DateRangeValue dateRange = (DateRangeValue)value;
			return JSONRange.of(dateRange);
		}
		else if (value instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)value;
			return JSONLookupValue.ofLookupValue(lookupValue, jsonOpts.getAdLanguage());
		}
		else if (value instanceof LookupValuesList)
		{
			final LookupValuesList lookupValues = (LookupValuesList)value;
			return JSONLookupValuesList.ofLookupValuesList(lookupValues, jsonOpts.getAdLanguage());
		}
		else if (value instanceof NamePair)
		{
			final NamePair lookupValue = (NamePair)value;
			return JSONLookupValue.ofNamePair(lookupValue);
		}
		else if (value instanceof BigDecimal)
		{
			return bigDecimalToJson((BigDecimal)value);
		}
		else if (value instanceof Quantity)
		{
			return bigDecimalToJson(((Quantity)value).toBigDecimal());
		}
		else if (value instanceof Money)
		{
			return bigDecimalToJson(((Money)value).toBigDecimal());
		}
		else if (value instanceof Amount)
		{
			return bigDecimalToJson(((Amount)value).getAsBigDecimal());
		}
		else if (value instanceof DocumentId)
		{
			return ((DocumentId)value).toJson();
		}
		else if (value instanceof Collection)
		{
			final Collection<?> valuesList = (Collection<?>)value;
			return valuesList.stream()
					.map(v -> valueToJsonObject(v, jsonOpts, fallbackMapper))
					.collect(Collectors.toCollection(ArrayList::new)); // don't use ImmutableList because we might get null values
		}
		else
		{
			return fallbackMapper.apply(value);
		}
	}

	public static String localDateToJson(final LocalDate value)
	{
		return DateTimeConverters.toJson(value);
	}

	private static String bigDecimalToJson(final BigDecimal value)
	{
		// NOTE: because javascript cannot distinguish between "1.00" and "1.0" as number,
		// we need to provide the BigDecimals as Strings.
		return value.toPlainString();
	}
}
