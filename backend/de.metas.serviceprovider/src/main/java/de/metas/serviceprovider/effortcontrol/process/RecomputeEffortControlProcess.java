/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.effortcontrol.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.serviceprovider.effortcontrol.RecomputeEffortControlService;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class RecomputeEffortControlProcess extends JavaProcess implements IProcessPrecondition
{
	private final RecomputeEffortControlService recomputeEffortControlService = SpringContextHolder.instance.getBean(RecomputeEffortControlService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final EffortControlId effortControlId = EffortControlId.ofRepoId(getRecord_ID());

		addLog("Invoking RecomputeEffortControlProcess for id: {}", effortControlId);

		recomputeEffortControlService.recomputeEffortControlFor(effortControlId);

		return MSG_OK;
	}
}
