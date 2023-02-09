/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.async;

import de.metas.async.AsyncBatchId;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;
import java.util.Properties;

public class UpdateInvalidShipmentSchedulesScheduler extends WorkpackagesOnCommitSchedulerTemplate<ShipmentSchedulesUpdateSchedulerRequest>
{
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