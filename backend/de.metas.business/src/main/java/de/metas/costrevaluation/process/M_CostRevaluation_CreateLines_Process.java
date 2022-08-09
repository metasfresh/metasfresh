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

import de.metas.costrevaluation.CostRevaluationService;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

/**
 * Process to create M_CostRevaluationLine records for products stocked based on accounting schema and cost element of M_CostRevaluation
 */
public class M_CostRevaluation_CreateLines_Process extends JavaProcess implements IProcessPrecondition
{
	private final CostRevaluationService costRevaluationService = SpringContextHolder.instance.getBean(CostRevaluationService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return context.acceptIfSingleSelection()
				.and(() -> acceptIfDraft(context))
				.toInternal();
	}

	private ProcessPreconditionsResolution acceptIfDraft(final @NonNull IProcessPreconditionsContext context)
	{
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(context.getSingleSelectedRecordId());
		return costRevaluationService.isDraftedDocument(costRevaluationId)
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("Already in progress/finished.");
	}

	@Override
	protected String doIt()
	{
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(getRecord_ID());
		costRevaluationService.createLines(costRevaluationId);

		return MSG_OK;
	}

}
