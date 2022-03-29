/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.impl.AbstractQueueProcessor;
import de.metas.async.processor.impl.SynchronousQueueProcessor;
import lombok.NonNull;

import java.util.Set;

public class SynchronousProcessorPlanner extends QueueProcessorPlanner
{
	public static void executeNow(@NonNull final IQueueProcessor queueProcessor)
	{
		final SynchronousProcessorPlanner synchronousProcessorPlanner = new SynchronousProcessorPlanner();
		synchronousProcessorPlanner.addQueueProcessor(queueProcessor);
		synchronousProcessorPlanner.start();
	}

	@Override
	protected boolean handleWorkPackageProcessing(final @NonNull IQueueProcessor queueProcessor, final @NonNull I_C_Queue_WorkPackage workPackage)
	{
		return queueProcessor.processLockedWorkPackage(workPackage);
	}

	@Override
	protected void startPlanner()
	{
		isRunning.set(true);
		run();
	}

	@Override
	protected void shutdownPlanner()
	{
		isRunning.set(false);
	}

	@Override
	protected Set<Class<? extends AbstractQueueProcessor>> getSupportedQueueProcessors()
	{
		return ImmutableSet.of(SynchronousQueueProcessor.class);
	}

	@Override
	protected boolean isStopOnFailedRun()
	{
		return true;
	}
}
