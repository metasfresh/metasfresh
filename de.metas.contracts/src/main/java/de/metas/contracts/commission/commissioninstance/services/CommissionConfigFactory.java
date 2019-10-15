package de.metas.contracts.commission.commissioninstance.services;

import java.time.LocalDate;
import java.util.List;
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

	ImmutableList<CommissionConfig> createFor(@NonNull final ContractRequest contractRequest)
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

		// TODO create those three in just one loop
		final ImmutableMap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds = extractBPartnerId2TermRecords(commissionTermRecords);
		final ImmutableListMultimap<BPartnerId, Integer> bPartnerId2ConditionRecordIds = extractBPartnerId2ConditionRecordIds(commissionTermRecords);
		final ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds = extractConditionRecordId2BPartnerIds(commissionTermRecords);

		final ImmutableSet<Integer> conditionRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				commissionTermRecords,
				I_C_Flatrate_Term::getC_Flatrate_Conditions_ID));

		final List<I_C_HierarchyCommissionSettings> commisionSettingsRecords = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_HierarchyCommissionSettings.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_HierarchyCommissionSettings.COLUMN_C_Flatrate_Conditions_ID, conditionRecordIds)
				.create()
				.list();

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

	private ImmutableMap<BPartnerId, FlatrateTermId> extractBPartnerId2TermRecords(
			@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final ImmutableMap.Builder<BPartnerId, FlatrateTermId> result = ImmutableMap.<BPartnerId, FlatrateTermId> builder();
		for (final I_C_Flatrate_Term commissionTermRecord : commissionTermRecords)
		{
			result.put(
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()),
					FlatrateTermId.ofRepoId(commissionTermRecord.getC_Flatrate_Term_ID()));
		}
		return result.build();
	}

	private ImmutableListMultimap<BPartnerId, Integer> extractBPartnerId2ConditionRecordIds(
			@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final ImmutableListMultimap.Builder<BPartnerId, Integer> result = ImmutableListMultimap.<BPartnerId, Integer> builder();
		for (final I_C_Flatrate_Term commissionTermRecord : commissionTermRecords)
		{
			result.put(
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()),
					commissionTermRecord.getC_Flatrate_Conditions_ID());
		}
		return result.build();
	}

	private ImmutableListMultimap<Integer, BPartnerId> extractConditionRecordId2BPartnerIds(
			@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final ImmutableListMultimap.Builder<Integer, BPartnerId> result = ImmutableListMultimap.<Integer, BPartnerId> builder();
		for (final I_C_Flatrate_Term commissionTermRecord : commissionTermRecords)
		{
			result.put(
					commissionTermRecord.getC_Flatrate_Conditions_ID(),
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()));
		}
		return result.build();
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
}
