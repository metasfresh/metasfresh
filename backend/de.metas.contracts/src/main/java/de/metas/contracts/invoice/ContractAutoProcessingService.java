/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.invoice;

import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.service.AsyncBatchService;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_EnqueueScheduleForOrder;

@Service
public class ContractAutoProcessingService
{
	private static final Logger logger = LogManager.getLogger(ContractAutoProcessingService.class);

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final AsyncBatchService asyncBatchService;
	private final InvoiceService invoiceService;
	
	public ContractAutoProcessingService(
			@NonNull final AsyncBatchService asyncBatchService, 
			final InvoiceService invoiceService)
	{
		this.asyncBatchService = asyncBatchService;
		this.invoiceService = invoiceService;
	}

	@NonNull
	public Set<InvoiceId> generateInvoiceSync(@NonNull final List<I_C_Flatrate_Term> contracts)
	{
		if (contracts.isEmpty())
		{
			return ImmutableSet.of();
		}

		contracts.forEach(this::generateMissingInvoiceCandidatesFromContract);

		// TODO
		// get invoice candidate IDs
		final Set<InvoiceCandidateId> invoiceCandidateIds=null;
		
		return invoiceService.generateInvoicesFromInvoiceCandidateIds(invoiceCandidateIds);
	}

	private void generateMissingInvoiceCandidatesFromContract(@NonNull final I_C_Flatrate_Term contract)
	{
		final I_C_Flatrate_Term contractWithAsyncBatch = assignAsyncBatchToContractIfMissing(contract);

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(contractWithAsyncBatch.getC_Async_Batch_ID());

		final Supplier<Void> action = () -> {
			CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(contract);
			return null;
		};

		asyncBatchService.executeBatch(action, asyncBatchId);
	}

	private @NonNull I_C_Flatrate_Term assignAsyncBatchToContractIfMissing(@NonNull final I_C_Flatrate_Term contract)
	{
		if (contract.getC_Async_Batch_ID() > 0)
		{
			return contract;
		}

		trxManager.runInNewTrx(() -> {

			final AsyncBatchId currentAsyncBatchId = asyncBatchBL.newAsyncBatch(Async_Constants.C_Async_Batch_InternalName_EnqueueInvoiceCandidateForContract);
			flatrateDAO.assignAsyncBatchId(
					FlatrateTermId.ofRepoId(contract.getC_Flatrate_Term_ID()),
					currentAsyncBatchId);

			contract.setC_Async_Batch_ID(currentAsyncBatchId.getRepoId());
		});
		return contract;
	}
	
	
}
