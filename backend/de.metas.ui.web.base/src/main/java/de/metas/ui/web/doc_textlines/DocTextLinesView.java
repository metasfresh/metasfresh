package de.metas.ui.web.doc_textlines;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * The merged view of one sales order's article lines and text lines (DESIGN.md § D-E). Article rows are
 * read-only, for orientation/positioning only; text rows are inline-editable (task 6, {@link IEditableView}) --
 * {@code ViewRowEditRestController} requires the view itself to implement {@link IEditableView} (it casts via
 * {@link IEditableView#asEditableView}), even though {@code patchViewRow} is already implemented concretely on
 * {@link AbstractCustomView}. Quick actions (insert-above/delete/move) are wired on top of this by later tasks.
 */
public final class DocTextLinesView extends AbstractCustomView<DocTextLinesRow> implements IEditableView
{
	@Builder
	private DocTextLinesView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final DocTextLinesRows rows)
	{
		super(viewId, description, rows, NullDocumentFilterDescriptorsProvider.instance);
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	protected DocTextLinesRows getRowsData()
	{
		return DocTextLinesRows.cast(super.getRowsData());
	}

	/**
	 * The public seam a future insert-above quick-action process (tasks 8/9, in a sibling {@code process}
	 * subpackage) uses to compute {@code InsertAboveRequest}'s three position values -- reached via
	 * {@code getView()} the way {@code ProductsProposalViewBasedProcess} calls public methods on
	 * {@code ProductsProposalView}. The actual derivation lives in {@link DocTextLinesRows}, where the merged
	 * ordering is held.
	 *
	 * @param referenceRowId the row the user selected before invoking insert-above; {@code null} only when the
	 *                        document has no rows at all (AC25).
	 */
	public InsertAbovePositions getInsertAbovePositions(@Nullable final DocumentId referenceRowId)
	{
		return getRowsData().computeInsertAbovePositions(referenceRowId);
	}
}
