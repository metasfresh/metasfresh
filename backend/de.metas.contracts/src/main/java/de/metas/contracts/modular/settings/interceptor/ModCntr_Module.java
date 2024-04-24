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

package de.metas.contracts.modular.settings.interceptor;

import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.X_ModCntr_Module;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Objects;

import static de.metas.contracts.modular.ComputingMethodType.SalesOnProcessedProduct;
import static de.metas.contracts.modular.ComputingMethodType.SalesOnRawProduct;

@Component
@Interceptor(I_ModCntr_Module.class)
@AllArgsConstructor
public class ModCntr_Module
{
	private static final AdMessageKey MOD_CNTR_SETTINGS_CANNOT_BE_CHANGED = AdMessageKey.of("ModCntr_Settings_cannot_be_changed");
	private static final AdMessageKey productNotInPS = AdMessageKey.of("de.metas.pricing.ProductNotInPriceSystem");
	private static final AdMessageKey ERROR_ComputingMethodRequiresRawProduct = AdMessageKey.of("ComputingMethodTypeRequiresRawProduct");
	private static final AdMessageKey ERROR_ComputingMethodRequiresProcessedProduct = AdMessageKey.of("ComputingMethodTypeRequiresProcessedProduct");
	private static final AdMessageKey ERROR_ComputingMethodRequiresCoProduct = AdMessageKey.of("ComputingMethodTypeRequiresCoProduct");
	private static final AdMessageKey ERROR_SALES_RAW_AND_PROCESSED_PRODUCT_BOTH_SET = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError");
	private static final AdMessageKey ERROR_SALES_RAW_PRODUCT_REQUIRED_INV_GROUP = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup");

	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@NonNull private final ModularContractSettingsBL modularContractSettingsBL;
	@NonNull private final ADReferenceService adReferenceService;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void validateModule(@NonNull final I_ModCntr_Module moduleRecord)
	{
		modularContractSettingsBL.validateModularContractSettingsNotUsed(ModularContractSettingsId.ofRepoId(moduleRecord.getModCntr_Settings_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE, ModelValidator.TYPE_BEFORE_NEW })
	public void validateSettingsNotUsedAlready(@NonNull final I_ModCntr_Module type)
	{
		final ModularContractSettingsId modCntrSettingsId = ModularContractSettingsId.ofRepoId(type.getModCntr_Settings_ID());

		if (modularContractSettingsBL.isSettingsUsedInCompletedFlatrateConditions(modCntrSettingsId))
		{
			throw new AdempiereException(MOD_CNTR_SETTINGS_CANNOT_BE_CHANGED);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validateSettings(@NonNull final I_ModCntr_Module record)
	{
		final ModularContractSettings settings = modularContractSettingsBL.getById(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()));

		validateProductInPS(ProductId.ofRepoIdOrNull(record.getM_Product_ID()), settings.getPricingSystemId(), settings.getSoTrx());
	}

	private void validateProductInPS(@Nullable final ProductId productId, @NonNull final PricingSystemId pricingSystemId, @NonNull final SOTrx soTrx)
	{
		if (productId != null && !priceListDAO.isProductPriceExistsInSystem(pricingSystemId, soTrx, productId))
		{
			final String productName = productDAO.getByIdInTrx(productId).getName();
			final String pricingSystemName = Objects.requireNonNull(priceListDAO.getPricingSystemById(pricingSystemId)).getName();
			throw new AdempiereException(productNotInPS, productName, pricingSystemName);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW })
	public void validateModuleComputingMethods(@NonNull final I_ModCntr_Module type)
	{
		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId.ofRepoId(type.getModCntr_Settings_ID());
		final ModularContractModuleId modularContractModuleId = ModularContractModuleId.ofRepoId(type.getModCntr_Module_ID());
		final ComputingMethodType computingMethodType = ComputingMethodType.ofCode(type.getModCntr_Type().getModularContractHandlerType());
		final ModularContractSettings settings = modularContractSettingsBL.getById(modularContractSettingsId);
		final ProductId productId = ProductId.ofRepoId(type.getM_Product_ID());

		final boolean hasAlreadyComputingTypeAndProduct = settings.getModuleConfigs().stream()
				.filter(moduleConfig -> !ModularContractModuleId.equals(moduleConfig.getId().getModularContractModuleId(), modularContractModuleId))
				.filter(moduleConfig -> moduleConfig.isMatching(computingMethodType))
				.anyMatch(moduleConfig -> ProductId.equals(moduleConfig.getProductId(), productId));

		if (hasAlreadyComputingTypeAndProduct)
		{
			throw new AdempiereException("Combination of ComputingMethodType and ProductId needs to be unique")
					.setParameter("ProductId: ", type.getM_Product_ID())
					.setParameter("ComputingMethodType: ", type.getModCntr_Type().getName());
		}

		final boolean bothSalesOnRawProductAndProcessedProductSet = settings.getModuleConfigs()
				.stream()
				.filter(module -> module.isMatching(SalesOnRawProduct)
						|| module.isMatching(SalesOnProcessedProduct))
				.count() > 1;

		if (bothSalesOnRawProductAndProcessedProductSet)
		{
			throw new AdempiereException(ERROR_SALES_RAW_AND_PROCESSED_PRODUCT_BOTH_SET);
		}

		switch (computingMethodType)
		{
			case Receipt ->
			{
				if (!ProductId.equals(settings.getRawProductId(), productId))
				{
					throw new AdempiereException(ERROR_ComputingMethodRequiresRawProduct);
				}
			}

			case SalesOnRawProduct ->
			{
				if (!X_ModCntr_Module.INVOICINGGROUP_Service.equals(type.getInvoicingGroup()))
				{
					final ITranslatableString translatedValue = adReferenceService.retrieveListNameTranslatableString(
							ReferenceId.ofRepoId(X_ModCntr_Module.INVOICINGGROUP_AD_Reference_ID),
							X_ModCntr_Module.INVOICINGGROUP_Service);

					throw new AdempiereException(ERROR_SALES_RAW_PRODUCT_REQUIRED_INV_GROUP, translatedValue);
				}

				if (!ProductId.equals(settings.getRawProductId(), productId))
				{
					throw new AdempiereException(ERROR_ComputingMethodRequiresRawProduct);
				}
			}

			case SalesOnProcessedProduct ->
			{
				if (!ProductId.equals(settings.getProcessedProductId(), productId))
				{
					throw new AdempiereException(ERROR_ComputingMethodRequiresProcessedProduct);

				}
			}
			case CoProduct, ReductionCalibration ->
			{
				if (!ProductId.equals(settings.getProcessedProductId(), productId))
				{
					throw new AdempiereException(ERROR_ComputingMethodRequiresCoProduct);

				}
			}

		}
	}

}
