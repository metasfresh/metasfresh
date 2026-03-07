package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.ui.web.order.sales.hu.reservation.process.MaterialCockpitSalesOrderLine;
import de.metas.ui.web.order.sales.hu.reservation.process.MaterialCockpitViewContext;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;
import lombok.NonNull;

public abstract class MaterialCockpitV2BasedProcess extends ViewBasedProcessTemplate
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private MaterialCockpitViewContext _viewContext;
	private MaterialCockpitSalesOrderLine _salesOrderLine;

	@NonNull
	protected final MaterialCockpitSalesOrderLine getSalesOrderLine()
	{
		if (_salesOrderLine == null)
		{
			_salesOrderLine = retrieveSalesOrderLine();
		}
		return _salesOrderLine;
	}

	private MaterialCockpitSalesOrderLine retrieveSalesOrderLine()
	{
		final OrderLineId salesOrderLineId = getSalesOrderLineId();
		I_C_OrderLine orderLineRecord = orderDAO.getOrderLineById(salesOrderLineId, I_C_OrderLine.class);
		return MaterialCockpitSalesOrderLine.of(orderLineRecord);
	}

	@NonNull
	protected final OrderLineId getSalesOrderLineId()
	{
		return getMaterialCockpitViewContext().getSalesOrderLineId();
	}

	protected final MaterialCockpitViewContext getMaterialCockpitViewContext()
	{
		if (_viewContext == null)
		{
			_viewContext = MaterialCockpitViewContext.of(getView());
		}
		return _viewContext;
	}

	protected final void invalidateViewSelection()
	{
		getView().invalidateSelection();
	}

	protected final MaterialCockpitV2RowVO getSingleSelectedMaterialCockpitRow()
	{
		return MaterialCockpitV2RowVO.ofViewRow(getSingleSelectedRow());
	}

	protected final MaterialCockpitV2RowVO getSingleSelectedMaterialCockpitRowOrNull()
	{
		return isSingleSelectedRow()
				? getSingleSelectedMaterialCockpitRow()
				: null;
	}

}
