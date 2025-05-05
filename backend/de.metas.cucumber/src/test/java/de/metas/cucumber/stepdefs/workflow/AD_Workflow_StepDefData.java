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

package de.metas.cucumber.stepdefs.workflow;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.material.planning.pporder.PPRoutingId;
import org.compiere.model.I_AD_Workflow;

public class AD_Workflow_StepDefData extends StepDefData<I_AD_Workflow>
		implements StepDefDataGetIdAware<PPRoutingId, I_AD_Workflow>
{
	public AD_Workflow_StepDefData()
	{
		super(I_AD_Workflow.class);
	}

	@Override
	public PPRoutingId extractIdFromRecord(final I_AD_Workflow record)
	{
		return PPRoutingId.ofRepoId(record.getAD_Workflow_ID());
	}
}
