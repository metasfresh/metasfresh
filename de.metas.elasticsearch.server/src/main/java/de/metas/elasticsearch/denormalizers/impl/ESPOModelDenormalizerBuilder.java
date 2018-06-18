package de.metas.elasticsearch.denormalizers.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.Check;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
import de.metas.elasticsearch.types.ESIndexType;
import lombok.NonNull;

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

final class ESPOModelDenormalizerBuilder
{
	private static final List<String> COLUMNNAMES_StandardColumns = ImmutableList.of("Created", "CreatedBy", "Updated", "UpdatedBy", "IsActive");

	private final IESDenormalizerFactory factory;

	private final ESModelIndexerProfile profile;
	private final POInfo poInfo;
	private final String modelTableName;
	private final String keyColumnName;

	private final Set<String> columnsToAlwaysInclude = new HashSet<>();
	private final Set<String> columnsToInclude = new HashSet<>();
	private final Set<String> columnsToExclude = new HashSet<>();
	private final Map<String, ESModelDenormalizerColumn> columnDenormalizersByColumnName = new HashMap<>();
	private final Map<String, ESIndexType> columnsIndexType = new HashMap<>();

	private String currentColumnName;

	ESPOModelDenormalizerBuilder(
			@NonNull final IESDenormalizerFactory factory,
			@NonNull final ESModelIndexerProfile profile,
			@NonNull final String modelTableName)
	{
		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");

		this.factory = factory;
		this.profile = profile;
		this.modelTableName = modelTableName;

		poInfo = POInfo.getPOInfo(modelTableName);
		keyColumnName = poInfo.getKeyColumnName();
		columnsToAlwaysInclude.add(keyColumnName);

		excludeColumn("DocAction"); // FIXME: hardcoded
	}

	public ESPOModelDenormalizer build()
	{
		//
		// Add registered denormalizers
		final Map<String, ESModelDenormalizerColumn> columnDenormalizersEffective = new HashMap<>();
		for (final Map.Entry<String, ESModelDenormalizerColumn> entry : columnDenormalizersByColumnName.entrySet())
		{
			final String columnName = entry.getKey();
			if (!isIncludeColumn(columnName))
			{
				continue;
			}

			final ESModelDenormalizerColumn valueExtractorAndDenormalizer = entry.getValue();
			if (valueExtractorAndDenormalizer == null)
			{
				continue;
			}

			columnDenormalizersEffective.put(columnName, valueExtractorAndDenormalizer);
		}

		//
		// Autodetect column normalizers
		for (int columnIndex = 0, columnsCount = poInfo.getColumnCount(); columnIndex < columnsCount; columnIndex++)
		{
			final String columnName = poInfo.getColumnName(columnIndex);
			if (!isIncludeColumn(columnName))
			{
				continue;
			}

			if (columnDenormalizersEffective.containsKey(columnName))
			{
				continue;
			}

			final ESModelDenormalizerColumn valueExtractorAndDenormalizer = generateColumn(columnName);
			if (valueExtractorAndDenormalizer == null)
			{
				continue;
			}

			columnDenormalizersEffective.put(columnName, valueExtractorAndDenormalizer);
		}

		return new ESPOModelDenormalizer(profile, modelTableName, keyColumnName, columnDenormalizersEffective);
	}

