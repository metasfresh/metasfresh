package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;

import org.adempiere.util.Check;
import org.elasticsearch.common.xcontent.XContentBuilder;

import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.types.ESIndexType;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DateDenormalizer implements IESDenormalizer
{
	public static final DateDenormalizer of(final int dateDisplayType, final ESIndexType indexType)
	{
		return new DateDenormalizer(dateDisplayType, indexType);
	}

	private final ESIndexType indexType;

	private DateDenormalizer(final int dateDisplayType, final ESIndexType indexType)
	{
		super();

		// TODO: use dateDisplayType to set the right date_format

		Check.assumeNotNull(indexType, "Parameter indexType is not null");
		this.indexType = indexType;
	}

	@Override
	public void appendMapping(final XContentBuilder builder, final String fieldName) throws IOException
	{
		//@formatter:off
		builder.startObject(fieldName)
				.field("type", "date")
				.field("index", indexType.getEsTypeAsString())
			.endObject();
		//@formatter:on
	}

	@Override
	public Object denormalize(final Object value)
	{
		return value;
	}

}
