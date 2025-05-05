package de.metas.ui.web.invoice.match_inout_costs;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.lang.SOTrx;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.List;

public class InOutCostsView extends AbstractCustomView<InOutCostRow>
{
	@NonNull private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;

	@Builder
	private InOutCostsView(
			final @NonNull ViewId viewId,
			final @NonNull InOutCostsViewData rowsData,
			final @NonNull DocumentFilterDescriptor filterDescriptor,
			final @NonNull @Singular ImmutableList<RelatedProcessDescriptor> relatedProcesses)
	{
		super(viewId, null, rowsData, ImmutableDocumentFilterDescriptorsProvider.of(filterDescriptor));
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
	protected InOutCostsViewData getRowsData() {return (InOutCostsViewData)super.getRowsData();}

	public SOTrx getSoTrx() {return getRowsData().getSoTrx();}

	public InvoiceAndLineId getInvoiceLineId() {return getRowsData().getInvoiceAndLineId();}

	@Override
	public ViewHeaderProperties getHeaderProperties() {return getRowsData().getHeaderProperties();}
}
