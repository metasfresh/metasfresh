/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.settings.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.standard.ICalendarBL;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.impl.ProductBOM;
import org.eevolution.api.impl.ProductBOMRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Component
@Interceptor(I_ModCntr_Settings.class)
@RequiredArgsConstructor
public class ModCntr_Settings
{
	@NonNull private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	@NonNull private final ICalendarBL calendarBL = Services.get(ICalendarBL.class);
	@NonNull private final ModularContractSettingsBL modularContractSettingsBL;

	private static final AdMessageKey ERROR_CO_PRODUCT_SET_WITHOUT_PROCESSED_PRODUCT_SET = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.CoProductSetWithoutProcessedProductSet");
	private static final AdMessageKey ERROR_NO_BOM_FOUND_FOR_PROCESSED_PRODUCT = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.NoBOMFoundForProcessedProduct");
	private static final AdMessageKey ERROR_FOUND_BOM_DOESNT_HAVE_ONLY_RAW_COMPONENT = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyRawComponent");
	private static final AdMessageKey ERROR_FOUND_BOM_DOESNT_HAVE_ONLY_CO_PRODUCT = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.FoundBOMDoesntHaveOnlyCoProduct");
	private static final AdMessageKey ERROR_FOUND_BOM_CO_PRODUCT_DOESNT_MATCH_SETTINGS = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.FoundBOMCoProductDoesntMatch");
	private static final AdMessageKey ERROR_SETTING_LINES_DEPEND_ON_PRODUCT = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.SettingLineDependOnProduct");


	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void validateSettings(@NonNull final I_ModCntr_Settings record)
	{
		modularContractSettingsBL.validateModularContractSettingsNotUsed(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID },
			ifUIAction = true)
	public void upsertInformativeLogsModule(@NonNull final I_ModCntr_Settings record)
	{
		final ProductId rawProductId = ProductId.ofRepoIdOrNull(record.getM_Raw_Product_ID());

		if (rawProductId == null)
		{
			// nothing to do
			return;
		}

		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID());

		modularContractSettingsBL.upsertInformativeLogsModule(modularContractSettingsId, rawProductId);

	}


	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID, I_ModCntr_Settings.COLUMNNAME_M_Processed_Product_ID },
			ifUIAction = true)
	public void upsertDefinitiveInvoiceModule(@NonNull final I_ModCntr_Settings record)
	{
		final ProductId rawProductId = ProductId.ofRepoIdOrNull(record.getM_Raw_Product_ID());

		if (rawProductId == null)
		{
			// nothing to do
			return;
		}
		final ProductId processedProductId = ProductId.ofRepoIdOrNull(record.getM_Processed_Product_ID());

		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID());

		modularContractSettingsBL.upsertDefinitiveInvoiceModule(modularContractSettingsId, rawProductId, processedProductId);

	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID })
	public void updateModulesRawProductsIfNeeded(@NonNull final I_ModCntr_Settings record)
	{
		final ImmutableList<ComputingMethodType> rawComputingMethods = ImmutableList.of(ComputingMethodType.Receipt, ComputingMethodType.SalesOnRawProduct);
		updateModulesProducts(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()), rawComputingMethods, ProductId.ofRepoId(record.getM_Raw_Product_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Co_Product_ID })
	public void updateModulesCoProductsIfNeeded(@NonNull final I_ModCntr_Settings record)
	{
		final ImmutableList<ComputingMethodType> coComputingMethods = ImmutableList.of(ComputingMethodType.CoProduct);
		updateModulesProducts(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()), coComputingMethods, ProductId.ofRepoIdOrNull(record.getM_Co_Product_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Processed_Product_ID })
	public void updateModulesProcessedProductsIfNeeded(@NonNull final I_ModCntr_Settings record)
	{
		final ImmutableList<ComputingMethodType> processedComputingMethods = ImmutableList.of(ComputingMethodType.SalesOnProcessedProduct);
		updateModulesProducts(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()), processedComputingMethods, ProductId.ofRepoIdOrNull(record.getM_Processed_Product_ID()));
	}

	private void updateModulesProducts(
			@NonNull final ModularContractSettingsId modularContractSettingsId,
			@NonNull final ImmutableList<ComputingMethodType> list,
			@Nullable final ProductId productId)
	{
		final List<ModuleConfig> moduleConfigs = modularContractSettingsBL.getById(modularContractSettingsId)
				.getModuleConfigs(list);

		if(productId == null && !moduleConfigs.isEmpty())
		{
			throw new AdempiereException(ERROR_SETTING_LINES_DEPEND_ON_PRODUCT);
		}

		moduleConfigs.forEach((moduleConfig) -> modularContractSettingsBL.updateModule(moduleConfig, null, productId));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID, I_ModCntr_Settings.COLUMNNAME_M_Co_Product_ID, I_ModCntr_Settings.COLUMNNAME_M_Processed_Product_ID })
	public void validateProducts(@NonNull final I_ModCntr_Settings record)
	{
		final ProductId rawProductId = ProductId.ofRepoId(record.getM_Raw_Product_ID());
		final ProductId coProductId = ProductId.ofRepoIdOrNull(record.getM_Co_Product_ID());
		final ProductId processedProductId = ProductId.ofRepoIdOrNull(record.getM_Processed_Product_ID());
		if(coProductId == null && processedProductId == null)
		{
			return;
		}
		else if (coProductId != null && processedProductId == null)
		{
			throw new AdempiereException(ERROR_CO_PRODUCT_SET_WITHOUT_PROCESSED_PRODUCT_SET);
		}


		final YearId harvestingYearId = YearId.ofRepoId(record.getC_Year_ID());
		final Optional<ProductBOM> bomOptional = productBOMBL.retrieveValidProductBOM(ProductBOMRequest.builder()
													 .date(calendarBL.getFirstDayOfYear(harvestingYearId).toInstant())
													 .productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(processedProductId))
													 .build());

		if(bomOptional.isEmpty())
		{
			throw new AdempiereException(ERROR_NO_BOM_FOUND_FOR_PROCESSED_PRODUCT);
		}

		final ProductBOM bom = bomOptional.get();
		Check.assume(ProductId.equals(processedProductId, bom.getProductId()), "BOM should be for processed product");
		if(bom.getComponents().size() != 1)
		{
			throw new AdempiereException(ERROR_FOUND_BOM_DOESNT_HAVE_ONLY_RAW_COMPONENT);
		}
		else if (!ProductId.equals(ProductId.ofRepoId(bom.getComponents().get(0).getM_Product_ID()), rawProductId))
		{
			throw new AdempiereException(ERROR_FOUND_BOM_DOESNT_HAVE_ONLY_RAW_COMPONENT);
		}

		if(coProductId == null && bom.getCoProducts().isEmpty())
		{
			return;
		}
		else if (coProductId != null && bom.getCoProducts().isEmpty())
		{
			throw new AdempiereException(ERROR_FOUND_BOM_DOESNT_HAVE_ONLY_CO_PRODUCT);
		}
		else if (bom.getCoProducts().size() != 1)
		{
			throw new AdempiereException(ERROR_FOUND_BOM_DOESNT_HAVE_ONLY_CO_PRODUCT);
		}
		final ProductId bomCoProductId = bom.getCoProducts().get(0).getProductId();
		if (coProductId != null && !ProductId.equals(bomCoProductId, coProductId))
		{
			throw new AdempiereException(ERROR_FOUND_BOM_CO_PRODUCT_DOESNT_MATCH_SETTINGS);
		}
	}
}
