package de.metas.contracts.commission.commissioninstance.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory.ConfigRequestForNewInstance;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

@Service
public class CommissionInstanceRequestFactory
{
	private static final Logger logger = LogManager.getLogger(CommissionInstanceRequestFactory.class);

	private final CommissionConfigFactory commissionContractFactory;
	private final CommissionHierarchyFactory commissionHierarchyFactory;
	private final CommissionTriggerFactory commissionTriggerFactory;

	public CommissionInstanceRequestFactory(
			@NonNull final CommissionConfigFactory commissionContractFactory,
			@NonNull final CommissionHierarchyFactory commissionHierarchyFactory,
			@NonNull final CommissionTriggerFactory commissionTriggerFactory)
	{
		this.commissionContractFactory = commissionContractFactory;
		this.commissionHierarchyFactory = commissionHierarchyFactory;
		this.commissionTriggerFactory = commissionTriggerFactory;
	}

	/**
	 * Creates one request - for the shares of one commission instance.
	 * Note: if the given IC is a "commission-product-IC" or a purchase-IC, then there won't a request because these IC's don't have a sales rep.
	 */
	public Optional<CreateCommissionSharesRequest> createRequestFor(@NonNull final CommissionTriggerDocument commissionTriggerDocument)
	{
		final BPartnerId salesRepBPartnerId = commissionTriggerDocument.getSalesRepBPartnerId();
		final Hierarchy hierarchy = commissionHierarchyFactory.createFor(salesRepBPartnerId);

		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.commissionHierarchy(hierarchy)
				.customerBPartnerId(commissionTriggerDocument.getCustomerBPartnerId())
				.salesRepBPartnerId(salesRepBPartnerId)
				.commissionDate(commissionTriggerDocument.getCommissionDate())
				.salesProductId(commissionTriggerDocument.getProductId())
				.build();
		final ImmutableList<CommissionConfig> configs = commissionContractFactory.createForNewCommissionInstances(contractRequest);
		if (configs.isEmpty())
		{
			logger.debug("Found no CommissionConfigs for contractRequest; -> return empty; contractRequest={}", contractRequest);
			return Optional.empty();
		}

		final CommissionTrigger trigger = commissionTriggerFactory.createForDocument(commissionTriggerDocument, false /* candidateDeleted */);

		return Optional.of(createRequest(hierarchy, configs, trigger));

	}

	private CreateCommissionSharesRequest createRequest(
			@NonNull final Hierarchy hierarchy,
			@NonNull final ImmutableList<CommissionConfig> configs,
			@NonNull final CommissionTrigger trigger)
	{
		final CreateCommissionSharesRequest request = CreateCommissionSharesRequest.builder()
				.configs(configs)
				.hierarchy(hierarchy)
				.trigger(trigger)
				.build();
		return request;
	}
}
