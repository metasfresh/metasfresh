package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.inoutcandidate.qty_reservation.MaterialCockpitV2RowVO;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.ui.web.material.cockpit.v2.MaterialCockpitV2Service;
import de.metas.ui.web.order.sales.hu.reservation.process.MaterialCockpitSalesOrderLine;
import de.metas.ui.web.order.sales.hu.reservation.process.MaterialCockpitViewContext;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public abstract class MaterialCockpitV2BasedProcess extends ViewBasedProcessTemplate
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull @Autowired private MaterialCockpitV2Service materialCockpitV2Service;

	private MaterialCockpitViewContext _viewContext;
	private MaterialCockpitSalesOrderLine _salesOrderLine;
	private MaterialCockpitV2RowVO _singleSelectedRow;

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
		final OrderAndLineId salesOrderLineId = getSalesOrderAndLineId();
		I_C_OrderLine orderLineRecord = orderDAO.getOrderLineById(salesOrderLineId, I_C_OrderLine.class);
		return MaterialCockpitSalesOrderLine.of(orderLineRecord);
	}

	@NonNull
	protected final OrderAndLineId getSalesOrderAndLineId()
	{
		return getMaterialCockpitViewContext().getSalesOrderAndLineId();
	}

	protected final MaterialCockpitViewContext getMaterialCockpitViewContext()
	{
		if (_viewContext == null)
		{
			_viewContext = MaterialCockpitViewContext.of(getView());
		}
		return _viewContext;
	}

	protected final void recreateSelection()
	{
		materialCockpitV2Service.recreateSourceSelection(getMaterialCockpitViewContext());
		invalidateViewSelection();
	}

	protected final void invalidateViewSelection()
	{
		getView().invalidateSelection();
	}

	protected final MaterialCockpitV2RowVO getSingleSelectedMaterialCockpitRow()
	{
		if (_singleSelectedRow == null)
		{
			_singleSelectedRow = MaterialCockpitV2RowVOs.ofViewRow(getSingleSelectedRow());
		}
		return _singleSelectedRow;
	}
}
