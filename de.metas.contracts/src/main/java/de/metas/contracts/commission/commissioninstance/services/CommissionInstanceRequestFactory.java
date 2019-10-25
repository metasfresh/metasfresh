package de.metas.contracts.commission.commissioninstance.services;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Optional;

import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateInstanceRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory.ContractRequest;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.product.ProductId;
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

	/** note: if the given IC is a "commission-product-IC, then there won't be requests because these IC's don't have a sales rep */
	public ImmutableList<CreateInstanceRequest> createRequestsForNewSalesInvoiceCandidate(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate icRecord = loadOutOfTrx(invoiceCandidateId, I_C_Invoice_Candidate.class);
		return createRequestFor(icRecord);
	}

	private ImmutableList<CreateInstanceRequest> createRequestFor(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final BPartnerId salesRepBPartnerId = BPartnerId.ofRepoIdOrNull(icRecord.getC_BPartner_SalesRep_ID());
		if (salesRepBPartnerId == null)
		{
			return ImmutableList.of();
		}

		final ContractRequest contractRequest = ContractRequest.builder()
				.bPartnerId(salesRepBPartnerId)
				.date(TimeUtil.asLocalDate(icRecord.getDateOrdered()))
				.productId(ProductId.ofRepoId(icRecord.getM_Product_ID()))
				.build();
		final ImmutableList<CommissionConfig> configs = commissionContractFactory.createFor(contractRequest);
		if (configs.isEmpty())
		{
			return ImmutableList.of();
		}

		final Optional<CommissionTrigger> trigger = commissionTriggerFactory.createForNewSalesInvoiceCandidate(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()));
		if (!trigger.isPresent())
		{
			return ImmutableList.of();
		}

		final Hierarchy hierarchy = commissionHierarchyFactory.createFor(salesRepBPartnerId);

		final ImmutableList.Builder<CreateInstanceRequest> result = ImmutableList.builder();
		for (final CommissionConfig config : configs)
		{
			result.add(createRequest(hierarchy, config, trigger.get()));
		}
		return result.build();
	}

	private CreateInstanceRequest createRequest(
			@NonNull final Hierarchy hierarchy,
			@NonNull final CommissionConfig config,
			@NonNull final CommissionTrigger trigger)
	{
		final CreateInstanceRequest request = CreateInstanceRequest.builder()
				.config(config)
				.hierarchy(hierarchy)
				.trigger(trigger)
				.build();
		return request;
	}
}
