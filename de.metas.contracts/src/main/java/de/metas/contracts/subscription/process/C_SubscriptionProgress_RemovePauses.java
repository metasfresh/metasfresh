package de.metas.contracts.subscription.process;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.impl.SubscriptionCommand;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

public class C_SubscriptionProgress_RemovePauses
		extends JavaProcess
		implements IProcessPrecondition
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		final I_C_SubscriptionProgress subscriptionProgress = context.getSelectedModel(I_C_SubscriptionProgress.class);
		if (X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause.equals(subscriptionProgress.getContractStatus()))
		{
			return ProcessPreconditionsResolution.accept();
		}
		else if(X_C_SubscriptionProgress.EVENTTYPE_EndOfPause.equals(subscriptionProgress.getEventType()))
		{
			return ProcessPreconditionsResolution.accept();
		}
		return ProcessPreconditionsResolution.reject();
	}

	@Override
	protected String doIt()
	{
		final I_C_SubscriptionProgress subscriptionProgress = getRecord(I_C_SubscriptionProgress.class);
		SubscriptionCommand.get().removePauses(subscriptionProgress.getC_Flatrate_Term(), subscriptionProgress.getEventDate(), subscriptionProgress.getEventDate());

		return MSG_OK;
	}
}
