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

package de.metas.payment.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Guards the {@code C_Payment.IsAutoAllocateAvailableAmt} flag on proforma-prepayment payments.
 *
 * <p>A proforma-prepayment payment is identified by a non-zero {@code Proforma_Invoice_ID}
 * (same canonical "is this a proforma prepayment" check used by {@code C_Payment_LCStep} and
 * the iter-2 proforma flow). Once such a payment has at least one
 * {@link ProformaOrderAllocService#hasAllocations(InvoiceId) proforma-order allocation}, the
 * auto-allocation flag must remain {@code N} — the proforma-prepayment flow assumes the
 * payment amount is reserved for the proforma's eventual final invoice, so opening it up to the
 * generic auto-allocation engine would race with the LC-step machinery and double-allocate.
 *
 * <p>Restricted direction only: the guard fires only when {@code IsAutoAllocateAvailableAmt}
 * is being flipped to {@code Y}. Saving the same payment with the flag still at {@code N} is
 * always allowed — that is the AC-mandated default for proforma prepayments and the most
 * common save path through this interceptor.
 *
 * <p>Regular (non-proforma) payments are entirely outside scope: the iter-2 / pre-existing
 * auto-allocation flow on AR/AP payments must continue to work and the guard's
 * {@code Proforma_Invoice_ID > 0} predicate ensures we never interfere with it.
 *
 * <p>The error message references the column name {@code IsAutoAllocateAvailableAmt} so that
 * a user-facing UAT or REST-API consumer can identify which field caused the rejection without
 * having to consult the AD_Message catalog.
 */
@Interceptor(I_C_Payment.class)
@Component
@RequiredArgsConstructor
public class C_Payment_AutoAllocateGuard
{
	private static final AdMessageKey MSG_IsAutoAllocateAvailableAmt_ProformaPrepayment_Forbidden =
			AdMessageKey.of("IsAutoAllocateAvailableAmt_ProformaPrepayment_Forbidden");

	@NonNull private final ProformaOrderAllocService proformaOrderAllocService;

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Payment.COLUMNNAME_IsAutoAllocateAvailableAmt)
	public void rejectFlipOnProformaPrepayment(@NonNull final I_C_Payment payment)
	{
		// Only the flip-to-true direction is restricted; flip-to-false is always allowed (and
		// is the AC-mandated default state on proforma prepayments).
		if (!payment.isAutoAllocateAvailableAmt())
		{
			return;
		}

		// Wrap raw int at the boundary, per RepoIdAware coding rule (java-general.md §6).
		final InvoiceId proformaInvoiceId = InvoiceId.ofRepoIdOrNull(payment.getProforma_Invoice_ID());
		if (proformaInvoiceId == null)
		{
			// Regular payment — the iter-2 auto-allocation flow must not be disturbed.
			return;
		}

		if (!proformaOrderAllocService.hasAllocations(proformaInvoiceId))
		{
			// Proforma invoice without an allocation to a purchase order is not yet a real
			// proforma-prepayment scenario — the guard does not apply.
			return;
		}

		throw new AdempiereException(MSG_IsAutoAllocateAvailableAmt_ProformaPrepayment_Forbidden)
				.markAsUserValidationError();
	}
}
