/**
 *
 */
package de.metas.async.api;

import de.metas.async.AsyncBatchId;
import de.metas.async.asyncbatchmilestone.AsyncBatchMilestoneId;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Milestone;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.util.ISingletonService;

import java.util.List;
import java.util.Properties;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2015 metas GmbH
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

/**
 * @author cg
 *
 */
public interface IAsyncBatchDAO extends ISingletonService
{

	String ASYNC_BATCH_TYPE_DEFAULT = "Default";

	I_C_Async_Batch retrieveAsyncBatchRecord(AsyncBatchId asyncBatchId);

	/**
	 * Retrieve async batch type by internal name which must be unique.
	 *
	 * @return {@link I_C_Async_Batch_Type}; never returns null
	 */
	I_C_Async_Batch_Type retrieveAsyncBatchType(Properties ctx, String internalName);

	/**
	 * retrieve workpackages for async batch
	 */
	List<I_C_Queue_WorkPackage> retrieveWorkPackages(I_C_Async_Batch asyncBatch, String trxName);

	/**
	 * retrieve workpackages for async batch
	 */
	List<I_C_Queue_WorkPackage> retrieveWorkPackages(I_C_Async_Batch asyncBatch, String trxName, Boolean processed);

	/**
	 * retrieve notified workpackages fro an async batch
	 */
	List<I_C_Queue_WorkPackage_Notified> retrieveWorkPackagesNotified(I_C_Async_Batch asyncBatch, boolean notified);

	/**
	 * fetch the notifyable record for a given workpackage
	 */
	I_C_Queue_WorkPackage_Notified fetchWorkPackagesNotified(I_C_Queue_WorkPackage workPackage);

	AsyncBatchId retrieveAsyncBatchIdByMilestone(AsyncBatchMilestoneId milestoneId);

	List<I_C_Async_Batch_Milestone> retrieveMilestonesForAsyncBatchId(AsyncBatchId id);
}
