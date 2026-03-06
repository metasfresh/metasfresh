package de.metas.ui.web.invoice.match_inout_costs;

import com.google.common.collect.ImmutableList;
import de.metas.order.costs.OrderCostService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.compiere.SpringContextHolder;

public abstract class InOutCostsViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	protected final OrderCostService orderCostService = SpringContextHolder.instance.getBean(OrderCostService.class);

	@Override
	protected abstract ProcessPreconditionsResolution checkPreconditionsApplicable();

	@Override
	protected final InOutCostsView getView()
	{
		return (InOutCostsView)super.getView();
	}

	protected final ImmutableList<InOutCostRow> getSelectedRows()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		return getView().streamByIds(rowIds).collect(ImmutableList.toImmutableList());
	}

}
