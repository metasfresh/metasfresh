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

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI;

public class M_HU_PI_StepDefData extends StepDefData<I_M_HU_PI>
		implements StepDefDataGetIdAware<HuPackingInstructionsId, I_M_HU_PI>
{
	public M_HU_PI_StepDefData()
	{
		super(I_M_HU_PI.class);
	}

	@Override
	public HuPackingInstructionsId extractIdFromRecord(final I_M_HU_PI record) {return HuPackingInstructionsId.ofRepoId(record.getM_HU_PI_ID());}
}
