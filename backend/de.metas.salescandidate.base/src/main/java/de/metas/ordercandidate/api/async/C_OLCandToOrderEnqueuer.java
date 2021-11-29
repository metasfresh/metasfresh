/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.api.async;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static de.metas.ordercandidate.api.async.C_OLCandToOrderWorkpackageProcessor.OLCandProcessor_ID;
import static org.compiere.util.Env.getCtx;

@Service
public class C_OLCandToOrderEnqueuer
{
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);

	public void enqueue(@NonNull final Integer olCandProcessorId, @Nullable final AsyncBatchId asyncBatchId)
	{
		final IWorkPackageBuilder workPackageBuilder = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), C_OLCandToOrderWorkpackageProcessor.class)
				.newBlock()
				.setContext(getCtx())
				.newWorkpackage()
				.parameter(OLCandProcessor_ID, olCandProcessorId);

		if (asyncBatchId != null)
		{
			final I_C_Async_Batch asyncBatch = asyncBatchDAO.retrieveAsyncBatchRecord(asyncBatchId);
			workPackageBuilder.setC_Async_Batch(asyncBatch);
		}

		workPackageBuilder.build();
	}
}
