package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.InsertAboveRequest;
import de.metas.doctextline.TextLineScope;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Holder of the merged article-line/text-line rows of one {@link DocTextLinesView}. Row order starts as the
 * merged order {@link DocTextLinesRowsLoader} computed, preserved via {@link #rowIds}' order, and is widened
 * by {@link #insertRowAbove} as rows are added.
 * <p>
 * One holder is one open modal, and there can be several over the same document at once -- in this process or
 * in another one. So every write that touches the ORDER -- {@link #insertRowAbove}, {@link #moveRow} -- first
 * re-derives that order from the database ({@link #refreshMergedOrderFromDatabase}) and computes against what
 * it finds, with all writes of one document serialised on a row lock on the document's own record (see
 * {@link #withDocumentLocked}). A holder's own snapshot is never a safe basis for placing a row: it is taken
 * when the view is built and nothing else refreshes it.
 * <p>
 * Editable ({@link IEditableRowsData}): patching a text row's text/scope persists immediately, in keeping
 * with this WebUI's field-level auto-save behaviour, rather than deferring to a view-close batch the way
 * {@code shipment_candidates_editor} does for its heavier shipment-schedule business logic -- there is no
 * equivalent batching need here, {@code C_Doc_TextLine.TextLine}/{@code TextLineScope} are plain fields.
 * Article rows stay read-only: {@link DocTextLinesRow#withChanges} rejects the patch before anything is written.
 */
final class DocTextLinesRows implements IEditableRowsData<DocTextLinesRow>
{
	static DocTextLinesRows cast(final IRowsData<DocTextLinesRow> rowsData)
	{
		return (DocTextLinesRows)rowsData;
	}

	/**
	 * The merged order. Replaced wholesale, never mutated in place: every write goes through
	 * {@link #updateRowIds} while {@link #structuralLock} is held, and the readers that walk it hold no lock
	 * at all, so a reader must always see one whole ordering rather than the halves of two.
	 */
	private volatile ImmutableList<DocumentId> rowIds;

	private final ConcurrentHashMap<DocumentId, DocTextLinesRow> rowsById;
	private final ConcurrentHashMap<DocumentId, Object> rowLocksById; // one dedicated monitor per row, see #patchRow
	private final DocTextLineRepository docTextLineRepository;

	/** The document whose rows these are -- the key every structural write locks on, see {@link DocTextLinesDocumentLocks}. */
	private final DocTextLineDocumentRef documentRef;

	/** Locks the document and re-runs {@link DocTextLinesRowsLoader}'s merge -- see {@link #withDocumentLocked} and {@link #refreshMergedOrderFromDatabase}. */
	private final DocTextLinesDocumentAccess documentAccess;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Serialises the writes that touch the ordering OF THIS HOLDER -- two concurrent requests into one open
	 * modal must not corrupt {@link #rowIds}' index arithmetic. It is held inside the document's row lock, not
	 * instead of it: writes coming from a DIFFERENT holder of the same document are a different problem, and
	 * the row lock {@link #withDocumentLocked} takes is what answers that one.
	 */
	private final Object structuralLock = new Object();

	@Builder
	private DocTextLinesRows(
			@NonNull final List<DocTextLinesRow> rows,
			@NonNull final DocTextLineDocumentRef documentRef,
			@NonNull final DocTextLineRepository docTextLineRepository,
			@NonNull final DocTextLinesDocumentAccess documentAccess)
	{
		// empty is legal here (unlike the shipment-candidates-editor precedent): an order with no lines at
		// all still opens the modal, just with zero rows.
		rowIds = rows.stream()
				.map(DocTextLinesRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = new ConcurrentHashMap<>(Maps.uniqueIndex(rows, DocTextLinesRow::getId));
		rowLocksById = new ConcurrentHashMap<>(rowIds.stream().collect(ImmutableMap.toImmutableMap(id -> id, id -> new Object())));
		this.documentRef = documentRef;
		this.docTextLineRepository = docTextLineRepository;
		this.documentAccess = documentAccess;
	}

	/**
	 * Replaces {@link #rowIds} with the result of applying {@code mutation} to a copy of it. Callers must hold
	 * {@link #structuralLock}: the copy-mutate-publish sequence is only atomic with respect to other writers
	 * because they are serialised there. Readers need no lock -- they see either the old ordering or the new
	 * one, never a half-applied mutation.
	 */
	private void updateRowIds(@NonNull final Consumer<List<DocumentId>> mutation)
	{
		final List<DocumentId> updatedRowIds = new ArrayList<>(rowIds);
		mutation.accept(updatedRowIds);
		rowIds = ImmutableList.copyOf(updatedRowIds);
	}

	@Override
	public Map<DocumentId, DocTextLinesRow> getDocumentId2TopLevelRows()
	{
		final ImmutableMap.Builder<DocumentId, DocTextLinesRow> result = ImmutableMap.builder();
		rowIds.forEach(rowId -> resolveRow(rowId).ifPresent(row -> result.put(rowId, row)));
		return result.build();
	}

	/**
	 * Resolves {@code rowId} through {@link #rowsById}, returning empty rather than {@code null} when the id is
	 * not there. Every reader that walks {@link #rowIds} and looks up a row it did NOT receive directly from its
	 * own caller -- a neighbour, a "beyond" bound, a row scanned in a sublist -- must go through this method
	 * rather than {@code rowsById.get(...)} directly, so a vanished row is a value every such caller is forced
	 * to handle rather than an uncontrolled {@code NullPointerException} that happens to be avoided only by
	 * luck of call order. What the right handling is depends on the question being asked, and there are only
	 * two answers: skip the row where it provably cannot affect the outcome, or refuse the operation. Reading
	 * it as "there is no row there" is never one of them -- an absent row and an unreadable one mean opposite
	 * things to the position arithmetic, which is what {@link #boundPositionAt} exists to keep apart. (A
	 * row a caller received directly -- e.g. {@link #patchRow}'s own target, or {@link #moveRow}'s/
	 * {@link #deleteRow}'s own selected row -- is a different case: its own not-found path already throws
	 * {@link EntityNotFoundException} deliberately, via {@link #getRowOrThrow}/{@link #getTextRowOrThrow}, and
	 * that is unrelated to this method.)
	 * <p>
	 * <b>The one sanctioned cause of a miss is {@link #deleteRow}'s own two-section shape.</b> Its row-lock
	 * section clears {@link #rowsById} only after a successful persist, and its separate {@link #structuralLock}
	 * section trims {@link #rowIds} afterwards; between the two, {@link #structuralLock} is briefly free, so
	 * another thread's {@link #moveRow}, {@link #insertRowAbove} (via {@link #computeInsertAbovePositions}), or
	 * {@link #getDocumentId2TopLevelRows} can observe an id still in {@link #rowIds} with no matching
	 * {@link #rowsById} entry. That combination is the ONLY legitimate reason this method ever returns empty.
	 * It is not a general licence to treat a missing row as unremarkable -- any other cause would be a genuine
	 * bug in this class, and a caller silently swallowing it here would only hide that bug rather than fix it.
	 */
	private Optional<DocTextLinesRow> resolveRow(@NonNull final DocumentId rowId)
	{
		return Optional.ofNullable(rowsById.get(rowId));
	}

	/**
	 * The position of the row at {@code index} in the merged order, for use as a bound of the position
	 * arithmetic. Two very different situations can stop an index yielding one, and conflating them is what
	 * turns a momentary inconsistency into a permanently wrong stored position:
	 * <ul>
	 * <li><b>There is no row at that index at all</b> -- {@code index} is past the start or the end of
	 * {@link #rowIds}. Then there genuinely is no bound on that side, {@code null} says exactly that, and the
	 * arithmetic is free to place the row past everything in that direction.</li>
	 * <li><b>A row is listed at that index but is already gone from {@link #rowsById}</b> -- the moment
	 * {@link #resolveRow} describes. The bound exists; only its value is unreadable from here. Reporting it as
	 * "no bound" would silently compute against the WRONG row -- past the unreadable one, towards whatever
	 * lies further out -- and the collision guard in
	 * {@link DocTextLineRepository#computeInsertAbovePosition} compares its result only against the two bounds
	 * it was handed, so it cannot see the row that result would actually duplicate. The outcome is a duplicate
	 * position, or short of exact equality an inverted order, with nothing anywhere reporting it. So the
	 * operation is refused instead. That state resolves itself within the same request that caused it, so a
	 * refusal the user can simply repeat costs a retry; a guessed position costs a corrupted document.</li>
	 * </ul>
	 * Walking further out to the next readable row is deliberately NOT done. It would be sound only while the
	 * unreadable row's own database row is already gone, which is true of one specific ordering inside
	 * {@link #deleteRow} and of nothing else -- an invariant of another method, silently load-bearing here,
	 * whose failure mode is precisely the duplicate position this class already guards against everywhere
	 * else. Refusing is correct whatever made the row unreadable.
	 */
	@Nullable
	private BigDecimal boundPositionAt(final int index)
	{
		// one read of the volatile field: "is there a row there" and "which row is there" must be answered
		// about the same ordering, or a concurrent structural write between the two would turn a shrinking
		// list into an IndexOutOfBoundsException
		final List<DocumentId> currentRowIds = rowIds;
		if (index < 0 || index >= currentRowIds.size())
		{
			return null;
		}

		final DocumentId boundRowId = currentRowIds.get(index);
		return resolveRow(boundRowId)
				.map(DocTextLinesRow::getLine)
				.orElseThrow(() -> rowIsBeingRemoved(boundRowId));
	}

	/**
	 * Refusal for an operation that would have to compute a position against a row which is in the middle of
	 * being removed. Phrased for the user who clicked the quick action: the situation is transient and a
	 * repeat of the same action succeeds.
	 */
	private static AdempiereException rowIsBeingRemoved(@NonNull final DocumentId rowId)
	{
		return new AdempiereException("A neighbouring row is being removed right now, so this row cannot be placed safely. Please try again.")
				.appendParametersToMessage()
				.setParameter("rowId", rowId);
	}

	/**
	 * Refusal for an operation whose own selected row is no longer part of the document -- somebody deleted it
	 * while this window was open. Unlike {@link #rowIsBeingRemoved} this does not resolve itself: there is
	 * nothing to retry against, so the message asks for the window to be reopened and a line chosen again.
	 */
	private static AdempiereException rowNoLongerExists(@NonNull final DocumentId rowId)
	{
		return new AdempiereException("The line you selected is not part of this document any more -- it was removed while this window was open."
				+ " Please close and reopen the window, then select a line again.")
				.appendParametersToMessage()
				.setParameter("rowId", rowId);
	}

	/**
	 * Refusal for an insert-above on a window that was opened on a document with no lines at all, while the
	 * document has lines by now. No reference row is ever sent in that case, so there is nothing to place the
	 * new line above, and placing it "first" against lines this window has never shown is exactly the guess
	 * this class refuses to make.
	 */
	private static AdempiereException documentGainedRowsSinceWindowOpened(@NonNull final List<DocumentId> rowIds)
	{
		return new AdempiereException("This document has gained lines since this window was opened, so there is nothing here to insert above."
				+ " Please close and reopen the window, then select a line again.")
				.appendParametersToMessage()
				.setParameter("rowIds", rowIds);
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
		// nothing: rows are loaded once, fresh, whenever the view is (re-)created
	}

	/**
	 * Given the row a caller selected to insert above, derives the three values {@code InsertAboveRequest}
	 * needs -- this is the merged ordering's own data ({@link #rowIds}), so the derivation lives here rather
	 * than being hand-rolled by each caller.
	 * <p>
	 * Refuses rather than answers when the row it would have to read as the lower bound is in the middle of
	 * being removed -- see {@link #boundPositionAt} for why a guessed bound is the worse of the two outcomes.
	 * <p>
	 * It answers about THIS view's ordering, which is only as current as the last
	 * {@link #refreshMergedOrderFromDatabase} -- that is why {@link #insertRowAbove} runs one immediately
	 * before calling this, and why an answer taken from here on its own must not be turned into a stored
	 * position by anyone else.
	 *
	 * @param referenceRowId the selected row; {@code null} only when the document has no rows at all.
	 */
	InsertAbovePositions computeInsertAbovePositions(@Nullable final DocumentId referenceRowId)
	{
		final List<DocumentId> currentRowIds = rowIds; // one read, see boundPositionAt

		if (referenceRowId == null)
		{
			if (!currentRowIds.isEmpty())
			{
				throw documentGainedRowsSinceWindowOpened(currentRowIds);
			}
			return InsertAbovePositions.EMPTY_DOCUMENT;
		}

		// the reference row was explicitly selected by the caller. If it is gone -- deleted in another window,
		// which the re-derivation has just established -- then the user's whole reference point is gone, and
		// no position can stand in for it. Asked BEFORE the index lookup so that the answer the user gets is
		// the one that tells them what happened, rather than a bare not-found on an internal row id.
		final BigDecimal referencePosition = resolveRow(referenceRowId)
				.map(DocTextLinesRow::getLine)
				.orElseThrow(() -> rowNoLongerExists(referenceRowId));

		final int referenceIndex = indexOfOrThrow(currentRowIds, referenceRowId);

		// index -1 means the reference row is the first of the merged order, so there genuinely is no previous
		// row and the midpoint arithmetic is unbounded below; a row listed there but unreadable is refused
		// instead of being reported as absent -- see boundPositionAt
		final BigDecimal previousPosition = boundPositionAt(referenceIndex - 1);

		// this scan collects every ARTICLE row's position ahead of the insert position. An unreadable row here
		// is skipped rather than refused, and that is safe for a reason specific to this question rather than a
		// general tolerance: only text rows are ever removed (deleteRow rejects an article row outright), and
		// a text row answers this question neither way, so a skipped one cannot change the outcome
		final List<BigDecimal> articleLinePositionsBeforeReference = currentRowIds.subList(0, referenceIndex).stream()
				.map(this::resolveRow)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.filter(DocTextLinesRow::isArticleLine)
				.map(DocTextLinesRow::getLine)
				.collect(ImmutableList.toImmutableList());

		// shared with any other caller that has already resolved the merged order -- see
		// DocTextLineRepository#articleLineExistsBefore's javadoc for why this lives in the business layer
		// rather than being re-derived here
		final boolean articleLineExistsBeforeReferencePosition =
				DocTextLineRepository.articleLineExistsBefore(articleLinePositionsBeforeReference, referencePosition);

		return InsertAbovePositions.builder()
				.referencePosition(referencePosition)
				.previousPosition(previousPosition)
				.articleLineExistsBeforeReferencePosition(articleLineExistsBeforeReferencePosition)
				.build();
	}

	/**
	 * Runs {@code action} as the only structural write of this document anywhere, in one transaction.
	 * <p>
	 * The serialising primitive is the <b>database</b> row lock on the document's own record
	 * ({@link DocTextLinesDocumentAccess#lockDocumentForUpdate}), because the writers to keep apart are not
	 * only the ones in this JVM: a second webapi instance serving the second browser tab would not see an
	 * in-process lock at all, and would re-derive the same ordering and persist into it. The lock is taken
	 * inside {@code callInThreadInheritedTrx} and released when that transaction ends, which is why the
	 * action's own reads and writes must run in the same transaction -- they do: the quick action's
	 * {@code doIt} is already wrapped in one by {@code ProcessExecutor}, and this call joins it (or opens one
	 * for a caller that has none, rather than letting the lock be released the moment it is taken).
	 * <p>
	 * The in-process lock in front of it is NOT part of that guarantee and must not be read as one: everything
	 * this method's action does -- including publishing into {@link #rowsById}/{@link #rowIds} -- happens
	 * inside the transaction while the row lock is held, so the row lock alone already serialises two rows
	 * holders of this JVM exactly as it serialises two application instances. What the in-process lock buys is
	 * cheaper waiting: a second local writer waits here instead of opening a transaction, taking a pooled
	 * database connection and then sitting idle in a lock wait inside PostgreSQL. It is also the only
	 * serialisation available to the unit-test harness, which has no database at all, and so is what makes an
	 * in-JVM concurrency test of this method mean anything.
	 * <p>
	 * Lock order is fixed -- in-process lock, then the transaction, then the row lock, then
	 * {@link #structuralLock} (or, in {@link #deleteRow}, a row monitor and then {@link #structuralLock}) --
	 * and nothing acquires an outer one while holding an inner one, which is what makes holding several safe
	 * here.
	 */
	private <T> T withDocumentLocked(@NonNull final Supplier<T> action)
	{
		final Lock inProcessLock = DocTextLinesDocumentLocks.forDocument(documentRef);
		inProcessLock.lock();
		try
		{
			return trxManager.callInThreadInheritedTrx(() -> {
				documentAccess.lockDocumentForUpdate();
				return action.get();
			});
		}
		finally
		{
			inProcessLock.unlock();
		}
	}

	/**
	 * Re-derives the document's merged order from the database and folds it into this view's own, so that the
	 * position arithmetic that follows computes against the order that ACTUALLY exists rather than the one
	 * this view was created with. Called at the top of each structural write, with this document's lock and
	 * {@link #structuralLock} both held.
	 * <p>
	 * This is what a second open modal needs. A rows holder's ordering is taken once, when its view is built,
	 * and nothing refreshes it while the view lives -- so two modals on one document (one user, two browser
	 * tabs) each keep placing rows relative to a picture the other has already invalidated, and two text rows
	 * end up at the same {@code Line}. Nothing in the schema rejects that, the midpoint guard in
	 * {@link DocTextLineRepository#computeInsertAbovePosition} only compares against the two bounds it was
	 * handed, and both report functions order text rows by position alone -- so a tie leaves the printed
	 * order of the two rows to chance, and a text line scoped to the lines below it then applies to different
	 * article lines than the one the user placed it under.
	 * <p>
	 * After it, this view's rows ARE the document's rows: same set, same order. Two asymmetries in how the
	 * fresh data is folded in, and one thing that is deliberately total:
	 * <ul>
	 * <li><b>Only the POSITION of a row this view already knows is refreshed</b> -- never its text or scope.
	 * Those belong to {@link #patchRow}, which owns them under the row's own monitor; overwriting them from
	 * here would let a structural write in one modal undo an edit being made in another.</li>
	 * <li><b>A row this view has never seen is published whole</b>, with its own monitor, so it is
	 * immediately patchable and movable like any other -- the same reason {@link #insertRowAbove} widens all
	 * three structures together.</li>
	 * <li><b>A row the database no longer has is dropped</b>, from all three structures. Keeping it would
	 * leave the arithmetic a bound with nothing behind it: the vanished row's position is remembered from
	 * whenever this view was built, while the rows around it carry the positions they have now, so a midpoint
	 * between the two can land in a completely different article gap -- and a text line scoped to the lines
	 * that follow it then applies to a different run of articles than the user was pointing at. A row that is
	 * gone is not a position; it is a reference the user has to be told about, which is what
	 * {@link #rowNoLongerExists} is for.</li>
	 * </ul>
	 * Dropping is safe here for a reason that only became true once {@link #deleteRow} took the document lock
	 * across BOTH of its sections: a delete of this document can no longer be half-done while this method
	 * runs, so "absent from the database" no longer has to be read as "possibly being deleted right now". The
	 * same property is what stops this method re-publishing a row a concurrent delete has just removed.
	 * {@link #resolveRow}'s narrower case -- an id listed in {@link #rowIds} with no {@link #rowsById} entry --
	 * survives only on the read paths that do not refresh, such as {@link DocTextLinesView#getInsertAbovePositions}.
	 */
	private void refreshMergedOrderFromDatabase()
	{
		final ImmutableList<DocTextLinesRow> freshRows = documentAccess.loadMergedRows();
		final ImmutableSet<DocumentId> freshRowIds = freshRows.stream()
				.map(DocTextLinesRow::getId)
				.collect(ImmutableSet.toImmutableSet());

		for (final DocTextLinesRow freshRow : freshRows)
		{
			final DocumentId rowId = freshRow.getId();
			rowLocksById.computeIfAbsent(rowId, id -> new Object());
			rowsById.merge(rowId, freshRow, (knownRow, fresh) -> knownRow.getLine().compareTo(fresh.getLine()) == 0
					? knownRow
					: knownRow.toBuilder().line(fresh.getLine()).build());
		}

		rowsById.keySet().retainAll(freshRowIds);
		rowLocksById.keySet().retainAll(freshRowIds);

		// ImmutableSet iterates in insertion order, so this is the fresh merged order
		rowIds = freshRowIds.asList();
	}

	/**
	 * Derives the new row's position, persists it via {@link DocTextLineRepository#insertAbove}, and adds it to
	 * the merged order -- immediately above {@code referenceRowId}, or as the document's only row when
	 * {@code referenceRowId} is {@code null} (the document had no rows at all). The whole sequence --
	 * {@link #refreshMergedOrderFromDatabase re-derive} the merged order, compute against THAT, persist, then
	 * widen {@link #rowIds}/{@link #rowsById}/{@link #rowLocksById} -- runs under this document's lock and
	 * {@link #structuralLock}, in that order.
	 * <p>
	 * Both are needed and neither is sufficient. {@link #structuralLock} keeps two concurrent inserts within
	 * ONE modal from reading the same reference/previous positions and persisting the same midpoint
	 * {@code Line} (the collision guard inside {@link DocTextLineRepository#insertAbove} only catches a
	 * midpoint colliding with its own inputs, not with a concurrently-computed one from another request). The
	 * re-derivation is what keeps a SECOND modal of the same document from computing against an ordering the
	 * first has already replaced -- no concurrency required for that one, a snapshot minutes old is enough.
	 * And the document lock is what makes the re-derivation trustworthy: without it, the two modals could
	 * re-derive the same ordering and both still persist into it.
	 * <p>
	 * Widening {@link #rowsById} AND {@link #rowLocksById} together with {@link #rowIds} also means the new row
	 * is immediately patchable via {@link #patchRow} the moment this method returns -- a row present in
	 * {@link #rowsById} without a matching {@link #rowLocksById} entry would make {@link #getRowLockOrThrow}
	 * throw {@link EntityNotFoundException} on the row's very first edit.
	 * <p>
	 * The trade-off of holding database round trips inside the locks is deliberate: contention is confined to
	 * structural writes on one document, which mirrors the choice already accepted for {@link #patchRow}'s own
	 * per-row locking. Ordinary field edits take neither lock and are unaffected.
	 */
	DocTextLinesRow insertRowAbove(
			@Nullable final DocumentId referenceRowId,
			@Nullable final String textLine)
	{
		return withDocumentLocked(() -> {
			synchronized (structuralLock)
			{
				refreshMergedOrderFromDatabase();

				final InsertAbovePositions positions = computeInsertAbovePositions(referenceRowId);

				final InsertAboveRequest request = InsertAboveRequest.builder()
						.documentRef(documentRef)
						.textLine(textLine)
						.referencePosition(positions.getReferencePosition())
						.previousPosition(positions.getPreviousPosition())
						.articleLineExistsBeforeReferencePosition(positions.isArticleLineExistsBeforeReferencePosition())
						.build();
				final DocTextLine persistedTextLine = docTextLineRepository.insertAbove(request);
				final DocTextLinesRow newRow = DocTextLinesRow.ofTextLine(persistedTextLine);
				final DocumentId newRowId = newRow.getId();

				final int insertIndex = referenceRowId == null ? 0 : indexOfOrThrow(referenceRowId);

				rowLocksById.put(newRowId, new Object());
				rowsById.put(newRowId, newRow);
				updateRowIds(ids -> ids.add(insertIndex, newRowId));

				return newRow;
			}
		});
	}

	private int indexOfOrThrow(@NonNull final DocumentId rowId)
	{
		return indexOfOrThrow(rowIds, rowId);
	}

	/** Overload for a caller that has already taken its own snapshot of the merged order and must stay on it -- see {@link #boundPositionAt}. */
	private static int indexOfOrThrow(@NonNull final List<DocumentId> rowIds, @NonNull final DocumentId rowId)
	{
		final int index = rowIds.indexOf(rowId);
		if (index < 0)
		{
			throw new EntityNotFoundException(rowId.toJson());
		}
		return index;
	}

	/**
	 * {@code true} when there is a row -- of either kind -- immediately before/after {@code rowId} in the merged
	 * order. The move precondition: a move is only impossible when the selected row already sits at the very
	 * start or very end of the WHOLE merged list, not merely relative to other text rows.
	 * <p>
	 * A precondition check must resolve to a rejection, never throw -- so unlike {@link #moveRow}'s own use of
	 * {@link #indexOfOrThrow}, a {@code rowId} no longer present in {@link #rowIds} (selected, then removed by a
	 * concurrent delete before this check ran) resolves to {@code false}: a vanished row has no neighbour to
	 * exchange with either, which is the correct rejection outcome, not a server error.
	 */
	boolean hasNeighbor(@NonNull final DocumentId rowId, final boolean towardStart)
	{
		final List<DocumentId> currentRowIds = rowIds; // one read, see boundPositionAt
		final int index = currentRowIds.indexOf(rowId);
		if (index < 0)
		{
			return false;
		}
		return towardStart ? index > 0 : index < currentRowIds.size() - 1;
	}

	/**
	 * Exchanges {@code rowId} (always a text row) with the row immediately before/after it in the merged order,
	 * whatever kind that row is. Two distinct persistence shapes, chosen by the neighbour's kind:
	 * <ul>
	 * <li><b>Neighbour is a text row</b>: a genuine two-way exchange of stored positions via
	 * {@link DocTextLineRepository#swapPositions} -- both rows' {@code Line} values trade places, neither's
	 * {@code TextLineScope} is touched (the repository call only ever writes {@code Line}).</li>
	 * <li><b>Neighbour is an article row</b>: the article's {@code Line} is never written -- it belongs to the
	 * order/shipment line table, not this one, and article positions are user-visible/printed and must stay
	 * exactly as they are. Only {@code rowId}'s own position is recomputed, via
	 * {@link DocTextLineRepository#computePositionBetween}, to land strictly on the other side of the article:
	 * between the article's position and whatever comes after it (moving down) or before it (moving up), or
	 * simply past it when there is nothing on that far side. The two rows still visibly trade places in the
	 * merged order -- only one of their two stored positions actually changes.</li>
	 * </ul>
	 * Both shapes run entirely under this document's lock and {@link #structuralLock}, behind the same
	 * {@link #refreshMergedOrderFromDatabase re-derivation} {@link #insertRowAbove} runs -- a move touches the
	 * ordering just as much as an insert does, and needs the same freshness for a reason of its own. The
	 * article-neighbour shape computes a new position and can land on one another modal has just taken. The
	 * text-neighbour shape computes nothing and cannot produce a tie at all -- it permutes two values that
	 * already exist -- but picks its exchange partner BY POSITION IN THE MERGED ORDER, so a stale order makes
	 * it trade places with the wrong row and move the selected row past several at once. Both are corrupted
	 * orderings; only one of them is a duplicate.
	 * <p>
	 * Every position this method reads (the neighbour's, and the row beyond it) is read from
	 * {@link #rowsById}/{@link #rowIds} after that refresh, never cached across calls. If one of those rows is
	 * in the middle of being removed, so that its position cannot be read at all, the move is refused rather
	 * than computed against a substitute -- see {@link #boundPositionAt}.
	 * <p>
	 * Deliberately NOT additionally synchronized on either row's own monitor from {@link #rowLocksById} (unlike
	 * {@link #deleteRow}): a concurrent {@link #patchRow} of a row this method touches only ever writes {@code
	 * TextLine}/{@code TextLineScope} -- disjoint database columns from the {@code Line} column both persistence
	 * shapes above write -- metasfresh's PO layer issues an {@code UPDATE} only for the columns actually set on
	 * that PO instance (verified against {@code PO.saveUpdate()}), so the two saves cannot clobber each other at
	 * the database level regardless of interleaving. The remaining exposure -- {@link #rowsById}'s in-memory
	 * copy of the moved row briefly reverting a concurrent patch's field until the next reload -- is the same
	 * severity class already accepted between {@link #insertRowAbove} and {@link #patchRow} today (neither
	 * takes the other's lock either), not the corrupted-ordering failure {@link #structuralLock} exists to
	 * prevent. Taking a row's monitor here in addition to {@link #structuralLock} would mean holding both at
	 * once -- exactly the nesting the class is built to avoid -- for a benign race a disjoint-column analysis
	 * already rules out as data-corrupting.
	 */
	DocTextLinesRow moveRow(@NonNull final DocumentId rowId, final boolean towardStart)
	{
		return withDocumentLocked(() -> moveRowLocked(rowId, towardStart));
	}

	private DocTextLinesRow moveRowLocked(@NonNull final DocumentId rowId, final boolean towardStart)
	{
		synchronized (structuralLock)
		{
			refreshMergedOrderFromDatabase();

			// the row the user selected may have been deleted in another window, which the re-derivation has
			// just established -- same situation as on the insert path, same answer
			if (!resolveRow(rowId).isPresent())
			{
				throw rowNoLongerExists(rowId);
			}

			final DocTextLinesRow row = getTextRowOrThrow(rowId);
			final int index = indexOfOrThrow(rowId);
			final int neighborIndex = towardStart ? index - 1 : index + 1;
			if (neighborIndex < 0 || neighborIndex >= rowIds.size())
			{
				throw new AdempiereException("No row to move " + (towardStart ? "up" : "down") + " into")
						.appendParametersToMessage()
						.setParameter("rowId", rowId);
			}

			final DocumentId neighborId = rowIds.get(neighborIndex);
			// a neighbour that is listed but unreadable is a row in the middle of being removed, which is a
			// different situation from the "nothing on that side" rejected just above: there IS an exchange
			// partner, it is simply not one this move can act on right now, so the refusal says so and invites
			// a retry rather than claiming the row is already at the edge of the list
			final DocTextLinesRow neighbor = resolveRow(neighborId)
					.orElseThrow(() -> rowIsBeingRemoved(neighborId));

			final DocTextLinesRow newRow;
			if (neighbor.isTextLine())
			{
				docTextLineRepository.swapPositions(row.getTextLineId(), neighbor.getTextLineId());

				newRow = row.toBuilder().line(neighbor.getLine()).build();
				final DocTextLinesRow newNeighbor = neighbor.toBuilder().line(row.getLine()).build();
				rowsById.put(rowId, newRow);
				rowsById.put(neighborId, newNeighbor);

				updateRowIds(ids -> {
					ids.set(index, neighborId);
					ids.set(neighborIndex, rowId);
				});
			}
			else
			{
				// out of range means the neighbour is itself the first/last row, so there is nothing on the far
				// side to stay clear of; a row listed there but unreadable is refused -- see boundPositionAt
				final BigDecimal beyondPosition = boundPositionAt(towardStart ? neighborIndex - 1 : neighborIndex + 1);
				final BigDecimal newPosition = towardStart
						? DocTextLineRepository.computePositionBetween(beyondPosition, neighbor.getLine())
						: DocTextLineRepository.computePositionBetween(neighbor.getLine(), beyondPosition);

				docTextLineRepository.updatePosition(row.getTextLineId(), newPosition);

				newRow = row.toBuilder().line(newPosition).build();
				rowsById.put(rowId, newRow);

				updateRowIds(ids -> {
					ids.remove(index);
					ids.add(neighborIndex, rowId);
				});
			}

			return newRow;
		}
	}

	/**
	 * Removes a text row by running {@link #deleteRowPersistAndUnpublish} and then
	 * {@link #deleteRowRemoveFromMergedOrder} -- two sequential, never nested critical sections, ordered so
	 * that nothing is un-published before the delete has actually succeeded. That is the same
	 * persist-then-mutate shape {@link #insertRowAbove} follows for its own success path, just run in reverse
	 * (there, a successful persist is followed by widening; here, a successful persist is followed by
	 * shrinking).
	 * <p>
	 * The two sections are sequential, not nested -- the row's own monitor is released before
	 * {@link #structuralLock} is acquired, so a delete never holds both at once, and deadlock is not possible.
	 * They are kept as two separately-callable methods so that the boundary between them -- the one moment at
	 * which a row is already gone from {@link #rowsById} but still listed in {@link #rowIds} -- is an
	 * addressable point rather than an instant buried inside one method body.
	 * <p>
	 * Both sections run under this document's lock, which a delete needs for one specific reason: a structural
	 * write in another modal re-reads the document from the database and publishes what it finds
	 * ({@link #refreshMergedOrderFromDatabase}). Were that read allowed between this delete's own persist and
	 * its clearing of {@link #rowsById}, it would put the deleted row back into a view as a row with no record
	 * behind it. Holding the document lock across both sections removes that interleaving; it also means the
	 * moment described above is never observed by another structural write of the same document, only by the
	 * lock-free readers for which {@link #resolveRow} and {@link #boundPositionAt} exist.
	 */
	void deleteRow(@NonNull final DocumentId rowId)
	{
		withDocumentLocked(() -> {
			deleteRowPersistAndUnpublish(rowId);
			deleteRowRemoveFromMergedOrder(rowId);
			return null;
		});
	}

	/**
	 * First of {@link #deleteRow}'s two sections. Under the row's own monitor from {@link #rowLocksById} --
	 * the SAME monitor {@link #patchRow} synchronizes on -- the database delete happens FIRST; only once it
	 * has returned without throwing are {@link #rowsById}/{@link #rowLocksById} cleared, still inside this
	 * same section. If {@code deleteById} throws, this section is exited by the exception before either map is
	 * touched, and {@link #deleteRowRemoveFromMergedOrder} is never reached -- the row stays fully intact (in
	 * {@link #rowIds}, {@link #rowsById}, the database) rather than becoming a row that is gone from the
	 * merged view but still undeleted underneath.
	 * <p>
	 * Clearing {@link #rowsById} here, in the same section as the persist and before this method's own monitor
	 * is released, is also what keeps the resurrection guard deterministic: a {@link #patchRow} blocked on
	 * this exact monitor is only ever admitted AFTER {@link #rowsById} no longer has the entry, so its
	 * {@code getRowOrThrow} is guaranteed to throw {@link EntityNotFoundException} rather than racing the
	 * second section.
	 */
	void deleteRowPersistAndUnpublish(@NonNull final DocumentId rowId)
	{
		synchronized (getRowLockOrThrow(rowId))
		{
			final DocTextLinesRow row = getTextRowOrThrow(rowId);
			docTextLineRepository.deleteById(row.getTextLineId());

			// only reached once the database delete above succeeded
			rowsById.remove(rowId);
			rowLocksById.remove(rowId);
		}
	}

	/**
	 * Second of {@link #deleteRow}'s two sections, reached only once {@link #deleteRowPersistAndUnpublish} has
	 * returned normally. Under {@link #structuralLock} -- the same guard {@link #insertRowAbove}/
	 * {@link #moveRow} use for their own ordering mutation -- {@code rowId} is removed from {@link #rowIds}
	 * only now, i.e. only after the delete is confirmed persisted. Until it runs, a reader can observe
	 * {@code rowId} still in {@link #rowIds} with no matching {@link #rowsById} entry; every reader that can
	 * see that combination handles it deliberately -- see {@link #resolveRow} and {@link #boundPositionAt}.
	 */
	void deleteRowRemoveFromMergedOrder(@NonNull final DocumentId rowId)
	{
		synchronized (structuralLock)
		{
			updateRowIds(ids -> ids.remove(rowId));
		}
	}

	private DocTextLinesRow getTextRowOrThrow(@NonNull final DocumentId rowId)
	{
		final DocTextLinesRow row = getRowOrThrow(rowId);
		if (!row.isTextLine())
		{
			throw new AdempiereException("Article line rows cannot be deleted or moved")
					.appendParametersToMessage()
					.setParameter("rowId", rowId);
		}
		return row;
	}

	/**
	 * Patches one row's text and/or scope. The {@code ctx.documentsCollection}/{@code ctx.userRolePermissions}
	 * carried by {@link RowEditingContext} are not needed here -- unlike a window-backed document patch, this
	 * view has no logic-expression re-evaluation or permission-gated field -- only
	 * {@link RowEditingContext#getRowId()} is used.
	 * <p>
	 * Two invariants both hold, in this order:
	 * <ol>
	 * <li><b>Per-row atomicity.</b> The whole read-persist-publish sequence for one row runs under that row's
	 * own monitor ({@link #rowLocksById}), so two concurrent patches of the same row cannot interleave --
	 * this module's field-level auto-save means "user edits the text, then the scope, moments apart" is the
	 * ordinary case, not an edge case, and an unserialised read-modify-write here would silently drop
	 * whichever edit lost the race.</li>
	 * <li><b>No publish before a successful persist.</b> The DB write happens before {@code rowsById} is
	 * updated; if it throws, the {@code synchronized} block is exited by the exception before the row is
	 * touched, so a reader of this view (including the same failed request's own error response) still sees
	 * the old, actually-persisted value.</li>
	 * </ol>
	 * A per-row {@code Object} monitor was chosen over folding the repository call into
	 * {@code ConcurrentHashMap#compute} (which would also serialise and would also leave the mapping
	 * untouched on failure): {@code compute}'s own contract discourages exactly this -- a blocking, "not
	 * short and simple" computation held under the map's internal per-bin lock can stall unrelated keys that
	 * happen to hash into the same bin, a cost with no visible trace in this class. A monitor scoped to one
	 * {@link DocumentId}, built once for the fixed row set at construction time, keeps the blocking window
	 * provably limited to that one row.
	 */
	@Override
	public void patchRow(
			final RowEditingContext ctx,
			final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final DocTextLineRowUserChangeRequest userChanges = toUserChangeRequest(fieldChangeRequests);
		final DocumentId rowId = ctx.getRowId();

		synchronized (getRowLockOrThrow(rowId))
		{
			final DocTextLinesRow patchedRow = getRowOrThrow(rowId).withChanges(userChanges);

			docTextLineRepository.updateTextAndScope(
					patchedRow.getTextLineId(),
					patchedRow.getTextLine(),
					patchedRow.getTextLineScope());

			// only reached once the DB write above succeeded -- see invariant 2 above
			rowsById.put(rowId, patchedRow);
		}
	}

	private static DocTextLineRowUserChangeRequest toUserChangeRequest(@NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		Check.assumeNotEmpty(fieldChangeRequests, "fieldChangeRequests is not empty");

		final DocTextLineRowUserChangeRequest.DocTextLineRowUserChangeRequestBuilder builder = DocTextLineRowUserChangeRequest.builder();
		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (DocTextLinesRow.FIELD_TextLine.equals(fieldName))
			{
				builder.textLine(fieldChangeRequest.getValueAsString(null));
			}
			else if (DocTextLinesRow.FIELD_TextLineScope.equals(fieldName))
			{
				builder.textLineScope(fieldChangeRequest.getValueAsEnum(TextLineScope.class));
			}
		}

		return builder.build();
	}

	private DocTextLinesRow getRowOrThrow(@NonNull final DocumentId rowId)
	{
		final DocTextLinesRow row = rowsById.get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException(rowId.toJson());
		}
		return row;
	}

	private Object getRowLockOrThrow(@NonNull final DocumentId rowId)
	{
		final Object lock = rowLocksById.get(rowId);
		if (lock == null)
		{
			throw new EntityNotFoundException(rowId.toJson());
		}
		return lock;
	}
}
