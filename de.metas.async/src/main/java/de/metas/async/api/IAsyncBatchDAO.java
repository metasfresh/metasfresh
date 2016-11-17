/**
 * 
 */
package de.metas.async.api;

import java.util.List;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;

/**
 * @author cg
 *
 */
public interface IAsyncBatchDAO extends ISingletonService
{
	
	public static final String ASYNC_BATCH_TYPE_DEFAULT = "Default";
	
	/**
	 * Retrieve async batch type by internal name which must be unique.
	 * 
	 * @param ctx
	 * @param internalName
	 * @return {@link I_C_Async_Batch_Type}; never returns null
	 */
	I_C_Async_Batch_Type retrieveAsyncBatchType(Properties ctx, String internalName);

	/**
	 * retrieve workpackages for async batch
	 * 
	 * @param asyncBatch
	 * @return
	 */
	List<I_C_Queue_WorkPackage> retrieveWorkPackages(final I_C_Async_Batch asyncBatch);

	/**
	 * retrieve workpackages for async batch
	 * 
	 * @param asyncBatch
	 * @param processed
	 * @return
	 */
	List<I_C_Queue_WorkPackage> retrieveWorkPackages(final I_C_Async_Batch asyncBatch, final Boolean processed);

	/**
	 * retrieve notified workpackages fro an async batch
	 * 
	 * @param asyncBatch
	 * @param notified
	 * @return
	 */
	List<I_C_Queue_WorkPackage_Notified> retrieveWorkPackagesNotified(I_C_Async_Batch asyncBatch, boolean notified);

	/**
	 * fetch the notifiable record for a given workpackage
	 * 
	 * @param workPackage
	 * @return
	 */
	I_C_Queue_WorkPackage_Notified fetchWorkPackagesNotified(I_C_Queue_WorkPackage workPackage);
}
