package de.metas.ordercandidate.process;

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


import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.TrxRunnable2;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;

public class ProcessOLCands extends SvrProcess
{
	private int olCandProcessorId;

	@Override
	protected String doIt() throws Exception
	{
		//
		// Services
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

		Check.assume(olCandProcessorId > 0, "olCandProcessorId > 0");

		final Throwable[] error = new Throwable[1];

		trxManager.run(get_TrxName(), new TrxRunnable2()
		{
			@Override
			public void run(final String trxName)
			{
				final I_C_OLCandProcessor olCandProcessor = InterfaceWrapperHelper.create(getCtx(), olCandProcessorId, I_C_OLCandProcessor.class, trxName);
				olCandBL.process(getCtx(), olCandProcessor, ProcessOLCands.this, trxName);
				error[0] = null;
			}

			@Override
			public boolean doCatch(final Throwable e) throws Exception
			{
				error[0] = e;

				// returning 'true' to roll back the transaction
				return true;
			}

			@Override
			public void doFinally()
			{
				// nothing to do
			}
		});
		if (error[0] != null)
		{
			addLog("@Error@: " + error[0].getMessage());
			addLog("@Rollback@");

			// returning success, because "error" would roll back the complete process-trx, including our log messages
			return error[0].getMessage();
		}
		return "@Success@";
	}

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				continue;
			}

			if (name.equals(I_C_OLCandProcessor.COLUMNNAME_C_OLCandProcessor_ID))
			{
				olCandProcessorId = ((BigDecimal)para[i].getParameter()).intValue();
			}
		}
	}
}
