package de.metas.elasticsearch.denormalizers.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.elasticsearch.denormalizers.IESValueDenormalizer;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.elasticsearch.common.xcontent.XContentBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/*
 * #%L
 * de.metas.business
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

@ToString
final class DateDenormalizer implements IESValueDenormalizer
{
	public static DateDenormalizer of(final int dateDisplayType)
	{
		return new DateDenormalizer(dateDisplayType);
	}

	private static final DateTimeFormatter FORMATTER_StrictDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final int dateDisplayType;

	private DateDenormalizer(final int dateDisplayType)
	{
		Check.assume(DisplayType.isDate(dateDisplayType), "dateDisplayType shall be a date type but it was {}", dateDisplayType);
		this.dateDisplayType = dateDisplayType;
	}

	@Override
	public void appendMapping(final XContentBuilder builder, final String fieldName) throws IOException
	{
		//@formatter:off
		builder.startObject(fieldName)
				.field("type", "date")
				.field("format", "strict_date_optional_time||epoch_millis")
			.endObject();
		//@formatter:on
	}

	@Override
	public Object denormalizeValue(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (dateDisplayType == DisplayType.Date)
		{
			return FORMATTER_StrictDate.format(toTemporal(value));
		}
		else if (dateDisplayType == DisplayType.DateTime)
		{
			return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(toTemporal(value));
		}
		else
		{
			return value;
		}
	}

	private static Temporal toTemporal(@NonNull final Object value)
	{
		if (value instanceof Temporal)
		{
			return (Temporal)value;
		}
		else
		{
			return TimeUtil.asZonedDateTime(value, SystemTime.zoneId());
		}
	}
}
