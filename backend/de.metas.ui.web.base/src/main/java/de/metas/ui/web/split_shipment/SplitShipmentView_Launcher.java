package de.metas.ui.web.split_shipment;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class SplitShipmentView_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ShipmentScheduleId shipmentScheduleId = getSelectedShipmentScheduleId();
		final ViewId viewId = viewsFactory.createView(SplitShipmentViewFactory.createViewRequest(shipmentScheduleId))
				.getViewId();

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	private ShipmentScheduleId getSelectedShipmentScheduleId()
	{
		if (I_M_ShipmentSchedule.Table_Name.equals(getTableName()))
		{
			return ShipmentScheduleId.ofRepoId(getRecord_ID());
		}
		else
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

}
