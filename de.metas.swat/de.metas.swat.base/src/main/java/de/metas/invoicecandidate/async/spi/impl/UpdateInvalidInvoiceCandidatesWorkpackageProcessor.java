package de.metas.invoicecandidate.async.spi.impl;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.DB;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;

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

/**
 * Workpackage used to update all invalid {@link I_C_Invoice_Candidate}s.
 *
 * To schedule an invoice candidates update please use {@link #schedule(Properties, String)}.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class UpdateInvalidInvoiceCandidatesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	/**
	 * Schedule a new "update invalid invoice candidates" run.
	 *
	 * NOTE: the workpackages are not created right away, but the models are collected per database transaction and a workpackage is enqueued when the transaction is committed.
	 * 
	 * @param ctx context
	 * @param trxName optional transaction name. In case is provided, the workpackage will be marked as ready for processing when given transaction is committed.
	 */
	public static final void schedule(final Properties ctx, final String trxName)
	{
		final IContextAware request = new PlainContextAware(ctx, trxName);
		SCHEDULER.schedule(request);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<IContextAware> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<IContextAware>(
			UpdateInvalidInvoiceCandidatesWorkpackageProcessor.class)
	{

		@Override
		protected Properties extractCtxFromItem(final IContextAware item)
		{
			return item.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final IContextAware item)
		{
			return item.getTrxName();
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final IContextAware item)
		{
			return null;
		}

		@Override
		protected boolean isEnqueueWorkpackageWhenNoModelsEnqueued()
		{
			return true;
		};
	};

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final int adClientId = workpackage.getAD_Client_ID();
		Check.assume(adClientId > 0, "No point in calling this process with AD_Client_ID=0");
		final int adPInstanceId = DB.getNextID(ctx, I_AD_PInstance.Table_Name, ITrx.TRXNAME_None);

		final IInvoiceCandBL service = Services.get(IInvoiceCandBL.class);
		try
		{
			// make sure that the code in the thread knows..
			// this avoids the effort of invalidating candidates over and over by different model validators etc
			service.setUpdateProcessInProgress(true);

			service.updateInvalid()
					.setContext(ctx, localTrxName)
					.setManagedTrx(false)
					.setLoggable(getLoggable())
					.setLockedBy(ILock.NULL)
					.updateAll(adPInstanceId);
		}
		finally
		{
			service.setUpdateProcessInProgress(false);
		}

		return Result.SUCCESS;
	}

}
