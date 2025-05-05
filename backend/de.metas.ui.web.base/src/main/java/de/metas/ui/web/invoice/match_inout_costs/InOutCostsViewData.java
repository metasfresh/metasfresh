package de.metas.ui.web.invoice.match_inout_costs;

import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.lang.SOTrx;
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

public class InOutCostsViewData implements IRowsData<InOutCostRow>
{
	//
	// services
	@NonNull private final InOutCostsViewDataService viewDataService;

	//
	// parameters
	@Getter @NonNull final SOTrx soTrx;
	@Getter @NonNull private final InvoiceAndLineId invoiceAndLineId;
	@Getter @Nullable private final DocumentFilter filter;

	//
	// state
	@NonNull private final SynchronizedMutable<ViewHeaderProperties> headerPropertiesHolder = SynchronizedMutable.empty();
	@NonNull private final SynchronizedRowsIndexHolder<InOutCostRow> rowsHolder;

	@Builder
	private InOutCostsViewData(
			@NonNull final InOutCostsViewDataService viewDataService,
			@NonNull final SOTrx soTrx,
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@Nullable final DocumentFilter filter)
	{
		this.viewDataService = viewDataService;
		this.invoiceAndLineId = invoiceAndLineId;
		this.filter = filter;
		this.soTrx = soTrx;
		this.rowsHolder = SynchronizedRowsIndexHolder.of(viewDataService.retrieveRows(soTrx, filter));
	}

	@Override
	public Map<DocumentId, InOutCostRow> getDocumentId2TopLevelRows()
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
		rowsHolder.setRows(viewDataService.retrieveRows(soTrx, filter));
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
								.value(TranslatableStrings.amount(viewDataService.getInvoiceLineOpenAmount(invoiceAndLineId)))
								.build())
						.build())
				.build();
	}

}
