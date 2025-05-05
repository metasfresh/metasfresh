package de.metas.inout.process;

import de.metas.document.engine.DocStatus;
import de.metas.inout.api.IInOutInvoiceCandidateBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;

public class M_InOut_ApproveForInvoicing extends JavaProcess implements IProcessPrecondition
{

	private int p_M_InOut_ID = 0;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (ProcessInfoParameter element : para)
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
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
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		// Make this process only available for inout entries that are active and have the status Completed or Closed
		if (I_M_InOut.Table_Name.equals(context.getTableName()))
		{
			final I_M_InOut inout = context.getSelectedModel(I_M_InOut.class);
			final DocStatus docStatus = DocStatus.ofCode(inout.getDocStatus());
			return ProcessPreconditionsResolution.acceptIf(docStatus.isCompletedOrClosed());
		}
		else
		{
			return ProcessPreconditionsResolution.reject();
		}
	}

}
