package de.metas.inout.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.document.engine.IDocActionBL;
import de.metas.inout.api.IInOutInvoiceCandidateBL;
import de.metas.inout.model.I_M_InOut;

public class M_InOut_ApproveForInvoicing extends SvrProcess implements ISvrProcessPrecondition
{

	private int p_M_InOut_ID = 0;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
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
