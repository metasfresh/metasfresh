package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AD_Table_StepDef
{
	private static final Logger logger = LogManager.getLogger(AD_Table_StepDef.class);

	@And("validate all AD_Tables, except:")
	public void assertAllTablesAreValid(@NonNull final DataTable dataTable)
	{
		final TableNamesSkipList tableNamesSkipList = TableNamesSkipList.ofDataTable(dataTable);

		final Progress progress = new Progress();
		final SoftAssertions softly = new SoftAssertions();

		final POInfo.POInfoMap poInfoMap = POInfo.getPOInfoMap();
		poInfoMap.stream()
				.forEach(poInfo -> {
					final String tableName = poInfo.getTableName();

					if (tableNamesSkipList.isSkipped(tableName))
					{
						logger.info("{} is SKIPPED", tableName);
						return;
					}

					softly.assertThatCode(() -> tryRetrieveFirstRow(poInfo, progress))
							.as(() -> "Expect table " + tableName + " is valid")
							.doesNotThrowAnyException();
				});

		//
		// Hygiene: make sure the skip rules are still matching.
		// If not, advice the developer to remote the rule.
		tableNamesSkipList.forEachTableNameUC(tableNameUC -> {
			softly.assertThatCode(() -> {
						final POInfo poInfo = poInfoMap.getByTableName(tableNameUC);
						tryRetrieveFirstRow(poInfo, null); // expect failure
					})
					.as(() -> "Expected table " + tableNameUC + " is still invalid but it's OK. GREAT! Consider removing this exclude rule.")
					.isInstanceOf(Throwable.class);
		});

		progress.logSummary();

		softly.assertThat(progress.countErrors()).as("Expect no errors").isZero();
		softly.assertAll();
	}

	private static void tryRetrieveFirstRow(final @NonNull POInfo poInfo, @Nullable final Progress progress)
	{
		final String sql = getSqlSelectFirstRow(poInfo);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();

			if (progress != null)
			{
				progress.addOK(poInfo.getTableName());
			}
		}
		catch (SQLException ex)
		{
			final DBException dbException = new DBException(ex, sql);
			if (progress != null)
			{
				progress.addError(poInfo.getTableName(), dbException);
			}

			throw dbException;
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

		private TableNamesSkipList(@NonNull ImmutableSet<String> tableNamesUC)
		{
			this.tableNamesUC = tableNamesUC;
			System.out.println("tableNamesUC=" + this.tableNamesUC);
		}

		public static TableNamesSkipList ofDataTable(@NonNull final DataTable dataTable)
		{
			return new TableNamesSkipList(
					DataTableRows.of(dataTable)
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

		public void forEachTableNameUC(final Consumer<String> consumer) {tableNamesUC.forEach(consumer);}
	}

	private static class Progress
	{
		private int countAll = 0;
		private final LinkedHashMap<String, ErrorEntry> errors = new LinkedHashMap<>();

		public void addOK(@NonNull final String tableName)
		{
			countAll++;
			logger.info("{} is OK", tableName);
		}

		public void addError(@NonNull final String tableName, @Nullable final Exception ex)
		{
			countAll++;
			logger.warn("{} has errors", tableName, ex);
			errors.put(tableName, ErrorEntry.builder()
					.tableName(tableName)
					.error(ex != null ? ex.getLocalizedMessage() : "")
					.build());
		}

		public void logSummary()
		{
			final int countErrors = countErrors();
			logger.info("Checked all {} tables. Found {} are with errors.", countAll, countErrors);
			if (countErrors > 0)
			{
				logger.info("Tables with errors are: {}", errors.keySet());
				logger.info("To ignore them pls add following to your ignore table:\n"
						+ getErrorsAsCucumberTable()
				);
			}

		}

		private String getErrorsAsCucumberTable()
		{
			return errors.values().stream()
					.map(error -> " | " + error.getTableName() + " | " + error.getError() + " | ")
					.collect(Collectors.joining("\n"));
		}

		private int countErrors()
		{
			return errors.size();
		}
	}

	@Value
	@Builder
	private static class ErrorEntry
	{
		String tableName;
		String error;
	}
}
