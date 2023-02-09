package org.adempiere.ad.table.api;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

@Value
public class ColumnNameFQ
{
	@NonNull TableName tableName;
	@NonNull ColumnName columnName;

	public static ColumnNameFQ ofTableAndColumnName(@NonNull final String tableName, @NonNull final String columnName)
	{
		return new ColumnNameFQ(TableName.ofString(tableName), ColumnName.ofString(columnName));
	}

	public static ColumnNameFQ ofTableAndColumnName(@NonNull final TableName tableName, @NonNull final String columnName)
	{
		return new ColumnNameFQ(tableName, ColumnName.ofString(columnName));
	}

	public static ColumnNameFQ ofTableAndColumnName(@NonNull final TableName tableName, @NonNull final ColumnName columnName)
	{
		return new ColumnNameFQ(tableName, columnName);
	}

	private ColumnNameFQ(@NonNull final TableName tableName, @NonNull final ColumnName columnName)
	{
		this.tableName = tableName;
		this.columnName = columnName;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return tableName.getAsString() + "." + columnName.getAsString();
	}

	public static TableName extractSingleTableName(final ColumnNameFQ... columnNames)
	{
		if (columnNames == null || columnNames.length == 0)
		{
			throw new AdempiereException("Cannot extract table name from null/empty column names array");
		}

		TableName singleTableName = null;
		for (final ColumnNameFQ columnNameFQ : columnNames)
		{
			if (columnNameFQ == null)
			{
				continue;
			}
			else if (singleTableName == null)
			{
				singleTableName = columnNameFQ.getTableName();
			}
			else if (!TableName.equals(singleTableName, columnNameFQ.getTableName()))
			{
				throw new AdempiereException("More than one table name found in " + Arrays.asList(columnNames));
			}
		}

		if (singleTableName == null)
		{
			throw new AdempiereException("Cannot extract table name from null/empty column names array: " + Arrays.asList(columnNames));
		}

		return singleTableName;
	}
}
