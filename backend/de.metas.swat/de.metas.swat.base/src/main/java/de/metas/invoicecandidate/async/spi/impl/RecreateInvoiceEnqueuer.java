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

package de.metas.invoicecandidate.async.spi.impl;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_AD_PInstance;
import org.springframework.stereotype.Service;

import static de.metas.invoicecandidate.api.InvoiceCandidate_Constants.C_Async_Batch_InternalName_VoidAndRecreateInvoice;
import static org.compiere.util.Env.getCtx;

@Service
public class RecreateInvoiceEnqueuer
{
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	public void enqueueSelection(@NonNull final PInstanceId pInstanceId)
	{
		final I_C_Async_Batch asyncBatch = asyncBatchBL.newAsyncBatch()
				.setContext(getCtx())
				.setC_Async_Batch_Type(C_Async_Batch_InternalName_VoidAndRecreateInvoice)
				.setName(C_Async_Batch_InternalName_VoidAndRecreateInvoice)
				.build();

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), RecreateInvoiceWorkpackageProcessor.class);
		queue.newBlock()
				.setContext(getCtx())
				.newWorkpackage()
				.setC_Async_Batch(asyncBatch)
				.parameter(I_AD_PInstance.COLUMNNAME_AD_PInstance_ID, pInstanceId)
				.build();
	}
}
