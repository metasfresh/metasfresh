/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.async.processor.impl.planner;

import de.metas.async.QueueProcessorTestBase;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.X_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.processor.impl.MockedWorkpackageProcessor;
import de.metas.async.processor.impl.StaticMockedWorkpackageProcessor;
import de.metas.async.spi.IWorkpackageProcessor.Result;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * The async poll re-fetch used to run on every poll tick, even when the claim
 * ({@code query.updateDirectly(...)}) locked nothing. This test proves the {@code lockedCount == 0} guard skips
 * that re-fetch, and the sibling test proves the guard does not change behaviour for a productive poll.
 */
public class QueueProcessorPlanner_PollGuardTest extends QueueProcessorTestBase
{
	@Test
	public void refetch_is_skipped_when_nothing_claimed()
	{
		final I_C_Queue_PackageProcessor packageProcessorDef = helper.createPackageProcessor(ctx, StaticMockedWorkpackageProcessor.class);
		final I_C_Queue_Processor queueProcessorDef = helper.createQueueProcessor(StaticMockedWorkpackageProcessor.class.getName(), 1, 1000);
		helper.assignPackageProcessor(queueProcessorDef, packageProcessorDef);

		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);
		// note: NO workpackages enqueued -> the claim (updateDirectly) will lock 0 rows

		// register a spy wrapping the real IQueryBL, BEFORE the planner is constructed, so the planner's own
		// `queryBL` field (a plain `Services.get(IQueryBL.class)` eager field) picks up the spy. Pre-existing
		// singletons (e.g. the queue DAO) already hold the real instance and are unaffected, so this only
		// observes calls made by the planner itself.
		final IQueryBL realQueryBL = Services.get(IQueryBL.class);
		final IQueryBL queryBLSpy = Mockito.spy(realQueryBL);
		Services.registerService(IQueryBL.class, queryBLSpy);

		final IQueueProcessor processor = helper.newSynchronousQueueProcessor(workpackageQueue);
		SynchronousProcessorPlanner.executeNow(processor);

		// the re-fetch builder must never be invoked when nothing was claimed
		verify(queryBLSpy, never()).createQueryBuilder(I_C_Queue_WorkPackage.class);
	}

	@Test
	public void refetch_still_returns_claimed_package_with_predicates()
	{
		final I_C_Queue_PackageProcessor packageProcessorDef = helper.createPackageProcessor(ctx, StaticMockedWorkpackageProcessor.class);
		final I_C_Queue_Processor queueProcessorDef = helper.createQueueProcessor(StaticMockedWorkpackageProcessor.class.getName(), 1, 1000);
		helper.assignPackageProcessor(queueProcessorDef, packageProcessorDef);

		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 1, true); // markReadyForProcessing=true
		final I_C_Queue_WorkPackage workpackage = workpackages.get(0);

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setDefaultResult(Result.SUCCESS);

		final IQueueProcessor processor = helper.newSynchronousQueueProcessor(workpackageQueue);
		SynchronousProcessorPlanner.executeNow(processor);

		// the just-claimed row (Processed='N', IsError='N', IsReadyForProcessing='Y' at claim time) must still be
		// returned by the re-fetch (and thus processed) even after the new predicates are added to that query
		final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();
		assertThat(processedWorkpackages)
				.extracting(I_C_Queue_WorkPackage::getC_Queue_WorkPackage_ID)
				.containsExactly(workpackage.getC_Queue_WorkPackage_ID());

		InterfaceWrapperHelper.refresh(workpackage);
		assertThat(workpackage.isProcessed()).as("workpackage shall be processed").isTrue();
		assertThat(workpackage.isError()).as("workpackage shall not be in error").isFalse();

		helper.assertNothingLocked();
	}

	@Test
	public void ready_packages_processed_exactly_once_in_priority_order()
	{
		final I_C_Queue_PackageProcessor packageProcessorDef = helper.createPackageProcessor(ctx, StaticMockedWorkpackageProcessor.class);
		final I_C_Queue_Processor queueProcessorDef = helper.createQueueProcessor(StaticMockedWorkpackageProcessor.class.getName(), 1, 1000);
		helper.assignPackageProcessor(queueProcessorDef, packageProcessorDef);

		final IWorkPackageQueue workpackageQueue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, StaticMockedWorkpackageProcessor.class);

		final List<I_C_Queue_WorkPackage> workpackages = helper.createAndEnqueueWorkpackages(workpackageQueue, 3, true); // markReadyForProcessing=true

		// assign distinct priorities, out of creation order, so processing order proves priority-based ordering
		workpackages.get(0).setPriority(X_C_Queue_WorkPackage.PRIORITY_Low);
		InterfaceWrapperHelper.save(workpackages.get(0));
		workpackages.get(1).setPriority(X_C_Queue_WorkPackage.PRIORITY_Urgent);
		InterfaceWrapperHelper.save(workpackages.get(1));
		workpackages.get(2).setPriority(X_C_Queue_WorkPackage.PRIORITY_Medium);
		InterfaceWrapperHelper.save(workpackages.get(2));

		final MockedWorkpackageProcessor workpackageProcessor = StaticMockedWorkpackageProcessor.getMockedWorkpackageProcessor();
		workpackageProcessor.setDefaultResult(Result.SUCCESS);

		final IQueueProcessor processor = helper.newSynchronousQueueProcessor(workpackageQueue);
		SynchronousProcessorPlanner.executeNow(processor);

		final List<I_C_Queue_WorkPackage> processedWorkpackages = workpackageProcessor.getProcessedWorkpackages();

		// exactly once each (MockedWorkpackageProcessor itself asserts no double-processing), and in Priority order
		assertThat(processedWorkpackages)
				.extracting(I_C_Queue_WorkPackage::getC_Queue_WorkPackage_ID)
				.containsExactly(
						workpackages.get(1).getC_Queue_WorkPackage_ID(), // Urgent = "1"
						workpackages.get(2).getC_Queue_WorkPackage_ID(), // Medium = "5"
						workpackages.get(0).getC_Queue_WorkPackage_ID()); // Low = "7"

		for (final I_C_Queue_WorkPackage wp : workpackages)
		{
			InterfaceWrapperHelper.refresh(wp);
			assertThat(wp.isProcessed()).as("wp %s shall be processed", wp.getC_Queue_WorkPackage_ID()).isTrue();
			assertThat(wp.isError()).as("wp %s shall not be in error", wp.getC_Queue_WorkPackage_ID()).isFalse();
		}

		helper.assertNothingLocked();
	}
}
