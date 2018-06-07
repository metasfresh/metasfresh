/**
 *
 */
package de.metas.letter.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.X_C_Async_Batch;
import de.metas.letter.LetterConstants;
import de.metas.letter.process.C_Letter_PrintAutomatically;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;

/*
 * #%L
 * marketing-serialleter
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Interceptor(I_C_Async_Batch.class)
@Component
public class C_Async_Batch
{
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Async_Batch.COLUMNNAME_Processed)
	public void print(final I_C_Async_Batch asyncBatch)
	{

		if (asyncBatch.isProcessed()
				&& LetterConstants.C_Async_Batch_InternalName_CreateLettersAsync.equals(asyncBatch.getC_Async_Batch_Type().getInternalName()))
		{
			runPrintingProcess(asyncBatch);
		}
	}

	}

	private void runPrintingProcess(final I_C_Async_Batch asyncBatch)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final int processId = adProcessDAO.retrieveProcessIdByClass(C_Letter_PrintAutomatically.class);
		Check.assume(processId > 0, "No process found");

		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final ITrx trx = Services.get(ITrxManager.class).getTrx(InterfaceWrapperHelper.getTrxName(asyncBatch));
		Check.assume(trx != null, "trx not null");

		final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(ctx, processId, 0, 0);
		pinstance.setIsProcessing(true);
		InterfaceWrapperHelper.save(pinstance);

		final List<ProcessInfoParameter> piParams = new ArrayList<>();
		piParams.add(ProcessInfoParameter.ofValueObject(X_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID, asyncBatch.getC_Async_Batch_ID()));
		Services.get(IADPInstanceDAO.class).saveParameterToDB(pinstance.getAD_PInstance_ID(), piParams);

		ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(processId)
				.setAD_PInstance(pinstance)
				.setAD_User_ID(asyncBatch.getCreatedBy()) // we need this to set in here in order to be user as userToPrint
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.executeSync();

	}

}
