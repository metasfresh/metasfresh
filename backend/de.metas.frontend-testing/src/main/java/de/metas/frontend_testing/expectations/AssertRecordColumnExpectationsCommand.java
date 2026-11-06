package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonRecordColumnExpectation;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

import java.util.Map;
import java.util.Objects;

import static de.metas.frontend_testing.expectations.assertions.Assertions.fail;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

/**
 * Asserts the stored value of a single column, pass/fail only.
 * Deliberately neither the stored nor the expected value is put into the failure message or context,
 * so it also works for secrets (e.g. passwords) which the UI does not show.
 * The key of each expectation is just a label chosen by the test.
 */
@Builder
class AssertRecordColumnExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonRecordColumnExpectation> expectations;

	void execute()
	{
		expectations.forEach(this::assertRecordColumn);
	}

	private void assertRecordColumn(@NonNull final String label, @NonNull final JsonRecordColumnExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("label", label);

			final String tableName = expectation.getTableName();
			final String columnName = expectation.getColumnName();
			final int recordId = expectation.getRecordId();
			final String what = tableName + "." + columnName + " of record " + recordId;

			// table, key and column names are validated against the application dictionary, so they are safe to put into the SQL
			final POInfo poInfo = POInfo.getPOInfo(tableName);
			if (poInfo == null)
			{
				throw new AdempiereException("Unknown table: " + tableName);
			}
			final String keyColumnName = poInfo.getKeyColumnName();
			if (keyColumnName == null)
			{
				throw new AdempiereException("Table has no single key column: " + tableName);
			}
			final int columnIndex = poInfo.getColumnIndex(columnName);
			if (columnIndex < 0 || poInfo.isVirtualColumn(columnIndex))
			{
				throw new AdempiereException("Unknown or virtual column: " + tableName + "." + columnName);
			}

			final String recordExistsSql = "SELECT COUNT(1) FROM " + poInfo.getTableName() + " WHERE " + keyColumnName + "=?";
			if (DB.getSQLValueEx(null, recordExistsSql, recordId) <= 0)
			{
				fail(what + ": record not found");
				return;
			}

			final String storedValue = DB.getSQLValueStringEx(null, "SELECT " + poInfo.getColumnName(columnIndex) + "::text FROM " + poInfo.getTableName() + " WHERE " + keyColumnName + "=?", recordId);
			if (!Objects.equals(storedValue, expectation.getExpectedValue()))
			{
				fail(what + ": stored value does not match expected");
			}
		});
	}
}
