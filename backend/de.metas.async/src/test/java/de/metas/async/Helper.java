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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkpackageProcessorExecutionResult;
import de.metas.async.processor.impl.SynchronousQueueProcessor;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.common.util.time.SystemTime;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.lock.spi.impl.PlainLockDatabase;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Misc helper used when testing ASync module
 * 
 * @author tsa
 * 
 */
public class Helper
{
	private final Logger logger = LogManager.getLogger(getClass());

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
		if (!lockDatabase.getLocks().isEmpty())
		{
			lockDatabase.dump();
			Assert.fail("No locks were expected but we found some."
					+ "\n Locked objects info: " + lockDatabase.getLockedObjectsInfo());
		}
	}

	public I_C_Queue_Processor createQueueProcessor(
			final String name, final int poolSize, final int keepAliveTimeMillis)
	{
		final Optional<I_C_Queue_Processor> existingProcessor = queryBL.createQueryBuilder(I_C_Queue_Processor.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_Processor.COLUMNNAME_Name, name)
				.create()
				.firstOnlyOptional(I_C_Queue_Processor.class);

		final I_C_Queue_Processor queueProcessorDef = existingProcessor
				.orElseGet(() -> InterfaceWrapperHelper.create(ctx, I_C_Queue_Processor.class, ITrx.TRXNAME_None));

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

		final Optional<I_C_Queue_Processor_Assign> existingAssignment = queryBL.createQueryBuilder(I_C_Queue_Processor_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_PackageProcessor_ID, packageProcessorDef.getC_Queue_PackageProcessor_ID())
				.addEqualsFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_Processor_ID, queueProcessorDef.getC_Queue_Processor_ID())
				.create()
				.firstOnlyOptional(I_C_Queue_Processor_Assign.class);

		if (existingAssignment.isPresent())
		{
			return;
		}

		final I_C_Queue_Processor_Assign assignment = InterfaceWrapperHelper.create(ctx, I_C_Queue_Processor_Assign.class, trxName);
		assignment.setC_Queue_Processor(queueProcessorDef);
		assignment.setC_Queue_PackageProcessor(packageProcessorDef);
		InterfaceWrapperHelper.save(assignment);
	}

	public List<I_C_Queue_WorkPackage> createAndEnqueueWorkpackages(final IWorkPackageQueue workpackageQueue, final int count, final boolean markReadyForProcessing)
	{
		final List<I_C_Queue_WorkPackage> workpackages = new ArrayList<>(count);
		for (int i = 1; i <= count; i++)
		{
			final I_C_Queue_WorkPackage workPackage = workpackageQueue.newWorkPackage().buildWithPackageProcessor();

			final I_C_Queue_WorkPackage wp = workpackageQueue.enqueueWorkPackage(workPackage, IWorkPackageQueue.PRIORITY_AUTO);
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

	public void markReadyForProcessing(I_C_Queue_WorkPackage... workpackages)
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
	 * @param timeoutMillis how many millis to wait for result. If ZERO, we will wait forever
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
			long currentTS = de.metas.common.util.time.SystemTime.millis();
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

	public SynchronousQueueProcessor newSynchronousQueueProcessor(final IWorkPackageQueue workpackageQueueForProcessing)
	{
		return new SynchronousQueueProcessor(workpackageQueueForProcessing, NOPWorkpackageLogsRepository.instance);
	}
}
