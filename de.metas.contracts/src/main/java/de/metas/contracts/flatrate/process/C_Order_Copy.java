package de.metas.contracts.flatrate.process;

import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.impl.subscriptioncommands.ExtendContractOrder;
import de.metas.contracts.subscription.model.I_C_Order;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;

public class C_Order_Copy extends JavaProcess implements IProcessPrecondition
{
	private final ISubscriptionDAO subscriptionDAO = Services.get(ISubscriptionDAO.class);

	@Override
	protected String doIt()
	{
		final I_C_Order existentOrder = getRecord(I_C_Order.class);
		return ExtendContractOrder.extend(existentOrder);
	}


	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_Order.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on C_Order table");
		}
		
		if (!subscriptionDAO.isContractSalesOrder( OrderId.ofRepoId(context.getSingleSelectedRecordId())))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on contract order");
		}
		
		return ProcessPreconditionsResolution.accept();
	}

	
}
