/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.settings;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CachingKeysMapper;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class ModularContractSettingsDAO
{
	private final static Logger logger = LogManager.getLogger(ModularContractSettingsDAO.class);

	private final CCache<SettingsLookupKey, CachedSettingsId> cacheKey2SettingsId = CCache.<SettingsLookupKey, CachedSettingsId>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)
			.tableName(I_C_Flatrate_Conditions.Table_Name)
			.additionalTableNamesToResetFor(ImmutableSet.of(I_C_Flatrate_Term.Table_Name))
			.invalidationKeysMapper(new SettingsIdCachingKeysMapper())
			.build();

	private final CCache<ModularContractSettingsId, ModularContractSettings> id2ModularContractSettings = CCache.<ModularContractSettingsId, ModularContractSettings>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)
			.tableName(I_ModCntr_Settings.Table_Name)
			.additionalTableNamesToResetFor(ImmutableSet.of(I_ModCntr_Module.Table_Name))
			.invalidationKeysMapper(new SettingsInfoCachingKeysMapper())
			.build();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ModularContractSettings getByFlatrateTermId(@NonNull final FlatrateTermId contractId)
	{
		final ModularContractSettings modularContractSettings = getByFlatrateTermIdOrNull(contractId);
		if (modularContractSettings == null)
		{
			throw new AdempiereException("No modular contract settings found for " + contractId);
		}
		return modularContractSettings;
	}

	@Nullable
	public ModularContractSettings getByFlatrateTermIdOrNull(@NonNull final FlatrateTermId contractId)
	{
		return getOrLoadBy(contractId);
	}

	@NonNull
	private ModularContractSettings getById(@NonNull final ModularContractSettingsId contractSettingsId)
	{
		final List<I_ModCntr_Module> moduleRecords = queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Module.COLUMN_ModCntr_Settings_ID, contractSettingsId)
				.create()
				.list();

		return fromPOs(InterfaceWrapperHelper.load(contractSettingsId, I_ModCntr_Settings.class), moduleRecords);
	}

	@NonNull
	private static ModularContractSettings fromPOs(
			@NonNull final I_ModCntr_Settings settingsRecord,
			@NonNull final List<I_ModCntr_Module> moduleRecords)
	{

		final ModularContractSettings.ModularContractSettingsBuilder result = ModularContractSettings.builder()
				.id(ModularContractSettingsId.ofRepoId(settingsRecord.getModCntr_Settings_ID()))
				.orgId(OrgId.ofRepoId(settingsRecord.getAD_Org_ID()))
				.yearAndCalendarId(YearAndCalendarId.ofRepoId(settingsRecord.getC_Year_ID(), settingsRecord.getC_Calendar_ID()))
				.pricingSystemId(PricingSystemId.ofRepoIdOrNull(settingsRecord.getM_PricingSystem_ID()))
				.productId(ProductId.ofRepoId(settingsRecord.getM_Product_ID()))
				.name(settingsRecord.getName());

		for (final I_ModCntr_Module moduleRecord : moduleRecords)
		{
			final I_ModCntr_Type modCntrType = moduleRecord.getModCntr_Type();

			final ModuleConfig moduleConfig = ModuleConfig.builder()
					.name(moduleRecord.getName())
					.productId(ProductId.ofRepoId(moduleRecord.getM_Product_ID()))
					.seqNo(SeqNo.ofInt(moduleRecord.getSeqNo()))
					.invoicingGroup(moduleRecord.getInvoicingGroup())
					.modularContractType(ModularContractType.builder()
							.id(ModularContractTypeId.ofRepoId(modCntrType.getModCntr_Type_ID()))
							.value(modCntrType.getValue())
							.name(modCntrType.getName())
							.className(modCntrType.getClassname())
							.build())
					.build();

			result.moduleConfig(moduleConfig);
		}

		return result.build();
	}

	public boolean isSettingsUsedInCompletedFlatrateConditions(final ModularContractSettingsId modCntrSettingsId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addEqualsFilter(I_ModCntr_Settings.COLUMN_ModCntr_Settings_ID, modCntrSettingsId)
				.andCollectChildren(I_C_Flatrate_Conditions.COLUMN_ModCntr_Settings_ID)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed)
				.anyMatch();
	}

	public boolean isSettingsExist(final @NonNull ModularContractSettingsQuery query)
	{
		final YearAndCalendarId yearAndCalendarId = query.yearAndCalendarId();
		return queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Calendar_ID, yearAndCalendarId.calendarId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Year_ID, yearAndCalendarId.yearId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Product_ID, query.productId())
				.anyMatch();
	}

	@Nullable
	private ModularContractSettings getOrLoadBy(@NonNull final FlatrateTermId termId)
	{
		final SettingsLookupKey key = SettingsLookupKey.of(termId);

		final CachedSettingsId cachedSettingsId = cacheKey2SettingsId.get(key);
		final ModularContractSettingsId settingsId = cachedSettingsId == null
				? loadCacheFor(termId)
				: cachedSettingsId.getSettingsId();

		if (settingsId == null)
		{
			return null;
		}

		return id2ModularContractSettings.getOrLoad(settingsId, this::getById);
	}

	@NonNull
	public ModularContractSettings getByFlatrateConditionsId(@NonNull final ConditionsId conditionsId)
	{
		final ModularContractSettings settings = getByFlatrateConditionsIdOrNull(conditionsId);
		if (settings == null)
		{
			throw new AdempiereException("No modular contract settings found for " + conditionsId);
		}
		return settings;
	}

	@Nullable
	public ModularContractSettings getByFlatrateConditionsIdOrNull(@NonNull final ConditionsId conditionsId)
	{
		final SettingsLookupKey key = SettingsLookupKey.of(conditionsId);

		final CachedSettingsId cachedSettingsId = cacheKey2SettingsId.get(key);
		final ModularContractSettingsId settingsId = cachedSettingsId == null
				? loadCacheFor(conditionsId, null)
				: cachedSettingsId.getSettingsId();

		if (settingsId == null)
		{
			return null;
		}

		return id2ModularContractSettings.getOrLoad(settingsId, this::getById);
	}

	@Nullable
	private ModularContractSettingsId loadCacheFor(@NonNull final FlatrateTermId termId)
	{
		final ConditionsId conditionsId = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, termId)
				.andCollect(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID)
				.create()
				.firstIdOnlyOptional(ConditionsId::ofRepoId)
				.orElseThrow(() -> new AdempiereException("No C_Flatrate_Conditions_ID set on C_Flatrate_Term, even though the column is marked as mandatory!")
						.appendParametersToMessage()
						.setParameter("termId", termId));

		return loadCacheFor(conditionsId, termId);
	}

	@Nullable
	private ModularContractSettingsId loadCacheFor(@NonNull final ConditionsId conditionsId, @Nullable final FlatrateTermId termId)
	{
		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.load(conditionsId, I_C_Flatrate_Conditions.class);

		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId
				.ofRepoIdOrNull(conditions.getModCntr_Settings_ID());

		cacheKey2SettingsId.put(SettingsLookupKey.of(conditionsId), CachedSettingsId.ofNullable(modularContractSettingsId));
		Optional.ofNullable(termId)
				.map(SettingsLookupKey::of)
				.ifPresent(termCacheKey -> cacheKey2SettingsId.put(termCacheKey, CachedSettingsId.ofNullable(modularContractSettingsId)));

		return modularContractSettingsId;
	}

	private static class SettingsInfoCachingKeysMapper implements CachingKeysMapper<ModularContractSettingsId>
	{
		@Override
		public Collection<ModularContractSettingsId> computeCachingKeys(final TableRecordReference recordRef)
		{
			if (I_ModCntr_Settings.Table_Name.equals(recordRef.getTableName()))
			{
				return ImmutableSet.of(ModularContractSettingsId.ofRepoId(recordRef.getRecord_ID()));
			}
			else if (I_ModCntr_Module.Table_Name.equals(recordRef.getTableName()))
			{
				final I_ModCntr_Module module = recordRef.getModel(I_ModCntr_Module.class);
				if (module == null)
				{
					throw new AdempiereException("When a ModCntr_Module is deleted, isResetAll() should take care of resetting the cache!")
							.appendParametersToMessage()
							.setParameter("recordRef", recordRef);
				}

				return ImmutableSet.of(ModularContractSettingsId.ofRepoId(module.getModCntr_Settings_ID()));
			}

			throw new AdempiereException("Unexpected table name=" + recordRef.getTableName());
		}

		@Override
		public boolean isResetAll(@NonNull final TableRecordReference recordRef)
		{
			if (I_ModCntr_Settings.Table_Name.equals(recordRef.getTableName()))
			{
				return false;
			}

			//reset all cache if the object was deleted, and we can't get to a ModularContractSettingsId
			final Object recordRefModel = recordRef.getModel(Object.class);
			return recordRefModel == null;
		}
	}

	private class SettingsIdCachingKeysMapper implements CachingKeysMapper<SettingsLookupKey>
	{
		@Override
		public Collection<SettingsLookupKey> computeCachingKeys(@NonNull final TableRecordReference recordRef)
		{
			if (I_C_Flatrate_Conditions.Table_Name.equals(recordRef.getTableName()))
			{
				logger.debug("ComputeCachingKeys called for a ({},{}) that wasn't cached so far!",
						recordRef.getRecord_ID(),
						recordRef.getTableName());

				return ImmutableSet.of();
			}
			else if (I_C_Flatrate_Term.Table_Name.equals(recordRef.getTableName()))
			{
				final FlatrateTermId contractId = recordRef.getIdAssumingTableName(I_C_Flatrate_Term.Table_Name, FlatrateTermId::ofRepoId);

				logger.debug("ComputeCachingKeys called for ({},{})!",
						recordRef.getRecord_ID(),
						recordRef.getTableName());

				return ImmutableSet.of(SettingsLookupKey.of(contractId));
			}

			throw new AdempiereException("Unexpected table name=" + recordRef.getTableName());
		}

		@Override
		public boolean isResetAll(@NonNull final TableRecordReference recordRef)
		{
			if (I_C_Flatrate_Term.Table_Name.equals(recordRef.getTableName()))
			{
				return false;
			}
			else if (I_C_Flatrate_Conditions.Table_Name.equals(recordRef.getTableName())
					&& resetAllForFlatrateConditions(recordRef.getIdAssumingTableName(I_C_Flatrate_Conditions.Table_Name, ConditionsId::ofRepoId)))
			{
				return true;
			}

			//reset all the cache if the object was deleted, and we can't get to a SettingsLookupKey
			final Object recordRefModel = recordRef.getModel(Object.class);
			return recordRefModel == null;
		}

		private boolean resetAllForFlatrateConditions(@NonNull final ConditionsId conditionsId)
		{
			return cacheKey2SettingsId.containsKey(SettingsLookupKey.of(conditionsId));
		}
	}

	@Value
	private static class SettingsLookupKey
	{
		@Nullable
		FlatrateTermId contractId;
		@Nullable
		ConditionsId conditionsId;

		@NonNull
		public static SettingsLookupKey of(@NonNull final ConditionsId conditionsId)
		{
			return new SettingsLookupKey(null, conditionsId);
		}

		@NonNull
		public static SettingsLookupKey of(@NonNull final FlatrateTermId contractId)
		{
			return new SettingsLookupKey(contractId, null);
		}

		private SettingsLookupKey(
				@Nullable final FlatrateTermId contractId,
				@Nullable final ConditionsId conditionsId)
		{
			if (conditionsId == null && contractId == null)
			{
				throw new AdempiereException("conditionsId && contractId cannot be both null!");
			}

			if (conditionsId != null && contractId != null)
			{
				throw new AdempiereException("conditionsId && contractId cannot be both set!");
			}

			this.contractId = contractId;
			this.conditionsId = conditionsId;
		}
	}

	@Value
	private static class CachedSettingsId
	{
		private static CachedSettingsId ofNullable(@Nullable final ModularContractSettingsId settingsId)
		{
			return new CachedSettingsId(settingsId);
		}

		@Nullable
		ModularContractSettingsId settingsId;
	}
}
