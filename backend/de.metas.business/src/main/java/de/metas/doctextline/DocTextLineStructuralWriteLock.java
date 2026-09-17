package de.metas.doctextline;

import com.google.common.collect.ImmutableList;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

/**
 * Serialises the structural writes -- insert-above, move, delete -- of one document's text lines, across
 * application instances: text lines share one numeric ordering space with the document's article lines,
 * nothing in the schema rejects a duplicate position, and two writers reading the same neighbours compute the
 * same midpoint.
 * <p>
 * This is the generic {@link ILockManager} lock on the document's own record -- deliberately not a mechanism
 * of this feature's own. It is a TRY-lock ({@link ILockCommand#setFailIfAlreadyLocked(boolean)}): a writer
 * that meets the document held is refused with a message it can show the user, rather than left waiting
 * with no timeout and no feedback.
 * <p>
 * Released when the acquiring transaction ends -- committed or rolled back -- and never before. {@code T_Lock}
 * rows are written outside that transaction, so releasing in a plain {@code finally} would free the document
 * while this writer's own row is still uncommitted, and the next writer would compute its midpoint from the
 * neighbours as they stood BEFORE the commit: exactly the collision this lock exists to prevent.
 * <p>
 * It follows that this is ONE acquisition per transaction, not a re-entrant lock: the owner is new each time,
 * so a second acquisition in the same transaction meets its own predecessor and is refused. Each quick action
 * performs exactly one structural write, which is what makes that safe.
 */
public final class DocTextLineStructuralWriteLock
{
	private static final String LOCK_OWNER_NAME_PREFIX = DocTextLineStructuralWriteLock.class.getSimpleName();

	/**
	 * Locks {@code documentRef}'s own record for the rest of the caller's transaction.
	 *
	 * @throws AdempiereException addressed to the user, if another writer holds the document right now.
	 */
	public static void acquireUntilTransactionEnds(@NonNull final DocTextLineDocumentRef documentRef)
	{
		final TableRecordReference documentRecord = documentRef.toRecordRef();

		final ILock lock;
		try
		{
			lock = Services.get(ILockManager.class)
					.lock()
					// a fresh owner per acquisition: ILock#unlockAll releases BY OWNER, so a shared owner name
					// would let one document's release free another document's lock
					.setOwner(LockOwner.newOwner(LOCK_OWNER_NAME_PREFIX))
					.setFailIfAlreadyLocked(true)
					.setAutoCleanup(true) // so a lock left behind by a crashed instance does not block the document forever
					.addRecord(documentRecord)
					.acquire();
		}
		catch (final LockFailedException e)
		{
			throw documentIsBeingEditedRightNow(documentRecord, e);
		}

		// registers the release with the transaction; see the class javadoc for why it must not happen earlier
		lock.asAutocloseableOnTrxClose(ITrx.TRXNAME_ThreadInherited).close();
	}

	/**
	 * Refusal for a structural write that met the document held by another writer. It resolves itself -- the
	 * other writer's transaction ends in milliseconds -- so the message asks for a retry rather than for the
	 * window to be reopened. The holder's identity is a generated lock-owner name, which tells the user
	 * nothing they can act on, so it stays a parameter for the log.
	 */
	private static AdempiereException documentIsBeingEditedRightNow(
			@NonNull final TableRecordReference documentRecord,
			@NonNull final LockFailedException cause)
	{
		@Nullable final ImmutableList<ExistingLockInfo> existingLocks = cause.getExistingLocks();

		return new AdempiereException("This document's text lines are being edited right now."
				+ " Please try again in a moment.", cause)
				.appendParametersToMessage()
				.setParameter("document", documentRecord)
				.setParameter("existingLocks", existingLocks);
	}

	private DocTextLineStructuralWriteLock()
	{
	}
}
