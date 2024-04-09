package de.metas.ui.web.split_shipment;

import de.metas.inout.ShipmentScheduleId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.compiere.SpringContextHolder;

public class SplitShipmentView_Launcher extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		if (rowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		else if (!rowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		else
		{
			return ProcessPreconditionsResolution.accept();
		}
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
		return getSelectedRowIds().getSingleDocumentId().toId(ShipmentScheduleId::ofRepoId);
	}

}
