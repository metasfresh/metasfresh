package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Holder of the merged article-line/text-line rows of one {@link DocTextLinesView}. Row order starts as the
 * merged order {@link DocTextLinesRowsLoader} computed, preserved via {@link #rowIds}' insertion order, and is
 * widened in place by {@link #insertRowAbove} as rows are added.
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

	private final List<DocumentId> rowIds; // preserves the merged order; mutated only under #structuralLock
	private final ConcurrentHashMap<DocumentId, DocTextLinesRow> rowsById;
	private final ConcurrentHashMap<DocumentId, Object> rowLocksById; // one dedicated monitor per row, see #patchRow
	private final DocTextLineRepository docTextLineRepository;

	/** Guards {@link #insertRowAbove} against two concurrent inserts corrupting {@link #rowIds}' index arithmetic. */
	private final Object structuralLock = new Object();

	@Builder
	private DocTextLinesRows(
			@NonNull final List<DocTextLinesRow> rows,
			@NonNull final DocTextLineRepository docTextLineRepository)
	{
		// empty is legal here (unlike the shipment-candidates-editor precedent): an order with no lines at
		// all still opens the modal, just with zero rows.
		rowIds = new CopyOnWriteArrayList<>(rows.stream()
				.map(DocTextLinesRow::getId)
				.collect(ImmutableList.toImmutableList()));

		rowsById = new ConcurrentHashMap<>(Maps.uniqueIndex(rows, DocTextLinesRow::getId));
		rowLocksById = new ConcurrentHashMap<>(rowIds.stream().collect(ImmutableMap.toImmutableMap(id -> id, id -> new Object())));
		this.docTextLineRepository = docTextLineRepository;
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
	 * to handle, in whatever way is right for it (skip it, reject cleanly, or treat it as absent), rather than
	 * an uncontrolled {@code NullPointerException} that happens to be avoided only by luck of call order. (A
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
	 *
	 * @param referenceRowId the selected row; {@code null} only when the document has no rows at all.
	 */
	InsertAbovePositions computeInsertAbovePositions(@Nullable final DocumentId referenceRowId)
	{
		if (referenceRowId == null)
		{
			if (!rowIds.isEmpty())
			{
				throw new AdempiereException("referenceRowId is required unless the document has no rows at all")
						.appendParametersToMessage()
						.setParameter("rowIds", rowIds);
			}
			return InsertAbovePositions.EMPTY_DOCUMENT;
		}

		final int referenceIndex = indexOfOrThrow(referenceRowId);

		// the reference row was explicitly selected by the caller -- if it vanished between selection and this
		// computation, there is nothing sensible to insert above; reject cleanly rather than crash
		final BigDecimal referencePosition = resolveRow(referenceRowId)
				.map(DocTextLinesRow::getLine)
				.orElseThrow(() -> new AdempiereException("Cannot insert above a row that no longer exists")
						.appendParametersToMessage()
						.setParameter("referenceRowId", referenceRowId));

		// the previous row is only a bound for the midpoint arithmetic -- a vanished one (the delete-caused
		// gap resolveRow's javadoc names) is treated as no bound at all, same as there genuinely being none
		final BigDecimal previousPosition = referenceIndex > 0
				? resolveRow(rowIds.get(referenceIndex - 1)).map(DocTextLinesRow::getLine).orElse(null)
				: null;

		// a vanished row in the preceding sublist contributes nothing to "does an article precede this
		// position" either way -- skip it, same as if it had already been trimmed from rowIds
		final boolean articleLineExistsBeforeReferencePosition = rowIds.subList(0, referenceIndex).stream()
				.map(this::resolveRow)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.anyMatch(DocTextLinesRow::isArticleLine);

		return InsertAbovePositions.builder()
				.referencePosition(referencePosition)
				.previousPosition(previousPosition)
				.articleLineExistsBeforeReferencePosition(articleLineExistsBeforeReferencePosition)
				.build();
	}

	/**
	 * Derives the new row's position, persists it via {@link DocTextLineRepository#insertAbove}, and adds it to
	 * the merged order -- immediately above {@code referenceRowId}, or as the document's only row when
	 * {@code referenceRowId} is {@code null} (the document had no rows at all). The whole sequence -- read the
	 * current merged ordering, persist against it, then widen {@link #rowIds}/{@link #rowsById}/
	 * {@link #rowLocksById} -- runs under {@link #structuralLock}: two concurrent inserts against the same
	 * {@code referenceRowId} must not both read the same reference/previous positions and persist the same
	 * midpoint {@code Line} (the collision guard inside {@link DocTextLineRepository#insertAbove} only catches
	 * a midpoint colliding with its own inputs, not with a concurrently-computed one from another request).
	 * Widening {@link #rowsById} AND {@link #rowLocksById} together with {@link #rowIds} also means the new row
	 * is immediately patchable via {@link #patchRow} the moment this method returns -- a row present in
	 * {@link #rowsById} without a matching {@link #rowLocksById} entry would make {@link #getRowLockOrThrow}
	 * throw {@link EntityNotFoundException} on the row's very first edit.
	 * <p>
	 * The trade-off of holding a database round trip inside {@code structuralLock} is deliberate: contention is
	 * confined to concurrent insert-above requests on one view instance (one order's modal), which mirrors the
	 * choice already accepted for {@link #patchRow}'s own per-row locking.
	 */
	DocTextLinesRow insertRowAbove(
			@Nullable final DocumentId referenceRowId,
			@NonNull final DocTextLineDocumentRef documentRef,
			@Nullable final String textLine)
	{
		synchronized (structuralLock)
		{
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
			rowIds.add(insertIndex, newRowId);

			return newRow;
		}
	}

	private int indexOfOrThrow(@NonNull final DocumentId rowId)
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
		final int index = rowIds.indexOf(rowId);
		if (index < 0)
		{
			return false;
		}
		return towardStart ? index > 0 : index < rowIds.size() - 1;
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
	 * Both shapes run entirely under {@link #structuralLock}, the same guard {@link #insertRowAbove} uses for
	 * its own read-neighbours/persist/mutate sequence: two concurrent moves (or a move racing an insert or a
	 * delete) reading the same merged order and both acting on it is exactly the duplicate-position failure
	 * class already fixed once for insert-above, and a move touches the ordering just as much as an insert
	 * does. Every position read this method uses (the neighbour's, and the row beyond it) is read fresh from
	 * {@link #rowsById}/{@link #rowIds} at the moment the lock is held, never cached across calls -- so a move
	 * queued behind another structural change always computes against the ordering that change left behind, not
	 * a stale snapshot.
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
		synchronized (structuralLock)
		{
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
			// the neighbour is the row this method is about to exchange with -- if it vanished (the
			// delete-caused gap resolveRow's javadoc names), there is nothing sensible left to exchange with;
			// reject cleanly rather than crash, same rationale as the reference-row lookup in
			// computeInsertAbovePositions
			final DocTextLinesRow neighbor = resolveRow(neighborId)
					.orElseThrow(() -> new AdempiereException("No row to move " + (towardStart ? "up" : "down") + " into")
							.appendParametersToMessage()
							.setParameter("rowId", rowId));

			final DocTextLinesRow newRow;
			if (neighbor.isTextLine())
			{
				docTextLineRepository.swapPositions(row.getTextLineId(), neighbor.getTextLineId());

				newRow = row.toBuilder().line(neighbor.getLine()).build();
				final DocTextLinesRow newNeighbor = neighbor.toBuilder().line(row.getLine()).build();
				rowsById.put(rowId, newRow);
				rowsById.put(neighborId, newNeighbor);

				rowIds.set(index, neighborId);
				rowIds.set(neighborIndex, rowId);
			}
			else
			{
				final int beyondIndex = towardStart ? neighborIndex - 1 : neighborIndex + 1;
				// the row beyond the neighbour is only a bound for the position arithmetic -- a vanished one
				// is treated as no bound at all, same as there genuinely being none (out of range)
				final BigDecimal beyondPosition = beyondIndex >= 0 && beyondIndex < rowIds.size()
						? resolveRow(rowIds.get(beyondIndex)).map(DocTextLinesRow::getLine).orElse(null)
						: null;
				final BigDecimal newPosition = towardStart
						? DocTextLineRepository.computePositionBetween(beyondPosition, neighbor.getLine())
						: DocTextLineRepository.computePositionBetween(neighbor.getLine(), beyondPosition);

				docTextLineRepository.updatePosition(row.getTextLineId(), newPosition);

				newRow = row.toBuilder().line(newPosition).build();
				rowsById.put(rowId, newRow);

				rowIds.remove(index);
				rowIds.add(neighborIndex, rowId);
			}

			return newRow;
		}
	}

	/**
	 * Removes a text row in two sequential (never nested) critical sections, ordered so that nothing is
	 * un-published before the delete has actually succeeded -- the same persist-then-mutate shape
	 * {@link #insertRowAbove} follows for its own success path, just run in reverse (there, a successful
	 * persist is followed by widening; here, a successful persist is followed by shrinking):
	 * <ol>
	 * <li>Under the row's own monitor from {@link #rowLocksById} -- the SAME monitor {@link #patchRow}
	 * synchronizes on -- the database delete happens FIRST; only once it has returned without throwing are
	 * {@link #rowsById}/{@link #rowLocksById} cleared, still inside this same section. If {@code deleteById}
	 * throws, this section is exited by the exception before either map is touched, and section 2 below is
	 * never reached -- the row stays fully intact (in {@link #rowIds}, {@link #rowsById}, the database) rather
	 * than becoming a ghost that is gone from the merged view but still undeleted underneath. Clearing
	 * {@link #rowsById} here, in the same section as the persist and before this method's own monitor is
	 * released, is also what keeps the resurrection guard deterministic: a {@link #patchRow} blocked on this
	 * exact monitor is only ever admitted AFTER {@link #rowsById} no longer has the entry, so its
	 * {@code getRowOrThrow} is guaranteed to throw {@link EntityNotFoundException} rather than racing this
	 * method's second section.</li>
	 * <li>Under {@link #structuralLock} -- the same guard {@link #insertRowAbove}/{@link #moveRow} use for
	 * their own ordering mutation -- {@code rowId} is removed from {@link #rowIds} only now, i.e. only after
	 * the delete is confirmed persisted. Between the two sections, a reader can briefly observe {@code rowId}
	 * still in {@link #rowIds} with no matching {@link #rowsById} entry; {@link #getDocumentId2TopLevelRows()}
	 * tolerates exactly that combination by skipping it, for this reason.</li>
	 * </ol>
	 * The two sections are sequential, not nested -- the row's own monitor is released before
	 * {@link #structuralLock} is acquired, so this method never holds both at once, and deadlock is not
	 * possible.
	 */
	void deleteRow(@NonNull final DocumentId rowId)
	{
		synchronized (getRowLockOrThrow(rowId))
		{
			final DocTextLinesRow row = getTextRowOrThrow(rowId);
			docTextLineRepository.deleteById(row.getTextLineId());

			// only reached once the database delete above succeeded
			rowsById.remove(rowId);
			rowLocksById.remove(rowId);
		}

		synchronized (structuralLock)
		{
			rowIds.remove(rowId);
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
