package de.metas.pos;

import de.metas.error.IErrorManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Failure capture of a background sweep that handles its records one by one ({@code K} identifies a record): a failure is
 * logged and recorded as an AD_Issue of the record, once per failure (same signature, see {@link #toSignature}) for as long
 * as the record keeps failing, so that a failure that repeats in every cycle is recorded once and the sweep goes on with
 * the next record. Thread-safe.
 */
public final class SweepFailureRecorder<K>
{
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final Logger logger;
	@NonNull private final String recordKind;

	/**
	 * Per record, the signatures of the sweep failures already recorded as AD_Issue; cleared when the record is swept
	 * without an error.
	 */
	private final Map<K, Set<String>> recordedFailureSignatures = new ConcurrentHashMap<>();

	/**
	 * @param logger     the sweep's logger
	 * @param recordKind what a record is, for the log (e.g. {@code card settlement})
	 */
	public SweepFailureRecorder(@NonNull final Logger logger, @NonNull final String recordKind)
	{
		this.logger = logger;
		this.recordKind = recordKind;
	}

	/**
	 * Forgets the records that are no longer swept.
	 */
	public void retainOnly(@NonNull final Collection<K> sweptKeys)
	{
		recordedFailureSignatures.keySet().retainAll(sweptKeys);
	}

	/**
	 * The record was swept without an error: a later failure is recorded again.
	 */
	public void cleared(@NonNull final K key)
	{
		recordedFailureSignatures.remove(key);
	}

	/**
	 * Logs the failure and records it once as AD_Issue of the given record. A failure whose cause chain is cyclic is
	 * replaced by a copy without causes (see {@link #toReportable}): the logger would fail on it with a
	 * {@link StackOverflowError}, and the AD_Issue writer would loop forever.
	 */
	public void onFailure(@NonNull final K key, @NonNull final Exception failure, @NonNull final TableRecordReference recordRef)
	{
		final Throwable rootCause = ExceptionUtils.getRootCause(failure); // cycle-safe: stops at the first repeated cause
		final Exception reportable = toReportable(failure, rootCause);

		logger.warn("Sweep of {} {} failed; continuing with the next one", recordKind, key, reportable);
		recordFailureOnce(key, toSignature(rootCause), reportable, recordRef);
	}

	/**
	 * @return the failure itself, or, if its cause chain is cyclic (the root cause still has a cause), a copy without
	 * causes: same text and stack trace, plus the root cause's text
	 */
	@NonNull
	private static Exception toReportable(@NonNull final Exception failure, @NonNull final Throwable rootCause)
	{
		if (rootCause.getCause() == null)
		{
			return failure;
		}

		final AdempiereException copy = new AdempiereException(failure + " (cyclic cause chain, root cause: " + rootCause + ")");
		copy.setStackTrace(failure.getStackTrace());
		return copy;
	}

	/**
	 * Records the failure as an AD_Issue of the record, unless the same failure (same signature) was already recorded
	 * since the record was last swept without an error.
	 */
	private void recordFailureOnce(
			@NonNull final K key,
			@NonNull final String signature,
			@NonNull final Exception failure,
			@NonNull final TableRecordReference recordRef)
	{
		final Set<String> signatures = recordedFailureSignatures.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet());
		if (!signatures.add(signature))
		{
			return;
		}

		try
		{
			errorManager.createIssue(AdempiereException.wrapIfNeeded(failure).setRecord(recordRef));
		}
		catch (final Exception issueEx)
		{
			// e.g. the DB is down: the next cycle tries again
			signatures.remove(signature);
			logger.warn("Could not record the sweep failure of {} {} as AD_Issue", recordKind, key, issueEx);
		}
	}

	/**
	 * @return the root cause's class and the place it was thrown ({@code class#method:line}); not the message, which can
	 * vary (ids, timestamps) for the same failure
	 */
	@NonNull
	private static String toSignature(@NonNull final Throwable rootCause)
	{
		final StackTraceElement[] stackTrace = rootCause.getStackTrace();
		final String place = stackTrace.length > 0
				? stackTrace[0].getClassName() + "#" + stackTrace[0].getMethodName() + ":" + stackTrace[0].getLineNumber()
				: "?";
		return rootCause.getClass().getName() + "@" + place;
	}
}
