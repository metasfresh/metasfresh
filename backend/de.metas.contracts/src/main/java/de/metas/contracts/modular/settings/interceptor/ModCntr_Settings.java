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
import de.metas.i18n.ITranslatableString;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.product.IProductBL;
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

import java.util.Optional;

@Component
@Interceptor(I_ModCntr_Settings.class)
@RequiredArgsConstructor
public class ModCntr_Settings
{
	@NonNull private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	@NonNull private final ICalendarBL calendarBL = Services.get(ICalendarBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ModularContractSettingsBL modularContractSettingsBL;


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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID })
	public void updateModulesRawProductsIfNeeded(@NonNull final I_ModCntr_Settings record)
	{
		final ImmutableList<ComputingMethodType> rawComputingMethods = ImmutableList.of(ComputingMethodType.Receipt, ComputingMethodType.SalesOnRawProduct);
		updateModulesProducts(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()), rawComputingMethods, ProductId.ofRepoId(record.getM_Raw_Product_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Co_Product_ID })
	public void updateModulesCoProductsIfNeeded(@NonNull final I_ModCntr_Settings record)
	{
		final ImmutableList<ComputingMethodType> coComputingMethods = ImmutableList.of(ComputingMethodType.CoProduct);
		updateModulesProducts(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()), coComputingMethods, ProductId.ofRepoId(record.getM_Co_Product_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Processed_Product_ID })
	public void updateModulesProcessedProductsIfNeeded(@NonNull final I_ModCntr_Settings record)
	{
		final ImmutableList<ComputingMethodType> processedComputingMethods = ImmutableList.of(ComputingMethodType.SalesOnProcessedProduct);
		updateModulesProducts(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()), processedComputingMethods, ProductId.ofRepoId(record.getM_Processed_Product_ID()));
	}

	private void updateModulesProducts(
			@NonNull final ModularContractSettingsId modularContractSettingsId,
			@NonNull final ImmutableList<ComputingMethodType> list,
			@NonNull final ProductId productId)
	{
		modularContractSettingsBL.getById(modularContractSettingsId)
				.getModuleConfigs(list)
				.forEach((moduleConfig) -> modularContractSettingsBL.updateModuleProduct(moduleConfig, productId));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_ModCntr_Settings.COLUMNNAME_M_Co_Product_ID, I_ModCntr_Settings.COLUMNNAME_M_Processed_Product_ID })
	public void validateProducts(@NonNull final I_ModCntr_Settings record)
	{
		final ProductId rawProductId = ProductId.ofRepoId(record.getM_Raw_Product_ID());
		final ProductId coProductId = ProductId.ofRepoIdOrNull(record.getM_Co_Product_ID());
		final ProductId processedProductId = ProductId.ofRepoIdOrNull(record.getM_Co_Product_ID());
		if(coProductId == null && processedProductId == null)
		{
			return;
		}
		else if (coProductId != null && processedProductId == null)
		{
			throw new AdempiereException("processed product needs to be set if co product is set"); //TODO ADMsg
		}


		final YearId harvestingYearId = YearId.ofRepoId(record.getC_Year_ID());
		final Optional<ProductBOM> bomOptional = productBOMBL.retrieveValidProductBOM(ProductBOMRequest.builder()
													 .date(calendarBL.getFirstDayOfYear(harvestingYearId).toInstant())
													 .productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(processedProductId))
													 .build());

		if(bomOptional.isEmpty())
		{
			throw new AdempiereException("No BOM found for processed product"); //TODO ADMsg
		}

		final ProductBOM bom = bomOptional.get();
		Check.assume(ProductId.equals(processedProductId, bom.getProductId()), "BOM should be for processed product");
		if(bom.getComponents().size() != 1)
		{
			throw new AdempiereException("Only BOMs with only the raw product as component are supported"); //TODO ADMsg
		}
		else if (!ProductId.equals(ProductId.ofRepoId(bom.getComponents().get(0).getM_Product_ID()), rawProductId))
		{
			throw new AdempiereException("Found BOM doesn't have raw product as component"); //TODO ADMsg
		}

		if(coProductId == null && bom.getCoProducts().isEmpty())
		{
			return;
		}
		else if (coProductId != null && bom.getCoProducts().isEmpty())
		{
			throw new AdempiereException("Co-Product is set, but matching bom doesn't have a co-product"); //TODO ADMsg
		}
		else if (bom.getCoProducts().size() != 1)
		{
			throw new AdempiereException("Only BOMs with one co product are supported"); //TODO ADMsg
		}
		final ProductId bomCoProductId = bom.getCoProducts().get(0).getProductId();
		final ITranslatableString bomCoProductName = productBL.getProductNameTrl(bomCoProductId);
		if (coProductId == null)
		{
			throw new AdempiereException("Co-Product is not set, but matching bom has a co-product " + bomCoProductName); //TODO ADMsg
		}
		if (!ProductId.equals(bomCoProductId, coProductId))
		{
			throw new AdempiereException("Co-Product doesn't match found bom with co-product " + bomCoProductName); //TODO ADMsg
		}
	}
}
