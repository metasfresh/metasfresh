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
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class CommissionConfigProvider
{
	@NonNull
	private final List<ICommissionConfigFactory> priorityOrderedConfigFactories;

	public CommissionConfigProvider(@NonNull final List<ICommissionConfigFactory> factories)
	{
		this.priorityOrderedConfigFactories = factories.stream()
				.sorted(Comparator.comparing(ICommissionConfigFactory::getPriority).reversed())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableList<CommissionConfig> createForNewCommissionInstances(@NonNull final ConfigRequestForNewInstance contractRequest)
	{
		final Iterator<ICommissionConfigFactory> configFactoryIterator = priorityOrderedConfigFactories.iterator();
		final ImmutableList.Builder<CommissionConfig> commissionConfigCollector = ImmutableList.builder();
		boolean nextAllowed = true;

		while (configFactoryIterator.hasNext() && nextAllowed)
		{
			final ICommissionConfigFactory currentConfigFactory = configFactoryIterator.next();

			if (!currentConfigFactory.appliesFor(contractRequest))
			{
				continue;
			}

			final ImmutableList<CommissionConfig> configs = currentConfigFactory.createForNewCommissionInstances(contractRequest);

			if (!Check.isEmpty(configs))
			{
				commissionConfigCollector.addAll(configs);
				nextAllowed = currentConfigFactory.isFurtherSearchForConfigTypesAllowed();
			}
		}

		return commissionConfigCollector.build();
	}

	@NonNull
	public ImmutableMap<FlatrateTermId, CommissionConfig> createForExistingInstance(@NonNull final CommissionConfigProvider.ConfigRequestForExistingInstance commissionConfigRequest)
	{
		final ImmutableMap.Builder<FlatrateTermId, CommissionConfig> resultBuilder = ImmutableMap.builder();

		priorityOrderedConfigFactories.stream()
				.map(configFactory -> configFactory.createForExistingInstance(commissionConfigRequest))
				.forEach(resultBuilder::putAll);

		final ImmutableMap<FlatrateTermId, CommissionConfig> result = resultBuilder.build();

		if (result.isEmpty())
		{
			throw new AdempiereException("The given commissionConfigRequest needs at least one commissionConfig")
					.appendParametersToMessage()
					.setParameter("result.size()", result.size())
					.setParameter("commissionConfigRequest", commissionConfigRequest)
					.setParameter("result", result);
		}

		return result;
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

		public boolean isCustomerTheSalesRep()
		{
			return customerBPartnerId.equals(salesRepBPartnerId);
		}
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
