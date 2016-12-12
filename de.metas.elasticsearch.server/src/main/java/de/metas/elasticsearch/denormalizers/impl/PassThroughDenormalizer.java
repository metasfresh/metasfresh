package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;

import org.adempiere.util.Check;
import org.elasticsearch.common.xcontent.XContentBuilder;

import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
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

public final class PassThroughDenormalizer implements IESDenormalizer
{
	public static final PassThroughDenormalizer of(final ESDataType dataType, final ESIndexType indexType)
	{
		return new PassThroughDenormalizer(dataType, indexType);
	}

	private final ESDataType dataType;
	private final ESIndexType indexType;

	private PassThroughDenormalizer(final ESDataType dataType, final ESIndexType indexType)
	{
		super();
		Check.assumeNotNull(dataType, "Parameter dataType is not null");
		this.dataType = dataType;
		Check.assumeNotNull(indexType, "Parameter indexType is not null");
		this.indexType = indexType;
	}

	@Override
	public void appendMapping(final Object builderObj, final String fieldName) throws IOException
	{
		final XContentBuilder builder = ESDenormalizerHelper.extractXContentBuilder(builderObj);
		builder.startObject(fieldName)
				.field("type", dataType.getEsTypeAsString())
				.field("index", indexType.getEsTypeAsString())
				.endObject();
	}

	@Override
	public Object denormalize(final Object value)
	{
		return value;
	}
}
