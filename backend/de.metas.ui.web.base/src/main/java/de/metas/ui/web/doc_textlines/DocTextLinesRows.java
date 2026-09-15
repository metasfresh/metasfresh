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
	 * Persists before mutating the in-memory row, deliberately not the other way round: if the DB write
	 * fails, a reader of this view (including the same failed request's own error response) must still see
	 * the old, actually-persisted value -- not a new value that only ever existed in memory.
	 */
	@Override
	public void patchRow(
			final RowEditingContext ctx,
			final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final DocTextLineRowUserChangeRequest userChanges = toUserChangeRequest(fieldChangeRequests);
		final DocumentId rowId = ctx.getRowId();
		final DocTextLinesRow patchedRow = getRowOrThrow(rowId).withChanges(userChanges);

		docTextLineRepository.updateTextAndScope(
				patchedRow.getTextLineId(),
				patchedRow.getTextLine(),
				patchedRow.getTextLineScope());

		// only reached once the DB write above succeeded -- see the persist-before-mutate note above
		rowsById.put(rowId, patchedRow);
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
}
