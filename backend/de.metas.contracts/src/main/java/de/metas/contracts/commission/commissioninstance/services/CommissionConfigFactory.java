package de.metas.contracts.commission.commissioninstance.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateDAO.TermsQuery;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionSettingsLineId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig.HierarchyConfigBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfigId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract.HierarchyContractBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigStagingDataService.StagingData;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import de.metas.util.lang.RepoIdAwares;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.StreamSupport;

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
	private static final Logger logger = LogManager.getLogger(CommissionConfigFactory.class);

	private final CommissionConfigStagingDataService commissionConfigStagingDataService;

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
		
		// Note: we start with the customer which *might* be a sales rep too. 
		// If that's the case, the contract's C_HierarchyCommissionSettings might indicate that we need a 0% C_CommissionShare for that endcustomer
		final Beneficiary beneficiary = Beneficiary.of(contractRequest.getCustomerBPartnerId()); 
		final Iterable<HierarchyNode> beneficiaries = hierarchy.getUpStream(beneficiary);

		final ImmutableList<BPartnerId> allBPartnerIds = StreamSupport
				.stream(beneficiaries.spliterator(), false)
				.map(HierarchyNode::getBeneficiary)
				.map(Beneficiary::getBPartnerId)
				.collect(ImmutableList.toImmutableList());

		// don't look up the terms via product; instead, get all commission-terms for the respective sales reps that are currently active
		final TermsQuery termsQuery = TermsQuery.builder()
				.billPartnerIds(allBPartnerIds)
				.orgId(contractRequest.getOrgId())
				.dateOrdered(contractRequest.getCommissionDate())
				.build();
		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = flatrateDAO
				.retrieveTerms(termsQuery)
				.stream()
				.filter(termRecord -> CommissionConstants.TYPE_CONDITIONS_COMMISSION.equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap<FlatrateTermId, CommissionConfig> contractId2Config = createCommissionConfigsFor(
				commissionTermRecords,
				contractRequest.getCustomerBPartnerId(),
				contractRequest.getSalesProductId());

		return CollectionUtils.extractDistinctElements(contractId2Config.values(), Function.identity());
	}

	public ImmutableMap<FlatrateTermId, CommissionConfig> createForExisingInstance(
			@NonNull final ConfigRequestForExistingInstance commissionConfigRequest)
	{
		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = flatrateDAO
				.retrieveTerms(commissionConfigRequest.getContractIds())
				.stream()
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap<FlatrateTermId, CommissionConfig> result = createCommissionConfigsFor(
				commissionTermRecords,
				commissionConfigRequest.getCustomerBPartnerId(),
				commissionConfigRequest.getSalesProductId());
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

	private ImmutableMap<FlatrateTermId, CommissionConfig> createCommissionConfigsFor(
			@NonNull final ImmutableList<I_C_Flatrate_Term> termRecords,
			@NonNull final BPartnerId customerBPartnerId,
			@NonNull final ProductId salesProductId)
	{
		try (final MDCCloseable ignore = MDC.putCloseable("salesProductId", Integer.toString(RepoIdAwares.toRepoId(salesProductId)));
				final MDCCloseable ignore1 = MDC.putCloseable("customerBPartnerId", Integer.toString(RepoIdAwares.toRepoId(customerBPartnerId))))
		{
			final StagingData stagingData = commissionConfigStagingDataService.retrieveStagingData(termRecords);

			final ImmutableListMultimap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds = stagingData.getBPartnerId2FlatrateTermIds();
			final ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds = stagingData.getConditionRecordId2BPartnerIds();

			final ProductCategoryId salesProductCategoryId = productDAO.retrieveProductCategoryByProductId(salesProductId);
			final BPGroupId customerGroupId = bPartnerDAO.getBPGroupIdByBPartnerId(customerBPartnerId);

			final ImmutableSet<Entry<Integer, Collection<Integer>>> settingsIdWithTermId = stagingData.getSettingsId2termIds()
					.asMap()
					.entrySet();

			final ArrayList<HierarchyConfig> hierarchyConfigs = new ArrayList<>();

			// i know the nesting is way too deep here; however if i extract methods, i end up with a lot of parameters per method
			// or with big "request"-classes that i use as params.
			for (final Entry<Integer, Collection<Integer>> settingsId2TermsIds : settingsIdWithTermId)
			{
				final Integer settingsId = settingsId2TermsIds.getKey();

				final I_C_HierarchyCommissionSettings settingsRecord = stagingData.getId2SettingsRecord().get(settingsId);
				try (final MDCCloseable ignore2 = TableRecordMDC.putTableRecordReference(settingsRecord))
				{
					final HierarchyConfigBuilder configBuilder = HierarchyConfig
							.builder()
							.id(HierarchyConfigId.ofRepoId(settingsRecord.getC_HierarchyCommissionSettings_ID()))
							.commissionProductId(ProductId.ofRepoId(settingsRecord.getCommission_Product_ID()))
							.subtractLowerLevelCommissionFromBase(settingsRecord.isSubtractLowerLevelCommissionFromBase());

					for (final Integer termRepoId : settingsId2TermsIds.getValue())
					{
						final I_C_Flatrate_Term termRecord = stagingData.getId2TermRecord().get(termRepoId);
						try (final MDCCloseable ignore3 = TableRecordMDC.putTableRecordReference(termRecord))
						{
							final FlatrateTermId termId = FlatrateTermId.ofRepoId(termRepoId);

							final ImmutableList<BPartnerId> salesRepBPartnerIds = conditionRecordId2BPartnerIds.get(termRecord.getC_Flatrate_Conditions_ID());
							for (final BPartnerId salesRepBPartnerId : salesRepBPartnerIds)
							{
								if (!bPartnerId2FlatrateTermIds.get(salesRepBPartnerId).contains(termId))
								{
									continue;
								}
								final HierarchyContractBuilder contractBuilder = HierarchyContract.builder()
										.id(termId)
										.isSimulation(termRecord.isSimulation())
										.pointsPrecision(settingsRecord.getPointsPrecision());

								boolean foundMatchingSettingsLine = false;
								final ImmutableListMultimap<Integer, Integer> settingsId2settingsLineIds = stagingData.getSettingsId2settingsLineIds();
								for (final Integer settingsLineId : settingsId2settingsLineIds.get(settingsId))
								{
									final I_C_CommissionSettingsLine settingsLineRecord = stagingData.getId2SettingsLineRecord().get(settingsLineId);
									try (final MDCCloseable ignore4 = TableRecordMDC.putTableRecordReference(settingsLineRecord))
									{
										final boolean settingsLineMatches = settingsLineRecordMatches(
												salesProductCategoryId,
												customerGroupId,
												customerBPartnerId,
												settingsLineRecord);
										if (settingsLineMatches)
										{
											if (Objects.equals(salesRepBPartnerId, customerBPartnerId))
											{
												if (settingsRecord.isCreateShareForOwnRevenue())
												{
													logger.debug("Settings line matches; salesRep is also endcustomer and createShareOnOwnReveneue=Y; -> commissionPercent=0");
													contractBuilder
															.commissionSettingsLineId(CommissionSettingsLineId.ofRepoId(settingsLineRecord.getC_CommissionSettingsLine_ID()))
															.commissionPercent(Percent.ZERO);
													foundMatchingSettingsLine = true;
												}
												else
												{
													logger.debug("Settings line matches; salesRep is also endcustomer and createShareOnOwnReveneue=N; -> nothing to do");
												}
											}
											else
											{
												logger.debug("Settings line matches; -> commissionPercent={}", settingsLineRecord.getPercentOfBasePoints());
												contractBuilder
														.commissionSettingsLineId(CommissionSettingsLineId.ofRepoId(settingsLineRecord.getC_CommissionSettingsLine_ID()))
														.commissionPercent(Percent.of(settingsLineRecord.getPercentOfBasePoints()));
												foundMatchingSettingsLine = true;
											}
											break;
										}
									}
								}
								if (foundMatchingSettingsLine)
								{
									configBuilder.beneficiary2HierarchyContract(Beneficiary.of(salesRepBPartnerId), contractBuilder);
								}
							}
						}
					}

					final HierarchyConfig config = configBuilder.build();
					if (config.containsContracts()) // discard it if there aren't any beneficiaries/contracts
					{
						hierarchyConfigs.add(config);
					}
				}
			}

			// finally index the configs by contract-Id
			final ImmutableMap.Builder<FlatrateTermId, CommissionConfig> result = ImmutableMap.builder();
			for (final HierarchyConfig hierarchyConfig : hierarchyConfigs)
			{
				final int settingsRepoId = hierarchyConfig.getId().getRepoId();
				for (final Integer contractId : stagingData.getSettingsId2termIds().get(settingsRepoId))
				{
					result.put(FlatrateTermId.ofRepoId(contractId), hierarchyConfig);
				}
			}
			return result.build();
		}
	}

	private boolean settingsLineRecordMatches(
			@Nullable final ProductCategoryId salesProductCategoryId,
			@Nullable final BPGroupId customerGroupId,
			@NonNull final BPartnerId customerPartnerId,
			@NonNull final I_C_CommissionSettingsLine settingsLineRecord)
	{
		final ProductCategoryId settingsProductCategoryId = ProductCategoryId.ofRepoIdOrNull(settingsLineRecord.getM_Product_Category_ID());
		final String logMessagePrefix = "SeqNo " + settingsLineRecord.getSeqNo() + ": ";
		final boolean productMatches;
		if (settingsProductCategoryId == null)
		{
			logger.debug(logMessagePrefix + "settingsProductCategoryId is null; ->productMatches=true;");
			productMatches = true;
		}
		else
		{
			final boolean productCategoryIdEquals = settingsProductCategoryId.equals(salesProductCategoryId);
			productMatches = settingsLineRecord.isExcludeProductCategory() ? !productCategoryIdEquals : productCategoryIdEquals;
			logger.debug(logMessagePrefix + "settingsProductCategoryId={} and salesProductCategoryId={} match={}; excludeProductCategory={}; -> productMatches={}",
					RepoIdAwares.toRepoId(settingsProductCategoryId), RepoIdAwares.toRepoId(salesProductCategoryId), productCategoryIdEquals, settingsLineRecord.isExcludeProductCategory(), productMatches);
		}
		final boolean customerMatches;
		final BPGroupId settingsCustomerGroupId = BPGroupId.ofRepoIdOrNull(settingsLineRecord.getCustomer_Group_ID());
		final BPartnerId settingsCustomerId = BPartnerId.ofRepoIdOrNull(settingsLineRecord.getC_BPartner_Customer_ID());
		if (settingsCustomerGroupId == null && settingsCustomerId == null)
		{
			logger.debug(logMessagePrefix + "settingsCustomerGroupId and settingsCustomerId are null; => customerMatches=true");
			customerMatches = true;
		}
		else
		{
			final boolean groupIdEquals = Objects.equals(settingsCustomerGroupId, customerGroupId);
			final boolean partnerIdEquals = Objects.equals(settingsCustomerId, customerPartnerId);
			final boolean groupOrPartnerEquals = groupIdEquals || partnerIdEquals;
			customerMatches = settingsLineRecord.isExcludeBPGroup() ? !groupOrPartnerEquals : groupOrPartnerEquals;
			logger.debug(logMessagePrefix + "settingsCustomerGroupId={} and customerGroupId={} match={}; settingsCustomerId={} and customerPartnerId{} match={}; excludeBPGroup={}; -> customerMatches={}",
					RepoIdAwares.toRepoId(settingsCustomerGroupId), RepoIdAwares.toRepoId(customerGroupId), groupIdEquals, RepoIdAwares.toRepoId(settingsCustomerId), RepoIdAwares.toRepoId(customerPartnerId), partnerIdEquals, settingsLineRecord.isExcludeBPGroup(), customerMatches);
		}

		final boolean settingsLineMatches = customerMatches && productMatches;
		logger.debug(logMessagePrefix + "customerMatches={}; productMatches={}; -> settingsLineMatches={}", customerMatches, productMatches, settingsLineMatches);
		return settingsLineMatches;
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
