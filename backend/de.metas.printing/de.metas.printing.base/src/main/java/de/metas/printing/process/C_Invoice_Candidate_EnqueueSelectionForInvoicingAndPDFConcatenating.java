/**
 *
 */
package de.metas.printing.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableMap;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.printing.async.spi.impl.InvoiceEnqueueingWorkpackageProcessor;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;
import de.metas.process.RunOutOfTrx;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_InvoiceCandidate_Processing;

public class C_Invoice_Candidate_EnqueueSelectionForInvoicingAndPDFConcatenating extends JavaProcess
{
	//
	// Services
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	// Parameters
	private IInvoicingParams invoicingParams;

	private int selectionCount = 0;

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		setShowProcessLogs(ShowProcessLogs.OnError);

		final IParams params = getParameterAsIParams();
		this.invoicingParams = new InvoicingParams(params);

		selectionCount = createSelection();
		if (selectionCount <= 0)
		{
			final Properties ctx = getCtx();
			throw new AdempiereException(msgBL.getMsg(ctx, IInvoiceCandidateEnqueuer.MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P));
		}

	}

	private AsyncBatchId createAsyncBatch()
	{
		return asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_InvoiceCandidate_Processing);
	}

	@Override
	protected String doIt() throws Exception
	{
		final AsyncBatchId asyncBatchId = createAsyncBatch();
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);
		asyncBatchBL.setPInstance_IDAndSave(asyncBatch, getPinstanceId());

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), InvoiceEnqueueingWorkpackageProcessor.class);
		queue.newBlock()
				.setContext(getCtx())
				.newWorkpackage()
				.setC_Async_Batch(asyncBatchBL.getAsyncBatchById(asyncBatchId))
				.parameters(invoicingParams.asMap())
				.build();

		return MSG_OK;
	}

	private int createSelection()
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = createICQueryBuilder();

		//
		// Create selection and return how many items were added
		final PInstanceId adPInstanceId = getPinstanceId();
		Check.assumeNotNull(adPInstanceId, "adPInstanceId is not null");

		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		final int selectionCount = queryBuilder
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(adPInstanceId);

		return selectionCount;
	}

	private IQueryBuilder<I_C_Invoice_Candidate> createICQueryBuilder()
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class, getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID, invoicingParams.getAD_Org_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		return queryBuilder;
	}
}
