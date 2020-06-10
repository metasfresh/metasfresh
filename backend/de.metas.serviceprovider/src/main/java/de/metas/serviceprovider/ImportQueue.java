/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ImportQueue<T>
{
	private static final Logger log = LogManager.getLogger(ImportQueue.class);

	private final LinkedBlockingQueue<T> queue;
	private final String logPrefix;

	public ImportQueue(final int queueCapacity, final String logPrefix)
	{
		this.queue = new LinkedBlockingQueue<>(queueCapacity);
		this.logPrefix = logPrefix;
	}

	@NonNull
	public ImmutableList<T> drainAll()
	{
		final ArrayList<T> objectList = new ArrayList<>();

		try
		{
			final Optional<T> object = Optional.ofNullable(queue.poll(2, TimeUnit.SECONDS));
			object.ifPresent(objectList::add);

			queue.drainTo(objectList);

			log.debug(" {} drained {} objects for processing!", logPrefix, objectList.size());
		}
		catch (final InterruptedException e)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(logPrefix + e.getMessage(),e);
		}

		return ImmutableList.copyOf(objectList);
	}

	public boolean isEmpty()
	{
		return queue.isEmpty();
	}

	public void add(@NonNull final T object)
	{
		try
		{
			queue.put(object);
		}
		catch (final InterruptedException e)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(logPrefix + e.getMessage(),e);
		}
	}
}
