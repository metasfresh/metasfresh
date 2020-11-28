package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;

import org.compiere.model.PO;
import org.elasticsearch.common.xcontent.XContentBuilder;

import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
import de.metas.elasticsearch.types.ESIndexType;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2018 metas GmbH
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
final class ESPOModelDenormalizerColumn
{
	public static final ESPOModelDenormalizerColumn of(final IESModelValueExtractor valueExtractor, final IESDenormalizer valueDenormalizer)
	{
		return new ESPOModelDenormalizerColumn(valueExtractor, valueDenormalizer);
	}

	public static final ESPOModelDenormalizerColumn of(final IESModelDenormalizer valueModelDenormalizer)
	{
		final String valueModelTableName = valueModelDenormalizer.getModelTableName();
		final IESModelValueExtractor valueExtractor = POModelValueExtractor.of(valueModelTableName);
		return new ESPOModelDenormalizerColumn(valueExtractor, valueModelDenormalizer);
	}

	public static final ESPOModelDenormalizerColumn passThrough(final ESDataType dataType, final ESIndexType indexType)
	{
		final String analyzer = null;
		return passThrough(dataType, indexType, analyzer);
	}

	public static final ESPOModelDenormalizerColumn passThrough(final ESDataType dataType, final ESIndexType indexType, final String analyzer)
	{
		final PassThroughDenormalizer valueDenormalizer = PassThroughDenormalizer.of(dataType, indexType, analyzer);
		return new ESPOModelDenormalizerColumn(PORawValueExtractor.instance, valueDenormalizer);
	}

	public static final ESPOModelDenormalizerColumn rawValue(final IESDenormalizer valueDenormalizer)
	{
		return new ESPOModelDenormalizerColumn(PORawValueExtractor.instance, valueDenormalizer);
	}

	private final IESModelValueExtractor valueExtractor;
	private final IESDenormalizer valueDenormalizer;

	private ESPOModelDenormalizerColumn(@NonNull final IESModelValueExtractor valueExtractor, @NonNull final IESDenormalizer valueDenormalizer)
	{
		this.valueExtractor = valueExtractor;
		this.valueDenormalizer = valueDenormalizer;
	}

	public Object extractValueAndDenormalize(final PO po, final String columnName)
	{
		final Object value = valueExtractor.extractValue(po, columnName);
		if (value == null)
		{
			return null;
		}

		final Object valueDenorm = valueDenormalizer.denormalize(value);
		return valueDenorm;
	}

	public void appendMapping(final XContentBuilder builder, final String columnName) throws IOException
	{
		valueDenormalizer.appendMapping(builder, columnName);
	}
}
