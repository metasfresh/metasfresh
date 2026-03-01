package de.metas.ui.web.material.cockpit.v2.reservation;

import com.google.common.collect.ImmutableMap;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Launch_HUEditor;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class MD_CockpitV2_DeleteQtyReservation
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Autowired
	private QtyReservationService qtyReservationService;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final OrderLineId orderLineId = getOrderLineIdOrNull();
		if (orderLineId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No sales order line context");
		}

		final BigDecimal reservedQtyTU = qtyReservationService.getReservedQtyTU(orderLineId);
		if (reservedQtyTU.signum() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No reservations to delete");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final OrderLineId orderLineId = getOrderLineIdOrNull();
		if (orderLineId == null)
		{
			throw new org.adempiere.exceptions.AdempiereException("No sales order line context");
		}

		qtyReservationService.deleteReservationsForOrderLine(orderLineId);

		invalidateView();

		return MSG_OK;
	}

	@Nullable
	private OrderLineId getOrderLineIdOrNull()
	{
		final ImmutableMap<String, Object> params = getView().getParameters();
		final Object value = params.get(WEBUI_C_OrderLineSO_Launch_HUEditor.VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID);
		if (value instanceof OrderLineId)
		{
			return (OrderLineId)value;
		}
		return null;
	}
}
