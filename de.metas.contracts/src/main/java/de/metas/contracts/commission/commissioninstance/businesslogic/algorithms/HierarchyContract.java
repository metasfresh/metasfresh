package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class HierarchyContract implements CommissionContract
{
	HierarchyConfig config;

	public static HierarchyContract cast(@NonNull final CommissionContract contract)
	{
		if (contract instanceof HierarchyContract)
		{
			return (HierarchyContract)contract;
		}

		throw new AdempiereException("Cannot cast the given contract to HierarchyContract")
				.appendParametersToMessage()
				.setParameter("contract", contract);
	}

	@JsonCreator
	public HierarchyContract(@JsonProperty("config") @NonNull final HierarchyConfig config)
	{
		this.config = config;
	}

	/** Note: add "Hierarchy" as method parameters if and when we have a commission type where it makes a difference. */
	public Percent getCommissionPercent()
	{
		return Percent.of("10");
	}

	public int getPointsPrecision()
	{
		return 2;
	}



}
