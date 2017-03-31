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


import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.util.Services;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IMutableQueueProcessorStatistics;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorsExecutor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor.Result;

/**
 * Test Queue Processor and Workpackage Processor counters/statistics
 * 
 * @author tsa
 * 
 */
public class QueueProcessor_Statistics_Test extends QueueProcessorTestBase
{
	private I_C_Queue_Processor processorDef;
	private IQueueProcessorsExecutor processorsExecutor;
	private IQueueProcessor processor;

	private IWorkPackageQueue workpackageQueue;

	private I_C_Queue_PackageProcessor workpackageProcessorDef;
	private IMutableQueueProcessorStatistics workpackageProcessorStatistics;
	private MockedWorkpackageProcessor workpackageProcessor;

	@Override
	protected void beforeTestCustomized()
	{
		POJOWrapper.setDefaultStrictValues(false);
		
		//
		// Configure WorkPackage Processor
		workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setDefaultResult(Result.SUCCESS);
	}

	@Override
	protected void afterTestCustomized()
	{
		logger.info(StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor().getStatisticsInfo());

		if (processorsExecutor != null)
		{
			processorsExecutor.shutdown();
			processorsExecutor = null;
			processorDef = null;
		}
	}

	private void setupQueueProcessor(final int poolSize, final int maxPoolSize)
	{
		//
		// Create and configure Queue Processor
		processorDef = helper.createQueueProcessor("test",
				poolSize, // poolSize
				maxPoolSize, // maxPoolSize
				1000 // keepAliveTimeMillis
		);
		workpackageProcessorDef = helper.assignPackageProcessor(processorDef, StaticMockedWorkpackageProcessor.class);

		processorsExecutor = new QueueProcessorsExecutor();
		processorsExecutor.addQueueProcessor(processorDef);
		processor = processorsExecutor.getQueueProcessor(processorDef.getC_Queue_Processor_ID());

		//
		// Create Queue
		workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		workpackageProcessorStatistics = processor.getActualWorkpackageProcessorFactory().getWorkpackageProcessorStatistics(workpackageProcessorDef);
	}

	@Test
	@Ignore // FIXME this test is unstable and fails on some machines for no known reasons. To fix, please make sure to know what the test is doing..I mean, don't use "sleep" assume each thread will be at a certain point, but use locking etc to make sure.
	public void test() throws Exception
	{
		//
		// Setup Queue processors
		// NOTE: we are setting poolSize=maxPoolSize=1 to make sure there is only one slot and tasks are executed in order, one by one
		setupQueueProcessor(1, 1); // poolSize=maxPoolSize=1

		Assert.assertEquals("Invalid QueueSize", 0, workpackageProcessorStatistics.getQueueSize());
		Assert.assertEquals("Invalid CountAll", 0, workpackageProcessorStatistics.getCountAll());
		Assert.assertEquals("Invalid CountErrors", 0, workpackageProcessorStatistics.getCountErrors());
		Assert.assertEquals("Invalid CountProcessed", 0, workpackageProcessorStatistics.getCountProcessed());
		Assert.assertEquals("Invalid CountSkipped", 0, workpackageProcessorStatistics.getCountSkipped());

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 
				5, // count == 5 
				false); // markReadyForProcessing == false
		
		Assert.assertEquals("Invalid QueueSize", 5, workpackageProcessorStatistics.getQueueSize());
		Assert.assertEquals("Invalid CountAll", 0, workpackageProcessorStatistics.getCountAll());
		Assert.assertEquals("Invalid CountErrors", 0, workpackageProcessorStatistics.getCountErrors());
		Assert.assertEquals("Invalid CountProcessed", 0, workpackageProcessorStatistics.getCountProcessed());
		Assert.assertEquals("Invalid CountSkipped", 0, workpackageProcessorStatistics.getCountSkipped());

