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

package de.metas.cucumber.stepdefs.costing;

import de.metas.costrevaluation.CostRevaluationId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.compiere.model.I_M_CostRevaluation;

/**
 * Stores the {@link I_M_CostRevaluation} document records created by a scenario, keyed by identifier.
 */
public class M_CostRevaluation_StepDefData extends StepDefData<I_M_CostRevaluation>
		implements StepDefDataGetIdAware<CostRevaluationId, I_M_CostRevaluation>
{
	public M_CostRevaluation_StepDefData()
	{
		super(I_M_CostRevaluation.class);
	}

	@Override
	public CostRevaluationId extractIdFromRecord(final I_M_CostRevaluation record)
	{
		return CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
	}
}
