package de.metas.contracts.flatrate.process;

import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;

import de.metas.contracts.subscription.ISubscriptionDAO;
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
		final I_C_Order existentOrder = InterfaceWrapperHelper.getPO(getRecord(I_C_Order.class));
		final I_C_Order newOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, existentOrder);
		
		final PO to = InterfaceWrapperHelper.getPO(newOrder);
		final PO from = InterfaceWrapperHelper.getPO(existentOrder);
				
		PO.copyValues(from, to, true);

		InterfaceWrapperHelper.save(newOrder);
		
		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(I_C_Order.Table_Name);
		childCRS.setParentPO(to);
		childCRS.setBase(true);
		childCRS.copyRecord(from, get_TrxName());

		newOrder.setDocStatus(X_C_Order.DOCSTATUS_Drafted);
		newOrder.setDocAction(X_C_Order.DOCACTION_Complete);
		
		InterfaceWrapperHelper.save(newOrder);

		// link the existent order to the new one
		existentOrder.setRef_FollowupOrder_ID(newOrder.getC_Order_ID());
		InterfaceWrapperHelper.save(existentOrder);

		return newOrder.getDocumentNo();
		
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
		
		if (!subscriptionDAO.isContractSalesOrder( OrderId.ofRepoId(getRecord_ID())))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on contract order");
		}
		
		return ProcessPreconditionsResolution.accept();
	}

	
}
