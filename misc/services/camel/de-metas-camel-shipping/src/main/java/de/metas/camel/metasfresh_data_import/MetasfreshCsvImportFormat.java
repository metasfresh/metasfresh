package de.metas.camel.metasfresh_data_import;

import java.time.format.DateTimeFormatter;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.camel.metasfresh_data_import.MetasfreshColumnCsvImportFormat.DataType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@ToString(exclude = "newLine")
public class MetasfreshCsvImportFormat
{
	boolean skipHeaderRecord;

	@NonNull
	String delimiter;

	@NonNull
	String newLine;

	@NonNull
	ImmutableList<@NonNull String> columnNames;

	@Getter(AccessLevel.NONE)
	private final ImmutableMap<@NonNull String, MetasfreshColumnCsvImportFormat> columnsByColumnName;

	@Builder
	private MetasfreshCsvImportFormat(
			boolean skipHeaderRecord,
			@NonNull String delimiter,
			@NonNull String newLine,
			@NonNull @Singular ImmutableList<MetasfreshColumnCsvImportFormat> columns)
	{
		this.skipHeaderRecord = skipHeaderRecord;
		this.delimiter = delimiter;
		this.newLine = newLine;

		columnNames = columns.stream()
				.map(MetasfreshColumnCsvImportFormat::getColumnName)
				.collect(ImmutableList.toImmutableList());

		columnsByColumnName = Maps.uniqueIndex(columns, MetasfreshColumnCsvImportFormat::getColumnName);
	}

	public Object formatValue(@NonNull final String columnName, @Nullable final Object value)
	{
		return getColumn(columnName).formatValue(value);
	}

	public boolean hasColumn(final String columnName)
	{
		return columnsByColumnName.get(columnName) != null;
	}

	private MetasfreshColumnCsvImportFormat getColumn(@NonNull final String columnName)
	{
		MetasfreshColumnCsvImportFormat column = columnsByColumnName.get(columnName);
		if (column == null)
		{
			throw new IllegalArgumentException("Column `" + columnName + "` not found in " + columnsByColumnName.values());
		}
		return column;
	}

	//
	//
	//
	//
	//

	public static class MetasfreshCsvImportFormatBuilder
	{
		public MetasfreshCsvImportFormatBuilder stringColumn(@NonNull final String columnName)
		{
			return column(MetasfreshColumnCsvImportFormat.builder()
					.columnName(columnName)
					.dataType(DataType.STRING)
					.build());
		}

		public MetasfreshCsvImportFormatBuilder dateColumn(
				@NonNull final String columnName,
				@NonNull final DateTimeFormatter dateFormat)
		{
			return column(MetasfreshColumnCsvImportFormat.builder()
					.columnName(columnName)
					.dataType(DataType.DATE)
					.dateFormat(dateFormat)
					.build());
		}

		public MetasfreshCsvImportFormatBuilder numberColumn(@NonNull final String columnName)
		{
			return column(MetasfreshColumnCsvImportFormat.builder()
					.columnName(columnName)
					.dataType(DataType.NUMBER)
					.build());
		}
	}

}
