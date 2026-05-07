/*
 * #%L
 * de.metas.business
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

package de.metas.order.paymentschedule.interceptor;

import de.metas.order.OrderId;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.OrderPayScheduleMaterialReceiptService;
import de.metas.order.paymentschedule.steps.material_receipt.OrderPayScheduleMaterialReceiptStepService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut.class)
@Component
@RequiredArgsConstructor
public class M_InOut
{
	@NonNull private final OrderPayScheduleMaterialReceiptStepService materialReceiptStepService;

	/**
	 * Fires on AFTER_COMPLETE only.
	 * Threads the in-memory completing receipt through so the recompute can see it
	 * even before it is saved to DB ({@code DocStatus} is still {@code IP} in DB at this point).
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onComplete(@NonNull final I_M_InOut inoutRecord)
	{
		materialReceiptStepService.recomputeDeliverySteps(inoutRecord);
	}

	/**
	 * Fires on AFTER_REVERSECORRECT and AFTER_REVERSEACCRUAL.
	 *
	 * <p>Threads the in-memory reversed receipt down so the recompute excludes it from the
	 * receipts list even though the DB still shows it as {@code DocStatus=CO / Reversal_ID=0}
	 * at the point this interceptor fires.  See
	 * {@link OrderPayScheduleMaterialReceiptStepService#recomputeDeliveryStepsAfterReceiptReversed}
	 * for the full explanation.
	 */
	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL
	})
	public void onReverse(@NonNull final I_M_InOut inoutRecord)
	{
		if (!OrderPayScheduleMaterialReceiptService.isMaterialReceipt(inoutRecord))
		{
			return;
		}

		final OrderId orderId = OrderPayScheduleMaterialReceiptService.extractOrderIdOrNull(inoutRecord);
		if (orderId == null)
		{
			return;
		}

		materialReceiptStepService.recomputeDeliveryStepsAfterReceiptReversed(orderId, inoutRecord);
	}
}
