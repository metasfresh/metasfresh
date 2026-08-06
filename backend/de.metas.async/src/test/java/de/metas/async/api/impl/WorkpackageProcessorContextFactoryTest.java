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

package de.metas.async.api.impl;

import de.metas.async.AsyncBatchId;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@code isProcessingWorkpackage()}, the predicate {@code AsyncBatchService.executeBatch} uses to warn
 * that it is about to block a queue-processor thread.
 * <p>
 * Why it matters (measured 2026-08-05): waiting for an async batch from a thread that is itself processing a
 * workpackage holds one of that processor's pool slots for up to
 * {@code de.metas.async.AsyncBatchObserver.WaitTimeOutMS}. That is how a processor runs out of permits, which
 * is the precondition for the workpackage-lock leak fixed in
 * {@code AbstractQueueProcessor_UnlockOnNotProcessed_Test}. The predicate reads the same thread-local that
 * {@code WorkpackageProcessorTask} sets around each workpackage, so it must track set/clear exactly.
 */
public class WorkpackageProcessorContextFactoryTest
{
	private WorkpackageProcessorContextFactory contextFactory;

	@Before
	public void init()
	{
		contextFactory = new WorkpackageProcessorContextFactory();
	}

	@Test
	public void givenNothingSet_whenIsProcessingWorkpackage_thenFalse()
	{
		assertThat(contextFactory.isProcessingWorkpackage())
				.as("a plain caller thread (REST, a process, a test) is not processing a workpackage")
				.isFalse();
	}

	@Test
	public void givenWorkpackageAsyncBatchSet_whenIsProcessingWorkpackage_thenTrue()
	{
		// given: exactly what WorkpackageProcessorTask does before invoking the processor
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(AsyncBatchId.ofRepoId(1000001));

		assertThat(contextFactory.isProcessingWorkpackage()).isTrue();
	}

	@Test
	public void givenWorkpackageAsyncBatchCleared_whenIsProcessingWorkpackage_thenFalseAgain()
	{
		contextFactory.setThreadInheritedWorkpackageAsyncBatch(AsyncBatchId.ofRepoId(1000001));

		// when: what WorkpackageProcessorTask does once the workpackage is done
		final AsyncBatchId previous = contextFactory.setThreadInheritedWorkpackageAsyncBatch(null);

		assertThat(previous).as("setter returns the id it replaced").isEqualTo(AsyncBatchId.ofRepoId(1000001));
		assertThat(contextFactory.isProcessingWorkpackage())
				.as("the thread must not stay marked as processing after the workpackage finished")
				.isFalse();
	}

	@Test
	public void givenOnlyTheEnqueuingAsyncBatchSet_whenIsProcessingWorkpackage_thenFalse()
	{
		// given: the *enqueuing* async batch, a different thread-local - a REST caller sets this while
		// remaining a plain caller thread. Confusing the two would warn on every enqueue.
		contextFactory.setThreadInheritedAsyncBatch(AsyncBatchId.ofRepoId(1000002));

		assertThat(contextFactory.isProcessingWorkpackage())
				.as("setThreadInheritedAsyncBatch must not mark the thread as processing a workpackage")
				.isFalse();
	}
}
