package de.metas.ui.web.invoice.match_receipt_costs;

import com.google.common.collect.ImmutableList;
import de.metas.order.costs.OrderCostService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.compiere.SpringContextHolder;

public abstract class ReceiptCostsViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	protected final OrderCostService orderCostService = SpringContextHolder.instance.getBean(OrderCostService.class);

	@Override
	protected abstract ProcessPreconditionsResolution checkPreconditionsApplicable();

	@Override
	protected final ReceiptCostsView getView()
	{
		return (ReceiptCostsView)super.getView();
	}

	protected final ImmutableList<ReceiptCostRow> getSelectedRows()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		return getView().streamByIds(rowIds).collect(ImmutableList.toImmutableList());
	}

}
