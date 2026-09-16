package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;

/**
 * The two things a structural write of {@link DocTextLinesRows} needs from the database about the document it
 * is writing to: the document serialised against every other writer, and its merged order as it stands right
 * now. They belong together because neither is worth anything without the other -- an unserialised
 * re-derivation can be overtaken between the read and the write, and a lock without a re-derivation still
 * computes against a stale picture.
 * <p>
 * Implemented by {@link DocTextLinesRowsLoader}, which is already the one class that talks to the order's own
 * DAO and owns the merge. Keeping both behind this interface is what lets the rows holder stay out of the
 * article-line tables, which are not its to query.
 */
interface DocTextLinesDocumentAccess
{
	/**
	 * Locks the document's own record until the current transaction ends, so that a writer in another
	 * application instance -- which an in-process lock cannot see at all -- waits rather than computing a
	 * position against the same state.
	 */
	void lockDocumentForUpdate();

	/** The document's merged article-line/text-line rows, read fresh and in merged order. */
	ImmutableList<DocTextLinesRow> loadMergedRows();
}
