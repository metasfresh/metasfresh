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
import de.metas.invoice.proforma.ProformaOrderAlloc;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.order.paymentschedule.service.OrderPayScheduleLCService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Fires {@link OrderPayScheduleLCService#recomputeLCStep} whenever a payment's {@code DocStatus}
 * changes (after-change) and the payment is linked to a proforma invoice.
 *
 * <p><b>Why DocStatus, not DocValidate?</b>
 * The DocStatus column is updated for the original payment when it transitions CO → RE during
 * {@code MPayment.reverseCorrectIt()}. An AFTER_CHANGE listener on DocStatus fires for that
 * transition and lets the service re-evaluate the LC step (Paid → Awaiting_Pay).
 *
 * <p><b>Guard on Proforma_Invoice_ID &lt;= 0:</b>
 * The reversal payment created inside {@code MPayment.reverseCorrectIt()} has
 * {@code Proforma_Invoice_ID = NULL} (cleared explicitly — see MPayment line 1873).
 * The guard ensures the interceptor only reacts to payments that are actually prepayments.
 *
 * <p><b>Idempotence:</b>
 * {@link OrderPayScheduleLCService#recomputeLCStep} is idempotent — if fired twice
 * (e.g. because of an additional model-change cascade), the LC step state remains correct.
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_Payment.COLUMNNAME_DocStatus })
	public void onDocStatusChanged(@NonNull final I_C_Payment payment)
	{
		final int pid = payment.getProforma_Invoice_ID();
		if (pid <= 0)
		{
			return; // not a proforma prepayment — skip
		}

		final ProformaOrderAlloc alloc = proformaOrderAllocRepository
				.findByProformaInvoiceId(InvoiceId.ofRepoId(pid))
				.orElse(null);
		if (alloc == null)
		{
			return; // defensive: no alloc row found — nothing to recompute
		}

		lcService.recomputeLCStep(alloc.getOrderId());
	}
}
