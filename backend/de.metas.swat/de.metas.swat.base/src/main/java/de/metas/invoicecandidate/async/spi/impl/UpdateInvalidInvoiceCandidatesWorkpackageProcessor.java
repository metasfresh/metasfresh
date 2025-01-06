package de.metas.invoicecandidate.async.spi.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerRequest;
import de.metas.invoicecandidate.api.impl.InvoiceCandUpdateSchedulerRequest;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;

import java.util.Properties;

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
 * Workpackage processor used to update all invalid {@link I_C_Invoice_Candidate}s.<br>
 * Important notes:
 * <ul>
 * <li>to schedule an invoice candidates update please use {@link #schedule(IInvoiceCandUpdateSchedulerRequest)}.
 * <li>you can set the maximum number of invalid ICs to update per run, by using <code>AD_Sysconfig</code> {@value #SYSCONFIG_MaxInvoiceCandidatesToUpdate}. If there are more invalid ICs than this
 * specified maximum, then the work package processor will schedule another workpackage for the remainder.
 * </ul>
 */
public class UpdateInvalidInvoiceCandidatesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	/**
	 * Schedule a new "update invalid invoice candidates" run.
	 * <p>
	 * NOTE: the workpackages are not created right away, but the models are collected per database transaction and a workpackage is enqueued when the transaction is committed.
	 */
	public static void schedule(@NonNull final IInvoiceCandUpdateSchedulerRequest request)
	{
		SCHEDULER.schedule(request);
	}

	private static final UpdateInvalidInvoiceCandidatesWorkpackageProcessorScheduler //
			SCHEDULER = new UpdateInvalidInvoiceCandidatesWorkpackageProcessorScheduler(true/*createOneWorkpackagePerAsyncBatch*/);

	private static final String SYSCONFIG_MaxInvoiceCandidatesToUpdate = "de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor.MaxInvoiceCandidatesToUpdate";

	private static final int DEFAULT_MaxInvoiceCandidatesToUpdate = 500;

	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@Override
	public final boolean isRunInTransaction()
	{
		return false; // run out of transaction
	}

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		trxManager.assertTrxNameNull(localTrxName);

		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final int adClientId = workpackage.getAD_Client_ID();
		Check.assume(adClientId > 0, "No point in calling this process with AD_Client_ID=0");

		//
		// Get parameters
		final int maxInvoiceCandidatesToUpdate = getMaxInvoiceCandidatesToUpdate();

		//
		// Update invalid ICs
		invoiceCandBL.updateInvalid()
				.setContext(ctx, localTrxName)
				// Only those which are not locked at all
				.setLockedBy(ILock.NULL)
				.setTaggedWithNoTag()
				.setLimit(maxInvoiceCandidatesToUpdate)
				.update();

		//
		// If we updated just a limited set of invoice candidates,
		// then create a new workpackage to update the rest of them.
		if (maxInvoiceCandidatesToUpdate > 0)
		{
			final int countRemaining = invoiceCandDAO.tagToRecompute()
					.setContext(ctx, localTrxName)
					.setLockedBy(ILock.NULL)
					.setTaggedWithNoTag()
					.countToBeTagged();

			if (countRemaining > 0)
			{
				final IInvoiceCandUpdateSchedulerRequest request = InvoiceCandUpdateSchedulerRequest.of(ctx, localTrxName);
				schedule(request);

				Loggables.addLog("Scheduled another workpackage for {} remaining recompute records", countRemaining);
			}
		}
		return Result.SUCCESS;
	}

	private int getMaxInvoiceCandidatesToUpdate()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_MaxInvoiceCandidatesToUpdate, DEFAULT_MaxInvoiceCandidatesToUpdate);
	}

}
