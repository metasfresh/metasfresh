/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

public class M_Delivery_Planning_Create_M_Delivery_Instruction_WorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	private static final WorkpackagesOnCommitSchedulerTemplate<I_M_Delivery_Planning>
			SCHEDULER = WorkpackagesOnCommitSchedulerTemplate
			.newModelScheduler(M_Delivery_Planning_Create_M_Delivery_Instruction_WorkpackageProcessor.class, I_M_Delivery_Planning.class)
			.setCreateOneWorkpackagePerModel(true);

	public static void scheduleOnTrxCommit(@NonNull final I_M_Delivery_Planning deliveryPlanningRecord)
	{
		SCHEDULER.schedule(deliveryPlanningRecord);
	}

	@Override
	public IWorkpackageProcessor.Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		queueDAO.retrieveAllItems(workPackage, I_M_Delivery_Planning.class)
				.stream()
				.map(deliveryPlanningRecord -> DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID()))
				.forEach(deliveryPlanningService::generateCompleteDeliveryInstruction);

		return IWorkpackageProcessor.Result.SUCCESS;
	}
}
