package de.metas.async;

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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.junit.Assert;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.lock.spi.impl.PlainLockDatabase;

/**
 * Misc helper used when testing ASync module
 * 
 * @author tsa
 * 
 */
public class Helper
{
	private final Logger logger = LogManager.getLogger(getClass());

	private Properties ctx;

	public Helper()
	{
		super();
		this.ctx = Env.getCtx();
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public void assertNothingLocked()
	{
		final PlainLockManager lockManager = (PlainLockManager)Services.get(ILockManager.class);
		final PlainLockDatabase lockDatabase = lockManager.getLockDatabase();
		Assert.assertTrue("No locks expecteded\n" + lockDatabase.getLockedObjectsInfo(), lockDatabase.getLocks().isEmpty());
	}

	public I_C_Queue_Processor createQueueProcessor(
			final String name,
			final int poolSize,
			final int maxPoolSize,
			final int keepAliveTimeMillis
			)
	{
		final I_C_Queue_Processor queueProcessorDef = InterfaceWrapperHelper.create(ctx, I_C_Queue_Processor.class, ITrx.TRXNAME_None);
		queueProcessorDef.setName(name);
		queueProcessorDef.setPoolSize(poolSize);
		queueProcessorDef.setKeepAliveTimeMillis(keepAliveTimeMillis);
		InterfaceWrapperHelper.save(queueProcessorDef);
		return queueProcessorDef;
	}

	public I_C_Queue_PackageProcessor createPackageProcessor(Properties ctx, Class<? extends IWorkpackageProcessor> packageProcessorClass, String trxName)
	{
		final I_C_Queue_PackageProcessor packageProcessorDef = InterfaceWrapperHelper.create(ctx, I_C_Queue_PackageProcessor.class, trxName);
		packageProcessorDef.setClassname(packageProcessorClass.getCanonicalName());
		InterfaceWrapperHelper.save(packageProcessorDef);

		return packageProcessorDef;
	}

	public I_C_Queue_PackageProcessor createPackageProcessor(Properties ctx, Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		return createPackageProcessor(ctx, packageProcessorClass, ITrx.TRXNAME_None);
	}

	public I_C_Queue_PackageProcessor assignPackageProcessor(I_C_Queue_Processor queueProcessorDef, Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(queueProcessorDef);
		final String trxName = InterfaceWrapperHelper.getTrxName(queueProcessorDef);

		final I_C_Queue_PackageProcessor packageProcessorDef = createPackageProcessor(ctx, packageProcessorClass, trxName);
		assignPackageProcessor(queueProcessorDef, packageProcessorDef);

		return packageProcessorDef;
	}

	public void assignPackageProcessor(final I_C_Queue_Processor queueProcessorDef, final I_C_Queue_PackageProcessor packageProcessorDef)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(queueProcessorDef);
		final String trxName = InterfaceWrapperHelper.getTrxName(queueProcessorDef);

		final I_C_Queue_Processor_Assign assignment = InterfaceWrapperHelper.create(ctx, I_C_Queue_Processor_Assign.class, trxName);
		assignment.setC_Queue_Processor(queueProcessorDef);
		assignment.setC_Queue_PackageProcessor(packageProcessorDef);
		InterfaceWrapperHelper.save(assignment);
	}

	public List<I_C_Queue_WorkPackage> createAndEnqueueWorkpackages(final IWorkPackageQueue workpackageQueue, final int count, final boolean markReadyForProcessing)
	{
		final I_C_Queue_Block block = workpackageQueue.enqueueBlock(ctx);

		final List<I_C_Queue_WorkPackage> workpackages = new ArrayList<I_C_Queue_WorkPackage>(count);
		for (int i = 1; i <= count; i++)
		{
			final I_C_Queue_WorkPackage wp = workpackageQueue.enqueueWorkPackage(block, IWorkPackageQueue.PRIORITY_AUTO);
			POJOWrapper.setInstanceName(wp, "workpackage-" + i);
			if (markReadyForProcessing)
			{
				workpackageQueue.markReadyForProcessing(wp);
			}

			workpackages.add(wp);
		}

		return workpackages;
	}

	public void markReadyForProcessing(List<I_C_Queue_WorkPackage> workpackages)
	{
		for (I_C_Queue_WorkPackage wp : workpackages)
		{
			wp.setIsReadyForProcessing(true);
			InterfaceWrapperHelper.save(wp);
		}
	}

	public void markReadyForProcessing(I_C_Queue_WorkPackage ... workpackages)
	{
		Check.assumeNotNull(workpackages, "workpackages not null");
		if (workpackages.length == 0)
		{
			return;
		}
		
		markReadyForProcessing(Arrays.asList(workpackages));
	}

	/**
	 * Mark the workpackage as ready for processing and wait until the workpackage gets processed
	 * 
	 * @param workpackageQueue
	 * @param workpackage
	 * @throws TimeoutException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void markReadyForProcessingAndWait(final IWorkPackageQueue workpackageQueue, final I_C_Queue_WorkPackage workpackage) throws InterruptedException, ExecutionException, TimeoutException
	{
		Future<IWorkpackageProcessorExecutionResult> futureResult = workpackageQueue.markReadyForProcessingAndReturn(workpackage);

		futureResult.get(1, TimeUnit.MINUTES);
	}

	public void markUnprocessed(final I_C_Queue_WorkPackage workpackage)
	{
		workpackage.setIsReadyForProcessing(false);
		InterfaceWrapperHelper.save(workpackage);

		workpackage.setProcessed(false);
		workpackage.setIsError(false);
		workpackage.setSkippedAt(null);
		workpackage.setAD_Issue(null);

		InterfaceWrapperHelper.save(workpackage);
	}

	/**
	 * Method waits until the given {@code list} has reached the given {@code targetSize} or if the given {@code timeoutMillis} has passed. If the timeout has pased without the size beeing reached,
	 * the method throws an Exception.
	 * 
	 * @param list
	 * @param targetSize
	 * @param timeoutMillis how many millis to wait for result. If ZERO, we will wait forever
	 * @throws InterruptedException
	 * @throws TimeoutException if the timeout exceeded
	 */
	public void waitUntilSize(final List<?> list, final int targetSize, final long timeoutMillis) throws InterruptedException, TimeoutException
	{
		Thread.sleep(100); // wait a bit, because the processor need not only to increase list.size(), but also flag the package as "processed"
		
		int retryCount = 0;

		final long beginTS = SystemTime.millis();
		int lastSize = -1;
		int size = list.size();
		retryCount++;

		while (size < targetSize)
		{
			long currentTS = SystemTime.millis();
			if (lastSize != -1 && timeoutMillis > 0)
			{
				final long elapsedMillis = currentTS - beginTS;
				if (elapsedMillis >= timeoutMillis)
				{
					throw new TimeoutException("Timeout while waiting for " + list + "to have at least " + targetSize + " items");
				}
			}

			Thread.sleep(200);

			size = list.size();
			lastSize = size;
			retryCount++;

			logger.trace("Retry " + retryCount + " => size=" + size);
		}
		logger.trace("Finished after " + retryCount + " retries => size=" + size);
		Thread.sleep(200); // without further delay, the future we wait for might still not be "done"
	}
}
