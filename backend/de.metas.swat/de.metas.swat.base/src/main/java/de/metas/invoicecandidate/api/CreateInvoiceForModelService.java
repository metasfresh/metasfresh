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
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Supplier;

import static org.compiere.util.Env.getCtx;

@Service
public class CreateInvoiceForModelService
{
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final AsyncBatchService asyncBatchService;

	public CreateInvoiceForModelService(
			@NonNull final AsyncBatchService asyncBatchService)
	{
		this.asyncBatchService = asyncBatchService;
	}

	/**
	 * Uses the Async-framework to create invoice candidates for the given model. Waits until those candidates are created and then enqueues them for invoicing.
	 *
	 * @param modelReference the model for which the invoice cnadidates and subsequently invoices shall be created.
	 *                       It is expected that the model already references an AsyncBatch_ID.
	 */
	public void generateIcsAndInvoices(@NonNull final TableRecordReference modelReference)
	{
		generateMissingInvoiceCandidatesForModel(modelReference);

		final Set<InvoiceCandidateId> invoiceCandidateIds = invoiceCandDAO.retrieveReferencingIds(modelReference);

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandidateIds, Trx.TRXNAME_None);
		invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setInvoicingParams(createDefaultIInvoicingParams())
				.setFailIfNothingEnqueued(true)
				.enqueueSelection(invoiceCandidatesSelectionId);
	}

	private void generateMissingInvoiceCandidatesForModel(@NonNull final TableRecordReference modelReference)
	{
		final Object model = modelReference.getModel(Object.class);

		final ImmutablePair<AsyncBatchId, Object> batchIdWithUpdatedModel = asyncBatchBL
				.assignTempAsyncBatchToModelIfMissing(model, Async_Constants.C_Async_Batch_InternalName_EnqueueInvoiceCandidateCreation);

		final Supplier<Void> action = () -> {
			CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(batchIdWithUpdatedModel.getRight());
			return null;
		};

		asyncBatchService.executeBatch(action, batchIdWithUpdatedModel.getLeft());
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
