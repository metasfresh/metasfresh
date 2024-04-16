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

import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
@Interceptor(I_ModCntr_Settings.class)
@RequiredArgsConstructor
public class ModCntr_Settings
{
	private static final AdMessageKey productNotInPS = AdMessageKey.of("de.metas.pricing.ProductNotInPriceSystem"); // TODO add AD_Message

	@NonNull
	private final ModularContractSettingsBL modularContractSettingsBL;

	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void validateSettings(@NonNull final I_ModCntr_Settings record)
	{
		modularContractSettingsBL.validateModularContractSettingsNotUsed(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()));
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(record.getM_PricingSystem_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(record.isSOTrx());

		validateProductInPS(ProductId.ofRepoIdOrNull(record.getM_Raw_Product_ID()), pricingSystemId, soTrx);
		validateProductInPS(ProductId.ofRepoIdOrNull(record.getM_Processed_Product_ID()), pricingSystemId, soTrx);
		validateProductInPS(ProductId.ofRepoIdOrNull(record.getM_Co_Product_ID()), pricingSystemId, soTrx);
	}

	private void validateProductInPS(@Nullable final ProductId productId, @NonNull final PricingSystemId pricingSystemId, @NonNull final SOTrx soTrx)
	{
		if (productId != null && !priceListDAO.isProductPriceExistsInSystem(pricingSystemId, soTrx, productId))
		{

			final String productName = productDAO.getByIdInTrx(productId).getName();
			final String pricingSystemName = priceListDAO.getPricingSystemById(pricingSystemId).getName();
			throw new AdempiereException(productNotInPS, productName, pricingSystemName);
		}
	}

}
