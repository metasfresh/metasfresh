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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CachingKeysMapper;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.tuple.Pair;
import org.compiere.model.IQuery;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ModularContractSettingsDAO
{
	private final static Logger logger = LogManager.getLogger(ModularContractSettingsDAO.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private final CCache<Integer, ModularContractTypeMap> contractTypesCache = CCache.<Integer, ModularContractTypeMap>builder()
			.tableName(I_ModCntr_Type.Table_Name)
			.build();

	private final CCache<SettingsLookupKey, CachedSettingsId> cacheKey2SettingsId = CCache.<SettingsLookupKey, CachedSettingsId>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)
			.tableName(I_C_Flatrate_Conditions.Table_Name)
			.invalidationKeysMapper(new SettingsIdCachingKeysMapper())
			.build();

	private final CCache<ModularContractSettingsId, ModularContractSettings> id2ModularContractSettings = CCache.<ModularContractSettingsId, ModularContractSettings>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)
			.tableName(I_ModCntr_Settings.Table_Name)
			.additionalTableNameToResetFor(I_ModCntr_Module.Table_Name)
			.invalidationKeysMapper(new SettingsInfoCachingKeysMapper())
			.build();

	private final CCache<ModularContractModuleId, ModuleConfig> id2ModuleConfig = CCache.<ModularContractModuleId, ModuleConfig>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)
			.tableName(I_ModCntr_Module.Table_Name)
			.build();

	@NotNull
	private static ModularContractSettingsId extractId(final I_ModCntr_Settings settings)
	{
		return ModularContractSettingsId.ofRepoId(settings.getModCntr_Settings_ID());
	}

	// visible for interceptors
	public ModuleConfig fromRecord(@NonNull final I_ModCntr_Module record)
	{
		return fromRecord(record, getContractTypes());
	}

	private static ModuleConfig fromRecord(@NonNull final I_ModCntr_Module record, @NonNull final ModularContractTypeMap contractTypes)
	{
		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID());

		return ModuleConfig.builder()
				.id(ModuleConfigAndSettingsId.ofRepoId(modularContractSettingsId, record.getModCntr_Module_ID()))
				.name(record.getName())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.invoicingGroup(InvoicingGroupType.ofCode(record.getInvoicingGroup()))
				.modularContractType(contractTypes.getById(ModularContractTypeId.ofRepoId(record.getModCntr_Type_ID())))
				.build();
	}

	public ModularContractType getContractTypeById(@NonNull final ModularContractTypeId id)
	{
		return getContractTypes().getById(id);
	}

	private ModularContractTypeMap getContractTypes()
	{
		return contractTypesCache.getOrLoad(0, this::retrieveContractTypes);
	}

	private ModularContractTypeMap retrieveContractTypes()
	{
		return queryBL.createQueryBuilder(I_ModCntr_Type.class)
				.stream()
				.map(ModularContractSettingsDAO::fromRecord)
				.collect(ModularContractTypeMap.collect());
	}

	private static ModularContractType fromRecord(@NonNull final I_ModCntr_Type record)
	{
		return ModularContractType.builder()
				.id(ModularContractTypeId.ofRepoId(record.getModCntr_Type_ID()))
				.value(record.getValue())
				.name(record.getName())
				.computingMethodType(ComputingMethodType.ofCode(record.getModularContractHandlerType()))
				.columnName(record.getColumnName())
				.build();
	}

	@NonNull
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
	private ModularContractSettings loadById(@NonNull final ModularContractSettingsId contractSettingsId)
	{
		final List<I_ModCntr_Module> moduleRecords = queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Module.COLUMN_ModCntr_Settings_ID, contractSettingsId)
				.create()
				.list();

		return fromRecord(InterfaceWrapperHelper.load(contractSettingsId, I_ModCntr_Settings.class), moduleRecords);
	}

	@NonNull
	private ImmutableMap<ModularContractSettingsId, ModularContractSettings> retrieveByIds(@NonNull final Set<ModularContractSettingsId> contractSettingsIds)
	{
		final ImmutableListMultimap<ModularContractSettingsId, I_ModCntr_Module> settingsId2ModuleRecords = queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_ModCntr_Module.COLUMN_ModCntr_Settings_ID, contractSettingsIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						module -> ModularContractSettingsId.ofRepoId(module.getModCntr_Settings_ID()),
						Function.identity()));

		return InterfaceWrapperHelper.loadByRepoIdAwares(contractSettingsIds, I_ModCntr_Settings.class)
				.stream()
				.map(settings -> fromRecord(settings, settingsId2ModuleRecords.get(extractId(settings))))
				.collect(ImmutableMap.toImmutableMap(ModularContractSettings::getId, Function.identity()));
	}

	@NonNull
	private ModularContractSettings fromRecord(
			@NonNull final I_ModCntr_Settings settingsRecord,
			@NonNull final List<I_ModCntr_Module> moduleRecords)
	{
		final ModularContractTypeMap contractTypes = getContractTypes();

		return ModularContractSettings.builder()
				.id(extractId(settingsRecord))
				.orgId(OrgId.ofRepoId(settingsRecord.getAD_Org_ID()))
				.yearAndCalendarId(YearAndCalendarId.ofRepoId(settingsRecord.getC_Year_ID(), settingsRecord.getC_Calendar_ID()))
				.pricingSystemId(PricingSystemId.ofRepoId(settingsRecord.getM_PricingSystem_ID()))
				.rawProductId(ProductId.ofRepoId(settingsRecord.getM_Raw_Product_ID()))
				.processedProductId(ProductId.ofRepoIdOrNull(settingsRecord.getM_Processed_Product_ID()))
				.coProductId(ProductId.ofRepoIdOrNull(settingsRecord.getM_Co_Product_ID()))
				.name(settingsRecord.getName())
				.soTrx(SOTrx.ofBooleanNotNull(settingsRecord.isSOTrx()))
				.additionalInterestDays(settingsRecord.getAddInterestDays())
				.interestPercent(Percent.of(settingsRecord.getInterestRate()))
				.storageCostStartDate(LocalDateAndOrgId.ofTimestamp(settingsRecord.getStorageCostStartDate(),
						OrgId.ofRepoId(settingsRecord.getAD_Org_ID()),
						orgDAO::getTimeZone))
				.moduleConfigs(moduleRecords.stream()
						.map(moduleRecord -> fromRecord(moduleRecord, contractTypes))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public boolean isSettingsUsedInCompletedFlatrateConditions(final @NonNull ModularContractSettingsId modCntrSettingsId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addEqualsFilter(I_ModCntr_Settings.COLUMN_ModCntr_Settings_ID, modCntrSettingsId)
				.andCollectChildren(I_C_Flatrate_Conditions.COLUMN_ModCntr_Settings_ID)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed)
				.anyMatch();
	}

	public boolean isSettingsExist(final @NonNull ModularContractSettingsQuery query)
	{
		final YearAndCalendarId yearAndCalendarId = query.getYearAndCalendarId();
		return queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Calendar_ID, yearAndCalendarId.calendarId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Year_ID, yearAndCalendarId.yearId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID, query.getRawProductId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_IsSOTrx, query.getSoTrx().toBoolean())
				.anyMatch();
	}

	public List<ModularContractSettings> getSettingsByQuery(final @NonNull ModularContractSettingsQuery query)
	{
		final YearAndCalendarId yearAndCalendarId = query.getYearAndCalendarId();
		final IQueryBuilder<I_ModCntr_Settings> queryBuilder = queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Calendar_ID, yearAndCalendarId.calendarId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_C_Year_ID, yearAndCalendarId.yearId())
				.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_IsSOTrx, query.getSoTrx().toBoolean());

		if(query.getRawProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID, query.getRawProductId());
		}

		if(query.getCoProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Co_Product_ID, query.getCoProductId());
		}

		if(query.getProcessedProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_ModCntr_Settings.COLUMNNAME_M_Processed_Product_ID, query.getProcessedProductId());
		}

		if(query.isCheckHasCompletedModularCondition())
		{
			final IQuery<I_C_Flatrate_Conditions> completedModularConditions = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
					.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, X_C_Flatrate_Conditions.TYPE_CONDITIONS_ModularContract)
					.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed)
					.create();
			queryBuilder.addInSubQueryFilter(I_ModCntr_Settings.COLUMNNAME_ModCntr_Settings_ID,I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID, completedModularConditions);
		}
		return queryBuilder.create().stream().map(setting -> getById(ModularContractSettingsId.ofRepoId(setting.getModCntr_Settings_ID()))).toList();
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

		return id2ModularContractSettings.getOrLoad(settingsId, this::loadById);
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

		return id2ModularContractSettings.getOrLoad(settingsId, this::loadById);
	}

	public ModularContractSettings getById(@NonNull final ModularContractSettingsId settingsId)
	{
		return id2ModularContractSettings.getOrLoad(settingsId, this::loadById);
	}

	@NonNull
	public ImmutableMap<FlatrateTermId, ModularContractSettings> getOrLoadBy(@NonNull final Set<FlatrateTermId> termIds)
	{
		final ImmutableList<SettingsLookupKey> lookupKeys = termIds.stream()
				.map(SettingsLookupKey::of)
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap<SettingsLookupKey, CachedSettingsId> lookupKey2CachedSettingsId = lookupKeys.stream()
				.filter(cacheKey2SettingsId::containsKey)
				.collect(ImmutableMap.toImmutableMap(Function.identity(), cacheKey2SettingsId::get));

		final ImmutableSet<FlatrateTermId> notCachedFlatrateTermIds = lookupKeys.stream()
				.filter(settingsLookupKey -> !lookupKey2CachedSettingsId.containsKey(settingsLookupKey))
				.map(SettingsLookupKey::getContractId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap.Builder<FlatrateTermId, ModularContractSettingsId> termId2SettingsIdBuilder = ImmutableMap.builder();
		termId2SettingsIdBuilder.putAll(loadCacheFor(notCachedFlatrateTermIds));
		lookupKey2CachedSettingsId
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue().getSettingsId() != null)
				.forEach(entry -> termId2SettingsIdBuilder.put(entry.getKey().getContractId(), entry.getValue().getSettingsId()));
		final ImmutableMap<FlatrateTermId, ModularContractSettingsId> termId2SettingsId = termId2SettingsIdBuilder.build();

		final ImmutableMap<ModularContractSettingsId, ModularContractSettings> id2Settings = id2ModularContractSettings
				.getAllOrLoad(termId2SettingsId.values(), this::retrieveByIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(ModularContractSettings::getId, Function.identity()));

		return termId2SettingsId.entrySet()
				.stream()
				.map(entry -> Pair.of(entry.getKey(), id2Settings.get(entry.getValue())))
				.collect(ImmutableMap.toImmutableMap(Pair::getKey, Pair::getValue));
	}

	@NonNull
	private ImmutableMap<FlatrateTermId, ModularContractSettingsId> loadCacheFor(@NonNull final Set<FlatrateTermId> termIds)
	{
		if (termIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final List<I_C_Flatrate_Term> flatrateTerms = flatrateDAO.getByIds(termIds).values().asList();

		final ImmutableSet<ConditionsId> conditionsIds = flatrateTerms.stream()
				.map(I_C_Flatrate_Term::getC_Flatrate_Conditions_ID)
				.map(ConditionsId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<ConditionsId, I_C_Flatrate_Conditions> id2Conditions = flatrateDAO.getTermConditionsByIds(conditionsIds);

		return flatrateTerms.stream()
				.map(term -> {
					final ConditionsId conditionsId = ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID());
					final FlatrateTermId termId = FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID());
					final ModularContractSettingsId settingsId = loadCacheFor(id2Conditions.get(conditionsId), termId);
					return Pair.of(termId, settingsId);
				})
				.filter(termId2SettingsPair -> termId2SettingsPair.getValue() != null)
				.collect(ImmutableMap.toImmutableMap(Pair::getKey, Pair::getValue));
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

		return loadCacheFor(conditions, termId);
	}

	@Nullable
	private ModularContractSettingsId loadCacheFor(@NonNull final I_C_Flatrate_Conditions conditions, @Nullable final FlatrateTermId termId)
	{
		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId
				.ofRepoIdOrNull(conditions.getModCntr_Settings_ID());

		cacheKey2SettingsId.put(SettingsLookupKey.of(ConditionsId.ofRepoId(conditions.getC_Flatrate_Conditions_ID())),
				CachedSettingsId.ofNullable(modularContractSettingsId));

		Optional.ofNullable(termId)
				.map(SettingsLookupKey::of)
				.ifPresent(termCacheKey -> cacheKey2SettingsId.put(termCacheKey, CachedSettingsId.ofNullable(modularContractSettingsId)));

		return modularContractSettingsId;
	}

	void createModule(@NonNull final ModuleConfigCreateRequest request)
	{
		final I_ModCntr_Module module = newInstance(I_ModCntr_Module.class);
		module.setModCntr_Settings_ID(request.getModularContractSettingsId().getRepoId());
		module.setM_Product_ID(request.getProductId().getRepoId());
		module.setInvoicingGroup(request.getInvoicingGroup().getCode());
		module.setModCntr_Type_ID(request.getModularContractType().getId().getRepoId());
		module.setSeqNo(request.getSeqNo().toInt());
		module.setName(request.getName());
		module.setProcessed(request.isProcessed());
		saveRecord(module);
	}

	@NonNull
	public ModuleConfig getByModuleId(@NonNull final ModularContractModuleId modularContractModuleId)
	{
		return id2ModuleConfig.getOrLoad(modularContractModuleId, this::loadByModuleId);
	}

	private  ModuleConfig loadByModuleId(@NonNull final ModularContractModuleId modularContractModuleId)
	{
		final I_ModCntr_Module record = load(ModularContractModuleId.toRepoId(modularContractModuleId), I_ModCntr_Module.class);
		return fromRecord(record, getContractTypes());
	}

	public void updateModule(@NonNull final ModularContractModuleId modularContractModuleId,
			@NonNull final ModularContractModuleUpdateRequest request)
	{
		final I_ModCntr_Module record = load(ModularContractModuleId.toRepoId(modularContractModuleId), I_ModCntr_Module.class);
		updateModule(record, request);
	}

	public void updateModule(@NonNull final I_ModCntr_Module existingModuleConfig,
			@NonNull final ModularContractModuleUpdateRequest request)
	{
		final ProductId productId = request.getProductId();
		if (productId != null)
		{
			existingModuleConfig.setM_Product_ID(productId.getRepoId());
		}

		final ModularContractTypeId modularContractTypeId = request.getModularContractTypeId();
		if(modularContractTypeId != null)
		{
			existingModuleConfig.setModCntr_Type_ID(modularContractTypeId.getRepoId());
		}

		final String moduleName = request.getModuleName();
		if(moduleName != null)
		{
			existingModuleConfig.setName(moduleName);
		}
		saveRecord(existingModuleConfig);
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

	@Value
	private static class SettingsLookupKey
	{
		@Nullable FlatrateTermId contractId;
		@Nullable ConditionsId conditionsId;

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
	}

	@Value
	private static class CachedSettingsId
	{
		@Nullable ModularContractSettingsId settingsId;

		private static CachedSettingsId ofNullable(@Nullable final ModularContractSettingsId settingsId)
		{
			return new CachedSettingsId(settingsId);
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

	@Nullable
	I_ModCntr_Module retrieveInformativeLogModuleRecordOrNull(@NonNull final ModularContractSettingsId modularContractSettingsId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addEqualsFilter(I_ModCntr_Module.COLUMNNAME_ModCntr_Settings_ID, modularContractSettingsId)
				.addEqualsFilter(I_ModCntr_Module.COLUMNNAME_ModCntr_Type_ID, ModularContract_Constants.CONTRACT_MODULE_TYPE_INFORMATIVE_LOGS_ID)
				.create()
				.firstOnly();
	}


	@Nullable
	public I_ModCntr_Module retrieveDefinitiveInvoiceModuleRecordOrNull(@NonNull final ModularContractSettingsId modularContractSettingsId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addEqualsFilter(I_ModCntr_Module.COLUMNNAME_ModCntr_Settings_ID, modularContractSettingsId)
				.addInArrayFilter(I_ModCntr_Module.COLUMNNAME_ModCntr_Type_ID,
								  ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceRawProduct,
								  ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceProcessedProduct)
				.create()
				.firstOnly();
	}

}