package de.metas.acct.acct_simulation;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_Invoice_AcctSimulation_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ViewId viewId = viewsFactory.createView(AcctSimulationViewFactory.createViewRequest(getRecordRef())).getViewId();
		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());
		return MSG_OK;
	}

}
