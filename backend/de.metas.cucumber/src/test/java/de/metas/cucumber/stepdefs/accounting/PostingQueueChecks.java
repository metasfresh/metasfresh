package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.model.I_Document_Acct_Log;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Note;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Remembers, per document, how many posting requests ({@link I_Document_Acct_Log}) and notes ({@link I_AD_Note}) existed at some point,
 * so that a scenario can assert that a later action did NOT queue a posting and did not raise a posting error.
 * Shared (per scenario) between the document step definitions.
 */
public class PostingQueueChecks
{
	/** The posting after-commit message is sent asynchronously; give it time to show up before asserting that nothing happened. */
	private static final long WAIT_BEFORE_NEGATIVE_CHECK_MILLIS = 5000;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final Map<String, Counts> remembered = new HashMap<>();

	@Value
	private static class Counts
	{
		int logs;
		int notes;
	}

	public void remember(@NonNull final Class<?> tableModelClass, final int recordId)
	{
		remembered.put(key(tableModelClass, recordId), currentCounts(tableModelClass, recordId));
	}

	public void assertNothingNewSinceRemembered(@NonNull final Class<?> tableModelClass, final int recordId) throws InterruptedException
	{
		final Counts before = remembered.get(key(tableModelClass, recordId));
		assertThat(before).as("counts remembered for %s %s", tableModelClass.getSimpleName(), recordId).isNotNull();

		Thread.sleep(WAIT_BEFORE_NEGATIVE_CHECK_MILLIS);

		final Counts now = currentCounts(tableModelClass, recordId);
		assertThat(now.getLogs()).as("Document_Acct_Log entries of %s %s (enqueued / posting_ok / posting_error) since remembered", tableModelClass.getSimpleName(), recordId).isEqualTo(before.getLogs());
		assertThat(now.getNotes()).as("AD_Note entries of %s %s since remembered", tableModelClass.getSimpleName(), recordId).isEqualTo(before.getNotes());
	}

	private Counts currentCounts(@NonNull final Class<?> tableModelClass, final int recordId)
	{
		final int tableId = InterfaceWrapperHelper.getTableId(tableModelClass);
		final int logs = queryBL.createQueryBuilderOutOfTrx(I_Document_Acct_Log.class)
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_Record_ID, recordId)
				.create()
				.count();
		final int notes = queryBL.createQueryBuilderOutOfTrx(I_AD_Note.class)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_Record_ID, recordId)
				.create()
				.count();
		return new Counts(logs, notes);
	}

	private static String key(final Class<?> tableModelClass, final int recordId) {return tableModelClass.getSimpleName() + "#" + recordId;}
}
