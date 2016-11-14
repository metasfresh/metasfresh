package de.metas.payment.esr.process;

/*
 * #%L
 * de.metas.payment.esr
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


import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.process.SvrProcess;

import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_ESR_Import;

public class ESR_Complete_Process extends SvrProcess
{

	private int p_ESR_Import_ID;

	@Override
	protected void prepare()
	{
		if (I_ESR_Import.Table_Name.equals(getTableName()))
		{
			p_ESR_Import_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_ESR_Import_ID <= 0)
		{
			throw new FillMandatoryException(I_ESR_Import.COLUMNNAME_ESR_Import_ID);
		}

		final I_ESR_Import esrImport = InterfaceWrapperHelper.create(getCtx(), p_ESR_Import_ID, I_ESR_Import.class, get_TrxName());

		// 04582: making sure we will use the trxName of this process in our business logic
		Check.assume(get_TrxName().equals(InterfaceWrapperHelper.getTrxName(esrImport)), "TrxName {} of {} is equal to the process-TrxName {}",
				InterfaceWrapperHelper.getTrxName(esrImport),
				esrImport,
				get_TrxName());
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.NESTED, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);

		Check.errorUnless(esrImport.isValid(), "The document can not be processed, since it is not valid.");

		final String description = getProcessInfo().getTitle() + " #" + getAD_PInstance_ID();
		Services.get(IESRImportBL.class).complete(esrImport, description, trxRunConfig);
		
		return "";
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (success)
		{
			final I_ESR_Import esrImport = InterfaceWrapperHelper.create(getCtx(), p_ESR_Import_ID, I_ESR_Import.class, get_TrxName());
			final boolean processed = Services.get(IESRImportBL.class).isProcessed(esrImport);
			if (processed)
			{
				getResult().addSummary(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@ESR_Complete_Process_postProcess@"));
			}
			else
			{
				getResult().addSummary(Services.get(IMsgBL.class).parseTranslation(getCtx(), "@" + ESRConstants.ERR_ESR_LINE_WITH_NO_PAYMENT_ACTION + "@"));
			}

		}
	}
}
