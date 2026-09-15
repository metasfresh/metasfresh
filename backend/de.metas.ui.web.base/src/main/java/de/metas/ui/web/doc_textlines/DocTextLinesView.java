package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The merged view of one sales order's article lines and text lines. Article rows are read-only, for
 * orientation/positioning only; text rows are inline-editable via {@link IEditableView}. Implementing
 * {@link IEditableView} is required here because {@code ViewRowEditRestController} casts the view via
 * {@link IEditableView#asEditableView}, even though {@code patchViewRow} is already implemented concretely on
 * {@link AbstractCustomView}. Quick actions (insert-above/delete/move) are wired on top of this separately.
 */
public final class DocTextLinesView extends AbstractCustomView<DocTextLinesRow> implements IEditableView
{
	public static DocTextLinesView cast(final Object viewObj)
	{
		return (DocTextLinesView)viewObj;
	}

	@Getter
	@NonNull
	private final DocTextLineDocumentRef documentRef;

	private final ImmutableList<RelatedProcessDescriptor> processes;

	@Builder
	private DocTextLinesView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final DocTextLinesRows rows,
			@NonNull final DocTextLineDocumentRef documentRef,
			@Nullable final List<RelatedProcessDescriptor> processes)
	{
		super(viewId, description, rows, NullDocumentFilterDescriptorsProvider.instance);
		this.documentRef = documentRef;
		this.processes = processes != null ? ImmutableList.copyOf(processes) : ImmutableList.of();
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return processes;
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
	 *                        document has no rows at all.
	 */
	public InsertAbovePositions getInsertAbovePositions(@Nullable final DocumentId referenceRowId)
	{
		return getRowsData().computeInsertAbovePositions(referenceRowId);
	}

	/**
	 * Persists a new text row and adds it to this view, immediately above {@code referenceRowId} -- see
	 * {@link DocTextLinesRows#insertRowAbove(DocumentId, DocTextLineDocumentRef, String)} for the placement rule
	 * and the atomicity it provides -- and notifies the frontend to reload, the same way
	 * {@code ProductsProposalView#addOrUpdateRows} does after widening its own rows data. This is the one entry
	 * point an insert-above quick-action process needs; it never touches {@code DocTextLineRepository} itself.
	 */
	public void insertRowAbove(@Nullable final DocumentId referenceRowId, @Nullable final String textLine)
	{
		getRowsData().insertRowAbove(referenceRowId, documentRef, textLine);
		invalidateAll();
	}

	/** {@code true} when {@code rowId} has a text-row neighbour to swap positions with in the given direction -- the delete/move-precondition seam, mirroring {@link #getInsertAbovePositions}. */
	public boolean hasTextNeighbor(@NonNull final DocumentId rowId, final boolean towardStart)
	{
		return getRowsData().hasTextNeighbor(rowId, towardStart);
	}

	/**
	 * Swaps {@code rowId}'s position with its nearest text-row neighbour -- see
	 * {@link DocTextLinesRows#moveRow(DocumentId, boolean)} for the jump-over-article-rows rule and the
	 * concurrency guard -- and notifies the frontend to reload.
	 *
	 * @param towardStart {@code true} to move the row earlier in the merged order ("up"), {@code false} to move it later ("down")
	 */
	public void moveRow(@NonNull final DocumentId rowId, final boolean towardStart)
	{
		getRowsData().moveRow(rowId, towardStart);
		invalidateAll();
	}

	/**
	 * Deletes a text row -- see {@link DocTextLinesRows#deleteRow(DocumentId)} for the two-critical-section
	 * concurrency guard -- and notifies the frontend to reload.
	 */
	public void deleteRow(@NonNull final DocumentId rowId)
	{
		getRowsData().deleteRow(rowId);
		invalidateAll();
	}
}
