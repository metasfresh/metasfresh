/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.async;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableMap;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class UpdateInvalidShipmentSchedulesScheduler extends WorkpackagesOnCommitSchedulerTemplate<ShipmentSchedulesUpdateSchedulerRequest>
{
	private static final Logger logger = LogManager.getLogger(UpdateInvalidShipmentSchedulesScheduler.class);

	public UpdateInvalidShipmentSchedulesScheduler(final boolean createOneWorkpackagePerAsyncBatch)
	{
		super(UpdateInvalidShipmentSchedulesWorkpackageProcessor.class);
		super.setCreateOneWorkpackagePerAsyncBatch(createOneWorkpackagePerAsyncBatch);
	}

	@Override
	protected Properties extractCtxFromItem(@NonNull final ShipmentSchedulesUpdateSchedulerRequest item)
	{
		return item.getCtx();
	}

	@Override
	protected String extractTrxNameFromItem(@NonNull final ShipmentSchedulesUpdateSchedulerRequest item)
	{
		return item.getTrxName();
	}

	@Override
	protected Object extractModelToEnqueueFromItem(final Collector collector, final ShipmentSchedulesUpdateSchedulerRequest item)
	{
		return null; // there is no actual model to be collected
	}

	/**
	 * One processable workpackage recomputes all flagged schedules, so none is created if more than one is already queued.
	 * <p>
	 * Decided here, not when the flag is written: for a flag written in a transaction this runs after the commit, so the queued
	 * workpackages (which only count while not yet started) start after the commit and see the flag. Decided while the flag was
	 * still uncommitted, they could all run before the commit, leaving the committed flag without a workpackage.
	 */
	@Override
	protected boolean isSkipCreatingWorkpackages(@NonNull final IWorkPackageQueue workPackageQueue)
	{
		final int alreadyEnqueuedWPs = workPackageQueue.size();
		if (alreadyEnqueuedWPs > 1)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Not scheduling WP because there are {} processable workpackages, and we just need one to revalidate all flagged schedules: {}",
					alreadyEnqueuedWPs,
					UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return true;
		}
		return false;
	}

	/**
	 * A retry pass carries its generation as workpackage parameter; a normal pass carries none (i.e. 0).
	 * Retry requests are scheduled out of trx, so each gets its own collector and the generations of different requests never meet in one workpackage.
	 */
	@Override
	protected Map<String, Object> extractParametersFromItem(@NonNull final ShipmentSchedulesUpdateSchedulerRequest item)
	{
		if (item.getRetryGeneration() <= 0)
		{
			return ImmutableMap.of();
		}
		return ImmutableMap.of(UpdateInvalidShipmentSchedulesWorkpackageProcessor.PARAM_RecomputeRetryGeneration, item.getRetryGeneration());
	}

	@Override
	protected boolean isEnqueueWorkpackageWhenNoModelsEnqueued()
	{
		return true;
	}

	@Override
	public Optional<AsyncBatchId> extractAsyncBatchFromItem(@NonNull final Collector collector, @NonNull final ShipmentSchedulesUpdateSchedulerRequest request)
	{
		return Optional.ofNullable(request.getAsyncBatchId());
	}
}
