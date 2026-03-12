package de.metas.order.process;

import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.inout.InOutFromOrderProducer;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

import java.time.LocalDate;

public class C_Order_GenerateShipment_Proforma extends JavaProcess implements IProcessPrecondition
{
	private static final String PARAM_MovementDate = "MovementDate";
	@Param(parameterName = PARAM_MovementDate, mandatory = true)
	private LocalDate p_MovementDate;

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		return newProducer().checkCanCreateShipment(context.getSingleSelectedRecordId(OrderId.class));
	}

	@Override
	protected String doIt()
	{
		newProducer().createShipment(OrderId.ofRepoId(getRecord_ID()), p_MovementDate);
		return MSG_OK;
	}

	private InOutFromOrderProducer newProducer()
	{
		return orderBL.newInOutFromOrderProducer();
	}
}
