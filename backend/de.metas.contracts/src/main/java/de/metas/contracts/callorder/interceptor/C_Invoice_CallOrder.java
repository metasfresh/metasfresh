/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.interceptor;

import de.metas.contracts.callorder.detail.document.DocumentChangeHandler_Invoice;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice_CallOrder
{
	@NonNull
	private final DocumentChangeHandler_Invoice invoiceDocumentChangeHandler;

	public C_Invoice_CallOrder(@NonNull final DocumentChangeHandler_Invoice invoiceDocumentChangeHandler)
	{
		this.invoiceDocumentChangeHandler = invoiceDocumentChangeHandler;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void handleReactivate(@NonNull final I_C_Invoice invoice)
	{
		invoiceDocumentChangeHandler.onReactivate(invoice);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void handleComplete(@NonNull final I_C_Invoice invoice)
	{
		invoiceDocumentChangeHandler.onComplete(invoice);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void handleReversed(@NonNull final I_C_Invoice invoice)
	{
		invoiceDocumentChangeHandler.onReverse(invoice);
	}
}

