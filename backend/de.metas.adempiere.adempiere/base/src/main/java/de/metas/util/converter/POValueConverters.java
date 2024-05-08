/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.util.converter;

import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;

@UtilityClass
public class POValueConverters
{
	@Nullable
	public static Object convertFromPOValue(@Nullable final Object value, final int displayType, @NonNull final ZoneId zoneId)
	{
		if (value == null)
		{
			return null;
		}

		if (value.getClass().equals(Timestamp.class))
		{
			if (displayType == DisplayType.Time)
			{
				return TimeUtil.asLocalTime(value, zoneId);
			}
			else if (displayType == DisplayType.Date)
			{
				return TimeUtil.asLocalDate(value, zoneId);
			}
			else if (displayType == DisplayType.DateTime)
			{
				return TimeUtil.asInstant(value, zoneId);
			}
		}
		return value;
	}

	@Nullable
	public static Object convertToPOValue(@Nullable final Object value, @NonNull final Class<?> targetClass, final int displayType,@NonNull final ZoneId zoneId)
	{
		if (value == null)
		{
			return null;
		}

		final Class<?> valueClass = value.getClass();

		if (targetClass.isAssignableFrom(valueClass))
		{
			return value;
		}
		else if (Integer.class.equals(targetClass))
		{
			if (String.class.equals(valueClass))
			{
				return new BigDecimal((String)value).intValue();
			}
			else if (RepoIdAware.class.isAssignableFrom(valueClass))
			{
				final RepoIdAware repoIdAware = (RepoIdAware)value;
				return repoIdAware.getRepoId();
			}
		}
		else if (String.class.equals(targetClass))
		{
			return String.valueOf(value);
		}
		else if (Timestamp.class.equals(targetClass))
		{
			final Object valueDate = DateTimeConverters.fromObject(value, displayType, zoneId);

			return TimeUtil.asTimestamp(valueDate);
		}
		else if (Boolean.class.equals(targetClass))
		{
			if (String.class.equals(valueClass))
			{
				return StringUtils.toBoolean(value);
			}
		}
		else if (BigDecimal.class.equals(targetClass))
		{
			if (Double.class.equals(valueClass))
			{
				return BigDecimal.valueOf((Double)value);
			}
			else if (Float.class.equals(valueClass))
			{
				return BigDecimal.valueOf((Float)value);
			}
		}

		throw new RuntimeException("TargetClass " + targetClass + " does not match any supported classes!");
	}
}
