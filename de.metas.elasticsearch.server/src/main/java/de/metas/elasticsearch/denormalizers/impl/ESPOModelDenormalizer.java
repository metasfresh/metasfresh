package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

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

/* package */class ESPOModelDenormalizer implements IESModelDenormalizer
{
	public static final ESPOModelDenormalizerBuilder builder(
			final IESDenormalizerFactory factory,
			final ESModelIndexerProfile profile,
			final String modelTableName)
	{
		return new ESPOModelDenormalizerBuilder(factory, profile, modelTableName);
	}

	@Getter
	private final ESModelIndexerProfile profile;
	@Getter
	private final String modelTableName;
	private final String keyColumnName;
	@Getter
	private final ImmutableSet<String> columnNames;
	private final ImmutableMap<String, ESPOModelDenormalizerColumn> columnDenormalizers;
	@Getter
	private final ImmutableSet<String> fullTextSearchFieldNames;

	ESPOModelDenormalizer(
			@NonNull final ESModelIndexerProfile profile,
			@NonNull final String modelTableName,
			final String keyColumnName, // null is accepted
			final Map<String, ESPOModelDenormalizerColumn> columnDenormalizers,
			final Set<String> fullTextSearchFieldNames)
	{
		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		Check.assumeNotEmpty(columnDenormalizers, "columnDenormalizers is not empty");

		this.profile = profile;
		this.modelTableName = modelTableName;
		this.keyColumnName = keyColumnName;
		this.columnDenormalizers = ImmutableMap.copyOf(columnDenormalizers);

		final ImmutableSet.Builder<String> columnNames = ImmutableSet.builder();
		columnNames.addAll(columnDenormalizers.keySet());
		if (keyColumnName != null)
		{
			columnNames.add(keyColumnName);
		}
		this.columnNames = columnNames.build();

		this.fullTextSearchFieldNames = fullTextSearchFieldNames != null ? ImmutableSet.copyOf(fullTextSearchFieldNames) : ImmutableSet.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("profile", profile)
				.add("modelTableName", modelTableName)
				.toString();
	}

	@Override
	public void appendMapping(final Object builderObj, final String fieldName) throws IOException
	{
		final XContentBuilder builder = ESDenormalizerHelper.extractXContentBuilder(builderObj);
		final boolean isTopLevel = fieldName == null;

		if (!isTopLevel)
		{
			builder.startObject(fieldName);
			builder.field("type", ESDataType.Object.getEsTypeAsString());
			builder.startObject("properties");
		}

		for (final Map.Entry<String, ESPOModelDenormalizerColumn> entry : columnDenormalizers.entrySet())
		{
			final String columnName = entry.getKey();
			final ESPOModelDenormalizerColumn columnDenorm = entry.getValue();
			columnDenorm.appendMapping(builder, columnName);
		}

		if (!isTopLevel)
		{
			builder.endObject(); // properties
			builder.endObject(); // fieldName
		}
	}

	@Override
	public Map<String, Object> denormalize(final Object model)
	{
		final Map<String, Object> result = new LinkedHashMap<>();
		final PO po = InterfaceWrapperHelper.getPO(model);
		for (final Map.Entry<String, ESPOModelDenormalizerColumn> columnNameAndDenorm : columnDenormalizers.entrySet())
		{
			final String columnName = columnNameAndDenorm.getKey();

			final ESPOModelDenormalizerColumn columnValueExtractorAndDenormalizer = columnNameAndDenorm.getValue();
			final Object valueDenorm = columnValueExtractorAndDenormalizer.extractValueAndDenormalize(po, columnName);
			if (valueDenorm == null)
			{
				continue;
			}

			result.put(columnName, valueDenorm);
		}

		return result;
	}

	@Override
	public String extractId(final Object model)
	{
		if (keyColumnName == null)
		{
			return null;
		}
		final Object idObj = PORawValueExtractor.instance.extractValue(model, keyColumnName);
		return idObj == null ? null : idObj.toString();
	}
}
