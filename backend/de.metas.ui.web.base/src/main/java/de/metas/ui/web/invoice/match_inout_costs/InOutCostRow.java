package de.metas.ui.web.invoice.match_inout_costs;

import de.metas.order.costs.inout.InOutCostId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;

public class InOutCostRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_BPartner_ID")
	private final LookupValue bpartner;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_OrderPO_ID")
	private final LookupValue purchaseOrder;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_InOut_ID")
	private final LookupValue inout;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_Cost_Type_ID")
	private final LookupValue costType;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Text, captionKey = "C_Currency_ID")
	private final String currency;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Amount, captionKey = "CostAmount")
	private final BigDecimal costAmountToInvoice;

	private final ViewRowFieldNameAndJsonValuesHolder<InOutCostRow> values;
	private final DocumentId rowId;
	@Getter private final InOutCostId inoutCostId;

	@Builder
	private InOutCostRow(
			final InOutCostId inoutCostId,
			final LookupValue bpartner,
			final LookupValue purchaseOrder,
			final LookupValue inout,
			final LookupValue costType,
			final String currency,
			final BigDecimal costAmountToInvoice)
	{
		this.bpartner = bpartner;
		this.purchaseOrder = purchaseOrder;
		this.inout = inout;
		this.costType = costType;
		this.currency = currency;
		this.costAmountToInvoice = costAmountToInvoice;

		values = ViewRowFieldNameAndJsonValuesHolder.newInstance(InOutCostRow.class);
		this.rowId = DocumentId.of(inoutCostId);
		this.inoutCostId = inoutCostId;
	}

	@Override
	public DocumentId getId() {return rowId;}

	@Override
	public boolean isProcessed() {return true;}

	@Nullable
	@Override
	public DocumentPath getDocumentPath() {return null;}

	@Override
	public Set<String> getFieldNames() {return values.getFieldNames();}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues() {return values.get(this);}
}
