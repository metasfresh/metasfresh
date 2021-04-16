/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.async.event;

import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static de.metas.async.Async_Constants.WORKPACKAGE_LIFECYCLE_TOPIC;
import static de.metas.async.event.WorkpackageProcessedEvent.Status.DONE;
import static de.metas.async.event.WorkpackageProcessedEvent.Status.ERROR;

/**
 * Use this if you want to enqueue asynchronous-queue-workpackages and then wait for them to be done.
 * See {@link #create(IEventBus)}.
 * 
 * Note: if the workpackage is processed on the same machine you might also take a look as {@link de.metas.async.processor.impl.SyncQueueProcessorListener}
 */
@ToString
public class WorkpackagesProcessedWaiter
{
	public static final WorkpackagesProcessedWaiter NOOP = new WorkpackagesProcessedWaiter();

	/**
	 * Creates a new instance. This registers an event listener that will count the processed workpackages. 
	 * 
	 * @param eventBus the event bus must have the topic {@link de.metas.async.Async_Constants#WORKPACKAGE_LIFECYCLE_TOPIC}.
	 */
	public static WorkpackagesProcessedWaiter create(@NonNull final IEventBus eventBus)
	{
		final UUID correlationId = UUID.randomUUID();
		return new WorkpackagesProcessedWaiter(eventBus, correlationId);
	}

	private final boolean isNoopConsumer;
	private int eventsReceivedBeforeWaitStarted = 0;

	@Getter
	private final UUID correlationId;

	private CountDownLatch countDownLatch = null;
	private final IEventListener eventListener;
	private final IEventBus eventBus;

	private WorkpackagesProcessedWaiter()
	{
		this.isNoopConsumer = true;

		this.correlationId = null;
		this.eventListener = null;
		this.eventBus = null;
	}

	private WorkpackagesProcessedWaiter(
			@NonNull final IEventBus eventBus,
			@NonNull final UUID correlationId)
	{
		this.isNoopConsumer = false;

		Check.errorUnless(WORKPACKAGE_LIFECYCLE_TOPIC.getName().equals(eventBus.getTopicName()),
				"The given eventbus needs to have topic.name={}, but instead has topic.name={}", WORKPACKAGE_LIFECYCLE_TOPIC.getName(), eventBus.getTopicName());

		this.correlationId = correlationId;
		this.eventBus = eventBus;

		this.eventListener = eventBus.subscribeOn(WorkpackageProcessedEvent.class, this::accept);
	}

	private void accept(@NonNull final WorkpackageProcessedEvent workpackageLifeCycleEvent)
	{
		if (isNoopConsumer)
		{
			return; // accept "should" not be called because we are not subscribed anywhere
		}

		final boolean eventShallBeCounted =
				(DONE.equals(workpackageLifeCycleEvent.getStatus()) || ERROR.equals(workpackageLifeCycleEvent.getStatus()))
						&& correlationId.equals(workpackageLifeCycleEvent.getCorrelationId());
		if (!eventShallBeCounted)
		{
			return;
		}

		synchronized (this)
		{
			if (countDownLatch != null)
			{
				countDownLatch.countDown();
			}
			else
			{
				eventsReceivedBeforeWaitStarted++;
			}
		}
	}

	public void waitForWorkpackagesDone(final int enqueuedWorkpackages)
	{
		if (isNoopConsumer)
		{
			return;
		}

		synchronized (this)
		{
			final int remainingWorkpackages = enqueuedWorkpackages - eventsReceivedBeforeWaitStarted;
			if(remainingWorkpackages<=0)
			{
				eventBus.unsubscribe(eventListener);
				return; // the workpackages were already processed before we even started waiting; => nothing more to do
			}
			countDownLatch = new CountDownLatch(remainingWorkpackages);
		}
		try
		{
			countDownLatch.await();
		}
		catch (final InterruptedException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage().setParameter("this", this);
		}
		finally
		{
			eventBus.unsubscribe(eventListener);
		}
	}
}
