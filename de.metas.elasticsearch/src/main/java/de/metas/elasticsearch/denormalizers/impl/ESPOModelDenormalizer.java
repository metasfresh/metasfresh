package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
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

/*package*/class ESPOModelDenormalizer implements IESModelDenormalizer
{
	public static final ESPOModelDenormalizerBuilder builder(final IESDenormalizerFactory factory, final String modelTableName)
	{
		return new ESPOModelDenormalizerBuilder(factory, modelTableName);
	}

	private final String modelTableName;
	private final String keyColumnName;
	private final ImmutableSet<String> columnNames;
	private final Map<String, ESModelDenormalizerColumn> columnDenormalizers;

	private ESPOModelDenormalizer(final String modelTableName, final String keyColumnName //
	, final Map<String, ESModelDenormalizerColumn> columnDenormalizers //
	)
	{
		super();

		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		this.modelTableName = modelTableName;

		// Check.assumeNotEmpty(keyColumnName, "keyColumnName is not empty"); // null is accepted
		this.keyColumnName = keyColumnName;

		Check.assumeNotEmpty(columnDenormalizers, "columnDenormalizers is not empty");
		this.columnDenormalizers = ImmutableMap.copyOf(columnDenormalizers);

		final ImmutableSet.Builder<String> columnNames = ImmutableSet.builder();
		columnNames.addAll(columnDenormalizers.keySet());
		if (keyColumnName != null)
		{
			columnNames.add(keyColumnName);
		}
		this.columnNames = columnNames.build();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("modelTableName", modelTableName)
				.toString();
	}

	@Override
	public String getModelTableName()
	{
		return modelTableName;
	}

	public Set<String> getColumnNames()
	{
		return columnNames;
	}

	@Override
	public void appendMapping(final XContentBuilder builder, final String fieldName) throws IOException
	{
		final boolean isTopLevel = fieldName == null;

		if (!isTopLevel)
		{
			builder.startObject(fieldName);
			builder.field("type", ESDataType.Object.getEsTypeAsString());
			builder.startObject("properties");
		}

		for (final Map.Entry<String, ESModelDenormalizerColumn> entry : columnDenormalizers.entrySet())
		{
			final String columnName = entry.getKey();
			final ESModelDenormalizerColumn columnDenorm = entry.getValue();
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
		for (final Map.Entry<String, ESModelDenormalizerColumn> columnNameAndDenorm : columnDenormalizers.entrySet())
		{
			final String columnName = columnNameAndDenorm.getKey();

			final ESModelDenormalizerColumn columnValueExtractorAndDenormalizer = columnNameAndDenorm.getValue();
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

	@FunctionalInterface
	private static interface IESValueExtractor
	{
		Object extractValue(Object model, String columnName);
	}

	private static final class PORawValueExtractor implements IESValueExtractor
	{
		public static final transient PORawValueExtractor instance = new PORawValueExtractor();

		private PORawValueExtractor()
		{
			super();
		}

		@Override
		public Object extractValue(final Object model, final String columnName)
		{
			final PO po = InterfaceWrapperHelper.getPO(model);
			final Object value = po.get_Value(columnName);
			return value;
		}
	}

	private static final class POModelValueExtractor implements IESValueExtractor
	{
		public static final POModelValueExtractor of(final String modelValueTableName)
		{
			return new POModelValueExtractor(modelValueTableName);
		}

		private final String modelValueTableName;

		private POModelValueExtractor(final String modelValueTableName)
		{
			super();
			Check.assumeNotEmpty(modelValueTableName, "modelValueTableName is not empty");
			this.modelValueTableName = modelValueTableName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper("extractor")
					.addValue(modelValueTableName)
					.toString();
		}

		@Override
		public Object extractValue(final Object model, final String columnName)
		{
			final PO po = InterfaceWrapperHelper.getPO(model);
			final Object valueAsModel = po.get_ValueAsPO(columnName, modelValueTableName);
			return valueAsModel;
		}
	}

	public static final class ESPOModelDenormalizerBuilder
	{
		private static final List<String> COLUMNNAMES_StandardColumns = ImmutableList.of("Created", "CreatedBy", "Updated", "UpdatedBy", "IsActive");

		private final IESDenormalizerFactory factory;

		private final POInfo poInfo;
		private final String modelTableName;
		private final String keyColumnName;

		private final Set<String> columnsToAlwaysInclude = new HashSet<>();
		private final Set<String> columnsToInclude = new HashSet<>();
		private final Set<String> columnsToExclude = new HashSet<>();
		private final Map<String, ESModelDenormalizerColumn> columnDenormalizers = new HashMap<>();
		private final Map<String, ESIndexType> columnsIndexType = new HashMap<>();

		private String currentColumnName;

		private ESPOModelDenormalizerBuilder(final IESDenormalizerFactory factory, final String modelTableName)
		{
			super();
			Check.assumeNotNull(factory, "Parameter factory is not null");
			this.factory = factory;

			Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
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
			for (final Map.Entry<String, ESModelDenormalizerColumn> entry : columnDenormalizers.entrySet())
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

			return new ESPOModelDenormalizer(modelTableName, keyColumnName, columnDenormalizersEffective);
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

				final IESModelDenormalizer valueModelDenormalizer = factory.getModelValueDenormalizer(refTableName);
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

		public ESPOModelDenormalizerBuilder includeColumn(final String columnName, final IESValueExtractor valueExtractor, final IESDenormalizer valueDenormalizer)
		{
			final ESModelDenormalizerColumn valueExtractorAndDenormalizer = ESModelDenormalizerColumn.of(valueExtractor, valueDenormalizer);
			includeColumn(columnName);
			columnDenormalizers.put(columnName, valueExtractorAndDenormalizer);
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

	private static final class ESModelDenormalizerColumn
	{
		public static final ESModelDenormalizerColumn of(final IESValueExtractor valueExtractor, final IESDenormalizer valueDenormalizer)
		{
			return new ESModelDenormalizerColumn(valueExtractor, valueDenormalizer);
		}

		public static final ESModelDenormalizerColumn of(final IESModelDenormalizer valueModelDenormalizer)
		{
			final String valueModelTableName = valueModelDenormalizer.getModelTableName();
			final IESValueExtractor valueExtractor = POModelValueExtractor.of(valueModelTableName);
			return new ESModelDenormalizerColumn(valueExtractor, valueModelDenormalizer);
		}

		public static final ESModelDenormalizerColumn passThrough(final ESDataType dataType, final ESIndexType indexType)
		{
			final PassThroughDenormalizer valueDenormalizer = PassThroughDenormalizer.of(dataType, indexType);
			return new ESModelDenormalizerColumn(PORawValueExtractor.instance, valueDenormalizer);
		}

		public static final ESModelDenormalizerColumn rawValue(final IESDenormalizer valueDenormalizer)
		{
			return new ESModelDenormalizerColumn(PORawValueExtractor.instance, valueDenormalizer);
		}

		private final IESValueExtractor valueExtractor;
		private final IESDenormalizer valueDenormalizer;

		private ESModelDenormalizerColumn(final IESValueExtractor valueExtractor, final IESDenormalizer valueDenormalizer)
		{
			super();

			Check.assumeNotNull(valueExtractor, "Parameter valueExtractor is not null");
			this.valueExtractor = valueExtractor;

			Check.assumeNotNull(valueDenormalizer, "Parameter valueDenormalizer is not null");
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
}
