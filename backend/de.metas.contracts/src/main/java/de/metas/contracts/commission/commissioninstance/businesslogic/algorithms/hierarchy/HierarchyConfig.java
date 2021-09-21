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
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract.HierarchyContractBuilder;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Map;
import java.util.Map.Entry;

@Value
public class HierarchyConfig implements CommissionConfig
{
	HierarchyConfigId id;

	boolean subtractLowerLevelCommissionFromBase;

	@Getter
	ProductId commissionProductId;

	/* using integer as key to avoid complications on deserializing from JSON which we do in our tests. */
	Map<Integer, HierarchyContract> bpartnerId2HierarchyContracts;

	public static boolean isInstance(@NonNull final CommissionConfig config)
	{
		return config instanceof HierarchyConfig;
	}

	public static HierarchyConfig cast(@NonNull final CommissionConfig config)
	{
		if (config instanceof HierarchyConfig)
		{
			return (HierarchyConfig)config;
		}

		throw new AdempiereException("Cannot cast the given config to HierarchyConfig")
				.appendParametersToMessage()
				.setParameter("config", config);
	}

	@JsonCreator
	@Builder
	private HierarchyConfig(
			@JsonProperty("id") @NonNull final HierarchyConfigId id,
			@JsonProperty("commissionProductId") @NonNull final ProductId commissionProductId,
			@JsonProperty("subtractLowerLevelCommissionFromBase") @NonNull final Boolean subtractLowerLevelCommissionFromBase,
			@JsonProperty("beneficiary2HierarchyContracts") @Singular @NonNull final Map<Beneficiary, HierarchyContractBuilder> beneficiary2HierarchyContracts)
	{
		this.id = id;
		this.commissionProductId = commissionProductId;
		this.subtractLowerLevelCommissionFromBase = subtractLowerLevelCommissionFromBase;

		final ImmutableMap.Builder<Integer, HierarchyContract> builder = ImmutableMap.<Integer, HierarchyContract> builder();
		for (final Entry<Beneficiary, HierarchyContractBuilder> entry : beneficiary2HierarchyContracts.entrySet())
		{
			final HierarchyContract contract = entry.getValue().build();
			builder.put(entry.getKey().getBPartnerId().getRepoId(), contract);
		}
		this.bpartnerId2HierarchyContracts = builder.build();
	}

	@Override
	public CommissionType getCommissionType()
	{
		return CommissionType.HIERARCHY_COMMISSION;
	}

	@Override
	public CommissionContract getContractFor(@NonNull final BPartnerId contractualBPartnerId)
	{
		return bpartnerId2HierarchyContracts.get(contractualBPartnerId.getRepoId());
	}

	public boolean containsContracts()
	{
		return !bpartnerId2HierarchyContracts.isEmpty();
	}
}
