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

import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.i18n.AdMessageKey;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
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

/** The own-line product must be a percentage discount product by its own type: deliberately stricter than line creation, which forces the type to Discount and takes only the amount type from the product. */
@Interceptor(I_C_CompensationGroup_ContractSettings_TakeOver.class)
@Component
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver
{
	private static final AdMessageKey MSG_TakeOverOwnLineProductNotPercentDiscount = AdMessageKey.of("ContractCompensationGroup_TakeOverOwnLineProductNotPercentDiscount");

	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_M_Product_ID,
					I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_IsActive })
	public void assertOwnLineProductIsPercentDiscount(final I_C_CompensationGroup_ContractSettings_TakeOver record)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(record.getM_Product_ID());
		if (!record.isActive() || productId == null)
		{
			return;
		}

		if (!compensationLineCreateRequestFactory.isPercentDiscountProduct(productId))
		{
			throw new AdempiereException(MSG_TakeOverOwnLineProductNotPercentDiscount, productBL.getProductValueAndName(productId));
		}
	}
}
