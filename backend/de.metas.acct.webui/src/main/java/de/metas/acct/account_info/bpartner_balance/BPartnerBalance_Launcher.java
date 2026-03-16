package de.metas.acct.account_info.bpartner_balance;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class BPartnerBalance_Launcher extends JavaProcess
{
	@NonNull private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	protected String doIt()
	{
		final ViewId viewId = viewsFactory.createView(BPartnerBalanceViewFactory.createViewRequest())
				.getViewId();

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.NewBrowserTab)
				.build());

		return MSG_OK;
	}
}
