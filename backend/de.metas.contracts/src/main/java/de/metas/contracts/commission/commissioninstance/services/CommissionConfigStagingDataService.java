package de.metas.contracts.commission.commissioninstance.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CommissionConfigStagingDataService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** Intended to be used by the actual repository. all the stating records loaded here are also cached. */
	public StagingData retrieveStagingData(@NonNull final ImmutableList<I_C_Flatrate_Term> commissionTermRecords)
	{
		// 1. load the actual records
		//
		// C_Flatrate_Term
		final ImmutableMap<Integer, I_C_Flatrate_Term> //
		id2TermRecord = Maps.uniqueIndex(commissionTermRecords, I_C_Flatrate_Term::getC_Flatrate_Term_ID);

		//
		// C_Flatrate_Conditions
		final ImmutableSet<Integer> conditionsRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				commissionTermRecords,
				I_C_Flatrate_Term::getC_Flatrate_Conditions_ID));
		final List<I_C_Flatrate_Conditions> conditionsRecords = InterfaceWrapperHelper.loadByIdsOutOfTrx(conditionsRecordIds, I_C_Flatrate_Conditions.class);

		final ImmutableMap<Integer, I_C_Flatrate_Conditions> //
		id2ConditionsRecord = Maps.uniqueIndex(conditionsRecords, I_C_Flatrate_Conditions::getC_Flatrate_Conditions_ID);

		//
		// C_HierarchyCommissionSettings
		final ImmutableSet<Integer> settingsRecordIds = ImmutableSet.copyOf(CollectionUtils.extractDistinctElements(
				conditionsRecords,
				I_C_Flatrate_Conditions::getC_HierarchyCommissionSettings_ID));
		final List<I_C_HierarchyCommissionSettings> settingsRecords = InterfaceWrapperHelper.loadByIdsOutOfTrx(settingsRecordIds, I_C_HierarchyCommissionSettings.class);

		final ImmutableMap<Integer, I_C_HierarchyCommissionSettings> //
		id2SettingsRecord = Maps.uniqueIndex(settingsRecords, I_C_HierarchyCommissionSettings::getC_HierarchyCommissionSettings_ID);

		//
		// C_CommissionSettingsLine
		final List<I_C_CommissionSettingsLine> settingsLineRecords = retrieveHierarchySettings(settingsRecordIds);
		final ImmutableMap<Integer, I_C_CommissionSettingsLine> //
		id2SettingsLineRecord = Maps.uniqueIndex(settingsLineRecords, I_C_CommissionSettingsLine::getC_CommissionSettingsLine_ID);

		// 2. create additional mappings between different records' IDs
		//
		final ListMultimap<Integer, Integer> settingsId2termIds = MultimapBuilder.hashKeys().arrayListValues().build();
		final ImmutableListMultimap.Builder<Integer, BPartnerId> conditionRecordId2BPartnerIds = ImmutableListMultimap.builder();
		final ImmutableListMultimap.Builder<BPartnerId, FlatrateTermId> bpartnerId2FlatrateTermId = ImmutableListMultimap.builder();

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

		final ImmutableListMultimap.Builder<Integer, Integer> settingsId2settingsLineIds = ImmutableListMultimap
				.builder(); // the list is and arraylist, so the settingsLines' ordering is preserved
		for (final I_C_CommissionSettingsLine settingsLineRecord : settingsLineRecords)
		{
			settingsId2settingsLineIds.put(settingsLineRecord.getC_HierarchyCommissionSettings_ID(), settingsLineRecord.getC_CommissionSettingsLine_ID());
		}

		return StagingData.builder()
				.id2TermRecord(id2TermRecord)
				.id2ConditionsRecord(id2ConditionsRecord)
				.id2SettingsRecord(id2SettingsRecord)
				.id2SettingsLineRecord(id2SettingsLineRecord)
				.settingsId2termIds(ImmutableMultimap.copyOf(settingsId2termIds))
				.conditionRecordId2BPartnerIds(conditionRecordId2BPartnerIds.build())
				.bPartnerId2FlatrateTermIds(bpartnerId2FlatrateTermId.build())
				.settingsId2settingsLineIds(settingsId2settingsLineIds.build())
				.build();
	}

	private final CCache<Integer, List<I_C_CommissionSettingsLine>> commissionSettingsLineRecordCache = CCache
			.<Integer, List<I_C_CommissionSettingsLine>> builder()
			.tableName(I_C_CommissionSettingsLine.Table_Name)
			.build();

	/**
	 * Notes:
	 * <li>{@link InterfaceWrapperHelper#loadByIdsOutOfTrx(java.util.Set, Class)} already does caching. Therefore the only thing we need to cache in this service class is this.
	 */
	private List<I_C_CommissionSettingsLine> retrieveHierarchySettings(@NonNull final ImmutableSet<Integer> settingsRecordIds)
	{
		final Collection<List<I_C_CommissionSettingsLine>> allRecords = commissionSettingsLineRecordCache.getAllOrLoad(
				settingsRecordIds,
				this::retrieveHierarchySettings0);

		return allRecords
				.stream()
				.flatMap(Collection::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private Map<Integer, List<I_C_CommissionSettingsLine>> retrieveHierarchySettings0(@NonNull final Collection<Integer> settingsRecordIds)
	{
		final List<I_C_CommissionSettingsLine> settingsLineRecords = queryBL.createQueryBuilderOutOfTrx(I_C_CommissionSettingsLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_CommissionSettingsLine.COLUMN_C_HierarchyCommissionSettings_ID, settingsRecordIds)
				.orderBy(I_C_CommissionSettingsLine.COLUMN_C_HierarchyCommissionSettings_ID)
				.orderBy(I_C_CommissionSettingsLine.COLUMN_SeqNo)
				.create()
				.list();

		final HashMap<Integer, List<I_C_CommissionSettingsLine>> result = new HashMap<>();
		for (final I_C_CommissionSettingsLine settingsLineRecord : settingsLineRecords)
		{
			final List<I_C_CommissionSettingsLine> settingsLines = result.computeIfAbsent(settingsLineRecord.getC_HierarchyCommissionSettings_ID(), ignored -> new ArrayList<I_C_CommissionSettingsLine>());
			settingsLines.add(settingsLineRecord);
		}

		return ImmutableMap.copyOf(result);
	}

	@Value
	@Builder
	public static class StagingData
	{
		@NonNull
		ImmutableMap<Integer, I_C_Flatrate_Term> id2TermRecord;

		@NonNull
		ImmutableMap<Integer, I_C_Flatrate_Conditions> id2ConditionsRecord;

		@NonNull
		ImmutableMap<Integer, I_C_HierarchyCommissionSettings> id2SettingsRecord;

		@NonNull
		ImmutableMap<Integer, I_C_CommissionSettingsLine> id2SettingsLineRecord;

		@NonNull
		ImmutableListMultimap<BPartnerId, FlatrateTermId> bPartnerId2FlatrateTermIds;

		@NonNull
		ImmutableListMultimap<Integer, BPartnerId> conditionRecordId2BPartnerIds;

		@NonNull
		ImmutableMultimap<Integer, Integer> settingsId2termIds;

		@NonNull
		ImmutableListMultimap<Integer, Integer> settingsId2settingsLineIds;
	}
}
