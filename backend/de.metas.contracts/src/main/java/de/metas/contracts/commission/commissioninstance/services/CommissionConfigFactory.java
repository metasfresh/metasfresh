package de.metas.contracts.commission.commissioninstance.services;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateDAO.TermsQuery;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionSettingsLineId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig.HierarchyConfigBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfigId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract.HierarchyContractBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigStagingDataService.StagingData;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static de.metas.contracts.commission.CommissionConstants.NO_COMMISSION_AGREEMENT_DEFAULT_CONTRACT_DURATION;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.DOCSTATUS_Completed;

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
public class CommissionConfigFactory implements ICommissionConfigFactory
{
	private static final Logger logger = LogManager.getLogger(CommissionConfigFactory.class);

	private final CommissionConfigStagingDataService commissionConfigStagingDataService;
	private final CommissionProductService commissionProductService;

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private final ImmutableSet<CommissionTriggerType> SUPPORTED_TRIGGER_TYPES = ImmutableSet.of(CommissionTriggerType.SalesInvoice,
																								CommissionTriggerType.InvoiceCandidate,
																								CommissionTriggerType.SalesCreditmemo,
																								CommissionTriggerType.Plain);

	public CommissionConfigFactory(
			@NonNull final CommissionConfigStagingDataService commissionConfigStagingDataService,
			@NonNull final CommissionProductService commissionProductService)
	{
		this.commissionConfigStagingDataService = commissionConfigStagingDataService;
		this.commissionProductService = commissionProductService;
	}

	public ImmutableList<CommissionConfig> createForNewCommissionInstances(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
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
		final ImmutableList.Builder<I_C_Flatrate_Term> commissionTermRecordsBuilder = ImmutableList.builder();

		flatrateDAO
				.retrieveTerms(termsQuery)
				.stream()
				.filter(termRecord -> TypeConditions.COMMISSION.getCode().equals(termRecord.getType_Conditions()))
				.forEach(commissionTermRecordsBuilder::add);

		getConditionsForSalesRepWithoutContract()
				.map(flatrateConditions -> createGenericContract(flatrateConditions, commissionTermRecordsBuilder.build(), allBPartnerIds, beneficiary))
				.ifPresent(contractTermList -> contractTermList.forEach(commissionTermRecordsBuilder::add));

		final ImmutableMap<FlatrateTermId, CommissionConfig> contractId2Config = createCommissionConfigsFor(
				commissionTermRecordsBuilder.build(),
				contractRequest.getCustomerBPartnerId(),
				contractRequest.getSalesProductId());

		return CollectionUtils.extractDistinctElements(contractId2Config.values(), Function.identity());
	}

	@Override
	public boolean appliesFor(@NonNull final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest)
	{
		return SUPPORTED_TRIGGER_TYPES.contains(contractRequest.getCommissionTriggerType());
	}

	@NonNull
	public ImmutableMap<FlatrateTermId, CommissionConfig> createForExistingInstance(
			@NonNull final CommissionConfigProvider.ConfigRequestForExistingInstance commissionConfigRequest)
	{
		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = flatrateDAO
				.retrieveTerms(commissionConfigRequest.getContractIds())
				.stream()
				.filter(termRecord -> TypeConditions.COMMISSION.getCode().equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		if (commissionTermRecords.isEmpty())
		{
			return ImmutableMap.of();
		}

		return createCommissionConfigsFor(
				commissionTermRecords,
				commissionConfigRequest.getCustomerBPartnerId(),
				commissionConfigRequest.getSalesProductId());
	}

	/**
	 * Creates commission contracts for bpartners that don't have one yet
	 */
	@NonNull
	private ImmutableList<I_C_Flatrate_Term> createGenericContract(
			@NonNull final I_C_Flatrate_Conditions flatrateCondition,
			@NonNull final ImmutableList<I_C_Flatrate_Term> existingCommissionTermRecords,
			@NonNull final ImmutableList<BPartnerId> hierarchyBPartnerIds,
			@NonNull final Beneficiary endCustomer)
	{
		final ImmutableSet<Integer> salesRepIdsWithContract = existingCommissionTermRecords.stream()
				.map(I_C_Flatrate_Term::getBill_BPartner_ID)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<BPartnerId> salesRepIdsWithoutContract = hierarchyBPartnerIds.stream()
				.filter(salesRepId -> !salesRepIdsWithContract.contains(salesRepId.getRepoId()))
				.filter(salesRepId -> salesRepId.getRepoId() != endCustomer.getBPartnerId().getRepoId())
				.collect(ImmutableSet.toImmutableSet());

		if (salesRepIdsWithoutContract.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_C_BPartner> bPartnersWithoutContracts = bPartnerDAO.retrieveByIds(salesRepIdsWithoutContract);

		final ProductId commissionProductId = commissionProductService.getCommissionProduct(ConditionsId.ofRepoId(flatrateCondition.getC_Flatrate_Conditions_ID()));

		final I_M_Product commissionProductRecord = productDAO.getById(commissionProductId);

		final FlatrateTermCreator termCreator = FlatrateTermCreator.builder()
				.ctx(Env.getCtx())
				.products(ImmutableList.of(commissionProductRecord))
				.startDate(Timestamp.from(Instant.now()))
				.endDate(Timestamp.from(Instant.now().plus(NO_COMMISSION_AGREEMENT_DEFAULT_CONTRACT_DURATION)))
				.conditions(flatrateCondition)
				.bPartners(bPartnersWithoutContracts)
				.isCompleteDocument(true)
				.build();

		Loggables.withLogger(logger, Level.INFO).addLog("Commission contracts created for bPartners: {}", salesRepIdsWithoutContract);
		return termCreator.createTermsForBPartners();
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

	@NonNull
	private Optional<I_C_Flatrate_Conditions> getConditionsForSalesRepWithoutContract()
	{
		final I_C_Flatrate_Conditions flatrateConditions = flatrateDAO.getConditionsById(CommissionConstants.FLATRATE_CONDITION_0_COMMISSION_ID);

		if (flatrateConditions == null
				|| !flatrateConditions.isActive()
				|| !flatrateConditions.getDocStatus().equals(DOCSTATUS_Completed))
		{
			return Optional.empty();
		}

		return Optional.of(flatrateConditions);
	}
}
