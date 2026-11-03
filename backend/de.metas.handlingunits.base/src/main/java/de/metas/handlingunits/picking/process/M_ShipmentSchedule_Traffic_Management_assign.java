/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.process;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.commands.PickingJobScheduleAutoAssignRequest;
import de.metas.process.JavaProcess;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class M_ShipmentSchedule_Traffic_Management_assign extends JavaProcess
{
	@NonNull private final PickingJobScheduleService pickingJobScheduleService = SpringContextHolder.instance.getBean(PickingJobScheduleService.class);

	@Override
	protected String doIt() throws Exception
	{
		pickingJobScheduleService.autoAssign(PickingJobScheduleAutoAssignRequest.builder()
				.preparationDate(SystemTime.asLocalDate())
				.build());

		return MSG_OK;
	}
}
