package org.adempiere.ad.table.ddl;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;

import java.util.stream.Collectors;

@Value
@Builder
class TableDDL
{
	@NonNull String tableName;
	@NonNull ImmutableList<ColumnDDL> columns;

	public String getSQLCreate()
	{
		final StringBuilder sqlColumns = new StringBuilder();
		final StringBuilder sqlConstraints = new StringBuilder();
		boolean hasPK = false;
		boolean hasParents = false;
		for (final ColumnDDL column : columns)
		{
			final String colSQL = column.getSQLDDL();
			if (colSQL == null || Check.isBlank(colSQL))
			{
				continue; // virtual column
			}

			if (sqlColumns.length() > 0)
			{
				sqlColumns.append(", ");
			}
			sqlColumns.append(column.getSQLDDL());

			if (column.isPrimaryKey())
			{
				hasPK = true;
			}
			if (column.isParentLink())
			{
				hasParents = true;
			}

			final String constraint = column.getSQLConstraint();
			if (!Check.isBlank(constraint))
			{
				sqlConstraints.append(", ").append(constraint);
			}
		}

		final StringBuilder sql = new StringBuilder(MigrationScriptFileLoggerHolder.DDL_PREFIX + "CREATE TABLE ")
				.append("public.") // schema
				.append(getTableName())
				.append(" (")
				.append(sqlColumns);

		// Multi Column PK
		if (!hasPK && hasParents)
		{
			final String cols = columns.stream()
					.filter(ColumnDDL::isParentLink)
					.map(ColumnDDL::getColumnName)
					.collect(Collectors.joining(", "));

			sql.append(", CONSTRAINT ").append(getTableName()).append("_Key PRIMARY KEY (").append(cols).append(")");
		}

		sql.append(sqlConstraints).append(")");

		return sql.toString();
	}
}
