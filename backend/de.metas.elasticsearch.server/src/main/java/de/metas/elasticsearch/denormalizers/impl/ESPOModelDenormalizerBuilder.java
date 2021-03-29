package de.metas.elasticsearch.denormalizers.impl;

import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.config.ESTextAnalyzer;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
	private final HashMap<String, ESDataType> columnsType = new HashMap<>();

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
		if (poInfo == null)
		{
			throw new AdempiereException("No POInfo found for " + modelTableName);
		}

		keyColumnName = poInfo.getKeyColumnName();
		columnsToAlwaysInclude.add(keyColumnName);

		excludeColumn("DocAction"); // FIXME: hardcoded
	}

	public ESPOModelDenormalizer build()
	{
		final Map<String, ESPOModelDenormalizerColumn> columnDenormalizersEffective = new HashMap<>();
		final Set<String> fullTextSearchFieldNames = new LinkedHashSet<>();

		//
		// Autodetect column normalizers
		for (int columnIndex = 0, columnsCount = poInfo.getColumnCount(); columnIndex < columnsCount; columnIndex++)
		{
			final String columnName = Objects.requireNonNull(poInfo.getColumnName(columnIndex));

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

			if (hasFullTextSearchSupport(columnName))
			{
				fullTextSearchFieldNames.add(columnName);
			}
		}

		return new ESPOModelDenormalizer(profile, modelTableName, keyColumnName, columnDenormalizersEffective, fullTextSearchFieldNames);
	}

	private boolean hasFullTextSearchSupport(@NonNull final String columnName)
	{
		final int displayType = poInfo.getColumnDisplayType(columnName);
		return hasFullTextSearchSupport(columnName, displayType);
	}

	private static boolean hasFullTextSearchSupport(@NonNull final String columnName, final int displayType)
	{
		// Exclude passwords
		if (DisplayType.isPassword(columnName, displayType))
		{
			return false;
		}

		return DisplayType.isText(displayType)
				|| displayType == DisplayType.Location;
	}

	@Nullable
	private ESPOModelDenormalizerColumn generateColumn(final String columnName)
	{
		final int displayType = poInfo.getColumnDisplayType(columnName);
		final int AD_Reference_ID = poInfo.getColumnReferenceValueId(columnName);
		//final ESIndexType indexType = getIndexType(columnName, displayType);
		final ESDataType dataTypeOverride = columnsType.get(columnName);

		//
		// ID column
		if (DisplayType.ID == displayType)
		{
			return ESPOModelDenormalizerColumn.passThrough(dataTypeOverride != null ? dataTypeOverride : ESDataType.Keyword);
		}

		//
		// Parent link column
		// NOTE: don't skip parent columns because it might be that we have a value deserializer registered for it
		// else if (poInfo.isColumnParent(columnName))
		// {
		// return ESModelDenormalizerColumn.passThrough(ESDataType.String, indexType);
		// }

		//
		// Text
		else if (DisplayType.isText(displayType))
		{
			final ESTextAnalyzer textAnalyzer = profile.getTextAnalyzer();
			ESDataType dataType = dataTypeOverride;
			if (dataType == null)
			{
				dataType = textAnalyzer == ESTextAnalyzer.FULL_TEXT_SEARCH && hasFullTextSearchSupport(columnName, displayType)
						? ESDataType.Text
						: ESDataType.Keyword;
			}
			return ESPOModelDenormalizerColumn.passThrough(dataType, textAnalyzer);
		}

		//
		// Numeric
		else if (DisplayType.isNumeric(displayType))
		{
			ESDataType dataType = dataTypeOverride;
			if (dataType == null)
			{
				dataType = DisplayType.Integer == displayType ? ESDataType.Integer : ESDataType.Double;
			}
			return ESPOModelDenormalizerColumn.passThrough(dataType);
		}

		//
		// Date
		else if (DisplayType.isDate(displayType))
		{
			Check.assumeNull(dataTypeOverride, "overriding type for {} is not allowed", columnName);
			return ESPOModelDenormalizerColumn.rawValue(DateDenormalizer.of(displayType));
		}

		//
		// Boolean
		else if (DisplayType.YesNo == displayType)
		{
			Check.assumeNull(dataTypeOverride, "overriding type for {} is not allowed", columnName);
			return ESPOModelDenormalizerColumn.passThrough(ESDataType.Boolean);
		}

		//
		// List/Button list
		else if (DisplayType.Button == displayType && AD_Reference_ID <= 0)
		{
			Check.assumeNull(dataTypeOverride, "overriding type for {} is not allowed", columnName);
			return null;
		}
		else if ((DisplayType.List == displayType || DisplayType.Button == displayType) && AD_Reference_ID > 0)
		{
			Check.assumeNull(dataTypeOverride, "overriding type for {} is not allowed", columnName);
			return ESPOModelDenormalizerColumn.of(PORawValueExtractor.instance, AD_Ref_List_Denormalizer.instance);
		}

		//
		// Lookups:
		// * generic: TableDir, Table, Search
		// * special: ASI, Location, Locator, Color etc
		else if (DisplayType.isAnyLookup(displayType))
		{
			Check.assumeNull(dataTypeOverride, "overriding type for {} is not allowed", columnName);

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

		//
		//
		else
		{
			Check.assumeNull(dataTypeOverride, "overriding type for {} is not allowed", columnName);

			return null;
		}
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

		return !columnsToExclude.contains(columnName);
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

	public ESPOModelDenormalizerBuilder type(@NonNull final ESDataType esDataType)
	{
		Check.assumeNotEmpty(currentColumnName, "lastIncludedColumn is not empty");
		columnsType.put(currentColumnName, esDataType);
		return this;
	}
}
