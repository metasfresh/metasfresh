package de.metas.async.api.impl;

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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.async.Helper;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.impl.ConstantWorkpackagePrio;

/**
 * See {@link #test_forwardWorkPackagePrio()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TestQueue_InheritPriority
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private Properties ctx;
	private String trxName;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		//
		// Setup test data
		ctx = Env.getCtx();
		trxName = ITrx.TRXNAME_None;
	}

	private I_C_Queue_Element createQueueElement(final I_C_Queue_WorkPackage workpackage, final int recordId)
	{
		final int adTableId = 12345;

		final I_C_Queue_Element e = InterfaceWrapperHelper.create(ctx, I_C_Queue_Element.class, trxName);
		e.setC_Queue_WorkPackage(workpackage);
		e.setAD_Table_ID(adTableId);

		e.setRecord_ID(recordId);
		InterfaceWrapperHelper.save(e);

		return e;
	}

	/**
	 * Creates an enqueues a workpackage to be processed by {@link TestQueue_InheritPriority_WorkPackageProcessor}.<br>
	 * The processor itself then creates another WP and verifies that the original WP's priority was forwarded to the new WP.
	 */
	@Test
	public void test_forwardWorkPackagePrio()
	{
		doTheTestWithPrio(ConstantWorkpackagePrio.low());
		doTheTestWithPrio(ConstantWorkpackagePrio.high());
	}

	private void doTheTestWithPrio(final ConstantWorkpackagePrio priorityToForward)
	{
		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IQueueProcessorFactory queueProcessorFactory = Services.get(IQueueProcessorFactory.class);
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final IWorkPackageQueue queueForEnqueuing = workPackageQueueFactory.getQueueForEnqueuing(ctx, TestQueue_InheritPriority_WorkPackageProcessor.class);

		// set the expected prio in our workpackage-processor
		TestQueue_InheritPriority_WorkPackageProcessor.expectedPriority = priorityToForward;

		// tell the processor to perform the verification, as oposed to returning directly
		TestQueue_InheritPriority_WorkPackageProcessor.returnDirectly = false;

		I_C_Queue_Block block1 = queueForEnqueuing
				.newBlock()
				.build();
		// creating the WP with this method because this is still the code under test and it is also still called by the modern builder API.
		@SuppressWarnings("deprecation")
		final I_C_Queue_WorkPackage wp1 = queueForEnqueuing.enqueueWorkPackage(block1, priorityToForward);
		createQueueElement(wp1, 456);
		wp1.setIsReadyForProcessing(true);

		InterfaceWrapperHelper.save(wp1);

		final Helper helper = new Helper();
		final I_C_Queue_Processor queueProcessorDef = helper.createQueueProcessor("Test_forwardWorkPackagePrio", 10, 10, 1000);

		final I_C_Queue_PackageProcessor retrievePackageProcessorDefByClass = queueDAO.retrievePackageProcessorDefByClass(ctx, TestQueue_InheritPriority_WorkPackageProcessor.class);
		helper.assignPackageProcessor(queueProcessorDef, retrievePackageProcessorDefByClass);

		final IWorkPackageQueue queueForPackageProcessing = workPackageQueueFactory.getQueueForPackageProcessing(queueProcessorDef);

		final IQueueProcessor processor = queueProcessorFactory.createSynchronousQueueProcessor(queueForPackageProcessing);
		processor.run();
		processor.shutdown();

		// expecting CountProcessed = two, because when we enqueue the 2nd WP in TestQueue_InheritPriority_WorkPackageProcessor (in order to verify it's prio),
		// the second WP will also be processed
		assertThat(processor.getStatisticsSnapshot().getCountProcessed(), is(2L));
		assertThat(processor.getStatisticsSnapshot().getCountErrors(), is(0L));
	}
}
