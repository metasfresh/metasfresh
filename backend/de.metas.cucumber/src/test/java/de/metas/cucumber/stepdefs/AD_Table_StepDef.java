package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.assertj.core.api.Assertions;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class AD_Table_StepDef
{
	private static final Logger logger = LogManager.getLogger(AD_Table_StepDef.class);

	@And("validate all AD_Tables, except:")
	public void assertAllTablesAreValid(@NonNull final DataTable dataTable)
	{
		final TableNamesSkipList tableNamesSkipList = TableNamesSkipList.ofDataTable(dataTable);

		final AtomicInteger countOK = new AtomicInteger(0);
		final AtomicInteger countErrors = new AtomicInteger(0);

		POInfo.getPOInfoMap()
				.stream()
				.forEach(poInfo -> {
					if (tableNamesSkipList.isSkipped(poInfo.getTableName()))
					{
						logger.info("{} is SKIPPED", poInfo.getTableName());
						return;
					}

					try
					{
						tryRetrieveFirstRow(poInfo);
						logger.info("{} is OK", poInfo.getTableName());
						countOK.incrementAndGet();
					}
					catch (Exception ex)
					{
						logger.warn("{} has errors", poInfo.getTableName(), ex);
						countErrors.incrementAndGet();
					}
				});

		logger.info("Checked all tables. {} are OK, {} are with errors.", countOK, countErrors);

		Assertions.assertThat(countErrors.intValue()).as("AD_Tables with errors").isZero();
	}

	private static void tryRetrieveFirstRow(final @NonNull POInfo poInfo)
	{
		final String sql = getSqlSelectFirstRow(poInfo);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static String getSqlSelectFirstRow(final @NonNull POInfo poInfo)
	{
		final String tableName = poInfo.getTableName();

		final StringBuilder sqlSelectColumns = new StringBuilder();
		for (final POInfoColumn poInfoColumn : poInfo.getColumns())
		{
			if (!sqlSelectColumns.isEmpty())
			{
				sqlSelectColumns.append(",");
			}

			final String columnSql = ColumnSql.ofSql(poInfoColumn.getColumnSqlForSelect(), tableName).toSqlString();
			sqlSelectColumns.append(columnSql);
		}

		return "SELECT " + sqlSelectColumns + " FROM " + tableName + " LIMIT 1";
	}

	private static class TableNamesSkipList
	{
		@NonNull private final ImmutableSet<String> tableNamesUC;

		private TableNamesSkipList(@NonNull ImmutableSet<String> tableNamesUC) {this.tableNamesUC = tableNamesUC;}

		public static TableNamesSkipList ofDataTable(@NonNull final DataTable dataTable)
		{
			return new TableNamesSkipList(
					DataTableRow.toRows(dataTable)
							.stream()
							.map(row -> row.getAsString("TableName"))
							.map(String::toUpperCase)
							.collect(ImmutableSet.toImmutableSet())
			);
		}

		public boolean isSkipped(@NonNull final String tableName)
		{
			return tableNamesUC.contains(tableName.toUpperCase());
		}
	}
}
