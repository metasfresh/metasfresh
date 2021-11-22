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

package de.metas.contracts.commission.mediated;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.services.ICommissionConfigFactory;
import de.metas.contracts.commission.mediated.algorithm.MediatedCommissionConfig;
import de.metas.contracts.commission.mediated.algorithm.MediatedCommissionContract;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettings;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsId;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsLine;
import de.metas.contracts.commission.mediated.repository.MediatedCommissionSettingsRepo;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MediatedCommissionConfigFactory implements ICommissionConfigFactory
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@NonNull
	private final MediatedCommissionSettingsRepo mediatedCommissionSettingsRepo;

	public MediatedCommissionConfigFactory(@NonNull final MediatedCommissionSettingsRepo mediatedCommissionSettingsRepo)
	{
		this.mediatedCommissionSettingsRepo = mediatedCommissionSettingsRepo;
	}

	@Override
	public ImmutableList<CommissionConfig> createForNewCommissionInstances(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		if (!appliesFor(contractRequest))
		{
			return ImmutableList.of();
		}

		//dev-note: in case of MediatedCommission the customer is in fact a vendor and the sales-rep is the org's bpartner
		final BPartnerId vendorId = contractRequest.getCustomerBPartnerId();

		final List<I_C_Flatrate_Term> contractList = retrieveContracts(vendorId, contractRequest.getOrgId(), contractRequest.getCommissionDate());

		if (contractList.isEmpty())
		{
			return ImmutableList.of();
		}

		return createCommissionConfigs(contractRequest.getSalesProductId(), contractList)
				.map(ImmutableList::of)
				.orElseGet(ImmutableList::of);
	}

	@Override
	@NonNull
	public ImmutableMap<FlatrateTermId, CommissionConfig> createForExistingInstance(@NonNull final CommissionConfigProvider.ConfigRequestForExistingInstance request)
	{
		final ImmutableList<I_C_Flatrate_Term> mediatedCommissionTermRecords = flatrateDAO
				.retrieveTerms(request.getContractIds())
				.stream()
				.filter(contract -> TypeConditions.MEDIATED_COMMISSION.getCode().equals(contract.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		if (mediatedCommissionTermRecords.isEmpty())
		{
			return ImmutableMap.of();
		}

		return createCommissionConfigs(request.getSalesProductId(), mediatedCommissionTermRecords)
				.map(commissionConfig -> ImmutableMap.of(commissionConfig.getContractFor(request.getCustomerBPartnerId()).getId(), commissionConfig))
				.orElseGet(ImmutableMap::of);
	}

	@Override
	public boolean appliesFor(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		return CommissionTriggerType.MediatedOrder.equals(contractRequest.getCommissionTriggerType());
	}

	private Optional<CommissionConfig> createCommissionConfigs(
			@NonNull final ProductId transactionProductId,
			@NonNull final List<I_C_Flatrate_Term> mediatedCommissionContracts)
	{
		final I_C_Flatrate_Term currentContract = getContractToEnforce(mediatedCommissionContracts);
		final ConditionsId conditionsId = ConditionsId.ofRepoId(currentContract.getC_Flatrate_Conditions_ID());
		final I_C_Flatrate_Conditions conditions = contractsDAO.getConditionsById(conditionsId, I_C_Flatrate_Conditions.class);

		final MediatedCommissionSettingsId mediatedSettingsId = MediatedCommissionSettingsId.ofRepoIdOrNull(conditions.getC_MediatedCommissionSettings_ID());
		if (mediatedSettingsId == null)
		{
			throw new AdempiereException("MediatedCommissionSettingsId not set on MediatedCommission type C_Flatrate_Conditions!")
					.appendParametersToMessage()
					.setParameter("C_Flatrate_Conditions_ID", conditions.getC_Flatrate_Conditions_ID())
					.setParameter("C_Flatrate_Conditions.Type_Conditions", conditions.getType_Conditions())
					.setParameter("C_Flatrate_Term_ID", currentContract.getC_Flatrate_Term_ID());
		}

		final MediatedCommissionSettings mediatedCommissionSettings = mediatedCommissionSettingsRepo.getById(mediatedSettingsId);

		if (mediatedCommissionSettings.getCommissionProductId().getRepoId() != currentContract.getM_Product_ID())
		{
			throw new AdempiereException("CommissionProductId doesn't match!")
					.appendParametersToMessage()
					.setParameter("MediatedCommissionSettingsId", mediatedCommissionSettings.getCommissionSettingsId())
					.setParameter("MediatedCommissionSettings.CommissionProductID", mediatedCommissionSettings.getCommissionProductId())
					.setParameter("C_Flatrate_Term_ID", currentContract.getC_Flatrate_Term_ID())
					.setParameter("C_Flatrate_Term.M_Product_ID", currentContract.getM_Product_ID());
		}

		final ProductCategoryId productCategoryId = Objects.requireNonNull(productDAO.retrieveProductCategoryByProductId(transactionProductId));

		final Optional<MediatedCommissionSettingsLine> commissionSettingsLineOpt = mediatedCommissionSettings
				.getLineForProductCategory(productCategoryId);

		if (!commissionSettingsLineOpt.isPresent())
		{
			return Optional.empty();
		}

		final MediatedCommissionContract mediatedCommissionContract = MediatedCommissionContract.builder()
				.contractId(FlatrateTermId.ofRepoId(currentContract.getC_Flatrate_Term_ID()))
				.contractOwnerBPartnerId(BPartnerId.ofRepoId(currentContract.getBill_BPartner_ID()))
				.build();

		return Optional.of(MediatedCommissionConfig.builder()
								   .mediatedCommissionSettingsLineId(commissionSettingsLineOpt.get().getMediatedCommissionSettingsLineId())
								   .mediatedCommissionContract(mediatedCommissionContract)
								   .commissionPercent(commissionSettingsLineOpt.get().getPercentOfBasedPoints())
								   .pointsPrecision(mediatedCommissionSettings.getPointsPrecision())
								   .commissionProductId(mediatedCommissionSettings.getCommissionProductId())
								   .build());
	}

	@NonNull
	private I_C_Flatrate_Term getContractToEnforce(@NonNull final List<I_C_Flatrate_Term> mediatedCommissionContracts)
	{
		//dev-note: see de.metas.contracts.interceptor.C_Flatrate_Term.ensureOneMediatedContract
		Check.assume(mediatedCommissionContracts.size() == 1, "There should always be only one mediated contract at this point!");

		return mediatedCommissionContracts.get(0);
	}

	@VisibleForTesting
	List<I_C_Flatrate_Term> retrieveContracts(
			@NonNull final BPartnerId vendorId,
			@NonNull final OrgId orgId,
			@NonNull final LocalDate commissionDate)
	{
		final IFlatrateDAO.TermsQuery termsQuery = IFlatrateDAO.TermsQuery.builder()
				.billPartnerId(vendorId)
				.orgId(orgId)
				.dateOrdered(commissionDate)
				.build();

		return flatrateDAO
				.retrieveTerms(termsQuery)
				.stream()
				.filter(termRecord -> TypeConditions.MEDIATED_COMMISSION.getCode().equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());
	}
}
