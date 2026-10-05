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
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * A take-over record's own-line product becomes an appended compensation line (its type is forced to Discount at line
 * creation; only its amount type is taken from the product). This guard requires the own-line product to be a percentage
 * discount product and refuses anything else where it is entered, for two reasons: a non-Percent amount type would
 * silently collapse the appended line to 0% and lose the take-over; and a non-Discount (e.g. Surcharge) product is a
 * misconfiguration for what is, by design, a discount own line. The guard is therefore deliberately STRICTER than line
 * creation on the type dimension (line creation forces Discount and never inspects the product's own type).
 */
@Interceptor(I_C_CompensationGroup_ContractSettings_TakeOver.class)
@Component
public class C_CompensationGroup_ContractSettings_TakeOver
{
	private static final AdMessageKey MSG_TakeOverOwnLineProductNotPercentDiscount = AdMessageKey.of("ContractCompensationGroup_TakeOverOwnLineProductNotPercentDiscount");

	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

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

		final I_M_Product product = productBL.getById(productId);

		final GroupCompensationType type = GroupCompensationLineCreateRequestFactory.extractGroupCompensationType(product);
		final GroupCompensationAmtType amtType = GroupCompensationLineCreateRequestFactory.extractGroupCompensationAmtType(product);

		if (type != GroupCompensationType.Discount || amtType != GroupCompensationAmtType.Percent)
		{
			throw new AdempiereException(MSG_TakeOverOwnLineProductNotPercentDiscount, productBL.getProductValueAndName(productId))
					.markAsUserValidationError();
		}
	}
}
