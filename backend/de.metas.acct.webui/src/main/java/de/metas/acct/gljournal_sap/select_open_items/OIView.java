package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableList;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.List;

public class OIView extends AbstractCustomView<OIRow>
{
	@NonNull private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;
	@NonNull private final SAPGLJournalId sapglJournalId;

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
	protected OIViewData getRowsData() {return (OIViewData)super.getRowsData();}
}
