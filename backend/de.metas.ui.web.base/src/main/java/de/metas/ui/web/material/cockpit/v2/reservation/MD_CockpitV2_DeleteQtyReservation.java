package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
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
		final OrderLineId orderLineId = getSalesOrderLineId();
		if (!qtyReservationService.hasReservation(orderLineId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No reservations to delete");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		qtyReservationService.deleteReservationsForOrderLine(getSalesOrderLineId());

		invalidateView();

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success) {return;}
		invalidateViewSelection();
	}
}
