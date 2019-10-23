package de.metas.contracts.commission.commissioninstance.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;

import de.metas.bpartner.BPartnerId;
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
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
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

		return createCommissionConfigsFor(commissionTermRecords);
	}

	public ImmutableList<CommissionConfig> createCommissionConfigsFor(@NonNull final ImmutableList<I_C_Flatrate_Term> termRecords)
	{
		final StagingData stagingData = extractMappings(termRecords);

		final ImmutableMap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds = stagingData.getBPartnerId2FlatrateTermIds();
		final ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds = stagingData.getConditionRecordId2BPartnerIds();

		final ImmutableList.Builder<CommissionConfig> result = ImmutableList.<CommissionConfig> builder();

		for (final Entry<Integer, Collection<Integer>> settingsId2TermsIds : stagingData.getSettingsId2termIds().asMap().entrySet())
		{
			final I_C_HierarchyCommissionSettings settingsRecord = stagingData.getId2SettingsRecord().get(settingsId2TermsIds.getKey());
			final HierarchyConfigBuilder builder = HierarchyConfig
					.builder()
					.subtractLowerLevelCommissionFromBase(settingsRecord.isSubtractLowerLevelCommissionFromBase());

			for (final Integer termId : settingsId2TermsIds.getValue())
			{
				final I_C_Flatrate_Term termRecord = stagingData.getId2TermRecord().get(termId);

				final ImmutableList<BPartnerId> bPartnerIds = conditionRecordId2BPartnerIds.get(termRecord.getC_Flatrate_Conditions_ID());
				for (final BPartnerId bPartnerId : bPartnerIds)
				{
					final HierarchyContractBuilder contractBuilder = HierarchyContract.builder()
							.id(bPartnerId2FlatrateTermIds.get(bPartnerId))
							.commissionPercent(Percent.of(settingsRecord.getPercentOfBasePoints()))
							.pointsPrecision(2); // TODO get from the commission-products UOM precision
					builder.beneficiary2HierarchyContract(Beneficiary.of(bPartnerId), contractBuilder);
				}
			}
			result.add(builder.build());
		}
		return result.build();
	}

	private StagingData extractMappings(@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		final ImmutableMap<Integer, I_C_Flatrate_Term> //
		id2TermRecord = Maps.uniqueIndex(commissionTermRecords, I_C_Flatrate_Term::getC_Flatrate_Term_ID);

		final ImmutableSet<Integer> conditionsRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				commissionTermRecords,
				I_C_Flatrate_Term::getC_Flatrate_Conditions_ID));
		final List<I_C_Flatrate_Conditions> conditionsRecords = InterfaceWrapperHelper.loadByIdsOutOfTrx(conditionsRecordIds, I_C_Flatrate_Conditions.class);

		final ImmutableMap<Integer, I_C_Flatrate_Conditions> //
		id2ConditionsRecord = Maps.uniqueIndex(conditionsRecords, I_C_Flatrate_Conditions::getC_Flatrate_Conditions_ID);

		final ImmutableSet<Integer> settingsRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				conditionsRecords,
				I_C_Flatrate_Conditions::getC_HierarchyCommissionSettings_ID));
		final List<I_C_HierarchyCommissionSettings> settingsRecords = InterfaceWrapperHelper.loadByIdsOutOfTrx(settingsRecordIds, I_C_HierarchyCommissionSettings.class);

		final ImmutableMap<Integer, I_C_HierarchyCommissionSettings> //
		id2SettingsRecord = Maps.uniqueIndex(settingsRecords, I_C_HierarchyCommissionSettings::getC_HierarchyCommissionSettings_ID);

		final ListMultimap<Integer, Integer> settingsId2termIds = MultimapBuilder.hashKeys().arrayListValues().build();
		final ImmutableListMultimap.Builder<Integer, BPartnerId> conditionRecordId2BPartnerIds = ImmutableListMultimap.<Integer, BPartnerId> builder();
		final ImmutableMap.Builder<BPartnerId, FlatrateTermId> bpartnerId2FlatrateTermId = ImmutableMap.<BPartnerId, FlatrateTermId> builder();

		for (final I_C_Flatrate_Term commissionTermRecord : commissionTermRecords)
		{
			final I_C_Flatrate_Conditions conditionsRecord = id2ConditionsRecord.get(commissionTermRecord.getC_Flatrate_Conditions_ID());
			settingsId2termIds.put(conditionsRecord.getC_HierarchyCommissionSettings_ID(), commissionTermRecord.getC_Flatrate_Term_ID());

			conditionRecordId2BPartnerIds.put(
					commissionTermRecord.getC_Flatrate_Conditions_ID(),
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()));
			bpartnerId2FlatrateTermId.put(
					BPartnerId.ofRepoId(commissionTermRecord.getBill_BPartner_ID()),
					FlatrateTermId.ofRepoId(commissionTermRecord.getC_Flatrate_Term_ID()));
		}

		return StagingData.builder()
				.id2TermRecord(id2TermRecord)
				.id2ConditionsRecord(id2ConditionsRecord)
				.id2SettingsRecord(id2SettingsRecord)
				.settingsId2termIds(ImmutableMultimap.copyOf(settingsId2termIds))
				.conditionRecordId2BPartnerIds(conditionRecordId2BPartnerIds.build())
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

		final ImmutableList<CommissionConfig> result = createCommissionConfigsFor(commissionTermRecords);
		if (result.size() != 1)
		{
			throw new AdempiereException("The given flatrateTermIds need to belong all to one CommissionConfig")
					.appendParametersToMessage()
					.setParameter("result.size()", result.size())
					.setParameter("flatrateTermIds", flatrateTermIds)
					.setParameter("result", result);

		}
		return result.get(0);
	}

	@Value
	@Builder
	private static class StagingData
	{
		@NonNull
		ImmutableMap<Integer, I_C_Flatrate_Term> id2TermRecord;

		@NonNull
		ImmutableMap<Integer, I_C_Flatrate_Conditions> id2ConditionsRecord;

		@NonNull
		ImmutableMap<Integer, I_C_HierarchyCommissionSettings> id2SettingsRecord;

		@NonNull
		ImmutableMap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds;

		@NonNull
		ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds;

		@NonNull
		ImmutableMultimap<Integer, Integer> settingsId2termIds;
	}
}
