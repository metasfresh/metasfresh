/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import de.metas.order.paymentschedule.OrderPayScheduleId;
import org.compiere.model.I_C_OrderPaySchedule;

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
public class C_OrderPaySchedule_StepDefData extends StepDefData<I_C_OrderPaySchedule>
		implements StepDefDataGetIdAware<OrderPayScheduleId, I_C_OrderPaySchedule>
{
	public C_OrderPaySchedule_StepDefData()
	{
		super(I_C_OrderPaySchedule.class);
	}

	@Override
	public OrderPayScheduleId extractIdFromRecord(final I_C_OrderPaySchedule record)
	{
		return OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID());
	}
}
