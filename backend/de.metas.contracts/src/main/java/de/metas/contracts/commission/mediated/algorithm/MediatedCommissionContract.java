/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.commission.mediated.algorithm;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

@Value
@Builder
public class MediatedCommissionContract implements CommissionContract
{
	@NonNull
	FlatrateTermId contractId;

	@NonNull
	BPartnerId contractOwnerBPartnerId;

	@NonNull
	public static MediatedCommissionContract cast(@NonNull final CommissionContract commissionContract)
	{
		return castOrEmpty(commissionContract)
				.orElseThrow(() -> new AdempiereException("Cannot cast the given contract to MediatedCommissionContract")
						.appendParametersToMessage()
						.setParameter("contract", commissionContract));
	}

	@NonNull
	public static Optional<MediatedCommissionContract> castOrEmpty(@NonNull final CommissionContract commissionContract)
	{
		if (commissionContract instanceof MediatedCommissionContract)
		{
			return Optional.of((MediatedCommissionContract)commissionContract);
		}

		return Optional.empty();
	}

	@Override
	public FlatrateTermId getId()
	{
		return contractId;
	}

	@Override
	public boolean isSimulation()
	{
		return false;
	}
}
