/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.project.step.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.project.workorder.project.WOProjectService;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.project.step.ResolveReservationView;
import de.metas.ui.web.project.step.WOProjectStepResourceRow;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.util.stream.Stream;

public class C_Project_WO_Step_ResolveHours extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private static final String PARAM_HOURS = "Hours";
	@Param(parameterName = PARAM_HOURS, mandatory = true)
	private int hours;

	@NonNull
	private final WOProjectService woProjectService = SpringContextHolder.instance.getBean(WOProjectService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ResolveReservationView resolveReservationView = ResolveReservationView.cast(super.getView());

		final Stream<WOProjectResourceId> resourceIds = resolveReservationView
				.streamByIds(getSelectedRowIds())
				.map(WOProjectStepResourceRow::getResourceId);

		woProjectService.allocateHoursToResources(hours, resourceIds);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}
}
