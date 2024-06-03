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

package de.metas.contracts.commission.commissioninstance.services.margin;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigPriority;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.services.ICommissionConfigFactory;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMargin;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginId;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginLine;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginLine.MappingCriteria;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginService;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MarginCommissionConfigFactory implements ICommissionConfigFactory
{
	private final ImmutableSet<CommissionTriggerType> SUPPORTED_TRIGGER_TYPES = ImmutableSet.of(CommissionTriggerType.SalesInvoice,
																								CommissionTriggerType.InvoiceCandidate,
																								CommissionTriggerType.Plain);

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);

	private final CustomerTradeMarginService customerTradeMarginService;
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	public MarginCommissionConfigFactory(@NonNull final CustomerTradeMarginService customerTradeMarginService)
	{
		this.customerTradeMarginService = customerTradeMarginService;
	}

	@Override
	@NonNull
	public ImmutableList<CommissionConfig> createForNewCommissionInstances(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		if (!appliesFor(contractRequest))
		{
			return ImmutableList.of();
		}

		final ImmutableList<I_C_Flatrate_Term> marginCommissionContracts = retrieveContracts(contractRequest.getSalesRepBPartnerId(),
																							 contractRequest.getOrgId(),
																							 contractRequest.getCommissionDate());

		if (marginCommissionContracts.isEmpty())
		{
			return ImmutableList.of();
		}

		final I_C_Flatrate_Term contract = getContractToEnforce(marginCommissionContracts);

		final MappingCriteria mappingCriteria = extractMappingCriteria(contractRequest.getSalesProductId(), contractRequest.getCustomerBPartnerId());

		return createCommissionConfigsFor(contract, mappingCriteria)
				.map(ImmutableList::of)
				.orElseGet(ImmutableList::of);
	}

	@Override
	public ImmutableMap<FlatrateTermId, CommissionConfig> createForExistingInstance(@NonNull final CommissionConfigProvider.ConfigRequestForExistingInstance commissionConfigRequest)
	{
		final ImmutableList<I_C_Flatrate_Term> marginCommissionContracts = flatrateDAO
				.retrieveTerms(commissionConfigRequest.getContractIds())
				.stream()
				.filter(termRecord -> TypeConditions.MARGIN_COMMISSION.getCode().equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		if (marginCommissionContracts.isEmpty())
		{
			return ImmutableMap.of();
		}

		final I_C_Flatrate_Term contract = getContractToEnforce(marginCommissionContracts);

		final MappingCriteria mappingCriteria = extractMappingCriteria(commissionConfigRequest.getSalesProductId(), commissionConfigRequest.getCustomerBPartnerId());

		return createCommissionConfigsFor(contract, mappingCriteria)
				.map(commissionConfig -> ImmutableMap.of(FlatrateTermId.ofRepoId(contract.getC_Flatrate_Term_ID()), commissionConfig))
				.orElseGet(ImmutableMap::of);
	}

	private MappingCriteria extractMappingCriteria(final ProductId salesProductId, final BPartnerId customerBPartnerId)
	{
		final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(salesProductId);

		return MappingCriteria.builder()
				.customerId(customerBPartnerId)
				.productId(salesProductId)
				.productCategoryId(productCategoryId)
				.build();
	}

	@Override
	public boolean appliesFor(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		return SUPPORTED_TRIGGER_TYPES.contains(contractRequest.getCommissionTriggerType())
				&& !contractRequest.isCustomerTheSalesRep();
	}

	@Override
	public boolean isFurtherSearchForConfigTypesAllowed()
	{
		return false;
	}

	@Override
	public CommissionConfigPriority getPriority()
	{
		return CommissionConfigPriority.ONE;
	}

	@NonNull
	private Optional<CommissionConfig> createCommissionConfigsFor(
			@NonNull final I_C_Flatrate_Term marginCommissionContract,
			@NonNull final MappingCriteria mappingCriteria)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(marginCommissionContract.getC_Flatrate_Conditions_ID());
		final I_C_Flatrate_Conditions conditions = contractsDAO.getConditionsById(conditionsId, I_C_Flatrate_Conditions.class);

		final CustomerTradeMarginId customerTradeMarginId = CustomerTradeMarginId.ofRepoIdOrNull(conditions.getC_Customer_Trade_Margin_ID());
		if (customerTradeMarginId == null)
		{
			throw new AdempiereException("CustomerTradeMarginId not set on MarginCommission type C_Flatrate_Conditions!")
					.appendParametersToMessage()
					.setParameter("C_Flatrate_Conditions_ID", conditions.getC_Flatrate_Conditions_ID())
					.setParameter("C_Flatrate_Conditions.Type_Conditions", conditions.getType_Conditions())
					.setParameter("C_Flatrate_Term_ID", marginCommissionContract.getC_Flatrate_Term_ID());
		}

		final CustomerTradeMargin customerTradeMargin = customerTradeMarginService.getById(customerTradeMarginId);

		if (customerTradeMargin.getCommissionProductId().getRepoId() != marginCommissionContract.getM_Product_ID())
		{
			throw new AdempiereException("CommissionProductId doesn't match!")
					.appendParametersToMessage()
					.setParameter("customerTradeMarginId", customerTradeMargin.getCustomerTradeMarginId())
					.setParameter("customerTradeMargin.CommissionProductID", customerTradeMargin.getCommissionProductId())
					.setParameter("C_Flatrate_Term_ID", marginCommissionContract.getC_Flatrate_Term_ID())
					.setParameter("C_Flatrate_Term.M_Product_ID", marginCommissionContract.getM_Product_ID());
		}

		final Optional<CustomerTradeMarginLine> lineForCustomerOpt = customerTradeMargin.getLineForCustomer(mappingCriteria);

		if (!lineForCustomerOpt.isPresent())
		{
			return Optional.empty();
		}

		final MarginContract contract = MarginContract.builder()
				.id(FlatrateTermId.ofRepoId(marginCommissionContract.getC_Flatrate_Term_ID()))
				.contractOwnerBPartnerId(BPartnerId.ofRepoId(marginCommissionContract.getBill_BPartner_ID()))
				.build();

		final CustomerTradeMarginLine lineForCustomer = lineForCustomerOpt.get();

		return Optional.of(MarginConfig.builder()
								   .customerTradeMarginLineId(lineForCustomer.getIdNotNull())
								   .pointsPrecision(customerTradeMargin.getPointsPrecision())
								   .tradedPercent(lineForCustomer.getPercent())
								   .commissionProductId(customerTradeMargin.getCommissionProductId())
								   .marginContract(contract)
								   .build());
	}

	@NonNull
	private I_C_Flatrate_Term getContractToEnforce(@NonNull final ImmutableList<I_C_Flatrate_Term> marginCommissionContracts)
	{
		// dev-note: see de.metas.contracts.interceptor.C_Flatrate_Term#ensureOneContract(I_C_Flatrate_Term term)
		Check.assume(marginCommissionContracts.size() == 1, "One salesRep should have only one margin commission contract for the same time period of time");

		return marginCommissionContracts.get(0);
	}

	@VisibleForTesting
	ImmutableList<I_C_Flatrate_Term> retrieveContracts(
			@NonNull final BPartnerId vendorId,
			@NonNull final OrgId orgId,
			@NonNull final LocalDate commissionDate)
	{
		final IFlatrateDAO.TermsQuery termsQuery = IFlatrateDAO.TermsQuery.builder()
				.billPartnerId(vendorId)
				.orgId(orgId)
				.dateOrdered(commissionDate)
				.build();

		return flatrateDAO.retrieveTerms(termsQuery)
				.stream()
				.filter(termRecord -> TypeConditions.MARGIN_COMMISSION.getCode().equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

	}
}
