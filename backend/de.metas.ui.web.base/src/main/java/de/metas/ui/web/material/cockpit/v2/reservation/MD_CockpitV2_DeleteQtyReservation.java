package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MD_CockpitV2_DeleteQtyReservation
		extends MaterialCockpitV2BasedProcess
		implements IProcessPrecondition
{
	@Autowired private QtyReservationService qtyReservationService;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final OrderLineId orderLineId = getSalesOrderLineIdOrNull();
		if (orderLineId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No sales order line context");
		}

		if (!qtyReservationService.getReservedQtyTU(orderLineId).isPositive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No reservations to delete");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final OrderLineId orderLineId = getSalesOrderLineIdOrNull();
		if (orderLineId == null)
		{
			throw new AdempiereException("No sales order line context");
		}

		qtyReservationService.deleteReservationsForOrderLine(orderLineId);

		invalidateView();

		return MSG_OK;
	}
}
