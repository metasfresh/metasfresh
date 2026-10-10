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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.api.IEnqueueResult;
import de.metas.async.eventbus.AsyncBatchEventBusService;
import de.metas.async.eventbus.AsyncBatchNotifyRequest;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.event.log.EventLogUserService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AsyncBatchServiceTest
{
	private static final String NO_OBSERVER_TO_REMOVE_MSG = "No observer registered that can be removed";

	private AsyncBatchId asyncBatchId;

	private Logger observerLogger;
	private ListAppender<ILoggingEvent> observerLogAppender;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_Async_Batch asyncBatchRecord = InterfaceWrapperHelper.newInstance(I_C_Async_Batch.class);
		InterfaceWrapperHelper.saveRecord(asyncBatchRecord);
		asyncBatchId = AsyncBatchId.ofRepoId(asyncBatchRecord.getC_Async_Batch_ID());

		observerLogger = (Logger)LoggerFactory.getLogger(AsyncBatchObserver.class);
		observerLogAppender = new ListAppender<>();
		observerLogAppender.start();
		observerLogger.addAppender(observerLogAppender);
	}

	@AfterEach
	void afterEach()
	{
		observerLogger.detachAppender(observerLogAppender);
	}

	/**
	 * A normal run registers once and removes exactly that one registration: nothing is left behind,
	 * and the observer never tries to remove a registration that is already gone.
	 */
	@Test
	void executeBatch_removesItsRegistrationExactlyOnce()
	{
		final AsyncBatchObserver observer = new AsyncBatchObserver();
		final AsyncBatchService asyncBatchService = newAsyncBatchService(observer);

		asyncBatchService.executeBatch(() -> enqueueOneAlreadyProcessedWorkPackage(observer), asyncBatchId);

		assertThat(observer.isAsyncBatchObserved(asyncBatchId)).isFalse();
		assertThat(observer.getStartMonitoringTimestamp(asyncBatchId)).as("monitoring lock released").isEmpty();
		assertThat(observerLogAppender.list)
				.filteredOn(event -> event.getLevel() == Level.WARN)
				.extracting(ILoggingEvent::getFormattedMessage)
				.noneMatch(message -> message.contains(NO_OBSERVER_TO_REMOVE_MSG));
	}

	/**
	 * Caller A runs {@code executeBatch}. As soon as A's batch is done, caller B starts observing the same async batch.
	 * A's cleanup must only remove A's own registration; B's registration (and its monitoring lock) must survive it.
	 */
	@Test
	void executeBatch_cleanupDoesNotRemoveTheRegistrationOfAnotherCallerOnTheSameAsyncBatch() throws Exception
	{
		final AtomicReference<Thread> callerB = new AtomicReference<>();
		final AtomicReference<Throwable> callerBFailure = new AtomicReference<>();

		final AsyncBatchObserver observer = new AsyncBatchObserver()
		{
			@Override
			public void waitToBeProcessed(@NonNull final AsyncBatchId id)
			{
				super.waitToBeProcessed(id);

				// caller A's batch is done; caller B now starts observing the same async batch
				final Thread thread = new Thread(() -> {
					try
					{
						observeOn(id);
					}
					catch (final Throwable t)
					{
						callerBFailure.set(t);
					}
				}, "callerB");
				callerB.set(thread);
				thread.start();

				// Return to caller A only once a registration is in place for this id.
				// If A's registration were already gone here, this waits until B's registration replaced it.
				waitUntil(() -> isAsyncBatchObserved(id) || callerBFailure.get() != null);
			}
		};
		final AsyncBatchService asyncBatchService = newAsyncBatchService(observer);

		asyncBatchService.executeBatch(() -> enqueueOneAlreadyProcessedWorkPackage(observer), asyncBatchId);

		callerB.get().join(Duration.ofSeconds(30).toMillis());
		assertThat(callerB.get().isAlive()).as("caller B finished observeOn").isFalse();
		assertThat(callerBFailure.get()).as("caller B failure").isNull();

		assertThat(observer.isAsyncBatchObserved(asyncBatchId)).as("caller B's registration survived caller A's cleanup").isTrue();
		assertThat(observer.getStartMonitoringTimestamp(asyncBatchId)).as("caller B's monitoring lock survived caller A's cleanup").isPresent();

		observer.removeObserver(asyncBatchId); // caller B's own cleanup
		assertThat(observer.isAsyncBatchObserved(asyncBatchId)).isFalse();
	}

	/**
	 * Caller B observes an async batch whose work is still running. Caller A's {@code executeBatch} on the same async batch
	 * gives up waiting for B's batch and fails in {@code observeOn}. A never registered anything, so its failure must not remove B's registration.
	 */
	@Test
	void executeBatch_failingToObserve_doesNotRemoveTheRegistrationOfAnotherCaller()
	{
		Services.get(ISysConfigBL.class).setValue(Async_Constants.SYS_Config_WaitTimeOutMS, 100, ClientId.SYSTEM, OrgId.ANY);

		final AsyncBatchObserver observer = new AsyncBatchObserver();
		final AsyncBatchService asyncBatchService = newAsyncBatchService(observer);

		observer.observeOn(asyncBatchId); // caller B; its batch never completes within this test

		assertThatThrownBy(() -> asyncBatchService.executeBatch(() -> enqueueOneAlreadyProcessedWorkPackage(observer), asyncBatchId))
				.as("caller A times out waiting for caller B's batch")
				.isInstanceOf(AdempiereException.class);

		assertThat(observer.isAsyncBatchObserved(asyncBatchId)).as("caller B's registration survived caller A's failure").isTrue();
		assertThat(observer.getStartMonitoringTimestamp(asyncBatchId)).as("caller B's monitoring lock survived caller A's failure").isPresent();

		observer.removeObserver(asyncBatchId); // caller B's own cleanup
	}

	@NonNull
	private static AsyncBatchService newAsyncBatchService(@NonNull final AsyncBatchObserver observer)
	{
		return new AsyncBatchService(
				observer,
				new AsyncBatchEventBusService(PlainEventBusFactory.newInstance(), new EventLogUserService()));
	}

	/**
	 * Simulates a workPackageEnqueuer whose single workpackage gets processed right away, so the batch is done once enqueueing is done.
	 */
	@NonNull
	private IEnqueueResult enqueueOneAlreadyProcessedWorkPackage(@NonNull final AsyncBatchObserver observer)
	{
		observer.handleRequest(AsyncBatchNotifyRequest.builder()
									   .clientId(ClientId.METASFRESH)
									   .asyncBatchId(asyncBatchId.getRepoId())
									   .noOfEnqueuedWPs(1)
									   .noOfProcessedWPs(1)
									   .noOfErrorWPs(0)
									   .build());
		return () -> 1;
	}

	private static void waitUntil(@NonNull final java.util.function.BooleanSupplier condition)
	{
		final Instant deadline = Instant.now().plusSeconds(30);
		while (!condition.getAsBoolean())
		{
			if (Instant.now().isAfter(deadline))
			{
				throw new AssertionError("Condition not met within 30s");
			}
			try
			{
				Thread.sleep(10);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new AssertionError(e);
			}
		}
	}
}
