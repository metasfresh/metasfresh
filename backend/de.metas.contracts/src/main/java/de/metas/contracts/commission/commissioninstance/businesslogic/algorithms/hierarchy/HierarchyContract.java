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

package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionSettingsLineId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.util.Check.assumeGreaterOrEqualToZero;

@Value
@ToString()
@EqualsAndHashCode()
public class HierarchyContract implements CommissionContract
{
	FlatrateTermId id;

	Percent commissionPercent;

	int pointsPrecision;

	boolean isSimulation;

	/**
	 * Technically not needed, but useful to help users retain a minimum level of sanity when analyzing why a particular commission was granted
	 */
	CommissionSettingsLineId commissionSettingsLineId;

	public static boolean isInstance(@NonNull final CommissionContract contract)
	{
		return contract instanceof HierarchyContract;
	}

	@NonNull
	public static Optional<HierarchyContract> castOrEmpty(@Nullable final CommissionContract contract)
	{
		if (contract == null)
		{
			return Optional.empty();
		}
		if (contract instanceof HierarchyContract)
		{
			return Optional.of((HierarchyContract)contract);
		}
		return Optional.empty();
	}

	@NonNull
	public static HierarchyContract cast(@Nullable final CommissionContract contract)
	{
		return castOrEmpty(contract)
				.orElseThrow(() -> new AdempiereException("Cannot cast the given contract to HierarchyContract")
						.appendParametersToMessage()
						.setParameter("contract", contract));
	}

	/**
	 * @param config when creating hierarchy-config + contracts via code, then this parameter is usually set right from the hierachy config's constructor!
	 */
	@JsonCreator
	@Builder
	public HierarchyContract(
			@JsonProperty("id") @NonNull final FlatrateTermId id,
			@JsonProperty("percent") @NonNull final Percent commissionPercent,
			@JsonProperty("pointsPrecision") final int pointsPrecision,
			@JsonProperty("commissionSettingsLineId") @Nullable final CommissionSettingsLineId commissionSettingsLineId,
			@JsonProperty("isSimulation") final boolean isSimulation)
	{
		this.id = id;
		this.commissionPercent = commissionPercent;
		this.pointsPrecision = assumeGreaterOrEqualToZero(pointsPrecision, "pointsPrecision");
		this.commissionSettingsLineId = commissionSettingsLineId;
		this.isSimulation = isSimulation;
	}

	/**
	 * Note: add "Hierarchy" as method parameters if and when we have a commission type where it makes a difference.
	 */
	public Percent getCommissionPercent()
	{
		return commissionPercent;
	}

	public int getPointsPrecision()
	{
		return pointsPrecision;
	}

}
