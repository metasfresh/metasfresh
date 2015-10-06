package de.metas.async.api;

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


import org.adempiere.util.ISingletonService;

import de.metas.async.model.I_C_Async_Batch;

public interface IWorkpackageProcessorContextFactory extends ISingletonService
{
	/**
	 * Associate the given <code>asyncBatch</code> with the current thread
	 * 
	 * @param asyncBatch
	 * @return the async batch that was formerly associate with the current thread, or <code>null</code>.
	 */
	I_C_Async_Batch setThreadInheritedAsyncBatch(I_C_Async_Batch asyncBatch);

	/**
	 * Get batch id from the inherited thread
	 * 
	 * @return
	 */
	int getThreadInheritedAsyncBatchId();

	/**
	 * Get the async batch associated with the current thread (or <code>null</code>).
	 * 
	 * @return
	 */
	I_C_Async_Batch getThreadInheritedAsyncBatch();

	/**
	 * Get the priority associated with the current thread
	 * 
	 * @return
	 */
	String getThreadInheritedPriority();

	/**
	 * Associate the given <code>priority</code> with the current thread
	 * 
	 * @param priority
	 */
	void setThreadInheritedPriority(String priority);

}
