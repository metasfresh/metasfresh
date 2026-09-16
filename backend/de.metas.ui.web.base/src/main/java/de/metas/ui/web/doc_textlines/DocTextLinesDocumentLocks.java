package de.metas.ui.web.doc_textlines;

import com.google.common.util.concurrent.Striped;
import de.metas.doctextline.DocTextLineDocumentRef;
import lombok.NonNull;

import java.util.concurrent.locks.Lock;

/**
 * The in-process half of the serialisation every structural write of one document runs under, taken by every
 * {@link DocTextLinesView} of that document in this JVM. The half that actually decides the outcome is the
 * database row lock on the document's own record -- see {@link DocTextLinesRows#withDocumentLocked}, which
 * takes both -- because two browser tabs can be served by two application instances, and no in-process lock
 * is visible to the other one.
 * <p>
 * This one is kept in front of it because it covers something the row lock cannot: two rows holders in THIS
 * JVM publishing into their own in-memory {@code rowsById}/{@code rowIds}, which happens outside any
 * transaction and goes on living after the commit that ends the row lock. It also keeps a second local
 * writer from occupying a database connection just to wait on the row.
 * <p>
 * {@link DocTextLinesRows#structuralLock} is narrower still and does not overlap either: it serialises the
 * writes of ONE rows holder, i.e. of one open modal.
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
