/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.async;

import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inout.ShipmentScheduleId;
import de.metas.shipper.gateway.commons.CarrierAdviseCommand;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.Env;

import javax.annotation.Nullable;

public class AdviseDeliveryOrderWorkpackageProcessor extends WorkpackageProcessorAdapter
{

	public static void enqueueOnTrxCommit(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

		workPackageQueueFactory
				.getQueueForEnqueuing(AdviseDeliveryOrderWorkpackageProcessor.class)
				.newWorkPackage()
				.setAsyncBatchId(asyncBatchId)
				.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
				.bindToThreadInheritedTrx()
				.parameters()
				.setParameter(PARAM_ShipmentScheduleId, shipmentScheduleId)
				.end()
				.buildAndEnqueue();
	}

	private static final String PARAM_ShipmentScheduleId = "ShipmentScheduleId";

	@Override
	public boolean isRunInTransaction()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName_NOTUSED)
	{
		final int shipmentSchedule = getParameters().getParameterAsInt(PARAM_ShipmentScheduleId, -1);
		CarrierAdviseCommand.of(ShipmentScheduleId.ofRepoId(shipmentSchedule))
				.execute();

		return Result.SUCCESS;
	}

}
