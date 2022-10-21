package de.metas.ui.web.order.sales.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

/**
 * Asks frontend to create a new Sales Order and also to open the New BPartner as modal.
 * <p>
 * NOTE: if you don't find an AD_Process record in database it's because this process is used only by some customers,
 * using their terminology, so we added only there.
 * Original branch is `hard_encoded_uat`.
 */
public class NewSalesOrderAndBPartnerAction extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	static final int SORT_NO = C_BPartner_NewSalesOrderAction.SORT_NO + 1;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return ProcessPreconditionsResolution.accept().withSortNo(SORT_NO);
	}

	@Override
	protected String doIt()
	{
		getResult().setWebuiNewRecord(BPartnerNewSalesOrderUtils.openNewSalesOrderWindowAndNewBPartnerModal());
		return MSG_OK;
	}
}
