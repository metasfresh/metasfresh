package de.metas.ui.web.doc_textlines;

import com.google.common.util.concurrent.Striped;
import de.metas.doctextline.DocTextLineDocumentRef;
import lombok.NonNull;

import java.util.concurrent.locks.Lock;

/**
 * An in-process fast path in front of the lock that actually serialises a structural write, which is the
 * document's generic {@code ILockManager} lock -- see {@link DocTextLinesRows#withDocumentLocked}, which takes
 * both. That one decides the outcome, because two browser tabs can be served by two application instances and
 * no in-process lock is visible to the other one.
 * <p>
 * This one carries no part of that guarantee. It saves the second writer IN THIS JVM from opening a
 * transaction and being refused for a lock a sibling thread is about to release; it waits on a monitor
 * instead. It is also the only serialisation the unit-test harness has, since that harness has no database, so
 * it is what lets an in-JVM concurrency test of these writes mean anything.
 * <p>
 * {@link DocTextLinesRows#structuralLock} is narrower still and nests inside both: it serialises the writes of
 * ONE rows holder, i.e. of one open modal.
 * <p>
 * Striped rather than one lock per document: a map keyed by document would grow for the lifetime of the JVM
 * with an entry per document anyone ever edited. A fixed set of stripes costs nothing and cannot leak; the
 * price is that two unrelated documents can share a stripe and briefly wait for each other, which is
 * invisible next to the round trips the lock already covers.
 */
final class DocTextLinesDocumentLocks
{
	private static final int STRIPES = 64;

	private static final Striped<Lock> LOCKS = Striped.lock(STRIPES);

	static Lock forDocument(@NonNull final DocTextLineDocumentRef documentRef)
	{
		return LOCKS.get(documentRef);
	}

	private DocTextLinesDocumentLocks()
	{
	}
}
