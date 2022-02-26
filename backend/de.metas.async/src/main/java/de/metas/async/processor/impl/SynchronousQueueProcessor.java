package de.metas.async.processor.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.api.IWorkpackageLogsRepository;
import lombok.NonNull;

import java.util.UUID;

public class SynchronousQueueProcessor extends AbstractQueueProcessor
{
	private final String name;

	public SynchronousQueueProcessor(
			@NonNull final IWorkPackageQueue queue,
			@NonNull final IWorkpackageLogsRepository logsRepository)
	{
		super(queue, logsRepository);
		this.name = "SynchronousQueueProcessor_" + UUID.randomUUID();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	protected void executeTask(final WorkpackageProcessorTask task)
	{
		task.run();
	}

	@Override
	public void shutdownExecutor()
	{
		// nothing
	}

	@Override
	public boolean isAvailableToWork()
	{
		return true;
	}
}
