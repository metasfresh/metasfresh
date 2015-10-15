/**
 *
 */
package de.metas.invoicecandidate.process;

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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import de.metas.invoicecandidate.api.IInvoiceCandBL;

/**
 * @author ts
 *
 */
public class C_Invoice_Candidate_Create_Missing extends SvrProcess
{
	@Override
	protected void prepare()
	{
		// nothing to do
	}

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();
		Check.assume(Env.getAD_Client_ID(ctx) > 0, "No point in calling this process with AD_Client_ID=0");
		final String trxName = ITrx.TRXNAME_None; // AD_Instance shall always be loaded out of trx

		final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(ctx, getAD_PInstance_ID(), I_AD_PInstance.class, trxName);

		final IInvoiceCandBL service = Services.get(IInvoiceCandBL.class);
		try
		{
			// make sure that the code in the thread knows..
			// this avoids the effort of invalidating candidates over and over by different model validators etc
			service.setUpdateProcessInProgress(true);
			service.createMissingCandidates(adPInstance, trxName);
		}
		finally
		{
			service.setUpdateProcessInProgress(false);
		}
		return "@Success@";
	}
}
