package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.posting.log.DocumentPostingLogRequestType;
import de.metas.acct.model.I_Document_Acct_Log;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Note;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Remembers, per document, how many posting requests ({@link I_Document_Acct_Log}) and notes ({@link I_AD_Note}) existed at some point,
 * so that a scenario can assert that a later action did NOT queue a posting and did not raise a posting error,
 * or (positive check) that a later action DID queue a new posting request.
 * Shared (per scenario) between the document step definitions.
 */
public class PostingQueueChecks
{
	/** The posting after-commit message is processed asynchronously: a negative check polls until the counts stayed unchanged for {@link #STABLE_MILLIS} (at most {@link #MAX_WAIT_MILLIS}). */
	private static final long POLL_MILLIS = 250;
	private static final long STABLE_MILLIS = 2000;
	private static final long MAX_WAIT_MILLIS = 15000;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final Map<TableRecordReference, Counts> remembered = new HashMap<>();

	@Value
	private static class Counts
	{
		int logs;
		/** only {@link DocumentPostingLogRequestType#Enqueued} entries (the PostingOK log is written after Posted=Y, so it is no new request) */
		int enqueuedLogs;
		int notes;
	}

	/** Remembers the current posting-log and note counts of the document; later asserts compare against them. */
	public void remember(@NonNull final TableRecordReference documentRef)
	{
		remembered.put(documentRef, currentCounts(documentRef));
	}

	/**
	 * Waits until the counts of the document stayed unchanged for {@link #STABLE_MILLIS} (at most {@link #MAX_WAIT_MILLIS}),
	 * then asserts that no {@link I_Document_Acct_Log} entry and no {@link I_AD_Note} was added since {@link #remember}.
	 *
	 * @throws AssertionError if {@link #remember} was not called for the document before, or something was added
	 */
	public void assertNothingNewSinceRemembered(@NonNull final TableRecordReference documentRef) throws InterruptedException
	{
		final Counts before = remembered.get(documentRef);
		assertThat(before).as("counts remembered for %s", documentRef).isNotNull();

		final Counts now = waitUntilStable(documentRef);
		assertThat(now.getLogs()).as("Document_Acct_Log entries of %s (enqueued / posting_ok / posting_error) since remembered", documentRef).isEqualTo(before.getLogs());
		assertThat(now.getNotes()).as("AD_Note entries of %s since remembered", documentRef).isEqualTo(before.getNotes());
	}

	/**
	 * Polls (at most {@link #MAX_WAIT_MILLIS}) until a posting request ({@link I_Document_Acct_Log} of type Enqueued) was added since {@link #remember}, then asserts it.
	 *
	 * @throws AssertionError if {@link #remember} was not called for the document before, or no new request appeared in time
	 */
	public void assertNewPostingRequestSinceRemembered(@NonNull final TableRecordReference documentRef) throws InterruptedException
	{
		final Counts before = remembered.get(documentRef);
		assertThat(before).as("counts remembered for %s", documentRef).isNotNull();

		try
		{
			StepDefUtil.tryAndWait(
					MAX_WAIT_MILLIS / 1000,
					POLL_MILLIS,
					() -> currentCounts(documentRef).getEnqueuedLogs() > before.getEnqueuedLogs());
		}
		catch (final AssertionError e)
		{
			// the wait timed out (StepDefUtil.tryAndWait fails with a generic message) - fall through to the assertion below, which names the document and the counts
		}
		final Counts current = currentCounts(documentRef);
		assertThat(current.getEnqueuedLogs()).as("Document_Acct_Log entries of type Enqueued of %s since remembered (a new posting request is expected; before: %s)", documentRef, before.getEnqueuedLogs()).isGreaterThan(before.getEnqueuedLogs());
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
		final int logs = countDocumentAcctLogs(documentRef, null);
		final int enqueuedLogs = countDocumentAcctLogs(documentRef, DocumentPostingLogRequestType.Enqueued);
		final int notes = queryBL.createQueryBuilderOutOfTrx(I_AD_Note.class)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Table_ID, documentRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Note.COLUMNNAME_Record_ID, documentRef.getRecord_ID())
				.create()
				.count();
		return new Counts(logs, enqueuedLogs, notes);
	}

	private int countDocumentAcctLogs(@NonNull final TableRecordReference documentRef, @Nullable final DocumentPostingLogRequestType type)
	{
		final IQueryBuilder<I_Document_Acct_Log> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_Document_Acct_Log.class)
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_AD_Table_ID, documentRef.getAD_Table_ID())
				.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_Record_ID, documentRef.getRecord_ID());
		if (type != null)
		{
			queryBuilder.addEqualsFilter(I_Document_Acct_Log.COLUMNNAME_Type, type.getCode());
		}
		return queryBuilder.create().count();
	}
}
