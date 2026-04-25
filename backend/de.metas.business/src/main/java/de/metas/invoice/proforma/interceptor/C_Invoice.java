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

package de.metas.invoice.proforma.interceptor;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice.class)
@Component
@RequiredArgsConstructor
public class C_Invoice
{
	// Dedicated AdMessageKey introduced by migration 5799610 — semantic match for "DocAction
	// (reverse / void / reactivate) is blocked because the proforma invoice has an active
	// C_Proforma_Order_Alloc and must be de-allocated first".
	private static final AdMessageKey MSG_DocActionBlockedByActiveAllocation =
			AdMessageKey.of("de.metas.invoice.proforma.DocActionBlockedByActiveAllocation");

	@NonNull final ProformaOrderAllocService proformaOrderAllocService;

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REACTIVATE})
	public void beforeReverse(@NonNull final I_C_Invoice invoice)
	{
		if (proformaOrderAllocService.hasAllocations(InvoiceId.ofRepoId(invoice.getC_Invoice_ID())))
		{
			throw new AdempiereException(MSG_DocActionBlockedByActiveAllocation);
		}
	}
}
