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
import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Queue_WorkPackage;
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

	@NonNull
	public QueueWorkPackageId enqueue(@NonNull final Integer olCandProcessorId, @Nullable final AsyncBatchId asyncBatchId)
	{
		final I_C_Queue_WorkPackage result = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), C_OLCandToOrderWorkpackageProcessor.class)
				.newWorkPackage()
				.parameter(OLCandProcessor_ID, olCandProcessorId)
				.setC_Async_Batch_ID(asyncBatchId)
				.buildAndEnqueue();

		return QueueWorkPackageId.ofRepoId(result.getC_Queue_WorkPackage_ID());
	}
}
