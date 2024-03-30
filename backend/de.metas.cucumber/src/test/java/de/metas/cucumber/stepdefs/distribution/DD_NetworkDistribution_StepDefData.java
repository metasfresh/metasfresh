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

package de.metas.cucumber.stepdefs.distribution;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import org.eevolution.model.I_DD_NetworkDistribution;

public class DD_NetworkDistribution_StepDefData extends StepDefData<I_DD_NetworkDistribution>
		implements StepDefDataGetIdAware<DistributionNetworkId, I_DD_NetworkDistribution>
{
	public DD_NetworkDistribution_StepDefData()
	{
		super(I_DD_NetworkDistribution.class);
	}

	@Override
	public DistributionNetworkId extractIdFromRecord(final I_DD_NetworkDistribution record)
	{
		return DistributionNetworkId.ofRepoId(record.getDD_NetworkDistribution_ID());
	}
}
