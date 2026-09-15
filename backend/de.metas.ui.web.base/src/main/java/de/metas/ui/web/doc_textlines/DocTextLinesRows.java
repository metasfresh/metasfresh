package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;

/**
 * Read-only holder of the merged article-line/text-line rows of one {@link DocTextLinesView} (DESIGN.md § D-E).
 * Row order is fixed at construction time -- it is the merged order {@link DocTextLinesRowsLoader} computed --
 * and preserved here via {@link ImmutableMap} iteration order (insertion order).
 * <p>
 * This task is read-only (no {@code IEditableRowsData}); patching text rows in place is a later task.
 */
final class DocTextLinesRows implements IRowsData<DocTextLinesRow>
{
	static DocTextLinesRows cast(final IRowsData<DocTextLinesRow> rowsData)
	{
		return (DocTextLinesRows)rowsData;
	}

	private final ImmutableList<DocumentId> rowIds; // preserves the merged order
	private final ImmutableMap<DocumentId, DocTextLinesRow> rowsById;

	@Builder
	private DocTextLinesRows(@NonNull final List<DocTextLinesRow> rows)
	{
		// empty is legal here (unlike the shipment-candidates-editor precedent): an order with no lines at
		// all (AC25) still opens the modal, just with zero rows.
		rowIds = rows.stream()
				.map(DocTextLinesRow::getId)
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap.Builder<DocumentId, DocTextLinesRow> rowsByIdBuilder = ImmutableMap.builder();
		rows.forEach(row -> rowsByIdBuilder.put(row.getId(), row));
		rowsById = rowsByIdBuilder.build();
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
}
