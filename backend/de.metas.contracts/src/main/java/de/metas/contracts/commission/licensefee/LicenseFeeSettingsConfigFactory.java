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

package de.metas.contracts.commission.licensefee;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IContractsDAO;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigPriority;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.services.ICommissionConfigFactory;
import de.metas.contracts.commission.licensefee.algorithm.LicenseFeeConfig;
import de.metas.contracts.commission.licensefee.algorithm.LicenseFeeContract;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettings;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsLine;
import de.metas.contracts.commission.licensefee.repository.LicenseFeeSettingsRepository;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseFeeSettingsConfigFactory implements ICommissionConfigFactory
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IContractsDAO contractsDAO = Services.get(IContractsDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private final LicenseFeeSettingsRepository licenseFeeSettingsRepository;

	private final ImmutableSet<CommissionTriggerType> SUPPORTED_TRIGGER_TYPES = ImmutableSet.of(CommissionTriggerType.SalesInvoice,
																								CommissionTriggerType.InvoiceCandidate,
																								CommissionTriggerType.SalesCreditmemo,
																								CommissionTriggerType.Plain);

	public LicenseFeeSettingsConfigFactory(@NonNull final LicenseFeeSettingsRepository licenseFeeSettingsRepository)
	{
		this.licenseFeeSettingsRepository = licenseFeeSettingsRepository;
	}

	@Override
	@NonNull
	public ImmutableList<CommissionConfig> createForNewCommissionInstances(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		if (!appliesFor(contractRequest))
		{
			return ImmutableList.of();
		}

		final ImmutableList<I_C_Flatrate_Term> licenseFeeCommissionContracts = retrieveContracts(contractRequest.getSalesRepBPartnerId(),
																								 contractRequest.getOrgId(),
																								 contractRequest.getCommissionDate());
		if (licenseFeeCommissionContracts.isEmpty())
		{
			return ImmutableList.of();
		}

		final I_C_Flatrate_Term licenseFeeCommissionContract = getContractToEnforce(licenseFeeCommissionContracts);

		return createCommissionConfigsFor(licenseFeeCommissionContract)
				.map(ImmutableList::of)
				.orElseGet(ImmutableList::of);
	}

	@Override
	@NonNull
	public ImmutableMap<FlatrateTermId, CommissionConfig> createForExistingInstance(@NonNull final CommissionConfigProvider.ConfigRequestForExistingInstance request)
	{
		final ImmutableList<I_C_Flatrate_Term> licenseFeeCommissionContracts = flatrateDAO
				.retrieveTerms(request.getContractIds())
				.stream()
				.filter(termRecord -> TypeConditions.LICENSE_FEE.getCode().equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		if (licenseFeeCommissionContracts.isEmpty())
		{
			return ImmutableMap.of();
		}

		final I_C_Flatrate_Term licenseFeeCommissionContract = getContractToEnforce(licenseFeeCommissionContracts);

		return createCommissionConfigsFor(licenseFeeCommissionContract)
				.map(commissionConfig -> ImmutableMap.of(FlatrateTermId.ofRepoId(licenseFeeCommissionContract.getC_Flatrate_Term_ID()), commissionConfig))
				.orElseGet(ImmutableMap::of);
	}

	@Override
	public boolean appliesFor(final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		return SUPPORTED_TRIGGER_TYPES.contains(contractRequest.getCommissionTriggerType());
	}

	@Override
	public boolean isFurtherSearchForConfigTypesAllowed()
	{
		return true;
	}

	@Override
	public CommissionConfigPriority getPriority()
	{
		return CommissionConfigPriority.ZERO;
	}

	@NonNull
	@VisibleForTesting
	ImmutableList<I_C_Flatrate_Term> retrieveContracts(
			@NonNull final BPartnerId salesRepId,
			@NonNull final OrgId orgId,
			@NonNull final LocalDate commissionDate)
	{
		final IFlatrateDAO.TermsQuery termsQuery = IFlatrateDAO.TermsQuery.builder()
				.billPartnerId(salesRepId)
				.orgId(orgId)
				.dateOrdered(commissionDate)
				.build();

		return flatrateDAO.retrieveTerms(termsQuery)
				.stream()
				.filter(termRecord -> TypeConditions.LICENSE_FEE.getCode().equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Optional<CommissionConfig> createCommissionConfigsFor(@NonNull final I_C_Flatrate_Term licenseFeeCommissionContract)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(licenseFeeCommissionContract.getC_Flatrate_Conditions_ID());
		final I_C_Flatrate_Conditions conditions = contractsDAO.getConditionsById(conditionsId, I_C_Flatrate_Conditions.class);

		final LicenseFeeSettingsId licenseFeeSettingsId = LicenseFeeSettingsId.ofRepoIdOrNull(conditions.getC_LicenseFeeSettings_ID());
		if (licenseFeeSettingsId == null)
		{
			throw new AdempiereException("LicenseFeeSettingsId not set on LicenseFeeCommission type C_Flatrate_Conditions!")
					.appendParametersToMessage()
					.setParameter("C_Flatrate_Conditions_ID", conditions.getC_Flatrate_Conditions_ID())
					.setParameter("C_Flatrate_Conditions.Type_Conditions", conditions.getType_Conditions())
					.setParameter("C_Flatrate_Term_ID", licenseFeeCommissionContract.getC_Flatrate_Term_ID());
		}

		final LicenseFeeSettings licenseFeeSettings = licenseFeeSettingsRepository.getById(licenseFeeSettingsId);

		if (licenseFeeSettings.getCommissionProductId().getRepoId() != licenseFeeCommissionContract.getM_Product_ID())
		{
			throw new AdempiereException("CommissionProductId doesn't match!")
					.appendParametersToMessage()
					.setParameter("LicenseFeeSettingsId", licenseFeeSettings.getLicenseFeeSettingsId())
					.setParameter("MediatedCommissionSettings.CommissionProductID", licenseFeeSettings.getCommissionProductId())
					.setParameter("C_Flatrate_Term_ID", licenseFeeCommissionContract.getC_Flatrate_Term_ID())
					.setParameter("C_Flatrate_Term.M_Product_ID", licenseFeeCommissionContract.getM_Product_ID());
		}

		final BPartnerId salesRepId = BPartnerId.ofRepoId(licenseFeeCommissionContract.getBill_BPartner_ID());

		final BPGroupId salesRepBPGroupId = bPartnerDAO.getBPGroupIdByBPartnerId(salesRepId);

		final Optional<LicenseFeeSettingsLine> licenseFeeSettingsLine = licenseFeeSettings.getLineForBPGroupId(salesRepBPGroupId);

		if (!licenseFeeSettingsLine.isPresent())
		{
			return Optional.empty();
		}

		final LicenseFeeContract contract = LicenseFeeContract.builder()
				.id(FlatrateTermId.ofRepoId(licenseFeeCommissionContract.getC_Flatrate_Term_ID()))
				.contractOwnerBPartnerId(BPartnerId.ofRepoId(licenseFeeCommissionContract.getBill_BPartner_ID()))
				.build();

		return Optional.of(LicenseFeeConfig.builder()
								   .licenseFeeContract(contract)
								   .commissionPercent(licenseFeeSettingsLine.get().getPercentOfBasedPoints())
								   .pointsPrecision(licenseFeeSettings.getPointsPrecision())
								   .licenseFeeSettingsLineId(licenseFeeSettingsLine.get().getIdNotNull())
								   .commissionProductId(licenseFeeSettings.getCommissionProductId())
								   .build());
	}

	@NonNull
	private I_C_Flatrate_Term getContractToEnforce(@NonNull final List<I_C_Flatrate_Term> licenseFeeCommissionContract)
	{
		// dev-note: see de.metas.contracts.interceptor.C_Flatrate_Term#ensureOneContract(I_C_Flatrate_Term term)
		Check.assume(licenseFeeCommissionContract.size() == 1, "One salesRep should have only one license commission contract for the same time period of time");

		return licenseFeeCommissionContract.get(0);
	}
}
