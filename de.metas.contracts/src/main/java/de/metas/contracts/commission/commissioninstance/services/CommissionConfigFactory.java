package de.metas.contracts.commission.commissioninstance.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateDAO.TermsQuery;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig.HierarchyConfigBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract.HierarchyContractBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
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
	private final CommissionHierarchyFactory commissionHierarchyFactory;
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public CommissionConfigFactory(@NonNull final CommissionHierarchyFactory commissionHierarchyFactory)
	{
		this.commissionHierarchyFactory = commissionHierarchyFactory;
	}

	public ImmutableList<CommissionConfig> createFor(@NonNull final ContractRequest contractRequest)
	{
		final Hierarchy hierarchy = commissionHierarchyFactory.createFor(contractRequest.getBPartnerId());
		final Iterable<HierarchyNode> beneficiaries = hierarchy.getUpStream(Beneficiary.of(contractRequest.getBPartnerId()));

		final ImmutableList<BPartnerId> allBPartnerIds = StreamSupport
				.stream(beneficiaries.spliterator(), false)
				.map(HierarchyNode::getBeneficiary)
				.map(Beneficiary::getBPartnerId)
				.collect(ImmutableList.toImmutableList());

		final TermsQuery termsQuery = TermsQuery.builder()
				.billPartnerIds(allBPartnerIds)
				.productId(contractRequest.getProductId())
				.dateOrdered(contractRequest.getDate())
				.build();

		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = flatrateDAO
				.retrieveTerms(termsQuery)
				.stream()
				.filter(termRecord -> CommissionConstants.TYPE_CONDITIONS_COMMISSION.equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		final Mappings mappings = extractMappings(commissionTermRecords);
		final ImmutableMap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds = mappings.getBPartnerId2FlatrateTermIds();
		final ImmutableListMultimap<BPartnerId, Integer> bPartnerId2ConditionRecordIds = mappings.getBPartnerId2ConditionRecordIds();
		final ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds = mappings.getConditionRecordId2BPartnerIds();

		final ImmutableList<I_C_HierarchyCommissionSettings> //
		commisionSettingsRecords = retrieveSettingsRecords(commissionTermRecords);

		// TODO add a UC such that two settings can't point to the same conditions

		final ImmutableSet<Integer> conditionRecordIdsOfRequestBPartner = ImmutableSet.copyOf(bPartnerId2ConditionRecordIds.get(contractRequest.getBPartnerId()));

		final ImmutableList.Builder<CommissionConfig> result = ImmutableList.<CommissionConfig> builder();
		for (final I_C_HierarchyCommissionSettings commisionSettingsRecord : commisionSettingsRecords)
		{
			final boolean requestBPartnerHasConditions = conditionRecordIdsOfRequestBPartner.contains(commisionSettingsRecord.getC_Flatrate_Conditions_ID());
			if (!requestBPartnerHasConditions)
			{
				continue;
			}

			final HierarchyConfigBuilder builder = HierarchyConfig
					.builder()
					.subtractLowerLevelCommissionFromBase(commisionSettingsRecord.isSubtractLowerLevelCommissionFromBase());

			final ImmutableList<BPartnerId> bPartnerIds = conditionRecordId2BPartnerIds.get(commisionSettingsRecord.getC_Flatrate_Conditions_ID());
			for (final BPartnerId bPartnerId : bPartnerIds)
			{
				final HierarchyContractBuilder contractBuilder = HierarchyContract.builder()
						.id(bPartnerId2FlatrateTermIds.get(bPartnerId))
						.commissionPercent(Percent.of(commisionSettingsRecord.getPercentOfBasePoints()))
						.pointsPrecision(2);
				builder.beneficiary2HierarchyContract(Beneficiary.of(bPartnerId), contractBuilder);
			}

			result.add(builder.build());
		}
		return result.build();
	}

	private Mappings extractMappings(@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final ImmutableListMultimap.Builder<BPartnerId, Integer> bpartnerId2ConditionRecordIds = ImmutableListMultimap.<BPartnerId, Integer> builder();
		final ImmutableListMultimap.Builder<Integer, BPartnerId> conditionRecordId2BPartnerIds = ImmutableListMultimap.<Integer, BPartnerId> builder();
		final ImmutableMap.Builder<BPartnerId, FlatrateTermId> bpartnerId2FlatrateTermId = ImmutableMap.<BPartnerId, FlatrateTermId> builder();

		for (final I_C_Flatrate_Term commissionTermRecord : commissionTermRecords)
		{
			conditionRecordId2BPartnerIds.put(
					commissionTermRecord.getC_Flatrate_Conditions_ID(),
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()));
			bpartnerId2ConditionRecordIds.put(
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()),
					commissionTermRecord.getC_Flatrate_Conditions_ID());
			bpartnerId2FlatrateTermId.put(
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()),
					FlatrateTermId.ofRepoId(commissionTermRecord.getC_Flatrate_Term_ID()));
		}

		return Mappings.builder()
				.conditionRecordId2BPartnerIds(conditionRecordId2BPartnerIds.build())
				.bPartnerId2ConditionRecordIds(bpartnerId2ConditionRecordIds.build())
				.bPartnerId2FlatrateTermIds(bpartnerId2FlatrateTermId.build())
				.build();
	}

	@Builder
	@Value
	public static class ContractRequest
	{
		@NonNull
		BPartnerId bPartnerId;

		@NonNull
		ProductId productId;

		@NonNull
		LocalDate date;
	}

	public CommissionConfig createFor(@NonNull final ImmutableList<FlatrateTermId> flatrateTermIds)
	{

		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = flatrateDAO
				.retrieveTerms(flatrateTermIds)
				.stream()
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> commisionSettingsRecords = retrieveTermRecord2settingsRecord(commissionTermRecords);

		final HierarchyConfigBuilder builder = HierarchyConfig
				.builder();

		for (final Entry<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> entry : commisionSettingsRecords.entrySet())
		{
			final I_C_Flatrate_Term commissionTermRecord = entry.getKey();
			final I_C_HierarchyCommissionSettings commisionSettingsRecord = entry.getValue();

			final HierarchyContractBuilder contractBuilder = HierarchyContract.builder()
					.id(FlatrateTermId.ofRepoId(commissionTermRecord.getC_Flatrate_Term_ID()))
					.commissionPercent(Percent.of(commisionSettingsRecord.getPercentOfBasePoints()))
					.pointsPrecision(2);

			builder.subtractLowerLevelCommissionFromBase(commisionSettingsRecord.isSubtractLowerLevelCommissionFromBase());

			builder.beneficiary2HierarchyContract(
					Beneficiary.of(BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID())),
					contractBuilder);
		}

		return builder.build();
	}

	private ImmutableMap<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> retrieveTermRecord2settingsRecord(
			@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final ImmutableSet<Integer> conditionRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				commissionTermRecords,
				I_C_Flatrate_Term::getC_Flatrate_Conditions_ID));

		final List<I_C_HierarchyCommissionSettings> commisionSettingsRecords = queryBL
				.createQueryBuilderOutOfTrx(I_C_HierarchyCommissionSettings.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_HierarchyCommissionSettings.COLUMN_C_Flatrate_Conditions_ID, conditionRecordIds)
				.create()
				.list();

		// each C_Flatrate_Conditionsrecord is referenced by just one C_HierarchyCommissionSettings record
		final ImmutableMap<Integer, I_C_HierarchyCommissionSettings> //
		conditionRecordId2CommissionSettings = Maps.uniqueIndex(commisionSettingsRecords, I_C_HierarchyCommissionSettings::getC_Flatrate_Conditions_ID);

		final ImmutableMap.Builder<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> //
		builder = ImmutableMap.<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> builder();

		for (final I_C_Flatrate_Term commissionTermRecord : commissionTermRecords)
		{
			final I_C_HierarchyCommissionSettings //
			commissionSettings = conditionRecordId2CommissionSettings.get(commissionTermRecord.getC_Flatrate_Conditions_ID());

			builder.put(commissionTermRecord, commissionSettings);
		}
		return builder.build();
	}

	private ImmutableList<I_C_HierarchyCommissionSettings> retrieveSettingsRecords(
			@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final ImmutableSet<Integer> conditionRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				commissionTermRecords,
				I_C_Flatrate_Term::getC_Flatrate_Conditions_ID));

		return queryBL
				.createQueryBuilderOutOfTrx(I_C_HierarchyCommissionSettings.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_HierarchyCommissionSettings.COLUMN_C_Flatrate_Conditions_ID, conditionRecordIds)
				.create()
				.listImmutable(I_C_HierarchyCommissionSettings.class);
	}

	@Value
	@Builder
	private static class Mappings
	{
		@NonNull
		ImmutableMap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds;

		@NonNull
		ImmutableListMultimap<BPartnerId, Integer> bPartnerId2ConditionRecordIds;

		@NonNull
		ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds;
	}
}
