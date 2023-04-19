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

package de.metas.deliveryplanning.async;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class M_ReceiptSchedule_Create_M_Delivery_Planning extends WorkpackageProcessorAdapter
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(de.metas.deliveryplanning.DeliveryPlanningService.class);
	private static final WorkpackagesOnCommitSchedulerTemplate<I_M_ReceiptSchedule> //
			SCHEDULER = WorkpackagesOnCommitSchedulerTemplate
			.newModelScheduler(M_ReceiptSchedule_Create_M_Delivery_Planning.class, I_M_ReceiptSchedule.class)
			.setCreateOneWorkpackagePerModel(true);

	public static void scheduleOnTrxCommit(@NonNull final I_M_ReceiptSchedule receiptScheduleRecord)
	{
		SCHEDULER.schedule(receiptScheduleRecord);
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		// retrieve the order and generate requests
		queueDAO.retrieveAllItems(workPackage, I_M_ReceiptSchedule.class)
				.forEach(deliveryPlanningService::generateIncomingDeliveryPlanning);

		return Result.SUCCESS;
	}
}
