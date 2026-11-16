package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.model.I_Document_Acct_Log;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Note;

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
	/** The posting after-commit message is processed asynchronously: a negative check polls until the counts stayed unchanged for {@link #STABLE_MILLIS} (at most {@link #MAX_WAIT_MILLIS}). */
	private static final long POLL_MILLIS = 250;
	private static final long STABLE_MILLIS = 2000;
	private static final long MAX_WAIT_MILLIS = 15000;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final Map<TableRecordReference, Counts> remembered = new HashMap<>();

	@Value
	private static class Counts
	{
		int logs;
		int notes;
	}

	public void remember(@NonNull final TableRecordReference documentRef)
	{
		remembered.put(documentRef, currentCounts(documentRef));
	}

	public void assertNothingNewSinceRemembered(@NonNull final TableRecordReference documentRef) throws InterruptedException
	{
		final Counts before = remembered.get(documentRef);
		assertThat(before).as("counts remembered for %s", documentRef).isNotNull();

		final Counts now = waitUntilStable(documentRef);
		assertThat(now.getLogs()).as("Document_Acct_Log entries of %s (enqueued / posting_ok / posting_error) since remembered", documentRef).isEqualTo(before.getLogs());
		assertThat(now.getNotes()).as("AD_Note entries of %s since remembered", documentRef).isEqualTo(before.getNotes());
	}

	/** Polls (at most {@link #MAX_WAIT_MILLIS}) until a posting request ({@link I_Document_Acct_Log}) was added since remembered, then asserts it. */
	public void assertNewPostingRequestSinceRemembered(@NonNull final TableRecordReference documentRef) throws InterruptedException
	{
		final Counts before = remembered.get(documentRef);
		assertThat(before).as("counts remembered for %s", documentRef).isNotNull();

		final long start = System.currentTimeMillis();
		Counts current = currentCounts(documentRef);
		while (current.getLogs() <= before.getLogs() && System.currentTimeMillis() - start < MAX_WAIT_MILLIS)
		{
			Thread.sleep(POLL_MILLIS);
			current = currentCounts(documentRef);
		}
		assertThat(current.getLogs()).as("Document_Acct_Log entries of %s since remembered (a new posting request is expected)", documentRef).isGreaterThan(before.getLogs());
	}

	private Counts waitUntilStable(@NonNull final TableRecordReference documentRef) throws InterruptedException
	{
		final long start = System.currentTimeMillis();
		Counts last = currentCounts(documentRef);
		long lastChange = start;
		while (true)
		{
			Thread.sleep(POLL_MILLIS);
			final long now = System.currentTimeMillis();
			final Counts current = currentCounts(documentRef);
			if (!current.equals(last))
			{
				last = current;
				lastChange = now;
			}
			if (now - lastChange >= STABLE_MILLIS || now - start >= MAX_WAIT_MILLIS)
			{
				return current;
			}
		}
	}

	private Counts currentCounts(@NonNull final TableRecordReference documentRef)
	{
		final int logs = queryBL.createQueryBuilderOutOfTrx(I_Document_Acct_Log.class)
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_AD_Table_ID, documentRef.getAD_Table_ID())
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_Record_ID, documentRef.getRecord_ID())
				.create()
				.count();
		final int notes = queryBL.createQueryBuilderOutOfTrx(I_AD_Note.class)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Table_ID, documentRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Note.COLUMNNAME_Record_ID, documentRef.getRecord_ID())
				.create()
				.count();
		return new Counts(logs, notes);
	}
}
