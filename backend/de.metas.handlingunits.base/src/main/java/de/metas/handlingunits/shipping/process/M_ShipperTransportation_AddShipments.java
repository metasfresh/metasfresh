package de.metas.handlingunits.shipping.process;


import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.shipping.InOutToTransportationOrderService;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;

import java.util.List;

public class M_ShipperTransportation_AddShipments extends JavaProcess implements IProcessPrecondition
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final ShipperTransportationDocSubTypeGuard docSubTypeGuard = SpringContextHolder.instance.getBean(ShipperTransportationDocSubTypeGuard.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{

		final List<I_M_ShipperTransportation> selectedModels = context.getSelectedModels(I_M_ShipperTransportation.class);
		if (selectedModels.size() == 1)
		{
			final I_M_ShipperTransportation shipperTransportation = selectedModels.get(0);
			return ProcessPreconditionsResolution.firstRejectOrElseAccept(
					() -> ProcessPreconditionsResolution.acceptIf(!shipperTransportation.isProcessed()),
					() -> docSubTypeGuard.rejectIfDeliveryInstruction(shipperTransportation));
		}

		return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ShipperTransportation shipperTransportation = shipperTransportationDAO.getById(ShipperTransportationId.ofRepoId(getRecord_ID()));

		final ImmutableList<InOutId> inOutIds = inOutDAO.retrieveShipmentsWithoutShipperTransportation(shipperTransportation.getDateDoc());

		final InOutToTransportationOrderService service = SpringContextHolder.instance.getBean(InOutToTransportationOrderService.class);
		service.addShipmentsToTransportationOrder(ShipperTransportationId.ofRepoId(getRecord_ID()), inOutIds);

		return "OK";
	}

}
