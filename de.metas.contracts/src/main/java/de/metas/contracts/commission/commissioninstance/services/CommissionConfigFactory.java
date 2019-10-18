package de.metas.contracts.commission.commissioninstance.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
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

		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = Services.get(IFlatrateDAO.class)
				.retrieveTerms( // TODO modernize this oldie
						Env.getCtx(),
						contractRequest.getBPartnerId().getRepoId(), // TODO instead give it all allBPartnerIds at once!
						TimeUtil.asTimestamp(contractRequest.getDate()),
						-1 /* m_Product_Category_ID */,
						contractRequest.getProductId().getRepoId(),
						-1 /* c_Charge_ID */,
						ITrx.TRXNAME_None)
				.stream()
				.filter(termRecord -> CommissionConstants.TYPE_CONDITIONS_COMMISSION.equals(termRecord.getType_Conditions()))
				.collect(ImmutableList.toImmutableList());

		final Mappings mappings = extractMappings(commissionTermRecords);
		final ImmutableMap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds = mappings.getBPartnerId2FlatrateTermIds();
		final ImmutableListMultimap<BPartnerId, Integer> bPartnerId2ConditionRecordIds = mappings.getBPartnerId2ConditionRecordIds();
		final ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds = mappings.getConditionRecordId2BPartnerIds();

		final List<I_C_HierarchyCommissionSettings> commisionSettingsRecords = retrieveHierachySettings(commissionTermRecords);

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





	private Mappings extractMappings(			@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
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

	public CommissionConfig createFor(ImmutableList<FlatrateTermId> flatrateTermIds)
	{
		final ImmutableList<I_C_Flatrate_Term> commissionTermRecords = Services.get(IFlatrateDAO.class)
				.retrieveTerms(flatrateTermIds)
				.stream()
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> commisionSettingsRecords = retrieveHierachySettings(commissionTermRecords);

		for (final Entry<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> entry : commisionSettingsRecords.entrySet())
		{
			final I_C_Flatrate_Term commissionTermRecord = entry.getKey();
			final I_C_HierarchyCommissionSettings commisionSettingsRecord = entry.getValue();

			final HierarchyContractBuilder contractBuilder = HierarchyContract.builder()
					.id(FlatrateTermId.ofRepoId(commissionTermRecord.getC_Flatrate_Term_ID()))
					.commissionPercent(Percent.of(commisionSettingsRecord.getPercentOfBasePoints()))
					.pointsPrecision(2);

			final HierarchyConfigBuilder builder = HierarchyConfig
					.builder()
					.subtractLowerLevelCommissionFromBase(commisionSettingsRecord.isSubtractLowerLevelCommissionFromBase());

			builder.beneficiary2HierarchyContract(
					Beneficiary.of(BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID())),
					contractBuilder);

		}

		// TODO Auto-generated method stub
		return null;
	}

	private ImmutableMap<I_C_Flatrate_Term, I_C_HierarchyCommissionSettings> retrieveHierachySettings(final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final Mappings mappings = extractMappings(commissionTermRecords);

		final ImmutableSet<Integer> conditionRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				commissionTermRecords,
				I_C_Flatrate_Term::getC_Flatrate_Conditions_ID));

		final List<I_C_HierarchyCommissionSettings> commisionSettingsRecords = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_HierarchyCommissionSettings.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_HierarchyCommissionSettings.COLUMN_C_Flatrate_Conditions_ID, conditionRecordIds)
				.create()
				.list();


		return commisionSettingsRecords;
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
