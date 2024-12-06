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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.ModularContractSettingsId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

import static de.metas.contracts.modular.ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceStorageCost_ID;
import static de.metas.contracts.modular.ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceUserElementNumber1_ID;
import static de.metas.contracts.modular.ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceUserElementNumber2_ID;
import static de.metas.contracts.modular.ModularContract_Constants.CONTRACT_MODULE_TYPE_StorageCost_ID;
import static de.metas.contracts.modular.ModularContract_Constants.CONTRACT_MODULE_TYPE_UserElementNumber1_ID;
import static de.metas.contracts.modular.ModularContract_Constants.CONTRACT_MODULE_TYPE_UserElementNumber2_ID;

@Service
@RequiredArgsConstructor
public class ModularContractSettingsService
{
	@NonNull private static final AdMessageKey ERROR_MSG_NO_FLATRATE_TERM_CONDITIONS = AdMessageKey.of("de.metas.contracts.modular.settings.missingFlatrateTermCondition");
	@NonNull private static final AdMessageKey MSG_ERROR_MODULARCONTRACTSETTINGS_ALREADY_USED = AdMessageKey.of("MSG_ModularContractSettings_AlreadyUsed");
	@NonNull private static final String AD_ELEMENT_DEFINITIVE_INVOICE = "DefinitiveInvoice";
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final ModularContractSettingsRepository modularContractSettingsRepository;

