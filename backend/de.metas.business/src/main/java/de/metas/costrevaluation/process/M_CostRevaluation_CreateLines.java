package de.metas.costrevaluation.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/**
 * Process to create M_CostRevaluationLine records for products stocked based on accounting schema and cost element of M_CostRevaluation
 */
public class M_CostRevaluation_CreateLines extends M_CostRevaluation_ProcessTemplate
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return context.acceptIfSingleSelection()
				.and(() -> acceptIfDraft(context))
				.and(() -> acceptIfDoesNotHaveActiveLines(context))
				.toInternal();
	}

	@Override
	protected String doIt()
	{
		costRevaluationService.createLines(getCostRevaluationId());
		return MSG_OK;
	}

}
