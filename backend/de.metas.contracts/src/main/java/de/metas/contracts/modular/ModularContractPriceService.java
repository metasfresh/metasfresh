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

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ModularContractPriceService
{
	private final ModularContractPriceRepository modularContractPriceRepository;
	private final ModularContractSettingsDAO modularContractSettingsDAO;

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public void createModularContractSpecificPricesFor(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID());
		if (modularContractPriceRepository.isSpecificPricesExistforFlatrateTermId(flatrateTermId))
		{
			return;
		}

		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermId(flatrateTermId);
		final IEditablePricingContext pricingContextTemplate = createPricingContextTemplate(flatrateTermRecord, settings);

		for (final ModuleConfig config : settings.getModularContractConfigs())
		{
			createModCntrSpecificPrices(flatrateTermRecord, config, pricingContextTemplate);
		}
	}

	public void createInterimContractSpecificPricesFor(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID());
		if (modularContractPriceRepository.isSpecificPricesExistforFlatrateTermId(flatrateTermId))
		{
			return;
		}

		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermId(flatrateTermId);
		final IEditablePricingContext pricingContextTemplate = createPricingContextTemplate(flatrateTermRecord, settings);

		for (final ModuleConfig config : settings.getInterimInvoiceConfigs())
		{
			createModCntrSpecificPrices(flatrateTermRecord, config, pricingContextTemplate);
		}
	}

	public void createModCntrSpecificPrices(final @NonNull I_C_Flatrate_Term flatrateTermRecord, @NonNull final ModuleConfig moduleConfig, final @NonNull IEditablePricingContext pricingContextTemplate)
	{
		final ProductId productId = moduleConfig.getProductId();
		final ModularContractModuleId modularContractModuleId = moduleConfig.getId().getModularContractModuleId();

		final IPricingResult pricingResult = getPricingResult(pricingContextTemplate, productId);
		final ModCntrSpecificPrice.ModCntrSpecificPriceBuilder modCntrSpecificPriceBuilder = ModCntrSpecificPrice.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID()))
				.modularContractModuleId(modularContractModuleId)
				.productId(productId)
				.taxCategoryId(pricingResult.getTaxCategoryId())
				.uomId(pricingResult.getPriceUomId())
				.amount(pricingResult.getPriceStdAsMoney());

		modularContractPriceRepository.save(modCntrSpecificPriceBuilder.build());
	}

	private IEditablePricingContext createPricingContextTemplate(final @NonNull I_C_Flatrate_Term flatrateTermRecord, final ModularContractSettings settings)
	{
		final OrgId orgId = OrgId.ofRepoId(flatrateTermRecord.getAD_Org_ID());
		return pricingBL.createPricingContext()
				.setFailIfNotCalculated()
				.setSOTrx(settings.getSoTrx())
				.setPricingSystemId(settings.getPricingSystemId())
				.setBPartnerId(BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID()))
				.setPriceDate(InstantAndOrgId.ofTimestamp(flatrateTermRecord.getStartDate(), orgId).toLocalDate(orgDAO::getTimeZone));
	}

	private IPricingResult getPricingResult(final @NonNull IEditablePricingContext pricingContextTemplate, @NonNull final ProductId productId)
	{
		final I_M_Product product = productDAO.getById(productId);
		final IEditablePricingContext pricingContext = pricingContextTemplate.setProductId(productId)
				.setQty(Quantity.of(BigDecimal.ONE, uomDAO.getById(UomId.ofRepoId(product.getC_UOM_ID()))));
		return pricingBL.calculatePrice(pricingContext);
	}

}
