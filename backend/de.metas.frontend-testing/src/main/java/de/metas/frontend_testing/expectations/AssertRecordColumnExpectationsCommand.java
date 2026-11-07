package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonRecordColumnExpectation;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.POInfo;

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
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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

			final POInfo poInfo = POInfo.getPOInfo(tableName);
			if (poInfo == null)
			{
				fail(what + ": unknown table");
				return;
			}
			final String keyColumnName = poInfo.getKeyColumnName();
			if (keyColumnName == null)
			{
				fail(what + ": table has no single key column");
				return;
			}
			final int columnIndex = poInfo.getColumnIndex(columnName);
			if (columnIndex < 0 || poInfo.isVirtualColumn(columnIndex))
			{
				fail(what + ": unknown or virtual column");
				return;
			}

			final Object record = queryBL.createQueryBuilderOutOfTrx(poInfo.getTableName())
					.addEqualsFilter(keyColumnName, recordId)
					.create()
					.firstOnly(Object.class);
			if (record == null)
			{
				fail(what + ": record not found");
				return;
			}

			final String storedValue = InterfaceWrapperHelper.getPO(record).get_ValueAsString(poInfo.getColumnName(columnIndex));
			if (!Objects.equals(storedValue, expectation.getExpectedValue()))
			{
				fail(what + ": stored value does not match expected");
			}
		});
	}
}
