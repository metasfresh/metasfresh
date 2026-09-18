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
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The merged view of one sales order's article lines and text lines. Article rows are read-only, for
 * orientation/positioning only; text rows are inline-editable via {@link IEditableView}. Implementing
 * {@link IEditableView} is required here because {@code ViewRowEditRestController} casts the view via
 * {@link IEditableView#asEditableView}, even though {@code patchViewRow} is already implemented concretely on
 * {@link AbstractCustomView}; {@link #getFieldDropdown} has no such concrete implementation and must be
 * provided here, because the scope column is a list widget whose values the frontend asks this view for.
 * Quick actions (insert-above/delete/move) are wired on top of this separately.
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

	/** The {@code TextLineScope} reference list, built once by {@link DocTextLinesViewFactory} -- see {@link #getFieldDropdown}. */
	@NonNull
	private final LookupDataSource textLineScopeLookup;

	@Builder
	private DocTextLinesView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final DocTextLinesRows rows,
			@NonNull final DocTextLineDocumentRef documentRef,
			@NonNull final LookupDataSource textLineScopeLookup,
			@Nullable final List<RelatedProcessDescriptor> processes)
	{
		super(viewId, description, rows, NullDocumentFilterDescriptorsProvider.instance);
		this.documentRef = documentRef;
		this.textLineScopeLookup = textLineScopeLookup;
		this.processes = processes != null ? ImmutableList.copyOf(processes) : ImmutableList.of();
	}

	/**
	 * Supplies the values the frontend offers when the user opens a text row's scope cell. The scope column is
	 * declared {@code editor = ViewEditorRenderMode.ALWAYS}, so {@code List/List.js} issues a
	 * {@code .../edit/textLineScope/dropdown} GET the moment that cell is opened, and
	 * {@code ViewRowEditRestController} routes it here -- without this override the column is not editable at
	 * all, because {@link IEditableView}'s default answers every such request with
	 * {@link UnsupportedOperationException} (HTTP 500).
	 * <p>
	 * The list is short and fixed (it is a reference list, not a searchable table), so it is returned whole,
	 * the same way {@code PricingConditionsRowLookups} serves its own list-backed columns. The row is not
	 * consulted: which values exist does not depend on which row is being edited -- an article row never
	 * reaches here, since its scope cell is rendered {@link de.metas.ui.web.window.descriptor.ViewEditorRenderMode#NEVER}.
	 * <p>
	 * There is deliberately no {@code getFieldTypeahead} counterpart: a {@code List} widget only ever requests
	 * {@code /dropdown} ({@code frontend/src/components/widget/List/List.js}); {@code /typeahead} is requested
	 * by the {@code Lookup} widget ({@code RawLookup.js}), which this view has no editable column of.
	 */
	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		if (!DocTextLinesRow.FIELD_TextLineScope.equals(fieldName))
		{
			throw new AdempiereException("Field " + fieldName + " does not exist or it's not a lookup field");
		}

		return textLineScopeLookup.findEntities(Evaluatees.empty()).getValues();
	}

	@Override
	public ImmutableList<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
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
	 * Reports what THIS view's ordering says, which is the ordering as of the last write it made -- another
	 * open view of the same order may have moved on since. {@link #insertRowAbove} does not go through here
	 * for that reason: it re-derives the ordering from the database inside its own write and computes the
	 * stored position against that. So this is a question to ask about the view, never the basis for a write.
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
	 * {@link DocTextLinesRows#insertRowAbove(DocumentId, String)} for the placement rule and the atomicity it
	 * provides -- and notifies the frontend to reload, the same way
	 * {@code ProductsProposalView#addOrUpdateRows} does after widening its own rows data. This is the one entry
	 * point an insert-above quick-action process needs; it never touches {@code DocTextLineRepository} itself.
	 */
	public void insertRowAbove(@Nullable final DocumentId referenceRowId, @NonNull final String textLine)
	{
		getRowsData().insertRowAbove(referenceRowId, textLine);
		invalidateAll();
	}

	/** {@code true} when {@code rowId} has a neighbouring row (of either kind) to exchange with in the given direction -- the move-precondition seam, mirroring {@link #getInsertAbovePositions}. */
	public boolean hasNeighbor(@NonNull final DocumentId rowId, final boolean towardStart)
	{
		return getRowsData().hasNeighbor(rowId, towardStart);
	}

	/**
	 * Exchanges {@code rowId} with the row immediately before/after it in the merged order -- see
	 * {@link DocTextLinesRows#moveRow(DocumentId, boolean)} for the two persistence shapes (article neighbour
	 * vs. text neighbour) and the concurrency guard -- and notifies the frontend to reload.
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
