/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.printing.async.spi.impl;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;

public class InvoiceEnqueueingWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		final IParams parameters = getParameters();

		final I_C_Async_Batch asyncBatch = workpackage.getC_Async_Batch();
		invoiceCandBL.enqueueForInvoicing()
				.setContext(InterfaceWrapperHelper.getCtx(workpackage))
				.setInvoicingParams(getInvoicingParams(parameters))
				.setFailIfNothingEnqueued(true)
				.setC_Async_Batch(asyncBatch)
				.prepareAndEnqueueSelection(PInstanceId.ofRepoIdOrNull(asyncBatch.getAD_PInstance_ID()));

		return Result.SUCCESS;
	}

	@NonNull
	private IInvoicingParams getInvoicingParams(@NonNull final IParams parameters)
	{
		return new InvoicingParams(getParameters());
	}
}