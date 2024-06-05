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

import com.google.common.collect.ImmutableList;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.settings.InvoicingGroupType;
import de.metas.contracts.modular.settings.ModularContractModuleUpdateRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.contracts.modular.settings.ModularContractType;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
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
import java.util.Optional;

import static de.metas.contracts.modular.ComputingMethodType.AddValueOnProcessedProduct;
import static de.metas.contracts.modular.ComputingMethodType.AddValueOnRawProduct;
import static de.metas.contracts.modular.ComputingMethodType.SalesOnProcessedProduct;
import static de.metas.contracts.modular.ComputingMethodType.SalesOnRawProduct;
import static de.metas.contracts.modular.ComputingMethodType.SubtractValueOnRawProduct;

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
	private static final AdMessageKey ERROR_SALES_PROCESSED_PRODUCT_REQUIRED_INV_GROUP = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.SalesOnProcessedProductRequiredInvoicingGroup");
	private static final AdMessageKey ERROR_PRODUCT_NEEDS_SAME_STOCK_UOM_AS_RAW = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsRaw");
	private static final AdMessageKey ERROR_PRODUCT_NEEDS_SAME_STOCK_UOM_AS_PROCESSED = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.SettingLineProductNeedsSameStockUOMAsProcessed");
	private static final AdMessageKey ERROR_INTERIM_REQUIRED_INV_GROUP = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.InterimRequiredInvoicingGroup");
	private static final AdMessageKey ERROR_INV_GROUP_NOT_FOUND = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.InvoicingGroupNotFound");
	private static final AdMessageKey ERROR_ADDED_SUBTRACTED_VALUE_ON_INTERIM = AdMessageKey.of("de.metas.contracts.modular.settings.interceptor.ERROR_ADDED_SUBTRACTED_VALUE_ON_INTERIM");

	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final ModularContractSettingsBL modularContractSettingsBL;
	@NonNull private final ModularContractSettingsDAO modularContractSettingsDAO;
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

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
			final String productName = productBL.getByIdInTrx(productId).getName();
			final String pricingSystemName = Objects.requireNonNull(priceListDAO.getPricingSystemById(pricingSystemId)).getName();
			throw new AdempiereException(productNotInPS, productName, pricingSystemName);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void validateModuleComputingMethods(@NonNull final I_ModCntr_Module record)
	{
		final ModuleConfig module = modularContractSettingsDAO.fromRecord(record);
		final ModularContractSettingsId modularContractSettingsId = module.getModularContractSettingsId();
		final ModularContractType type = module.getModularContractType();
		final ComputingMethodType computingMethodType = type.getComputingMethodType();
		final ModularContractSettings settings = modularContractSettingsBL.getById(modularContractSettingsId);
		final ProductId productId = module.getProductId();
		final Optional<InvoicingGroupId> invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(settings.getRawProductId(), settings.getYearAndCalendarId());

		final boolean hasAlreadyComputingTypeAndProduct = settings.countMatching(computingMethodType, productId) > 1;
		if (hasAlreadyComputingTypeAndProduct)
		{
			throw new AdempiereException("Combination of ComputingMethodType and ProductId needs to be unique")
					.setParameter("ProductId", productId)
					.setParameter("ComputingMethodType", type.getName());
		}

		final boolean bothSalesOnRawProductAndProcessedProductSet = settings.countMatchingAnyOf(SalesOnRawProduct, SalesOnProcessedProduct) > 1;
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
			case INTERIM_CONTRACT ->
			{
				if (!module.getInvoicingGroup().isServicesType())
				{
					throw new AdempiereException(ERROR_INTERIM_REQUIRED_INV_GROUP, InvoicingGroupType.SERVICES.getDisplayName());
				}
			}

			case SalesOnRawProduct ->
			{
				if (!module.getInvoicingGroup().isServicesType())
				{
					throw new AdempiereException(ERROR_SALES_RAW_PRODUCT_REQUIRED_INV_GROUP, InvoicingGroupType.SERVICES.getDisplayName());
				}

				if (!ProductId.equals(settings.getRawProductId(), productId))
				{
					throw new AdempiereException(ERROR_ComputingMethodRequiresRawProduct);
				}
				settings.getSingleModuleConfig(ComputingMethodType.DefinitiveInvoiceRawProduct)
						.ifPresent(definitiveInvoiceMethodType -> updateDefinitiveModuleName(definitiveInvoiceMethodType, module.getName()));
			}

			case SalesOnProcessedProduct ->
			{
				if (!module.getInvoicingGroup().isServicesType())
				{
					throw new AdempiereException(ERROR_SALES_PROCESSED_PRODUCT_REQUIRED_INV_GROUP, InvoicingGroupType.SERVICES.getDisplayName());
				}

				if (!ProductId.equals(settings.getProcessedProductId(), productId))
				{
					throw new AdempiereException(ERROR_ComputingMethodRequiresProcessedProduct);
				}
				settings.getSingleModuleConfig(ComputingMethodType.DefinitiveInvoiceProcessedProduct)
						.ifPresent(definitiveInvoiceMethodType -> updateDefinitiveModuleName(definitiveInvoiceMethodType, module.getName()));
			}
			case CoProduct ->
			{
				if (!ProductId.equals(settings.getCoProductId(), productId))
				{
					throw new AdempiereException(ERROR_ComputingMethodRequiresCoProduct);

				}
			}
			case AddValueOnInterim, SubtractValueOnInterim ->
			{
				if (!module.getInvoicingGroup().isCostsType())
				{
					throw new AdempiereException(ERROR_ADDED_SUBTRACTED_VALUE_ON_INTERIM, InvoicingGroupType.COSTS.getDisplayName());
				}
				if (invoicingGroupId.isEmpty())
				{
					throw new AdempiereException(ERROR_INV_GROUP_NOT_FOUND);
				}
			}
		}
	}

	private void updateDefinitiveModuleName(@NonNull final ModuleConfig moduleConfig, @NonNull final String productName)
	{
		modularContractSettingsDAO.updateModule(moduleConfig.getId().getModularContractModuleId(), ModularContractModuleUpdateRequest.builder()
				.moduleName(productName)
				.build());
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void validateProductsUOM(@NonNull final I_ModCntr_Module record)
	{
		final ModuleConfig module = modularContractSettingsDAO.fromRecord(record);
		final UomId moduleUOMId = productBL.getStockUOMId(module.getProductId());
		final ModularContractSettings settings = modularContractSettingsBL.getById(module.getModularContractSettingsId());

		final ImmutableList<ComputingMethodType> rawComputingMethods = ImmutableList.of(AddValueOnRawProduct, SubtractValueOnRawProduct);
		final UomId rawUOMId = productBL.getStockUOMId(settings.getRawProductId());
		if (module.isMatchingAnyOf(rawComputingMethods) && !UomId.equals(moduleUOMId, rawUOMId))
		{
			throw new AdempiereException(ERROR_PRODUCT_NEEDS_SAME_STOCK_UOM_AS_RAW);
		}

		if (settings.getProcessedProductId() == null)
		{
			return;
		}
		final ImmutableList<ComputingMethodType> processedComputingMethods = ImmutableList.of(AddValueOnProcessedProduct);
		final UomId processedUOMId = productBL.getStockUOMId(settings.getProcessedProductId());
		if (module.isMatchingAnyOf(processedComputingMethods) && !UomId.equals(moduleUOMId, processedUOMId))
		{
			throw new AdempiereException(ERROR_PRODUCT_NEEDS_SAME_STOCK_UOM_AS_PROCESSED);
		}
	}
}
