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

package de.metas.contracts.commission.commissioninstance.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommissionConfigProvider
{
	@NonNull
	private final List<ICommissionConfigFactory> factories;

	public CommissionConfigProvider(@NonNull final List<ICommissionConfigFactory> factories)
	{
		this.factories = factories;
	}

	@NonNull
	public ImmutableList<CommissionConfig> createForNewCommissionInstances(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		return factories.stream()
				.filter(commissionConfigFactory -> commissionConfigFactory.appliesFor(contractRequest))
				.map(commissionConfigFactory -> commissionConfigFactory.createForNewCommissionInstances(contractRequest))
				.flatMap(ImmutableList::stream)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableMap<FlatrateTermId, CommissionConfig> createForExistingInstance(@NonNull final CommissionConfigProvider.ConfigRequestForExistingInstance request)
	{
		final ImmutableMap.Builder<FlatrateTermId, CommissionConfig> contractId2CommissionConfigBuilder = ImmutableMap.builder();

		factories.stream()
				.map(commissionConfigFactory -> commissionConfigFactory.createForExistingInstance(request))
				.forEach(contractId2CommissionConfigBuilder::putAll);

		final ImmutableMap<FlatrateTermId, CommissionConfig> contractId2CommissionConfig = contractId2CommissionConfigBuilder.build();

		if (contractId2CommissionConfig.isEmpty())
		{
			throw new AdempiereException("The given commissionConfigRequest needs at least one commissionConfig")
					.appendParametersToMessage()
					.setParameter("result.size()", contractId2CommissionConfig.size())
					.setParameter("commissionConfigRequest", request)
					.setParameter("result", contractId2CommissionConfig);
		}

		return contractId2CommissionConfig;
	}

	@Builder
	@Value
	public static class ConfigRequestForNewInstance
	{
		@NonNull
		OrgId orgId;

		@NonNull
		BPartnerId salesRepBPartnerId;

		/**
		 * Needed because config settings can be specific to the customer's group.
		 */
		@NonNull
		BPartnerId customerBPartnerId;

		/**
		 * Needed because config settings can be specific to the product's category.
		 */
		@NonNull
		ProductId salesProductId;

		@NonNull
		LocalDate commissionDate;

		@NonNull
		Hierarchy commissionHierarchy;

		@NonNull
		CommissionTriggerType commissionTriggerType;
	}

	@Builder
	@Value
	public static class ConfigRequestForExistingInstance
	{
		@NonNull
		ImmutableList<FlatrateTermId> contractIds;

		@NonNull
		BPartnerId customerBPartnerId;

		@NonNull
		ProductId salesProductId;
	}
}
