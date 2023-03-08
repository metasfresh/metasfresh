package de.metas.ui.web.invoice.match_receipt_costs;

import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceLineId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.ViewHeaderPropertiesGroup;
import de.metas.ui.web.view.ViewHeaderProperty;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.Map;

public class ReceiptCostsViewData implements IRowsData<ReceiptCostRow>
{
	@NonNull private final ReceiptCostsViewDataService viewDataService;
	@Getter @Nullable private final DocumentFilter filter;
	@NonNull private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder = SynchronizedMutable.empty();
	@NonNull private final SynchronizedRowsIndexHolder<ReceiptCostRow> rowsHolder;
	@Getter @NonNull private final InvoiceLineId invoiceLineId;

	@Builder
	private ReceiptCostsViewData(
			@NonNull final ReceiptCostsViewDataService viewDataService,
			@NonNull final InvoiceLineId invoiceLineId,
			@Nullable final DocumentFilter filter)
	{
		this.viewDataService = viewDataService;
		this.invoiceLineId = invoiceLineId;
		this.filter = filter;
		this.rowsHolder = SynchronizedRowsIndexHolder.of(viewDataService.retrieveRows(filter));
	}

	@Override
	public Map<DocumentId, ReceiptCostRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
		headerPropertiesHolder.setValue(null);
		rowsHolder.setRows(viewDataService.retrieveRows(filter));
	}

	public ViewHeaderProperties getHeaderProperties()
	{
		return headerPropertiesHolder.computeIfNull(this::computeHeaderProperties);
	}

	private ViewHeaderProperties computeHeaderProperties()
	{
		return ViewHeaderProperties.builder()
				.group(ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.adElementOrMessage("InvoiceOpenAmt"))
								.value(TranslatableStrings.amount(viewDataService.getInvoiceLineOpenAmount(invoiceLineId)))
								.build())
						.build())
				.build();
	}

}
