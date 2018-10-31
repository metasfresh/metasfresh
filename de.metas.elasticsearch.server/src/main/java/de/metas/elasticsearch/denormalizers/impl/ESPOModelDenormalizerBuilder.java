package de.metas.elasticsearch.denormalizers.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
import de.metas.elasticsearch.types.ESIndexType;
import de.metas.util.Check;
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
	private final Map<String, ESPOModelDenormalizerColumn> columnDenormalizersByColumnName = new HashMap<>();
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
		final Map<String, ESPOModelDenormalizerColumn> columnDenormalizersEffective = new HashMap<>();
		final Set<String> fullTextSearchFieldNames = new LinkedHashSet<>();

		//
		// Add registered denormalizers
		for (final Map.Entry<String, ESPOModelDenormalizerColumn> entry : columnDenormalizersByColumnName.entrySet())
		{
			final String columnName = entry.getKey();
			if (!isIncludeColumn(columnName))
			{
				continue;
			}

			final ESPOModelDenormalizerColumn valueExtractorAndDenormalizer = entry.getValue();
			if (valueExtractorAndDenormalizer == null)
			{
				continue;
			}

			columnDenormalizersEffective.put(columnName, valueExtractorAndDenormalizer);

			if (isFullTextSearchField(columnName))
			{
				fullTextSearchFieldNames.add(columnName);
			}
		}

		//
		// Autodetect column normalizers
		for (int columnIndex = 0, columnsCount = poInfo.getColumnCount(); columnIndex < columnsCount; columnIndex++)
		{
			final String columnName = poInfo.getColumnName(columnIndex);

			// skip if it was explicitly banned
			if (!isIncludeColumn(columnName))
			{
				continue;
			}

			// skip if already considered
			if (columnDenormalizersEffective.containsKey(columnName))
			{
				continue;
			}

			// skip if not eligible for column auto-generation
			if (!isEligibleForColumnAutoGeneration(columnName))
			{
				continue;
			}

			// Generate and add
			final ESPOModelDenormalizerColumn valueExtractorAndDenormalizer = generateColumn(columnName);
			if (valueExtractorAndDenormalizer == null)
			{
				continue;
			}
			columnDenormalizersEffective.put(columnName, valueExtractorAndDenormalizer);

			if (isFullTextSearchField(columnName))
			{
				fullTextSearchFieldNames.add(columnName);
			}
		}

		return new ESPOModelDenormalizer(profile, modelTableName, keyColumnName, columnDenormalizersEffective, fullTextSearchFieldNames);
	}

	private boolean isFullTextSearchField(String columnName)
	{
		final int displayType = poInfo.getColumnDisplayType(columnName);

		// Exclude passwords
		if (DisplayType.isPassword(columnName, displayType))
		{
			return false;
		}

		if (DisplayType.isText(displayType))
		{
			return true;
		}
		else if (displayType == DisplayType.Location)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private final ESPOModelDenormalizerColumn generateColumn(final String columnName)
	{
		final int displayType = poInfo.getColumnDisplayType(columnName);
		final ESIndexType indexType = getIndexType(columnName, displayType);

		//
		// ID column
		if (DisplayType.ID == displayType)
		{
			return ESPOModelDenormalizerColumn.passThrough(ESDataType.String, indexType);
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
			final String analyzer = getAnalyzer(columnName);
			return ESPOModelDenormalizerColumn.passThrough(ESDataType.String, indexType, analyzer);
		}

		//
		// Numeric
		if (DisplayType.isNumeric(displayType))
		{
			final ESDataType dataType = DisplayType.Integer == displayType ? ESDataType.Integer : ESDataType.Double;
			return ESPOModelDenormalizerColumn.passThrough(dataType, indexType);
		}

		//
		// Date
		if (DisplayType.isDate(displayType))
		{
			return ESPOModelDenormalizerColumn.rawValue(DateDenormalizer.of(displayType, indexType));
		}

		//
		// Boolean
		if (DisplayType.YesNo == displayType)
		{
			return ESPOModelDenormalizerColumn.passThrough(ESDataType.Boolean, indexType);
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
			return ESPOModelDenormalizerColumn.of(PORawValueExtractor.instance, AD_Ref_List_Denormalizer.of(AD_Reference_ID));
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

			return ESPOModelDenormalizerColumn.of(valueModelDenormalizer);
		}

		return null;
	}

	private boolean isEligibleForColumnAutoGeneration(final String columnName)
	{
		final int displayType = poInfo.getColumnDisplayType(columnName);

		// ID column
		if (DisplayType.ID == displayType)
		{
			return true;
		}

		if (profile == ESModelIndexerProfile.FULL_TEXT_SEARCH)
		{
			return DisplayType.isText(displayType) || DisplayType.isAnyLookup(displayType);
		}

		return true;
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

	private ESIndexType getIndexType(final String columnName, final int displayType)
	{
		final ESIndexType esIndexType = columnsIndexType.get(columnName);
		return esIndexType != null ? esIndexType : getDefaultIndexType(displayType);
	}

	private ESIndexType getDefaultIndexType(final int displayType)
	{
		if (profile == ESModelIndexerProfile.FULL_TEXT_SEARCH)
		{
			if (DisplayType.isText(displayType))
			{
				return ESIndexType.Analyzed;
			}
		}

		// fallback
		return ESIndexType.NotAnalyzed;
	}

	private String getAnalyzer(final String columnName)
	{
		return profile.getDefaultAnalyzer();
	}
}
