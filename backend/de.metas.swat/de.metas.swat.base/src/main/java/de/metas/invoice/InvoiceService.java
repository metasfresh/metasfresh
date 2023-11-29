/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoice;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.service.AsyncBatchService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static org.compiere.util.Env.getCtx;

@Service
public class InvoiceService
{
	private final static Logger logger = LogManager.getLogger(InvoiceService.class);

	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private final AsyncBatchService asyncBatchService;

	public InvoiceService(@NonNull final AsyncBatchService asyncBatchService)
	{
		this.asyncBatchService = asyncBatchService;
	}

	/**
	 * @param shipmentLines the inout-lines for whose ICs the invoices shall be created
	 * @param asyncBatchId the async-batch-Id to create the workpackage(s) for and to wait for
	 */
	@NonNull
	public Set<InvoiceId> generateInvoicesFromShipmentLines(@NonNull final List<I_M_InOutLine> shipmentLines, @NonNull final AsyncBatchId asyncBatchId)
	{
		if (shipmentLines.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("generateInvoicesFromShipmentLines - Given shipmentLines list is empty; -> nothing to do");
			return ImmutableSet.of();
		}

		final Set<InvoiceCandidateId> invoiceCandidateIds = retrieveInvoiceCandsByInOutLines(shipmentLines)
				.stream()
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return generateInvoicesFromInvoiceCandidateIds(invoiceCandidateIds, asyncBatchId);
	}

	/**
	 * @param invoiceCandidateIds the Ids of the ICs to invoice
	 * @param asyncBatchId the async-batch-Id to create the workpackage(s) for and to wait for
	 */
	public ImmutableSet<InvoiceId> generateInvoicesFromInvoiceCandidateIds(
			@NonNull final Set<InvoiceCandidateId> invoiceCandidateIds,
			@NonNull final AsyncBatchId asyncBatchId)
	{
		processInvoiceCandidates(ImmutableSet.copyOf(invoiceCandidateIds), asyncBatchId);

		// retrieve the result
		return invoiceCandidateIds.stream()
				.map(invoiceCandDAO::retrieveIlForIc)
				.flatMap(List::stream)
				.map(org.compiere.model.I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void processInvoiceCandidates(
			@NonNull final Set<InvoiceCandidateId> invoiceCandidateIds,
			@NonNull final AsyncBatchId asyncBatchId)
	{
		generateInvoicesForAsyncBatch(invoiceCandidateIds, asyncBatchId);
	}

	@NonNull
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandsByInOutLines(@NonNull final List<I_M_InOutLine> shipmentLines)
	{
		return shipmentLines.stream()
				.map(invoiceCandDAO::retrieveInvoiceCandidatesForInOutLine)
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private void generateInvoicesForAsyncBatch(@NonNull final Set<InvoiceCandidateId> invoiceCandIds, @NonNull final AsyncBatchId asyncBatchId)
	{
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandIds, Trx.TRXNAME_None);

		final IInvoiceCandidateEnqueuer enqueuer = invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setC_Async_Batch(asyncBatch)
				.setInvoicingParams(createDefaultIInvoicingParams())
				.setFailIfNothingEnqueued(true);

		// this creates workpackages
		enqueuer.prepareSelection(invoiceCandidatesSelectionId);

		final Supplier<IEnqueueResult> enqueueInvoiceCandidates = () -> enqueuer
				.enqueueSelection(invoiceCandidatesSelectionId);

		asyncBatchService.executeBatch(enqueueInvoiceCandidates, asyncBatchId);
	}

	@NonNull
	private InvoicingParams createDefaultIInvoicingParams()
	{
		return InvoicingParams.builder()
				.ignoreInvoiceSchedule(false)
				.dateInvoiced(LocalDate.now())
				.build();
	}
}
