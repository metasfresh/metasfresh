package de.metas.inout.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.process.DocAction;

import de.metas.document.engine.IDocActionBL;
import de.metas.inout.api.IInOutInvoiceCandidateBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.process.ISvrProcessPrecondition;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class M_InOut_ApproveForInvoicing extends SvrProcess implements ISvrProcessPrecondition
{

	private int p_M_InOut_ID = 0;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.error("Unknown Parameter: " + name);
		}
		p_M_InOut_ID = getRecord_ID();

	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.create(getCtx(), p_M_InOut_ID, I_M_InOut.class, getTrxName());

		final IInOutInvoiceCandidateBL inOutBL = Services.get(IInOutInvoiceCandidateBL.class);

		final boolean isApprovedForInvoicing = inOutBL.isApproveInOutForInvoicing(inOut);

		// set the flag on true if the inout is active and complete/closed
		inOut.setIsInOutApprovedForInvoicing(isApprovedForInvoicing);

		InterfaceWrapperHelper.save(inOut);

		// set the linked rechnungsdispos as inOutApprovedForInvoicing and ApprovedForInvoicing
		inOutBL.approveForInvoicingLinkedInvoiceCandidates(inOut);

		return MSG_OK;
	}

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		// Make this process only available for inout entries that are active and have the status Completed or Closed

		if (I_M_InOut.Table_Name.equals(context.getTableName()))
		{
			final I_M_InOut inOut = context.getModel(I_M_InOut.class);
			return docActionBL.isStatusOneOf(inOut.getDocStatus(),
					DocAction.STATUS_Completed, DocAction.STATUS_Closed);
		}
		return false;
	}

}
