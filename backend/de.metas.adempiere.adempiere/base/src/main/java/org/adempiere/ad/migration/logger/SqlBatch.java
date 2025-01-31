package org.adempiere.ad.migration.logger;

import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class SqlBatch
{
	@Getter private final String sqlCommand;
	private final ArrayList<SqlParams> rows = new ArrayList<>();

	private SqlBatch(final String sqlCommand)
	{
		this.sqlCommand = sqlCommand;
	}

	public static SqlBatch ofSql(@NonNull final String sqlCommand)
	{
		return new SqlBatch(sqlCommand);
	}

	public void addRow(@Nullable final Map<Integer, Object> row)
	{
		addRow(SqlParams.ofMap(row));
	}

	public void addRow(@NonNull SqlParams row)
	{
		rows.add(row);
	}

	public Stream<Sql> streamSqls() {return rows.stream().map(this::toSql);}

	private Sql toSql(final SqlParams sqlParams)
	{
		return Sql.ofSql(sqlCommand, sqlParams);
	}

}
