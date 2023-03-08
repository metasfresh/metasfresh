package de.metas.ui.web.invoice.match_receipt_costs;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceLineId;
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

public class ReceiptCostsView extends AbstractCustomView<ReceiptCostRow>
{
	@NonNull private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;

	@Builder
	private ReceiptCostsView(
			final @NonNull ViewId viewId,
			final @NonNull ReceiptCostsViewData rowsData,
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
	protected ReceiptCostsViewData getRowsData() {return (ReceiptCostsViewData)super.getRowsData();}

	public InvoiceLineId getInvoiceLineId() {return getRowsData().getInvoiceLineId();}

	@Override
	public ViewHeaderProperties getHeaderProperties() {return getRowsData().getHeaderProperties();}
}
