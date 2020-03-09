package de.metas.contracts.commission.commissioninstance.services;

import java.util.Optional;

import de.metas.contracts.commission.Beneficiary;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory.ConfigRequestForNewInstance;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.Services;
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

	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

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
	public Optional<CreateCommissionSharesRequest> createRequestsForNewSalesInvoiceCandidate(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate icRecord = invoiceCandDAO.getById(invoiceCandidateId);
		return createRequestFor(icRecord);
	}

	private Optional<CreateCommissionSharesRequest> createRequestFor(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final BPartnerId salesRepBPartnerId = BPartnerId.ofRepoIdOrNull(icRecord.getC_BPartner_SalesRep_ID());
		if (salesRepBPartnerId == null)
		{
			return Optional.empty();
		}

		final Hierarchy hierarchy = commissionHierarchyFactory.createFor(salesRepBPartnerId);

		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.commissionHierarchy(hierarchy)
				.customerBPartnerId(BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID()))
				.salesRepBPartnerId(salesRepBPartnerId)
				.date(TimeUtil.asLocalDate(icRecord.getDateOrdered()))
				.salesProductId(ProductId.ofRepoId(icRecord.getM_Product_ID()))
				.build();
		final ImmutableList<CommissionConfig> configs = commissionContractFactory.createForNewCommissionInstances(contractRequest);
		if (configs.isEmpty())
		{
			logger.debug("Found no CommissionConfigs for contractRequest; -> return empty; contractRequest={}", contractRequest);
			return Optional.empty();
		}

		final Optional<CommissionTrigger> trigger = commissionTriggerFactory.createForSalesInvoiceCandidate(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()));
		if (!trigger.isPresent())
		{
			logger.debug("No CommissionTrigger for contractRequest; -> return empty; contractRequest={}", contractRequest);
			return Optional.empty();
		}

		return Optional.of(createRequest(hierarchy, configs, trigger.get()));

	}

	public Optional<CreateCommissionSharesRequest> createRequestFor(
			@NonNull final CreateForecastCommissionInstanceRequest retrieveForecastCommissionPointsRequest)
	{
		final Hierarchy hierarchy = commissionHierarchyFactory.createFor(retrieveForecastCommissionPointsRequest.getSalesRepId());

		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.customerBPartnerId(retrieveForecastCommissionPointsRequest.getCustomerId())
				.salesRepBPartnerId(retrieveForecastCommissionPointsRequest.getSalesRepId())
				.date(retrieveForecastCommissionPointsRequest.getDateOrdered())
				.salesProductId(retrieveForecastCommissionPointsRequest.getProductId())
				.commissionHierarchy(hierarchy)
				.build();

		final ImmutableList<CommissionConfig> configs = commissionContractFactory.createForNewCommissionInstances(contractRequest)
				.stream()
				.filter(config -> config.getContractFor(Beneficiary.of(contractRequest.getSalesRepBPartnerId())) != null)
				.collect(ImmutableList.toImmutableList());

		if (configs.isEmpty())
		{
			return Optional.empty();
		}

		final CommissionTrigger commissionTrigger = commissionTriggerFactory.createForForecastQtyAndPrice(
				retrieveForecastCommissionPointsRequest.getOrgId(),
				retrieveForecastCommissionPointsRequest.getProductPrice(),
				retrieveForecastCommissionPointsRequest.getForecastQty(),
				retrieveForecastCommissionPointsRequest.getSalesRepId(),
				retrieveForecastCommissionPointsRequest.getCustomerId());

		return Optional.of(createRequest(hierarchy, configs, commissionTrigger));
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
