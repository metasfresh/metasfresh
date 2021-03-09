/*
 * #%L
 * de.metas.servicerepair.webui
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

package de.metas.servicerepair.project.process;

import com.google.common.collect.ImmutableList;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskStatus;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskType;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

public class C_Project_StartRepairOrder
		extends ServiceOrRepairProjectBasedProcess
		implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final List<ServiceRepairProjectTask> tasks = getSelectedTasks(context)
				.stream()
				.filter(this::isEligible)
				.collect(ImmutableList.toImmutableList());
		if (tasks.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no eligible tasks found");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final List<ServiceRepairProjectTask> tasks = getSelectedTasks()
				.stream()
				.filter(this::isEligible)
				.collect(ImmutableList.toImmutableList());
		if (tasks.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		projectService.createRepairOrders(tasks);

		return MSG_OK;
	}

	private boolean isEligible(@NonNull final ServiceRepairProjectTask task)
	{
		return ServiceRepairProjectTaskType.REPAIR_ORDER.equals(task.getType())
				&& ServiceRepairProjectTaskStatus.NOT_STARTED.equals(task.getStatus())
				&& task.getRepairOrderId() == null;
	}
}
