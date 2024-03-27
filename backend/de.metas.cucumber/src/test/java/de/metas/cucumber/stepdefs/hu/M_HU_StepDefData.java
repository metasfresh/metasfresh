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
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;

import java.util.Optional;

public class M_HU_StepDefData extends StepDefData<I_M_HU>
{
	public M_HU_StepDefData()
	{
		super(I_M_HU.class);
	}

	public HuId getId(final StepDefDataIdentifier identifier)
	{
		return HuId.ofRepoId(get(identifier).getM_HU_ID());
	}

	public Optional<HuId> getIdOptional(final StepDefDataIdentifier identifier)
	{
		return getOptional(identifier).map(hu -> HuId.ofRepoId(hu.getM_HU_ID()));
	}
}
