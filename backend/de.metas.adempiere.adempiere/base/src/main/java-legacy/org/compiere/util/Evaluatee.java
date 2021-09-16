/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
package org.compiere.util;

import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Evaluator source.
 * <p>
 * To create {@link Evaluatee} instances, please use {@link Evaluatees}.
 */
public interface Evaluatee
{
	@Nullable
	@SuppressWarnings("unchecked")
	default <T> T get_ValueAsObject(final String variableName)
	{
		return (T)get_ValueAsString(variableName);
	}

	/**
	 * Get Variable Value
	 *
	 * @param variableName name
	 * @return value
	 */
	@Nullable
	String get_ValueAsString(String variableName);

	/**
	 * Get variable value as integer.
	 *
	 * @return <ul>
	 * <li>integer value
	 * <li>or <code>defaultValue</code> in case no value was found
	 * <li>or <code>defaultValue</code> in case the value was not parsable as integer
	 * </ul>
	 */
	@Nullable
	default Integer get_ValueAsInt(final String variableName, @Nullable final Integer defaultValue)
	{
		final Object valueObj = get_ValueAsObject(variableName);
		return convertToInteger(variableName, valueObj, defaultValue);
	}

	/**
	 * @return default value or null; never throws exception
	 */
	/* private */
	@Nullable
	static Integer convertToInteger(final String variableName, @Nullable final Object valueObj, @Nullable final Integer defaultValue)
	{
		if (valueObj == null)
		{
			return defaultValue;
		}
		else if (valueObj instanceof Number)
		{
			return ((Number)valueObj).intValue();
		}
		else
		{
			String valueStr = valueObj.toString();
			if (valueStr == null
					|| Env.isPropertyValueNull(variableName, valueStr))
			{
				return defaultValue;
			}

			valueStr = valueStr.trim();
			if (valueStr.isEmpty())
			{
				return defaultValue;
			}

			try
			{
				return Integer.parseInt(valueStr);
			}
			catch (final Exception e)
			{
				LogManager.getLogger(Evaluatee.class).warn("Failed converting {}={} to Integer. Returning default value: {}", variableName, valueStr, defaultValue, e);
				return defaultValue;
			}
		}
	}

	/**
	 * Get variable value as boolean.
	 *
	 * @return <ul>
	 * <li>boolean value
	 * <li>or <code>defaultValue</code> in case no value was found
	 * <li>or <code>defaultValue</code> in case the value was not parsable as boolean
	 * </ul>
	 */
	@Nullable
	default Boolean get_ValueAsBoolean(final String variableName, @Nullable final Boolean defaultValue)
	{
		final Object valueObj = get_ValueAsObject(variableName);
		return StringUtils.toBoolean(valueObj, defaultValue);
	}

	@Nullable
	default BigDecimal get_ValueAsBigDecimal(final String variableName, @Nullable final BigDecimal defaultValue)
	{
		final Object valueObj = get_ValueAsObject(variableName);
		return convertToBigDecimal(variableName, valueObj, defaultValue);
	}

	/**
	 * @return default value or null; never throws exception
	 */
	/* private */
	@Nullable
	static BigDecimal convertToBigDecimal(final String variableName, @Nullable final Object valueObj, @Nullable final BigDecimal defaultValue)
	{
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof BigDecimal)
		{
			return (BigDecimal)valueObj;
		}
		else if (valueObj instanceof Integer)
		{
			return BigDecimal.valueOf((Integer)valueObj);
		}
		else
		{
			String valueStr = valueObj.toString();
			if (valueStr == null || Env.isPropertyValueNull(variableName, valueStr))
			{
				return defaultValue;
			}

			valueStr = valueStr.trim();
			if (valueStr.isEmpty())
			{
				return defaultValue;
			}

			try
			{
				return new BigDecimal(valueStr);
			}
			catch (final Exception e)
			{
				LogManager.getLogger(Evaluatee.class).warn("Failed converting {}={} to BigDecimal. Returning default value: {}", variableName, valueStr, defaultValue, e);
				return defaultValue;
			}
		}
	}

	@Nullable
	default java.util.Date get_ValueAsDate(final String variableName, @Nullable final java.util.Date defaultValue)
	{
		final Object valueObj = get_ValueAsObject(variableName);
		return convertToDate(variableName, valueObj, defaultValue);
	}

	/* private */
	@Nullable
	static java.util.Date convertToDate(final String variableName, @Nullable final Object valueObj, @Nullable final java.util.Date defaultValue)
	{
		if (valueObj == null)
		{
			return defaultValue;
		}
		else if (TimeUtil.isDateOrTimeObject(valueObj))
		{
			return TimeUtil.asDate(valueObj);
		}
		else
		{
			final String valueStr = valueObj.toString();
			if (valueStr == null || valueStr.isEmpty())
			{
				return defaultValue;
			}

			try
			{
				final Timestamp value = Env.parseTimestamp(valueStr);
				return value == null ? defaultValue : value;
			}
			catch (final Exception e)
			{
				LogManager.getLogger(Evaluatee.class).warn("Failed converting {}={} to Date. Returning default value: {}", variableName, valueStr, defaultValue, e);
				return defaultValue;
			}
		}
	}

	default Optional<Object> get_ValueIfExists(@NonNull final String variableName, @NonNull final Class<?> targetType)
	{
		if (Integer.class.equals(targetType)
				|| int.class.equals(targetType))
		{
			final Integer valueInt = get_ValueAsInt(variableName, null);
			return Optional.ofNullable(valueInt);
		}
		else if (java.util.Date.class.equals(targetType))
		{
			final java.util.Date valueDate = get_ValueAsDate(variableName, null);
			return Optional.ofNullable(valueDate);
		}
		else if (Timestamp.class.equals(targetType))
		{
			final Timestamp valueDate = TimeUtil.asTimestamp(get_ValueAsDate(variableName, null));
			return Optional.ofNullable(valueDate);
		}
		else if (Boolean.class.equals(targetType))
		{
			final Boolean valueBoolean = get_ValueAsBoolean(variableName, null);
			return Optional.ofNullable(valueBoolean);
		}
		else
		{
			final Object valueObj = get_ValueAsObject(variableName);
			return Optional.ofNullable(valueObj);
		}
	}

	default Evaluatee andComposeWith(final Evaluatee other)
	{
		return Evaluatees.compose(this, other);
	}
}
