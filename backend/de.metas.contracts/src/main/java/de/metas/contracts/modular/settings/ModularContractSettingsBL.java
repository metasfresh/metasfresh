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

import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModularContractSettingsBL
{
	public static final String AD_ELEMENT_DEFINITIVE_INVOICE = "DefinitiveInvoice";
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	@NonNull private ModularContractSettingsDAO modularContractSettingsDAO;

	private static final AdMessageKey ERROR_MSG_NO_FLATRATE_TERM_CONDITIONS = AdMessageKey.of("de.metas.contracts.modular.settings.missingFlatrateTermCondition");
	public static final AdMessageKey MSG_ERROR_MODULARCONTRACTSETTINGS_ALREADY_USED = AdMessageKey.of("MSG_ModularContractSettings_AlreadyUsed");

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

	public boolean isSettingsUsedInCompletedFlatrateConditions(final @NonNull ModularContractSettingsId modCntrSettingsId)
	{
		return modularContractSettingsDAO.isSettingsUsedInCompletedFlatrateConditions(modCntrSettingsId);
	}

	public ModularContractSettings getById(@NonNull final ModularContractSettingsId settingsId)
	{
		return modularContractSettingsDAO.getById(settingsId);
	}

	@NonNull
	public ModularContractSettings getByFlatrateTermId(@NonNull final FlatrateTermId contractId)
	{
		return modularContractSettingsDAO.getByFlatrateTermId(contractId);
	}

	@NonNull
	public ModularContractType getModuleContractType(@NonNull final ModularContractModuleId modularContractModuleId)
	{
		final ModuleConfig moduleConfig = modularContractSettingsDAO.getByModuleId(modularContractModuleId);
		return moduleConfig.getModularContractType();
	}

	public boolean hasComputingMethodType(@NonNull final ModularContractModuleId modularContractModuleId, @NonNull final ComputingMethodType computingMethodType)
	{
		return getModuleContractType(modularContractModuleId).isMatching(computingMethodType);
	}

	private void createInformativeLogsModule(@NonNull final ModularContractSettingsId modularContractSettingsId)
	{
		modularContractSettingsDAO.createModule(
				ModuleConfigCreateRequest.builder()
						.modularContractSettingsId(modularContractSettingsId)
						.seqNo(SeqNo.ofInt(0))
						.name("Informative Logs") // NOTE en/de trl is the same
						.modularContractType(modularContractSettingsDAO.getContractTypeById(ModularContract_Constants.CONTRACT_MODULE_TYPE_INFORMATIVE_LOGS_ID))
						.invoicingGroup(InvoicingGroupType.SERVICES)
						.productId(getById(modularContractSettingsId).getRawProductId())
						.processed(true)
						.build()
		);
	}

	private void createDefinitiveInvoiceModule(@NonNull final ModularContractSettingsId modularContractSettingsId)
	{
		final ModularContractSettings modularContractSettings = getById(modularContractSettingsId);
		final ProductId moduleProductId;
		final ModularContractType moduleContractType;
		final ProductId processedProductId = modularContractSettings.getProcessedProductId();
		if(processedProductId != null)
		{
			moduleProductId = processedProductId;
			moduleContractType = modularContractSettingsDAO.getContractTypeById(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceProcessedProduct);
		}
		else
		{
			moduleProductId = modularContractSettings.getRawProductId();
			moduleContractType = modularContractSettingsDAO.getContractTypeById(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceRawProduct);
		}

		final String moduleName = TranslatableStrings.adElementOrMessage(AD_ELEMENT_DEFINITIVE_INVOICE).translate(Env.getADLanguageOrBaseLanguage());
		modularContractSettingsDAO.createModule(
				ModuleConfigCreateRequest.builder()
						.modularContractSettingsId(modularContractSettingsId)
						.seqNo(SeqNo.ofInt(0))
						.name(moduleName)
						.modularContractType(moduleContractType)
						.invoicingGroup(InvoicingGroupType.SERVICES)
						.productId(moduleProductId)
						.processed(true)
						.build()
		);
	}
	public void upsertInformativeLogsModule(@NonNull final ModularContractSettingsId modularContractSettingsId, @NonNull final ProductId rawProductId)
	{
		final I_ModCntr_Module existingModuleConfig = modularContractSettingsDAO.retrieveInformativeLogModuleRecordOrNull(modularContractSettingsId);
		if (existingModuleConfig == null)
		{
			createInformativeLogsModule(modularContractSettingsId);
		}
		else if (!ProductId.ofRepoId(existingModuleConfig.getM_Product_ID()).equals(rawProductId))
		{
			modularContractSettingsDAO.updateModule(existingModuleConfig, ModularContractModuleUpdateRequest.builder()
					.productId(rawProductId)
					.build());
		}
	}

	public void upsertDefinitiveInvoiceModule(@NonNull final ModularContractSettingsId modularContractSettingsId,
			@NonNull final ProductId rawProductId,
			@Nullable final ProductId processedProductId)
	{
		final I_ModCntr_Module existingModuleConfig = modularContractSettingsDAO.retrieveDefinitiveInvoiceModuleRecordOrNull(modularContractSettingsId);
		if (existingModuleConfig == null)
		{
			createDefinitiveInvoiceModule(modularContractSettingsId);
		}
		else if (processedProductId != null)
		{
			modularContractSettingsDAO.updateModule(existingModuleConfig, ModularContractModuleUpdateRequest.builder()
					.modularContractTypeId(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceProcessedProduct)
					.productId(processedProductId)
					.build());
		}
		else
		{
			modularContractSettingsDAO.updateModule(existingModuleConfig, ModularContractModuleUpdateRequest.builder()
					.modularContractTypeId(ModularContract_Constants.CONTRACT_MODULE_TYPE_DefinitiveInvoiceRawProduct)
					.productId(rawProductId)
					.build());
		}
	}

	public void updateModule(@NonNull final ModuleConfig moduleConfig, @Nullable final ModularContractTypeId modularContractTypeId, @NonNull final ProductId productId)
	{
		modularContractSettingsDAO.updateModule(moduleConfig.getId().getModularContractModuleId(), ModularContractModuleUpdateRequest.builder()
				.modularContractTypeId(modularContractTypeId)
				.productId(productId)
				.build());
	}

	public List<ModularContractSettings> getSettingsByQuery(final @NonNull ModularContractSettingsQuery query)
	{
		return modularContractSettingsDAO.getSettingsByQuery(query);
	}
}
