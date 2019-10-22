package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms;

import static de.metas.util.Check.assumeGreaterOrEqualToZero;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
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
@ToString(exclude = "config" /* avoid StackOverflowError */)
@EqualsAndHashCode(exclude = "config")
public class HierarchyContract implements CommissionContract
{
	FlatrateTermId id;

	HierarchyConfig config;

	Percent commissionPercent;

	int pointsPrecision;

	public static HierarchyContract cast(@Nullable final CommissionContract contract)
	{
		if (contract == null)
		{
			return null;
		}
		if (contract instanceof HierarchyContract)
		{
			return (HierarchyContract)contract;
		}

		throw new AdempiereException("Cannot cast the given contract to HierarchyContract")
				.appendParametersToMessage()
				.setParameter("contract", contract);
	}

	/**
	 * @param config when creating hierarchy-config + contracts via code, then this parameter is usually set right from the hierachy config's constructor!
	 */
	@JsonCreator
	@Builder
	public HierarchyContract(
			@JsonProperty("id") @NonNull final FlatrateTermId id,
			@JsonProperty("config") @NonNull final HierarchyConfig config,
			@JsonProperty("percent") @NonNull final Percent commissionPercent,
			@JsonProperty("pointsPrecision") final int pointsPrecision)
	{
		this.id = id;
		this.config = config;
		this.commissionPercent = commissionPercent;
		this.pointsPrecision = assumeGreaterOrEqualToZero(pointsPrecision, "pointsPrecision");
	}

	/** Note: add "Hierarchy" as method parameters if and when we have a commission type where it makes a difference. */
	public Percent getCommissionPercent()
	{
		return commissionPercent;
	}

	public int getPointsPrecision()
	{
		return pointsPrecision;
	}

	@Override
	@JsonIgnore /* avoid StackOverflowError */
	public HierarchyConfig getConfig()
	{
		return config;
	}


}
