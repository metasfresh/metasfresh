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

import com.google.common.collect.Multimap;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.service.AsyncBatchService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
	 * @param modelReferences the models for which the invoice candidates and subsequently invoice(s) shall be created.
	 */
	public void generateIcsAndInvoices(
			@NonNull final List<TableRecordReference> modelReferences,
			@Nullable final InvoicingParams invoicingParams)
	{
		generateMissingInvoiceCandidatesForModel(modelReferences);

		final HashSet<InvoiceCandidateId> invoiceCandidateIds = new HashSet<>();
		for (final TableRecordReference modelReference : modelReferences)
		{
			invoiceCandidateIds.addAll(invoiceCandDAO.retrieveReferencingIds(modelReference));
		}

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandidateIds, Trx.TRXNAME_None);
		invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setInvoicingParams(invoicingParams != null ? invoicingParams : createDefaultIInvoicingParams())
				.setFailIfNothingEnqueued(true)
				.prepareAndEnqueueSelection(invoiceCandidatesSelectionId);
	}

	private void generateMissingInvoiceCandidatesForModel(@NonNull final List<TableRecordReference> modelReferences)
	{
		final List<Object> models = TableRecordReference.getModels(modelReferences, Object.class);

		final Multimap<AsyncBatchId, Object> batchIdWithUpdatedModel =
				asyncBatchBL.assignTempAsyncBatchToModelsIfMissing(
						models,
						Async_Constants.C_Async_Batch_InternalName_EnqueueInvoiceCandidateCreation);

		for (final AsyncBatchId asyncBatchId : batchIdWithUpdatedModel.keySet())
		{
			final Collection<Object> modelsWithBatchId = batchIdWithUpdatedModel.get(asyncBatchId);

			final Supplier<IEnqueueResult> action = () -> {

				int counter = 0;
				for (final Object modelWithBatchId : modelsWithBatchId)
				{
					CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(modelWithBatchId);
					counter++;
				}
				final int finalCounter = counter; // a lambda's return value should be final
				return () -> finalCounter; // return the numer of workpackages that we enqeued
			};

			asyncBatchService.executeBatch(action, asyncBatchId);
		}

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
