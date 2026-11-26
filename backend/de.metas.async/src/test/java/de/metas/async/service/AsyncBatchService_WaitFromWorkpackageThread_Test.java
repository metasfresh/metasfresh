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
import de.metas.async.eventbus.AsyncBatchEventBusService;
import de.metas.util.Loggables;
import de.metas.util.PlainStringLoggable;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link AsyncBatchService#executeBatch} blocks the calling thread until the async batch is done. When that thread is itself
 * a queue-processor thread (i.e. it is processing a workpackage of another async batch), it holds one of the processor's
 * permits for up to {@code de.metas.async.AsyncBatchObserver.WaitTimeOutMS}, which can starve the very processor that
 * is supposed to process the awaited workpackages. That situation must be visible in the logs.
 */
public class AsyncBatchService_WaitFromWorkpackageThread_Test
{
	private static final String WARN_MARKER = "from a thread that is itself processing workpackage-batch";

	private final AsyncBatchId awaitedAsyncBatchId = AsyncBatchId.ofRepoId(20);

	private IWorkpackageProcessorContextFactory contextFactory;
	private AsyncBatchService asyncBatchService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		contextFactory = Services.get(IWorkpackageProcessorContextFactory.class);
		asyncBatchService = new AsyncBatchService(
				Mockito.mock(AsyncBatchObserver.class),
				Mockito.mock(AsyncBatchEventBusService.class));
	}

	@AfterEach
	public void afterEach()
	{
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(null);
	}

	@Test
	public void givenThreadIsProcessingAWorkpackageBatch_whenExecuteBatch_thenWarns()
	{
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(AsyncBatchId.ofRepoId(10));

		final PlainStringLoggable loggable = executeBatchAndCaptureLogs();

		assertThat(loggable.getSingleMessages())
				.anySatisfy(msg -> assertThat(msg)
						.contains(WARN_MARKER)
						.contains("C_Async_Batch_ID: 20")
						.contains("workpackage-batch 10"));
	}

	@Test
	public void givenThreadIsNotProcessingAWorkpackageBatch_whenExecuteBatch_thenDoesNotWarn()
	{
		final PlainStringLoggable loggable = executeBatchAndCaptureLogs();

		assertThat(loggable.getSingleMessages()).noneSatisfy(msg -> assertThat(msg).contains(WARN_MARKER));
	}

	private PlainStringLoggable executeBatchAndCaptureLogs()
	{
		final PlainStringLoggable loggable = Loggables.newPlainStringLoggable();
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(loggable))
		{
			// zero enqueued workpackages => executeBatch does not wait, so the test needs no async infrastructure
			asyncBatchService.executeBatch(() -> () -> 0, awaitedAsyncBatchId);
		}
		return loggable;
	}
}
