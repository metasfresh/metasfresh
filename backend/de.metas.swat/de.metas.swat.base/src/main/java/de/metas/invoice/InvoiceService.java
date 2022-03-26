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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.service.AsyncBatchService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_InvoiceCandidate_Processing;
import static org.compiere.util.Env.getCtx;

@Service
public class InvoiceService
{
	private final static transient Logger logger = LogManager.getLogger(InvoiceService.class);

	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private final AsyncBatchService asyncBatchService;

	public InvoiceService(@NonNull final AsyncBatchService asyncBatchService)
	{
		this.asyncBatchService = asyncBatchService;
	}

	@NonNull
	public Set<InvoiceId> generateInvoicesFromShipmentLines(@NonNull final List<I_M_InOutLine> shipmentLines)
	{
		if (shipmentLines.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("generateInvoicesFromShipmentLines - Given shipmentLines lit is empty; -> nothing to do");
			return ImmutableSet.of();
		}
		
		final Set<InvoiceCandidateId> invoiceCandidateIds = retrieveInvoiceCandsByInOutLines(shipmentLines)
				.stream()
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return generateInvoicesFromInvoiceCandidateIds(invoiceCandidateIds);
	}

	public ImmutableSet<InvoiceId> generateInvoicesFromInvoiceCandidateIds(@NonNull final Set<InvoiceCandidateId> invoiceCandidateIds)
	{
		processInvoiceCandidates(invoiceCandidateIds);

		return invoiceCandidateIds.stream()
				.map(invoiceCandDAO::retrieveIlForIc)
				.flatMap(List::stream)
				.map(org.compiere.model.I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void processInvoiceCandidates(@NonNull final Set<InvoiceCandidateId> invoiceCandidateIds)
	{
		final ImmutableMap<AsyncBatchId, List<InvoiceCandidateId>> asyncBatchId2InvoiceCandIds = getAsyncBatchId2InvoiceCandidateIds(invoiceCandidateIds);

		for (final Map.Entry<AsyncBatchId, List<InvoiceCandidateId>> entry : asyncBatchId2InvoiceCandIds.entrySet())
		{
			generateInvoicesForAsyncBatch(ImmutableSet.copyOf(entry.getValue()), entry.getKey());
		}
	}

	@NonNull
	public List<I_C_Invoice_Candidate> retrieveInvoiceCandsByInOutLines(@NonNull final List<I_M_InOutLine> shipmentLines)
	{
		return shipmentLines.stream()
				.map(invoiceCandDAO::retrieveInvoiceCandidatesForInOutLine)
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableMap<AsyncBatchId, List<InvoiceCandidateId>> getAsyncBatchId2InvoiceCandidateIds(@NonNull final Set<InvoiceCandidateId> invoiceCandidateIds)
	{
		if (invoiceCandidateIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.getByIds(invoiceCandidateIds);

		final HashMap<AsyncBatchId, ArrayList<InvoiceCandidateId>> asyncBatchId2InvoiceCand = new HashMap<>();

		invoiceCandidates
				.forEach(invoiceCandidate -> {
					final AsyncBatchId currentAsyncBatchId = AsyncBatchId.ofRepoIdOrNone(invoiceCandidate.getC_Async_Batch_ID());

					final ArrayList<InvoiceCandidateId> currentInvoiceCands = new ArrayList<>();
					currentInvoiceCands.add(InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID()));

					asyncBatchId2InvoiceCand.merge(currentAsyncBatchId, currentInvoiceCands, CollectionUtils::mergeLists);
				});

		Optional.ofNullable(asyncBatchId2InvoiceCand.get(AsyncBatchId.NONE_ASYNC_BATCH_ID))
				.ifPresent(noAsyncBatchICIds -> {
					final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_InvoiceCandidate_Processing);

					noAsyncBatchICIds.forEach(icID -> invoiceCandBL.setAsyncBatch(icID, asyncBatchId));

					asyncBatchId2InvoiceCand.put(asyncBatchId, noAsyncBatchICIds);
					asyncBatchId2InvoiceCand.remove(AsyncBatchId.NONE_ASYNC_BATCH_ID);
				});

		return ImmutableMap.copyOf(asyncBatchId2InvoiceCand);
	}

	private void generateInvoicesForAsyncBatch(@NonNull final Set<InvoiceCandidateId> invoiceCandIds, @NonNull final AsyncBatchId asyncBatchId)
	{
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);

		final Supplier<Void> enqueueInvoiceCandidates = () -> {
			final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandIds, Trx.TRXNAME_None);

			invoiceCandBL.enqueueForInvoicing()
					.setContext(getCtx())
					.setC_Async_Batch(asyncBatch)
					.setInvoicingParams(createDefaultIInvoicingParams())
					.setFailIfNothingEnqueued(true)
					.enqueueSelection(invoiceCandidatesSelectionId);

			return null;
		};

		asyncBatchService.executeBatch(enqueueInvoiceCandidates, asyncBatchId);
	}

	@NonNull
	private IInvoicingParams createDefaultIInvoicingParams()
	{
		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(false);
		invoicingParams.setSupplementMissingPaymentTermIds(true);
		invoicingParams.setDateInvoiced(LocalDate.now());

		return invoicingParams;
	}
}
