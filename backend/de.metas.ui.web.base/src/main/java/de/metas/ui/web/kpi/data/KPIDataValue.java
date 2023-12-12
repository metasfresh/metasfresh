/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.kpi.data;

import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.ui.web.dashboard.json.KPIJsonOptions;
import de.metas.ui.web.kpi.descriptor.KPIField;
import de.metas.ui.web.kpi.descriptor.KPIFieldValueType;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

@Value
@Builder
public class KPIDataValue
{
	private static final Logger logger = LogManager.getLogger(KPIDataValue.class);

	@Nullable Object value;
	@NonNull KPIFieldValueType valueType;
	@Nullable Integer numberPrecision;

	public static KPIDataValue ofValueAndField(@Nullable final Object value, @NonNull final KPIField kpiField)
	{
		return builder()
				.value(value)
				.valueType(kpiField.getValueType())
				.numberPrecision(kpiField.getNumberPrecision())
				.build();
	}

	public static KPIDataValue ofValueAndType(@Nullable final Object value, @NonNull final KPIFieldValueType valueType)
	{
		return builder()
				.value(value)
				.valueType(valueType)
				.build();
	}

	public static KPIDataValue ofUnknownType(@Nullable final Object value)
	{
		return builder()
				.value(value)
				.valueType(KPIFieldValueType.String)
				.build();
	}

	public boolean isNull()
	{
		return value == null;
	}

	public Object toJsonValue(@NonNull final KPIJsonOptions jsonOpts)
	{
		final Object jsonValue = jsonOpts.isPrettyValues()
				? toJsonValue_PrettyValues(value, jsonOpts)
				: toJsonValue_NoFormat(value, jsonOpts);

		return JSONNullValue.wrapIfNull(jsonValue);
	}

	@Nullable
	private Object toJsonValue_NoFormat(
			@Nullable final Object value,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			switch (valueType)
			{
				case Date:
				{
					final LocalDate date = DateTimeConverters.fromObjectToLocalDate(value);
					return DateTimeConverters.toJson(date);
				}
				case DateTime:
				{
					final ZonedDateTime date = DateTimeConverters.fromObjectToZonedDateTime(value);
					return DateTimeConverters.toJson(date, jsonOpts.getZoneId());
				}
				case Number:
				{
					if (value instanceof String)
					{
						final BigDecimal bd = new BigDecimal(value.toString());
						return roundToPrecision(bd);
					}
					else if (value instanceof Double)
					{
						final BigDecimal bd = BigDecimal.valueOf((Double)value);
						return roundToPrecision(bd);
					}
					else if (value instanceof Integer)
					{
						return value;
					}
					else if (value instanceof Number)
					{
						final BigDecimal bd = BigDecimal.valueOf(((Number)value).doubleValue());
						return roundToPrecision(bd);
					}
					else
					{
						return value;
					}
				}
				case URL:
				case String:
				{
					return value.toString();
				}
				default:
				{
					throw new IllegalStateException("valueType not supported: " + valueType);
				}
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} for field {}", value, this, ex);
			return value.toString();
		}
	}

	@Nullable
	private Object toJsonValue_PrettyValues(
			@Nullable final Object value,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			if (valueType == KPIFieldValueType.Date)
			{
				if (value instanceof String)
				{
					final Date date = TimeUtil.asDate(DateTimeConverters.fromObjectToZonedDateTime(value));
					final Language language = Language.getLanguage(jsonOpts.getAdLanguage());
					return DisplayType.getDateFormat(DisplayType.Date, language)
							.format(date);
				}
				else if (value instanceof Date)
				{
					final Date date = (Date)value;
					final Language language = Language.getLanguage(jsonOpts.getAdLanguage());
					return DisplayType.getDateFormat(DisplayType.Date, language)
							.format(date);
				}
				else if (value instanceof Number)
				{
					final long millis = ((Number)value).longValue();
					final Date date = new Date(millis);
					final Language language = Language.getLanguage(jsonOpts.getAdLanguage());
					return DisplayType.getDateFormat(DisplayType.Date, language)
							.format(date);
				}
				else
				{
					return value.toString();
				}
			}
			else if (valueType == KPIFieldValueType.DateTime)
			{
				if (value instanceof String)
				{
					final Date date = TimeUtil.asDate(DateTimeConverters.fromObjectToZonedDateTime(value));
					final Language language = Language.getLanguage(jsonOpts.getAdLanguage());
					return DisplayType.getDateFormat(DisplayType.DateTime, language)
							.format(date);
				}
				else if (value instanceof Date)
				{
					final Date date = (Date)value;
					final Language language = Language.getLanguage(jsonOpts.getAdLanguage());
					return DisplayType.getDateFormat(DisplayType.DateTime, language)
							.format(date);
				}
				else if (value instanceof Number)
				{
					final long millis = ((Number)value).longValue();
					final Date date = new Date(millis);
					final Language language = Language.getLanguage(jsonOpts.getAdLanguage());
					return DisplayType.getDateFormat(DisplayType.DateTime, language)
							.format(date);
				}
				else
				{
					return value.toString();
				}
			}
			else
			{
				return toJsonValue_NoFormat(value, jsonOpts);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} for field {}", value, this, ex);
			return value.toString();
		}
	}

	private BigDecimal roundToPrecision(final BigDecimal bd)
	{
		if (numberPrecision == null)
		{
			return bd;
		}
		else
		{
			return bd.setScale(numberPrecision, RoundingMode.HALF_UP);
		}
	}
}
