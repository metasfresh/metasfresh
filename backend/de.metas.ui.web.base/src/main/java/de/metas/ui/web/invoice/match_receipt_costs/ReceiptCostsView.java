package de.metas.ui.web.invoice.match_receipt_costs;

import com.google.common.collect.ImmutableList;
import de.metas.invoice.InvoiceLineId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.List;

public class ReceiptCostsView extends AbstractCustomView<ReceiptCostRow>
{
	@Getter private final InvoiceLineId invoiceLineId;
	@NonNull private final ImmutableList<RelatedProcessDescriptor> relatedProcesses;
	private final DocumentFilterList filters;

	@Builder
	private ReceiptCostsView(
			final @NonNull ViewId viewId,
			final @NonNull ReceiptCostRowsData rowsData,
			final @NonNull DocumentFilterDescriptor filterDescriptor,
			final @Nullable DocumentFilter filter,
			final @NonNull InvoiceLineId invoiceLineId,
			final @NonNull @Singular ImmutableList<RelatedProcessDescriptor> relatedProcesses)
	{
		super(viewId, null, rowsData, ImmutableDocumentFilterDescriptorsProvider.of(filterDescriptor));
		this.filters = DocumentFilterList.ofNullable(filter);
		this.invoiceLineId = invoiceLineId;
		this.relatedProcesses = relatedProcesses;

	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId) {return null;}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors() {return relatedProcesses;}

	@Override
	public DocumentFilterList getFilters() {return filters;}

	@Override
	protected ReceiptCostRowsData getRowsData() {return (ReceiptCostRowsData)super.getRowsData();}
}