	public static ModularContractSettingsService newInstanceForJUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ModularContractSettingsService(new ModularContractSettingsRepository());
	}

	public ConditionsId retrieveFlatrateConditionId(
			@NonNull final ModularContractSettings modularContractSettings,
			@NonNull final TypeConditions typeConditions)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(X_C_Flatrate_Conditions.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.addEqualsFilter(X_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID, modularContractSettings.getId().getRepoId())
				.addEqualsFilter(X_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, typeConditions.getCode())
				.create()
				.firstIdOnly());

		if (conditionsId == null)
		{
			throw new AdempiereException(ERROR_MSG_NO_FLATRATE_TERM_CONDITIONS, modularContractSettings);
		}

		return conditionsId;
	}

	public void validateModularContractSettingsNotUsed(@NonNull final ModularContractSettingsId modularContractSettingsId)
	{
		final Optional<I_C_Flatrate_Conditions> completedConditions = flatrateBL.streamCompletedConditionsBy(modularContractSettingsId)
				.findFirst();

		if (completedConditions.isPresent())
		{
			throw new AdempiereException(MSG_ERROR_MODULARCONTRACTSETTINGS_ALREADY_USED)
					.appendParametersToMessage()
					.setParameter("C_Flatrate_Conditions_ID", completedConditions.get().getC_Flatrate_Conditions_ID())
					.setParameter("ModCntr_Settings_ID", modularContractSettingsId.getRepoId());
		}
	}

	public ModularContractSettings getById(@NonNull final ModularContractSettingsId settingsId)
	{
		return modularContractSettingsRepository.getById(settingsId);
	}

	@NonNull
	public ModularContractSettings getByFlatrateTermId(@NonNull final FlatrateTermId contractId)
	{
		return modularContractSettingsRepository.getByFlatrateTermId(contractId);
	}

	@NonNull
	public ModularContractType getModuleContractType(@NonNull final ModularContractModuleId modularContractModuleId)
	{
		final ModuleConfig moduleConfig = modularContractSettingsRepository.getByModuleId(modularContractModuleId);
		return moduleConfig.getModularContractType();
	}

	public boolean hasComputingMethodType(@NonNull final ModularContractModuleId modularContractModuleId, @NonNull final ComputingMethodType computingMethodType)
	{
		return getModuleContractType(modularContractModuleId).isMatching(computingMethodType);
	}

	public boolean isMatchingAnyComputingMethodType(@NonNull final ModularContractTypeId modularContractTypeId, @NonNull final ImmutableSet<ComputingMethodType> computingMethodTypes)
	{
		final ModularContractType contractType = modularContractSettingsRepository.getContractTypeById(modularContractTypeId);
		return computingMethodTypes
				.stream()
				.anyMatch(contractType::isMatching);
	}

	private void createInformativeLogsModule(@NonNull final ModularContractSettingsId modularContractSettingsId, @NonNull final ModularContractTypeId modularContractTypeId)
	{
		modularContractSettingsRepository.createModule(
				ModuleConfigCreateRequest.builder()
						.modularContractSettingsId(modularContractSettingsId)
						.seqNo(SeqNo.ofInt(0))
						.name("Informative Logs") // NOTE en/de trl is the same
						.modularContractType(modularContractSettingsRepository.getContractTypeById(modularContractTypeId))
						.invoicingGroup(InvoicingGroupType.SERVICES)
						.productId(getById(modularContractSettingsId).getRawProductId())
						.generated(true)
						.build()
		);
	}

	private void createDefinitiveInvoiceModule(@NonNull final ModularContractSettingsId modularContractSettingsId)
	{
		final ModularContractSettings modularContractSettings = getById(modularContractSettingsId);
		final ProductId moduleProductId;
		final ModularContractType moduleContractType;
		final ProductId processedProductId = modularContractSettings.getProcessedProductId();
		if (processedProductId != null)
		{
			moduleProductId = processedProductId;
			moduleContractType = modularContractSettingsRepository.getContractTypeById(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceProcessedProduct_ID);
		}
		else
		{
			moduleProductId = modularContractSettings.getRawProductId();
			moduleContractType = modularContractSettingsRepository.getContractTypeById(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceRawProduct_ID);
		}

		final String moduleName = TranslatableStrings.adElementOrMessage(AD_ELEMENT_DEFINITIVE_INVOICE).translate(Env.getADLanguageOrBaseLanguage());
		modularContractSettingsRepository.createModule(
				ModuleConfigCreateRequest.builder()
						.modularContractSettingsId(modularContractSettingsId)
						.seqNo(SeqNo.ofInt(0))
						.name(moduleName)
						.modularContractType(moduleContractType)
						.invoicingGroup(InvoicingGroupType.SERVICES)
						.productId(moduleProductId)
						.generated(true)
						.build()
		);
	}
	public void upsertInformativeLogsModule(@NonNull final ModularContractSettingsId modularContractSettingsId, @NonNull final ProductId rawProductId, @NonNull final SOTrx soTrx)
	{
		final ModularContractTypeId modularContractTypeIdToUse = soTrx.isPurchase() ? ModularContract_Constants.CONTRACT_MODULE_TYPE_INFORMATIVE_LOGS_ID : ModularContract_Constants.CONTRACT_MODULE_TYPE_SALES_INFORMATIVE_LOGS_ID;
		final I_ModCntr_Module existingModuleConfig = modularContractSettingsRepository.retrieveModuleRecordOrNull(
				ModularContractModuleQuery.builder()
						.modularContractSettingsId(modularContractSettingsId)
						.modularContractTypeId(modularContractTypeIdToUse)
						.build());

		if (existingModuleConfig == null)
		{
			createInformativeLogsModule(modularContractSettingsId, modularContractTypeIdToUse);
		}
		else if (!ProductId.ofRepoId(existingModuleConfig.getM_Product_ID()).equals(rawProductId))
		{
			modularContractSettingsRepository.updateModule(existingModuleConfig, ModularContractModuleUpdateRequest.builder()
					.productId(rawProductId)
					.build());
		}
	}

	public void upsertDefinitiveInvoiceSalesModule(@NonNull final ModularContractSettingsId modularContractSettingsId,
			@NonNull final ProductId rawProductId,
			@Nullable final ProductId processedProductId)
	{
		final I_ModCntr_Module existingModuleConfig = modularContractSettingsRepository.retrieveModuleRecordOrNull(
				ModularContractModuleQuery.builder()
						.modularContractSettingsId(modularContractSettingsId)
						.modularContractTypeIds(ImmutableSet.of(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceRawProduct_ID,
																ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceProcessedProduct_ID))
						.build());
		if (existingModuleConfig == null)
		{
			createDefinitiveInvoiceModule(modularContractSettingsId);
		}
		else if (processedProductId != null)
		{
			modularContractSettingsRepository.updateModule(existingModuleConfig, ModularContractModuleUpdateRequest.builder()
					.modularContractTypeId(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceProcessedProduct_ID)
					.productId(processedProductId)
					.moduleName(productBL.getProductName(processedProductId))
					.build());
		}
		else
		{
			modularContractSettingsRepository.updateModule(existingModuleConfig, ModularContractModuleUpdateRequest.builder()
					.modularContractTypeId(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceRawProduct_ID)
					.productId(rawProductId)
					.moduleName(productBL.getProductName(rawProductId))
					.build());
		}
	}

	public void updateModule(@NonNull final ModuleConfig moduleConfig, @Nullable final ModularContractTypeId modularContractTypeId, @NonNull final ProductId productId)
	{
		modularContractSettingsRepository.updateModule(moduleConfig.getModularContractModuleId(), ModularContractModuleUpdateRequest.builder()
				.modularContractTypeId(modularContractTypeId)
				.productId(productId)
				.build());
	}

	public void upsertDefinitiveModule(@NonNull final ModuleConfig moduleConfig)
	{
		final ModularContractTypeId moduleTypeIdToUse = getRelatedDefinitiveTypeId(moduleConfig.getModularContractTypeId());
		if(moduleTypeIdToUse != null)
		{
			final ModularContractModuleQuery query = ModularContractModuleQuery.builder()
					.modularContractSettingsId(moduleConfig.getModularContractSettingsId())
					.modularContractTypeId(moduleTypeIdToUse)
					.build();

			final ModularContractModuleUpdateRequest updateRequest = ModularContractModuleUpdateRequest.builder()
					.modularContractSettingsId(moduleConfig.getModularContractSettingsId())
					.seqno(moduleConfig.getSeqNo())
					.productId(moduleConfig.getProductId())
					.moduleName(moduleConfig.getName())
					.modularContractTypeId(moduleTypeIdToUse)
					.invoicingGroupType(moduleConfig.getInvoicingGroup())
					.generated(true)
					.build();

			modularContractSettingsRepository.upsertModule(query, updateRequest);
		}
	}

	@Nullable
	public ModularContractTypeId getRelatedDefinitiveTypeId(final @NotNull ModularContractTypeId moduleTypeId)
	{
		final ModularContractTypeId moduleTypeIdToUse;

		if(ModularContractTypeId.equals(moduleTypeId, CONTRACT_MODULE_TYPE_UserElementNumber1_ID))
		{
			moduleTypeIdToUse = CONTRACT_MODULE_TYPE_DefinitiveInvoiceUserElementNumber1_ID;
		}
		else if(ModularContractTypeId.equals(moduleTypeId, CONTRACT_MODULE_TYPE_UserElementNumber2_ID))
		{
			moduleTypeIdToUse = CONTRACT_MODULE_TYPE_DefinitiveInvoiceUserElementNumber2_ID;
		}
		else if(ModularContractTypeId.equals(moduleTypeId, CONTRACT_MODULE_TYPE_StorageCost_ID))
		{
			moduleTypeIdToUse = CONTRACT_MODULE_TYPE_DefinitiveInvoiceStorageCost_ID;
		}
		else
		{
			moduleTypeIdToUse = null;
		}
		return moduleTypeIdToUse;
	}

	@Nullable
	public ModularContractSettings getByFlatrateTermIdOrNull(@NonNull final FlatrateTermId contractId)
	{
		return modularContractSettingsRepository.getByFlatrateTermIdOrNull(contractId);
	}

	@NonNull
	public ImmutableMap<FlatrateTermId, ModularContractSettings> getOrLoadBy(@NonNull final Set<FlatrateTermId> termIds)
	{
		return modularContractSettingsRepository.getOrLoadBy(termIds);
	}

	@NonNull
	public ModuleConfig getByModuleId(@NonNull final ModularContractModuleId modularContractModuleId)
	{
		return modularContractSettingsRepository.getByModuleId(modularContractModuleId);
	}
}
