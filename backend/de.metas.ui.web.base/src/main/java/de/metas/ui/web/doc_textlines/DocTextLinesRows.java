package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.doctextline.DocTextLineRepository;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holder of the merged article-line/text-line rows of one {@link DocTextLinesView}. Row order is fixed at
 * construction time -- it is the merged order {@link DocTextLinesRowsLoader} computed -- and preserved here
 * via {@link #rowIds}' insertion order.
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

	private final ImmutableList<DocumentId> rowIds; // preserves the merged order
	private final ConcurrentHashMap<DocumentId, DocTextLinesRow> rowsById;
	private final ImmutableMap<DocumentId, Object> rowLocksById; // one dedicated monitor per row, see #patchRow
	private final DocTextLineRepository docTextLineRepository;

	@Builder
	private DocTextLinesRows(
			@NonNull final List<DocTextLinesRow> rows,
			@NonNull final DocTextLineRepository docTextLineRepository)
	{
		// empty is legal here (unlike the shipment-candidates-editor precedent): an order with no lines at
		// all still opens the modal, just with zero rows.
		rowIds = rows.stream()
				.map(DocTextLinesRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = new ConcurrentHashMap<>(Maps.uniqueIndex(rows, DocTextLinesRow::getId));
		rowLocksById = rowIds.stream().collect(ImmutableMap.toImmutableMap(id -> id, id -> new Object()));
		this.docTextLineRepository = docTextLineRepository;
	}

	@Override
	public Map<DocumentId, DocTextLinesRow> getDocumentId2TopLevelRows()
	{
		final ImmutableMap.Builder<DocumentId, DocTextLinesRow> result = ImmutableMap.builder();
		rowIds.forEach(rowId -> result.put(rowId, rowsById.get(rowId)));
		return result.build();
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

		final int referenceIndex = rowIds.indexOf(referenceRowId);
		if (referenceIndex < 0)
		{
			throw new EntityNotFoundException(referenceRowId.toJson());
		}

		final BigDecimal referencePosition = rowsById.get(referenceRowId).getLine();

		final BigDecimal previousPosition = referenceIndex > 0
				? rowsById.get(rowIds.get(referenceIndex - 1)).getLine()
				: null;

		final boolean articleLineExistsBeforeReferencePosition = rowIds.subList(0, referenceIndex).stream()
				.map(rowsById::get)
				.anyMatch(DocTextLinesRow::isArticleLine);

		return InsertAbovePositions.builder()
				.referencePosition(referencePosition)
				.previousPosition(previousPosition)
				.articleLineExistsBeforeReferencePosition(articleLineExistsBeforeReferencePosition)
				.build();
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
