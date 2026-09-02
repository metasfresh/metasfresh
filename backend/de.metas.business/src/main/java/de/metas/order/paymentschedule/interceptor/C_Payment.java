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

import de.metas.order.paymentschedule.steps.letter_of_credit.OrderPayScheduleLCStepService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Payment.class)
@Component
@RequiredArgsConstructor
public class C_Payment
{
	@NonNull private final OrderPayScheduleLCStepService lcStepService;

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onPaymentCompleted(@NonNull final I_C_Payment paymentRecord)
	{
		lcStepService.recomputeLCStepAfterPaymentCompleted(paymentRecord);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL
	})
	public void onPaymentReversed(@NonNull final I_C_Payment payment)
	{
		lcStepService.recomputeLCStepAfterPaymentReversed(payment);
	}
}
