package de.metas.contracts.commission.commissioninstance.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateDAO.TermsQuery;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig.HierarchyConfigBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract.HierarchyContractBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigStagingDataService.StagingData;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
public class CommissionConfigFactory
{
	private CommissionConfigStagingDataService commissionConfigStagingDataService;

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	public CommissionConfigFactory(@NonNull final CommissionConfigStagingDataService commissionConfigStagingDataService)
	{
		this.commissionConfigStagingDataService = commissionConfigStagingDataService;
	}

	public ImmutableList<CommissionConfig> createForNewCommissionInstances(@NonNull final ConfigRequestForNewInstance contractRequest)
	{
		final Hierarchy hierarchy = contractRequest.getCommissionHierarchy();
		final Iterable<HierarchyNode> beneficiaries = hierarchy.getUpStream(Beneficiary.of(contractRequest.getSalesRepBPartnerId()));

		final ImmutableList<BPartnerId> allBPartnerIds = StreamSupport
				.stream(beneficiaries.spliterator(), false)
				.map(HierarchyNode::getBeneficiary)
				.map(Beneficiary::getBPartnerId)
				.collect(ImmutableList.toImmutableList());

		// don't look up the terms via product; instead, get all commission-terms for the respective sales reps that are currently active
		final TermsQuery termsQuery = TermsQuery.builder()
				.billPartnerIds(allBPartnerIds)
				.dateOrdered(contractRequest.getDate())
				.build();
		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = flatrateDAO
				.retrieveTerms(termsQuery)
				.stream()
				.filter(termRecord -> CommissionConstants.TYPE_CONDITIONS_COMMISSION.equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		return createCommissionConfigsFor(commissionTermRecords,
				contractRequest.getCustomerBPartnerId(),
				contractRequest.getSalesProductId());
	}

	public CommissionConfig createForExisingInstance(@NonNull final ConfigRequestForExistingInstance commissionConfigRequest)
	{
		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = flatrateDAO
				.retrieveTerms(commissionConfigRequest.getContractIds())
				.stream()
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<CommissionConfig> result = createCommissionConfigsFor(
				commissionTermRecords,
				commissionConfigRequest.getCustomerBPartnerId(),
				commissionConfigRequest.getSalesProductId());
		if (result.size() != 1)
		{
			throw new AdempiereException("The given commissionConfigRequest needs specify exactly one CommissionConfig")
					.appendParametersToMessage()
					.setParameter("result.size()", result.size())
					.setParameter("commissionConfigRequest", commissionConfigRequest)
					.setParameter("result", result);

		}
		return result.get(0);
	}

	private ImmutableList<CommissionConfig> createCommissionConfigsFor(
			@NonNull final ImmutableList<I_C_Flatrate_Term> termRecords,
			@NonNull final BPartnerId customerBPartnerId,
			@NonNull final ProductId salesproductId)
	{
		final StagingData stagingData = commissionConfigStagingDataService.retrieveStagingData(termRecords);

		final ImmutableListMultimap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds = stagingData.getBPartnerId2FlatrateTermIds();
		final ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds = stagingData.getConditionRecordId2BPartnerIds();

		final ImmutableList.Builder<CommissionConfig> commissionConfigs = ImmutableList.<CommissionConfig> builder();

		final ProductCategoryId salesProductCategory = productDAO.retrieveProductCategoryByProductId(salesproductId);
		final BPGroupId customerGroupId = bPartnerDAO.getBPGroupIdByBPartnerId(customerBPartnerId);

		for (final Entry<Integer, Collection<Integer>> settingsId2TermsIds : stagingData.getSettingsId2termIds().asMap().entrySet())
		{
			final Integer settingsId = settingsId2TermsIds.getKey();

			final I_C_HierarchyCommissionSettings settingsRecord = stagingData.getId2SettingsRecord().get(settingsId);
			final HierarchyConfigBuilder builder = HierarchyConfig
					.builder()
					.commissionProductId(ProductId.ofRepoId(settingsRecord.getCommission_Product_ID()))
					.subtractLowerLevelCommissionFromBase(settingsRecord.isSubtractLowerLevelCommissionFromBase());

			for (final Integer termRepoId : settingsId2TermsIds.getValue())
			{
				final I_C_Flatrate_Term termRecord = stagingData.getId2TermRecord().get(termRepoId);

				final FlatrateTermId termId = FlatrateTermId.ofRepoId(termRepoId);

				final ImmutableList<BPartnerId> bPartnerIds = conditionRecordId2BPartnerIds.get(termRecord.getC_Flatrate_Conditions_ID());
				for (final BPartnerId bPartnerId : bPartnerIds)
				{
					if (!bPartnerId2FlatrateTermIds.get(bPartnerId).contains(termId))
					{
						continue;
					}

					final HierarchyContractBuilder contractBuilder = HierarchyContract.builder()
							.id(termId)
							.pointsPrecision(settingsRecord.getPointsPrecision());

					boolean foundMatchingSettingsLine = false;
					final ImmutableListMultimap<Integer, Integer> settingsId2settingsLineIds = stagingData.getSettingsId2settingsLineIds();
					for (final Integer settingsLineId : settingsId2settingsLineIds.get(settingsId))
					{
						final I_C_CommissionSettingsLine settingsLineRecord = stagingData.getId2SettingsLineRecord().get(settingsLineId);

						final boolean settingsLineMatches = settingsLineRecordMatches(salesProductCategory, customerGroupId, settingsLineRecord);
						if (settingsLineMatches)
						{
							contractBuilder.commissionPercent(Percent.of(settingsLineRecord.getPercentOfBasePoints()));
							foundMatchingSettingsLine = true;
							break;
						}
					}
					if (foundMatchingSettingsLine)
					{
						builder.beneficiary2HierarchyContract(Beneficiary.of(bPartnerId), contractBuilder);
					}
				}
			}
			final HierarchyConfig config = builder.build();
			if (config.containsContracts()) // discard it if there aren't any beneficiaries/contracts
			{
				commissionConfigs.add(config);
			}
		}
		return commissionConfigs.build();
	}

	private boolean settingsLineRecordMatches(
			@Nullable final ProductCategoryId salesProductCategoryId,
			@Nullable final BPGroupId customerGroupId,
			@NonNull final I_C_CommissionSettingsLine settingsLineRecord)
	{

		final BPGroupId recordBPGroupId = BPGroupId.ofRepoIdOrNull(settingsLineRecord.getC_BP_Group_ID());
		final ProductCategoryId recordProductCategoryId = ProductCategoryId.ofRepoIdOrNull(settingsLineRecord.getM_Product_Category_ID());

		final boolean productMatches;
		if (recordProductCategoryId == null)
		{
			productMatches = true;
		}
		else
		{
			final boolean productCategoryIdEquals = recordProductCategoryId.equals(salesProductCategoryId);
			productMatches = settingsLineRecord.isExcludeProductCategory() ? !productCategoryIdEquals : productCategoryIdEquals;
		}


		final boolean bPartnerMatches;
		if (recordBPGroupId == null)
		{
			bPartnerMatches = true;
		}
		else
		{
			final boolean groupIdEquals = recordBPGroupId.equals(customerGroupId);
			bPartnerMatches = settingsLineRecord.isExcludeBPGroup() ? !groupIdEquals : groupIdEquals;
		}

		final boolean settingsLineMatches = bPartnerMatches && productMatches;
		return settingsLineMatches;
	}

	@Builder
	@Value
	public static class ConfigRequestForNewInstance
	{
		@NonNull
		BPartnerId salesRepBPartnerId;

		/** Needed because config settings can be specific to the customer's group. */
		@NonNull
		BPartnerId customerBPartnerId;

		/** Needed because config settings can be specific to the product's category. */
		@NonNull
		ProductId salesProductId;

		@NonNull
		LocalDate date;

		@NonNull
		Hierarchy commissionHierarchy;
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
