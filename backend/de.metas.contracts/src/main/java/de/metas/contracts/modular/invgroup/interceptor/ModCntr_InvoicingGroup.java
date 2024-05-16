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

package de.metas.contracts.modular.invgroup.interceptor;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.InvoicingGroupProductId;
import de.metas.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_ModCntr_InvoicingGroup.class)
@AllArgsConstructor
public class ModCntr_InvoicingGroup
{
	private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_ModCntr_InvoicingGroup.COLUMNNAME_C_Harvesting_Calendar_ID, I_ModCntr_InvoicingGroup.COLUMNNAME_Harvesting_Year_ID })
	public void validateProductNotAlreadyUsed(@NonNull final I_ModCntr_InvoicingGroup invoicingGroup)
	{
		modCntrInvoicingGroupRepository.streamInvoicingGroupProductsFor(InvoicingGroupId.ofRepoId(invoicingGroup.getModCntr_InvoicingGroup_ID()))
				.forEach(invGroupProduct -> {
					final ProductId productId = ProductId.ofRepoId(invGroupProduct.getM_Product_ID());
					final InvoicingGroupProductId invoicingGroupProductId = InvoicingGroupProductId.ofRepoId(invGroupProduct.getModCntr_InvoicingGroup_Product_ID());
					final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoId(invoicingGroup.getHarvesting_Year_ID(), invoicingGroup.getC_Harvesting_Calendar_ID());
					modCntrInvoicingGroupRepository.validateInvoicingGroupProductNoOverlap(productId, invoicingGroupProductId, yearAndCalendarId);
				});
	}

}
