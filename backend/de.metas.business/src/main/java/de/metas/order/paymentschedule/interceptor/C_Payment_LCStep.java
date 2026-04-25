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

import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.service.OrderPayScheduleLCService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Drives {@link OrderPayScheduleLCService#recomputeLCStep} from the C_Payment doc-action lifecycle.
 *
 * <p>Uses {@code @DocValidate} (not {@code @ModelChange} on DocStatus) so each interception
 * fires once per genuine doc-action — not on every transient DocStatus column change inside
 * {@code processIt(...)} or {@code reverseCorrectIt(...)}. See {@code architecture.md} §6
 * for the rationale.
 *
 * <p>Two events are wired:
 * <ul>
 *   <li><b>AFTER_COMPLETE</b> — payment was completed via processIt(Complete). For a "real"
 *       completion we recompute (LC step → Paid). The reversal payment that
 *       {@link org.compiere.model.MPayment#reverseCorrectIt()} creates also goes through
 *       processIt(Complete) transiently before being flipped to Reversed; we skip those by
 *       checking {@code PayAmt.signum() < 0} (reversal payments carry a negated amount).
 *   <li><b>AFTER_REVERSECORRECT / AFTER_REVERSEACCRUAL</b> — payment was reversed. Both the
 *       original and the reversal are at DocStatus=Reversed at this point, so the authority
 *       query finds zero CO/CL payments for the proforma → LC step rolls back to Awaiting_Pay.
 * </ul>
 *
 * <p>Idempotence: {@link OrderPayScheduleLCService#recomputeLCStep} is total and idempotent;
 * a duplicate fire produces no additional change.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29368">me03 #29368 Split-Payment Iter 2</a>
 */
@Interceptor(I_C_Payment.class)
@Component
@RequiredArgsConstructor
public class C_Payment_LCStep
{
	@NonNull private final ProformaOrderAllocRepository proformaOrderAllocRepository;
	@NonNull private final OrderPayScheduleLCService lcService;

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onPaymentCompleted(@NonNull final I_C_Payment payment)
	{
		// Skip reversal payments: their AFTER_COMPLETE is transient (the reversal is flipped to
		// Reversed immediately afterwards in MPayment.reverseCorrectIt). The authoritative
		// recompute for the reversal flow happens via TIMING_AFTER_REVERSECORRECT below, where
		// both the original and the reversal sit at DocStatus=Reversed.
		if (payment.getPayAmt().signum() < 0)
		{
			return;
		}
		recomputeLCStepIfProforma(payment);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL
	})
	public void onPaymentReversed(@NonNull final I_C_Payment payment)
	{
		recomputeLCStepIfProforma(payment);
	}

	private void recomputeLCStepIfProforma(@NonNull final I_C_Payment payment)
	{
		final InvoiceId proformaInvoiceId = InvoiceId.ofRepoIdOrNull(payment.getProforma_Invoice_ID());
		if (proformaInvoiceId == null)
		{
			return;
		}

		final OrderId orderId = proformaOrderAllocRepository
				.findOrderIdByProformaInvoiceId(proformaInvoiceId)
				.orElse(null);
		if (orderId == null)
		{
			return;
		}

		lcService.recomputeLCStep(orderId);
	}
}
