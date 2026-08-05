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

package de.metas.async.service;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IWorkpackageProcessorContextFactory;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the guard that forbids waiting for an async batch from a thread that is itself processing a
 * workpackage.
 * <p>
 * Concrete failure it prevents (measured 2026-08-04, cucumber
 * {@code automateOrderCandToOrderAndShipmentAndInvoice.feature}): {@code ProcessOLCandsWorkpackageProcessor}
 * runs with {@code C_Queue_Processor.PoolSize = 1}. Its workpackage called
 * {@code ShipmentService.generateShipments} → {@code AsyncBatchService.executeBatch} →
 * {@code AsyncBatchObserver.waitToBeProcessed}, which blocked that processor's only thread for the full
 * {@code de.metas.async.AsyncBatchObserver.WaitTimeOutMS} (300 000 ms) and then failed with a
 * {@code TimeoutException}, surfacing as an HTTP 400 from
 * {@code PUT api/v2/orders/sales/candidates/process}.
 */
public class AsyncBatchServiceTest
{
	private IWorkpackageProcessorContextFactory contextFactory;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		contextFactory = Services.get(IWorkpackageProcessorContextFactory.class);
	}

	@After
	public void tearDown()
	{
		// the thread-local is inheritable and the test threads are pooled -> never leak it into the next test
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(null);
	}

	@Test
	public void givenThreadIsProcessingAWorkpackage_whenIsWaitingForAsyncBatchAllowed_thenFalse()
	{
		// given: this thread is processing a workpackage, exactly as WorkpackageProcessorTask sets it up
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(AsyncBatchId.ofRepoId(1000001));

		// when / then: waiting for any async batch from here would block a queue-processor thread
		assertThat(AsyncBatchService.isWaitingForAsyncBatchAllowed()).isFalse();
	}

	@Test
	public void givenThreadIsNotProcessingAWorkpackage_whenIsWaitingForAsyncBatchAllowed_thenTrue()
	{
		// given: a plain caller thread (REST/Tomcat, a process, a test) - nothing set
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(null);

		// when / then
		assertThat(AsyncBatchService.isWaitingForAsyncBatchAllowed()).isTrue();
	}
}
