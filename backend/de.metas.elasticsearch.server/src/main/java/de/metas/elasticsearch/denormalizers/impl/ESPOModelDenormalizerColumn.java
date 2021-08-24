package de.metas.elasticsearch.denormalizers.impl;

import de.metas.elasticsearch.config.ESTextAnalyzer;
import de.metas.elasticsearch.denormalizers.IESValueDenormalizer;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.source.ESModelToIndex;
import lombok.NonNull;
import lombok.ToString;
import org.elasticsearch.common.xcontent.XContentBuilder;

import javax.annotation.Nullable;
import java.io.IOException;

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
	public static ESPOModelDenormalizerColumn of(final IESModelValueExtractor valueExtractor, final IESValueDenormalizer valueDenormalizer)
	{
		return new ESPOModelDenormalizerColumn(valueExtractor, valueDenormalizer);
	}

	public static ESPOModelDenormalizerColumn of(final IESModelDenormalizer valueModelDenormalizer)
	{
		final String valueModelTableName = valueModelDenormalizer.getModelTableName();
		final IESModelValueExtractor valueExtractor = POModelValueExtractor.of(valueModelTableName);
		final IESValueDenormalizer valueDenormalizer = ModelAsValueDenormalizerWrapper.of(valueModelDenormalizer);
		return new ESPOModelDenormalizerColumn(valueExtractor, valueDenormalizer);
	}

	public static ESPOModelDenormalizerColumn passThrough(final ESDataType dataType)
	{
		return passThrough(dataType, null);
	}

	public static ESPOModelDenormalizerColumn passThrough(
			@NonNull final ESDataType dataType,
			@Nullable final ESTextAnalyzer textAnalyzer)
	{
		return new ESPOModelDenormalizerColumn(
				PORawValueExtractor.instance,
				PassThroughValueDenormalizer.of(dataType, textAnalyzer));
	}

	public static ESPOModelDenormalizerColumn rawValue(final IESValueDenormalizer valueDenormalizer)
	{
		return new ESPOModelDenormalizerColumn(PORawValueExtractor.instance, valueDenormalizer);
	}

	private final IESModelValueExtractor valueExtractor;
	private final IESValueDenormalizer valueDenormalizer;

	private ESPOModelDenormalizerColumn(
			@NonNull final IESModelValueExtractor valueExtractor,
			@NonNull final IESValueDenormalizer valueDenormalizer)
	{
		this.valueExtractor = valueExtractor;
		this.valueDenormalizer = valueDenormalizer;
	}

	@Nullable
	public Object extractValueAndDenormalize(final ESModelToIndex model, final String columnName)
	{
		final Object value = valueExtractor.extractValue(model, columnName);
		return value != null
				? valueDenormalizer.denormalizeValue(value)
				: null;
	}

	public void appendMapping(final XContentBuilder builder, final String columnName) throws IOException
	{
		valueDenormalizer.appendMapping(builder, columnName);
	}
}
