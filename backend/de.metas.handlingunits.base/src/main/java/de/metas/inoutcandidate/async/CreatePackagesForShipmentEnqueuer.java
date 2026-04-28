/*
 * #%L
 * de-metas-salesorder
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

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

import static org.compiere.util.Env.getCtx;

@Service
public class CreatePackagesForShipmentEnqueuer
{
	public static final String WP_PARAM_M_InOut_ID = I_M_InOut.COLUMNNAME_M_InOut_ID;
	public static final String WP_PARAM_CREATE_AND_ADD_TO_TRANSPORTATION_Order = "addToTransportationOrder";

	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	public static CreatePackagesForShipmentEnqueuer newInstance()
	{
		return new CreatePackagesForShipmentEnqueuer();
	}

	private CreatePackagesForShipmentEnqueuer()
	{
	}

	public void enqueue(@NonNull final InOutId inOutId, final boolean addToTransportationOrder)
	{
		workPackageQueueFactory.getQueueForEnqueuing(getCtx(), CreatePackagesForShipmentWorkpackageProcessor.class)
				.newWorkPackage()
				.parameter(WP_PARAM_M_InOut_ID, inOutId)
				.parameter(WP_PARAM_CREATE_AND_ADD_TO_TRANSPORTATION_Order, addToTransportationOrder)
				.buildAndEnqueue();
	}
}
