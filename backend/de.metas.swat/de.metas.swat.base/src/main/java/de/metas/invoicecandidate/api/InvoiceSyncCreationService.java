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

package de.metas.invoicecandidate.api;

import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.service.AsyncBatchService;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.impl.InvoiceCandDAO;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Supplier;

@Service
public class InvoiceSyncCreationService
{
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final AsyncBatchService asyncBatchService;
	private final InvoiceService invoiceService;
	private final InvoiceCandDAO invoiceCandDAO = Services.get(InvoiceCandDAO.class);

	public InvoiceSyncCreationService(
			@NonNull final AsyncBatchService asyncBatchService,
			@NonNull final InvoiceService invoiceService)
	{
		this.asyncBatchService = asyncBatchService;
		this.invoiceService = invoiceService;
	}

	@NonNull
	public Set<InvoiceId> generateIcsAndInvoices(@NonNull final Object model)
	{
		generateMissingInvoiceCandidatesFromContract(model);

		final Set<InvoiceCandidateId> invoiceCandidateIds = invoiceCandDAO.retrieveReferencingIds(TableRecordReference.of(model));

		return invoiceService.generateInvoicesFromInvoiceCandidateIds(invoiceCandidateIds);
	}

	private void generateMissingInvoiceCandidatesFromContract(@NonNull final Object model)
	{
		final ImmutablePair<AsyncBatchId, Object> immutablePair = asyncBatchBL.assignAsyncBatchToContractIfMissing(model, Async_Constants.C_Async_Batch_InternalName_EnqueueInvoiceCandidateForContract);

		final Supplier<Void> action = () -> {
			CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(immutablePair.getRight());
			return null;
		};

		asyncBatchService.executeBatch(action, immutablePair.getLeft());
	}

}
