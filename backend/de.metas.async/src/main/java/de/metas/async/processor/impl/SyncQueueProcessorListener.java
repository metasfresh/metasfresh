package de.metas.async.processor.impl;

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


import java.util.concurrent.Future;

import org.adempiere.util.concurrent.FutureValue;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessorListener;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * Callback used to implement synchronous processing over our asynchronous queue
 * 
 * @author tsa
 */
public class SyncQueueProcessorListener implements IQueueProcessorListener
{
	private final FutureValue<IWorkpackageProcessorExecutionResult> futureResult;

	public SyncQueueProcessorListener()
	{
		futureResult = new FutureValue<>();
	}

	@Override
	public void onWorkpackageProcessed(final I_C_Queue_WorkPackage workpackage, final IWorkpackageProcessor workpackageProcessor)
	{
		final WorkpackageProcessorExecutionResult result = new WorkpackageProcessorExecutionResult(workpackage, workpackageProcessor);
		futureResult.set(result);
	}

	/**
	 * Gets future workpackage processing result
	 */
	public Future<IWorkpackageProcessorExecutionResult> getFutureResult()
	{
		return futureResult;
	}

	/**
	 * Mark the {@link Future} result as cancelled.
	 * 
	 * NOTE: In this case the {@link Future#get()} of {@link #getFutureResult()} will throw CalcellationException.
	 * 
	 * @param error error message/exception to be attached
	 */
	public void cancelWithError(final Exception error)
	{
		futureResult.setCanceled(error);
	}
}
