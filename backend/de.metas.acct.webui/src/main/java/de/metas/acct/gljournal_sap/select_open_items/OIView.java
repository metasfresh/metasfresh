package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableList;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.List;

public class OIView extends AbstractCustomView<OIRow> implements IEditableView
{
	public static OIView cast(IView view) {return (OIView)view;}

	@NonNull private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;
	@Getter @NonNull private final SAPGLJournalId sapglJournalId;

	@Builder
	private OIView(
			final @NonNull ViewId viewId,
			final @NonNull OIViewData rowsData,
			final @NonNull DocumentFilterDescriptor filterDescriptor,
			final @NonNull SAPGLJournalId sapglJournalId,
			final @NonNull @Singular ImmutableList<RelatedProcessDescriptor> relatedProcesses)
	{
		super(viewId, null, rowsData, ImmutableDocumentFilterDescriptorsProvider.of(filterDescriptor));
		this.sapglJournalId = sapglJournalId;
		this.relatedProcesses = relatedProcesses;
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) {return null;}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors() {return relatedProcesses;}

	@Override
	public DocumentFilterList getFilters() {return DocumentFilterList.ofNullable(getRowsData().getFilter());}

	@Override
	protected OIViewData getRowsData() {return OIViewData.cast(super.getRowsData());}

	@Override
	public ViewHeaderProperties getHeaderProperties() {return getRowsData().getHeaderProperties();}

	@Override
	public LookupValuesPage getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query) {throw new UnsupportedOperationException();}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName) {throw new UnsupportedOperationException();}

	public void markRowsAsSelected(final DocumentIdsSelection rowIds)
	{
		getRowsData().markRowsAsSelected(rowIds);
		ViewChangesCollector.getCurrentOrAutoflush().collectRowsAndHeaderPropertiesChanged(this, rowIds);
	}

	public boolean hasSelectedRows()
	{
		return getRowsData().hasSelectedRows();
	}

	public void clearUserInputAndInvalidateAll()
	{
		getRowsData().clearUserInput();
		invalidateAll();
	}

	public OIRowUserInputParts getUserInput() {return getRowsData().getUserInput();}

}
