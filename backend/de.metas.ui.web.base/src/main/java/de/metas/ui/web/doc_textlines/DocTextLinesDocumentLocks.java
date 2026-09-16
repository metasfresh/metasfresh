package de.metas.ui.web.doc_textlines;

import com.google.common.util.concurrent.Striped;
import de.metas.doctextline.DocTextLineDocumentRef;
import lombok.NonNull;

import java.util.concurrent.locks.Lock;

/**
 * The lock every structural write of one document takes, whichever {@link DocTextLinesView} it comes from.
 * <p>
 * {@link DocTextLinesRows#structuralLock} serialises the writes of ONE rows holder, i.e. of one open modal.
 * That is not enough: every launcher click mints a new view with its own rows holder, so one user with a
 * second browser tab has two of them over the same document, each computing positions against the order as
 * it stood when its own modal was opened. Re-deriving that order from the database inside the write is what
 * removes the resulting duplicate positions -- and re-deriving is only sound if no other write of the same
 * document can slip between one writer's derivation and its persist. This lock is that guarantee, and it is
 * held for the whole derive/compute/persist/publish sequence.
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
