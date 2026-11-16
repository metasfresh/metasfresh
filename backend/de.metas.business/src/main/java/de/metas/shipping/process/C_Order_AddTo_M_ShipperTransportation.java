package de.metas.shipping.process;

import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.shipping.process.AddOrderLinesToShipperTransportation.MAX_SELECTION_SIZE;

public class C_Order_AddTo_M_ShipperTransportation extends JavaProcess implements IProcessPrecondition
{
	public final static AdMessageKey MSG_DOCUMENT_NOT_COMPLETE = AdMessageKey.of("DocumentNotComplete");
	private final static AdMessageKey MSG_ORDER_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER = AdMessageKey.of("OrderAssignedToProcessedTransportationOrder");

	private final PurchaseOrderToShipperTransportationService orderToShipperTransportationService = SpringContextHolder.instance.getBean(PurchaseOrderToShipperTransportationService.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID)
	private ShipperTransportationId p_M_ShipperTransportation_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (context.isMoreThanAllowedSelected(MAX_SELECTION_SIZE))
		{
			return ProcessPreconditionsResolution.rejectBecauseTooManyRecordsSelected(MAX_SELECTION_SIZE);
		}
		final IQueryFilter<I_C_Order> queryFilter = context.getQueryFilter(I_C_Order.class);
		final List<I_C_Order> selectedOrders = orderBL.getByQueryFilter(queryFilter);

		if (selectedOrders.stream().anyMatch(orderBL::isRequisition))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("is purchase requisition");
		}
		if (selectedOrders.stream().anyMatch(o -> !orderBL.isCompleted(o)))
		{
			return ProcessPreconditionsResolution.reject(MSG_DOCUMENT_NOT_COMPLETE);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<OrderId> orderIds = orderBL.getByQueryFilter(getProcessInfo().getQueryFilterOrElseFalse())
				.stream()
				.map(o -> OrderId.ofRepoId(o.getC_Order_ID()))
				.collect(Collectors.toSet());

		orderToShipperTransportationService.deleteShippingPackagesForOrders(orderIds, MSG_ORDER_ASSIGNED_TO_PROCESSED_TRANSPORTATION_ORDER);

		orderToShipperTransportationService.addPurchaseOrdersToShipperTransportation(p_M_ShipperTransportation_ID, orderIds);
		return MSG_OK;

	}

}
