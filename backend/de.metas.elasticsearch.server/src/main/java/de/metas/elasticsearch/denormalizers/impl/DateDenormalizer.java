package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;
import org.elasticsearch.common.xcontent.XContentBuilder;

import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.types.ESIndexType;
import lombok.NonNull;
import lombok.ToString;

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
final class DateDenormalizer implements IESDenormalizer
{
	public static DateDenormalizer of(final int dateDisplayType, final ESIndexType indexType)
	{
		return new DateDenormalizer(dateDisplayType, indexType);
	}

	private static final DateTimeFormatter FORMATTER_StrictDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final ESIndexType indexType;
	private final int dateDisplayType;

	private DateDenormalizer(final int dateDisplayType, @NonNull final ESIndexType indexType)
	{
		this.dateDisplayType = dateDisplayType;
		this.indexType = indexType;
	}

	@Override
	public void appendMapping(final Object builderObj, final String fieldName) throws IOException
	{
		final XContentBuilder builder = ESDenormalizerHelper.extractXContentBuilder(builderObj);
		//@formatter:off
		builder.startObject(fieldName)
				.field("type", "date")
				.field("index", indexType.getEsTypeAsString())
				.field("format", "strict_date_optional_time||epoch_millis")
			.endObject();
		//@formatter:on
	}

	@Override
	public Object denormalize(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		if (dateDisplayType == DisplayType.Date)
		{
			return FORMATTER_StrictDate.format(toTemporal(value));
		}
		else if (dateDisplayType == DisplayType.DateTime)
		{
			return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(toTemporal(value));
		}
		return value;
	}

	private Temporal toTemporal(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof java.util.Date)
		{
			final java.util.Date date = (java.util.Date)value;
			return ZonedDateTime.ofInstant(date.toInstant(), SystemTime.zoneId());
		}
		else if (value instanceof Temporal)
		{
			return (Temporal)value;
		}
		else
		{
			throw new AdempiereException("Unsupported date value '" + value + "' (" + value.getClass() + "))");
		}
	}
}
