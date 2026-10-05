/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverId;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverProductId;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverRepository;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.i18n.AdMessageKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Within one compensation-group contract settings, a customer discount product may be listed on at most one take-over
 * record. A single database index cannot express this, because the settings reference sits on the parent take-over
 * record while the product sits on its child.
 */
@Interceptor(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class)
@Component
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver_Product
{
	private static final AdMessageKey MSG_TakeOverProductNotUnique = AdMessageKey.of("ContractCompensationGroup_TakeOverProductNotUnique");

	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ContractSettingsTakeOverRepository takeOverRepository;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_M_Product_ID,
					I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID,
					I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_IsActive })
	public void assertProductUniquePerSettings(final I_C_CompensationGroup_ContractSettings_TakeOver_Product record)
	{
		if (!record.isActive())
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final boolean productAlreadyListed = takeOverRepository.isProductListedInSameSettings(
				ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID()),
				productId,
				ContractSettingsTakeOverProductId.ofRepoIdOrNull(record.getC_CompensationGroup_ContractSettings_TakeOver_Product_ID()));

		if (productAlreadyListed)
		{
			throw new AdempiereException(MSG_TakeOverProductNotUnique, productBL.getProductValueAndName(productId))
					.markAsUserValidationError();
		}
	}
}
