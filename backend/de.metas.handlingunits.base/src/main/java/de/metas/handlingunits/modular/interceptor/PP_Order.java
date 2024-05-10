/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.modular.interceptor;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.modular.ModularPPOrderService;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

@Interceptor(I_PP_Order.class)
@Component
@RequiredArgsConstructor
public class PP_Order
{
	private static final AdMessageKey MSG_CannotReactivateVoid = AdMessageKey.of("de.metas.handlingunits.modular.interceptor.PP_Order.CannotReactivateVoid");

	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPPOrderBL orderBL = Services.get(IPPOrderBL.class);

	@NonNull private final ModularPPOrderService modularPPOrderService;

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL })
	public void onBeforeReverse(@NonNull final I_PP_Order order)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(order.getPP_Order_ID());

		if (orderBL.isModularOrder(ppOrderId) && orderBL.isSomethingProcessed(order))
		{
			throw new AdempiereException(MSG_CannotReactivateVoid)
					.appendParametersToMessage()
					.setParameter("PP_Order_ID", ppOrderId)
					.markAsUserValidationError();
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = { I_PP_Order.COLUMNNAME_C_DocTypeTarget_ID, I_PP_Order.COLUMNNAME_Modular_Flatrate_Term_ID })
	public void validateManufacturingModularOrder(@NonNull final I_PP_Order ppOrderRecord)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoId(ppOrderRecord.getC_DocTypeTarget_ID());

		if (!docTypeBL.isModularManufacturingOrder(docTypeId))
		{
			ppOrderRecord.setModular_Flatrate_Term_ID(0); // dev-note: reset it if the order is not modular anymore
			return;
		}

		modularPPOrderService.validateModularOrder(PPOrderId.ofRepoId(ppOrderRecord.getPP_Order_ID()));
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void onBeforeComplete(@NonNull final I_PP_Order order)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(order.getPP_Order_ID());

		if (orderBL.isModularOrder(ppOrderId))
		{
			modularPPOrderService.validateModularOrder(ppOrderId);
		}
	}
}
