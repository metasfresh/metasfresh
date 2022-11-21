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

package de.metas.deliveryplanning;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import java.util.Properties;

public class OutgoingDeliveryPlanningWorkingProcessor extends WorkpackageProcessorAdapter
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	public static void createWorkpackage(final I_M_ShipmentSchedule shipmentSchedule)
	{
		SCHEDULER.schedule(shipmentSchedule);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<I_M_ShipmentSchedule> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<I_M_ShipmentSchedule>(OutgoingDeliveryPlanningWorkingProcessor.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final I_M_ShipmentSchedule model)
		{
			return model != null && model.getM_ShipmentSchedule_ID() > 0;
		}

		@Override
		protected Properties extractCtxFromItem(final I_M_ShipmentSchedule item)
		{
			return Env.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final I_M_ShipmentSchedule item)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final I_M_ShipmentSchedule item)
		{
			return TableRecordReference.of(I_M_ShipmentSchedule.Table_Name, item.getM_ShipmentSchedule_ID());
		}
	};

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		// retrieve the order and generate requests
		queueDAO.retrieveAllItems(workPackage, I_M_ShipmentSchedule.class)
				.forEach(shipmentScheduleBL::generateDeliveryPlanning);

		return Result.SUCCESS;
	}

}