		// Workpackage 0: Process Successfully
		{
			final I_C_Queue_WorkPackage workpackage = workpackages.get(0);
			
			Assert.assertEquals("Invalid ReadyForProcessing", false, workpackage.isReadyForProcessing());
			helper.markReadyForProcessingAndWait(workpackageQueue, workpackage);
			Assert.assertEquals("Invalid ReadyForProcessing", true, workpackage.isReadyForProcessing());
			
			Assert.assertEquals("Invalid QueueSize", 4, workpackageProcessorStatistics.getQueueSize());
			Assert.assertEquals("Invalid CountAll", 1, workpackageProcessorStatistics.getCountAll());
			Assert.assertEquals("Invalid CountErrors", 0, workpackageProcessorStatistics.getCountErrors());
			Assert.assertEquals("Invalid CountProcessed", 1, workpackageProcessorStatistics.getCountProcessed());
			Assert.assertEquals("Invalid CountSkipped", 0, workpackageProcessorStatistics.getCountSkipped());
		}

		// Workpackage 1: Process with Errors
		{
			final I_C_Queue_WorkPackage workpackage = workpackages.get(1);
			workpackageProcessor.setException(workpackage, "test error");
			helper.markReadyForProcessingAndWait(workpackageQueue, workpackage);
			Assert.assertEquals("Invalid QueueSize", 3, workpackageProcessorStatistics.getQueueSize());
			Assert.assertEquals("Invalid CountAll", 2, workpackageProcessorStatistics.getCountAll());
			Assert.assertEquals("Invalid CountErrors", 1, workpackageProcessorStatistics.getCountErrors());
			Assert.assertEquals("Invalid CountProcessed", 1, workpackageProcessorStatistics.getCountProcessed());
			Assert.assertEquals("Invalid CountSkipped", 0, workpackageProcessorStatistics.getCountSkipped());
		}

		// Workpackage 2: Process Skipped
		{
			final I_C_Queue_WorkPackage workpackage = workpackages.get(2);
			workpackageProcessor.setSkip(workpackage, 99999999); // Skip it forever
			helper.markReadyForProcessingAndWait(workpackageQueue, workpackage);
			Assert.assertEquals("Invalid QueueSize", 3, workpackageProcessorStatistics.getQueueSize());
			Assert.assertEquals("Invalid CountAll", 3, workpackageProcessorStatistics.getCountAll());
			Assert.assertEquals("Invalid CountErrors", 1, workpackageProcessorStatistics.getCountErrors());
			Assert.assertEquals("Invalid CountProcessed", 1, workpackageProcessorStatistics.getCountProcessed());
			Assert.assertEquals("Invalid CountSkipped", 1, workpackageProcessorStatistics.getCountSkipped());
		}

		// Workpackage 3: Process Successfully (again)
		{
			final I_C_Queue_WorkPackage workpackage = workpackages.get(3);
			helper.markReadyForProcessingAndWait(workpackageQueue, workpackage);
			Assert.assertEquals("Invalid QueueSize", 2, workpackageProcessorStatistics.getQueueSize());
			Assert.assertEquals("Invalid CountAll", 4, workpackageProcessorStatistics.getCountAll());
			Assert.assertEquals("Invalid CountErrors", 1, workpackageProcessorStatistics.getCountErrors());
			Assert.assertEquals("Invalid CountProcessed", 2, workpackageProcessorStatistics.getCountProcessed());
			Assert.assertEquals("Invalid CountSkipped", 1, workpackageProcessorStatistics.getCountSkipped());
		}

		// Workpackage 4: Process Successfully (again)
		{
			final I_C_Queue_WorkPackage workpackage = workpackages.get(4);
			helper.markReadyForProcessingAndWait(workpackageQueue, workpackage);
			Assert.assertEquals("Invalid QueueSize", 1, workpackageProcessorStatistics.getQueueSize());
			Assert.assertEquals("Invalid CountAll", 5, workpackageProcessorStatistics.getCountAll());
			Assert.assertEquals("Invalid CountErrors", 1, workpackageProcessorStatistics.getCountErrors());
			Assert.assertEquals("Invalid CountProcessed", 3, workpackageProcessorStatistics.getCountProcessed());
			Assert.assertEquals("Invalid CountSkipped", 1, workpackageProcessorStatistics.getCountSkipped());
		}

	}
}
