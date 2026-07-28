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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
	private static final Logger logger = LoggerFactory.getLogger(Debouncer.class);

	// Params
	@Nullable
	private final String name; // having it as a field for debugging purposes
	@NonNull
	private final ScheduledExecutorService executor;
	private final int bufferMaxSize;
	/**
	 * Hard upper bound on the in-memory buffer — a <b>load-shedding</b> drop cap (NOT backpressure: it does not
	 * slow the producer; it discards overflow). Unlike {@link #bufferMaxSize} — which only controls the
	 * scheduling delay — this is an absolute ceiling: once reached, the oldest buffered items are dropped (and
	 * counted/logged) instead of letting the buffer grow without bound, preventing an OutOfMemoryError when the
	 * consumer is wedged/slower than the producers (e.g. blocked on an exhausted connection pool).
	 * <p>
	 * <b>OPT-IN.</b> Defaults to {@code -1} ("no cap" = the pre-existing unbounded behaviour) so existing callers
	 * are never silently capped — a cap means dropping items, which is data loss for a caller whose items are not
	 * recomputable. Set it explicitly only where dropping the oldest pending items under overload is acceptable
	 * (e.g. idempotent, re-computable-on-next-event sync requests).
	 */
	private final int bufferHardLimit;
	private final int delayInMillis;
	@NonNull
	private final Consumer<List<T>> consumer;

	// State
	private final Object lock = new Object();
	private long dueTime = -1;
	private final Collection<T> buffer;
	private long droppedItemsCount = 0; // guarded by lock

	@Builder
	private Debouncer(
			@Nullable final String name,
			@NonNull final Consumer<List<T>> consumer,
			final int bufferMaxSize,
			final int bufferHardLimit,
			final int delayInMillis,
			final boolean distinct)
	{
		Check.assumeGreaterThanZero(delayInMillis, "delayInMillis");

		this.name = name;
		this.executor = createExecutor(name);
		this.consumer = consumer;
		this.bufferMaxSize = bufferMaxSize > 0 ? bufferMaxSize : -1;
		// Opt-in only: no cap unless the caller sets one. Deriving a cap from bufferMaxSize would retroactively
		// drop items for existing unbounded callers (e.g. the process-log debouncer) = silent data loss.
		this.bufferHardLimit = bufferHardLimit > 0 ? bufferHardLimit : -1;
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

		final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory);

		// Allow the core thread to terminate after 60s of idleness.
		// Without this, each Debouncer instance keeps its thread alive forever,
		// even after shutdown(). Since ProcessExecutionResult creates a new Debouncer
		// per process execution, this leaked ~1 thread per execution.
		//
		// This is safe for long-lived singleton debouncers (e.g. WebsocketSender):
		// the executor stays alive and automatically creates a new thread when
		// the next task is scheduled via add().
		executor.setKeepAliveTime(60, TimeUnit.SECONDS);
		executor.allowCoreThreadTimeOut(true);

		return executor;
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
			enforceHardLimit();
			updateDueTimeAndScheduleTask();
		}
	}

	public void add(@NonNull final T item)
	{
		synchronized (lock)
		{
			buffer.add(item);
			enforceHardLimit();
			updateDueTimeAndScheduleTask();
		}
	}

	/**
	 * Backpressure: enforce {@link #bufferHardLimit} by dropping the OLDEST buffered items. Must be called while
	 * holding {@link #lock}. Dropping (rather than blocking the producer) is the correct overload response here:
	 * the buffer only grows unbounded when the consumer cannot keep up (e.g. wedged on an exhausted connection
	 * pool), and in that state blocking the producers would only spread the stall; the stalest pending items are
	 * shed instead, and the drop is counted + logged. A dropped item is recomputed on the next triggering event.
	 */
	private void enforceHardLimit()
	{
		if (bufferHardLimit <= 0 || buffer.size() <= bufferHardLimit)
		{
			return;
		}

		long dropped = 0;
		final Iterator<T> it = buffer.iterator();
		while (buffer.size() > bufferHardLimit && it.hasNext())
		{
			it.next();
			it.remove();
			dropped++;
		}

		if (dropped > 0)
		{
			droppedItemsCount += dropped;
			logger.warn("Debouncer {}: buffer hard limit {} reached — dropped {} oldest item(s) (total dropped so far: {}). "
							+ "The consumer is not keeping up; check for a wedged/slow consumer (e.g. an exhausted DB connection pool).",
					name, bufferHardLimit, dropped, droppedItemsCount);
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

	public int getCurrentBufferSize()
	{
		synchronized (lock)
		{
			return buffer.size();
		}
	}

	/** Number of items dropped so far due to the {@link #bufferHardLimit} backpressure (monitoring / tests). */
	public long getDroppedItemsCount()
	{
		synchronized (lock)
		{
			return droppedItemsCount;
		}
	}

	public void processAndClearBufferSync()
	{
		synchronized (lock)
		{
			if (!buffer.isEmpty())
			{
				final ArrayList<T> itemsToConsume = new ArrayList<>(buffer);

				consumer.accept(itemsToConsume);

				buffer.clear();

			}
		}
	}

	public void shutdown()
	{
		executor.shutdown();
	}

	public void purgeBuffer()
	{
		synchronized (lock)
		{
			buffer.clear();
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