	private final ESModelDenormalizerColumn generateColumn(final String columnName)
	{
		final ESIndexType indexType = getIndexType(columnName);
		final int displayType = poInfo.getColumnDisplayType(columnName);

		//
		// ID column
		if (DisplayType.ID == displayType)
		{
			return ESModelDenormalizerColumn.passThrough(ESDataType.String, indexType);
		}

		//
		// Parent link column
		// NOTE: don't skip parent columns because it might be that we have a value deserializer registered for it
		// if (poInfo.isColumnParent(columnName))
		// {
		// return ESModelDenormalizerColumn.passThrough(ESDataType.String, indexType);
		// }

		//
		// Text
		if (DisplayType.isText(displayType))
		{
			return ESModelDenormalizerColumn.passThrough(ESDataType.String, indexType);
		}

		//
		// Numeric
		if (DisplayType.isNumeric(displayType))
		{
			final ESDataType dataType = DisplayType.Integer == displayType ? ESDataType.Integer : ESDataType.Double;
			return ESModelDenormalizerColumn.passThrough(dataType, indexType);
		}

		//
		// Date
		if (DisplayType.isDate(displayType))
		{
			return ESModelDenormalizerColumn.rawValue(DateDenormalizer.of(displayType, indexType));
		}

		//
		// Boolean
		if (DisplayType.YesNo == displayType)
		{
			return ESModelDenormalizerColumn.passThrough(ESDataType.Boolean, indexType);
		}

		//
		// List/Button list
		final int AD_Reference_ID = poInfo.getColumnReferenceValueId(columnName);
		if (DisplayType.Button == displayType && AD_Reference_ID <= 0)
		{
			return null;
		}
		if ((DisplayType.List == displayType || DisplayType.Button == displayType) && AD_Reference_ID > 0)
		{
			return ESModelDenormalizerColumn.of(PORawValueExtractor.instance, AD_Ref_List_Denormalizer.of(AD_Reference_ID));
		}

		//
		// Lookups:
		// * generic: TableDir, Table, Search
		// * special: ASI, Location, Locator, Color etc
		if (DisplayType.isAnyLookup(displayType))
		{
			final String refTableName = poInfo.getReferencedTableNameOrNull(columnName);
			if (refTableName == null)
			{
				return null;
			}

			final IESModelDenormalizer valueModelDenormalizer = factory.getModelValueDenormalizer(profile, refTableName);
			if (valueModelDenormalizer == null)
			{
				return null;
			}

			return ESModelDenormalizerColumn.of(valueModelDenormalizer);
		}

		return null;
	}

	private boolean isIncludeColumn(final String columnName)
	{
		if (columnsToAlwaysInclude.contains(columnName))
		{
			return true;
		}

		if (!columnsToInclude.isEmpty() && !columnsToInclude.contains(columnName))
		{
			return false;
		}

		if (columnsToExclude.contains(columnName))
		{
			return false;
		}

		return true;
	}

	private ESPOModelDenormalizerBuilder includeColumn(final String columnName, final IESModelValueExtractor valueExtractor, final IESDenormalizer valueDenormalizer)
	{
		final ESModelDenormalizerColumn valueExtractorAndDenormalizer = ESModelDenormalizerColumn.of(valueExtractor, valueDenormalizer);
		includeColumn(columnName);
		columnDenormalizersByColumnName.put(columnName, valueExtractorAndDenormalizer);
		return this;
	}

	public ESPOModelDenormalizerBuilder includeColumn(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "columnName is not empty");
		columnsToInclude.add(columnName);
		currentColumnName = columnName;
		return this;
	}

	public ESPOModelDenormalizerBuilder excludeColumn(final String columnName)
	{
		columnsToExclude.add(columnName);
		return this;
	}

	public ESPOModelDenormalizerBuilder excludeStandardColumns()
	{
		columnsToExclude.addAll(COLUMNNAMES_StandardColumns);
		return this;
	}

	public ESPOModelDenormalizerBuilder index(final ESIndexType esIndexType)
	{
		Check.assumeNotEmpty(currentColumnName, "lastIncludedColumn is not empty");
		Check.assumeNotNull(esIndexType, "Parameter esIndexType is not null");

		columnsIndexType.put(currentColumnName, esIndexType);

		return this;
	}

	private ESIndexType getIndexType(final String columnName)
	{
		final ESIndexType esIndexType = columnsIndexType.get(columnName);
		return esIndexType != null ? esIndexType : ESIndexType.NotAnalyzed;
	}
}
