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

package de.metas.contracts.modular;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.location.CountryId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.IPricingBL;
import de.metas.pricing.service.ProductPrices;
import de.metas.pricing.service.ProductScalePriceService;
import de.metas.pricing.service.ScalePriceQtyFrom;
import de.metas.pricing.service.ScalePriceUsage;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class ModularContractPriceService
{
	private final ModularContractPriceRepository modularContractPriceRepository;
	private final ModularContractComputingMethodHandlerRegistry modularContractComputingMethodHandlerRegistry;
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	private final ProductScalePriceService productScalePriceService;

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

	public static final AdMessageKey MSG_ERROR_MODULARCONTRACTPRICE_NO_SCALE_PRICE = AdMessageKey.of("MSG_ModularContractPrice_NoScalePrice");

	public ModCntrSpecificPrice getById(@NonNull final ModCntrSpecificPriceId id)
	{
		return modularContractPriceRepository.getById(id);
	}

	public void deleteById(@NonNull final ModCntrSpecificPriceId id)
	{
		modularContractPriceRepository.deleteById(id);
	}

	public void createModularContractSpecificPricesFor(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID());
		if (modularContractPriceRepository.isSpecificPricesExistForFlatrateTermId(flatrateTermId))
		{
			return;
		}

		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermId(flatrateTermId);
		final IEditablePricingContext pricingContextTemplate = createPricingContextTemplate(flatrateTermRecord, settings);

		final List<ModuleConfig> moduleConfigs = settings.getModuleConfigsWithout(ComputingMethodType.INTERIM_CONTRACT);

		for (final ModuleConfig config : moduleConfigs)
		{
			final IComputingMethodHandler handler = modularContractComputingMethodHandlerRegistry.getApplicableHandlerFor(config.getComputingMethodType());
			handler.streamContractSpecificPricedProductIds(config.getId().getModularContractModuleId())
					.forEach(productId -> {
						setProductDataOnPricingContext(productId, pricingContextTemplate);
						createModCntrSpecificPrices(flatrateTermRecord, productId, config, pricingContextTemplate);
					});
		}
	}

	public void createInterimContractSpecificPricesFor(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID());
		if (modularContractPriceRepository.isSpecificPricesExistForFlatrateTermId(flatrateTermId))
		{
			return;
		}

		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermId(flatrateTermId);
		final ModuleConfig interimContractModule = settings.getSingleModuleConfig(ComputingMethodType.INTERIM_CONTRACT).orElse(null);
		if (interimContractModule == null)
		{
			// there is no module for interim prices
			return;
		}

		final IEditablePricingContext pricingContextTemplate = createPricingContextTemplate(flatrateTermRecord, settings);
		final ProductId productId = settings.getRawProductId();
		setProductDataOnPricingContext(productId, pricingContextTemplate);
		createModCntrSpecificPrices(flatrateTermRecord, productId, interimContractModule, pricingContextTemplate);

	}

	private void setProductDataOnPricingContext(final ProductId productId, final IEditablePricingContext pricingContextTemplate)
	{
		final I_M_Product product = productDAO.getById(productId);
		pricingContextTemplate.setProductId(productId)
				.setQty(Quantity.of(BigDecimal.ONE, uomDAO.getById(UomId.ofRepoId(product.getC_UOM_ID()))));
	}

	private void createModCntrSpecificPrices(final @NonNull I_C_Flatrate_Term flatrateTermRecord, final ProductId productId, @NonNull final ModuleConfig moduleConfig, final @NonNull IEditablePricingContext pricingContextTemplate)
	{
		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContextTemplate);


		final ModCntrSpecificPrice.ModCntrSpecificPriceBuilder specificPriceTemplate = ModCntrSpecificPrice.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID()))
				.modularContractModuleId(moduleConfig.getId().getModularContractModuleId())
				.taxCategoryId(pricingResult.getTaxCategoryId())
				.uomId(pricingResult.getPriceUomId())
				.amount(pricingResult.getPriceStdAsMoney())
				.productId(productId)
				.seqNo(moduleConfig.getSeqNo());

		if (moduleConfig.isMatching(ComputingMethodType.AverageAddedValueOnShippedQuantity))
		{
			final I_M_ProductPrice productPrice = ProductPrices.retrieveMainProductPriceOrNull(pricingResult.getPriceListVersionId(), productId);
			if (productPrice == null)
			{
				throw new AdempiereException(MSG_ERROR_MODULARCONTRACTPRICE_NO_SCALE_PRICE);
			}

			final ScalePriceUsage scalePriceUsage = ScalePriceUsage.ofCode(productPrice.getUseScalePrice());
			if (!scalePriceUsage.isUseScalePrice())
			{
				throw new AdempiereException(MSG_ERROR_MODULARCONTRACTPRICE_NO_SCALE_PRICE)
						.appendParametersToMessage()
						.setParameter("M_ProductPrice_ID", productPrice.getM_ProductPrice_ID());
			}

			if (!scalePriceUsage.isUseScalePriceStrict())
			{
				modularContractPriceRepository.save(specificPriceTemplate.isScalePrice(true).minValue(BigDecimal.ZERO).build());
			}

			//
			//
			final ScalePriceQtyFrom scalePriceQtyFrom = ScalePriceQtyFrom.optionalOfNullableCode(productPrice.getScalePriceQuantityFrom()).orElse(null);
			if (scalePriceQtyFrom == null || !scalePriceQtyFrom.isQuantity())
			{
				final ProductPriceId productPriceId = ProductPriceId.ofRepoId(productPrice.getM_ProductPrice_ID());
				final ImmutableList<ModCntrSpecificPrice> specificPrices = productScalePriceService.getScalePrices(productPriceId)
						.stream()
						.map(scale -> specificPriceTemplate.isScalePrice(true).minValue(scale.getQuantityMin())
								.amount(Money.of(scale.getPriceStd(),pricingResult.getCurrencyId()))
								.build())
						.collect(ImmutableList.toImmutableList());

				modularContractPriceRepository.saveAll(specificPrices);
			}
		}
		else
		{
			modularContractPriceRepository.save(specificPriceTemplate.isScalePrice(false).minValue(null).build());
		}

	}

	private IEditablePricingContext createPricingContextTemplate(final @NonNull I_C_Flatrate_Term flatrateTermRecord, final ModularContractSettings settings)
	{
		final OrgId orgId = OrgId.ofRepoId(flatrateTermRecord.getAD_Org_ID());
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID(), flatrateTermRecord.getBill_Location_ID());
		final CountryId countryId = partnerDAO.getCountryId(bpartnerLocationId);
		return pricingBL.createPricingContext()
				.setOrgId(orgId)
				.setFailIfNotCalculated()
				.setSOTrx(settings.getSoTrx())
				.setPricingSystemId(settings.getPricingSystemId())
				.setBPartnerId(BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID()))
				.setCountryId(countryId)
				.setPriceDate(InstantAndOrgId.ofTimestamp(flatrateTermRecord.getStartDate(), orgId).toLocalDate(orgDAO::getTimeZone));
	}

	public ModCntrSpecificPrice updateById(@NonNull final ModCntrSpecificPriceId id, @NonNull final UnaryOperator<ModCntrSpecificPrice> mapper)
	{
		return modularContractPriceRepository.updateById(id, mapper);
	}

	public ModCntrSpecificPrice cloneById(@NonNull final ModCntrSpecificPriceId id, @NonNull final UnaryOperator<ModCntrSpecificPrice> mapper)
	{
		return modularContractPriceRepository.cloneById(id, mapper);
	}

}
