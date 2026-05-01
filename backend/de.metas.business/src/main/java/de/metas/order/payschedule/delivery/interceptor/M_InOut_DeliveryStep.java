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

package de.metas.order.payschedule.delivery.interceptor;

import de.metas.inout.InOutId;
import de.metas.order.OrderId;
import de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Drives {@link OrderPayScheduleDeliveryService#recomputeDeliverySteps} from the
 * {@code M_InOut} (receipt) doc-action lifecycle.
 *
 * <p>Fires on three timings:
 * <ul>
 *   <li><b>AFTER_COMPLETE</b> — a new receipt has been completed (goods received).
 *       The delivery sub-row for this receipt should be created / updated.
 *   <li><b>AFTER_REVERSECORRECT / AFTER_REVERSEACCRUAL</b> — a receipt was reversed.
 *       The corresponding sub-row should revert to Pending and its invoice FK cleared
 *       (the service re-derives this from current DB truth).
 * </ul>
 *
 * <p>Guards:
 * <ul>
 *   <li>Sales receipts ({@code IsSOTrx=Y}) are skipped — only purchase receipts are
 *       relevant to the split-payment Delivery step.
 *   <li>Receipts without a linked order ({@code C_Order_ID <= 0}) are skipped.
 * </ul>
 *
 * <p>The service is idempotent; a duplicate fire produces no additional change.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Interceptor(I_M_InOut.class)
@Component
@RequiredArgsConstructor
public class M_InOut_DeliveryStep
{
	@NonNull private final OrderPayScheduleDeliveryService deliveryService;

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL
	})
	public void recompute(@NonNull final I_M_InOut inout)
	{
		// Only purchase receipts drive the Delivery step
		if (inout.isSOTrx())
		{
			return;
		}
		// Must be linked to a purchase order
		if (inout.getC_Order_ID() <= 0)
		{
			return;
		}
		// Pass the completing receipt's ID so loadInputs can include it regardless of its
		// DB DocStatus (which is still "DR" when TIMING_AFTER_COMPLETE fires — see
		// DocumentEngine.prepareIt() which sets "IP" in memory only, never saves to DB).
		final InOutId completingReceiptId = inout.getM_InOut_ID() > 0
				? InOutId.ofRepoId(inout.getM_InOut_ID())
				: null;
		deliveryService.recomputeDeliverySteps(OrderId.ofRepoId(inout.getC_Order_ID()), completingReceiptId);
	}
}
