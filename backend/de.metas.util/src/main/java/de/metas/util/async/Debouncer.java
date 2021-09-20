/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.util.async;

import com.google.common.base.MoreObjects;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Accumulates items and sends them to a {@link Consumer} after a specified amount of time.
 *
 * @param <T>
 */
public final class Debouncer<T>
{
	// Params
	@Nullable
	private final String name; // having it as a field for debugging purposes
	@NonNull
	private final ScheduledExecutorService executor;
	private final int bufferMaxSize;
	private final int delayInMillis;
	@NonNull
	private final Consumer<List<T>> consumer;

	// State
	private final Object lock = new Object();
	private long dueTime = -1;
	private final Collection<T> buffer;

	@Builder
	private Debouncer(
			@Nullable final String name,
			@NonNull final Consumer<List<T>> consumer,
			final int bufferMaxSize,
			final int delayInMillis,
			final boolean distinct)
	{
		Check.assumeGreaterThanZero(delayInMillis, "delayInMillis");

		this.name = name;
		this.executor = createExecutor(name);
		this.consumer = consumer;
		this.bufferMaxSize = bufferMaxSize > 0 ? bufferMaxSize : -1;
		this.delayInMillis = delayInMillis;
		this.buffer = distinct
				? new LinkedHashSet<>(bufferMaxSize)
				: new ArrayList<>(bufferMaxSize);
	}

	private static ScheduledExecutorService createExecutor(@Nullable final String name)
	{
		final String threadNamePrefix = Check.isNotBlank(name)
				? name
				: Debouncer.class.getSimpleName();

		final CustomizableThreadFactory threadFactory = new CustomizableThreadFactory(threadNamePrefix + "-");
		threadFactory.setDaemon(true);

		return new ScheduledThreadPoolExecutor(1, threadFactory);
	}

	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", name)
				.add("delayInMillis", delayInMillis)
				.add("bufferMaxSize", bufferMaxSize)
				.toString();
	}

	public void addAll(@NonNull final List<T> items)
	{
		if (items.isEmpty())
		{
			return;
		}

		synchronized (lock)
		{
			buffer.addAll(items);
			updateDueTimeAndScheduleTask();
		}
	}

	public void add(@NonNull final T item)
	{
		synchronized (lock)
		{
			buffer.add(item);
			updateDueTimeAndScheduleTask();
		}
	}

	private void updateDueTimeAndScheduleTask()
	{
		final boolean taskWasAlreadyScheduled = dueTime > 0;

		final int delayInMillisEffective = bufferMaxSize > 0 && buffer.size() >= bufferMaxSize
				? 0 // ASAP
				: delayInMillis;
		// we don't use SystemTime because in our usual tests it's rigged to return a fixed value. Fee free to use it here, too - maybe with an enhanced Timesource - when it makes sense
		dueTime = System.currentTimeMillis() + delayInMillisEffective;
		//System.out.println(this + " - new dueTime=" + dueTime);

		if (!taskWasAlreadyScheduled)
		{
			executor.schedule(this::executeTask, delayInMillisEffective, TimeUnit.MILLISECONDS);
		}
	}

	private void executeTask()
	{
		ArrayList<T> itemsToConsume = null;
		final long remaining;
		final int bufferSize;

		synchronized (lock)
		{
			// we don't use SystemTime because in our usual tests it's rigged to return a fixed value. Fee free to use it here, too - maybe with an enhanced Timesource - when it makes sense
			remaining = dueTime - System.currentTimeMillis();
			bufferSize = buffer.size();

			//
			// Re-schedule task
			if (remaining > 0
					&& bufferSize > 0
					&& (bufferMaxSize <= 0 || bufferMaxSize > bufferSize))
			{
				//System.out.println("" + this + " - executeTask:Rescheduling in " + remaining + " ms(bufferSize = " + bufferSize + ") ");
				executor.schedule(this::executeTask, remaining, TimeUnit.MILLISECONDS);
			}
			//
			// Mark as terminated and invoke the consumer
			else
			{
				dueTime = -1;

				if (bufferSize > 0)
				{
					itemsToConsume = new ArrayList<>(buffer);
					buffer.clear();
				}
			}
		}

		if (itemsToConsume != null)
		{
			//System.out.println("" + this + " - executeTask: consuming " + bufferSize + " items(remaining was" + remaining + "ms) ");
			consumer.accept(itemsToConsume);
		}

	}

	/*
	public static void main(String[] args) throws InterruptedException
	{
		final Debouncer<Integer> debouncer = Debouncer.<Integer>builder()
				.name("test-debouncer")
				.delayInMillis(500)
				.bufferMaxSize(500)
				.consumer(items -> System.out.println("Got " + items.size() + " items: "
						+ items.get(0) + "..." + items.get(items.size() - 1)))
				.build();

		System.out.println("Start sending events...");

		for (int i = 1; i <= 100; i++)
		{
			debouncer.add(i);
			//Thread.yield();
			Thread.sleep(0, 1);
		}

		System.out.println("Enqueuing done. Waiting a bit to finish...");
		Thread.sleep(5000);
	}
	*/

}
