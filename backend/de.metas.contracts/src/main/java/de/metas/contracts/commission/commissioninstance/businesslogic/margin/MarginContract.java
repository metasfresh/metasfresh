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

package de.metas.contracts.commission.commissioninstance.businesslogic.margin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class MarginContract implements CommissionContract
{
	@NonNull
	FlatrateTermId id;

	@NonNull
	BPartnerId contractOwnerBPartnerId;

	@JsonCreator
	@Builder
	public MarginContract(
			@JsonProperty("id") @NonNull final FlatrateTermId id,
			@JsonProperty("contractOwnerBPartnerId") @NonNull final BPartnerId contractOwnerBPartnerId)
	{
		this.id = id;
		this.contractOwnerBPartnerId = contractOwnerBPartnerId;
	}

	@Override
	public boolean isSimulation()
	{
		return false;
	}
}
