package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
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
final class PassThroughDenormalizer implements IESDenormalizer
{
	public static final PassThroughDenormalizer of(
			final ESDataType dataType,
			final ESIndexType indexType,
			final String analyzer)
	{
		return new PassThroughDenormalizer(dataType, indexType, analyzer);
	}

	private final ESDataType dataType;
	private final ESIndexType indexType;
	private final String analyzer;

	private PassThroughDenormalizer(
			@NonNull final ESDataType dataType,
			@NonNull final ESIndexType indexType,
			final String analyzer)
	{
		this.dataType = dataType;
		this.indexType = indexType;
		this.analyzer = analyzer;
	}

	@Override
	public void appendMapping(final Object builderObj, final String fieldName) throws IOException
	{
		final XContentBuilder builder = ESDenormalizerHelper.extractXContentBuilder(builderObj);
		builder.startObject(fieldName);

		builder.field("type", dataType.getEsTypeAsString());
		builder.field("index", indexType.getEsTypeAsString());

		if (analyzer != null)
		{
			builder.field("analyzer", analyzer);
		}

		builder.endObject();
	}

	@Override
	public Object denormalize(final Object value)
	{
		return value;
	}
}
